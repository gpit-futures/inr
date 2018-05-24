package com.answerdigital.irn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.irn.dto.Encounter;
import com.answerdigital.irn.service.EncounterRestService;

@RestController
@RequestMapping("/encounter")
public class EncounterController extends AbstractController<Encounter> {

	@Autowired
	public EncounterController(EncounterRestService encounterRestService) {
		super(encounterRestService);
	}
	
}
