package com.appserviceagendamento.domain.dto;

import com.appserviceagendamento.domain.entity.StatusConsulta;
import java.time.LocalDateTime;

public record ConsultaResponse(
         Long idMedico,
         Long idPaciente,
         String descricao,
         LocalDateTime diaHoraConsulta,
         StatusConsulta status,
         String motivoConsulta
) {
}
