package com.answerdigital.irn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.irn.dto.Observation;
import com.answerdigital.irn.service.ObservationRestService;

@RestController
@RequestMapping("/observation")
public class ObservationController extends AbstractController<Observation> {

	public ObservationController(ObservationRestService observationRestService) {
		super(observationRestService);
	}
	
}
