package br.com.fiap.techchallenge_gateway.repository;

import br.com.fiap.techchallenge_gateway.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{
    UserDetails findByEmail(String email);
}
