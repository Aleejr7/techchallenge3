package com.appserviceagendamento.domain.dto;

import com.appserviceagendamento.domain.entity.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ConsultaUpdateDTOKafka(
        @Positive(message = "Insira um valor válido")
        Long id,
        @Positive(message = "Insira um valor válido")
        Long idMedico,
        @Positive(message = "Insira um valor válido")
        Long idPaciente,
        String descricao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime diaHoraConsulta,
        @NotNull(message = "O status não pode ser nulo")
        StatusConsulta status,
        @NotBlank(message = "Insira o omitvo da consulta")
        @NotNull
        String motivoConsulta,
        @NotBlank
        String emailPaciente,
        @NotBlank
        String nomePaciente,
        @NotBlank
        String nomeMedico
) {
}
