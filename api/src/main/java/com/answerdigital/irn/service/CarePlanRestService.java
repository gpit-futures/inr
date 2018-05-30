package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.CarePlan;

@Service
public class CarePlanRestService extends RestService<CarePlan> {

	@Value("${care.plan.namespace}")
	private String carePlanNamespace;
	
	@Autowired
	public CarePlanRestService(CarePlanMessageService carePlanMessageService) {
		super(CarePlan.class, carePlanMessageService);
	}

	@Override
	protected String namespace() {
		return carePlanNamespace;
	}
}
