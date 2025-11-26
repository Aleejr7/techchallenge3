package com.techcallenge3.historicographql.service;

import com.techcallenge3.historicographql.domain.dto.HistConsultaDTO;
import com.techcallenge3.historicographql.domain.entity.HistConsulta;
import com.techcallenge3.historicographql.domain.repository.HistConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistConsultaService {

    private final HistConsultaRepository repository;

    public HistConsultaService(HistConsultaRepository repository) {
        this.repository = repository;
    }

    public List<HistConsulta> buscarHistorico(Long idPaciente, Boolean apenasFuturas) {
        if (Boolean.TRUE.equals(apenasFuturas)) {
            return repository.findByIdPacienteAndDiaHoraConsultaAfter(idPaciente, LocalDateTime.now());
        }
        return repository.findByIdPaciente(idPaciente);
    }

    @Transactional
    public HistConsulta salvarConsulta(HistConsultaDTO dados) {
        HistConsulta novaConsulta = new HistConsulta(dados);
        return repository.save(novaConsulta);
    }
}