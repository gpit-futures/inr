package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.Patient;

@Service
public class PatientRestService extends RestService<Patient> {

	@Value("${patient.namespace}")
	private String patientNamespace;

	
	public PatientRestService() {
		super(Patient.class);
	}

	@Override
	protected String namespace() {
		return patientNamespace;
	}

}
