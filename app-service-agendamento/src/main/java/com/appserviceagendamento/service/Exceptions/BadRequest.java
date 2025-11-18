package com.appserviceagendamento.service.Exceptions;

public class BadRequest extends RuntimeException{
    public BadRequest(String mensagem){
        super(mensagem);
    }
}
