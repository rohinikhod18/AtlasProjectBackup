package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

/**
 * The Enum DBQueryConstant.
 *
 * @author Rajesh
 */
@SuppressWarnings("squid:S1192")
public enum DBQueryConstant {
	
	GET_ACCOUNT_CONTACT_DETAILS ("WITH CTE AS( "
			+ " SELECT A.Id AS AccountID "
			+ " FROM Account A "
			+ " WHERE crmaccountid = ? "
			+ " UNION SELECT AccountID AS AccountID "
			+ " FROM Contact B "
			+ " WHERE crmcontactid IN({CONTACT_SF_ID}) "
			+ ") SELECT * FROM "
			+ " ( SELECT "
			+ " (SELECT id FROM organization WHERE code = ? ) AS RequestOrgID, "
			+ " (SELECT id FROM country WHERE code = ? ) AS CountryID, "
			+ " (SELECT id FROM currency WHERE code = ? ) AS BuyCurrencyId, "
			+ " (SELECT id FROM currency WHERE code = ? ) AS SellCurrencyId "
			+ " FROM (SELECT 1 AS t ) DUMMY) mt "
			+ " LEFT JOIN(SELECT (SELECT Count( cc.id ) FROM contact cc WHERE cc.accountid = ac.id ) AS contacts, "
			+ " ac.id AS AccountId, "
			+ " ac.EIDStatus AS AEIDStatus, "
			+ " ac.FraugsterStatus AS AFraugsterStatus, "
			+ " ac.SanctionStatus AS ASanctionStatus, "
			+ " ac.BlacklistStatus AS ABlacklistStatus, "
			+ " ac.PayInWatchListStatus AS PayInWatchlistStatus, "
			+ " ac.PayOutWatchListStatus AS PayOutWatchlistStatus, "
			+ " ac.isOnQueue AS AccOnQueue, "
			+ " ac.LegalEntity AS ACLegalEntity, "//Add for AT-3327
			+ " ac.OldOrganizationID, "
			+ " (SELECT Status from AccountComplianceStatusEnum acs where acs.id = ac.compliancestatus ) AS ACStatus, "
			+ " AC.createdon AS RegistrationInTime, "
			+ " AC.ComplianceDoneOn AS RegisteredTime, "
			+ " AC.ComplianceExpiry AS ComplianceExpiry, "
			+ " ACA.attributes AS ACAttrib, "
			+ " ACA.version AS ACVersion, "
			+ " c.id AS ContactId, "
			+ " c.CRMContactID AS CRMContactID, "
			+ " (select status from ContactComplianceStatusEnum ccs where ccs.id = c.ComplianceStatus ) AS CoStatus, "
			+ " c.[primary] AS PrimaryContact, "
			+ " c.EIDStatus AS CEIDStatus, "
			+ " c.FraugsterStatus AS CFraugsterStatus, "
			+ " c.SanctionStatus AS CSanctionStatus, "
			+ " c.BlacklistStatus AS CBlacklistStatus, "
			+ " c.CustomCheckStatus AS CCustomCheckStatus, "
			+ " c.PayInWatchListStatus AS CPayInWatchlistStatus, "//Add for AT-2986
			+ " c.PayOutWatchListStatus AS CPayOutWatchlistStatus, "//Add for AT-2986
			+ " c.isOnQueue AS ContactOnQueue, "
			+ " CA.attributes AS CAttrib, "
			+ " CA.version AS CAVersion, "
			+ " org.id AS ACOrgID, "
			+ " org.code AS ACOrgName, "
			+ " ac.AccountTMFlag," //Add For AT-4169
			+ " ac.IntuitionRiskLevel" //Added for AT-4190
			+ " FROM account AC "
			+ " JOIN CTE AcS ON "
			+ " Acs.accountid = AC.Id "
			+ " JOIN accountattribute ACA ON AC.id = ACA.id "
			+ " RIGHT JOIN contact C ON c.accountid = AC.id "
			+ " JOIN contactattribute CA ON c.id = CA.id "
			+ " JOIN organization org ON AC.organizationid = org.id ) details ON 1 = 1"),

	INSERT_INTO_ACCOUNT("INSERT INTO Account([OrganizationID], [Name], [CRMAccountID], [TradeAccountID], [TradeAccountNumber], [Type], [AccountStatus], [ComplianceStatus], [ComplianceDoneOn], [ComplianceExpiry],[CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn] ,[Version]  , [EIDStatus], [FraugsterStatus], [SanctionStatus], [BlacklistStatus], [PayInWatchListStatus], [PayOutWatchListStatus],[LegalEntity],[isOnQueue],[STPFlag],[LegalEntityID],[FirstFundsInEDDCheck]) VALUES((select id from Organization where code=?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,(select id from LegalEntity where code=?),?)"),
	
	//[ComplianceDoneOn]=?, [ComplianceExpiry]=?, [CreatedBy]=?, [CreatedOn]=?, 
	UPDATE_INTO_ACCOUNT("UPDATE Account SET [OrganizationID]=(SELECT [ID] FROM Organization WHERE CODE=?), [Name]=?, [CRMAccountID]=?, [TradeAccountID]=?, [TradeAccountNumber]=?, [Type]=?, [AccountStatus]=?, [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=? ,[Version]=? ,[LegalEntity]=? ,[ComplianceDoneOn] = ?, [ComplianceExpiry]= ?, [LegalEntityID]=(SELECT [ID] FROM LegalEntity WHERE CODE=?) WHERE ID=?"),
	
	//For CDSA migration AT-4474
	UPDATE_INTO_ACCOUNT_FOR_LEGACY_NUMBER("UPDATE Account SET [UpdatedBy]=?, [UpdatedOn]=?, [LegacyTradeAccountNumber]=?, [legacyTradeAccountID]=? WHERE ID=?"),
	
	UPDATE_ACCOUNT_VERSION("UPDATE Account SET [UpdatedBy]=?, [UpdatedOn]=? ,[Version]=? WHERE ID=?"),
	
	UPDATE_ACCOUNT_WITHOUT_KYC_COMPLIANCE_STATUS("UPDATE Account SET [ComplianceStatus]=(SELECT [ID] FROM [AccountComplianceStatusEnum ] WHERE Status = ?), [AccountStatus]=?, [UpdatedBy]=?, [UpdatedOn]=?,FraugsterStatus=?,BlacklistStatus=?,PayInWatchListStatus=?, PayOutWatchListStatus=?,ComplianceDoneOn=? ,ComplianceExpiry = ? , isOnQueue = ?, SanctionStatus=? WHERE ID=?"),
	
	UPDATE_ACCOUNT_WITHOUT_SANCTION_COMPLIANCE_STATUS("UPDATE Account SET [ComplianceStatus]=(SELECT [ID] FROM [AccountComplianceStatusEnum ] WHERE Status = ?), [AccountStatus]=?, [UpdatedBy]=?, [UpdatedOn]=?,FraugsterStatus=?,BlacklistStatus=?,PayInWatchListStatus=?, PayOutWatchListStatus=? ,ComplianceDoneOn=? ,ComplianceExpiry = ? , isOnQueue = ?, EIDStatus=? WHERE ID=?"),
	
	UPDATE_ACCOUNT_WITHOUT_SANCTION_AND_KYC_COMPLIANCE_STATUS("UPDATE Account SET [ComplianceStatus]=(SELECT [ID] FROM [AccountComplianceStatusEnum ] WHERE Status = ?), [AccountStatus]=?, [UpdatedBy]=?, [UpdatedOn]=?,FraugsterStatus=?,BlacklistStatus=?,PayInWatchListStatus=?, PayOutWatchListStatus=? ,ComplianceDoneOn=? ,ComplianceExpiry = ? , isOnQueue = ? WHERE ID=?"),
	
	UPDATE_ACCOUNT_COMPLIANCE_STATUS("UPDATE Account SET [ComplianceStatus]=(SELECT [ID] FROM [AccountComplianceStatusEnum ] WHERE Status = ?), [AccountStatus]=?, [UpdatedBy]=?, [UpdatedOn]=?,FraugsterStatus=?,BlacklistStatus=?,PayInWatchListStatus=?, PayOutWatchListStatus=? ,ComplianceDoneOn=? ,ComplianceExpiry = ? , isOnQueue = ?, EIDStatus=?, SanctionStatus=? WHERE ID=?"),
	
	UPDATE_ACCOUNT_COMPLIANCE_STATUS_STP("UPDATE Account SET [ComplianceStatus]=(SELECT [ID] FROM [AccountComplianceStatusEnum ] WHERE Status = ?), [AccountStatus]=?, [UpdatedBy]=?, [UpdatedOn]=?,FraugsterStatus=?,BlacklistStatus=?,PayInWatchListStatus=?, PayOutWatchListStatus=? ,ComplianceDoneOn=? ,ComplianceExpiry = ? , isOnQueue = ?, EIDStatus=?,SanctionStatus=?, STPFlag = ? WHERE ID=?"),
	
	INSERT_INTO_ACCOUNT_ATTRIBUTE("INSERT INTO AccountAttribute([ID], [Attributes], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn], [Version])"+
	" VALUES(?, ?, ?, ?, ?, ?, ?)"),
	
	//UPDATE_INTO_ACCOUNT_ATTRIBUTE("UPDATE AccountAttribute SET [Attributes]=?, [UpdatedBy]=?, [UpdatedOn]=?, [Type]=?, [BuyCurrency]=?, [SellCurrency]=?, [Source]=?, [TransactionValue]=?,[Version]=?, [Country]=(SELECT [ID] FROM [Country] WHERE DisplayName=?) WHERE ID=?"),
	UPDATE_INTO_ACCOUNT_ATTRIBUTE("UPDATE AccountAttribute SET [Attributes]=?, [UpdatedBy]=?, [UpdatedOn]=?, [Version]=? "+
	"  WHERE ID=?"),
	
	UPDATE_INTO_ACCOUNT_ATTRIBUTE_STATUS("UPDATE AccountAttribute SET [Attributes]=?, [UpdatedBy]=?, [UpdatedOn]=? WHERE ID=?"),
	
	INSERT_INTO_ACCOUNT_ATTRIBUTE_HISTORY("INSERT INTO AccountAttributeHistory ([AccountID], [Attributes], [CreatedBy], [CreatedOn],[Version]) VALUES(?, ?, ?, ?, ?)"),
	
	//Add paymentIN/OUTwatchliststatus for AT-2986
	INSERT_INTO_CONTACT("INSERT INTO Contact([OrganizationID], [AccountID], [Name], [CRMContactID], [TradeContactID], [Primary], [ComplianceStatus], [ComplianceDoneOn], [ComplianceExpiry], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn], "
			+ " [Version], [Country] , [EIDStatus], [FraugsterStatus], [SanctionStatus], [BlacklistStatus] ,[CustomCheckStatus],[PayInWatchListStatus], [PayOutWatchListStatus], [isOnQueue],[STPFlag] )" +
	" VALUES((select id from Organization where code=?), ?, ?, ?, ?, ?, (select id from ContactComplianceStatusEnum  where STATUS=?), ?, ?, ?, ?, ?, ?, ?, (SELECT [ID] FROM [Country] WHERE code=?),?,?, ? ,?,?,?,?,?,?)"),
	
	UPDATE_CONTACT_BY_ID("UPDATE Contact SET [Name]=?, [CRMContactID]=?, [TradeContactID]=?, [Primary]=?, [UpdatedBy]=?, [UpdatedOn]=?,[Version]=?,[Country]=(SELECT [ID] FROM [Country] WHERE code=?) WHERE Id=?"),
	
	//For CDSA migration AT-4474
	UPDATE_CONTACT_BY_ID_FOR_LEGACY_NUMBER("UPDATE Contact SET [UpdatedBy]=?, [UpdatedOn]=?, [LegacyTradeContactID]=? WHERE Id=?"),
	
	UPDATE_CONTACT_WITHOUT_SANCTION_AND_KYC_AND_FRAUDPREDICT_COMPLIANCE_STATUS("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,BlacklistStatus=? , CustomCheckStatus=?,isOnQueue = ?, PayInWatchListStatus=?, PayOutWatchListStatus=?  WHERE Id=?"),
	
	UPDATE_CONTACT_WITHOUT_SANCTION_AND_FRAUDPREDICT_COMPLIANCE_STATUS("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,EIDStatus=?,BlacklistStatus=? ,CustomCheckStatus=?, isOnQueue = ? , PayInWatchListStatus=?, PayOutWatchListStatus=?  WHERE Id=?"),
	
	UPDATE_CONTACT_WITHOUT_KYC_AND_FRAUDPREDICT_COMPLIANCE_STATUS("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,SanctionStatus=?,BlacklistStatus=?, CustomCheckStatus=?,isOnQueue = ?, PayInWatchListStatus=?, PayOutWatchListStatus=?  WHERE Id=?"),
	
	UPDATE_CONTACT_COMPLIANCE_STATUS("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,EIDStatus=?,FraugsterStatus=?,SanctionStatus=?,BlacklistStatus=? , CustomCheckStatus=?,isOnQueue = ? , PayInWatchListStatus=?, PayOutWatchListStatus=? WHERE Id=?"),
	
	UPDATE_CONTACT_COMPLIANCE_STATUS_STP("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,EIDStatus=?,FraugsterStatus=?,SanctionStatus=?,BlacklistStatus=? , CustomCheckStatus=?,isOnQueue = ?, STPFlag=?, POI_NEEDED=?, PayInWatchListStatus=?, PayOutWatchListStatus=?  WHERE Id=?"),
	
	//Add For AT-2986 update contact payment IN/OUT watchlist status
	UPDATE_CONTACT_PAYMENTS_WATCHLIST_STATUS("UPDATE Contact SET [UpdatedBy]=?, [UpdatedOn]=?, [PayInWatchListStatus]=?, [PayOutWatchListStatus]=?  WHERE Id = ? "),
		
	INSERT_INTO_CONTACT_ATTRIBUTE("INSERT INTO ContactAttribute([ID],[Attributes], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn],[Version]) VALUES(?, ?, ?, ?, ?, ?, ?)"),
	
	UPDATE_INTO_CONTACT_ATTRIBUTE("UPDATE ContactAttribute SET [Attributes]=?,[UpdatedBy]=?, [UpdatedOn]=?,[Version]=? WHERE ID=?"),
	
	INSERT_INTO_CONTACT_ATTRIBUTE_HISTORY("INSERT INTO ContactAttributeHistory ([ContactID], [Attributes], [CreatedBy], [CreatedOn],[Version]) VALUES(?, ?, ?, ?, ?)"),
	
	INSERT_INTO_EVENT_REGISTRATION("INSERT INTO Event([OrganizationID], [EventType], [AccountID], [AccountVersion], [PaymentInID], [PaymentOutID], [CreatedBy], [CreatedOn]) VALUES(( SELECT [ID] FROM Organization WHERE CODE=?), (SELECT [ID] FROM EventTypeEnum WHERE Type=?), ?, ?, ?, ?, ?, ?)"),
	
	UPDATE_EVENT_REGISTRATION("UPDATE Event SET [Status]=?, [Summary]=? WHERE [ID]=?"),
	
	INSERT_INTO_EVENT_SERVICE_LOG("INSERT INTO EventServiceLog([EventID], [EntityType], [EntityID], [EntityVersion], [ServiceProvider], [ServiceType], [ProviderResponse], [Status], [Summary],[CreatedBy],[CreatedOn], [UpdatedBy], [UpdatedOn]) "
			+ "VALUES( ?, ?, ?, ?, (SELECT [ID] FROM Compliance_ServiceProvider WHERE Code =?) , (SELECT [ID] FROM Compliance_ServiceTypeEnum WHERE code=?), ?, ?, ?, ?, ?, ?, ?)"),
	
	GET_PROVIDER_INFO("select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=1"),
	
	UPDATE_DOCUMENT_KYC_CONTACT_STATUS("UPDATE Account SET [AccountStatus]=? WHERE [OrganizationID]=? , [CRMAccountID]=?"),

	GET_ACCOUNTID_FROM_CRMACCOUNTID("SELECT ID FROM Account WHERE CRMAccountID=?"),
	
	GET_ATTRIBUTES_FROM_CONTACTID("SELECT [Attributes] FROM ContactAttribute where ID=?"),
	
	GET_ATTRIBUTES_FROM_ACCOUNTID("SELECT [Attributes] FROM AccountAttribute where ID=?"),
	
	GET_CONTACTID_FROM_CRMCONTACTID("SELECT ID FROM Contact WHERE CRMContactID=?"),
	
	/*GET_CONTACTWATCHLIST_BY_ACCOUNTID(" SELECT * FROM Contactwatchlist WHERE Contact IN (SELECT id FROM Contact WHERE AccountId= ? ) "),*/
	
	GET_CONTACTWATCHLIST_BY_ACCOUNTID(" SELECT * FROM Contactwatchlist cw JOIN watchlist w ON (w.id=cw.Reason AND w.StopPaymentIn =1 AND w.StopPaymentOut = 1) WHERE Contact "
			+ " IN (SELECT id FROM Contact "
			+ " WHERE AccountId= ? )"),
	
	GET_CONTACTWATCHLIST_BY_ACCOUNTID_FOR_PAYMENTIN(" SELECT *  FROM Contactwatchlist cw JOIN watchlist w ON (w.id=cw.Reason AND w.StopPaymentIn =1) WHERE Contact "
			+ " IN (SELECT id "
			+ " FROM Contact WHERE AccountId= ? )"),
	
	GET_CONTACTWATCHLIST_BY_ACCOUNTID_FOR_PAYMENTOUT(" SELECT *  FROM Contactwatchlist cw JOIN watchlist w ON (w.id=cw.Reason AND w.StopPaymentOut =1) WHERE Contact "
			+ " IN (SELECT id FROM Contact WHERE AccountId= ? )"),
	
	INSERT_INTO_CONTACT_WATCHLIST("INSERT INTO ContactWatchList([Reason], [Contact], [CreatedBy], [CreatedOn]) VALUES((SELECT [ID] FROM WatchList WHERE Reason=?), ?, ?, ?)"),
	
	CHECK_ACCOUNT_ID_EXIST("select * from Account where CRMAccountID=?"),
	
	GET_ACCOUNT_BY_TRADE_ACCOUNT_NUMBER("select acc.ID, acc.AccountStatus, acc.version, acc.LEUpdateDate, att.attributes from Account acc join AccountAttribute att on acc.id = att.id where TradeAccountNumber=? and acc.OrganizationID= "
				+ " (SELECT id from Organization where code=?)"),//Add for AT-3349
	
	CHECK_CONTACT_ID_EXIST("select * from Contact where CRMContactID=?"),
	
	CHECK_TRADE_CONTACT_ID_EXIST("select c.id, c.version, att.attributes from Contact c join ContactAttribute att on c.id=att.id "
		+ "join account acc on acc.id = c.accountid "
		+ "where TradeContactID=? and acc.OrganizationID= "
		+ " (SELECT id from Organization where code=?)"),
	
	INSERT_INTO_DEVICEINFO("INSERT INTO DeviceInfo([EventID], [UserAgent], [DeviceType], [DeviceName], [DeviceVersion], [DeviceID], [DeviceManufacturer], [OSType], [BrowserName], [BrowserVersion], [BrowserLanguage], [BrowserOnline], [OSTimestamp], [CDAppID], [CDAppVersion], [CreatedBy], [CreatedOn], [ScreenResolution]) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"),
	
	CHECK_ACCOUNT_ID_EXIST_FOR_REPEAT_CHECK("select * from Account where ID=?"),
	
	CHECK_CONTACT_ID_EXIST_FOR_REPEAT_CHECK("select * from Contact where ID=?"),
	
	CHECK_TRADE_ACCOUNT_ID_EXIST_FOR_REPEAT_CHECK("SELECT TradeAccountID FROM ACCOUNT WHERE ID=?"),
	
	GET_EVENT_BY_ID("SELECT * FROM EventServiceLog WHERE EventId=?"),
	
	GET_EVENT_BY_ID_AND_SERVICE_TYPE("SELECT * FROM EventServiceLog WHERE EventId=? AND ServiceType=(SELECT [ID] FROM Compliance_ServiceTypeEnum WHERE code=?)"),
	
	UPDATE_EVENT_BY_EVENT_SERVICE_ID_AND_SERVICE_TYPE("UPDATE EventServiceLog SET ProviderResponse=?,Status=?,Summary=?,UpdatedBy=?,UpdatedOn=? WHERE Id=? AND ServiceType=(SELECT [ID] FROM Compliance_ServiceTypeEnum WHERE code=?)"),
	
	GET_EVENT_LOGS_BY_EVENT_IDS_AND_SERVICE_TYPE("SELECT * FROM EventServiceLog WHERE EventId IN ({EVENT_IDS}) AND EntityID= ? AND ServiceType=(SELECT [ID] FROM Compliance_ServiceTypeEnum WHERE code=?)"),

	INSERT_PROFILE_ACTIVITY_LOG("INSERT INTO [ProfileActivityLog] ([Timestamp], ActivityBy, OrganizationID, AccountID, ContactID, Comments, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn, UserLockID) "
			+ "VALUES( ?, (SELECT id FROM [USER] WHERE SSOUserID=?), "
			+ "(SELECT Id FROM Organization WHERE Code= ? ), ?, ?, ?, "
			+ "(SELECT id FROM [USER] WHERE SSOUserID=?), ?, "
			+ "(SELECT id FROM [USER] WHERE SSOUserID=?), ?, "
			+ "(SELECT ID FROM UserResourceLock WHERE ResourceType = ? AND ResourceID = ? AND LockReleasedOn IS NULL))"),
	
	INSERT_PROFILE_ACTIVITY_LOG_DETAIL("INSERT INTO [ProfileActivityLogDetail] VALUES(?, (SELECT Id FROM ActivityTypeEnum WHERE Module=? and Type=?), ?, (SELECT id FROM [USER] WHERE SSOUserID=?), ?, (SELECT id FROM [USER] WHERE SSOUserID=?), ?)"),

	INSERT_PAYMENT_OUT_ACTIVITY_LOG("INSERT INTO [PaymentOutActivityLog] ( PaymentOutID, ActivityBy, [Timestamp], Comments, CreatedBy, CreatedOn, UserLockID)"
			+ " VALUES(?, (SELECT id FROM [USER] WHERE SSOUserID=?), "
			+ "?, ?, (SELECT id FROM [USER] WHERE SSOUserID=?), ?,"
			+ "(SELECT ID FROM UserResourceLock WHERE ResourceType = 1 AND ResourceID = ? AND LockReleasedOn IS NULL) )"),
	
	INSERT_PAYMENT_OUT_ACTIVITY_LOG_DETAIL("INSERT INTO [PaymentOutActivityLogDetail]([ActivityID], [ActivityType], [Log], [CreatedBy], [CreatedOn]) VALUES(?, (SELECT Id FROM ActivityTypeEnum WHERE Module=? and Type=?), ?, (SELECT id FROM [USER] WHERE SSOUserID=?), ?)"),
	
	INSERT_PAYMENT_IN_ACTIVITY_LOG("INSERT INTO [PaymentInActivityLog] (PaymentInID, ActivityBy, [Timestamp], Comments, CreatedBy, CreatedOn, UserLockID)"
			+ " VALUES(?, (SELECT id FROM [USER] WHERE SSOUserID=?), "
			+ "?, ?, (SELECT id FROM [USER] WHERE SSOUserID=?), ?,"
			+ "(SELECT ID FROM UserResourceLock WHERE ResourceType = 2 AND ResourceID = ? AND LockReleasedOn IS NULL) )"),
	
	INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL("INSERT INTO [PaymentInActivityLogDetail]([ActivityID], [ActivityType], [Log], [CreatedBy], [CreatedOn]) VALUES(?, (SELECT Id FROM ActivityTypeEnum WHERE Module=? and Type=?), ?, (SELECT id FROM [USER] WHERE SSOUserID=?), ?)"),
	
	CHECK_COUNTRY_ENUM("select * from Country where code=?"),
	
	INSERT_INTO_PROFILE_STATUS_REASON("INSERT INTO [ProfileStatusReason]([OrganizationID], [AccountID], [ContactID], [StatusUpdateReasonID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) VALUES( (select id from Organization where code=?), ?, ?, (SELECT [ID] FROM [StatusUpdateReason] WHERE [Module]=? AND [Reason]=?), ?, ?, ?, ?)"),
	
	INSERT_INTO_ACCOUNT_COMPLIANCE_HISTORY("INSERT INTO [AccountComplianceHistory]([AccountID], [Status], [CreatedBy], [CreatedOn],[Version]) VALUES(?, (SELECT [ID] FROM [AccountComplianceStatusEnum] WHERE Status = ?), ?, ?, ?)"),

	INSERT_INTO_CONTACT_COMPLIANCE_HISTORY("INSERT INTO [ContactComplianceHistory]([ContactID], [Status], [CreatedBy], [CreatedOn],[Version]) VALUES(?, (SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), ?, ?, ?)"),
	
	GET_ACCOUNT_VERSION("SELECT Version FROM Account WHERE CRMAccountID= ?"),
	
	GET_CONTACT_VERSION("SELECT Version FROM Contact WHERE CRMContactID= ?"),
	
	GET_ACCOUNT_AND_CONTACT_ATTRIBUTE_BY_CONTACTID("Select acc.Attributes as AccAttrib,con.Attributes as ConAttrib,og.Code as Org,a.OldOrganizationId from ContactAttribute con join AccountAttribute acc on acc.ID=? and con.id=? join Organization og on og.ID=(select OrganizationID from Contact where id=?) join Account a ON acc.id = a.id"),
	
	GET_ACCOUNT_ATTRIBUTE_BY_ACCOUNT_ID("Select a.OldOrganizationId, aa.Attributes from Account a JOIN AccountAttribute aa ON a.id = aa.id where aa.id=?"),
	
	GET_DEVICE_INFO("SELECT TOP 1 d.BrowserLanguage, d.BrowserVersion, d.BrowserName, d.BrowserOnline, d.CDAppID,"
			+ " d.CDAppVersion, d.DeviceID, d.DeviceManufacturer, d.DeviceName, d.OSType, d.DeviceType, "
			+ " d.DeviceVersion, d.OSTimestamp, d.UserAgent, d.ScreenResolution"
			+ " FROM DeviceInfo d JOIN Event e on d.eventid=e.id WHERE e.accountid=? and e.eventtype<=3 ORDER BY e.id DESC"),
	
	GET_ACCOUNT_BY_TRADEACCOUNTID_AND_ORG("Select id from Account where TradeAccountId=? And OrganizationID=(Select id from Organization where code=?)"),
	
	INSERT_INTO_PAYMENTS_OUT("INSERT INTO [PaymentOut]([OrganizationID], [AccountID], [ContactID], [ContractNumber], [TradePaymentID], [ComplianceStatus], [ComplianceDoneOn], [Deleted], [CreatedBy],"
			+ " [CreatedOn], [UpdatedBy], [UpdatedOn] , [FraugsterStatus] , [SanctionStatus], [BlacklistStatus], [CustomCheckStatus], [TransactionDate] , [isOnQueue],[STPFlag],[LegalEntityId], [IntuitionCheckStatus])"+
			    "VALUES((select id from Organization where code=?), ?, ?, ?, ?, (select id from PaymentComplianceStatusEnum where status=?), ?, ?, ?, ?, ?, ? , ? ,? ,?,?,?,?,?,(select id from legalEntity where code=?),?)"),
	
	INSERT_INTO_PAYMENTS_OUT_ATTRIBUTE("INSERT INTO [PaymentOutAttribute]([ID], [Version], [Attributes], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn],[BeneAmount])"+ 
			"VALUES(?, ?, ?, ?, ?, ?, ?, ? )"),
	
	INSERT_INTO_PAYMENTS_OUT_ATTRIBUTE_HISTORY("INSERT INTO [PaymentOutAttributeHistory]([PaymentOutID], [Version], [Attributes], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])"+ 
			"VALUES(?, (SELECT version from PaymentOutAttribute where ID =?), (SELECT Attributes from PaymentOutAttribute where ID =?), ?, ?, ?, ?)"),
	
	UPDATE_PAYMENT_OUT("UPDATE [PaymentOut] SET [UpdatedBy]=?, [UpdatedOn]=?  WHERE id = ?"),
	UPDATE_PAYMENT_OUT_STATUS ("UPDATE [PaymentOut] SET [UpdatedBy]=?, [UpdatedOn]=? , [ComplianceStatus] = ? WHERE id = ?"),
	
	UPDATE_PAYMENT_OUT_ATTRIBUTE("UPDATE [PaymentOutAttribute] SET [Version]= [Version]+1, [Attributes]=?, [UpdatedBy]=?, [UpdatedOn]=?, [BeneAmount]=? WHERE id = ?"),
	
	UPDATE_CONTACT_ATTRIBUTE("update ContactAttribute set Attributes = ? where id=?"),
	
	GET_SERVICE_STATUS_BY_ENTITY_ID(" SELECT TOP 1 providerResponse,summary,Status,(SELECT Name FROM Account WHERE id=entityid) As AccountName, "
			+ " (SELECT Name From contact WHERE id=entityid) As ContactName  FROM eventservicelog "
			+ " WHERE eventid IN (SELECT id FROM event WHERE PaymentInID IS null AND PaymentOutID IS null) "
			+ " AND ServiceType= (SELECT [ID] FROM Compliance_ServiceTypeEnum WHERE code= ?) "
			+ " AND entityid=? and entitytype=? AND (status='PASS' OR status='FAIL' OR status='PENDING') ORDER BY id DESC "),
	
	GET_PREVIOUS_SANCTION_DETAILS_BY_ENTITY_ID("SELECT id,entityid,providerResponse,summary,Status "
			+ " FROM eventservicelog WHERE ServiceType= 7 AND entityid=? and entitytype=? ORDER BY id DESC "),
	

	GET_PREVIOUS_FRAUGSTER_DETAILS_BY_ENTITY_ID("SELECT id,entityid,providerResponse,summary,Status" 
	        + " FROM eventservicelog WHERE ServiceType= 6 AND entityid=? and entitytype=? ORDER BY id DESC "),
	
	GET_PREVIOUS_BLACKLIST_DETAILS_BY_ENTITY_ID("SELECT id,entityid,providerResponse,summary,Status" 
	        + " FROM eventservicelog WHERE ServiceType= 3 AND entityid=? and entitytype=? ORDER BY id DESC "),
	
	GET_KYC_SERVICE_STATUS_BY_ENTITY_ID(" SELECT TOP 1 id,providerResponse,summary,Status, "
			+ " (SELECT Name From contact WHERE id=entityid) As ContactName  FROM eventservicelog "
			+ " WHERE ServiceType= 1 AND entityid=? and entitytype=? ORDER BY id DESC "),
	
	GET_FUNDS_OUT_SERVICE_STATUS_BY_ENTITY_ID("SELECT TOP 1 providerResponse,summary,status FROM  eventservicelog WHERE  servicetype = "
			+ "(SELECT [ID] FROM compliance_servicetypeenum WHERE code = ?) AND entityid = ? AND entitytype = ? "
			+ "AND (status='PASS' OR status='FAIL' OR status='PENDING') ORDER  BY id DESC"),
	
	GET_FUNDS_IN_SERVICE_STATUS_BY_ENTITY_ID("SELECT TOP 1 Status  FROM eventservicelog "
			+ " WHERE eventid IN (SELECT id FROM event WHERE PaymentOutID IS null AND PaymentInID=?) "
			+ " AND ServiceType= (SELECT [ID] FROM Compliance_ServiceTypeEnum WHERE code= ?)  "
			+ " AND entityid=? AND entitytype=? ORDER BY id DESC "),
	
	GET_FUNDS_IN_CUSTOMCHECK_SERVICE_STATUS_BY_ENTITY_ID("SELECT TOP 1  * FROM Eventservicelog eventLog "
			+ " WHERE ServiceType= (SELECT [ID] FROM Compliance_ServiceTypeEnum WHERE code= ?) "
			+ " AND EventId IN (SELECT id FROM Event WHERE PaymentInId=? AND PaymentoutId IS NULL ) ORDER BY ID DESC"),
	
	GET_FUNDS_OUT_CUSTOMCHECK_SERVICE_STATUS_BY_ENTITY_ID("SELECT TOP 1  * FROM Eventservicelog eventLog "
			+ " WHERE ServiceType= (SELECT [ID] FROM Compliance_ServiceTypeEnum WHERE code= ? ) "
			+ " AND EventId IN (SELECT id FROM Event WHERE PaymentoutId=? AND PaymentInId IS NULL ) ORDER BY ID DESC"),
	
	GET_NUMBER_OF_CONTACT_IN_ACCOUNT("SELECT count(id) FROM CONTACT WHERE AccountID = ?"),
	
	IS_CONTACT_ON_WATCHLIST("SELECT StopPaymentOut,StopPaymentIn FROM ContactWatchList cw JOIN WatchList wl ON wl.id = cw.reason WHERE cw.contact = ?"),
	
	IS_BENE_USED_BY_OTHER_CONTACT("SELECT p.contactID FROM [PaymentOut] p JOIN [PaymentOutAttribute] pa ON p.ID = pa.ID "+
			"WHERE p.ContactID <> ? AND [BeneficiaryFirstName]= ? AND [BeneficiaryLastName] = ? AND [BeneficiaryCountry] = ?"),
	
	UPDATE_FUNDS_OUT_STATUS("UPDATE [PaymentOut] SET [ComplianceStatus]=(SELECT [ID] FROM [PaymentComplianceStatusEnum] WHERE [Status]=?), [BlacklistStatus]=?, [FraugsterStatus]=?, "
			+ " [SanctionStatus]=?,[CustomCheckStatus]=?,  [ComplianceDoneOn]=?, [UpdatedBy]=?, [UpdatedOn]=?, [isOnQueue]=?, [STPFlag]=?, [InitialStatus]=?, [PaymentReferenceStatus]=?, [IntuitionClientRiskLevel]=?, [IntuitionCheckStatus]=? WHERE id=?"), //IntuitionRiskLevel added for AT-4451
	
	UPDATE_FUNDS_OUT_STATUS_FOR_UPDATE_DELETE_API("UPDATE [PaymentOut] SET [ComplianceStatus]=(SELECT [ID] FROM [PaymentComplianceStatusEnum] WHERE [Status]= ? ),"
			+ " [BlacklistStatus]=(select BlacklistStatus from [PaymentOut] where id= ? ), [FraugsterStatus]=(select FraugsterStatus from [PaymentOut] where id=? ), [SanctionStatus]= "
			+ " (select SanctionStatus from [PaymentOut] where id= ? ),[CustomCheckStatus]=(select CustomCheckStatus from [PaymentOut] where id= ? ),"
			+ " [ComplianceDoneOn]=?, [UpdatedBy]=?, [UpdatedOn]=? WHERE id=?"),
	
	INSERT_INTO_FUNDS_OUT_STATUS_REASON("INSERT INTO [PaymentOutStatusReason]([PaymentOutID], [StatusUpdateReasonID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])"+ 
		    "VALUES(?, ((SELECT [ID] FROM [StatusUpdateReason] WHERE [Module]=? AND [Reason]=?)), ?, ?,?, ?)"), 
	GET_ORG_ID("SELECT [ID] FROM [Organization] WHERE [code]=?"),
	
	GET_EVENT_SERVICE_LOG_ID("SELECT * FROM EventServiceLog WHERE ID=?"),
	
	PROFILE_GET_EVENT_SERVICE_LOG_ID_AND_SERVICE("SELECT *, (SELECT  acc.Name FROM Account acc WHERE id =(SELECT AccountId FROM Event  WHERE id=EventID)) As AccountName, "
			+ "(SELECT con.Name FROM Contact con WHERE id=EntityId) As ContactName "
			+ "FROM EventServiceLog eventLog WHERE ID=? AND ServiceType = (SELECT [ID] "
			+ "FROM Compliance_ServiceTypeEnum WHERE code= ?) AND  eventid "
			+ "IN (SELECT id FROM event WHERE PaymentInID IS null AND PaymentOutID IS null) "),
	
	FUNDS_OUT_GET_EVENT_SERVICE_LOG_ID_AND_SERVICE("select esl.*,acc.Name as AccountName,con.Name as ContactName,poa.vBankName as BankName, "
			+ " concat(poa.vBeneficiaryFirstName,' ', poa.vBeneficiaryLastName) As BeneficiaryName from EventServiceLog esl "
			+ " Left Join Event eve On eve.id=esl.eventid "
			+ " Left Join Account acc On acc.id=eve.accountid"
           	+ " Left Join Contact con On con.id=esl.entityId "
           	+ " Left Join PaymentOutAttribute poa On poa.id= eve.paymentoutid "
           	+ " where esl.id=? AND ServiceType = (SELECT [ID] "
            + " FROM Compliance_ServiceTypeEnum WHERE code= ?) AND  eventid  "
			+ " IN (SELECT id FROM event WHERE PaymentInID IS null AND PaymentOutID IS NOT null) "),
	
	FUNDS_IN_GET_EVENT_SERVICE_LOG_ID_AND_SERVICE("select esl.*,acc.Name as AccountName,con.Name as ContactName "
			+ " from EventServiceLog esl "
			+ " Left Join Event eve On eve.id=esl.eventid "
			+ " Left Join Account acc On acc.id=eve.accountid "
			+ " LEFT JOIN PaymentIn p ON eve.PaymentInID = p.id "
			+ " Left Join Contact con On con.id=p.contactid "
			+ " where esl.id=? AND ServiceType = (SELECT [ID] "
			+ " FROM Compliance_ServiceTypeEnum WHERE code= ?)"),
	
	DOES_FUNDS_OUT_EXIST("SELECT [ID] FROM [PaymentOut] WHERE [TradePaymentID]=?"),
	
	GET_FUNDS_IN_DETAILS("SELECT ac.id              AS accountid, "
			+ "       aa.attributes                 AS acattrib, "
			+ "       acs.status                    AS accountStatus, "
			+ "       c.tradecontactid              AS TradeContactID, "
			+ "       c.id                          AS contactid, "
			+ "       ca.attributes                 AS contactattrib, "
			+ "       ccs.status                    AS ContactStatus, "
			+ "       cw.id                         AS watchlistid, "
			+ "       (SELECT Count(id) FROM   contact cc WHERE  cc.accountid = ac.id) AS contacts, "
			+ "       (SELECT id from Organization where code=?) AS OrgID, "
			+ "       PIDetails.* "
			+ "FROM   (SELECT po.id          AS fundsinId, "
			+ "               poa.attributes AS PIAttrib, "
			+ "               pcs.status     AS PIComplianceStatus, "
			+ "               accountid "
			+ "        FROM   paymentin po "
			+ "               JOIN paymentinattribute poa "
			+ "                 ON po.id = poa.id "
			+ "               JOIN [paymentcompliancestatusenum] pcs "
			+ "                 ON po.compliancestatus = pcs.id "
			+ "        WHERE  po.tradepaymentid = ?) AS PIDetails "
			+ "       RIGHT JOIN [account] ac "
			+ "               ON ac.id = PIDetails.accountid "
			+ "       JOIN [accountattribute] aa "
			+ "         ON ac.id = aa.id "
			+ "       JOIN [accountcompliancestatusenum] acs "
			+ "         ON ac.compliancestatus = acs.id "
			+ "       JOIN [contact] c "
			+ "         ON c.accountid = ac.id "
			+ "       JOIN [contactattribute] ca "
			+ "         ON c.id = ca.id "
			+ "       JOIN [contactcompliancestatusenum] ccs "
			+ "         ON c.compliancestatus = ccs.id "
			+ "       LEFT JOIN [contactwatchlist] cw "
			+ "              ON c.id = cw.contact "
			+ "WHERE  tradeaccountnumber = ?"),
	
	
	GET_FUNDS_OUT_ACCOUNT_CONTACT_DETAILS("SELECT ac.id       AS accountid, "
+ " ac.AccountTMFlag, "
+ " ac.DormantFlag, "
+ " aa.attributes AS acattrib, "
+ "ac.type AS custType, "
+ " acs.status AS accountStatus, "
+ " c.tradecontactid AS TradeContactID, "
+ " c.id AS contactid, "
+ " ca.attributes AS contactattrib, "
+ " ccs.status AS ContactStatus, "
+ " cw.id AS watchlistid, "
+ " ac.LEUpdateDate AS leupdatedate,"//Add for AT-3349
+ " c.POIExists AS poiexists,"//Add for AT-3349
+ " cw.reason AS watchlistreason, "
+ " watch.reason AS watchlistreasonname, "
+ " (SELECT id from Organization where code=?) AS requestOrgID,"
+ " (SELECT Count(id) "
+ " FROM   contact cc "
+ " WHERE  cc.accountid = ac.id) AS contacts "
+ " FROM "
+ " [account] ac "
+ " "
+ " JOIN [accountattribute] aa "
+ " ON ac.id = aa.id "
+ " JOIN [accountcompliancestatusenum] acs "
+ " ON ac.compliancestatus = acs.id "
+ " JOIN [contact] c "
+ " ON c.accountid = ac.id "
+ " JOIN [contactattribute] ca "
+ " ON c.id = ca.id "
+ " JOIN [contactcompliancestatusenum] ccs "
+ " ON c.compliancestatus = ccs.id "
+ " LEFT JOIN [contactwatchlist] cw "
+ " ON c.id = cw.contact "
+ " LEFT Join WatchList watch "
+ " ON  (watch.id = cw.reason   AND watch.StopPaymentOut = 1) "
+ " WHERE  tradeaccountnumber = ? and ac.OrganizationID= "
+ " (SELECT id from Organization where code=?) ORDER BY  watch.id DESC "),
	
	GET_FUNDS_IN_ACCOUNT_CONTACT_DETAILS("SELECT  ac.id       AS accountid, "
+ " ac.AccountTMFlag  , "
+ " ac.DormantFlag  , "
+ " ac.type AS custType, "
+ " ac.OldOrganizationId, "
+ " aa.attributes AS acattrib, "
+ " acs.status AS accountStatus, "
+ " c.tradecontactid AS TradeContactID, "
+ " c.id AS contactid, "
+ " ca.attributes AS contactattrib, "
+ " ccs.status AS ContactStatus, "
+ " cw.id AS watchlistid, "
+ " cw.reason AS watchlistreason, "
+ " watch.reason AS watchlistreasonname, "
+ " (SELECT id from Organization where code=?) AS requestOrgID,"
+ " (SELECT Count(id) "
+ " FROM contact cc "
+ " WHERE  cc.accountid = ac.id) AS contacts, "
+ " ac.LEUpdateDate AS leUpdateDate,"
+ " c.POIExists AS poiExists"
+ " FROM "
+ " [account] ac "
+ " "
+ " JOIN [accountattribute] aa "
+ " ON ac.id = aa.id "
+ " JOIN [accountcompliancestatusenum] acs "
+ " ON ac.compliancestatus = acs.id "
+ " JOIN [contact] c "
+ " ON c.accountid = ac.id "
+ " JOIN [contactattribute] ca "
+ " ON c.id = ca.id "
+ " JOIN [contactcompliancestatusenum] ccs "
+ " ON c.compliancestatus = ccs.id "
+ " LEFT JOIN [contactwatchlist] cw "
+ " ON c.id = cw.contact "
+ " LEFT Join WatchList watch "
+ " ON (watch.id = cw.reason   AND watch.StopPaymentIn = 1) "
+ " WHERE  tradeaccountnumber = ? and ac.OrganizationID= "
+ " (SELECT id from Organization where code=?)"),
	
	DELETE_FUNDS_OUT("UPDATE [PaymentOut] SET [ComplianceStatus]=(SELECT ID FROM PaymentComplianceStatusEnum WHERE status=?), [Deleted]=1, [UpdatedBy]=?, [UpdatedOn]=? WHERE [TradePaymentID]=?"),
	
	GET_ACCOUNT_CREATION_TIME("SELECT [CreatedOn] FROM Account where id=?"),
	//AT-3830
	INSERT_INTO_PAYMENTS_IN("INSERT INTO [PaymentIn]([OrganizationID], [AccountID], [ContactID], [TradeContractNumber], "
			+ " [TradePaymentID], [ComplianceStatus], [ComplianceDoneOn], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy], "
			+ " [UpdatedOn], [FraugsterStatus] , [SanctionStatus], [BlacklistStatus], [CustomCheckStatus], [TransactionDate] , [isOnQueue],[STPFlag],[LegalEntityId],[DebitCardFraudCheckStatus], [IntuitionCheckStatus] ) "
			+ " VALUES((select id from Organization where code=?), ?, ?, ?, ?, (select id from PaymentComplianceStatusEnum where status=?), ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,(select id from legalEntity where code=?),?,?)"),
	
	INSERT_INTO_PAYMENTS_IN_ATTRIBUTE("INSERT INTO [PaymentInAttribute]([ID], [Version],[Attributes], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) VALUES(?,?,?,?,?,?,?)"),
	//AT-3830
	UPDATE_FUNDS_IN_STATUS("UPDATE [PaymentIn] SET [ComplianceStatus]=(SELECT [ID] FROM [PaymentComplianceStatusEnum] WHERE [Status]=?), [FraugsterStatus]= ?, [SanctionStatus]= ?, "
			+ " [BlacklistStatus] = ?, [CustomCheckStatus] = ?,[ComplianceDoneOn]=?, [UpdatedBy]=?, [UpdatedOn]=? , [isOnQueue] = ?, [STPFlag] = ?, [InitialStatus] = ?,[DebitCardFraudCheckStatus]=?, [IntuitionClientRiskLevel]=?, [IntuitionCheckStatus]=? WHERE id=?"), //IntuitionRiskLevel added for AT-4451
	
	INSERT_INTO_FUNDS_IN_STATUS_REASON("INSERT INTO [PaymentInStatusReason]([PaymentInID], [StatusUpdateReasonID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])"+ 
		    "VALUES(?, ((SELECT [ID] FROM [StatusUpdateReason] WHERE [Module]=? AND [Reason]=?)), ?, ?,?, ?)"),
	
	DOES_FUNDS_IN_EXIST("SELECT [ID] FROM [PaymentIn] WHERE [TradePaymentID]=?"),
	
	GET_FUNDSOUT_REQUEST_BY_PAYMENTOUTID("SELECT [AccountID], [ContactID], [Attributes] FROM [PaymentOut] po JOIN [PaymentOutAttribute] poa ON po.id=poa.id WHERE po.id= ?"),
	
	GET_FUNDSIN_REQUEST_BY_PAYMENTINID("SELECT [AccountID], [ContactID], [Attributes] FROM [PaymentIn] pi JOIN [PaymentInAttribute] pia ON pi.id=pia.id WHERE pi.id= ?"),
	
	GET_SANCTION_FIRST_SUMMARY("select Top 1 Summary from EventServiceLog where EntityType = ? and EntityID = ? and ServiceProvider=(select id from Compliance_ServiceProvider where Code='SANCTION_SERVICE') and [Status] in(4,1,2) order by ID asc "),
	
	GET_FRAUGSTER_FIRST_SUMMARY("select Top 1 Summary from EventServiceLog where EntityType = ? and EntityID = ? and ServiceProvider=(select id from Compliance_ServiceProvider where Code='FRAUGSTER_ONUPDATE_SERVICE') and [Status] in(4,1,2) order by ID asc "),
	
	GET_FRAUGSTER_FIRST_SIGNUP_SUMMARY("select Top 1 Summary from EventServiceLog where EntityType = ? and EntityID = ? and ServiceProvider=(select id from Compliance_ServiceProvider where Code='FRAUGSTER_SIGNUP_SERVICE') and [Status] in(1,2) order by ID asc"),
	
	//ADD for AT-3161
	GET_FRAUGSTER_SIGNUP_DETAILS("select JSON_VALUE([ProviderResponse], '$.fraugsterApproved') As fraugsterApprovedScore, UpdatedOn from EventServiceLog where EntityType = ? and EntityID = ? and ServiceProvider IN (10, 11) order by UpdatedOn DESC"),
	
	GET_ISO_COUNTRY_CODE_AND_NAMES("select code,displayname from country"),
	
	/**Query change for AT-2524*/
	GET_BROADCAST_MESSAGE_FROM_DB("declare @Id int "
			+ " select TOP 1 @Id=Id from comp.StatusBroadCastQueue where DeliveryStatus in (1,3) and RetryCount<3 order by id asc "
			+ " Select * from comp.StatusBroadCastQueue where id = @Id"),
	
	UPDATE_BROADCAST_STATUS_TO_DB("update StatusBroadCastQueue set DeliveryStatus=?,RetryCount=?,DeliveredOn=? where id=?"),
	
	/**Query added to fetch user ID by SSOUserID from User table - Vishal J*/
	GET_USER_ID_BY_SSOUSERID("SELECT id FROM [USER] WHERE SSOUserID=?"),
	
	  SAVE_INTO_BROADCAST_QUEUE("INSERT INTO [StatusBroadCastQueue]([OrganizationID], [EntityType], [AccountID], [ContactID], [PaymentInID], [PaymentOutID], [StatusJson], [DeliveryStatus], [DeliverOn], [CreatedBy], [CreatedOn]) "
	    		+ "    VALUES((SELECT ID FROM Organization WHERE code=?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
	  
	UPDATE_FUNDS_IN_SANCTION_STATUS("UPDATE [PaymentIn] SET  [SanctionStatus]= ?, [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
	
	UPDATE_FUNDS_IN_CUSTOMCHECK_STATUS("UPDATE [PaymentIn] SET  [CustomCheckStatus]= ?, [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
	
	UPDATE_FUNDS_IN_FRAUGSTER_STATUS("UPDATE [PaymentIn] SET  [FraugsterStatus]= ?, [ComplianceDoneOn]=?, [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
	
	UPDATE_FUNDS_IN_BLACKLIST_STATUS("UPDATE [PaymentIn] SET  [BlacklistStatus]= ?, [ComplianceDoneOn]=?, [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
	
	UPDATE_FUNDS_OUT_CUSTOMCHECK_STATUS("UPDATE [PaymentOut] SET  [CustomCheckStatus]= ?, [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
	
	UPDATE_FUNDS_OUT_SANCTION_STATUS("UPDATE [PaymentOut] SET  [SanctionStatus]= ?,  [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
	
	UPDATE_FUNDS_OUT_FRAUGSTER_STATUS("UPDATE [PaymentOut] SET  [FraugsterStatus]= ?, [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
	
	UPDATE_FUNDS_OUT_BLACKLIST_STATUS("UPDATE [PaymentOut] SET  [BlacklistStatus]= ?, [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
	
	GET_ACCOUNT_CONTACT_EXISTING_ID("select acc.CRMAccountID As CRMAccountID, "
			+ " acc.TradeAccountNumber As TradeAccountNumber, "
			+ " acc.TradeAccountID As TradeAccountID , "
			+ " id as accountid, "
			+ " NULL As CRMContactID, "
			+ " NULL As TradeContactID "
			+ " from Account acc "
			+ " WHERE (acc.CRMAccountID = ? OR acc.TradeAccountID= ? OR acc.TradeAccountNumber= ?  ) "
			+ " AND    acc.OrganizationID=(select id from Organization where code= ? ) "
			+ " UNION ALL  "
			+ " SELECT  NULL As CRMAccountID, "
			+ " NULL As TradeAccountNumber,  "
			+ " NULL As TradeAccountID , "
			+ " accountid, "
			+ " cc.CRMContactID As CRMContactID, "
			+ " cc.TradeContactID  As TradeContactID "
			+ " FROM  Contact cc  "
			+ " where ( cc.CRMContactID IN({CONTACT_SF_ID}) OR  cc.TradeContactID IN({TRADE_CONTACT_ID})) "
			+ " AND  cc.OrganizationID=(select id from Organization where code= ? ) "),
	
	GET_INTERNAL_RULE_SERVICE_CONTACT_STATUS("SELECT ID As contactId, BlacklistStatus, SanctionStatus, ComplianceStatus From Contact  WHERE ComplianceStatus <>1"
			+ "and ACCOUNTID= ?"),
	
	DELETE_FROM_CONTACT_WATCHLIST("DELETE FROM ContactWatchList WHERE [Reason]=(SELECT [ID] FROM WatchList WHERE Reason= ? ) AND [Contact]= ? "),
	

	GET_FUNDS_OUT_ID("SELECT po.id AS fundsoutId, poa.attributes AS POAttrib, "
			+ " pcs.status AS POComplianceStatus, po.FraugsterStatus, "
			+ " po.accountid, po.ContactID, UserAgent, "
			+ " DeviceType, DeviceName, DeviceVersion, DeviceID, "
			+ " DeviceManufacturer, OSType, BrowserName, "
			+ " BrowserVersion, BrowserLanguage, "
			+ " BrowserOnline, OSTimestamp, "
			+ " CDAppID, CDAppVersion,ScreenResolution,acc.TradeAccountNumber "
			+ "FROM paymentout po "
			+ "JOIN paymentoutattribute poa ON po.id = poa.id "
			+ "JOIN [paymentcompliancestatusenum] pcs ON po.compliancestatus = pcs.id "
			+ "LEFT JOIN Event e ON e.paymentoutid = po.id "
			+ "LEFT JOIN DeviceInfo d ON e.id = d.eventid "
			+ "JOIN Account acc ON po.accountid = acc.id "
			+ "WHERE "
			+ " po.tradepaymentid =? "
			+ " and po.OrganizationID =(SELECT id from Organization where code=?)"),
	
	GET_FUNDS_OUT_ID_FOR_BLACKLIST("SELECT po.id AS fundsoutId, "
			+ " poa.attributes AS POAttrib, "
			+ " pcs.status AS POComplianceStatus, BlacklistStatus, "
			+ " po.accountid, po.ContactID"
			+ " FROM paymentout po "
			+ " JOIN paymentoutattribute poa ON po.id = poa.id "
			+ " JOIN [paymentcompliancestatusenum] pcs ON po.compliancestatus = pcs.id "
			+ " LEFT JOIN Event e ON e.paymentoutid = po.id "
			+ " WHERE po.tradepaymentid =? "
			+ " and po.OrganizationID =(SELECT id from Organization where code=?)"),
	
	UPDATE_SANCTION_STATUS_FOR_CONTACT("UPDATE Contact SET [SanctionStatus]= ? where ID=?"),
	
	UPDATE_SANCTION_STATUS_FOR_ACCOUNT("UPDATE Account SET [SanctionStatus]= ? where ID=?"),
	
	UPDATE_BLACKLIST_STATUS_FOR_CONTACT("UPDATE Contact SET [BlacklistStatus]= ? where ID=?"),
	
	UPDATE_BLACKLIST_STATUS_FOR_ACCOUNT("UPDATE Account SET [BlacklistStatus]= ? where ID=?"),
	
	UPDATE_KYC_STATUS_FOR_CONTACT("UPDATE Contact SET [EIDStatus]= ? where ID=?"),
	
	UPDATE_FRAUGSTER_STATUS_FOR_CONTACT("UPDATE Contact SET [FraugsterStatus]= ? where ID=?"),
	
	GET_SANCTION_STATUS_BY_ENTITY_ID("SELECT TOP 1 providerResponse,summary,status FROM  eventservicelog WHERE  servicetype = 7 "
			+ " AND entityid =? AND entitytype = ? "
			+ " AND (status=1 OR status= 2 OR status=4) ORDER  BY id DESC"),

	INSERT_USER_RESOURCE_LOCK(
			"INSERT INTO [UserResourceLock] (UserID, ResourceType, ResourceID, LockReleasedOn, CreatedBy, CreatedOn, WorkflowTime ) "
			+ " VALUES ((select Id from [user] where SSOUserID=?),?,?,?,(select Id from [user] where SSOUserID=?),?,?)"),
	
	GET_FUNDS_IN_ID_FOR_FRAUGSTER("SELECT pi.id AS fundsinId, "
			+ " pia.attributes AS PIAttrib, "
			+ " pcs.status AS PIComplianceStatus, FraugsterStatus, "
			+ " pi.accountid, pi.ContactID, UserAgent, DeviceType, "
			+ " DeviceName, DeviceVersion, DeviceID, DeviceManufacturer,"
			+ " OSType, BrowserName, BrowserVersion, BrowserLanguage, "
			+ " BrowserOnline, OSTimestamp, "
			+ " CDAppID, CDAppVersion,ScreenResolution"
			+ " FROM paymentin pi "
			+ " JOIN paymentinattribute pia ON pi.id = pia.id "
			+ " JOIN [paymentcompliancestatusenum] pcs ON pi.compliancestatus = pcs.id "
			+ " LEFT JOIN Event e ON e.paymentinid = pi.id "
			+ " LEFT JOIN DeviceInfo d ON e.id = d.eventid "
			+ " WHERE pi.tradepaymentid =? "
			+ " and pi.OrganizationID =(SELECT id from Organization where code=?)"),
	
	GET_FUNDS_IN_ID("SELECT pin.id AS fundsinId, "
			+ " pia.attributes AS PIAttrib, "
			+ " pcs.status AS PIComplianceStatus, "
			+ " accountid "
			+ " FROM paymentin pin "
			+ " JOIN paymentinattribute pia "
			+ " ON pin.id = pia.id "
			+ " JOIN [paymentcompliancestatusenum] pcs "
			+ " ON pin.compliancestatus = pcs.id "
			+ " WHERE  pin.tradepaymentid = ? "
			+ " AND pin.organizationid = (SELECT id "
			+ " FROM organization "
			+ " WHERE code = ?)"),
	
	GET_FUNDS_IN_ID_FOR_BLACKLIST("SELECT  pi.id AS fundsinId, "
			+ " pia.attributes AS PIAttrib, "
			+ " pcs.status AS PIComplianceStatus, BlacklistStatus, "
			+ " pi.accountid, pi.ContactID"
			+ " FROM paymentin pi "
			+ " JOIN paymentinattribute pia ON pi.id = pia.id "
			+ " JOIN [paymentcompliancestatusenum] pcs ON pi.compliancestatus = pcs.id "
			+ " LEFT JOIN Event e ON e.paymentinid = pi.id "
			+ " WHERE "
			+ " pi.tradepaymentid =? "
			+ " and pi.OrganizationID = (SELECT id from Organization where code=?)"),
	
	GET_COUNTRY_DISPLAY_NAME("SELECT DisplayName From Country WHERE Code=?"),
	/*GET_SANCTION_FAILED_RECORDS("WITH SelectedIDs AS (SELECT "
			+ "		payOut.id AS pID, payOut.OrganizationID,  ContractNumber, contactid,TradePaymentID "
			+ "	FROM "
			+ "		paymentout payOut JOIN Account a ON payOut.accountid=a.id "
			+ "	JOIN userresourcelock ur ON "
			+ "		ur.resourceID = payOut.id "
			+ "		and ur.lockreleasedon is NULL "
			+ "		AND resourceType = 1 "
			+ "		 "
			+ "	WHERE "
			+ "		payOut.deleted = 0 AND payOut.ComplianceStatus = 4 AND payOut.sanctionstatus<>1 "
			+ "		AND payOut.FraugsterStatus = 1 AND payOut.BlacklistStatus = 1 AND payOut.CustomCheckStatus = 1 "
			+ " AND a.WatchListStatus=1 ) "
			+ "select "
			+ "	EventID, accountid, c.EntityID,c.EntityType, paymentOutId, c.ID ESLid, org.Code,ContractNumber, contactid, TradePaymentID, c.status "
			+ "from "
			+ "SelectedIDs si JOIN Event e ON e.paymentOutId=si.pID "
			+ "JOIN EventServiceLog c ON c.eventid = e.id "
			+ "JOIN Organization org ON si.OrganizationID = org.id "
			+ "where "
			+ " c.ServiceType=7 AND c.Status IN (1,2,4) AND paymentOutId > ? " 
			+ " order by  EventID DESC"),*/
	
	
	GET_SANCTION_FAILED_RECORDS("WITH SelectedIDs AS (SELECT {BATCH_SIZE} "
			+ " payOut.id AS pID, payOut.OrganizationID, ContractNumber, contactid ,TradePaymentID "
			+ " FROM "
			+ " paymentout payOut JOIN Account a ON payOut.accountid=a.id "
			+ " WHERE "
			+ " payOut.deleted = 0 AND payOut.ComplianceStatus = 4 AND payOut.sanctionstatus<>1 "
			+ " AND payOut.FraugsterStatus = 1 AND payOut.BlacklistStatus = 1 AND payOut.CustomCheckStatus = 1 AND a.PayOutWatchListStatus=1  "
			+ " {CREATEDON} {PAYMENT_OUT_ID} "
			+ " ), "
			+ " uData AS( select "
			+ " cc.EventID, accountid, cc.EntityID, 3 AS EntityType, paymentOutId, cc.ID ESLid, org.Code,ContractNumber, contactid,TradePaymentID, cc.status, cc.updatedon , "
			+ " ROW_NUMBER() OVER (PARTITION BY cc.EntityType ORDER BY cc.updatedon DESC) ROWNUM "
			+ " from SelectedIDs si JOIN Event e ON e.paymentOutId=si.pID "
			+ " LEFT JOIN EventServiceLog cc ON cc.eventid = e.id AND cc.ServiceType=7 AND cc.entityType=3 "
			+ " JOIN Organization org "
			+ " ON si.OrganizationID = org.id "
			+ " "
			+ " UNION "
			+ " select bn.EventID, accountid, bn.EntityID, 2 AS EntityType, paymentOutId, "
			+ " bn.ID ESLid, org.Code,ContractNumber, contactid,TradePaymentID, bn.status, bn.updatedon , "
			+ " ROW_NUMBER() OVER (PARTITION BY bn.EntityType ORDER BY bn.updatedon DESC) ROWNUM "
			+ " from SelectedIDs si "
			+ " JOIN Event e ON e.paymentOutId=si.pID "
			+ " LEFT JOIN EventServiceLog bn ON bn.eventid = e.id AND bn.ServiceType=7 AND bn.entityType=2 "
			+ " JOIN Organization org ON si.OrganizationID = org.id "
			+ " "
			+ " UNION "
			+ " select "
			+ " bk.EventID, accountid, bk.EntityID, 4 AS EntityType, paymentOutId, bk.ID ESLid, org.Code,ContractNumber, contactid,TradePaymentID, bk.status, bk.updatedon, "
			+ " ROW_NUMBER() OVER (PARTITION BY bk.EntityType ORDER BY bk.updatedon DESC) ROWNUM "
			+ " from "
			+ " SelectedIDs si JOIN Event e ON e.paymentOutId=si.pID "
			+ " LEFT JOIN EventServiceLog bk ON bk.eventid = e.id AND bk.ServiceType=7 AND bk.entityType=4 "
			+ " JOIN Organization org ON si.OrganizationID = org.id "
			+ " "
			+ ") "
			+ "SELECT u.*, pa.vTradeBeneficiaryID, pa.vTradeBankID From Udata u JOIN PaymentOutAttribute pa ON u.paymentoutid=pa.id "
			+ "WHERE ROWNUM=1 "
			+ "ORDER BY ContractNumber"),
	
	GET_OTHER_ENTITY_STATUS("SELECT status, EntityType FROM EventServiceLog c JOIN Event E ON C.eventid=e.id WHERE paymentOutId = ? AND EntityType <> ? AND ServiceType=7 ORDER BY c.EntityType, c.id DESC"),
	
	GET_SANCTION_STATUS("SELECT sanctionstatus, sb.PaymentOutID FROM paymentout po LEFT JOIN StatusBroadCastQueue sb ON po.id=sb.PaymentOutID  WHERE po.id = ? AND sb.PaymentOutID IS NULL "),
	
	GET_WATCHLIST_REASON_LIST_FOR_PAYMENTOUT_AND_PAYMENTIN("SELECT MAX(category) AS Category, MAX(CAST(StopPaymentIn AS INT)) AS StopPaymentIn, MAX(CAST(StopPaymentOut AS INT)) AS StopPaymentOut"
			+ " FROM WatchList w JOIN ContactWatchList cw ON w.id=cw.reason JOIN Contact c ON cw.contact=c.id "
			+ " WHERE c.accountid= ? OR w.Reason IN(#) "),
	
	//new for AT-2986
	GET_WATCHLIST_REASON_LIST_FOR_PAYMENTOUT_AND_PAYMENTIN_WITH_CON("SELECT MAX(category) AS Category, MAX(CAST(StopPaymentIn AS INT)) AS StopPaymentIn, MAX(CAST(StopPaymentOut AS INT)) AS StopPaymentOut"
				+ " FROM WatchList w JOIN ContactWatchList cw ON w.id=cw.reason JOIN Contact c ON cw.contact=c.id "
				+ " WHERE c.id = ? "),
	
	GET_FRAUGSTERSCHEDULAR_DATA_FROM_DB("select ID,AtlasID,FraugsterTransactionID,Status,AsyncStatus,AsyncStatusDate,SyncStatus from FraugsterSchedularData where Delivered is NULL"),
	
	UPDATE_FRAUGSTER_DATA("UPDATE FraugsterSchedularData SET Delivered=? where ID = ?"),
	
	DELETE_FUNDS_IN("UPDATE [PaymentIn] SET [ComplianceStatus]=(SELECT ID FROM PaymentComplianceStatusEnum WHERE status=?), [UpdatedBy]=?, [UpdatedOn]=?,[Deleted]=1 WHERE [TradePaymentID]=? AND OrganizationID= (SELECT ID FROM Organization WHERE Code=?)"),
	
	GET_IS_LOCK_RELEASED_ON("SELECT LockReleasedOn FROM UserResourceLock WHERE ResourceType=2 AND ResourceID = (SELECT ID FROM PaymentIn WHERE TradePaymentID=? AND OrganizationID= (SELECT ID FROM Organization WHERE Code=?))"),
	 
	UPDATE_LOCK_RELEASED_ON("UPDATE UserResourceLock SET LockReleasedOn=? WHERE ResourceType=2 AND ResourceID = (SELECT ID FROM PaymentIn WHERE TradePaymentID=? AND OrganizationID= (SELECT ID FROM Organization WHERE Code=?))"),
	
	INSERT_FRAUGSTER_SCHEDULAR_DATA("INSERT INTO FraugsterSchedularData (AtlasID, FraugsterTransactionID, Status, "
			+ "AsyncStatus, SyncStatus, AsyncStatusDate, CreatedBy) VALUES(?,?,?,?,?,?,?)"),
	
	GET_PREVIOUS_CONTACT_INACTIVE_REASON("SELECT TOP 1 sur.Reason from ProfileStatusReason psr JOIN StatusUpdateReason sur ON sur.id = psr.StatusUpdateReasonID " 
	+ "and psr.contactid = ? order by psr.id DESC"),
	
	INSERT_PAYMENT_IN_ACTIVITY_LOG_STP("INSERT INTO [PaymentInActivityLog] (PaymentInID, ActivityBy, [Timestamp], CreatedBy, CreatedOn)"
			+ " VALUES(?,?,?,?,?)"),
	
	INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL_STP("INSERT INTO [PaymentInActivityLogDetail]([ActivityID], [ActivityType], [Log], [CreatedBy], [CreatedOn]) VALUES(?, (SELECT id FROM ActivityTypeEnum WHERE Module = ? AND Type = ?), ?, ?, ?)"),
	
	INSERT_PAYMENT_OUT_ACTIVITY_LOG_STP("INSERT INTO [PaymentOutActivityLog] (PaymentOutID, ActivityBy, [Timestamp], CreatedBy, CreatedOn)"
			+ " VALUES(?,?,?,?,?)"),
	
	INSERT_PAYMENT_OUT_ACTIVITY_LOG_DETAIL_STP("INSERT INTO [PaymentOutActivityLogDetail]([ActivityID], [ActivityType], [Log], [CreatedBy], [CreatedOn]) VALUES(?, (SELECT id FROM ActivityTypeEnum WHERE Module = ? AND Type = ?), ?, ?, ?)"),
	
	INSERT_PROFILE_ACTIVITY_LOG_STP("INSERT INTO [ProfileActivityLog] ([Timestamp], ActivityBy, OrganizationID, AccountID, ContactID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn) "
			+ " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
	
	INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP("INSERT INTO [ProfileActivityLogDetail] VALUES(?, (SELECT id FROM ActivityTypeEnum WHERE Module = ? AND Type = ?), ?, ?, ?, ?, ?)"),
	
	GET_WATCHLIST_REASONS("SELECT w.reason FROM WatchList w JOIN ContactWatchList cw ON w.id=cw.reason JOIN Contact c ON cw.contact=c.id WHERE Accountid = ?"), 
	
	GET_DETAILS_FOR_SERVICE_FAILED_REG_CFX (" WITH se AS( "
			+" SELECT MAX(id) eventid, accountid FROM Event WHERE EventType<4 AND accountid=? "
			+" GROUP BY accountid "
			+" ) "
			+" SELECT "
			+" acc.id AS accountId, acc.AccountTMFlag, " //AT-4288 (AccountTMFlag)
			+" accattr.attributes AS AccountAttrib, " 
			+" conattr.attributes AS ContactAttrib,  "
			+" se.eventid AS EventID, "
			+" acc.OrganizationID as OrgId, "
			+" (SELECT code from Comp.Organization where id = acc.OrganizationID) as OrgCode, "
			+" (SELECT Status from ContactComplianceStatusEnum acs where acs.id = con.compliancestatus )  AS ContactComplianceStatus, "
			+" (SELECT Status from AccountComplianceStatusEnum acs where acs.id = acc.compliancestatus )  AS AccountComplianceStatus, "
			+" acc.FraugsterStatus, con.FraugsterStatus AS ContactFraugsterStatus, acc.SanctionStatus,"
			+ " acc.BlacklistStatus, con.BlacklistStatus AS ContactBlacklistStatus, "
			+ " acc.EidStatus, con.EidStatus AS ContactEidStatus, acc.PayInWatchListStatus, acc.PayOutWatchListStatus, " 
			+" acc.id, acc.tradeaccountnumber AS TradeAccNo, UserAgent,con.id AS contactid , DeviceType,  "
			+" DeviceName, DeviceVersion,"
			+" DeviceID, DeviceManufacturer, "
			+" OSType, BrowserName, BrowserVersion, BrowserLanguage, BrowserOnline, OSTimestamp,  "
			+" CDAppID, CDAppVersion, se.eventid  "
			+" FROM account acc  "
			+" JOIN accountattribute accattr ON acc.id = accattr.id " 
			+" JOIN contact con ON acc.id = con.accountid  "
			+" JOIN contactattribute conattr ON con.id = conattr.id " 
			+" LEFT JOIN se ON acc.id=se.accountid "
			+" LEFT JOIN DeviceInfo d ON se.eventid = d.eventid " 
			+" LEFT JOIN EventServiceLog esl on esl.id = d.eventid "
			+" WHERE acc.id = ? "),
	
	GET_DETAILS_FOR_SERVICE_FAILED_REG_PFX (" WITH se AS( " 
			 +" SELECT MAX(id) eventid, accountid FROM Event WHERE EventType<4 AND accountid= ? " 
			 +" GROUP BY accountid "
			 +" ) " 
			 +" SELECT " 
			 +" con.id AS contactId, " 
			 +" accattr.attributes AS AccountAttrib, " 
			 +" conattr.attributes AS ContactAttrib, "  
			 +" se.eventid AS EventID, " 
			 +" acc.OrganizationID as OrgId, acc.AccountTMFlag, " //AT-4288 (AccountTMFlag)
			+" (SELECT code from Comp.Organization where id = acc.OrganizationID) as OrgCode, "
			 +" (SELECT Status from ContactComplianceStatusEnum acs where acs.id = con.compliancestatus )  AS ContactComplianceStatus, "
			 +" (SELECT Status from AccountComplianceStatusEnum acs where acs.id = acc.compliancestatus )  AS AccountComplianceStatus, "
			 + " con.FraugsterStatus, "
			 +" con.SanctionStatus, con.BlacklistStatus, con.EidStatus,  con.PayInWatchListStatus, con.PayOutWatchListStatus," //get contact pay IN/OUT watchlist status for AT-2986
			 +" acc.tradeaccountnumber AS TradeAccNo, UserAgent,acc.id AS accountid , DeviceType, "  
			 +" DeviceName, DeviceVersion, "
			 +" DeviceID, DeviceManufacturer, "
			 +" OSType, BrowserName, "  
			 +" BrowserVersion, BrowserLanguage, BrowserOnline, "
			 +" OSTimestamp, CDAppID, CDAppVersion " 
			 +" FROM  contact con"
			 +" JOIN account acc ON con.accountid  =acc.id " 
			 +" JOIN accountattribute accattr ON acc.id = accattr.id " 
			 +" JOIN contactattribute conattr ON con.id = conattr.id " 
			 +" LEFT JOIN se ON acc.id=se.accountid " 
			 +" LEFT JOIN DeviceInfo d ON se.eventid = d.eventid " 
			 +" WHERE con.id = ?"),//change where clause (accountId -> contactId)- for AT-4289
	
	GET_DETAILS_FOR_FAILED_FUNDS_OUT("SELECT "
			+ " po.id AS fundsoutId, "
			+ " poa.attributes AS POAttrib, "
			+ " po.Compliancestatus AS POComplianceStatus, po.FraugsterStatus, po.SanctionStatus, po.BlacklistStatus, po.CustomCheckStatus, po.PaymentReferenceStatus, "
			+ " po.accountid, acc.tradeaccountnumber, po.ContactID, UserAgent, DeviceType, "
			+ " DeviceName, DeviceVersion, DeviceID, "
			+ " DeviceManufacturer, OSType, BrowserName, "
			+ " BrowserVersion, BrowserLanguage, "
			+ " BrowserOnline, OSTimestamp, CDAppID, CDAppVersion, ScreenResolution "
			+ "FROM paymentout po "
			+ "JOIN paymentoutattribute poa ON po.id = poa.id "
			+ "LEFT JOIN Event e ON e.paymentoutid = po.id "
			+ "LEFT JOIN DeviceInfo d ON e.id = d.eventid "
			+ "JOIN Account acc ON po.accountid = acc.id "
			+ "WHERE po.id = ? and e.eventType = 7" ),
	
	
	GET_ESL_FOR_SERVICE_FAILED_FUNDSOUT("SELECT es.EntityType, es.ServiceType, es.ProviderResponse, "
			+ " es.Status, es.summary, "
			+ " es.EntityID, es.EntityVersion, es.eventID "
			+ " FROM EventServiceLog es "
			+ " JOIN Event e On es.eventid=e.id "
			+ " AND e.eventtype=7 WHERE e.paymentoutid=?"),
	
	GET_ESL_FOR_SERVICE_FAILED_REG("SELECT es.EntityType, es.ServiceType, es.ProviderResponse, es.Status, es.summary, es.EntityID, es.EntityVersion, es.eventID "
			+ "FROM EventServiceLog es WHERE EventID = ?"),
	
	//UPDATE_FAILED_RECHECK("UPDATE ReproccessFailed set Status = ? where TransId = ? and TransType = ? "),
	UPDATE_FAILED_RECHECK("UPDATE ReproccessFailed set Status = ?, RetryCount = 1 where TransId = ? and TransType = ? "),//AT-4289
	
	GET_DETAILS_FOR_FAILED_FUNDS_IN("SELECT " 
			+ " pin.id AS fundsinId, "
			+ " pina.attributes AS PIAttrib, "
			+ " pin.Compliancestatus AS PIComplianceStatus, pin.FraugsterStatus, pin.SanctionStatus, pin.BlacklistStatus, pin.CustomCheckStatus, "
			+ " pin.accountid, acc.tradeaccountnumber, pin.ContactID, UserAgent, DeviceType, "
			+ " DeviceName, DeviceVersion, DeviceID, "
			+ " DeviceManufacturer, OSType, "
			+ " BrowserName, BrowserVersion, BrowserLanguage, BrowserOnline, OSTimestamp, "
			+ " CDAppID, CDAppVersion, ScreenResolution "
			+ " FROM paymentin pin "
			+ " JOIN paymentinattribute pina ON pin.id = pina.id "
			+ " LEFT JOIN Event e ON e.paymentinid = pin.id "
			+ " LEFT JOIN DeviceInfo d ON e.id = d.eventid "
			+ " JOIN Account acc ON pin.accountid = acc.id "
			+ " WHERE pin.id = ? "),
	
	GET_ESL_FOR_SERVICE_FAILED_FUNDSIN("SELECT es.EntityType, es.ServiceType, es.ProviderResponse, "
			+ "es.Status, es.summary, es.EntityID, es.EntityVersion, es.eventID "
			+ "FROM EventServiceLog es "
			+ "JOIN Event e On es.eventid=e.id "
			+ "AND e.eventtype=6 WHERE e.paymentinid=?"),
	
	GET_ESL_FOR_SERVICE_FAILURE_OF_CONTACT("SELECT TOP 1 es.entitytype, "
			+ "             es.servicetype, "
			+ "             es.providerresponse, "
			+ "             es.status, "
			+ "             es.summary, "
			+ "             es.entityid, "
			+ "             es.entityversion, "
			+ "             es.eventid "
			+ "FROM   eventservicelog es "
			+ "       JOIN event e "
			+ "         ON e.id = es.eventid "
			+ "WHERE  es.servicetype = (SELECT id "
			+ "                         FROM   compliance_servicetypeenum "
			+ "                         WHERE  code = ?) "
			//+ "       AND es.eventid = ? " //commented for AT-4289
			+ "       AND e.accountid = ? "
			+ "       AND es.entitytype = 3 "
			//+ "       AND e.eventtype < 4 " //commented for AT-5436
			+ " AND e.eventtype IN (1,2,3,4,5,15,19,22) "
			+ "       AND es.entityid = ? "
			+ "ORDER  BY es.id DESC"),
	
	GET_ESL_FOR_SERVICE_FAILURE_OF_ACCOUNT("SELECT TOP 1 es.entitytype, "
			+ "             es.servicetype, "
			+ "             es.providerresponse, "
			+ "             es.status, "
			+ "             es.summary, "
			+ "             es.entityid, "
			+ "             es.entityversion, "
			+ "             es.eventid "
			+ "FROM   eventservicelog es "
			+ "       JOIN event e "
			+ "         ON e.id = es.eventid "
			+ "WHERE  es.servicetype = (SELECT id "
			+ "                         FROM   compliance_servicetypeenum "
			+ "                         WHERE  code = ?) "
			//+ "       AND es.eventid = ? " //commented for AT-4289
			+ "       AND e.accountid = ? "
			+ "       AND es.entitytype = 1 "
			//+ "       AND e.eventtype < 4 " //commented for AT-5436
			+ " AND e.eventtype IN (1,2,3,4,5,15,19,22) "
			+ "ORDER  BY es.id DESC"),
	
	GET_ACCOUNT_DETAILS_FROM_CONTACTID("select accountid AS AccountId , a.type from contact c JOIN Account a on a.ID = c.AccountID where c.id = ?"),
	
	GET_ACCOUNT_DETAILS_FROM_ACCOUNTID("SELECT id AS AccountId ,type from Account where id = ?"),
	
	UPDATE_CONTACT_STATUS_FOR_CFX("UPDATE [Contact] SET ComplianceStatus=(SELECT Id FROM ContactComplianceStatusEnum WHERE Status=?) "
			+"WHERE ID=?"),
	
	GET_ACCOUNT_DETAILS_BY_ACC_SF_ID("select acc.id AS AccountId, acc.Name AS AccountName, acc.CreatedOn AS ARegisteredDate, acc.ComplianceDoneOn AS ARegistrationInDate, acc.ComplianceExpiry AS AComplianceExpiry,"
			+ "acc.crmAccountID AS AccountSFId, acc.Version AS Version, acc.OrganizationID AS OrgID, c.id as ContactID, c.ComplianceDoneOn AS CRegisteredDate, c.CreatedOn AS CRegistrationInDate, acc.ComplianceExpiry AS CComplianceExpiry," + 
			"CASE acc.[Type] WHEN 2 THEN 'PFX' WHEN 1 THEN 'CFX' ELSE 'CFX (Etailer)' END AS CustType, " + 
			"acc.ComplianceStatus AS AccountComplianceStatus, acc.TradeAccountID AS TradeAccountId, " + 
			"ca.Attributes AS contactAttributes, c.crmcontactid as ContactSFID, C.EIDSTATUS, c.SanctionStatus, c.FraugsterStatus, "+
			"c.BlacklistStatus, c.CustomCheckStatus, c.ComplianceStatus AS ContactComplianceStatus, c.POI_NEEDED " + 
			"FROM Account acc JOIN contact c ON acc.id=c.accountid JOIN ContactAttribute ca ON c.id=ca.id " + 
			"where acc.crmAccountid = ? "),
	
	UPDATE_ACCOUNT_STATUS("UPDATE Account SET AccountStatus=?, ComplianceStatus=?, ComplianceDoneOn=?, ComplianceExpiry=?, UpdatedBy=?, UpdatedOn=?, PayInWatchListStatus=?, PayOutWatchListStatus=?, DocumentVerifiedStatus=? where CRMAccountID = ?"),
	
	UPDATE_CONTACT_STATUS("UPDATE Contact SET ComplianceStatus=?, ComplianceDoneOn=?, ComplianceExpiry=?, UpdatedBy=?, UpdatedOn=?,  PayInWatchListStatus=?, PayOutWatchListStatus=? where CRMContactID = ?"),//ADD Pay IN/OUT watchlist status for AT-2986
	
	INSERT_PROFILE_ACTIVITY_LOG_WITH_ORG_ID("INSERT INTO [ProfileActivityLog] ([Timestamp], ActivityBy, OrganizationID, AccountID, ContactID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn) "
			+ " VALUES( ?, ?, (select id from Organization where Code =? ), ?, ?, ?, ?, ?, ?)"),
	
	GET_CONTACT_DETAILS_FOR_DELETE("SELECT con.ID AS ContactID, con.CRMContactID,ca.[Attributes] AS ConAttrib, pin.id AS PaymentInId, "
			+ " po.id AS PaymentOutId, con.TradeContactID, acc.CrmAccountID, acc.TradeAccountID, acc.ID AS AccountID, acc.Version, "
			+ "acc.Type AS custType, (SELECT ID from Organization where Name = ?) AS OrgID "
			+ " FROM Contact con "
			+ " JOIN ContactAttribute ca ON con.id = ca.id "
			+ " JOIN Account acc ON con.AccountID = acc.id "
			+ " LEFT JOIN PaymentIn pin ON con.id = pin.ContactID "
			+ " LEFT JOIN PaymentOut po ON con.id = po.ContactID "
			+ " WHERE con.CRMContactID = ? AND con.TradeContactID = ? "
			+ " AND con.AccountID = (SELECT ID FROM ACCOUNT a WHERE a.CRMAccountID = ? "
			+ " AND a.TradeAccountID = ?)"),
	
	DELETE_CONTACT("UPDATE [Contact] SET [Deleted]=1, [UpdatedBy]=?, [UpdatedOn]=? WHERE [ID]=?"),
	
	GET_CONTACT_DETAILS_FOR_STATUSBROADCAST_IN_MQ("select c.CRMContactID as ContactSFID,c.TradeContactID from Contact c JOIN account a on a.ID = c.AccountID WHERE a.CRMAccountID = ?"),
	
	GET_OLD_ORG_ID("SELECT OldOrganizationId FROM Account WHERE Id = ?"),
	
	UPDATE_ONFIDO_STATUS_FOR_ACCOUNT("UPDATE Account SET [DocumentVerifiedStatus] = ? WHERE ID = (SELECT AccountID FROM Comp.Event WHERE ID = ?)"),
	
	GET_CURRENT_OPERATION_RECENT_DATA_FROM_EVENT_SERVICE_LOG("SELECT esl.ID as 'EventServiceLogID',esl.EntityType,esl.ServiceType,esl.Status,e.EventType,esl.EntityVersion,e.AccountID,esl.EntityID " + 
			"FROM Event e join EventServiceLog esl on e.ID = esl.EventID WHERE e.ID = ?"),
	
	INSERT_UPDATE_INTO_EVENT_SERVICE_LOG_SUMMARY("DECLARE @EntityId bigint;\n" + 
			"DECLARE @EntityType smallint;\n" + 
			"DECLARE @ServiceType smallint;\n" + 
			"DECLARE @ServiceStatus smallint;\n" + 
			"DECLARE @EventServiceLogID bigint;\n" + 
			"SET @EntityId = ?;\n" + 
			"SET @EntityType = ?;\n" + 
			"SET @ServiceType = ?;\n" + 
			"SET @EventServiceLogID = ?;\n" + 
			"SET @ServiceStatus = ?;\n" + 
			"MERGE INTO Comp.EventServiceLogSummary WITH (HOLDLOCK) AS Dst " + 
			"USING ( SELECT @EntityId * 10000 + @EntityType * 100 + @ServiceType AS EntityServiceHash ) AS Src " + 
			"ON Src.EntityServiceHash = Dst.[EntityServiceHash] " + 
			"WHEN MATCHED THEN " + 
			"UPDATE SET EventServiceLogID = @EventServiceLogID, Status = @ServiceStatus " + 
			"WHEN NOT MATCHED THEN " + 
			"INSERT (EntityServiceHash,Status,EventServiceLogID) " + 
			"VALUES (EntityServiceHash,@ServiceStatus,@EventServiceLogID);"),
	
	GET_RECENT_SPECIFIC_EVENT_SERVICE_DATA("With Selected AS (SELECT EventServiceLogID , Status, EntityServiceHash " + 
			"FROM Comp.EventServiceLogSummary " + 
			"WHERE EntityServiceHash= ?) " + 
			"SELECT s.EntityServiceHash, esl.Summary , esl.Status , esl.ProviderResponse " + 
			"FROM Comp.EventServiceLog esl JOIN Selected s ON esl.ID = s.EventServiceLogID;"),
	
	GET_USER_TABLE_IDS_NAMES("SELECT ID,SSOUserID AS UserName FROM [User];"),
	
	GET_COUNTRY_STATE_DATA("SELECT DisplayName AS StateName, Code FROM CountryState "),//AT-3719
	
	UPDATE_CONTACT_WITHOUT_KYC_AND_SANCTION_COMPLIANCE_STATUS("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,FraugsterStatus=?,BlacklistStatus=? , CustomCheckStatus=?,isOnQueue = ? , PayInWatchListStatus=?, PayOutWatchListStatus=? WHERE Id=?"),
	
	UPDATE_CONTACT_WITHOUT_KYC_COMPLIANCE_STATUS("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,FraugsterStatus=?,SanctionStatus=?,BlacklistStatus=? , CustomCheckStatus=?,isOnQueue = ? , PayInWatchListStatus=?, PayOutWatchListStatus=? WHERE Id=?"),
	
	UPDATE_CONTACT_WITHOUT_SANCTION_COMPLIANCE_STATUS("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,EIDStatus=?,FraugsterStatus=?,BlacklistStatus=? , CustomCheckStatus=?,isOnQueue = ? , PayInWatchListStatus=?, PayOutWatchListStatus=? WHERE Id=?"),
	
	UPDATE_CONTACT_WITHOUT_FRAUDPREDICT_COMPLIANCE_STATUS("UPDATE Contact SET [ComplianceStatus]=(SELECT [ID] FROM [ContactComplianceStatusEnum ] WHERE Status = ?), [UpdatedBy]=?, [UpdatedOn]=?,EIDStatus=?,SanctionStatus=?,BlacklistStatus=? , CustomCheckStatus=?,isOnQueue = ? , PayInWatchListStatus=?, PayOutWatchListStatus=? WHERE Id=?"),

	GET_ZERO_CLEAR_FUNDSIN_FOR_ACCOUNT("SELECT acc.FirstFundsInEDDCheck as FirstCreditCheck, " + 
			"(SELECT Count(pin.id)  FROM PaymentIn pin JOIN Account a on a.id = pin.AccountID  " + 
			"where a.TradeAccountNumber = ? and pin.ComplianceStatus = 1) As NumOfFundsInClear " + 
			"from Account acc where acc.TradeAccountNumber = ?"),//AT-3346
	
	GET_EDD_COUNTRIES("SELECT cou.Code,cou.EUFirstPayInEDD from Country cou;"),//AT-3346
	
	GET_NUMBER_OF_FI_CLEAR_AFTER_LEDATE("SELECT Count(pin.id) as NumOfFundsInClear FROM PaymentIn pin " + 
			"JOIN Account a on a.id = pin.AccountID " + 
			"Join Contact c on c.id = pin.ContactID " + 
			"where a.TradeAccountNumber = ? and c.TradeContactID = ? " + 
			"and pin.ComplianceStatus = 1 AND pin.CreatedOn >= ?"),//AT-3349
	
	GET_NUMBER_OF_FO_CLEAR_AFTER_LEDATE("SELECT Count(po.id) as NumOfFundsOutClear FROM PaymentOut po " + 
			"JOIN Account a on a.id = po.AccountID " + 
			"Join Contact c on c.id = po.ContactID " + 
			"where a.TradeAccountNumber = ? and c.TradeContactID = ? " + 
			"and po.ComplianceStatus = 1 AND po.CreatedOn >= ?"),//AT-3349

	UPDATE_FIRST_EDD_CHECK_IN_ACCOUNT("UPDATE Account SET FirstFundsInEDDCheck = ? where TradeAccountNumber = ?"),
	
	//Add for AT-3470
	GET_CONTACT_WATCHLIST_FUNDS_IN("SELECT wl.ID, wl.Reason , wl.StopPaymentIn FROM ContactWatchList cwl " + 
			"JOIN WatchList wl on wl.ID = cwl.Reason " + 
			"WHERE cwl.Contact = (SELECT pi.ContactID FROM  PaymentIn pi where pi.ID = ?)"),
	
	//Add for AT-3470
	GET_CONTACT_WATCHLIST_FUNDS_OUT("SELECT wl.ID, wl.Reason , wl.StopPaymentOut FROM ContactWatchList cwl " + 
			"JOIN WatchList wl on wl.ID = cwl.Reason " + 
			"WHERE cwl.Contact = (SELECT po.ContactID FROM  PaymentOut po where po.ID = ?)"),
		//AT-3830
	UPDATE_FUNDS_IN_STATUS_WITHOUT_INITITAL_STATUS("UPDATE [PaymentIn] SET [ComplianceStatus]=(SELECT [ID] FROM [PaymentComplianceStatusEnum] WHERE [Status]=?), [FraugsterStatus]= ?, [SanctionStatus]= ?, "
			+ " [BlacklistStatus] = ?, [CustomCheckStatus] = ?,[ComplianceDoneOn]=?, [UpdatedBy]=?, [UpdatedOn]=? , [isOnQueue] = ?, [STPFlag] = ?,[DebitCardFraudCheckStatus]=?, [IntuitionCheckStatus]=? WHERE id=?"),
	
	UPDATE_FUNDS_OUT_STATUS_WITHOUT_INITITAL_STATUS("UPDATE [PaymentOut] SET [ComplianceStatus]=(SELECT [ID] FROM [PaymentComplianceStatusEnum] WHERE [Status]=?), [BlacklistStatus]=?, [FraugsterStatus]=?, "
			+ " [SanctionStatus]=?,[CustomCheckStatus]=?,  [ComplianceDoneOn]=?, [UpdatedBy]=?, [UpdatedOn]=?, [isOnQueue]=?, [STPFlag]=?, [PaymentReferenceStatus]=?, [IntuitionCheckStatus]=? WHERE id=?"),
	
	//Add for AT-3738
	GET_ZERO_CLEAR_FUNDSIN_FOR_CDINC_ACCOUNT("SELECT Count(pin.id) AS NumOfFundsInClear " + 
			" FROM PaymentIn pin " + 
			" JOIN Account a ON a.id = pin.AccountID " + 
			" where a.TradeAccountNumber = ? AND pin.ComplianceStatus = 1"),
	
	//Added for AT-3658
		GET_FUNDS_OUT_ID_FOR_PAYMENT_REFERENCE("SELECT po.id AS fundsoutId, "
				+ " poa.attributes AS POAttrib, "
				+ " pcs.status AS POComplianceStatus, PaymentReferenceStatus, "
				+ " po.accountid, po.ContactID"
				+ " FROM paymentout po "
				+ " JOIN paymentoutattribute poa ON po.id = poa.id "
				+ " JOIN [paymentcompliancestatusenum] pcs ON po.compliancestatus = pcs.id "
				+ " LEFT JOIN Event e ON e.paymentoutid = po.id "
				+ " WHERE po.tradepaymentid =? "
				+ " and po.OrganizationID = (SELECT id from Organization where code=?)"),
		
		//Added for AT-3658
		UPDATE_FUNDS_OUT_PAYMENT_REFERENCE_STATUS("UPDATE [PaymentOut] SET  [PaymentReferenceStatus]= ?, [UpdatedBy]=?, [UpdatedOn]=?  WHERE id=?"),
		
		//Add for AT-3987
		GET_CONTACT_WATCHLIST("SELECT wl.Reason from ContactWatchList cwl Join WatchList wl ON wl.ID = cwl.Reason where cwl.Contact = ?"),
		
		GET_TM_MQ_MESSAGE_FROM_DB("declare @Id int;"
				+ "DECLARE @COUNT int; "
				+ "SET @COUNT = ?; "
				+ " select TOP 1 @Id=Id from comp.TransactionMonitoringMQ where DeliveryStatus in (1,3) and RetryCount<@COUNT order by id asc "
				+ " Select ID, (SELECT o.Code from Organization o WHERE ID = OrganizationID) AS OrganizationCode , "
				+ " RequestType, AccountID , ContactID, PaymentInID, PaymentOutID, RequestJson, RetryCount, CreatedBy ,CreatedOn, isPresent, "
				+ "(SELECT a.DormantFlag FROM Account a WHERE a.ID= AccountID) as DormantFlag "
				+ " from comp.TransactionMonitoringMQ where id = @Id"),
		
		UPDATE_TM_MQ_STATUS_TO_DB("update TransactionMonitoringMQ set DeliveryStatus=?,RetryCount=?,DeliveredOn=? where id=?"),
		
		INSERT_TM_MQ_PROFILE_ACTIVITY_LOG_STP("INSERT INTO [ProfileActivityLog] ([Timestamp], [ActivityBy], [OrganizationID], [AccountID], [ContactID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) "
				+ " VALUES( ?, ?, (SELECT id as orgID from Organization where code=?), ?, ?, ?, ?, ?, ?)"),
		
		INSERT_TM_MQ_PAYMENT_IN_ACTIVITY_LOG_STP("INSERT INTO [PaymentInActivityLog] ([PaymentInID], [ActivityBy], [Timestamp], [CreatedBy], [CreatedOn]) "
				+ " VALUES(?, ?, ?, ?, ?)"),
		
		INSERT_TM_MQ_PAYMENT_OUT_ACTIVITY_LOG_STP("INSERT INTO [PaymentOutActivityLog] ([PaymentOutID], [ActivityBy], [Timestamp], [CreatedBy], [CreatedOn]) "
				+ " VALUES(?, ?, ?, ?, ?)"),
		
		//Added for AT-4185
		UPDATE_ACC_TM_FLAG("UPDATE Account SET # IntuitionRiskLevel = ?, UpdatedBy = ?, UpdatedOn = ? WHERE ID = ? "),
		
		//update accountTMFlag if signup in TMMQ flow else update as it is
		UPDATE_ACC_TM_FLAG_MQ("UPDATE Account SET AccountTMFlag = CASE AccountTMFlag WHEN 0 THEN # else AccountTMFlag END WHERE ID = ? "),
		
		//Added for AT-4185
		DELETE_DELIVERED_RECORD_FROM_TMMQ("DELETE FROM TransactionMonitoringMQ WHERE DeliveryStatus = 2 And AccountID = ?"),
	
		//AT-4181
		UPDATE_INTUITION_STATUS_PFX("SELECT a.ID, a.TradeAccountID, a.TradeAccountNumber, a.CRMAccountID, a.ComplianceDoneOn, a.AccountStatus, a.Version, a.AccountTMFlag, "
				+ "JSON_VALUE(aa.[Attributes], '$.reg_date_time') AS regDateTime, a.IntuitionRiskLevel,"
				+ "c.ID AS ContactID, c.CRMContactID, c.TradeContactID, c.ComplianceStatus "
				+ "FROM Account a "
				+ "JOIN AccountAttribute aa ON a.ID = aa.ID "
				+ "JOIN contact c ON a.id = c.accountid "
				+ "WHERE c.ID = ?"),
		
		UPDATE_INTUITION_STATUS_CFX("SELECT a.ID, a.TradeAccountID, a.TradeAccountNumber, a.CRMAccountID, a.ComplianceDoneOn, a.AccountStatus, a.Version, a.AccountTMFlag, "
				+ "JSON_VALUE(aa.[Attributes], '$.reg_date_time') AS regDateTime, a.IntuitionRiskLevel,"
				+ "c.ID AS ContactID, c.CRMContactID, c.TradeContactID, c.ComplianceStatus "
				+ "FROM Account a "
				+ "JOIN AccountAttribute aa ON a.ID = aa.ID "
				+ "JOIN contact c ON a.id = c.accountid "
				+ "WHERE c.AccountID = ?"),
		
		UPDATE_ACCOUNT_INTUITION_RISK_LEVEL("UPDATE Account SET IntuitionRiskLevel = ?, UpdatedBy = ?, UpdatedOn = ? WHERE ID = ?"),
		
		//Add for AT-4239
		GET_CONTACT_STATUS_UPDATE_REASON("SELECT sur.Reason FROM ProfileStatusReason psr JOIN StatusUpdateReason sur on sur.ID = psr.StatusUpdateReasonID WHERE psr.ContactID = ?"),
		
		GET_ACCOUNT_CONTACT_DETAILS_FOR_INTUITION("SELECT a.ID as accID, c.ID as conID, a.LegalEntity, a.OrganizationID, "
				+ "acse.Status as accComplianceStatus, ccse.Status as conComplianceStatus, "
				+ "aa.[Attributes] as accAtt, ca.[Attributes] as conAtt, "
				+ "aa.Version as accVersion, a.CreatedOn, "
				+ "a.BlacklistStatus as accBlacklistStatus, "
				+ "a.SanctionStatus as accSanctionStatus, "
				+ "a.FraugsterStatus as accFraugsterStatus, "
				+ "a.AccountTMFlag, "
				+ "a.IntuitionRiskLevel, "
				+ "(SELECT o.Code from Organization o WHERE ID = a.OrganizationID) AS OrganizationCode, "
				+ "c.EIDStatus as conEIDStatus, "
				+ "c.FraugsterStatus as conFraugsterStatus, "
				+ "c.SanctionStatus as conSanctionStatus, "
				+ "c.BlacklistStatus as conBlacklistStatus, "
				+ "c.CustomCheckStatus as conCustomCheckStatus, "
				+ "c.Version as version, "
				+ "a.LegacyTradeAccountNumber " // Added for AT-5393
				+ "FROM Account a "
				+ "JOIN AccountAttribute aa on a.ID = aa.ID "
				+ "JOIN Contact c on c.AccountID = a.ID "
				+ "JOIN ContactAttribute ca on ca.ID = c.ID "
				+ "JOIN AccountComplianceStatusEnum acse on a.ComplianceStatus = acse.ID  "
				+ "JOIN ContactComplianceStatusEnum ccse on c.ComplianceStatus = ccse.ID "
				+ "WHERE a.TradeAccountNumber = ? "),
		
		GET_FRAUGSTER_CONTACT_STATUS_FOR_INTUITION("SELECT TOP 1 esl.Status, JSON_VALUE([Summary],'$.fraugsterApproved') as fraugsterApproved, esl.UpdatedOn "
				+ "from  EventServiceLog esl "
				+ "JOIN Event e on esl.EventID = e.ID and e.PaymentInID IS NULL AND e.PaymentOutID IS NULL "
				+ "WHERE esl.EntityType = 3 and esl.ServiceType = 6 and esl.EntityID = ? "
				+ "ORDER BY esl.ID DESC"),
		
		GET_COMPLIANCE_LOG_FOR_INTUITION("SELECT TOP 1 pal.Comments FROM ProfileActivityLog pal "
				+ "JOIN ProfileActivityLogDetail pald on pal.ID = pald.ActivityID and pald.Log LIKE '%Compliance Log Added%' "
				+ "WHERE pal.AccountID = ? ORDER BY pal.CreatedOn DESC "),
		
		GET_ONFIDO_CONTACT_STATUS_FOR_INTUITION("SELECT TOP 1 esl.EntityID, JSON_VALUE(esl.Summary ,'$.status') AS ESL_OnfidoStatus from EventServiceLog esl "
				+ "JOIN Event e on esl.EventID = e.ID and e.PaymentInID IS NULL AND e.PaymentOutID IS NULL "
				+ "WHERE esl.ServiceType = 13 and esl.EntityID = ? ORDER BY esl.ID DESC"),
		
		//AT-4451
		GET_FUNDS_IN_FOR_DETAILS_INTUITION("SELECT pi.ID as payInId,"
				+ " pi.AccountID as accountid,"
				+ " pi.ContactID as ContactID,"
				+ " pia.[Attributes] as PIAttrib,"
				+ " pi.FraugsterStatus,"
				+ " pi.BlacklistStatus ,"
				+ " pi.SanctionStatus ,"
				+ " pi.CustomCheckStatus ,"
				+ " pi.ComplianceStatus as PIComplianceStatus,"
				+ " pi.InitialStatus ,"
				+ " a.TradeAccountNumber ,"
				+ " a.AccountTMFlag ,"
				+ " (SELECT o.Code from Organization o WHERE ID = a.OrganizationID) AS OrganizationCode ," //AT-4749
				+ " c.TradeContactID,"
				+ " a.Version as AccountVersion,"
				+ " JSON_VALUE(aa.[Attributes],'$.company.etailer') as Etailer" //AT-5310
				+ " FROM PaymentIn pi "
				+ " JOIN PaymentInAttribute pia ON"
				+ " pi.id = pia.id"
				+ " JOIN Account a on"
				+ " a.ID = pi.AccountID"
				+ " JOIN AccountAttribute aa ON a.ID = aa.ID"
				+ " JOIN Contact c on"
				+ " c.ID = pi.ContactID"
				+ " WHERE pi.TradePaymentID = ?"),
		
		//AT-4451
		GET_FUNDS_IN_COUNTRY_CHECK_STATUS_FOR_INTUITION("SELECT"
				+ " TOP 1 esl.Status,"
				+ " JSON_VALUE([Summary],"
				+ " '$.status') as CountryCheckStatus"
				+ " from"
				+ " EventServiceLog esl"
				+ " JOIN Event e on"
				+ " esl.EventID = e.ID"
				+ " and e.PaymentInID IS NOT NULL"
				+ " WHERE"
				+ " esl.EntityType = 3"
				+ " and esl.ServiceType = 8"
				+ " and esl.EntityID = ?"
				+ " ORDER BY"
				+ " esl.ID DESC"),
		
		//AT-4451
		GET_STATUS_UPDATE_REASON_FOR_PAYMENT_IN_INTUITION("SELECT"
				+ " sur.Reason"
				+ " FROM"
				+ " PaymentInStatusReason pisr"
				+ " JOIN StatusUpdateReason sur on"
				+ " sur.ID = pisr.StatusUpdateReasonID"
				+ " WHERE"
				+ " pisr.PaymentInID = ?"),
		
		//AT-4451
		GET_FUNDS_OUT_DETAILS_FOR_INTUITION("SELECT"
				+ " po.ID as payOutId,"
				+ " po.AccountID as accountid,"
				+ " po.ContactID as ContactID,"
				+ " poa.[Attributes] as POAttrib,"
				+ " po.FraugsterStatus,"
				+ " po.BlacklistStatus ,"
				+ " po.CustomCheckStatus ,"
				+ " po.PaymentReferenceStatus ,"
				+ " po.ComplianceStatus ,"
				+ " po.InitialStatus ,"
				+ " a.TradeAccountNumber ,"
				+ " a.AccountTMFlag ,"
				+"  (SELECT o.Code from Organization o WHERE ID = a.OrganizationID) AS OrganizationCode," //AT-4749
				+ " c.TradeContactID,"
				+ " a.Version as AccountVersion,"
				+ " JSON_VALUE(aa.[Attributes],'$.company.etailer') as Etailer," //AT-5310
				+ " JSON_VALUE(aa.[Attributes],'$.affiliate_name') as AffiliateName " //AT-5454
				+ " FROM"
				+ " PaymentOut po"
				+ " JOIN PaymentOutAttribute poa ON"
				+ " po.id = poa.id"
				+ " JOIN Account a on"
				+ " a.ID = po.AccountID"
				+ " JOIN AccountAttribute aa ON a.ID = aa.ID"
				+ " JOIN Contact c on"
				+ " c.ID = po.ContactID"
				+ " WHERE"
				+ " po.TradePaymentID = ?"),
		
		//AT-4451
		GET_FUNDS_OUT_COUNTRY_CHECK_STATUS_FOR_INTUITION("SELECT"
				+ " TOP 1 esl.Status,"
				+ " JSON_VALUE([Summary],"
				+ " '$.status') as CountryCheckStatus"
				+ " from"
				+ " EventServiceLog esl"
				+ " JOIN Event e on"
				+ " esl.EventID = e.ID"
				+ " AND e.PaymentOutID IS NOT NULL"
				+ " WHERE"
				+ " esl.EntityType = 2"
				+ " and esl.ServiceType = 8"
				+ " and esl.EntityID = ?"
				+ " ORDER BY"
				+ " esl.ID DESC"),
		
		//AT-4451
		GET_FUNDS_OUT_CONTACT_SANCTION_STATUS("SELECT"
				+ " TOP 1 esl.Status,"
				+ " JSON_VALUE([Summary],"
				+ " '$.status') as ContactSanctionStatus"
				+ " from"
				+ " EventServiceLog esl"
				+ " JOIN Event e on"
				+ " esl.EventID = e.ID"
				+ " AND e.PaymentOutID IS NOT NULL"
				+ " WHERE"
				+ " esl.EntityType = 3"
				+ " and esl.ServiceType = 7"
				+ " and esl.EntityID = ? and e.PaymentOutID = ?"
				+ " ORDER BY"
				+ " esl.ID DESC"),
		
		//AT-4451
		GET_FUNDS_OUT_BENEFICIARY_SANCTION_STATUS("SELECT"
				+ " TOP 1 esl.Status,"
				+ " JSON_VALUE([Summary],"
				+ " '$.status') as BeneficierySanctionStatus"
				+ " from"
				+ " EventServiceLog esl"
				+ " JOIN Event e on"
				+ " esl.EventID = e.ID"
				+ " AND e.PaymentOutID IS NOT NULL"
				+ " WHERE"
				+ " esl.EntityType = 2"
				+ " and esl.ServiceType = 7"
				+ " and esl.EntityID = ? and e.PaymentOutID = ?"
				+ " ORDER BY"
				+ " esl.ID DESC"),
		
		//AT-4451
		GET_FUNDS_OUT_BANK_SANCTION_STATUS("SELECT"
				+ " TOP 1 esl.Status,"
				+ " JSON_VALUE([Summary],"
				+ " '$.status') as BankSanctionStatus"
				+ " from"
				+ " EventServiceLog esl"
				+ " JOIN Event e on"
				+ " esl.EventID = e.ID"
				+ " AND e.PaymentOutID IS NOT NULL"
				+ " WHERE"
				+ " esl.EntityType = 4"
				+ " and esl.ServiceType = 7"
				+ " and esl.EntityID = ? and e.PaymentOutID = ?"
				+ " ORDER BY"
				+ " esl.ID DESC"),
		
		//AT-4451
		GET_STATUS_UPDATE_REASON_FOR_PAYMENT_OUT_INTUITION("SELECT"
				+ " sur.Reason"
				+ " FROM"
				+ " PaymentOutStatusReason posr "
				+ " JOIN StatusUpdateReason sur on"
				+ " sur.ID = posr.StatusUpdateReasonID"
				+ " WHERE"
				+ " posr.PaymentOutID = ?"),
		
		//AT-4475
		GET_PAYMENT_REFERENCE_MATCHED_KEYWORD_FOR_INTUITION("SELECT"
				+ " TOP 1 "
				+ " JSON_VALUE([Summary],"
				+ " '$.sanctionText') as SanctionText"
				+ " from"
				+ " EventServiceLog esl"
				+ " JOIN Event e on"
				+ " esl.EventID = e.ID"
				+ " AND e.PaymentOutID IS NOT NULL"
				+ " WHERE"
				+ " esl.EntityType = 2"
				+ " and esl.ServiceType = 15"
				+ " and esl.EntityID = ?"
				+ " ORDER BY"
				+ " esl.ID DESC"),
		
		//AT-4558
		UPDATE_PAYMENT_IN_INTUITION_CLIENT_RISK_LEVEL("UPDATE PaymentIn SET IntuitionClientRiskLevel = ?, UpdatedBy = ?, UpdatedOn = ? WHERE ID = ?"),
		
		//AT-4558
		UPDATE_PAYMENT_OUT_INTUITION_CLIENT_RISK_LEVEL("UPDATE PaymentOut SET IntuitionClientRiskLevel = ?, UpdatedBy = ?, UpdatedOn = ? WHERE ID = ?"),
		
		//AT-4666
		GET_COUNTRY_SHORT_CODE("select code,shortcode from country"),
		
		UPDATE_PAYMENT_IN_INTUITION_CHECK_STATUS("UPDATE PaymentIn SET IntuitionCheckStatus = ?, UpdatedBy = ?, UpdatedOn = ? WHERE ID = ?"),
		
		UPDATE_PAYMENT_OUT_INTUITION_CHECK_STATUS("UPDATE PaymentOut SET IntuitionCheckStatus = ?, UpdatedBy = ?, UpdatedOn = ? WHERE ID = ?"),
		
		GET_FUNDS_IN_ACCOUNT_DETAILS_FOR_DELETE_OPI("SELECT  ac.id AS accountid, "
				+ " ac.type AS custType, "
				+ " ac.OrganizationID, "
				+ " aa.attributes AS acattrib,"
				+ " pi2.ID AS payInId "
				+ " FROM "
				+ " [account] ac "
				+ " JOIN [accountattribute] aa "
				+ " ON ac.id = aa.id "
				+ " JOIN PaymentIn pi2 ON pi2.AccountID = ac.ID "
				+ " WHERE  tradeaccountnumber = ? and pi2.TradePaymentID = ?"
				+ " and ac.OrganizationID= "
				+ " (SELECT id from Organization where code=?)"),
		
		UPDATE_PAYMENT_IN_STATUS("UPDATE [PaymentIn] SET [UpdatedBy]=?, [UpdatedOn]=? , [ComplianceStatus] = ? WHERE id = ?"),
		
		GET_PAYMENT_STATUS("Select ComplianceStatus from # WHERE ID = ? "),
		
		GET_ALL_TM_MQ_MESSAGE_FROM_DB(" Select TOP 60 ID, (SELECT o.Code from Organization o WHERE ID = OrganizationID) AS OrganizationCode , \r\n"
				+ " RequestType, AccountID , ContactID, PaymentInID, PaymentOutID, RequestJson, RetryCount, CreatedBy ,CreatedOn, isPresent \r\n"
				+ " from comp.TransactionMonitoringMQ where DeliveryStatus in (1,3) and RetryCount<? "),
		
		//AT-4773
		GET_ACCOUNT_ID_USING_TRADE_ACCOUNT_NUMBER("SELECT a.ID FROM Comp.Account a WHERE a.TradeAccountNumber = ?"),
		
		SAVE_INTO_TRANSACTION_MONITORING_MQ("INSERT INTO [TransactionMonitoringMQ]( [OrganizationID], [RequestType], [AccountID], [ContactID], [PaymentInID], [PaymentOutID], [RequestJson], [DeliveryStatus], [DeliverOn], [CreatedBy], [CreatedOn], [isPresent]) "
	    		+ "    VALUES((SELECT ID FROM Organization WHERE code=?),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
		
		GET_ACCOUNTID_FROM_TRADEACCOUNTNUMBER("SELECT a.ID, o.Code, c.ID AS contactId, a.TradeAccountID, a.AccountTMFlag FROM Account a JOIN Organization o ON a.OrganizationID = o.ID "
				+ "JOIN Contact c ON a.ID = c.AccountID WHERE a.TradeAccountNumber=? AND c.TradeContactID = ?"),
	    
		INSERT_INTO_CARD_ATTRIBUTE("INSERT INTO CardAttribute ([CardID], [AccountID], [Attributes], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn], [CardStatus], [ContactID]) VALUES(?, ?, ?, ?, ?, ?, ?,?,?)"),
		
		UPDATE_INTO_CARD_ATTRIBUTE("UPDATE CardAttribute SET [Attributes]=?, [UpdatedBy]=?, [UpdatedOn]=?, [CardStatus]=? WHERE CardID=?"),
	
		GET_CARD_DETAILS("SELECT * FROM CardAttribute WHERE CardID=?"),
	
		GET_CARD_DETAILS_BY_ACCOUNT_ID("SELECT * FROM CardAttribute WHERE AccountID=?"),
		
		GET_PAYMENT_STATUS_BY_PAYMENT_TRADE_ID("SELECT p.ComplianceStatus FROM Comp.# p WHERE p.TradePaymentID = ?"),
	
		UPDATE_INTO_ACCOUNT_TMFLAG("UPDATE Account SET AccountTMFlag = ?, UpdatedBy = ?, UpdatedOn = ? WHERE ID = ? "), //AT-5127
	
		AUDIT_FAILED_KAFKA_INSERT_QUERY("INSERT INTO [KafkaFailedTxRequestAudit]([MessageId], [MessageContent], [Topic], [Status], [CreatedOn], [UpdatedOn], [ErrorMessage]) VALUES(?, ?, ?, ?, ?, ?, ?)"),
		
		GET_FAILED_KAFKA_TX_QUERY("SELECT AuditId, MessageId, MessageContent, Topic, Status, CreatedOn, UpdatedOn, ErrorMessage from [KafkaFailedTxRequestAudit]  where status = ? "),		
	    
		GET_PAYMENT_IN_TX_DETAILS_QUERY("SELECT pi.ID as id, "
				+ "org.id as organizationID,  "
				+ "org.Code as organizationCode, "
				+ "org.Name as organizationName, "
				+ "acnt.ID as accountID , "
				+ "acnt.Name as accountName, "
				+ "acnt.TradeAccountID as tradeAccountId, "
				+ "acnt.CRMAccountID as cRMAccountID, "
				+ "acnt.TradeAccountNumber as tradeAccountNumber, "
				+ "cnt.Id as contactID , "
				+ "cnt.Name as contactName, "
				+ "pi.TradeContractNumber as tradeContractNumber , "
				+ "pi.TradePaymentID as tradePaymentID , "
				+ "pi.CreatedBy as createdBy, "
				+ "cusr.SSOUserID as createdBySSOUserId , "
				+ "pi.CreatedOn as createdOn ,"
				+ "pi.UpdatedBy as updatedBy , "
				+ "uusr.SSOUserID as updatedBySSOUserId , "
				+ "pi.UpdatedOn as updatedOn, "
				+ "pi.TransactionDate as transactionDate , "
				+ "acntCmpStsEnum.Description as description ,  "
				+ "acntCmpStsEnum.ID as complianceStatusId ,  "
				+ "acntCmpStsEnum.Status as complianceStatus , "
				+ "acnt.ComplianceDoneOn as complianceDoneOn, "
				+ "fraudSrvSts.ID as fraugsterStatusId, "
				+ "fraudSrvSts.Status as fraugsterStatus, "
				+ "santionSrvSts.ID as sanctionStatusId, "
				+ "santionSrvSts.Status as sanctionStatus, "
				+ "blckListSrvSts.ID as blacklistStatusId, "
				+ "blckListSrvSts.Status as blacklistStatus, "
				+ "customChkSrvSts.ID as customCheckStatusId, "
				+ "customChkSrvSts.Status as customCheckStatus, "
				+ "pi.IsOnQueue as isOnQueue, "
				+ "pi.stpFlag as sTPFlag, "
				+ "pi.LegacyTradePaymentId as legacyTradePaymentId , "
				+ "pi.LegacyTradeContractNumber as legacyTradeContractNumber , "
				+ "le.ID as legalEntityID, "
				+ "le.code as legalEntityCode,  "
				+ "le.name as legalEntityName, "
				+ "pi.InitialStatus as initialStatus , "
				+ "debitFraudSrvSts.ID as debitCardFraudCheckStatusId, "
				+ "debitFraudSrvSts.Status as debitCardFraudCheckStatus, "
				+ "pi.intuitionClientRiskLevel as intuitionClientRiskLevel,  "
				+ "intuitionSrvSts.Id as intuitionCheckStatusId, "
				+ "intuitionSrvSts.Status as intuitionCheckStatus, "
				+ "pi.ComplianceDoneOn as paymentClearedOn , "
				+ "pial.ID as activityID , "
				+ "pial.ActivityBy as activityBy , "
				+ "actbyusr.SSOUserID as activityByUserId , "
				+ "pial.Timestamp activityTimestamp , "
				+ "pial.Comments as activityComments, "
				+ "pial.CreatedBy as activityCreatedBy ,  "
				+ "actcrbyusr.SSOUserID as activityCreatedByUserId , "
				+ "pial.CreatedOn activityCreatedOn, "
				+ "piald.ActivityType activityType,  "
				+ "ate.Module activityTypeModule , "
				+ "ate.Type activityTypeValue, "
				+ "piald.CreatedOn as activityDetailsCreatedOn, "
				+ "piald.Log as activityLog, "
				+ "pi.Deleted as deleted  "
				+ "FROM  [PaymentIn] pi  "
				+ "LEFT OUTER JOIN   [PaymentInActivityLog] pial "
				+ "on pial.PaymentInID =pi.ID "
				+ "  AND pial.ID = ( "
				+ " SELECT  MAX(ID) "
				+ " FROM [PaymentInActivityLog] z "
				+ " WHERE z.PaymentInID = pial.PaymentInID "
				+ " ) "
				+ "LEFT OUTER JOIN   [PaymentInActivityLogDetail] piald "
				+ "on piald.ActivityID=pial.ID "
				+ "LEFT OUTER JOIN   [Organization] org  "
				+ "on org.id = pi.OrganizationID "
				+ "LEFT OUTER JOIN   [Account] acnt "
				+ "on acnt.ID = pi.AccountId "
				+ "LEFT OUTER JOIN   [Contact] cnt "
				+ " on cnt.ID=pi.ContactId "
				+ "LEFT OUTER JOIN   [User] cusr "
				+ "on cusr.ID=pi.CreatedBy "
				+ "LEFT OUTER JOIN   [User] uusr "
				+ "on uusr.ID=pi.UpdatedBy "
				+ "LEFT OUTER JOIN   [User] actbyusr "
				+ "on actbyusr.ID=pial.ActivityBy  "
				+ "LEFT OUTER JOIN   [User] actcrbyusr "
				+ "on actcrbyusr.ID=pial.CreatedBy "
				+ "LEFT OUTER JOIN   [AccountComplianceStatusEnum] acntCmpStsEnum "
				+ "on acntCmpStsEnum.ID= acnt.ComplianceStatus   "
				+ "LEFT OUTER JOIN   [ServiceStatusEnum] fraudSrvSts "
				+ "on fraudSrvSts.ID = pi.FraugsterStatus  "
				+ "LEFT OUTER JOIN   [ServiceStatusEnum] santionSrvSts "
				+ "on santionSrvSts.ID = pi.SanctionStatus  "
				+ "LEFT OUTER JOIN   [ServiceStatusEnum] blckListSrvSts "
				+ "on blckListSrvSts.ID = pi.BlacklistStatus "
				+ "LEFT OUTER JOIN   [ServiceStatusEnum] customChkSrvSts "
				+ "on customChkSrvSts.ID = pi.CustomCheckStatus  "
				+ "LEFT OUTER JOIN   [ServiceStatusEnum] debitFraudSrvSts "
				+ "on debitFraudSrvSts.ID = pi.DebitCardFraudCheckStatus "
				+ "LEFT OUTER JOIN   [ServiceStatusEnum] intuitionSrvSts "
				+ "on intuitionSrvSts.ID = pi.IntuitionCheckStatus "
				+ "LEFT OUTER JOIN   [LegalEntity] le "
				+ "on le.id = pi.LegalEntityID "
				+ "LEFT OUTER JOIN   [Country] country "
				+ "on country.ID= cnt.country "
				+ "LEFT OUTER JOIN   [ActivityTypeEnum] ate  "
				+ "on ate.id=piald.activityType "
				+ "where pi.TradePaymentID = ?  "),
		
		GET_PAYMENT_OUT_TX_DETAILS_QUERY("SELECT po.ID as id,"
				+ " org.id as organizationID, "
				+ " org.Code as organizationCode, "
				+ " org.Name as organizationName, "
				+ " acnt.ID as accountID , "
				+ " acnt.Name as accountName, "
				+ " acnt.TradeAccountID as tradeAccountId, "
				+ " acnt.CRMAccountID as cRMAccountID, "
				+ " acnt.TradeAccountNumber as tradeAccountNumber, "
				+ " cnt.Id as contactID , "
				+ " cnt.Name as contactName, "
				+ " po.ContractNumber as tradeContractNumber ,  "
				+ " po.TradePaymentID as tradePaymentID , "
				+ " po.CreatedBy as createdBy, "
				+ " cusr.SSOUserID as createdBySSOUserId , "
				+ " po.CreatedOn as createdOn ,"
				+ " po.UpdatedBy as updatedBy , "
				+ " uusr.SSOUserID as updatedBySSOUserId , "
				+ " po.UpdatedOn as updatedOn, "				
				+ " po.TransactionDate as transactionDate , "
				+ " acntCmpStsEnum.Description as description ,  "
				+ " acntCmpStsEnum.ID as complianceStatusId ,  "
				+ " acntCmpStsEnum.Status as complianceStatus , "
				+ " acnt.ComplianceDoneOn as complianceDoneOn, "
				+ " fraudSrvSts.ID as fraugsterStatusId, "
				+ " fraudSrvSts.Status as fraugsterStatus, "
				+ " santionSrvSts.ID as sanctionStatusId, "
				+ " santionSrvSts.Status as sanctionStatus, "
				+ " blckListSrvSts.ID as blacklistStatusId, "
				+ " blckListSrvSts.Status as blacklistStatus, "
				+ " customChkSrvSts.ID as customCheckStatusId, "
				+ " customChkSrvSts.Status as customCheckStatus, "
				+ " po.IsOnQueue as isOnQueue, "
				+ " po.STPFlag as sTPFlag, "
				+ " po.LegacyTradePaymentId as legacyTradePaymentId , "
				+ " po.LegacyTradeContractNumber as legacyTradeContractNumber , "
				+ " le.ID as legalEntityID, "
				+ " le.code as legalEntityCode,  "
				+ " le.name as legalEntityName, "
				+ " po.InitialStatus as initialStatus , "
				+ " po.intuitionClientRiskLevel as intuitionClientRiskLevel,  "
				+ " intuitionSrvSts.Id as intuitionCheckStatusId, "
				+ " intuitionSrvSts.Status as intuitionCheckStatus, "
				+ " paymentSrvSts.Id as paymentReferenceStatusId, "
				+ " paymentSrvSts.Status as paymentReferenceStatus, "
				+ " po.ComplianceDoneOn as paymentClearedOn , "
				+ " poal.ID as activityID ,  "
				+ " poal.ActivityBy as activityBy , "
				+ " actbyusr.SSOUserID as activityByUserId , "
				+ " poal.Timestamp activityTimestamp , "
				+ " poal.Comments as activityComments, "
				+ " poal.CreatedBy as activityCreatedBy ,  "
				+ " actcrbyusr.SSOUserID as activityCreatedByUserId , "
				+ " poal.CreatedOn activityCreatedOn, "
				+ " poald.ActivityType activityType,  "
				+ " ate.Module activityTypeModule , "
				+ " ate.Type activityTypeValue, "
				+ " poald.CreatedOn as activityDetailsCreatedOn, "
				+ " poald.Log as activityLog, "
				+ " po.Deleted as deleted  "
				+ " FROM  [PaymentOut] po  "
				+ " LEFT OUTER JOIN   [PaymentOutActivityLog] poal "
				+ " on poal.PaymentOutID =po.ID "
				+ "  AND poal.ID = ( "
				+ "		 SELECT  MAX(ID) "
				+ "		 FROM [PaymentOutActivityLog] z "
				+ "		 WHERE z.PaymentOutID = poal.PaymentOutID "
				+ "		  ) "
				+ "LEFT OUTER JOIN   [PaymentOutActivityLogDetail] poald "
				+ "on poald.ActivityID=poal.ID "
				+ " LEFT OUTER JOIN  [Organization] org  "
				+ " on org.id = po.OrganizationID "
				+ " LEFT OUTER JOIN   [Account] acnt "
				+ " on acnt.ID = po.AccountId "
				+ " LEFT OUTER JOIN   [Contact] cnt "
				+ " on cnt.ID=po.ContactId "
				+ " LEFT OUTER JOIN   [User] cusr "
				+ " on cusr.ID=po.createdBy "
				+ "LEFT OUTER JOIN   [User] uusr "
				+ "on uusr.ID=po.UpdatedBy "
				+ "LEFT OUTER JOIN   [User] actbyusr "
				+ "on actbyusr.ID=poal.ActivityBy  "
				+ "LEFT OUTER JOIN   [User] actcrbyusr "
				+ "on actcrbyusr.ID=poal.CreatedBy "
				+ " LEFT OUTER JOIN   [AccountComplianceStatusEnum] acntCmpStsEnum "
				+ " on acntCmpStsEnum.ID= acnt.ComplianceStatus  "
				+ " LEFT OUTER JOIN   [ServiceStatusEnum] fraudSrvSts "
				+ " on fraudSrvSts.ID = po.FraugsterStatus  "
				+ " LEFT OUTER JOIN   [ServiceStatusEnum] santionSrvSts "
				+ " on santionSrvSts.ID = po.SanctionStatus  "
				+ " LEFT OUTER JOIN   [ServiceStatusEnum] blckListSrvSts "
				+ " on blckListSrvSts.ID = po.BlacklistStatus "
				+ " LEFT OUTER JOIN   [ServiceStatusEnum] customChkSrvSts "
				+ " on customChkSrvSts.ID = po.CustomCheckStatus  "
				+ " LEFT OUTER JOIN   [ServiceStatusEnum] intuitionSrvSts "
				+ " on intuitionSrvSts.ID = po.IntuitionCheckStatus "
				+ " LEFT OUTER JOIN   [ServiceStatusEnum] paymentSrvSts "
				+ " on paymentSrvSts.ID = po.PaymentReferenceStatus  "
				+ " LEFT OUTER JOIN   [LegalEntity] le "
				+ " on le.id = po.LegalEntityID "
				+ " LEFT OUTER JOIN   [Country] country "
				+ " on country.ID= cnt.Country "
				+ " LEFT OUTER JOIN   [ActivityTypeEnum] ate  "
				+ " on ate.id=poald.ActivityType "
				+ " where po.TradePaymentID = ?  "),
		
		GET_TRADE_PAYMENT_ID_FROM_FUNDS_IN_ID(" Select tradePaymentId from [PaymentIn] where ID=? "),

		GET_TRADE_PAYMENT_ID_FROM_FUNDS_OUT_ID(" Select tradePaymentId from [PaymentOut] where ID=? "),

		//AT-5023
		GET_POST_CARD_TM_MQ_MESSAGE_FROM_DB("declare @Id int; "
				+ "DECLARE @COUNT int; "
				+ "SET @COUNT = ?; "
				+ "select TOP 1 @Id = Id from comp.PostCardTransactionMonitoringMQ where DeliveryStatus in (1, 3) and RetryCount<@COUNT order by id asc Select ID, TransactionID , CardRequestType, RequestJson, RetryCount, CreatedBy , CreatedOn, isPresent from comp.PostCardTransactionMonitoringMQ where id = @Id"),
		
		UPDATE_POST_CARD_TM_MQ_STATUS("UPDATE PostCardTransactionMonitoringMQ SET DeliveryStatus=?,RetryCount=?,DeliveredOn=? WHERE id=?"),
		
		DELETE_DELIVERED_RECORD_FROM_POST_CARD_TMMQ("DELETE FROM PostCardTransactionMonitoringMQ WHERE DeliveryStatus = 2 And ID = ?"),
		
		//AT-5364
		GET_DETAILS_FOR_INTUITION_HISTORIC_PAYMENT_IN_RECORDS("SELECT p.ID as payInId,"
				+ " p.AccountID as accountid,"
				+ " p.ContactID as ContactID, "
				+ " pia.[Attributes] as PIAttrib, "
				+ " p.FraugsterStatus, "
				+ " p.BlacklistStatus , "
				+ " p.SanctionStatus , "
				+ " p.CustomCheckStatus , "
				+ " p.ComplianceStatus as PIComplianceStatus, "
				+ " p.InitialStatus ,"
				+ " a.TradeAccountNumber , "
				+ " a.AccountTMFlag , "
				+ " (SELECT o.Code from Organization o WHERE ID = a.OrganizationID) AS OrganizationCode , c.TradeContactID, "
				+ " a.Version as AccountVersion, "
				+ " JSON_VALUE(aa.[Attributes],'$.company.etailer') as Etailer,"
				+ " ISNULL(CASE JSON_VALUE(pia.[Attributes],'$.trade.payment_time')" //Added for AT-5486
				+ " WHEN '' THEN Format(cast(p.CreatedOn as datetime2),'yyyy-MM-dd HH:mm:ss')"
				+ " ELSE Format(cast(JSON_VALUE(pia.[Attributes],'$.trade.payment_time') as datetime2),'yyyy-MM-dd HH:mm:ss') END,"
				+ " Format(cast(p.CreatedOn as datetime2),'yyyy-MM-dd HH:mm:ss'))  as PaymentTime"
				+ " FROM PaymentIn p "
				+ " JOIN PaymentInAttribute pia ON "
				+ " p.id = pia.id "
				+ " JOIN Account a on "
				+ " a.ID = p.AccountID "
				+ " JOIN AccountAttribute aa ON a.ID = aa.ID "
				+ " JOIN Contact c on"
				+ " c.ID = p.ContactID "
				+ " WHERE a.AccountTMFlag = 4 AND a.DormantFlag = 1 and p.CreatedOn < CURRENT_TIMESTAMP"),
		
		//AT-5364
		UPDATE_ACCOUNT_TM_FLAG_FOR_HISTORIC_PAYMENTS("UPDATE Account SET AccountTMFlag = ?, UpdatedBy = ?, UpdatedOn = ? WHERE ID = ?"),
	
		//AT-5304
	    GET_DETAILS_FOR_INTUITION_HISTORIC_PAYMENT_OUT_RECORDS("SELECT  p.ID as payOutId,"
	   		+ " p.AccountID as accountid,"
	   		+ " p.ContactID as ContactID,"
	   		+ " poa.[Attributes] as POAttrib,"
	   		+ " p.FraugsterStatus,"
	   		+ " p.BlacklistStatus ,"
	   		+ " p.SanctionStatus ,"
	   		+ " p.CustomCheckStatus ,"
	   		+ "             p.PaymentReferenceStatus,"
	   		+ " p.ComplianceStatus as POComplianceStatus,"
	   		+ " p.InitialStatus ,"
	   		+ " a.TradeAccountNumber ,"
	   		+ " a.AccountTMFlag ,"
	   		+ " (SELECT o.Code"
	   		+ " from Organization o"
	   		+ " WHERE ID = a.OrganizationID) AS OrganizationCode , c.TradeContactID, a.Version as AccountVersion,"
	   		+ " JSON_VALUE(aa.[Attributes],'$.company.etailer') as Etailer,"
	   		+ " ISNULL(CASE JSON_VALUE(poa.[Attributes],'$.beneficiary.trans_ts')" //Added for AT-5486
	   		+ " WHEN '' THEN Format(cast(p.CreatedOn as datetime2),'yyyy-MM-dd HH:mm:ss')"
	   		+ " ELSE Format(cast(JSON_VALUE(poa.[Attributes],'$.beneficiary.trans_ts') as datetime2),'yyyy-MM-dd HH:mm:ss') END,"
	   		+ " Format(cast(p.CreatedOn as datetime2),'yyyy-MM-dd HH:mm:ss')) as TransactionTimeDate"
	   		+ " FROM PaymentOut p"
	   		+ " JOIN PaymentOutAttribute poa ON p.id = poa.id"
	   		+ " JOIN Account a on "
	   		+ " a.ID = p.AccountID"
	   		+ " JOIN AccountAttribute aa ON a.ID = aa.ID"
	   		+ " JOIN Contact c on c.ID = p.ContactID"
	   		+ " WHERE a.AccountTMFlag = 4"
	   		+ " AND a.DormantFlag = 1"
	   		+ " AND p.CreatedOn < CURRENT_TIMESTAMP");

	
		
	private String query;
	
	DBQueryConstant(String query){
		this.query = query;
	}
	
	public String getQuery(){
		return this.query;
	}

}
