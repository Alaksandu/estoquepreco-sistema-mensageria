package com.microservice.estoquepreco.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviaMenssagem(String nomeFila, Object mensagem) {
        try {
            String msgJson = this.objectMapper.writeValueAsString(mensagem);
            // o nomeFila vai ser a routinkey e a msg vai ser o valor dela em Json
            this.rabbitTemplate.convertAndSend(nomeFila, msgJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
