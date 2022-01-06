package com.microservice.estoquepreco.conections;

import constantes.RabbitmqConstantes;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConection {

    private AmqpAdmin amqpAdmin;

    public RabbitMQConection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta() {
        return new DirectExchange(RabbitmqConstantes.NOME_EXCHANGE);
    }

    private Binding relacionamentoFilaExchange(Queue fila,  DirectExchange troca) {
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    // Esse Postconstruct, define pra acionar o método adciona, quando a classe for construida.
    @PostConstruct
    private void adiciona() {
       Queue filaEstoque = this.fila(RabbitmqConstantes.FILA_ESTOQUE);
       Queue filaPreco   = this.fila(RabbitmqConstantes.FILA_PRECO);

       DirectExchange troca = this.trocaDireta();

       Binding ligacaoEstoque = this.relacionamentoFilaExchange(filaEstoque, troca);
       Binding ligacaoPreco   = this.relacionamentoFilaExchange(filaPreco, troca);

       // Criação das filas, da troca e das ligações
       this.amqpAdmin.declareQueue(filaEstoque);
       this.amqpAdmin.declareQueue(filaPreco);

       this.amqpAdmin.declareExchange(troca);

       this.amqpAdmin.declareBinding(ligacaoEstoque);
       this.amqpAdmin.declareBinding(ligacaoPreco);

    }

}
