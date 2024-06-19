DROP VIEW [Comp].[vInactiveCustomer] ;

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [Comp].[vInactiveCustomer] AS
SELECT
		A.Id ContactId,
		A.AccountId,
		B.Type,
		A.Version,
		A.UpdatedOn,
		3 AS ResourceType,
		C.ResourceID,
		1 RowNum
	FROM
		comp.Contact A
	JOIN comp.Account B ON
		A.AccountId = B.Id
	JOIN comp.UserResourceLock C ON
		C.ResourceType = 3
		AND C.LockReleasedOn IS NULL
		AND C.ResourceID = A.Id
	WHERE
		B.Type = 2 
		AND A.ComplianceStatus = 4
UNION ALL SELECT
		A.Id ContactId,
		A.AccountId,
		B.Type,
		B.version,
		B.UpdatedOn,
		4 AS ResourceType,
		C.ResourceID,
		row_number() over(
			Partition by A.AccountId
		Order By
			A.UpdatedOn
		) Rownum
	FROM
		comp.Contact A
	JOIN comp.Account B ON
		A.AccountId = B.Id
	JOIN comp.UserResourceLock C ON
		C.ResourceType = 4
		AND C.LockReleasedOn IS NULL
		AND C.ResourceID = B.Id
	WHERE
		Type IN(1,3	) 
		AND A.ComplianceStatus = 4;	


/********************************************************/

DROP VIEW [Comp].[vCustomer] 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

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
	A.AccountId = B.Id;
	
/*****************************************************/

DROP INDEX IX_PaymentOutAttribute_vColumns ON [Comp].PaymentOutAttribute;

ALTER TABLE [Comp].PaymentOutAttribute DROP COLUMN vValueDate;
ALTER TABLE [Comp].PaymentOutAttribute ADD vValueDate AS (CONVERT([date],json_value([Attributes],'$.trade.value_date'), 102)) PERSISTED

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF


CREATE NONCLUSTERED INDEX [IX_PaymentOutAttribute_vColumns] ON [Comp].[PaymentOutAttribute]
(
	[vValueDate] ASC
)
INCLUDE ( 	[CreatedOn],
	[vMaturityDate],
	[vSellingCurrency],
	[vBuyingCurrency],
	[vSellingAmount],
	[vBuyingAmount],
	[vReasonOfTransfer],
	[vBeneficiaryFirstName],
	[vBeneficiaryLastName],
	[vBeneficiaryCountry],
	[vTradeBeneficiaryID],
	[vTradeBankID],
	[vBankName],
	[vBeneficiaryAmount]) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = ON, DROP_EXISTING = OFF, ONLINE = ON, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx];
	

