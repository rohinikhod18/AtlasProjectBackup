package com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntuitionPostCardAccountMQRequest implements Serializable{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The consolidated wallet balances. */
	@JsonProperty(value = "Consolidated_Wallet_balances")
	public Double consolidatedWalletBalances;
}
