package com.appserviceagendamento.domain.dto;

import com.appserviceagendamento.domain.entity.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ConsultaResponse(
         Long idMedico,
         Long idPaciente,
         String descricao,
         @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
         LocalDateTime diaHoraConsulta,
         StatusConsulta status,
         String motivoConsulta
) {
}
