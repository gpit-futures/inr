package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.Observation;

@Service
public class ObservationRestService extends RestService<Observation> {

	@Value("${observation.namespace}")
	private String observationNamespace;
	
	@Autowired
	public ObservationRestService(ObservationMessageService observationMessageService) {
		super(Observation.class, observationMessageService);
	}
	
	@Override
	protected String namespace() {
		return observationNamespace;
	}
}
