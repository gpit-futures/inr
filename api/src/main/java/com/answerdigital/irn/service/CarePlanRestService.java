package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.CarePlan;

@Service
public class CarePlanRestService extends RestService<CarePlan> {

	@Value("${care.plan.namespace}")
	private String carePlanNamespace;
	
	public CarePlanRestService() {
		super(CarePlan.class);
	}

	@Override
	protected String namespace() {
		return carePlanNamespace;
	}
}
