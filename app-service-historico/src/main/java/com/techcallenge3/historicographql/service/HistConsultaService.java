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

    public List<HistConsulta> buscarHistoricoPaciente(Long idPaciente, Boolean apenasFuturas, String userRole, Long userId) {
        // PACIENTE só pode ver suas próprias consultas
        if ("PACIENTE".equals(userRole) && !idPaciente.equals(userId)) {
            throw new SecurityException("Paciente só pode visualizar suas próprias consultas");
        }

        if (Boolean.TRUE.equals(apenasFuturas)) {
            return repository.findByIdPacienteAndDiaHoraConsultaAfter(idPaciente, LocalDateTime.now());
        }
        return repository.findByIdPaciente(idPaciente);
    }

    public List<HistConsulta> buscarHistoricoMedicoOuEnfermeiro(Long idMedicoOuEnfermeiro, Boolean apenasFuturas, String userRole) {
        // Apenas MEDICO e ENFERMEIRO podem visualizar
        if (!"MEDICO".equals(userRole) && !"ENFERMEIRO".equals(userRole)) {
            throw new SecurityException("Apenas médicos e enfermeiros podem visualizar este histórico");
        }

        if (Boolean.TRUE.equals(apenasFuturas)) {
            return repository.findByIdMedicoAndDiaHoraConsultaAfter(idMedicoOuEnfermeiro, LocalDateTime.now());
        }
        return repository.findByIdMedico(idMedicoOuEnfermeiro);
    }

    @Transactional
    public HistConsulta salvarConsulta(HistConsultaDTO dados) {
        HistConsulta novaConsulta = new HistConsulta(dados);
        return repository.save(novaConsulta);
    }
}

