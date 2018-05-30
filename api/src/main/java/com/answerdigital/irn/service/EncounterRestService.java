package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.Encounter;

@Service
public class EncounterRestService extends RestService<Encounter> {
	
	@Value("${encounter.namespace}")
	private String encounterNamespace;
	
	@Autowired
	public EncounterRestService(EncounterMessageService encounterMessageService) {
		super(Encounter.class, encounterMessageService);
	}
	
	@Override
	protected String namespace() {
		return encounterNamespace;
	}

	
}
