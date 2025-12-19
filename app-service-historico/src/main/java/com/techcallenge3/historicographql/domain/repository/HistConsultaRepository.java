package com.techcallenge3.historicographql.domain.repository;

import com.techcallenge3.historicographql.domain.entity.HistConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistConsultaRepository extends JpaRepository<HistConsulta, Long> {

    List<HistConsulta> findByIdPaciente(Long idPaciente);
    List<HistConsulta> findByIdPacienteAndDiaHoraConsultaAfter(Long idPaciente, LocalDateTime data);

    List<HistConsulta> findByIdMedico(Long idMedico);
    List<HistConsulta> findByIdMedicoAndDiaHoraConsultaAfter(Long idMedico, LocalDateTime data);
}