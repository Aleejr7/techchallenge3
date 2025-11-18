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
    // NÃO OBRIGATORIA
    // VERIFICAR RETORNO HORÁRIO
    @GetMapping("consulta/{id}")
    public ResponseEntity getConsultaId(@PathVariable Long id){
        var consulta = service.buscarAgendamento(id);
        return ResponseEntity.ok(consulta);
    }
    @PostMapping("consulta")
    public ResponseEntity postConsulta(@RequestBody @Valid ConsultaDTO consultaDTO){
        service.criarAgendamento(consultaDTO);
        return ResponseEntity.ok("Criado com sucesso"); //Retornar RESPONSE
    }
    @PutMapping("consulta")
    public ResponseEntity putConsulta( @RequestBody @Valid ConsultaUpdateDTO consultaUpdateDTO){
        service.editarAgendamento(consultaUpdateDTO);
        return ResponseEntity.ok("Editado com sucesso");
    }
}
