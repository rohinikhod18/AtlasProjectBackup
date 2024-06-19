package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoReport;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

public class StatusUpdateTransformer extends AbstractTransformer{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(StatusUpdateTransformer.class);
	
	/** The Constant RESPONSE_SUMMARY. */
	private static final String ONFIDO_RESPONSE = "ONFIDO_RESPONSE";
	
	/**
	 * Transform response.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		StatusUpdateRequest statusUpdateRequest = messageExchange.getRequest(StatusUpdateRequest.class);
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		EventServiceLog eventStatusUpdateLog = new EventServiceLog();
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest().getAdditionalAttribute(Constants.FIELD_EVENTID);
		RegistrationResponse profileUpdateResponse = (RegistrationResponse) statusUpdateRequest
				.getAdditionalAttribute(Constants.FIELD_BROADCAST_RESPONSE);
		ComplianceAccount caccount = profileUpdateResponse.getAccount();
		String status = statusUpdateRequest.getAdditionalAttribute(Constants.ONFIDO_RESULT).toString();
		Integer orgId = (Integer) statusUpdateRequest.getAdditionalAttribute("OrganizationId");

		try {
			Account account = (Account) statusUpdateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			ComplianceContact oldContact = new ComplianceContact();
			for (ComplianceContact contact : caccount.getContacts()) {
				if (contact.getContactSFID().equals(statusUpdateRequest.getCrmContactId())) {
					oldContact = contact;
					break;
				}
			}
			List<OnfidoReport> reports = statusUpdateRequest.getReports();
			//Creating Summary for showing on UI - AT-2050
			String modifiedOrgID = StringUtils.leftPadWithZero(3, orgId);
			OnfidoSummary summary = new OnfidoSummary();
			summary.setEventServiceLogId(eventId);
			summary.setStatus(status);
			
			StringBuilder onfidoId = new StringBuilder();
			onfidoId.append(modifiedOrgID).append("-C-").append(StringUtils.leftPadWithZero(10, oldContact.getId()));
			summary.setOnfidoId(onfidoId.toString());
			
			for(OnfidoReport report : reports) {
				if(Constants.DOCUMENT.equalsIgnoreCase(report.getName())) {
					summary.setOnfidoReport(report);
				}
			}
			statusUpdateRequest.addAttribute(ONFIDO_RESPONSE,reports);
			
			eventStatusUpdateLog.setEventId(eventId);
			eventStatusUpdateLog.setEntityType(EntityEnum.CONTACT.getEntityTypeAsString());
			eventStatusUpdateLog.setEntityId(oldContact.getId());
			eventStatusUpdateLog.setEntityVersion(account.getVersion());
			eventStatusUpdateLog.setServiceName(ServiceTypeEnum.ONFIDO_SERVICE.getShortName());
			eventStatusUpdateLog.setServiceProviderName(ServiceTypeEnum.ONFIDO_SERVICE.getShortName());
			eventStatusUpdateLog.setCratedBy(token.getUserID());
			eventStatusUpdateLog.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			eventStatusUpdateLog.setUpdatedBy(token.getUserID());
			updateEventServiceLogEntry(eventStatusUpdateLog,summary,statusUpdateRequest.getAdditionalAttribute(ONFIDO_RESPONSE),status);
			
			statusUpdateRequest.addAttribute(Constants.EVENT_SERVICE_LOG,eventStatusUpdateLog);
		}
		catch(Exception e) {
			message.getPayload().setFailed(true);
			LOGGER.error("Error in StatusUpdateTransformer transformResponse mapping {1}", e);
		}
		return message;
	}

	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		return null;
	}
}
