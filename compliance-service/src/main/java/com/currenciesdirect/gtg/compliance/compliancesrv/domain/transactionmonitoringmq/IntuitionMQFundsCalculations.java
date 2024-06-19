package com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntuitionMQFundsCalculations {

	@JsonProperty(value = "turnover")
	private Integer turnover;
}
