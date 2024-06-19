package com.currenciesdirect.gtg.compliance.core;

import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.ITokenizer;
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
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

/**
 * The Class TitanServiceImpl.
 */
@Component("titanServiceImpl")
public class TitanServiceImpl implements ITitanService {
	
	/** The Constant BASE_TITAN_URL. */
	private static final String BASE_TITAN_URL = "baseTitanUrl";

	/** The Constant AUTHORIZATION. */
	public static final String AUTHORIZATION = "Authorization";

	/** The Constant CONTENT_TYPE. */
	public static final String CONTENT_TYPE = "Content-Type";
	
	/** The Constant RETRY_COUNT. */
	public static final int RETRY_COUNT=3;
	
	public static final String TITAN = "titan";
	

	/** The i tokenizer. */
	@Autowired
	@Qualifier("tokenizer")
	private ITokenizer iTokenizer;
	
	/** The log. */
	private Logger log = LoggerFactory.getLogger(TitanServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.ITitanService#getCustomerAllWalletDetails(com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletRequest)
	 */
	@Override
	public WalletResponse getCustomerAllWalletDetails(WalletRequest walletRequest) throws CompliancePortalException {
		
		WalletResponse walletResponse = null;
		
		try {
			walletResponse = fetchCustomerAllWalletDetails(walletRequest);			
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return walletResponse;
	}

	/**
	 * Fetch customer all wallet details.
	 *
	 * @param walletRequest the wallet request
	 * @return the wallet response
	 * @throws Exception 
	 */
	private WalletResponse fetchCustomerAllWalletDetails(WalletRequest walletRequest) throws Exception {
		WalletResponse response = null;
		walletRequest.setOsrId(UUID.randomUUID().toString().replace("-", ""));
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		String baseUrl = System.getProperty(BASE_TITAN_URL);
		boolean breakFlag = false;
		int count=0;
		String token;
		do {
			if(breakFlag)
				break;
			try {
				token = iTokenizer.getAuthToken(TITAN,false);
				headers.putSingle(AUTHORIZATION, token);

				response = clientPool.sendRequest(
						baseUrl + "/titan-wrapper/services/wallet/getCustomerAllWalletBalance", "POST",
						JsonConverterUtil.convertToJsonWithNull(walletRequest), WalletResponse.class, headers,
						MediaType.APPLICATION_JSON_TYPE);
				breakFlag = true;
			} catch (CompliancePortalException e) {
				if (e.getCompliancePortalErrors()==CompliancePortalErrors.UNAUTHORISED_USER) {
					iTokenizer.setAuthToken(null,TITAN);
					count++;
				} else {
					breakFlag = true;
				}
			}
		}while (count<RETRY_COUNT);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.ITitanService#getCustomerWalletTransactionDetails(com.currenciesdirect.gtg.compliance.core.domain.WalletTransactionRequest)
	 */
	@Override
	public WalletTransactionDetailsResponse getCustomerWalletTransactionDetails(WalletTransactionRequest walletTransactionRequest) throws CompliancePortalException {
		
		WalletTransactionDetailsResponse walletTransactionDetailsResponse = null;
		
		try {
			walletTransactionDetailsResponse = fetchWalletTransactionsDetails(walletTransactionRequest);			
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return walletTransactionDetailsResponse;
	}
	
	/**
	 * Fetch wallet transactions details.
	 *
	 * @param walletTransactionRequest the wallet transaction request
	 * @return the wallet transaction details response
	 * @throws Exception 
	 */
	private WalletTransactionDetailsResponse fetchWalletTransactionsDetails(
			WalletTransactionRequest walletTransactionRequest) throws Exception {
		WalletTransactionDetailsResponse response = null;
		walletTransactionRequest.setOsrId(UUID.randomUUID().toString().replace("-", ""));
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		String baseUrl = System.getProperty(BASE_TITAN_URL);
		boolean breakFlag = false;

		int count = 0;
		String token;
		do {
			if(breakFlag)
				break;
			try {
				token = iTokenizer.getAuthToken(TITAN,false);
				headers.putSingle(AUTHORIZATION, token);
				response = clientPool.sendRequest(
						baseUrl + "/titan-wrapper/services/wallet/getCustomerWalletTransactions", "POST",
						JsonConverterUtil.convertToJsonWithNull(walletTransactionRequest),
						WalletTransactionDetailsResponse.class, headers, MediaType.APPLICATION_JSON_TYPE);
				breakFlag = true;
			} catch (CompliancePortalException e) {
				if (e.getCompliancePortalErrors()==CompliancePortalErrors.UNAUTHORISED_USER) {
					iTokenizer.setAuthToken(null,TITAN);
					count++;
				} else {
					breakFlag = true;
				}
			}
		} while (count < RETRY_COUNT);
		return response;
	}
	
	/**
	 * Log error.
	 *
	 * @param t the t
	 */
	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.ITitanService#getCustomerFXTicketList(com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketPortalRequest)
	 */
	@Override
	public FXTicketResponse getCustomerFXTicketList(FxTicketPortalRequest fxTicketPortalRequest) throws CompliancePortalException {
		
		FXTicketResponse fxTicketResponse = null;
		
		try {
			fxTicketResponse = fetchCustomerFXTicketList(fxTicketPortalRequest);			
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return fxTicketResponse;
	}
	
	/**
	 * Fetch customer FX ticket list.
	 *
	 * @param fxTicketPortalRequest the fx ticket portal request
	 * @return the FX ticket response
	 * @throws Exception 
	 */
	private FXTicketResponse fetchCustomerFXTicketList(FxTicketPortalRequest fxTicketPortalRequest) throws Exception {
		FXTicketResponse response = null;
		fxTicketPortalRequest.setOsrId(UUID.randomUUID().toString().replace("-", ""));
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		boolean breakFlag = false;
		String baseUrl = System.getProperty(BASE_TITAN_URL);
		int count=0;
		String token;
		do {
			if(breakFlag)
				break;
			try {
				token = iTokenizer.getAuthToken(TITAN,false);
				headers.putSingle(AUTHORIZATION, token);
				response = clientPool.sendRequest(baseUrl + "/titan-wrapper/services/fxTicket/getFxTicketList", "POST",
						JsonConverterUtil.convertToJsonWithNull(fxTicketPortalRequest), FXTicketResponse.class, headers,
						MediaType.APPLICATION_JSON_TYPE);
				breakFlag = true;
			} catch (CompliancePortalException e) {
				if (e.getCompliancePortalErrors()==CompliancePortalErrors.UNAUTHORISED_USER) {
					iTokenizer.setAuthToken(null,TITAN);
					count++;
				} else {
					breakFlag = true;
				}
			}
		}while (count<RETRY_COUNT);
		
		return response;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.ITitanService#getCustomerFXTicketDetails(com.currenciesdirect.gtg.compliance.core.domain.fxtickets.FxTicketDetailsRequest)
	 */
	@Override
	public FXTicketDetails getCustomerFXTicketDetails(FxTicketDetailsRequest fxTicketDetailsRequest)
			throws CompliancePortalException {
		
		FXTicketDetails fxTicketDetailResponse = null;
		try {
			fxTicketDetailResponse = fetchCustomerFXTicketDetails(fxTicketDetailsRequest);
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return fxTicketDetailResponse;
	}
	
	/**
	 * Fetch customer FX ticket details.
	 *
	 * @param fxTicketDetailsRequest the fx ticket details request
	 * @return the FX ticket details
	 * @throws Exception 
	 */
	private FXTicketDetails fetchCustomerFXTicketDetails(FxTicketDetailsRequest fxTicketDetailsRequest) throws Exception {
		FXTicketDetails response = null;
		fxTicketDetailsRequest.setOsrId(UUID.randomUUID().toString().replace("-", ""));
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		String baseUrl = System.getProperty(BASE_TITAN_URL);
		boolean breakFlag = false;
		int count=0;
		String token;
		do {
			if(breakFlag)
				break;
			try {
				token = iTokenizer.getAuthToken(TITAN,false);
				headers.putSingle(AUTHORIZATION, token);
				response = clientPool.sendRequest(baseUrl + "/titan-wrapper/services/fxTicket/getFxTicketDetails",
						"POST", JsonConverterUtil.convertToJsonWithNull(fxTicketDetailsRequest), FXTicketDetails.class,
						headers, MediaType.APPLICATION_JSON_TYPE);
				breakFlag = true;
			} catch (CompliancePortalException e) {
				if (e.getCompliancePortalErrors()==CompliancePortalErrors.UNAUTHORISED_USER) {
					iTokenizer.setAuthToken(null,TITAN);
					count++;
				} else {
					breakFlag = true;
				}
			}
		}while (count<RETRY_COUNT);
		return response;
	}

	/**
	 * Gets the card pilot list.
	 *
	 * @param cardPilotRequest the card pilot request
	 * @return the card pilot list
	 * @throws CompliancePortalException the compliance portal exception
	 * 
	 * AT-4625
	 */
	@Override
	public CardPilotResponse getCardPilotList(CardPilotRequest cardPilotRequest) throws CompliancePortalException {
		CardPilotResponse cardPilotResponse = null;
		
		try {
			cardPilotResponse = fetchCardPilotList(cardPilotRequest);			
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
		return cardPilotResponse;
	}
	
	/**
	 * Fetch card pilot list.
	 *
	 * @param cardPilotRequest the card pilot request
	 * @return the card pilot response
	 * @throws Exception the exception
	 * 
	 * AT-4625
	 */
	private CardPilotResponse fetchCardPilotList(CardPilotRequest cardPilotRequest) throws Exception {
		CardPilotResponse response = null;
		//cardPilotRequest.setOsrId(UUID.randomUUID().toString().replace("-", ""));
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		boolean breakFlag = false;
		String baseUrl = System.getProperty(BASE_TITAN_URL);
		int count=0;
		String token;
		do {
			if(breakFlag)
				break;
			try {
				token = iTokenizer.getAuthToken(TITAN,false);
				headers.putSingle(AUTHORIZATION, token);
				response = clientPool.sendRequest(baseUrl + "/titan-wrapper/services/CardPreAlpha/getCardActivityHistory", "POST",
						JsonConverterUtil.convertToJsonWithNull(cardPilotRequest), CardPilotResponse.class, headers,
						MediaType.APPLICATION_JSON_TYPE);
				breakFlag = true;
			} catch (CompliancePortalException e) {
				if (e.getCompliancePortalErrors()==CompliancePortalErrors.UNAUTHORISED_USER) {
					iTokenizer.setAuthToken(null,TITAN);
					count++;
				} else {
					breakFlag = true;
				}
			}
		}while (count<RETRY_COUNT);
		
		return response;
	}
}
