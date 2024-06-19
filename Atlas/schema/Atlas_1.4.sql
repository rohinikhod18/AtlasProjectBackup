/********************
 * 
 * AT-876 Adding Timestamp to TransactionDate
 * 
 ***********************/


ALTER TABLE
	[Comp].PaymentIn ALTER column TransactionDate [datetime] NOT NULL;

UPDATE P
	SET	TransactionDate = FORMAT(CAST(json_value(pia.Attributes,'$.trade.payment_time') as datetimeoffset), 'yyyy-MM-dd HH:mm:ss')
FROM
	[Comp].PaymentIn AS P JOIN [Comp].PaymentInAttribute pia ON pia.id = P.id;

CREATE NONCLUSTERED INDEX [IX_PaymentIn_TransactionDate] ON [Comp].[PaymentIn]
(
	[TransactionDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
	


DROP INDEX IX_PaymentOut_TransactionDate ON [Comp].PaymentOut;
ALTER TABLE
	[Comp].PaymentOut ALTER COLUMN TransactionDate [datetime] NULL;
	
UPDATE P
	SET TransactionDate = FORMAT( CAST(json_value(poa."Attributes",'$.beneficiary.trans_ts') as datetimeoffset),'yyyy-MM-dd HH:mm:ss')
FROM
	[Comp].PaymentOut AS P JOIN [Comp].PaymentOutAttribute poa ON poa.id = P.id;

CREATE NONCLUSTERED INDEX [IX_PaymentOut_TransactionDate] ON [Comp].[PaymentOut]
(
	[TransactionDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
	


/********************
 * 
 * AT-894TO Efficiency Report - Improvement Required
 * 
 ***********************/
ALTER TABLE [Comp].ProfileActivityLog ADD 	UserLockID [bigint] NULL;
ALTER TABLE [Comp].ProfileActivityLog  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLog_UserLockID_UserResourceLock] FOREIGN KEY([UserLockID])
REFERENCES [Comp].[UserResourceLock] ([ID]);

ALTER TABLE [Comp].PaymentInActivityLog ADD 	UserLockID [bigint] NULL;
ALTER TABLE [Comp].PaymentInActivityLog  WITH CHECK ADD  CONSTRAINT [FK_PaymentInActivityLog_UserLockID_UserResourceLock] FOREIGN KEY([UserLockID])
REFERENCES [Comp].[UserResourceLock] ([ID]);

ALTER TABLE [Comp].PaymentOutActivityLog ADD 	UserLockID [bigint] NULL;
ALTER TABLE [Comp].PaymentOutActivityLog  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutActivityLog_UserLockID_UserResourceLock] FOREIGN KEY([UserLockID])
REFERENCES [Comp].[UserResourceLock] ([ID]);



/********************
 * 
 * AT-796 Add new Column Custom Check on registration Queue 
 * 
 ***********************/
ALTER TABLE [Comp].Contact ADD CustomCheckStatus [tinyint] NULL;

-- Update Contact BlacklistStatus
WITH SelectedID AS
	(SELECT esl.EntityID AS entityID,esl.status as status, RANK() OVER (partition BY
	esl.EntityID ORDER BY esl.servicetype, esl.UpdatedOn desc) AS ESLNumber 
	FROM [Comp].EventServiceLog AS esl JOIN [Comp].contact c ON 
	c.id = esl.EntityID 
	JOIN [Comp].Event e ON esl.EventID = e.id 
	WHERE esl.ServiceType =3 AND EntityType=3 AND e.EventType IN(1,2,3) 
	GROUP BY esl.EntityID,esl.status,esl.UpdatedOn, esl.servicetype
)
UPDATE Con SET BlacklistStatus = status
	FROM [Comp].Contact Con JOIN SelectedID S ON Con.id= s.entityID AND ESLNumber =1;

-- Update Account BlacklistStatus
WITH SelectedID AS
	(SELECT esl.EntityID AS entityID,esl.status as status, RANK() OVER (partition BY
	esl.EntityID ORDER BY esl.servicetype, esl.UpdatedOn desc) AS ESLNumber 
	FROM [Comp].EventServiceLog AS esl JOIN [Comp].contact c ON 
	c.id = esl.EntityID 
	JOIN [Comp].Event e ON esl.EventID = e.id 
	WHERE esl.ServiceType =3 AND EntityType=1 AND e.EventType IN(1,2,3) 
	GROUP BY esl.EntityID,esl.status,esl.UpdatedOn, esl.servicetype
)
UPDATE A SET BlacklistStatus = status
	FROM [Comp].Account A JOIN SelectedID S ON A.id= s.entityID AND ESLNumber =1;	

-- Update New CountryCheckStatus for contact 
CREATE TABLE [Comp].TempCountryGlobalCheck (
[ID] [int] IDENTITY(1,1) NOT NULL,
contactID int NULL,
CountryCheckStatus tinyint  NULL,
GlobalCheckStatus tinyint  NULL,
) ON [PRIMARY];
-- Get GLOBALCHECK status in temp table
WITH SelectedID AS(
	SELECT esl.EntityID AS entityID,esl.status AS status, RANK() OVER (partition BY esl.EntityID 
	ORDER BY esl.servicetype, esl.UpdatedOn  desc) AS ESLNumber
	FROM [Comp].EventServiceLog AS esl JOIN [Comp].contact c ON  
	c.id = esl.EntityID
	JOIN [Comp].Event e ON esl.EventID = e.id 
	WHERE esl.ServiceType IN(5)  AND e.EventType IN(1,2,3)
	GROUP BY esl.EntityID,esl.status,esl.UpdatedOn, esl.servicetype
)
INSERT into [Comp].TempCountryGlobalCheck (contactID,GlobalCheckStatus) 
SELECT entityID,status FROM SelectedID WHERE ESLNumber =1;
-- Get COUNTRYCHECK status in temp table
WITH SelectedID AS
	(SELECT esl.EntityID AS entityID,esl.status as status, RANK() OVER (partition BY
	esl.EntityID ORDER BY esl.servicetype, esl.UpdatedOn desc) AS ESLNumber 
	FROM [Comp].EventServiceLog AS esl JOIN [Comp].contact c ON 
	c.id = esl.EntityID 
	JOIN [Comp].Event e ON esl.EventID = e.id 
	WHERE esl.ServiceType IN(8) AND e.EventType IN(1,2,3) 
	GROUP BY esl.EntityID,esl.status,esl.UpdatedOn, esl.servicetype
)
UPDATE tmp SET CountryCheckStatus = status
	FROM [Comp].TempCountryGlobalCheck tmp JOIN SelectedID S ON tmp.contactid= s.entityID AND ESLNumber =1;

-- Update New CountryCheckStatus for contact 
UPDATE c SET CustomCheckStatus =
CASE WHEN (t.CountryCheckStatus =1 AND t.GlobalCheckStatus =2) THEN 2
 WHEN (t.CountryCheckStatus =2 AND t.GlobalCheckStatus =1) THEN 2
 when (t.CountryCheckStatus =1 AND t.GlobalCheckStatus =9) THEN 1
 WHEN (t.CountryCheckStatus =9 AND t.GlobalCheckStatus =1) THEN 1 
 WHEN (t.CountryCheckStatus =1 AND t.GlobalCheckStatus =7) THEN 1
 WHEN (t.CountryCheckStatus =7 AND t.GlobalCheckStatus =1) THEN 1
 WHEN (t.CountryCheckStatus =1 AND t.GlobalCheckStatus =8) THEN 8
 WHEN (t.CountryCheckStatus =8 AND t.GlobalCheckStatus =1) THEN 8
 WHEN (t.CountryCheckStatus =1 AND t.GlobalCheckStatus =5) THEN 5
 WHEN (t.CountryCheckStatus =5 AND t.GlobalCheckStatus =1) THEN 5
 WHEN (t.CountryCheckStatus =2 AND t.GlobalCheckStatus =9) THEN 2
 WHEN (t.CountryCheckStatus =9 AND t.GlobalCheckStatus =2) THEN 2
 WHEN (t.CountryCheckStatus =2 AND t.GlobalCheckStatus =7) THEN 2
 WHEN (t.CountryCheckStatus =7 AND t.GlobalCheckStatus =2) THEN 2
 WHEN (t.CountryCheckStatus =2 AND t.GlobalCheckStatus =8) THEN 2
 WHEN (t.CountryCheckStatus =8 AND t.GlobalCheckStatus =2) THEN 2
 WHEN (t.CountryCheckStatus =2 AND t.GlobalCheckStatus =5) THEN 2
 WHEN (t.CountryCheckStatus =5 AND t.GlobalCheckStatus =2) THEN 2
 WHEN (t.CountryCheckStatus =9 AND t.GlobalCheckStatus =7) THEN 7
 WHEN (t.CountryCheckStatus =7 AND t.GlobalCheckStatus =9) THEN 1  
 WHEN (t.CountryCheckStatus =9 AND t.GlobalCheckStatus =8) THEN 8
 WHEN (t.CountryCheckStatus =8 AND t.GlobalCheckStatus =9) THEN 8
 WHEN (t.CountryCheckStatus =9 AND t.GlobalCheckStatus =5) THEN 5
 WHEN (t.CountryCheckStatus =5 AND t.GlobalCheckStatus =9) THEN 5
 WHEN (t.CountryCheckStatus =8 AND t.GlobalCheckStatus =7) THEN 8
 WHEN (t.CountryCheckStatus =7 AND t.GlobalCheckStatus =8) THEN 8
 WHEN (t.CountryCheckStatus =1 AND t.GlobalCheckStatus =1) THEN 1
 WHEN (t.CountryCheckStatus =2 AND t.GlobalCheckStatus =2) THEN 2
 WHEN (t.CountryCheckStatus =5 AND t.GlobalCheckStatus =5) THEN 5
 WHEN (t.CountryCheckStatus =7 AND t.GlobalCheckStatus =7) THEN 1
 WHEN (t.CountryCheckStatus =8 AND t.GlobalCheckStatus =8) THEN 8
 WHEN (t.CountryCheckStatus =9 AND t.GlobalCheckStatus =9) THEN 9
ELSE 9
END
FROM [Comp].Contact c JOIN TempCountryGlobalCheck t ON c.id = t.contactID; 
-- DELETE temp table
DROP TABLE [Comp].TempCountryGlobalCheck;

-- Update CustomCheckStatus for PaymentOut 

WITH SelectedID AS
(SELECT * FROM
	(SELECT * FROM (SELECT esl.ServiceType,esl.status AS status,e.paymentoutid AS paymentoutid, 
		RANK() OVER (partition BY e.PaymentOutID, esl.servicetype ORDER BY  esl.UpdatedOn  DESC) AS ESLNumber
		FROM [Comp].EventServiceLog AS esl 
		JOIN [Comp].Event e ON esl.EventID = e.id 
		JOIN [Comp].Paymentout p ON  
		p.id = e.paymentoutid
		WHERE esl.ServiceType IN (8,9)  and e.EventType IN (7,8,9)) 
            T WHERE ESLNumber =1) AS S
	   PIVOT(SUM(S.status)
FOR S.ServiceType IN ([8],[9]))	AS CustomCheckStatus)
UPDATE po 
SET CustomCheckStatus = CASE WHEN [8]=2 OR [9]=2 THEN 2
				     WHEN [8]=8 AND [9]=2 THEN 2
				     WHEN [8]=2 AND [9]=8 THEN 2
				     WHEN [8]=1 AND [9]=1 THEN 1	
				     WHEN [8]=8 AND [9]=1 THEN 1
				     WHEN [8]=1 AND [9]=8 THEN 1
				     WHEN [8]=8 AND [9]=8 THEN 8
				     WHEN [8]=9 THEN [9]
				     WHEN [9]=9 THEN [8]
				ELSE 6
				END 
FROM [Comp].PaymentOut po JOIN SelectedID Se ON po.id= Se.paymentoutid;

-- Update BlacklistStatus for PaymentOut

WITH SelectedID AS
	(SELECT esl.ServiceType,esl.status AS status,e.paymentoutid AS paymentoutid,
		RANK() OVER (partition BY e.paymentoutid ORDER BY esl.servicetype, esl.UpdatedOn  desc) AS ESLNumber
FROM [Comp].EventServiceLog AS esl 
JOIN [Comp].Event e ON esl.EventID = e.id 
JOIN [Comp].Paymentout p ON  
p.id = e.paymentoutid
WHERE esl.ServiceType IN (3)  AND e.EventType IN (7,8,9) 
GROUP BY esl.status,esl.servicetype,esl.UpdatedOn,e.PaymentOutID)
UPDATE po SET BlacklistStatus = status
FROM [Comp].PaymentOut po JOIN SelectedID S ON po.id= S.paymentoutid AND ESLNumber =1;

-- Update CustomCheckStatus for PaymentIn

WITH SelectedID AS(
SELECT * FROM
	(SELECT * FROM(SELECT esl.ServiceType,esl.status AS status,e.PaymentInID AS paymentinid, 
			RANK() OVER (partition BY e.PaymentInID, esl.servicetype ORDER BY  esl.UpdatedOn  DESC) 
			AS ESLNumber
		FROM [Comp].EventServiceLog AS esl 
		JOIN [Comp].Event e ON esl.EventID = e.id 
		JOIN [Comp].PaymentIn p ON  
		p.id = e.PaymentInID
		WHERE esl.ServiceType IN (8,9)  AND e.EventType IN (6)) 
		T WHERE ESLNumber =1) 
		AS S
		PIVOT(SUM(S.status)
		FOR S.ServiceType IN ([8],[9]))	AS CustomCheckStatus)
UPDATE pin 
SET CustomCheckStatus = CASE WHEN [8]=2 OR [9]=2 THEN 2
			     WHEN [8]=8 AND [9]=2 THEN 2
			     WHEN [8]=2 AND [9]=8 THEN 2
			     WHEN [8]=1 AND [9]=1 THEN 1	
			     WHEN [8]=8 AND [9]=1 THEN 1
			     WHEN [8]=1 AND [9]=8 THEN 1
			     WHEN [8]=8 AND [9]=8 THEN 8
			     WHEN [8]=9 THEN [9]
			     WHEN [9]=9 THEN [8]
			    ELSE 6
			    END 
FROM [Comp].PaymentIn pin JOIN SelectedID Se ON pin.id= Se.paymentinid;

-- Update BlacklistStatus for PaymentIn

WITH SelectedID AS
(SELECT esl.ServiceType,esl.status AS status,e.PaymentInID AS PaymentInID,
RANK() OVER (partition BY e.PaymentInID ORDER BY esl.servicetype, esl.UpdatedOn  desc) AS ESLNumber
FROM [Comp].EventServiceLog AS esl 
JOIN [Comp].Event e ON esl.EventID = e.id 
JOIN [Comp].PaymentIn p ON  
p.id = e.PaymentInID
WHERE esl.ServiceType IN (3)  AND e.EventType IN (6) 
GROUP by esl.status,esl.servicetype,esl.UpdatedOn,e.PaymentInID)
UPDATE po SET BlacklistStatus = status
FROM [Comp].PaymentIn po JOIN SelectedID S ON po.id= S.PaymentInID AND ESLNumber =1;


/**
 * AT- 1091 Enhance fraugster data upload process
 **/
DELETE FROM [Comp].FraugsterSchedularData;
ALTER TABLE [Comp].FraugsterSchedularData ADD SyncStatus [varchar](10) NULL;

ALTER TABLE [Comp].FraugsterSchedularData
ALTER COLUMN AsyncStatus [varchar](25) NULL;

ALTER TABLE [Comp].FraugsterSchedularData
ALTER COLUMN AsyncStatusDate datetime  NULL;

/** 
  As suggested by Caroline, these queries are to update Threshold score for fraugster since production ticket was raised for this change.
  Request ID : 90571
 Queries to change fraugster threshold score 
**/

UPDATE 
[Comp].Compliance_ServiceProviderAttribute
SET
Attribute = JSON_MODIFY(
	JSON_MODIFY(
		JSON_MODIFY(
			Attribute,
			'$.paymentOutThreesholdScore',
			'0.19'
		),
		'$.paymentInThreesholdScore',
		'0.19'
	),
	'$.registrationThreesholdScore',
	'0.19'
), UpdatedOn = getDate()
WHERE
id = 15;

