package com.appserviceagendamento.service.Exceptions;

public class NotFound extends RuntimeException{
    public NotFound(String mensagem){
        super(mensagem);
    }
}
