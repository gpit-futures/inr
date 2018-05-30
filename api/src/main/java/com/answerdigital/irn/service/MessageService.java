package com.answerdigital.irn.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.answerdigital.irn.dto.ResponseDTO;

public abstract class MessageService<DTO extends ResponseDTO> {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void publishCreateMessage(DTO dto) {
		this.rabbitTemplate.convertAndSend(getExchangeKey(), getCreateKey(), dto);
	}
	
	public void publishUpdateMessage(DTO dto) {
		this.rabbitTemplate.convertAndSend(getExchangeKey(), getUpdateKey(), dto);
	}
	
	protected abstract String getCreateKey();
	protected abstract String getUpdateKey();
	protected abstract String getExchangeKey();
}
