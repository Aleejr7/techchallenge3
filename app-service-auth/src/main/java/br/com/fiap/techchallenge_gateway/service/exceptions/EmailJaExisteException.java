package br.com.fiap.techchallenge_gateway.service.exceptions;

public class EmailJaExisteException extends RuntimeException {
    public EmailJaExisteException(String message) {
        super(message);
    }
}
