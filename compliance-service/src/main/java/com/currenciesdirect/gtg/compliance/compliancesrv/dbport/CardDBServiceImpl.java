package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Card;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;


/**
 * The Class CardDBServiceImpl.
 */
@Component("cardDBServiceImpl")
public class CardDBServiceImpl extends AbstractDao implements ICardDBServiceImpl{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CardDBServiceImpl.class);

	/**
	 * Save new card.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> saveNewCard(Message<MessageContext> message) throws ComplianceException {
		try {
			
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = messageExchange.getRequest(RegistrationServiceRequest.class);
			Card request = (Card) messageExchange.getRequest().getAdditionalAttribute("card");
			Boolean isTradeAccountNumberExist=request.getTradeAccountNumber()!=null;
			Boolean isUpdateCard = (Boolean) messageExchange.getRequest().getAdditionalAttribute("isUpdateCard");
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			Integer accountId = registrationRequest.getAccount().getId();
			if(isTradeAccountNumberExist) {
				if(Boolean.TRUE.equals(isUpdateCard))
					updateCard(request, token.getUserID());
				else	
					saveNewCard(request, token.getUserID(), accountId);
			}
			
			
		} catch (Exception e) {
			LOG.error("Error in ICardDBServiceImpl saveNewCard()", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	/**
	 * Save new card.
	 *
	 * @param request the request
	 * @param user the user
	 * @param accountId the account id
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	private boolean saveNewCard(Card request, Integer user, Integer accountId) throws ComplianceException {
		Connection connection = getConnection(Boolean.FALSE);
		try {
			beginTransaction(connection);
			saveIntoCardAttribute(connection, request, user, accountId);
			commitTransaction(connection);
			return true;
		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * Save into card attribute.
	 *
	 * @param connection the connection
	 * @param request the request
	 * @param user the user
	 * @param accountId the account id
	 * @throws ComplianceException the compliance exception
	 */
	private void saveIntoCardAttribute(Connection connection, Card request, Integer user, Integer accountId)
			throws ComplianceException {
			String cardJson = JsonConverterUtil.convertToJsonWithoutNull(request);
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(DBQueryConstant.INSERT_INTO_CARD_ATTRIBUTE.getQuery());
				statement.setString(1,request.getCardID());
				statement.setInt(2, accountId);
				statement.setString(3, cardJson);
				statement.setInt(4, user);
				statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				statement.setInt(6, user);
				statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				statement.setString(8, request.getCardStatusFlag());
				statement.setInt(9, request.getContactId());
				statement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closePrepareStatement(statement);
			}
	}
	
	  /**
  	 * Gets the card details from db.
  	 *
  	 * @param cardId the card id
  	 * @param accountID the account ID
  	 * @return the card details from db
  	 * @throws ComplianceException the compliance exception
  	 */
	@SuppressWarnings("java:S2095")
  	public Card getCardDetailsFromDb(String cardId) throws ComplianceException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Card oldRequest=null;
        String json;
        try {
            connection=getConnection(Boolean.TRUE);
            statement = connection.prepareStatement(DBQueryConstant.GET_CARD_DETAILS.getQuery());
            statement.setString(1,cardId);
            rs=statement.executeQuery();
            if (rs.next()) {
        		 json = rs.getString("Attributes");
        		 if(json!=null) {
                     oldRequest=setCardAttribute(json, oldRequest);
                 }
        		 return oldRequest;
             }
            else {
                return null;
            }
        }catch (Exception e) {
            throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
        }finally {
            closeResultset(rs);
            closePrepareStatement(statement);
            closeConnection(connection);
        }
    }
    
    /**
     * Sets the card attribute.
     *
     * @param attribute the attribute
     * @param request the request
     * @return the order card status update request
     */
    public Card setCardAttribute(String attribute, Card request) {
        if(attribute!=null) {
            request = JsonConverterUtil.convertToObject(Card.class, attribute);
        }
        return request;
    }
    
    /**
     * Update card.
     *
     * @param request the request
     * @param user the user
     * @param accountId the account id
     * @return true, if successful
     * @throws ComplianceException the compliance exception
     */
    private boolean updateCard(Card request, Integer user) throws ComplianceException {
		Connection connection = getConnection(Boolean.FALSE);
		try {
			beginTransaction(connection);
			updateIntoCardAttribute(connection, request, user);
			commitTransaction(connection);
			return true;
		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeConnection(connection);
		}
	}
    
    /**
     * Update into card attribute.
     *
     * @param connection the connection
     * @param request the request
     * @param user the user
     * @param accountId the account id
     * @throws ComplianceException the compliance exception
     */
    private void updateIntoCardAttribute(Connection connection, Card request, Integer user)
			throws ComplianceException {
			String cardJson = JsonConverterUtil.convertToJsonWithoutNull(request);
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(DBQueryConstant.UPDATE_INTO_CARD_ATTRIBUTE.getQuery());
				statement.setString(1, cardJson);
				statement.setInt(2, user);
				statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				statement.setString(4, request.getCardStatusFlag());
				statement.setString(5, request.getCardID());
				
				statement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closePrepareStatement(statement);
			}
	}
    
    @SuppressWarnings("java:S2095")
  	public Card getCardDetailsByAccountId(Integer accountId) throws ComplianceException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Card cardDetails=null;
        String json;
        try {
            connection=getConnection(Boolean.TRUE);
            statement = connection.prepareStatement(DBQueryConstant.GET_CARD_DETAILS_BY_ACCOUNT_ID.getQuery());
            statement.setInt(1,accountId);
            rs=statement.executeQuery();
            if (rs.next()) {
        		 json = rs.getString("Attributes");
        		 if(json!=null) {
        			 cardDetails = JsonConverterUtil.convertToObject(Card.class, json);
                 }
        		 return cardDetails;
             }
            else {
                return null;
            }
        }catch (Exception e) {
            throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
        }finally {
            closeResultset(rs);
            closePrepareStatement(statement);
            closeConnection(connection);
        }
    }    
}
