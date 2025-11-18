package com.appserviceagendamento.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ConsultaDTO(
         @Positive(message = "Insira um valor válido")
         Long idMedico,
         @Positive(message = "Insira um valor válido")
         Long idPaciente,
         String descricao,
         @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
         LocalDateTime diaHoraConsulta,
         @NotBlank(message = "Insira o motivo da consulta")
         String motivoConsulta
) {
}
