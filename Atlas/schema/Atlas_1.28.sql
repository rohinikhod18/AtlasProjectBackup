-- Queries for Atlas GDPR(data anonymisation) release --

--Add dataanonstatus column
ALTER TABLE [Comp].[Account] ADD [DataAnonStatus] tinyint;

CREATE TABLE [Comp].[DataAnonStatusEnum] (
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[Status] [varchar](50) NOT NULL,
	[Description] [varchar](255) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] DEFAULT getdate() NOT NULL,
	CONSTRAINT PK_DataAnonStatusEnum PRIMARY KEY (ID),
)

CREATE TABLE [Comp].[DataAnonActivityLog] (
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[AccountID] [int] NOT NULL,
	[ContactID] [int] NOT NULL,
	[InitialApprovalComment] [varchar](1024) NOT NULL,
	[InitialApprovalBy] [int] NOT NULL,
	[InitialApprovalDate] [datetime] DEFAULT getdate() NOT NULL,
	[FinalApprovalComment] [varchar](1024) NULL,
	[FinalApprovalBy] [int] NULL,
	[FinalApprovalDate] [datetime] DEFAULT getdate() NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] DEFAULT getdate() NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
	CONSTRAINT PK_DataAnonActivityLog PRIMARY KEY (ID),
	CONSTRAINT UQ_DataAnonActivityLog_AccountID UNIQUE (AccountID),
	CONSTRAINT FK_DataAnonActivityLog_Account_AccountID FOREIGN KEY (AccountID) REFERENCES [Comp].Account(ID),
	CONSTRAINT FK_DataAnonActivityLog_Contact_ContactID FOREIGN KEY (ContactID) REFERENCES [Comp].Contact(ID)
)

--Create Table for DataAnonnymization Process that start from Enterprise
CREATE TABLE [Comp].[DataAnonProcessQueue] (
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[NewReferenceID] [bigint] NOT NULL,
	[TradeAccountNumber] [varchar](20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[AccountID] [int] NOT NULL,
	[ContactID] [int] NOT NULL,
	[AnonJson] [nvarchar](4000) COLLATE Latin1_General_CI_AS NOT NULL,
	[AnonStatus] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[Comment] [nvarchar](1024) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[AnonProcessStatus] [tinyint] NOT NULL,
	[RetryCount] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NOT NULL,
	[UpdatedOn] [datetime] NOT NULL,
	CONSTRAINT PK_DataAnonProcessQueue PRIMARY KEY (ID),
	CONSTRAINT DF_DataAnonProcessQueue_Account_AccountID FOREIGN KEY (AccountID) REFERENCES [Comp].Account(ID),
	CONSTRAINT DF_DataAnonProcessQueue_Contact_ContactID FOREIGN KEY (ContactID) REFERENCES [Comp].Contact(ID),
	CONSTRAINT DF_DataAnonProcessQueue_User_CreatedBy FOREIGN KEY (CreatedBy) REFERENCES [Comp].[User](ID),
	CONSTRAINT DF_DataAnonProcessQueue_User_UpdatedBy FOREIGN KEY (UpdatedBy) REFERENCES [Comp].[User](ID)
)

--Drop existing view
DROP VIEW [Comp].[vCustomer]

DROP VIEW [Comp].[vInactiveCustomer]

--Create view vCustomer
CREATE VIEW [Comp].[vCustomer] WITH SCHEMABINDING AS 
SELECT
	A.id ContactId,
	B.Id AccountId,
	B.Type,
	CASE
		WHEN B.Type = 2 THEN 3
		ELSE 4
	END ResourceType,
	CASE
		WHEN B.Type = 2 THEN A.Id
		ELSE B.Id
	END ResourceId,
	CASE
		Type
		WHEN 2 THEN A.UpdatedOn
		ELSE B.UpdatedOn
	END UpdatedOn,
	CASE
		Type
		WHEN 2 THEN A.ComplianceStatus
		ELSE B.ComplianceStatus
	END ComplianceStatus,
	CASE
		Type
		WHEN 2 THEN A.Version
		ELSE B.Version
	END Version,
	B.DataAnonStatus as DataAnonStatus
FROM
	comp.Contact A
JOIN comp.Account B ON
	A.AccountId = B.Id

--Create View vInactiveCustomer
CREATE VIEW [Comp].[vInactiveCustomer] AS SELECT
		A.Id ContactId,
		A.AccountId,
		B.Type,
		A.UpdatedOn,
		A.Version,
		3 AS ResourceType,
		C.ResourceID,
		B.DataAnonStatus as DataAnonStatus,
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
		B.DataAnonStatus as DataAnonStatus,
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

--Function Table
INSERT INTO [Comp].[Function]([Name], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('canViewDataAnonQueue', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);
	
--FunctionGroupMapping Table
INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((select [ID] from [Comp].[Function] where [Name] = 'canViewDataAnonQueue'), 
		(select [ID] from [Comp].[FunctionGroup] where [Name] = 'Departmental Heads'), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

--Add data into DataAnonStatusEnum table
--ID 1
INSERT INTO [Comp].[DataAnonStatusEnum]
(Status, Description, CreatedBy, CreatedOn)
VALUES('FirstDataAnonRequestApproved', 'First data anonymisation request is approved', 1, (getdate()));

--ID 2
INSERT INTO [Comp].[DataAnonStatusEnum]
(Status, Description, CreatedBy, CreatedOn)
VALUES('SecondDataAnonRequestApproved', 'Second data anonymisation request is approved', 1, (getdate()));

--ID 3
INSERT INTO [Comp].[DataAnonStatusEnum]
(Status, Description, CreatedBy, CreatedOn)
VALUES('DataAnonRequestES', 'Data anonymisation request sent to Enterprise System', 1, (getdate()));

--ID 4
INSERT INTO [Comp].[DataAnonStatusEnum]
(Status, Description, CreatedBy, CreatedOn)
VALUES('DataAnonResponseES', 'Data anonymisation response recieved from Enterprise System', 1, (getdate()));

--ID 5
INSERT INTO [Comp].[DataAnonStatusEnum]
(Status, Description, CreatedBy, CreatedOn)
VALUES('AtlasDataAnonInitiated', 'Atlas System Data anonymisation Initiated', 1, (getdate()));

--ID 6
INSERT INTO [Comp].[DataAnonStatusEnum]
(Status, Description, CreatedBy, CreatedOn)
VALUES('AtlasDataAnonCompleted', 'Atlas System Data anonymisation Completed', 1, (getdate()));

--ID 7
INSERT INTO [Comp].[DataAnonStatusEnum]
(Status, Description, CreatedBy, CreatedOn)
VALUES('AtlasDataAnonFailure', 'Atlas System Data anonymisation Failure', 1, (getdate()));


---------------------------------------------------------------------------------------------------------------
--- Rollback script ---
---------------------------------------------------------------------------------------------------------------

--Remove data from DataAnonStatusEnum table
DELETE FROM [Comp].[DataAnonStatusEnum]
WHERE [Status] IN ('FirstDataAnonRequestApproved',
					'SecondDataAnonRequestApproved',
					'DataAnonRequestES',
					'DataAnonResponseES',
					'AtlasDataAnonInitiated',
					'AtlasDataAnonCompleted',
					'AtlasDataAnonFailure');

--Remove from FunctionGroupMapping Table
DELETE FROM [Comp].[FunctionGroupMapping]
WHERE [FunctionID] = (SELECT [ID]
					FROM [Comp].[Function]
					WHERE [Name] = 'canViewDataAnonQueue')

--Remove from Function Table
DELETE FROM [Comp].[Function]
WHERE [Name] = 'canViewDataAnonQueue';


---- Drop aand recreate view as earlier
--Drop changed  view
DROP VIEW [Comp].[vCustomer]

 --Drop changed  view
DROP VIEW [Comp].[vInactiveCustomer]

--Create(Revert) view vCustomer
CREATE VIEW [Comp].[vCustomer] WITH SCHEMABINDING AS 
SELECT
	A.id ContactId,
	B.Id AccountId,
	B.Type,
	CASE
		WHEN B.Type = 2 THEN 3
		ELSE 4
	END ResourceType,
	CASE
		WHEN B.Type = 2 THEN A.Id
		ELSE B.Id
	END ResourceId,
	CASE
		Type
		WHEN 2 THEN A.UpdatedOn
		ELSE B.UpdatedOn
	END UpdatedOn,
	CASE
		Type
		WHEN 2 THEN A.ComplianceStatus
		ELSE B.ComplianceStatus
	END ComplianceStatus,
	CASE
		Type
		WHEN 2 THEN A.Version
		ELSE B.Version
	END Version
FROM
	comp.Contact A
JOIN comp.Account B ON
	A.AccountId = B.Id

--Create(Revert) View vInactiveCustomer
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




--Drop table DataAnonProcessQueue
DROP TABLE [Comp].[DataAnonProcessQueue] 

--Drop table DataAnonActivityLog
DROP TABLE [Comp].[DataAnonActivityLog] 

--Drop table DataAnonStatusEnum
DROP TABLE [Comp].[DataAnonStatusEnum]

--Remove column in Account table
ALTER TABLE [Comp].[Account] DROP COLUMN [DataAnonStatus];