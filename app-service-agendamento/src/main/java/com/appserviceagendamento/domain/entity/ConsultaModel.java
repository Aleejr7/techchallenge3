package com.appserviceagendamento.domain.entity;

import com.appserviceagendamento.domain.dto.ConsultaDTO;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "consulta")
public class ConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "id_paciente")
    private Long idPaciente;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "dia_hora_consulta")
    private LocalDateTime diaHoraConsulta;

    @Column(name = "status")
    private StatusConsulta status;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    public ConsultaModel() {
    }

    public ConsultaModel(ConsultaDTO consultaDTO) {
        this.idMedico = consultaDTO.idMedico();
        this.idPaciente = consultaDTO.idPaciente();
        this.descricao = consultaDTO.descricao();
        this.diaHoraConsulta = consultaDTO.diaHoraConsulta();
        this.motivoConsulta = consultaDTO.motivoConsulta();
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDiaHoraConsulta() {
        return diaHoraConsulta;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDiaHoraConsulta(LocalDateTime diaHoraConsulta) {
        this.diaHoraConsulta = diaHoraConsulta;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }
}
