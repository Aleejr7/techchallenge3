package br.com.fiap.techchallenge_gateway.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarUsuarioDTO(
        @NotNull(message = "Nome é obrigatório")
        String nome,
        @NotNull(message = "Documento é obrigatório")
        String documento,
        @NotNull(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        String email,
        @NotNull(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha,
        @NotNull(message = "Tipo de usuário é obrigatório")
        String tipoUsuario
)
{
}
