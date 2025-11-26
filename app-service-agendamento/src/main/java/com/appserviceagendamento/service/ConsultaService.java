package com.appserviceagendamento.service;

import com.appserviceagendamento.domain.dto.ConsultaDTO;
import com.appserviceagendamento.domain.dto.ConsultaResponse;
import com.appserviceagendamento.domain.dto.ConsultaUpdateDTO;
import com.appserviceagendamento.domain.entity.ConsultaModel;
import com.appserviceagendamento.domain.repository.ConsultaRepository;
import com.appserviceagendamento.service.Exceptions.BadRequest;
import com.appserviceagendamento.service.Exceptions.Conflict;
import com.appserviceagendamento.service.Exceptions.NotFound;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultaService {
    private final ConsultaRepository repository;
    private final KafkaTemplate<String,ConsultaDTO> kafkaTemplate;

    public ConsultaService(ConsultaRepository repository, KafkaTemplate<String, ConsultaDTO> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
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

    public ConsultaModel criarAgendamento(ConsultaDTO request, String userRole, Long userId){
        if (!"MEDICO".equals(userRole) && !"ENFERMEIRO".equals(userRole)) {
            throw new BadRequest("Apenas médicos e enfermeiros podem criar consultas");
        }

        if (!request.diaHoraConsulta().isAfter(LocalDateTime.now())){
            throw new BadRequest("A data da consulta deve ser no futuro.");
        }

        ConsultaModel consultaModel = new ConsultaModel(request);
        kafkaTemplate.send("consulta-produzida",request);
        return repository.save(consultaModel);
    }

    public ConsultaModel editarAgendamento(ConsultaUpdateDTO request, String userRole, Long userId){
        ConsultaModel consultaModel = repository.findById(request.id()).
                orElseThrow(() -> new NotFound("Consulta não encontrada"));

        // Validar permissões baseado na role
        validarPermissaoEdicao(consultaModel, userRole, userId);

        if (!request.diaHoraConsulta().isAfter(LocalDateTime.now())){
            throw new BadRequest("A data da consulta deve ser no futuro.");
        }

        consultaModel.setIdMedico(request.idMedico());
        consultaModel.setIdPaciente(request.idPaciente());
        consultaModel.setDescricao(request.descricao());
        consultaModel.setDiaHoraConsulta(request.diaHoraConsulta());
        consultaModel.setStatus(request.status());
        consultaModel.setMotivoConsulta(request.motivoConsulta());
        return repository.save(consultaModel);
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
}
