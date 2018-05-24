package com.answerdigital.irn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.irn.dto.CarePlan;
import com.answerdigital.irn.service.CarePlanRestService;

@RestController
@RequestMapping("/careplan")
@CrossOrigin
public class CarePlanController extends AbstractController<CarePlan> {
	
	@Autowired
	public CarePlanController(CarePlanRestService carePlanRestService) {
		super(carePlanRestService);
	}
	
}
