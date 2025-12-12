package com.techcallenge3.historicographql.controller;

import com.techcallenge3.historicographql.domain.entity.HistConsulta;
import com.techcallenge3.historicographql.service.HistConsultaService;
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
    public List<HistConsulta> historicoPaciente(@Argument Long idPaciente, @Argument Boolean apenasFuturas) {
        return service.buscarHistorico(idPaciente, apenasFuturas);
    }

    @QueryMapping
    public List<HistConsulta> historicoMedico(@Argument Long idMedico, @Argument Boolean apenasFuturas) {
        return service.buscarHistoricoMedico(idMedico, apenasFuturas);
    }

    @QueryMapping
    public List<HistConsulta> historicoEnfermeiro(@Argument Long idEnfermeiro, @Argument Boolean apenasFuturas) {
        return service.buscarHistoricoEnfermeiro(idEnfermeiro, apenasFuturas);
    }
}