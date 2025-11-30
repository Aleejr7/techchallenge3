package com.notificacao.service;

import com.notificacao.DTO.ConsultaDTO;
import com.notificacao.DTO.ConsultaUpdateDTO;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "consulta-eventos", containerFactory = "consultaDTOConcurrentKafkaListenerContainerFactory")
public class NotificacaoService {

    @KafkaHandler
    public void notificacaoCriadaListener(ConsultaDTO consulta) {
        System.out.println("Mensagem recebida criação 01: " + consulta.idMedico() +
                " "+ consulta.motivoConsulta()+
                " "+ consulta.diaHoraConsulta()+
                " "+ consulta.idPaciente()+
                " "+ consulta.descricao()+
                " "+ consulta.status());
    }
    @KafkaHandler
    public void notificacaoEditadaListener(ConsultaUpdateDTO consulta) {
        System.out.println("Mensagem recebida edição 02: " + consulta.idMedico() +
                " "+ consulta.motivoConsulta()+
                " "+ consulta.diaHoraConsulta()+
                " "+ consulta.idPaciente()+
                " "+ consulta.descricao()+
                " "+ consulta.status());
    }
}