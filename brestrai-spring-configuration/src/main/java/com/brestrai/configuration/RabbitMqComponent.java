package com.brestrai.configuration;

import org.springframework.beans.factory.annotation.Value;

/*
 * Implementar @Component quando tiver a necessidade de utilizar o RabbitMQ
 * */
public class RabbitMqComponent {

    @Value("${app.rabbitmq.queue}")
    private String queue;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    // TODO: Implement RabbitMqComponent

}
