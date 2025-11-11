package br.com.fiap.techchallenge_gateway.domain.entity;

import br.com.fiap.techchallenge_gateway.domain.entity.utils.TipoUsuarioRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuarioRole tipoUsuario;

    public Usuario(String nome, String documento, String email, String senha, TipoUsuarioRole tipoUsuario)
    {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities( )
    {
        switch (this.tipoUsuario) {
            case MEDICO:
                return List.of(() -> "ROLE_MEDICO");
            case ENFERMEIRO:
                return List.of(() -> "ROLE_ENFERMEIRO");
            case PACIENTE:
                return List.of(() -> "ROLE_PACIENTE");
            default:
                return List.of( );
        }
    }

    @Override
    public String getPassword( )
    {
        return this.senha;
    }

    @Override
    public String getUsername( )
    {
        return this.email;
    }

}
