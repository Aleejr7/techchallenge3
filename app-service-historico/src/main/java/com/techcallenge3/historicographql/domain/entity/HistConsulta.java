package com.techcallenge3.historicographql.domain.entity;

import com.techcallenge3.historicographql.domain.dto.HistConsultaDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consulta")
public class HistConsulta {

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
    private HistStatusConsulta status;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    public HistConsulta() {
    }

    public HistConsulta(HistConsultaDTO histConsultaDTO) {
        this.idMedico = histConsultaDTO.idMedico();
        this.idPaciente = histConsultaDTO.idPaciente();
        this.descricao = histConsultaDTO.descricao();
        this.diaHoraConsulta = histConsultaDTO.diaHoraConsulta();
        this.motivoConsulta = histConsultaDTO.motivoConsulta();
        this.status = histConsultaDTO.status();
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

    public HistStatusConsulta getStatus() {
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

    public void setStatus(HistStatusConsulta status) {
        this.status = status;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }
}
