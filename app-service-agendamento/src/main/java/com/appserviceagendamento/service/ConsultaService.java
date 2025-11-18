package com.appserviceagendamento.service;

import com.appserviceagendamento.domain.dto.ConsultaDTO;
import com.appserviceagendamento.domain.dto.ConsultaResponse;
import com.appserviceagendamento.domain.dto.ConsultaUpdateDTO;
import com.appserviceagendamento.domain.entity.ConsultaModel;
import com.appserviceagendamento.domain.repository.ConsultaRepository;
import com.appserviceagendamento.service.Exceptions.BadRequest;
import com.appserviceagendamento.service.Exceptions.Conflict;
import com.appserviceagendamento.service.Exceptions.NotFound;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultaService {
    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }
    public ConsultaResponse buscarAgendamento(Long id){
        //ADICIONAR EXCEPTION PERSONALIZADA
        ConsultaModel agendamento = repository.findById(id).orElseThrow(() -> new NotFound("Consulta não encontrada"));
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

    public ConsultaModel criarAgendamento(ConsultaDTO request){

        //repository.findbyIdPaciente
        //repository.findbyIdMedico

        if (!request.diaHoraConsulta().isAfter(LocalDateTime.now())){ //ADICIONAR 1 DIA
            throw new BadRequest("A data da consulta deve ser no futuro.");
        }
        if (request.idMedico().equals(request.idPaciente())){
            throw new Conflict("O paciente não pode se atender");
        }
        ConsultaModel consultaModel = new ConsultaModel(request);
        return repository.save(consultaModel);
    }
    public ConsultaModel editarAgendamento(ConsultaUpdateDTO request){
        ConsultaModel consultaModel = repository.findById(request.id()).
                orElseThrow(() -> new NotFound("Consulta não encontrada"));

        //repository.findbyIdPaciente
        //repository.findbyIdMedico
        if (!request.diaHoraConsulta().isAfter(LocalDateTime.now())){ //ADICIONAR 1 DIA
            throw new BadRequest("A data da consulta deve ser no futuro.");
        }
        if (request.idMedico().equals(request.idPaciente())){
            throw new Conflict("O paciente não pode se atender");
        }
//        if (request.status().equals(StatusConsulta.values())){
//            throw new RuntimeException("Selecione um status válido");
//        }
        consultaModel.setIdMedico(request.idMedico());
        consultaModel.setIdPaciente(request.idPaciente());
        consultaModel.setDescricao(request.descricao());
        consultaModel.setDiaHoraConsulta(request.diaHoraConsulta());
        consultaModel.setStatus(request.status());
        consultaModel.setMotivoConsulta(request.motivoConsulta());
        return repository.save(consultaModel);
    }
}
