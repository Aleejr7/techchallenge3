package br.com.fiap.techchallenge_gateway.service.exceptions;

public class DocumentoJaExisteException extends RuntimeException {
    public DocumentoJaExisteException(String message) {
        super(message);
    }
}
