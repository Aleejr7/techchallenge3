package br.com.fiap.techchallenge_gateway.controller.handler;

import br.com.fiap.techchallenge_gateway.domain.dtos.ExceptionDTO;
import br.com.fiap.techchallenge_gateway.service.exceptions.CredenciaisInvalidasException;
import br.com.fiap.techchallenge_gateway.service.exceptions.DocumentoJaExisteException;
import br.com.fiap.techchallenge_gateway.service.exceptions.EmailJaExisteException;
import br.com.fiap.techchallenge_gateway.service.exceptions.TipoUsuarioInvalidoException;
import br.com.fiap.techchallenge_gateway.service.exceptions.TokenInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EmailJaExisteException.class)
    public ResponseEntity<ExceptionDTO> handleEmailJaExiste(EmailJaExisteException ex) {
        ExceptionDTO erro = new ExceptionDTO(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(DocumentoJaExisteException.class)
    public ResponseEntity<ExceptionDTO> handleDocumentoJaExiste(DocumentoJaExisteException ex) {
        ExceptionDTO erro = new ExceptionDTO(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler({CredenciaisInvalidasException.class, BadCredentialsException.class})
    public ResponseEntity<ExceptionDTO> handleCredenciaisInvalidas(Exception ex) {
        ExceptionDTO erro = new ExceptionDTO("Credenciais inválidas", HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ExceptionDTO> handleTokenInvalido(TokenInvalidoException ex) {
        ExceptionDTO erro = new ExceptionDTO(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(TipoUsuarioInvalidoException.class)
    public ResponseEntity<ExceptionDTO> handleTipoUsuarioInvalido(TipoUsuarioInvalidoException ex) {
        ExceptionDTO erro = new ExceptionDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        String mensagem = "Dados inválidos: " + ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + " - " + error.getDefaultMessage())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Erro de validação");
        ExceptionDTO erro = new ExceptionDTO(mensagem, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
