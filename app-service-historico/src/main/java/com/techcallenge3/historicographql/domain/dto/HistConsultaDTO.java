package com.techcallenge3.historicographql.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techcallenge3.historicographql.domain.entity.HistStatusConsulta;
import java.time.LocalDateTime;

public record HistConsultaDTO(
        Long idMedico,
        Long idPaciente,
        Long idEnfermeiro,
        String descricao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime diaHoraConsulta,
        HistStatusConsulta status,
        String motivoConsulta
) {
}