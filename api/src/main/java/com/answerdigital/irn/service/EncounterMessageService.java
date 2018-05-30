package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.Encounter;

@Service
public class EncounterMessageService extends MessageService<Encounter> {

	@Value("${encounter.created.key}")
    private String encounterCreatedKey;
	
	@Value("${encounter.updated.key}")
    private String encounterUpdatedKey;
	
	@Value("${encounter.exchange.key}")
    private String encounterExchangeKey;
	
	@Override
	protected String getCreateKey() {
		return encounterCreatedKey;
	}

	@Override
	protected String getUpdateKey() {
		return encounterUpdatedKey;
	}

	@Override
	protected String getExchangeKey() {
		return encounterExchangeKey;
	}
}
