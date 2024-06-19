/******************* Add New Watchlist (AT-903) ****************/


INSERT INTO WatchList
(Reason, StopPaymentIn, StopPaymentOut, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('E-Tailer Client - Documentation Required', ((1)), ((0)), ((0)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO WatchList
(Reason, StopPaymentIn, StopPaymentOut, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('E-Tailer Client - VAT Required', ((1)), ((0)), ((0)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


INSERT INTO WatchList
(Reason, StopPaymentIn, StopPaymentOut, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('Inter-company transfers only', ((1)), ((1)), ((0)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO WatchList
(Reason, StopPaymentIn, StopPaymentOut, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('Monitor Trading Activity / Beneficiary', ((0)), ((1)), ((0)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


/*******************  Update Watchlist (AT-903) ****************/


UPDATE WatchList
SET StopPaymentIn=((1)), StopPaymentOut=((0)),UpdatedBy=1, UpdatedOn=CURRENT_TIMESTAMP
WHERE Reason='Pending KYC on Account';

UPDATE WatchList
SET StopPaymentIn=((1)), StopPaymentOut=((0)),UpdatedBy=1, UpdatedOn=CURRENT_TIMESTAMP
WHERE Reason='E Tailer Client Monitoring of Trades';

UPDATE WatchList
SET StopPaymentIn=((0)), StopPaymentOut=((1)),UpdatedBy=1, UpdatedOn=CURRENT_TIMESTAMP
WHERE Reason='Travel Company invoice required for all trades';

UPDATE WatchList
SET Reason='CFX - High Risk',UpdatedBy=1, UpdatedOn=CURRENT_TIMESTAMP
WHERE Reason='CFX High risk score';

	/*******************  Update Account table columns (AT-903) ****************/
	
	ALTER TABLE ACCOUNT ADD PayInWatchListStatus tinyint DEFAULT ((0)), PayOutWatchListStatus tinyint DEFAULT ((0));

	WITH AccWatchList AS(
		SELECT
			c.accountid AS AccID,
			SUM( CASE WHEN StopPaymentIn > 0 THEN 1 ELSE 0 END ) AS PayInStatus,
			SUM( CASE WHEN StopPaymentOut > 0 THEN 1 ELSE 0 END ) AS PayOutStatus
		FROM
			ContactWatchList CW
		JOIN Contact C ON
			cw.contact = c.id
		JOIN Account A ON
			c.accountid = A.id
		JOIN WatchList W ON
			CW.Reason = w.id
		GROUP BY
			c.accountid
	) UPDATE
		Account
	SET
		PayInWatchListStatus = CASE
			WHEN PayInStatus > 0 THEN 2
			ELSE 0
		END,
		PayOutWatchListStatus = CASE
			WHEN PayOutStatus > 0 THEN 2
			ELSE 0
		END
	FROM
		AccWatchList AW
	JOIN Account A ON
		AW.AccID = A.id;
	
	COMMIT;


	ALTER TABLE ACCOUNT DROP CONSTRAINT DF_Account_WatchListStatus;

	ALTER TABLE ACCOUNT DROP COLUMN WatchListStatus;


/*********** AT-927 *******************/

INSERT INTO ActivityTypeEnum
( Module, "Type", Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('PROFILE', 'COMPLIANCE_LOG', ((1)), 1, (getdate()), 1, (getdate()));



/*************** AT - 604 *******************/
/****** New reasons added into StatusReasonUpdate table for Contact 'INACTIVE' ****************/

INSERT INTO [StatusUpdateReason]([Module], [Reason], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
 VALUES('ALL', 'Joint applicant removed from account – Compliance removed', 1, CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP)
GO
INSERT INTO [StatusUpdateReason]([Module], [Reason], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
 VALUES('ALL', 'Joint applicant removed from account – Client requested', 1, CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP)
GO
INSERT INTO [StatusUpdateReason]([Module], [Reason], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
 VALUES('ALL', 'Contact removed – Left company', 1, CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP)
GO
INSERT INTO [StatusUpdateReason]([Module], [Reason], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
 VALUES('ALL', 'Contact removed – No longer required', 1, CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP)
GO
INSERT INTO [StatusUpdateReason]([Module], [Reason], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
 VALUES('ALL', 'Contact removed – No longer holds a significant position', 1, CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP)
GO



