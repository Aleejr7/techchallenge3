package com.notificacao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;
    private final String emailFrom;

    public EmailService(JavaMailSender mailSender, @Value("${email.from}") String emailFrom) {
        this.mailSender = mailSender;
        this.emailFrom = emailFrom;
    }

    public void enviarEmailConsultaCriada(String emailPaciente, String nomePaciente, String nomeMedico,
                                          String motivoConsulta, LocalDateTime diaHoraConsulta) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailPaciente);
            message.setSubject("Consulta Agendada - TechChallenge");
            message.setText(construirMensagemConsultaCriada(nomePaciente, nomeMedico, motivoConsulta, diaHoraConsulta));

            mailSender.send(message);
            logger.info("Email de consulta criada enviado com sucesso para: {}", emailPaciente);
        } catch (Exception e) {
            logger.error("Erro ao enviar email de consulta criada para: {}. Erro: {}", emailPaciente, e.getMessage(), e);
        }
    }

    public void enviarEmailConsultaAtualizada(String emailPaciente, String nomePaciente, String nomeMedico,
                                              String motivoConsulta, LocalDateTime diaHoraConsulta, String status) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailPaciente);
            message.setSubject("Consulta Atualizada - TechChallenge");
            message.setText(construirMensagemConsultaAtualizada(nomePaciente, nomeMedico, motivoConsulta, diaHoraConsulta, status));

            mailSender.send(message);
            logger.info("Email de consulta atualizada enviado com sucesso para: {}", emailPaciente);
        } catch (Exception e) {
            logger.error("Erro ao enviar email de consulta atualizada para: {}. Erro: {}", emailPaciente, e.getMessage(), e);
        }
    }

    private String construirMensagemConsultaCriada(String nomePaciente, String nomeMedico,
                                                   String motivoConsulta, LocalDateTime diaHoraConsulta) {
        return "Ola " + nomePaciente + ",\n\n" +
                "Sua consulta foi agendada com sucesso!\n\n" +
                "Detalhes da consulta:\n" +
                "- Medico: " + nomeMedico + "\n" +
                "- Motivo: " + motivoConsulta + "\n" +
                "- Data/Hora: " + diaHoraConsulta.format(DATE_FORMATTER) + "\n\n" +
                "Por favor, compareca com 15 minutos de antecedencia.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Tech Challenge";
    }

    private String construirMensagemConsultaAtualizada(String nomePaciente, String nomeMedico,
                                                       String motivoConsulta, LocalDateTime diaHoraConsulta, String status) {
        return "Ola " + nomePaciente + ",\n\n" +
                "Sua consulta foi atualizada!\n\n" +
                "Detalhes da consulta:\n" +
                "- Medico: " + nomeMedico + "\n" +
                "- Motivo: " + motivoConsulta + "\n" +
                "- Data/Hora: " + diaHoraConsulta.format(DATE_FORMATTER) + "\n" +
                "- Status: " + status + "\n\n" +
                "Em caso de duvidas, entre em contato conosco.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Tech Challenge";
    }
}

