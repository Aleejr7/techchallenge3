package com.appserviceagendamento.domain.dto;

import java.util.List;

public record ListExceptionsDTO(List<String>errors, int status) {
}
