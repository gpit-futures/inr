package com.answerdigital.irn.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig  
{
	@Value("${patient.created.key}")
    private String patientCreatedKey;
	
	@Value("${patient.updated.key}")
    private String patientUpdatedKey;
	
	@Value("${patient.exchange.key}")
    private String patientExchangeKey;

	@Value("${care.plan.created.key}")
    private String carePlanCreatedKey;
	
	@Value("${care.plan.updated.key}")
    private String carePlanUpdatedKey;
	
	@Value("${care.plan.exchange.key}")
    private String carePlanExchangeKey;
	
	@Value("${observation.created.key}")
    private String observationCreatedKey;
	
	@Value("${observation.updated.key}")
    private String observationUpdatedKey;
	
	@Value("${observation.exchange.key}")
    private String observationExchangeKey;
	
	@Value("${encounter.created.key}")
    private String encounterCreatedKey;
	
	@Value("${encounter.updated.key}")
    private String encounterUpdatedKey;
	
	@Value("${encounter.exchange.key}")
    private String encounterExchangeKey;
	
    @Bean
    public List<Declarable> patientBindings() {
        return createBindings("created-patients-queue", "updated-patients-queue", patientExchangeKey, patientCreatedKey, patientUpdatedKey);
    }
    
    @Bean
    public List<Declarable> carePlanBindings() {
    	return createBindings("created-care-plan-queue", "updated-care-plan-queue", carePlanExchangeKey, carePlanCreatedKey, carePlanUpdatedKey);
    }
    
    @Bean
    public List<Declarable> observationBindings() {
    	return createBindings("created-observation-queue", "updated-observation-queue", observationExchangeKey, observationCreatedKey, observationUpdatedKey);
    }
    
    @Bean
    public List<Declarable> encounterBindings() {
    	return createBindings("created-encounter-queue", "updated-encounter-queue", encounterExchangeKey, encounterCreatedKey, encounterUpdatedKey);
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
    
	private List<Declarable> createBindings(String createdQueueName, String updatedQueueName, String exchangeKey, String createdKey, String updatedKey) {
		Queue createdQueue = QueueBuilder.durable(createdQueueName).build();
        Queue updatedQueue = QueueBuilder.durable(updatedQueueName).build();
     
        DirectExchange exchange = new DirectExchange(exchangeKey);
     
        return Arrays.asList(
          createdQueue,
          updatedQueue,
          exchange,
          BindingBuilder
            .bind(createdQueue)
            .to(exchange).with(createdKey),
          BindingBuilder
            .bind(updatedQueue)
            .to(exchange).with(updatedKey));
	}
}
