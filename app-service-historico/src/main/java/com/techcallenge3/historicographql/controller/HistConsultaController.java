package com.techcallenge3.historicographql.controller;

import com.techcallenge3.historicographql.domain.entity.HistConsulta;
import com.techcallenge3.historicographql.service.HistConsultaService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HistConsultaController {

    private final HistConsultaService service;

    public HistConsultaController(HistConsultaService service) {
        this.service = service;
    }

    @QueryMapping
    public List<HistConsulta> historicoPaciente(
            @Argument Long idPaciente,
            @Argument Boolean apenasFuturas,
            DataFetchingEnvironment env) {

        String userRole = env.getGraphQlContext().get("userRole");
        Long userId = env.getGraphQlContext().get("userId");

        return service.buscarHistoricoPaciente(idPaciente, apenasFuturas, userRole, userId);
    }

    @QueryMapping
    public List<HistConsulta> historicoMedicoOuEnfermeiro(
            @Argument Long idMedicoOuEnfermeiro,
            @Argument Boolean apenasFuturas,
            DataFetchingEnvironment env) {

        String userRole = env.getGraphQlContext().get("userRole");

        return service.buscarHistoricoMedicoOuEnfermeiro(idMedicoOuEnfermeiro, apenasFuturas, userRole);
    }
}

