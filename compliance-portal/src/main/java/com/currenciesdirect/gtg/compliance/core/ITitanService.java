package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.cardpilot.CardPilotRequest;
import com.currenciesdirect.gtg.compliance.core.domain.cardpilot.CardPilotResponse;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FXTicketDetails;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FXTicketResponse;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketDetailsRequest;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketPortalRequest;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletRequest;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletResponse;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletTransactionDetailsResponse;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletTransactionRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface ITitanService.
 */
public interface ITitanService {

	/**
	 * Gets the customer all wallet details.
	 *
	 * @param walletRequest the wallet request
	 * @return the customer all wallet details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public WalletResponse getCustomerAllWalletDetails(WalletRequest walletRequest) throws CompliancePortalException;
	
	/**
	 * Gets the customer wallet transaction details.
	 *
	 * @param walletTransactionRequest the wallet transaction request
	 * @return the customer wallet transaction details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public WalletTransactionDetailsResponse getCustomerWalletTransactionDetails(
			WalletTransactionRequest walletTransactionRequest) throws CompliancePortalException;
	
	/**
	 * Gets the customer FX ticket list.
	 *
	 * @param fxTicketPortalRequest the fx ticket portal request
	 * @return the customer FX ticket list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public FXTicketResponse getCustomerFXTicketList(FxTicketPortalRequest fxTicketPortalRequest)
			throws CompliancePortalException;
	
	/**
	 * Gets the customer FX ticket Details.
	 *
	 * @param fxTicketDetailsRequest the fx ticket details request
	 * @return the customer FX ticket list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public FXTicketDetails getCustomerFXTicketDetails(FxTicketDetailsRequest fxTicketDetailsRequest)
			throws CompliancePortalException;
	
	/**
	 * Gets the card pilot list.
	 *
	 * @param cardPilotRequest the card pilot request
	 * @return the card pilot list
	 * @throws CompliancePortalException the compliance portal exception
	 * AT-4625
	 */
	public CardPilotResponse getCardPilotList(CardPilotRequest cardPilotRequest)
			throws CompliancePortalException;
}
