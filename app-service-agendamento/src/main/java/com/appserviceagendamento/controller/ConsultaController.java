package com.appserviceagendamento.controller;

import com.appserviceagendamento.domain.dto.ConsultaCreateDTO;
import com.appserviceagendamento.domain.dto.ConsultaUpdateDTO;
import com.appserviceagendamento.service.ConsultaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping("consulta/{id}")
    public ResponseEntity getConsultaId(
            @PathVariable Long id,
            HttpServletRequest request) {

        String userRole = (String) request.getAttribute("userRole");
        Long userId = (Long) request.getAttribute("userId");

        var consulta = service.buscarAgendamento(id, userRole, userId);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("consulta")
    public ResponseEntity agendarConsulta(
            @RequestBody @Valid ConsultaCreateDTO consultaCreateDTO,
            HttpServletRequest request) {

        String userRole = (String) request.getAttribute("userRole");
        Long userId = (Long) request.getAttribute("userId");

        return ResponseEntity.ok(service.criarAgendamento(consultaCreateDTO, userRole, userId));
    }

    @PutMapping("consulta")
    public ResponseEntity atualizarConsulta(
            @RequestBody @Valid ConsultaUpdateDTO consultaUpdateDTO,
            HttpServletRequest request) {

        String userRole = (String) request.getAttribute("userRole");
        Long userId = (Long) request.getAttribute("userId");

        return ResponseEntity.ok(service.editarAgendamento(consultaUpdateDTO, userRole, userId));
    }
}
