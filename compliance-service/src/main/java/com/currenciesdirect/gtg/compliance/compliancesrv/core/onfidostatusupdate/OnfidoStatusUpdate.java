package com.currenciesdirect.gtg.compliance.compliancesrv.core.onfidostatusupdate;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.DBQueryConstant;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class OnfidoStatusUpdate extends AbstractDao {
	
	/**
	 * Update onfido status.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> updateOnfidoStatus(Message<MessageContext> message) throws ComplianceException {
		try {
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
			if (operation == OperationEnum.ONFIDO_STATUS_UPDATE) {
				MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
				OnfidoUpdateRequest onfidoUpdateRequest = messageExchange.getRequest(OnfidoUpdateRequest.class);
				EntityDetails entityDetails = (EntityDetails) onfidoUpdateRequest
						.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
				OnfidoUpdateData data = onfidoUpdateRequest.getOnfido().get(0);
				EventServiceLog eventServiceLog = messageExchange.getEventServiceLog(ServiceTypeEnum.ONFIDO_SERVICE,
						EntityEnum.valueOf(data.getEntityType()).name(), data.getEntityId());
				
				updateOnfidoServiceStatus(eventServiceLog, entityDetails);
			}
		} catch (Exception e) {
		     message.getPayload().setFailed(true);
		}
		return message;
	}
	
	/**
	 * Update onfido service status.
	 *
	 * @param eventServiceLog the event service log
	 * @param entityDetails the entity details
	 * @throws ComplianceException the compliance exception
	 */
	public void updateOnfidoServiceStatus(EventServiceLog eventServiceLog, EntityDetails entityDetails)
			throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		try {
			conn = getConnection(Boolean.FALSE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_ONFIDO_STATUS_FOR_ACCOUNT.getQuery());
			preparedStatment.setInt(1, ServiceStatus.getStatusAsInteger(eventServiceLog.getStatus()));
			preparedStatment.setInt(2, eventServiceLog.getEventId());
			preparedStatment.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
	}

}
