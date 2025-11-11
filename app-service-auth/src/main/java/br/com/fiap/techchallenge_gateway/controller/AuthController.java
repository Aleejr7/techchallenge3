package br.com.fiap.techchallenge_gateway.controller;

import br.com.fiap.techchallenge_gateway.domain.dtos.LoginRequestDTO;
import br.com.fiap.techchallenge_gateway.domain.dtos.CriarUsuarioDTO;
import br.com.fiap.techchallenge_gateway.domain.dtos.LoginResponseDTO;
import br.com.fiap.techchallenge_gateway.domain.entity.Usuario;
import br.com.fiap.techchallenge_gateway.infra.security.TokenService;
import br.com.fiap.techchallenge_gateway.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController
{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO loginRequestDTO ){

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken( loginRequestDTO.email(), loginRequestDTO.senha() );
        var auth = this.authenticationManager.authenticate( usernamePassword );

        var token =  tokenService.gerarToken( (Usuario) auth.getPrincipal() );

        return ResponseEntity.ok( new LoginResponseDTO( token ) );
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CriarUsuarioDTO criarUsuarioDTO ){
        usuarioService.salvarUsuario( criarUsuarioDTO );
        return ResponseEntity.ok().build();
    }
}
