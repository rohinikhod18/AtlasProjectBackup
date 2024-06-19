/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.dbport;

/**
 * The Class TransactionMonitoringQueryConstants.
 */
public class TransactionMonitoringQueryConstants {

	/** The Constant GET_TRANSACTION_MONITORING_PROVIDER_INIT_CONFIG_PROPERTY. */
	public static final String GET_TRANSACTION_MONITORING_PROVIDER_INIT_CONFIG_PROPERTY = "select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=0 and b.servicetype=(SELECT ID FROM Compliance_ServiceTypeEnum WHERE Code='TRANSACTION_MONITORING')";

	/** The Constant SAVE_INTO_TRANSACTION_MONITORING_MQ. */
	public static final String SAVE_INTO_TRANSACTION_MONITORING_MQ = "INSERT INTO [TransactionMonitoringMQ]( [OrganizationID], [RequestType], [AccountID], [ContactID], [PaymentInID], [PaymentOutID], [RequestJson], [DeliveryStatus], [DeliverOn], [CreatedBy], [CreatedOn], [isPresent]) "
    		+ "    VALUES((SELECT ID FROM Organization WHERE code=?),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/** The Constant SAVE_INTO_POST_CARD_TRANSACTION_MONITORING_MQ. */
	public static final String SAVE_INTO_POST_CARD_TRANSACTION_MONITORING_MQ = "INSERT INTO [PostCardTransactionMonitoringMQ] (TransactionID, CardRequestType, RequestJson, DeliveryStatus, DeliverOn, CreatedBy, CreatedOn, isPresent) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
	
	/** The Constant SAVE_INTO_POST_CARD_TRANSACTION_MONITORING_FAILED_REQUEST. */
	public static final String SAVE_INTO_POST_CARD_TRANSACTION_MONITORING_FAILED_REQUEST = "INSERT INTO [PostCardTransactionFailedRequest] (TransactionID, RequestJson, CreatedBy, CreatedOn) VALUES(?,?,?,?)";
	
	/**
	 * Instantiates a new transaction monitoring query constants.
	 */
	private TransactionMonitoringQueryConstants() {

	}

}
