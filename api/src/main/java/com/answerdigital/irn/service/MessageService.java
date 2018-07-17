package com.answerdigital.irn.service;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.answerdigital.irn.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class MessageService<DTO extends ResponseDTO> {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public void publishCreateMessage(DTO dto) throws JsonProcessingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
		Map<String, String> decodedDetails =  (Map<String, String>) details.getDecodedDetails();
		String odsId = decodedDetails.get("odsId");
		
		MessageProperties properties = new MessageProperties();
		properties.setHeader("originOds", odsId);
		
		Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(dto)).andProperties(properties).build();
		
		this.rabbitTemplate.convertAndSend(getExchangeKey(), getCreateKey(), message);
	}
	
	public void publishUpdateMessage(DTO dto) {
		this.rabbitTemplate.convertAndSend(getExchangeKey(), getUpdateKey(), dto);
	}
	
	// test
	public Map<String, Object> getExtraInfo(OAuth2Authentication auth) {
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
		return accessToken.getAdditionalInformation();
	}
	
	protected abstract String getCreateKey();
	protected abstract String getUpdateKey();
	protected abstract String getExchangeKey();
}
