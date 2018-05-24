package com.answerdigital.irn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.irn.dto.Patient;
import com.answerdigital.irn.service.PatientRestService;

@RestController
@RequestMapping("/patient")
@CrossOrigin
public class PatientController extends AbstractController<Patient> {
	
	@Autowired
	public PatientController(PatientRestService patientRestService) {
		super(patientRestService);
	}

}
