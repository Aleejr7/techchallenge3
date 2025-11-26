package com.notificacao.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ConsultaDTO(Long idMedico,
                          Long idPaciente,
                          String descricao,
                          @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
                          LocalDateTime diaHoraConsulta,
                          String status,
                          String motivoConsulta) {
}
