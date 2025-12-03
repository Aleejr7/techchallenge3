package com.notificacao.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ConsultaDTOKafka(Long idMedico,
                          Long idPaciente,
                          String descricao,
                          @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
                          LocalDateTime diaHoraConsulta,
                          String status,
                          String motivoConsulta,
                          String emailPaciente,
                          String nomePaciente,
                          String nomeMedico) {
}
