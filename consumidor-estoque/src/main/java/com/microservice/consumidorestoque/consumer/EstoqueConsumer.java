package com.microservice.consumidorestoque.consumer;

import constantes.RabbitmqConstantes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EstoqueDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EstoqueConsumer {

    // essa anotação, faz com que o estoqueConsumer fique ouvindo na fila
    @RabbitListener(queues = RabbitmqConstantes.FILA_ESTOQUE)
    private void consumidor(String mensagem) throws JsonProcessingException, InterruptedException {
        EstoqueDto estoqueDto = new ObjectMapper().readValue(mensagem, EstoqueDto.class);
        System.out.println(estoqueDto.codigoProduto);
        System.out.println(estoqueDto.quantidade);
        System.out.println("------------------------------------");

    }
}
