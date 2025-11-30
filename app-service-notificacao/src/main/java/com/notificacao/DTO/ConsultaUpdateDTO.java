package com.notificacao.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ConsultaUpdateDTO(
        @Positive(message = "Insira um valor válido")
        @NotNull(message = "id inválido")
        Long id,
        @Positive(message = "Insira um valor válido")
        @NotNull(message = "id do médico inválido")
        Long idMedico,
        @Positive(message = "Insira um valor válido")
        @NotNull(message = "id do paciente é inválido")
        Long idPaciente,
        String descricao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime diaHoraConsulta,
        @NotNull(message = "O status não pode ser nulo")
        String status,
        @NotBlank(message = "Insira o omitvo da consulta")
        @NotNull
        String motivoConsulta
) {
}
