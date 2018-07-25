package com.answerdigital.irn.service;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.answerdigital.irn.dto.Message;
import com.answerdigital.irn.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public abstract class MessageService<DTO extends ResponseDTO> {

	@Value("${application.name}")
	private String applicationName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void publishCreateMessage(DTO dto) throws JsonProcessingException {
		this.rabbitTemplate.convertAndSend(getExchangeKey(), getCreateKey(), createMessage(dto));
	}
	
	public void publishUpdateMessage(DTO dto) {
		this.rabbitTemplate.convertAndSend(getExchangeKey(), getUpdateKey(), createMessage(dto));
	}

	@SuppressWarnings("unchecked")
	private Message createMessage(DTO dto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<String, String> details =  
				(Map<String, String>) ((OAuth2AuthenticationDetails) auth.getDetails()).getDecodedDetails();
		
		String odsId = details.get("odsId");
		
		return new Message(applicationName, odsId, odsId, dto);
	}
	
	protected abstract String getCreateKey();
	protected abstract String getUpdateKey();
	protected abstract String getExchangeKey();
}
