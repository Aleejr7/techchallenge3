package br.com.fiap.techchallenge_gateway.domain.dtos;

import jakarta.validation.constraints.NotNull;

public record CriarUsuarioDTO(
        @NotNull
        String nome,
        @NotNull
        String documento,
        @NotNull
        String email,
        @NotNull
        String senha,
        @NotNull
        String tipoUsuario
)
{
}
