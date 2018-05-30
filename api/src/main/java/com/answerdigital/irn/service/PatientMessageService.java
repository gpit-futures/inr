package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.Patient;

@Service
public class PatientMessageService extends MessageService<Patient> {

	@Value("${patient.created.key}")
    private String patientCreatedKey;
	
	@Value("${patient.updated.key}")
    private String patientUpdatedKey;
	
	@Value("${patient.exchange.key}")
    private String patientExchangeKey;
	
	@Override
	protected String getCreateKey() {
		return patientCreatedKey;
	}

	@Override
	protected String getUpdateKey() {
		return patientUpdatedKey;
	}

	@Override
	protected String getExchangeKey() {
		return patientExchangeKey;
	}
	
}
