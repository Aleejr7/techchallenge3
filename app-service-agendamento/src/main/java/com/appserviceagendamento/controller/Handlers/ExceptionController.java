package com.appserviceagendamento.controller.Handlers;

import com.appserviceagendamento.domain.dto.ExceptionDTO;
import com.appserviceagendamento.service.Exceptions.BadRequest;
import com.appserviceagendamento.service.Exceptions.Conflict;
import com.appserviceagendamento.service.Exceptions.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ExceptionDTO> handlerNotFound(NotFound e){
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value()).body(new ExceptionDTO(e.getMessage(),status.value()));
    }
    @ExceptionHandler(Conflict.class)
    public ResponseEntity<ExceptionDTO> handlerConflict(Conflict e){
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status.value()).body(new ExceptionDTO(e.getMessage(),status.value()));
    }
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ExceptionDTO> handlerConflict(BadRequest e){
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status.value()).body(new ExceptionDTO(e.getMessage(),status.value()));
    }

}
