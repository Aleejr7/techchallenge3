package com.notificacao.service;

import com.notificacao.DTO.ConsultaDTOKafka;
import com.notificacao.DTO.ConsultaUpdateDTOKafka;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "consulta-eventos", containerFactory = "consultaDTOConcurrentKafkaListenerContainerFactory")
public class NotificacaoService {

    @KafkaHandler
    public void notificacaoCriadaListener(ConsultaDTOKafka consulta) {
        System.out.println("Mensagem recebida criação 01: " + consulta.idMedico() +
                " "+ consulta.motivoConsulta()+
                " "+ consulta.diaHoraConsulta()+
                " "+ consulta.idPaciente()+
                " "+ consulta.descricao()+
                " "+ consulta.status()+
                " "+consulta.emailPaciente()+
                " "+consulta.nomeMedico()+
                " "+consulta.nomePaciente());
    }
    @KafkaHandler
    public void notificacaoEditadaListener(ConsultaUpdateDTOKafka consulta) {
        System.out.println("Mensagem recebida edição 02: " + consulta.idMedico() +
                " "+ consulta.motivoConsulta()+
                " "+ consulta.diaHoraConsulta()+
                " "+ consulta.idPaciente()+
                " "+ consulta.descricao()+
                " "+ consulta.status()+
                " "+consulta.emailPaciente()+
                " "+consulta.nomeMedico()+
                " "+consulta.nomePaciente());
    }
}