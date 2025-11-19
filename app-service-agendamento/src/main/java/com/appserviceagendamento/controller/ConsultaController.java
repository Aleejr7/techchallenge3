package com.appserviceagendamento.controller;

import com.appserviceagendamento.domain.dto.ConsultaDTO;
import com.appserviceagendamento.domain.dto.ConsultaUpdateDTO;
import com.appserviceagendamento.service.ConsultaService;
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
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestHeader(value = "X-User-Id", required = false) Long userId){
        var consulta = service.buscarAgendamento(id, userRole, userId);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("consulta")
    public ResponseEntity postConsulta(
            @RequestBody @Valid ConsultaDTO consultaDTO,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestHeader(value = "X-User-Id", required = false) Long userId){
        service.criarAgendamento(consultaDTO, userRole, userId);
        return ResponseEntity.ok("Criado com sucesso");
    }

    @PutMapping("consulta")
    public ResponseEntity putConsulta(
            @RequestBody @Valid ConsultaUpdateDTO consultaUpdateDTO,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestHeader(value = "X-User-Id", required = false) Long userId){
        service.editarAgendamento(consultaUpdateDTO, userRole, userId);
        return ResponseEntity.ok("Editado com sucesso");
    }
}
