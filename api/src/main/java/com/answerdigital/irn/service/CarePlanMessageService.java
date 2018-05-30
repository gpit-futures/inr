package com.answerdigital.irn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.answerdigital.irn.dto.CarePlan;

@Service
public class CarePlanMessageService extends MessageService<CarePlan> {

	@Value("${care.plan.created.key}")
    private String carePlanCreatedKey;
	
	@Value("${care.plan.updated.key}")
    private String carePlanUpdatedKey;
	
	@Value("${care.plan.exchange.key}")
    private String carePlanExchangeKey;
	
	@Override
	protected String getExchangeKey() {
		return carePlanExchangeKey;
	}

	@Override
	protected String getCreateKey() {
		return carePlanCreatedKey;
	}

	@Override
	protected String getUpdateKey() {
		return carePlanUpdatedKey;
	}
	
}
