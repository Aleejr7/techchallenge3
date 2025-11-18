package com.appserviceagendamento.service.Exceptions;

public class Conflict extends RuntimeException{
    public Conflict(String mensagem){
        super(mensagem);
    }
}
