package com.currenciesdirect.gtg.compliance.httpport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.core.ITitanService;
import com.currenciesdirect.gtg.compliance.core.domain.cardpilot.CardPilotRequest;
import com.currenciesdirect.gtg.compliance.core.domain.cardpilot.CardPilotResponse;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FXTicketDetails;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FXTicketResponse;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketDetailsRequest;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketPortalRequest;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.Wallet;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletDetails;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletRequest;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletResponse;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletTransaction;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletTransactionDetailsResponse;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletTransactionRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class TitanController.
 */
@Controller
public class TitanController extends BaseController {

	/** The Constant Two Decimal . */
	private static final String TWO_DECIMAL = "#,##0.00";

	/** The Constant ORGANIZATION_NOT_MIGRATED. */
	public static final String ORGANIZATION_NOT_MIGRATED = "Organization not migrated";

	/** The i titan service. */
	@Autowired
	@Qualifier("titanServiceImpl")
	private ITitanService iTitanService;

	/** The log. */
	private Logger log = LoggerFactory.getLogger(TitanController.class);

	/**
	 * Gets the customer all wallet details.
	 *
	 * @param walletRequest
	 *            the wallet request
	 * @return the customer all wallet details
	 */
	@PostMapping(value = "/getCustomerAllWalletList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public WalletResponse getCustomerAllWalletDetails(@RequestBody WalletRequest walletRequest) {

		String orgCode = walletRequest.getOrgCode();
		Boolean result = checkIsOrganizationAllowed(orgCode);
		WalletResponse walletResponse;

		if (Boolean.TRUE.equals(result)) {
			try {
				walletResponse = iTitanService.getCustomerAllWalletDetails(walletRequest);
				if (null != walletResponse.getWalletList())
					roundTwoDecimalsForWalletDetails(walletResponse.getWalletList());

			} catch (CompliancePortalException e) {
				log.error("Error in getCustomerAllWalletDetails method of Titancontroller class", e);
				walletResponse = new WalletResponse();
				walletResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
				walletResponse.setErrorDescription(e.getCompliancePortalErrors().getErrorDescription());
			}
		} else {
			walletResponse = new WalletResponse();
			walletResponse.setResponseCode("555");
			walletResponse.setResponseDescription(ORGANIZATION_NOT_MIGRATED);
		}
		return walletResponse;
	}

	/**
	 * Gets the customer wallet transaction details.
	 *
	 * @param walletTransactionRequest
	 *            the wallet transaction request
	 * @return the customer wallet transaction details
	 */
	@PostMapping(value = "/getCustomerWalletTransactionDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ModelAndView getCustomerWalletTransactionDetails(
			@RequestBody WalletTransactionRequest walletTransactionRequest) {
		ModelAndView modelAndView = new ModelAndView();
		WalletTransactionDetailsResponse walletTransactionDetailsResponse;
		String orgCode = walletTransactionRequest.getOrgCode();
		Boolean result = checkIsOrganizationAllowed(orgCode);

		if (Boolean.TRUE.equals(result)) {
			try {
				walletTransactionDetailsResponse = iTitanService
						.getCustomerWalletTransactionDetails(walletTransactionRequest);
				if (null != walletTransactionDetailsResponse.getWalletTransactionList()
						&& null != walletTransactionDetailsResponse.getWallet()) {
					roundTwoDecimalsForWalletTransaction(walletTransactionDetailsResponse.getWalletTransactionList());
					roundTwoDecimalsForWallet(walletTransactionDetailsResponse.getWallet());
					formatDate(walletTransactionDetailsResponse.getWalletTransactionList());
				}

				walletTransactionDetailsResponse.setOrgCode(walletTransactionRequest.getOrgCode());
				walletTransactionDetailsResponse.setAccountNumber(walletTransactionRequest.getAccountNumber());
				modelAndView.addObject("walletTransactionDetails", walletTransactionDetailsResponse);
				modelAndView.setViewName(ViewNameEnum.WALLET_DETAILS.getViewName());
			} catch (CompliancePortalException e) {
				log.error("Error in getCustomerWalletTransactionDetails method of Titancontroller class", e);
				walletTransactionDetailsResponse = new WalletTransactionDetailsResponse();
				walletTransactionDetailsResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
				walletTransactionDetailsResponse
						.setErrorDescription(e.getCompliancePortalErrors().getErrorDescription());
			}
		} else {
			walletTransactionDetailsResponse = new WalletTransactionDetailsResponse();
			walletTransactionDetailsResponse.setResponseCode("555");
			walletTransactionDetailsResponse.setResponseDescription(ORGANIZATION_NOT_MIGRATED);
		}
		return modelAndView;
	}

	/**
	 * Gets the customer FX ticket list.
	 *
	 * @param fxTicketPortalRequest
	 *            the fx ticket portal request
	 * @return the customer FX ticket list
	 */
	@PostMapping(value = "/getCustomerFXTicketList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public FXTicketResponse getCustomerFXTicketList(@RequestBody FxTicketPortalRequest fxTicketPortalRequest) {

		FXTicketResponse fxTicketResponse;
		String orgCode = fxTicketPortalRequest.getFxTicketSearchCriteria().getFxTicketFilter().getOrganization().get(0);
		Boolean result = checkIsOrganizationAllowed(orgCode);

		if (Boolean.TRUE.equals(result)) {
			try {
				fxTicketResponse = iTitanService.getCustomerFXTicketList(fxTicketPortalRequest);
			} catch (CompliancePortalException e) {
				log.error("Error in getCustomerFXTicketList method of Titancontroller class", e);
				fxTicketResponse = new FXTicketResponse();
				fxTicketResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
				fxTicketResponse.setErrorDescription(e.getCompliancePortalErrors().getErrorDescription());
			}
		} else {
			fxTicketResponse = new FXTicketResponse();
			fxTicketResponse.setResponseCode("555");
			fxTicketResponse.setResponseDescription(ORGANIZATION_NOT_MIGRATED);
		}
		return fxTicketResponse;

	}

	/**
	 * Gets the customer FX ticke details.
	 *
	 * @param fxTicketDetailsRequest
	 *            the fx ticket details request
	 * @return the customer FX ticke details
	 */
	@PostMapping(value = "/getCustomerFXTicketDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ModelAndView getCustomerFXTickeDetails(@RequestBody FxTicketDetailsRequest fxTicketDetailsRequest) {

		ModelAndView modelAndView = new ModelAndView();
		FXTicketDetails fxTicketDetailsResponse;
		String orgCode = fxTicketDetailsRequest.getFxTicketDetailRequestList().get(0).getFxTicketPayload()
				.getOrganizationCode();
		Boolean result = checkIsOrganizationAllowed(orgCode);

		if (Boolean.TRUE.equals(result)) {
			try {
				fxTicketDetailsResponse = iTitanService.getCustomerFXTicketDetails(fxTicketDetailsRequest);
			} catch (CompliancePortalException e) {
				log.error("Error in getCustomerFXTickeDetails method of Titancontroller class", e);
				fxTicketDetailsResponse = new FXTicketDetails();
				fxTicketDetailsResponse.setResponseCode(e.getCompliancePortalErrors().getErrorCode());
				fxTicketDetailsResponse.setResponseDescription(e.getCompliancePortalErrors().getErrorDescription());
			}
		} else {
			fxTicketDetailsResponse = new FXTicketDetails();
			fxTicketDetailsResponse.setResponseCode("555");
			fxTicketDetailsResponse.setResponseDescription(ORGANIZATION_NOT_MIGRATED);
		}
		modelAndView.setViewName("fxticketdetails");
		modelAndView.addObject("fxTicketDetailWrapper", fxTicketDetailsResponse);
		return modelAndView;
	}

	/**
	 * Check is organization allowed.
	 *
	 * @param orgCode
	 *            the org code
	 * @return the boolean
	 */
	public Boolean checkIsOrganizationAllowed(String orgCode) {
		Boolean result = Boolean.FALSE;
		String titanOrganizations = System.getProperty("titan.supported.organizations");
		if (null != titanOrganizations && titanOrganizations.contains(orgCode)) {
			result =Boolean.TRUE;
			return result;
		}
		return result;
	}

	/**
	 * @param walletDetails
	 */
	public void roundTwoDecimalsForWalletDetails(List<WalletDetails> walletDetails) {
		for (WalletDetails walletDet : walletDetails) {
			DecimalFormat decimalFormat = new DecimalFormat(TWO_DECIMAL);
			walletDet.setWalletAvailableBalance(decimalFormat.format(walletDet.getAvailableBalance()));
			walletDet.setWalletTotalBalance(decimalFormat.format(walletDet.getTotalBalance()));
		}
	}

	/**
	 * @param walletTransaction
	 */
	public void roundTwoDecimalsForWalletTransaction(List<WalletTransaction> walletTransaction) {
		for (WalletTransaction walletTrans : walletTransaction) {
			DecimalFormat decimalFormat = new DecimalFormat(TWO_DECIMAL);
			walletTrans.setWalletAmount(decimalFormat.format(walletTrans.getAmount()));
		}
	}

	/**
	 * @param wallet
	 */
	public void roundTwoDecimalsForWallet(Wallet wallet) {
		DecimalFormat decimalFormat = new DecimalFormat(TWO_DECIMAL);
		wallet.setWalletAvailableBalance(decimalFormat.format(wallet.getAvailableBalance()));
		wallet.setWalletTotalBalance(decimalFormat.format(wallet.getTotalBalance()));
	}

	/**
	 * @param walletTransaction
	 */
	public void formatDate(List<WalletTransaction> walletTransaction) {
		for (WalletTransaction walletTrans : walletTransaction) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			walletTrans.setTransDate(sdf.format(walletTrans.getTransactionDate()));
		}
	}

	/**
	 * Gets the card pilot list.
	 *
	 * @param cardPilotRequest the card pilot request
	 * @return the card pilot list
	 * 
	 * AT-4625
	 */
	@PostMapping(value = "/getCardPilotList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public CardPilotResponse getCardPilotList(@RequestBody CardPilotRequest cardPilotRequest) {

		CardPilotResponse cardPilotResponse;
		
			try {
				cardPilotResponse = iTitanService.getCardPilotList(cardPilotRequest);
			} catch (CompliancePortalException e) {
				log.error("Error in getCustomerFXTicketList method of Titancontroller class", e);
				cardPilotResponse = new CardPilotResponse();
				cardPilotResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
				cardPilotResponse.setErrorDescription(e.getCompliancePortalErrors().getErrorDescription());
			}
		return cardPilotResponse;
	}

}
