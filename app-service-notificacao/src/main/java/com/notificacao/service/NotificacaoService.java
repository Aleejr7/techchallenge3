package com.notificacao.service;

import com.notificacao.DTO.ConsultaDTOKafka;
import com.notificacao.DTO.ConsultaUpdateDTOKafka;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "consulta-eventos", containerFactory = "consultaDTOConcurrentKafkaListenerContainerFactory")
public class NotificacaoService {

    private final EmailService emailService;

    public NotificacaoService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaHandler
    public void notificacaoCriadaListener(ConsultaDTOKafka consulta) {
        emailService.enviarEmailConsultaCriada(
                consulta.emailPaciente(),
                consulta.nomePaciente(),
                consulta.nomeMedico(),
                consulta.motivoConsulta(),
                consulta.diaHoraConsulta()
        );
    }
    @KafkaHandler
    public void notificacaoEditadaListener(ConsultaUpdateDTOKafka consulta) {
        emailService.enviarEmailConsultaAtualizada(
                consulta.emailPaciente(),
                consulta.nomePaciente(),
                consulta.nomeMedico(),
                consulta.motivoConsulta(),
                consulta.diaHoraConsulta(),
                consulta.status()
        );
    }
}