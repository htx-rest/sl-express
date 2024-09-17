package com.sl.mq.config;

import com.sl.transport.common.constant.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorMessageConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public TopicExchange errorMessageExchange() {
        return new TopicExchange(Constants.MQ.Exchanges.ERROR, true, false);
    }

    @Bean
    public Queue errorQueue() {
        return new Queue(Constants.MQ.Queues.ERROR_PREFIX + appName, true);
    }

    @Bean
    public Binding errorBinding(Queue errorQueue, TopicExchange errorMessageExchange) {
        return BindingBuilder.bind(errorQueue).to(errorMessageExchange).with(appName);
    }

    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, Constants.MQ.Exchanges.ERROR, appName);
    }
}
