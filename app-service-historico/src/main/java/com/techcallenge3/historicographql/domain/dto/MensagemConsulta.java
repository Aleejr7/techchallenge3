package com.techcallenge3.historicographql.domain.dto;

public record MensagemConsulta(
        Long id,
        Long idMedico,
        Long idPaciente,
        String descricao,
        String diaHoraConsulta,
        String status,
        String motivoConsulta
) {}

