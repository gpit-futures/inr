package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.Observation;

@Service
public class ObservationMessageService extends MessageService<Observation> {

	@Value("${observation.created.key}")
    private String observationCreatedKey;
	
	@Value("${observation.updated.key}")
    private String observationUpdatedKey;
	
	@Value("${observation.exchange.key}")
    private String observationExchangeKey;
	
	@Override
	protected String getCreateKey() {
		return observationCreatedKey;
	}

	@Override
	protected String getUpdateKey() {
		return observationUpdatedKey;
	}

	@Override
	protected String getExchangeKey() {
		return observationExchangeKey;
	}
}
