package br.com.fiap.techchallenge_gateway.service;

import br.com.fiap.techchallenge_gateway.domain.dtos.CriarUsuarioDTO;
import br.com.fiap.techchallenge_gateway.domain.entity.Usuario;
import br.com.fiap.techchallenge_gateway.domain.entity.utils.TipoUsuarioRole;
import br.com.fiap.techchallenge_gateway.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService
{
    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioRepository repository;


    public Usuario salvarUsuario( CriarUsuarioDTO criarUsuarioDTO ){

        boolean usuarioExistente = existsByEmail( criarUsuarioDTO.email() );
        if( usuarioExistente ){
            throw new IllegalArgumentException("Já existe um usuário com esse email.");
        }

        String senhaEncriptada = authService.encriptarSenha( criarUsuarioDTO.senha() );
        Usuario usuario = new Usuario(
            criarUsuarioDTO.nome(),
            criarUsuarioDTO.documento( ),
            criarUsuarioDTO.email(),
            senhaEncriptada,
            mapearTipoUsuario( criarUsuarioDTO.tipoUsuario() )
        );

        return this.repository.save( usuario );
    }

    private boolean existsByEmail(String email)
    {
        UserDetails user =  repository.findByEmail(email);
        return user != null;
    }

    private TipoUsuarioRole mapearTipoUsuario(String tipoUsuarioStr){
        switch (tipoUsuarioStr.toUpperCase()) {
            case "MEDICO":
                return TipoUsuarioRole.MEDICO;
            case "ENFERMEIRO":
                return TipoUsuarioRole.ENFERMEIRO;
            case "PACIENTE":
                return TipoUsuarioRole.PACIENTE;
            default:
                throw new IllegalArgumentException("Tipo de usuário inválido: " + tipoUsuarioStr);
        }
    }
}
