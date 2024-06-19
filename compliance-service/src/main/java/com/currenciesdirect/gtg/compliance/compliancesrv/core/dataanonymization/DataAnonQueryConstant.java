package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

@SuppressWarnings("squid:S1192")
public enum DataAnonQueryConstant {
	
	GET_ACCOUT_CONTACTS_ID("SELECT a.ID AS AccountID," + 
			" a.TradeAccountNumber AS TradeAccountNumber, " + 
			" case " + 
			" when a.[type] = 2 then 'PFX' " + 
			" when a.[type] = 1 then 'CFX' " + 
			" ELSE 'CFX (etailer)' " + 
			" END as [type], " + 
			" a.TradeAccountID as TradeAccountID, " + 
			" a.Version as AccountVersion, " + 
			" aa.[Attributes] as AccountAttributes, " + 
			" c.ID AS ContactID,      " + 
			" c.TradeContactID AS TradeContactID, " + 
			" c.Version as ContactVersion, " + 
			" ca.[Attributes] as ContactAttributes " + 
			" FROM Account a" + 
			" JOIN Contact c on c.AccountID = a.ID " + 
			" JOIN AccountAttribute aa on aa.id = a.ID " + 
			" JOIN ContactAttribute ca on ca.id = c.ID " + 
			" WHERE a.CRMAccountID = ?"),
	
	GET_EXISTING_PROCESS_FROM_PROCESSQUEUE("select a.NewReferenceID as ReferenceID, " + 
			" a.TradeAccountNumber as CustomerNumber, " + 
			" a.AnonStatus as status " + 
			" from DataAnonProcessQueue a WHERE a.NewReferenceID = ?"),
	
	INSERT_INTO_DATA_ANON_PROCESSQUEUE("INSERT into DataAnonProcessQueue([NewReferenceID],[TradeAccountNumber],[AccountID],[ContactID],[AnonJson],[AnonStatus],"
			+ "[AnonProcessStatus],[RetryCount],[CreatedBy],[CreatedOn],[UpdatedBy],[UpdatedOn])" + 
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"),
	
	UPDATE_ACCOUNT("UPDATE Account SET Name = ?,TradeAccountNumber=? WHERE ID = ?"),
	
	UPDATE_ACCOUNT_ATTRIBUTE_PFX("UPDATE AccountAttribute SET [Attributes] = "
			+ "JSON_MODIFY(JSON_MODIFY([Attributes],'$.act_name',?),'$.trade_acc_number',?) WHERE ID = ?"),
	
	UPDATE_ACCOUNT_ATTRIBUTE_CFX("UPDATE AccountAttribute SET [Attributes] = "
			+ "JSON_MODIFY(JSON_MODIFY(JSON_MODIFY(JSON_MODIFY([Attributes],'$.company.company_phone',?),"
			+ "'$.company.billing_address',?),'$.act_name',?),'$.trade_acc_number',?) " + 
			"WHERE ID = ?"),
	
	UPDATE_ACCOUNT_ATTRIBUTE_HISTORY_PFX("UPDATE AccountAttributeHistory SET [Attributes] = " + 
			"JSON_MODIFY(JSON_MODIFY([Attributes],'$.act_name',?),'$.trade_acc_number',?) WHERE AccountID = ?"),
	
	UPDATE_ACCOUNT_ATTRIBUTE_HISTORY_CFX("UPDATE AccountAttributeHistory SET [Attributes] = " + 
			"JSON_MODIFY(JSON_MODIFY(JSON_MODIFY(JSON_MODIFY([Attributes],'$.company.company_phone',?)," + 
			"'$.company.billing_address',?),'$.act_name',?),'$.trade_acc_number',?)" + 
			"WHERE AccountID = ?"),
	
	UPDATE_CONTACT("UPDATE Contact SET Name = ? WHERE AccountID = ?"),
	
	UPDATE_CONTACT_ATTRIBUTE("UPDATE ContactAttribute SET [ATTRIBUTES] = ? WHERE ID in (SELECT ID FROM Contact WHERE AccountID = ?)"),
	
	UPDATE_CONTACT_ATTRIBUTE_HISTORY("UPDATE ContactAttributeHistory SET [ATTRIBUTES] = ? WHERE ContactID in (SELECT ID FROM Contact WHERE AccountID = ?)"),
	
	UPDATE_EVENT_SERVICE_LOG_FOR_REG_BLACKLIST("UPDATE EventServiceLog SET ProviderResponse = ? " + 
			"WHERE EventID in (SELECT ID FROM Event WHERE PaymentInID IS NULL AND PaymentOutID IS NULL AND AccountID = ?)" + 
			"AND ServiceType = 3;"),
	
	UPDATE_EVENT_SERVICE_LOG_FOR_PAYIN_BLACKLIST("UPDATE EventServiceLog SET ProviderResponse = ? " + 
			"WHERE EventID in (SELECT ID FROM Event WHERE PaymentOutID IS NULL AND PaymentInID IS NOT NULL AND AccountID = ?) " + 
			"AND ServiceType = 3"),
	
	UPDATE_EVENT_SERVICE_LOG_FOR_PAYOUT_BLACKLIST("UPDATE EventServiceLog SET ProviderResponse = ? " + 
			"WHERE EventID in (SELECT ID FROM Event WHERE PaymentInID IS NULL AND PaymentOutID IS NOT NULL AND AccountID = ?) "+
			"AND ServiceType = 3"),
		
	UPDATE_PROFILE_ACTIVITY_LOG_DETAIL("UPDATE ProfileActivityLogDetail SET [Log] = ? "
			+ "WHERE ActivityID in (SELECT ID from ProfileActivityLog WHERE AccountID = ?)"),
	
	UPDATE_DATAANON_STATUS("UPDATE DataAnonProcessQueue SET AnonStatus = ? WHERE NewReferenceID = ?"),
	
	DELETE_DATA_ANON_PROCESS("DELETE FROM DataAnonProcessQueue WHERE NewReferenceID = ?");
	
	private String query;
	
	DataAnonQueryConstant(String query){
		this.query = query;
	}
	
	public String getQuery(){
		return this.query;
	}
}
