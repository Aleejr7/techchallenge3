package com.appserviceagendamento.service;

import com.appserviceagendamento.domain.dto.*;
import com.appserviceagendamento.domain.entity.ConsultaModel;
import com.appserviceagendamento.domain.repository.ConsultaRepository;
import com.appserviceagendamento.service.Exceptions.BadRequest;
import com.appserviceagendamento.service.Exceptions.NotFound;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultaService {
    private final ConsultaRepository repository;
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final JdbcTemplate jdbc;

    public ConsultaService(ConsultaRepository repository, KafkaTemplate<String, Object> kafkaTemplate, JdbcTemplate jdbc) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.jdbc = jdbc;
    }
    public ConsultaResponse buscarAgendamento(Long id, String userRole, Long userId){
        ConsultaModel agendamento = repository.findById(id).orElseThrow(() -> new NotFound("Consulta não encontrada"));

        // Validar permissões baseado na role
        validarPermissaoAcesso(agendamento, userRole, userId);

        ConsultaResponse consulta = new ConsultaResponse(
                agendamento.getIdMedico(),
                agendamento.getIdPaciente(),
                agendamento.getDescricao(),
                agendamento.getDiaHoraConsulta(),
                agendamento.getStatus(),
                agendamento.getMotivoConsulta()
        );
        return consulta;
    }

    public ConsultaResponse criarAgendamento(ConsultaCreateDTO request, String userRole, Long userId){
        if (!"MEDICO".equals(userRole) && !"ENFERMEIRO".equals(userRole)) {
            throw new BadRequest("Apenas médicos e enfermeiros podem criar consultas");
        }

        var nomeMedico = buscarMedicoId(request.idMedico());
        DadosPaciente paciente = buscarDadosPaciente(request.idPaciente());

        if (!request.diaHoraConsulta().isAfter(LocalDateTime.now())){
            throw new BadRequest("A data da consulta deve ser no futuro.");
        }

        ConsultaModel consultaModel = new ConsultaModel(request);
        ConsultaModel consultaSalva = repository.save(consultaModel);
        ConsultaResponse response = new ConsultaResponse(
                consultaSalva.getIdMedico(),
                consultaSalva.getIdPaciente(),
                consultaSalva.getDescricao(),
                consultaSalva.getDiaHoraConsulta(),
                consultaSalva.getStatus(),
                consultaSalva.getMotivoConsulta()
        );
        ConsultaDTOKafka requestKafka = new ConsultaDTOKafka(
                consultaSalva.getIdMedico(),
                consultaSalva.getIdPaciente(),
                consultaSalva.getDescricao(),
                consultaSalva.getDiaHoraConsulta(),
                consultaSalva.getMotivoConsulta(),
                paciente.email(),
                paciente.nome(),
                nomeMedico);
        kafkaTemplate.send("consulta-eventos","Consulta criada: "+ String.valueOf(consultaSalva.getIdPaciente()),requestKafka);
        return response;
    }

    public ConsultaResponse editarAgendamento(ConsultaUpdateDTO request, String userRole, Long userId){
        ConsultaModel consultaModel = repository.findById(request.id()).
                orElseThrow(() -> new NotFound("Consulta não encontrada"));

        // Validar permissões baseado na role
        validarPermissaoEdicao(consultaModel, userRole, userId);

        var nomeMedico = buscarMedicoId(request.idMedico());
        DadosPaciente paciente = buscarDadosPaciente(request.idPaciente());

        if (!request.diaHoraConsulta().isAfter(LocalDateTime.now())){
            throw new BadRequest("A data da consulta deve ser no futuro.");
        }

        consultaModel.setIdMedico(request.idMedico());
        consultaModel.setIdPaciente(request.idPaciente());
        consultaModel.setDescricao(request.descricao());
        consultaModel.setDiaHoraConsulta(request.diaHoraConsulta());
        consultaModel.setStatus(request.status());
        consultaModel.setMotivoConsulta(request.motivoConsulta());

        ConsultaModel consultaSalva = repository.save(consultaModel);

        ConsultaResponse response = new ConsultaResponse(
                consultaModel.getIdMedico(),
                consultaModel.getIdPaciente(),
                consultaModel.getDescricao(),
                consultaModel.getDiaHoraConsulta(),
                consultaModel.getStatus(),
                consultaModel.getMotivoConsulta()
        );

        ConsultaUpdateDTOKafka requestKafka = new ConsultaUpdateDTOKafka(
                request.id(),
                consultaSalva.getIdMedico(),
                consultaSalva.getIdPaciente(),
                consultaSalva.getDescricao(),
                consultaSalva.getDiaHoraConsulta(),
                consultaSalva.getStatus(),
                consultaSalva.getMotivoConsulta(),
                paciente.email(),
                paciente.nome,
                nomeMedico);
        kafkaTemplate.send("consulta-eventos","Consulta alterada: " + request.id(),requestKafka);
        return response;
    }

    private void validarPermissaoAcesso(ConsultaModel consulta, String userRole, Long userId) {
        if (userRole == null) {
            throw new BadRequest("Role do usuário não identificada");
        }

        // PACIENTE só pode ver suas próprias consultas
        if ("PACIENTE".equals(userRole) && !consulta.getIdPaciente().equals(userId)) {
            throw new BadRequest("Paciente só pode visualizar suas próprias consultas");
        }

        // MEDICO só pode ver consultas onde é o médico responsável
        if ("MEDICO".equals(userRole) && !consulta.getIdMedico().equals(userId)) {
            throw new BadRequest("Médico só pode visualizar suas próprias consultas");
        }
    }

    private void validarPermissaoEdicao(ConsultaModel consulta, String userRole, Long userId) {
        if (userRole == null) {
            throw new BadRequest("Role do usuário não identificada");
        }

        // PACIENTE não pode editar consultas
        if ("PACIENTE".equals(userRole)) {
            throw new BadRequest("Paciente não pode editar consultas");
        }

        // MEDICO só pode editar suas próprias consultas
        if ("MEDICO".equals(userRole) && !consulta.getIdMedico().equals(userId)) {
            throw new BadRequest("Médico só pode editar suas próprias consultas");
        }
    }

    private String buscarMedicoId(Long idUsuario){
        String sql = "SELECT nome FROM usuarios WHERE id = ? AND tipo_usuario = ?";
        try {
            return jdbc.queryForObject(sql, String.class,idUsuario,"MEDICO");
        }catch (Exception e){
            throw new NotFound("Medico não encontrado");
        }
    }

    private DadosPaciente buscarDadosPaciente(Long idUsuario){
        String sql = "SELECT nome,email FROM usuarios WHERE id = ? AND tipo_usuario = ?";
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> new DadosPaciente(
                    rs.getString("nome"),
                    rs.getString("email")
            ),idUsuario,"PACIENTE");
        }catch (Exception e){
            throw new NotFound("Paciente não encontrado");
        }
    }

    record DadosPaciente(String nome, String email) {}
}
