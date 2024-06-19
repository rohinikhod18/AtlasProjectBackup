---- Jira: AT-976 --------

ALTER TABLE Account ADD isOnQueue BIT NOT NULL  DEFAULT 0 WITH VALUES;
ALTER TABLE Contact ADD isOnQueue BIT NOT NULL  DEFAULT 0 WITH VALUES;
ALTER TABLE Paymentout ADD isOnQueue BIT NOT NULL DEFAULT 0 WITH VALUES;
ALTER TABLE PaymentIn ADD isOnQueue BIT NOT NULL  DEFAULT 0 WITH VALUES;

---- Jira: AT-972 Blacklist Addition via Atlas --------
ALTER TABLE BlackListData ADD Deleted BIT NOT NULL  DEFAULT 0 WITH VALUES;

/*****************************************
 * 
 * Update Registration Queue
 * 
*****************************************/

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
BEGIN TRANSACTION;
BEGIN TRY
	DECLARE @RegQueueData TABLE (Type TINYINT, AccountID BIGINT, ContactID BIGINT) ;
	WITH SelectedIds AS(
		SELECT
			A.Type,
			A.ResourceType,
			A.ResourceID,
			A.ContactId,
			A.AccountId,
			A.UpdatedOn,
			A.RowNum
		FROM
			vInActiveCustomer A  
	),
	SelectedId AS(
		select
			*
		from
			SelectedIds a
		WHERE
			RowNum = 1
	)
	INSERT INTO @RegQueueData SELECT [Type], AccountId, ContactId FROM SelectedId;
	
	UPDATE Account SET IsOnQueue=1 WHERE id IN (SELECT AccountID FROM @RegQueueData si WHERE si.[Type] IN (1,3));
	UPDATE Contact SET IsOnQueue=1 WHERE id IN (SELECT contactid FROM @RegQueueData si WHERE si.[Type] =2);
	COMMIT ;
END TRY
BEGIN CATCH
	SELECT ERROR_MESSAGE()
	ROLLBACK TRAN
END CATCH



/*****************************************
 * 
 * Update PaymentIn Queue
 * 
*****************************************/
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
BEGIN TRANSACTION;
BEGIN TRY 
	UPDATE payin
	SET
		IsOnQueue = 1
	FROM
		paymentin payin
	JOIN userresourcelock ur ON
		ur.resourceid = payin.id
		AND resourcetype = 2
		AND ur.LockReleasedOn IS NULL
	WHERE
		payin.compliancestatus = 4
		AND payin.Deleted <> 1; 
	COMMIT;
END TRY
BEGIN CATCH 
SELECT ERROR_MESSAGE() 
ROLLBACK TRAN
END CATCH


/*****************************************
 * 
 * Update PaymentOut Queue
 * 
*****************************************/
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
BEGIN TRANSACTION;
BEGIN TRY 
	UPDATE payout
	SET
		IsOnQueue = 1
	FROM
		paymentout payout
	JOIN userresourcelock ur ON
		ur.resourceid = payout.id
		AND resourcetype = 1
		AND ur.LockReleasedOn IS NULL
	WHERE
		payout.compliancestatus = 4
		AND payout.Deleted <> 1; 
	COMMIT;
END TRY
BEGIN CATCH 
SELECT ERROR_MESSAGE() 
ROLLBACK TRAN
END CATCH


------------vInactiveCustomer------------

DROP VIEW [Comp].[vInactiveCustomer];

CREATE VIEW [Comp].[vInactiveCustomer] AS SELECT
		A.Id ContactId,
		A.AccountId,
		B.Type,
		A.UpdatedOn,
		A.Version,
		3 AS ResourceType,
		C.ResourceID,
		1 RowNum
	FROM
		comp.Contact A
	JOIN comp.Account B ON
		A.AccountId = B.Id
	LEFT JOIN comp.UserResourceLock C ON
		C.ResourceType = 3
		AND C.LockReleasedOn IS NULL
		AND C.ResourceID = A.Id
	WHERE
		B.Type = 2	AND A.ComplianceStatus = 4 AND A.IsOnQueue = 1
UNION ALL SELECT
		A.Id ContactId,
		A.AccountId,
		B.Type,
		B.UpdatedOn,
		B.Version,
		4 AS ResourceType,
		C.ResourceID,
		row_number() over(Partition by A.AccountId Order By	A.UpdatedOn	) Rownum
	FROM
		comp.Contact A
	JOIN comp.Account B ON
		A.AccountId = B.Id
	LEFT JOIN comp.UserResourceLock C ON
		C.ResourceType = 4
		AND C.LockReleasedOn IS NULL
		AND C.ResourceID = B.Id
	WHERE
		Type IN(1, 3) AND B.ComplianceStatus = 4 AND B.IsOnQueue = 1;
   
/*******************************************************************************/
DELETE FROM Comp.UserResourceLock WHERE UserID=0;		
		
		
/*******************************************************************************/
DROP INDEX [IX_Account_CFXFilteredInactive] ON [Comp].[Account];

CREATE NONCLUSTERED INDEX [IX_Account_CFXFilteredInactive] ON [Comp].[Account]
(
    [UpdatedOn] ASC
)
INCLUDE (     [Name],
    [EIDStatus],
    [BlacklistStatus],
    [FraugsterStatus],
    [SanctionStatus])
WHERE (IsOnQueue = 1 AND [ComplianceStatus]=(4) AND [Type]=(1,3))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]


/*******************************************************************************/
DROP INDEX  [IX_compContactFilteredLast30DaysInactiveAccount] ON [Comp].[Contact];
CREATE NONCLUSTERED INDEX [IX_compContactFilteredLast30DaysInactiveAccount] ON [Comp].[Contact]
(
    [AccountID] ASC
)
WHERE (IsOnQueue = 1 AND [ComplianceStatus]=(4))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
 
/*******************************************************************************/
DROP INDEX [IX_Contact_FilteredInactive] ON [Comp].[Contact];

CREATE NONCLUSTERED INDEX [IX_Contact_FilteredInactive] ON [Comp].[Contact]
(
    [UpdatedOn] ASC,
    [AccountID] ASC
)
INCLUDE (     [Name],
    [EIDStatus],
    [BlacklistStatus],
    [FraugsterStatus],
    [SanctionStatus])
WHERE (IsOnQueue = 1 AND [ComplianceStatus]=(4))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]


/*******************************************************************************/
DROP INDEX [IX_PaymentIn_FilteredOnHold] ON [Comp].[PaymentIn];

CREATE NONCLUSTERED INDEX [IX_PaymentIn_FilteredOnHold] ON [Comp].[PaymentIn]
(
    [Deleted] ASC
)
INCLUDE (     [AccountID],
    [ContactID],
    [TradeContractNumber],
    [BlacklistStatus],
    [FraugsterStatus],
    [SanctionStatus],
    [CustomCheckStatus])
WHERE (IsOnQueue = 1 AND  [COmplianceStatus]=(4))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

/*******************************************************************************/
DROP INDEX IX_PaymentOut_FilteredOnHold ON [Comp].[PaymentOut];

CREATE NONCLUSTERED INDEX [IX_PaymentOut_FilteredOnHold] ON [Comp].[PaymentOut]
(
    [Deleted] ASC
)
INCLUDE (     [AccountID],
    [ContactID],
    [ContractNumber],
    [BlacklistStatus],
    [FraugsterStatus],
    [SanctionStatus],
    [CustomCheckStatus])
WHERE (IsOnQueue = 1 AND [COmplianceStatus]=(4))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]


---- Jira: AT-973 --------
CREATE TABLE [FraugsterSchedularData](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[AtlasID][varchar](10) NOT NULL,
	[FraugsterTransactionID] [varchar](50) NOT NULL,
	[Status][bit] NOT NULL,
	[AsyncStatus][bit] NOT NULL,
	[AsyncStatusDate][dateTime] NOT NULL,
	[Delivered][dateTime] NULL,
	[RetryCount][int] NULL,
	[CreatedBy] [int] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
CONSTRAINT [PK_FraugsterSchedularData] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]
