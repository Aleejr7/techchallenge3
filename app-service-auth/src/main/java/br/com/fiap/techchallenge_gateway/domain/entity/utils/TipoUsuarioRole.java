package br.com.fiap.techchallenge_gateway.domain.entity.utils;

public enum TipoUsuarioRole
{
    MEDICO("medico"),
    ENFERMEIRO("enfermeiro"),
    PACIENTE("paciente");

    private String role;

    TipoUsuarioRole(String role)
    {
        this.role = role;
    }

    public String getRole( )
    {
        return role;
    }
}
