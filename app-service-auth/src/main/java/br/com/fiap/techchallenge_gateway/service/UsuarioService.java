package br.com.fiap.techchallenge_gateway.service;

import br.com.fiap.techchallenge_gateway.domain.dtos.CriarUsuarioDTO;
import br.com.fiap.techchallenge_gateway.domain.entity.Usuario;
import br.com.fiap.techchallenge_gateway.domain.entity.utils.TipoUsuarioRole;
import br.com.fiap.techchallenge_gateway.repository.UsuarioRepository;
import br.com.fiap.techchallenge_gateway.service.exceptions.DocumentoJaExisteException;
import br.com.fiap.techchallenge_gateway.service.exceptions.EmailJaExisteException;
import br.com.fiap.techchallenge_gateway.service.exceptions.TipoUsuarioInvalidoException;
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

        boolean emailExistente = existsByEmail( criarUsuarioDTO.email() );
        if( emailExistente ){
            throw new EmailJaExisteException("Email já cadastrado no sistema");
        }

        boolean documentoExistente = existsByDocumento( criarUsuarioDTO.documento() );
        if( documentoExistente ){
            throw new DocumentoJaExisteException("Documento (CPF/CNPJ) já cadastrado no sistema");
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

    private boolean existsByDocumento(String documento)
    {
        Usuario usuario = repository.findByDocumento(documento);
        return usuario != null;
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
                throw new TipoUsuarioInvalidoException("Tipo de usuário inválido: " + tipoUsuarioStr + ". Valores aceitos: MEDICO, ENFERMEIRO, PACIENTE");
        }
    }
}
