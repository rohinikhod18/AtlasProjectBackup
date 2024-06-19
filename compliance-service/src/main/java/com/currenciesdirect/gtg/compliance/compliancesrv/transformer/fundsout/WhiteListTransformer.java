package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.VelocityCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Beneficiary;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.ApprovedCurrencyAmountRangePair;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.HighRiskCountry;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.UdateWhiteListRequest;
import com.currenciesdirect.gtg.compliance.commons.util.StringUtils;
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
			FundsOutRequest fundsOutRequest = (FundsOutRequest) request.getAdditionalAttribute("oldRequest");
			AccountWhiteList whiteList = new AccountWhiteList();
			whiteList.setOrgCode(fundsOutRequest.getOrgCode());
			whiteList.setAccountId(fundsOutRequest.getAccId());
			ApprovedCurrencyAmountRangePair buyCurrencyAmount = new ApprovedCurrencyAmountRangePair();
			buyCurrencyAmount.setCode(fundsOutRequest.getTrade().getBuyCurrency());
			buyCurrencyAmount.setTxnAmountUpperLimit(fundsOutRequest.getBeneficiary().getAmount());
			whiteList.getApprovedBuyCurrencyAmountRangeList().add(buyCurrencyAmount);
			whiteList.getApprovedReasonOfTransList().add(fundsOutRequest.getTrade().getPurposeOfTrade());
			if (request.isUSClientListBBeneAccNumber()) {
				whiteList.getUsClientListBBeneAccNumber().add(fundsOutRequest.getBeneficiary().getAccountNumber());
			}
			/** Code added to resolve AT-1122 */
			/**
			 * Bene Name+AccountNumber+Country should be whitelisted if this
			 * combination does not match, payment should be on HOLD
			 *
			 * Use code for Concantenated string & Displayname for pretty print
			 * on UI ex: John Doe, Account: 12345, Country: United Kingdom (UK)
			 **/
			Beneficiary bene = fundsOutRequest.getBeneficiary();
			if (null != request.getCountryRiskLevel() && !request.getCountryRiskLevel().isEmpty()
					&& request.getCountryRiskLevel().indexOf("High") > -1) {
				HighRiskCountry hrc = new HighRiskCountry();

				StringBuilder code = new StringBuilder();
				code.append(bene.getFullName()).append(bene.getAccountNumber()).append(bene.getCountry());
				hrc.setCountryCode(code.toString());
				StringBuilder pretty = new StringBuilder();
				pretty.append(bene.getFullName()).append(", Account:").append(bene.getAccountNumber())
						.append(", Country:")
						.append((String) request.getAdditionalAttribute(Constants.COUNTRY_DISPLAY_NAME)).append('(')
						.append(bene.getCountry()).append(')');
				hrc.setDisplayName(pretty.toString());
				whiteList.getApprovedHighRiskCountryList().add(hrc);
			}
			if (!StringUtils.isNullOrTrimEmpty(request.getBeneCheckStatus())
					&& request.getBeneCheckStatus().startsWith(ServiceStatus.FAIL.getServiceStatusAsString())) {
				whiteList.getApprovedOPIAccountNumber().add(bene.getAccountNumber());
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
			LOG.error("Error in CustomChecks transformer class, transformRequest method", e);
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
			CustomCheckResponse defaultServiceResponse = new CustomCheckResponse();
			VelocityCheckResponse defaultVResponse = new VelocityCheckResponse();
			defaultVResponse.setBeneCheck(ServiceStatus.SERVICE_FAILURE.name());
			defaultVResponse.setNoOffundsoutTxn(ServiceStatus.SERVICE_FAILURE.name());
			defaultVResponse.setPermittedAmoutcheck(ServiceStatus.SERVICE_FAILURE.name());
			defaultVResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			defaultServiceResponse.setVelocityCheck(defaultVResponse);
			WhiteListCheckResponse defaultWResponse = new WhiteListCheckResponse();
			defaultWResponse.setAmoutRange(ServiceStatus.SERVICE_FAILURE.name());
			defaultWResponse.setCurrency(ServiceStatus.SERVICE_FAILURE.name());
			defaultWResponse.setReasonOfTransfer(ServiceStatus.SERVICE_FAILURE.name());
			defaultServiceResponse.setWhiteListCheck(defaultWResponse);
			defaultServiceResponse.setOverallStatus(ServiceStatus.SERVICE_FAILURE.name());
			exchange.setResponse(defaultServiceResponse);
		}
		return message;
	}

}
