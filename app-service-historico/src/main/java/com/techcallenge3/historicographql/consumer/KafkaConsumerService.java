package com.techcallenge3.historicographql.consumer;

import com.techcallenge3.historicographql.domain.dto.HistConsultaDTO;
import com.techcallenge3.historicographql.domain.dto.MensagemConsulta;
import com.techcallenge3.historicographql.domain.entity.HistStatusConsulta;
import com.techcallenge3.historicographql.service.HistConsultaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class KafkaConsumerService {

    private final HistConsultaService service;

    public KafkaConsumerService(HistConsultaService service) {
        this.service = service;
    }

    @KafkaListener(topics = "consulta-produzida", groupId = "historico-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumir(MensagemConsulta mensagem) {
        try {
            System.out.println("Recebendo evento do Kafka. ID Agendamento: " + mensagem.id());

            LocalDateTime dataConvertida = LocalDateTime.parse(mensagem.diaHoraConsulta());

            HistConsultaDTO dto = new HistConsultaDTO(
                    mensagem.idMedico(),
                    mensagem.idPaciente(),
                    mensagem.idEnfermeiro(),
                    mensagem.descricao(),
                    dataConvertida,
                    HistStatusConsulta.valueOf(mensagem.status()),
                    mensagem.motivoConsulta()
            );

            service.salvarConsulta(dto);
            System.out.println("Histórico salvo com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem do Kafka: " + e.getMessage());
            e.printStackTrace();
        }
    }
}