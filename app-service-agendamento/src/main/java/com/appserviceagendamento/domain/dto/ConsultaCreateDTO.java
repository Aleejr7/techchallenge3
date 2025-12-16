package com.appserviceagendamento.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ConsultaCreateDTO(
         @NotNull(message = "O ID do médico é obrigatório")
         @Positive(message = "Insira um valor válido")
         Long idMedico,
         @NotNull(message = "O ID do paciente é obrigatório")
         @Positive(message = "Insira um valor válido")
         Long idPaciente,
         String descricao,
         @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
         LocalDateTime diaHoraConsulta,
         @NotBlank(message = "Insira o motivo da consulta")
         String motivoConsulta
) {
}
