package com.notificacao.service;

import com.notificacao.DTO.ConsultaDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    @KafkaListener(topicPartitions = @TopicPartition(topic = "consulta-produzida", partitions = { "0" }), containerFactory = "consultaDTOConcurrentKafkaListenerContainerFactory")
    public void notificacaoListener(ConsultaDTO consulta) {
        System.out.println("Mensagem recebida 01: " + consulta.idMedico() +" "+ consulta.motivoConsulta());
    }
}
