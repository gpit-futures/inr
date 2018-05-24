package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.Observation;

@Service
public class ObservationRestService extends RestService<Observation> {

	@Value("${observation.namespace}")
	private String observationNamespace;
	
	public ObservationRestService() {
		super(Observation.class);
	}
	
	@Override
	protected String namespace() {
		return observationNamespace;
	}
}
