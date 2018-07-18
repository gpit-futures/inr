package com.answerdigital.irn.service;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.answerdigital.irn.dto.Message;
import com.answerdigital.irn.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public abstract class MessageService<DTO extends ResponseDTO> {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@SuppressWarnings("unchecked")
	public void publishCreateMessage(DTO dto) throws JsonProcessingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<String, String> details =  
				(Map<String, String>) ((OAuth2AuthenticationDetails) auth.getDetails()).getDecodedDetails();
		
		String odsId = details.get("odsId");
		
		this.rabbitTemplate.convertAndSend(
				getExchangeKey(), getCreateKey(), new Message(odsId, odsId, dto));
	}
	
	public void publishUpdateMessage(DTO dto) {
		this.rabbitTemplate.convertAndSend(getExchangeKey(), getUpdateKey(), dto);
	}
	
	protected abstract String getCreateKey();
	protected abstract String getUpdateKey();
	protected abstract String getExchangeKey();
}
