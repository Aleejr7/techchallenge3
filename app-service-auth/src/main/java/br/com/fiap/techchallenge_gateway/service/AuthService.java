package br.com.fiap.techchallenge_gateway.service;

import br.com.fiap.techchallenge_gateway.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService
{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException
    {
        return repository.findByEmail(email);
    }

    public String encriptarSenha(String senha){
        return new BCryptPasswordEncoder().encode( senha );
    }
}
