package br.com.fiap.techchallenge_gateway.service.exceptions;

public class TipoUsuarioInvalidoException extends RuntimeException {
    public TipoUsuarioInvalidoException(String message) {
        super(message);
    }
}

