package com.currenciesdirect.gtg.compliance.dbport.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring.FraudRingNode;
import com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring.FraudRingRequest;
import com.currenciesdirect.gtg.compliance.core.report.IFraudRingDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.dbport.FraudRingQueryConstant;

@Component("fraudRingDBServiceImpl")
public class FraudRingDBServiceImpl extends AbstractDao implements IFraudRingDBService{
	
	private Logger logger = LoggerFactory.getLogger(FraudRingDBServiceImpl.class);
	
	public FraudRingNode getClientData(FraudRingRequest fraudRingGraphRequest) {
		FraudRingNode fraudRingNode = new FraudRingNode();
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStmt = connection.prepareStatement(FraudRingQueryConstant.GET_CLIENT_DETAILS_FOR_FRAUD_RING.getQuery());
			preparedStmt.setString(1, fraudRingGraphRequest.getCrmContactId());
			rs = preparedStmt.executeQuery();
			while(rs.next()) {
				fraudRingNode.setClientId(rs.getInt("ContactId"));
				fraudRingNode.setClientType(rs.getString("Type"));
				fraudRingNode.setClientEmail(rs.getString("Email"));
				fraudRingNode.setClientBuildingName(rs.getString("BuildingName"));
				fraudRingNode.setClientPostcode(rs.getString("Postcode"));
				fraudRingNode.setClientPhone(rs.getString("Phone"));
				fraudRingNode.setEntityCDH(rs.getString("CDH_Entity"));
				fraudRingNode.setModeOfRegistration(rs.getString("Registration_Mode"));
				fraudRingNode.setRegistrationDate(rs.getString("Registration_Date"));
				fraudRingNode.setRegisteredDate(rs.getString("Registered_Date"));
			}
		}
		catch(Exception e) {
			logger.error("Error in FraudRingDBServiceImpl class getClientData method : {1}", e);
		}
		finally {
			closeConnection(connection);
			closePrepareStatement(preparedStmt);
			closeResultset(rs);
		}
		return fraudRingNode;
	}
}
