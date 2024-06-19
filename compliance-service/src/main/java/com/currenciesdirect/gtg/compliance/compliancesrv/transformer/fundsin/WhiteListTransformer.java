package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.VelocityCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.ApprovedCurrencyAmountRangePair;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.HighRiskCountry;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.UdateWhiteListRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class WhiteListTransformer.
 */
public class WhiteListTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(WhiteListTransformer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformRequest(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		UdateWhiteListRequest request = (UdateWhiteListRequest) messageExchange.getRequest();

		try {
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) request
					.getAdditionalAttribute(Constants.OLD_REQUEST);
			AccountWhiteList whiteList = new AccountWhiteList();
			whiteList.setOrgCode(fundsInRequest.getOrgCode());
			whiteList.setAccountId(fundsInRequest.getAccId());
			ApprovedCurrencyAmountRangePair sellCurrencyAmount = new ApprovedCurrencyAmountRangePair();
			sellCurrencyAmount.setCode(fundsInRequest.getTrade().getTransactionCurrency());
			sellCurrencyAmount.setTxnAmountUpperLimit(fundsInRequest.getTrade().getSellingAmount());
			whiteList.getApprovedSellCurrencyAmountRangeList().add(sellCurrencyAmount);
			whiteList.getApprovedReasonOfTransList().add(fundsInRequest.getTrade().getPurposeOfTrade());
			if (null != fundsInRequest.getTrade() && fundsInRequest.getTrade().getThirdPartyPayment()) {
				whiteList.getApprovedThirdpartyAccountList().add(fundsInRequest.getTrade().getDebtorAccountNumber());
			}
			if (request.isDocumentationRequiredWatchlist()) {
				whiteList.getDocumentationRequiredWatchlistSellCurrency()
						.add(fundsInRequest.getTrade().getTransactionCurrency());
			}
			if (null != request.getCountryRiskLevel() && !request.getCountryRiskLevel().isEmpty()
					&& (request.getCountryRiskLevel().indexOf("High") > -1
							|| request.getCountryRiskLevel().indexOf("Sanctioned") > -1)
					&& null != fundsInRequest.getTrade() && null != fundsInRequest.getTrade().getCountryOfFund()) {
				HighRiskCountry hrc = new HighRiskCountry();
				hrc.setCountryCode(fundsInRequest.getTrade().getCountryOfFund());
				hrc.setDisplayName((String) request.getAdditionalAttribute(Constants.COUNTRY_DISPLAY_NAME));
				whiteList.getApprovedHighRiskCountryListForFundsIn().add(hrc);
			}
			CustomCheckResponse serviceResponse = new CustomCheckResponse();
			VelocityCheckResponse vResponse = new VelocityCheckResponse();
			vResponse.setBeneCheck(ServiceStatus.NOT_PERFORMED.name());
			vResponse.setNoOffundsoutTxn(ServiceStatus.NOT_PERFORMED.name());
			vResponse.setPermittedAmoutcheck(ServiceStatus.NOT_PERFORMED.name());
			vResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			serviceResponse.setVelocityCheck(vResponse);
			WhiteListCheckResponse wResponse = new WhiteListCheckResponse();
			wResponse.setAmoutRange(ServiceStatus.NOT_PERFORMED.name());
			wResponse.setCurrency(ServiceStatus.NOT_PERFORMED.name());
			wResponse.setReasonOfTransfer(ServiceStatus.NOT_PERFORMED.name());
			serviceResponse.setWhiteListCheck(wResponse);
			serviceResponse.setOverallStatus(ServiceStatus.NOT_PERFORMED.name());

			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setRequest(whiteList);
			ccExchange.setResponse(serviceResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in CustomChecks transformer class : transformRequest -", e);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
		try {
			CustomCheckResponse serviceResponse = (CustomCheckResponse) exchange.getResponse();
			LOG.debug(" {}", serviceResponse);
		} catch (Exception e) {
			LOG.error("Error in CustomChecks transformer class : transformResponse -", e);
			CustomCheckResponse serviceResponse = new CustomCheckResponse();
			VelocityCheckResponse vResponse = new VelocityCheckResponse();
			vResponse.setBeneCheck(ServiceStatus.SERVICE_FAILURE.name());
			vResponse.setNoOffundsoutTxn(ServiceStatus.SERVICE_FAILURE.name());
			vResponse.setPermittedAmoutcheck(ServiceStatus.SERVICE_FAILURE.name());
			vResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			serviceResponse.setVelocityCheck(vResponse);
			WhiteListCheckResponse wResponse = new WhiteListCheckResponse();
			wResponse.setAmoutRange(ServiceStatus.SERVICE_FAILURE.name());
			wResponse.setCurrency(ServiceStatus.SERVICE_FAILURE.name());
			wResponse.setReasonOfTransfer(ServiceStatus.SERVICE_FAILURE.name());
			serviceResponse.setWhiteListCheck(wResponse);
			serviceResponse.setOverallStatus(ServiceStatus.SERVICE_FAILURE.name());
			exchange.setResponse(serviceResponse);
		}
		return message;
	}

}
