USE [Compliance]

ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;

ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;

ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;

ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;

USE [Compliance]
GO
CREATE SCHEMA [Comp] 
GO

CREATE FULLTEXT CATALOG [AccountAttributes]WITH ACCENT_SENSITIVITY = ON


CREATE FULLTEXT CATALOG [ContactAttributes]WITH ACCENT_SENSITIVITY = ON


CREATE FULLTEXT CATALOG [PaymentInAttributes]WITH ACCENT_SENSITIVITY = ON


CREATE FULLTEXT CATALOG [PaymentoutAttributes]WITH ACCENT_SENSITIVITY = ON


CREATE PARTITION FUNCTION [ifts_comp_fragment_partition_function_099F5001](varbinary(128)) AS RANGE LEFT FOR VALUES (0x003000300033003200300030003000300030003100790073006600660073006100610072, 0x003A0022006A006A00700069006D006D, 0x006B0075006200620061, 0x006E006E0037003400370039003400370034003600350039002D)

CREATE PARTITION FUNCTION [ifts_comp_fragment_partition_function_1E305893](varbinary(128)) AS RANGE LEFT FOR VALUES (0x003200330035003300370032, 0x0066006C00650078006900740072006100640065)

CREATE PARTITION FUNCTION [ifts_comp_fragment_partition_function_55808D7D](varbinary(128)) AS RANGE LEFT FOR VALUES (0x00330031003100310069003200350034003500340030002D003000300037, 0x0035003900380035002E00350032, 0x006700720065006900670022002C0022006C006100730074005F006E0061006D00650022003A00220022002C00220065006D00610069006C0022003A0022006F00670072006500690067004000740061006C006B00740061006C006B002E006E00650074, 0x006E006E0033003700320039003700340032)

CREATE PARTITION SCHEME [ifts_comp_fragment_data_space_099F5001] AS PARTITION [ifts_comp_fragment_partition_function_099F5001] TO ([Ftsi], [Ftsi], [Ftsi], [Ftsi], [Ftsi])

CREATE PARTITION SCHEME [ifts_comp_fragment_data_space_1E305893] AS PARTITION [ifts_comp_fragment_partition_function_1E305893] TO ([Ftsi], [Ftsi], [Ftsi])

CREATE PARTITION SCHEME [ifts_comp_fragment_data_space_55808D7D] AS PARTITION [ifts_comp_fragment_partition_function_55808D7D] TO ([Ftsi], [Ftsi], [Ftsi], [Ftsi], [Ftsi])



SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Contact](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[AccountID] [int] NOT NULL,
	[Name] [nvarchar](150) NOT NULL,
	[CRMContactID] [char](18) NULL,
	[TradeContactID] [int] NOT NULL,
	[Primary] [bit] NOT NULL,
	[ComplianceStatus] [tinyint] NOT NULL,
	[ComplianceDoneOn] [datetime] NULL,
	[ComplianceExpiry] [date] NULL,
	[Country] [smallint] NOT NULL,
	[Version] [smallint] NOT NULL,
	[EIDStatus] [tinyint] NOT NULL,
	[FraugsterStatus] [tinyint] NOT NULL,
	[SanctionStatus] [tinyint] NOT NULL,
	[BlacklistStatus] [tinyint] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Contact] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[UserResourceLock](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NULL,
	[ResourceID] [bigint] NOT NULL,
	[LockReleasedOn] [datetime] NULL,
	[CreatedBy] [int] NULL,
	[CreatedOn] [datetime] NOT NULL,
	[WorkflowTime] [datetime] NULL,
	[ResourceType] [tinyint] NOT NULL,
 CONSTRAINT [PK_UserResourceLock] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Account](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[LegalEntity] nvarchar(35) NULL,
	[Name] [nvarchar](150) NOT NULL,
	[CRMAccountID] [char](18) NOT NULL,
	[TradeAccountID] [int] NOT NULL,
	[TradeAccountNumber] [varchar](20) NOT NULL,
	[Type] [tinyint] NOT NULL,
	[AccountStatus] [varchar](50) NOT NULL,
	[ComplianceStatus] [tinyint] NOT NULL,
	[ComplianceDoneOn] [datetime] NULL,
	[ComplianceExpiry] [datetime] NULL,
	[Version] [smallint] NOT NULL,
	[EIDStatus] [tinyint] NOT NULL,
	[FraugsterStatus] [tinyint] NOT NULL,
	[SanctionStatus] [tinyint] NOT NULL,
	[BlacklistStatus] [tinyint] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[WatchListStatus] [tinyint] NULL,
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [Comp].[vInactiveCustomer] AS SELECT
		A.Id ContactId,
		A.AccountId,
		B.Type,
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
		Type IN(
			1,
			3
		)
		AND A.ComplianceStatus = 4;
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [Comp].[vCustomer] WITH SCHEMABINDING AS SELECT
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
	END ComplianceStatus
FROM
	comp.Contact A
JOIN comp.Account B ON
	A.AccountId = B.Id
GO

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF

GO

/****** Object:  Index [IX_vCustomer]    Script Date: 13/04/2017 09:18:05 ******/
CREATE UNIQUE CLUSTERED INDEX [IX_vCustomer] ON [Comp].[vCustomer]
(
 [ContactId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100) ON [Idx]
GO
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[AccountAttribute](
	[ID] [int] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL,
	[Version] [smallint] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[vBuyCurrency]  AS (CONVERT([char](3),json_value([Attributes],'$.buying_currency'))),
	[vSellCurrency]  AS (CONVERT([char](3),json_value([Attributes],'$.selling_currency'))),
	[vsource]  AS (CONVERT([varchar](20),json_value([Attributes],'$.source'))),
	[vTransactionValue]  AS (CONVERT([varchar](60),json_value([Attributes],'$.txn_value'))),
	[vCountry]  AS (CONVERT([char](3),json_value([Attributes],'$.op_country'))),
 CONSTRAINT [PK_AccountAttribute] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[AccountAttributeHistory](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[AccountID] [int] NOT NULL,
	[Version] [int] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[CreatedBy] [int] NOT NULL,
 CONSTRAINT [PK_AccountAttributeHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[AccountAttributes_AttributesNotcompliant](
	[ID] [int] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[AccountComplianceHistory](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[AccountID] [int] NOT NULL,
	[Version] [int] NOT NULL,
	[Status] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_AccountComplianceHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[AccountComplianceStatusEnum](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[Status] [varchar](50) NOT NULL,
	[Description] [varchar](255) NOT NULL,
	[FundsInAllowed] [bit] NOT NULL,
	[FundsOutAllowed] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_AccountComplianceStatusEnum] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ActivityTypeEnum](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Module] [nvarchar](50) NOT NULL,
	[Type] [nvarchar](50) NOT NULL,
	[Active] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_ActivityTypeEnum] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[BlackListData](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[BlackListType] [int] NOT NULL,
	[Value] [nvarchar](255) NOT NULL,
	[Created_On] [datetime] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[Updated_On] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
 CONSTRAINT [PK_BlackListData] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[BlackListType](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Type] [nvarchar](20) NOT NULL,
	[Relevance] [smallint] NOT NULL,
	[Created_On] [datetime] NULL,
	[CreatedBy] [int] NOT NULL,
	[Updated_On] [datetime] NULL,
	[UpdatedBy] [int] NULL,
 CONSTRAINT [PK_BlackListType] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Compliance_ServiceProvider](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[ServiceType] [tinyint] NOT NULL,
	[Code] [nvarchar](40) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Internal] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Compliance_ServiceProvider] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Compliance_ServiceProviderAttribute](
	[ID] [tinyint] NOT NULL,
	[Attribute] [nvarchar](max) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Compliance_ServiceProviderAttribute] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Compliance_ServiceTypeEnum](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[Code] [nvarchar](50) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Compliance_ServiceTypeEnum] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ContactAttribute](
	[ID] [int] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL,
	[Version] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[vEmail]  AS (CONVERT([nvarchar](256),json_value([Attributes],'$.email'))),
	[vSecondEmail]  AS (CONVERT([nvarchar](256),json_value([Attributes],'$.second_email'))),
	[vOccupation]  AS (CONVERT([varchar](256),json_value([Attributes],'$.occupation'))),
	[vAddress]  AS (CONVERT([nvarchar](2048),json_value([Attributes],'$.address1'))),
 CONSTRAINT [PK_ContactAttribute] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ContactAttributeHistory](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ContactID] [int] NOT NULL,
	[Version] [int] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_ContactAttributeHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ContactAttributes_AttributesNotcompliant](
	[ID] [int] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ContactComplianceHistory](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ContactID] [int] NOT NULL,
	[Version] [int] NOT NULL,
	[Status] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[CreatedBy] [int] NOT NULL,
 CONSTRAINT [PK_ContactComplianceHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ContactComplianceStatusEnum](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[Status] [nvarchar](50) NOT NULL,
	[Description] [nvarchar](255) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_ContactComplianceStatusEnum] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ContactWatchList](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Reason] [int] NOT NULL,
	[Contact] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_ContactWatchList] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Country](
	[ID] [smallint] NOT NULL,
	[Code] [nchar](3) NOT NULL,
	[DisplayName] [nvarchar](50) NOT NULL,
	[ISOCode] [nchar](3) NOT NULL,
	[ShortCode] [nchar](2) NOT NULL,
	[ISDCode] [nvarchar](15) NOT NULL,
	[RiskLevel] [nchar](1) NOT NULL,
	[Active] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Country] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_Country_Code] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx],
 CONSTRAINT [UQ_Country_DisplayName] UNIQUE NONCLUSTERED 
(
	[DisplayName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[CountryState](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Country] [smallint] NOT NULL,
	[DisplayName] [nvarchar](50) NOT NULL,
	[Code] [nvarchar](15) NULL,
	[Active] [bit] NOT NULL,
	[CDLicenseStatus] [tinyint] NOT NULL,
	[TradeStateID] [int] NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_CountryState] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Currency](
	[ID] [smallint] NOT NULL,
	[Code] [nchar](3) NOT NULL,
	[ISOCode] [nchar](3) NOT NULL,
	[DisplayName] [nvarchar](50) NOT NULL,
	[Category] [tinyint] NOT NULL,
	[Active] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Currency] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [PK_Currency_Code] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [PK_Currency_DisplayName] UNIQUE NONCLUSTERED 
(
	[DisplayName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[CurrencyCategory](
	[ID] [tinyint] NOT NULL,
	[Category] [nvarchar](10) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_CurrencyCategory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_CurrencyCategory_Code] UNIQUE NONCLUSTERED 
(
	[Category] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[DeviceInfo](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[EventID] [bigint] NOT NULL,
	[UserAgent] [nvarchar](4000) NULL,
	[DeviceType] [nvarchar](50) NULL,
	[DeviceName] [nvarchar](100) NULL,
	[DeviceVersion] [nvarchar](30) NULL,
	[DeviceID] [nvarchar](100) NULL,
	[DeviceManufacturer] [nvarchar](100) NULL,
	[OSType] [nvarchar](100) NULL,
	[BrowserName] [nvarchar](50) NULL,
	[BrowserVersion] [nvarchar](30) NULL,
	[BrowserLanguage] [nvarchar](100) NULL,
	[BrowserOnline] [bit] NULL,
	[OSTimestamp] [datetime] NULL,
	[CDAppID] [nvarchar](100) NULL,
	[CDAppVersion] [nvarchar](30) NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_DeviceInfo] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Document](
	[ID] [smallint] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](250) NOT NULL,
	[Category] [varchar](5) NOT NULL,
	[IsPrimary] [bit] NOT NULL,
	[IsSecondary] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Document] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[EntityTypeEnum](
	[ID] [tinyint] NOT NULL,
	[Type] [varchar](50) NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedON] [datetime] NOT NULL,
	[UpdatedBy] [int] NOT NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_EntityTypeEnum] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Event](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[EventType] [tinyint] NOT NULL,
	[AccountID] [int] NOT NULL,
	[AccountVersion] [smallint] NOT NULL,
	[PaymentInID] [bigint] NULL,
	[PaymentOutID] [bigint] NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Event] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Eventsample](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [int] NOT NULL,
	[EventType] [int] NOT NULL,
	[AccountID] [int] NOT NULL,
	[AccountVersion] [int] NOT NULL,
	[PaymentInID] [bigint] NULL,
	[PaymentOutID] [bigint] NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Eventsample] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[EventServiceLog](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[EventID] [bigint] NOT NULL,
	[EntityType] [tinyint] NOT NULL,
	[EntityID] [bigint] NOT NULL,
	[EntityVersion] [smallint]  NULL,
	[ServiceProvider] [tinyint] NOT NULL,
	[ServiceType] [tinyint] NOT NULL,
	[ProviderResponse] [nvarchar](max) NOT NULL,
	[Status] [tinyint] NOT NULL,
	[Summary] [nvarchar](max) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_EventServiceLog] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]



SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[EventTypeEnum](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[Type] [nvarchar](50) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_EventTypeEnum] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Function](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
 CONSTRAINT [PK_Function] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_Func_Name] UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[FunctionGroup](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Active] [bit] NOT NULL,
	[Deleted] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
 CONSTRAINT [PK_FunctionGroup] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_FunctionGroup_Name] UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[FunctionGroupMapping](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[FunctionID] [int] NOT NULL,
	[FunctionGroupID] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
 CONSTRAINT [PK_FunctionGroupMapping] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_FunctionGroupMapping] UNIQUE NONCLUSTERED 
(
	[FunctionID] ASC,
	[FunctionGroupID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[KYC_CountryProviderMapping](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Country] [nvarchar](30) NOT NULL,
	[ProviderName] [nvarchar](30) NOT NULL,
	[CreatedOn] [datetime] NULL,
	[CreatedBy] [int] NOT NULL,
	[UpdatedOn] [datetime] NULL,
	[UpdatedBy] [int] NULL,
 CONSTRAINT [PK_KYC_CountryProviderMapping] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Organization](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[ParentID] [int] NULL,
	[Code] [nvarchar](50) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Locked] [bit] NOT NULL,
	[Deleted] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_Organization] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[OrganizationNew](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[ParentID] [int] NULL,
	[Code] [nvarchar](50) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Locked] [bit] NOT NULL,
	[Deleted] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[OverideFunction](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[FunctionID] [int] NOT NULL,
	[Include] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
 CONSTRAINT [PK_OverideFunction] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_OverideFunction] UNIQUE NONCLUSTERED 
(
	[UserID] ASC,
	[FunctionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentComplianceStatusEnum](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[Status] [nvarchar](50) NOT NULL,
	[Description] [nvarchar](255) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentComplianceStatusEnum] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentIn](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[AccountID] [int] NOT NULL,
	[ContactID] [int] NOT NULL,
	[TradeContractNumber] [varchar](25) NOT NULL,
	[TradePaymentID] [int] NOT NULL,
	[ComplianceStatus] [tinyint] NOT NULL,
	[ComplianceDoneOn] [datetime] NOT NULL,
	[Deleted] [bit] NOT NULL,
	[FraugsterStatus] [tinyint] NOT NULL,
	[SanctionStatus] [tinyint] NOT NULL,
	[BlacklistStatus] [tinyint] NOT NULL,
	[CustomCheckStatus] [tinyint] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[TransactionDate] [date] NOT NULL,
 CONSTRAINT [PK_PaymentIn] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentInActivityLog](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PaymentInID] [bigint] NOT NULL,
	[ActivityBy] [int] NOT NULL,
	[Timestamp] [datetime] NOT NULL,
	[Comments] [nvarchar](1024) NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentInActivityLog] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentInActivityLogDetail](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[ActivityID] [bigint] NOT NULL,
	[ActivityType] [int] NOT NULL,
	[Log] [nvarchar](4000) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentInActivityLogDetail] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentInAttribute](
	[ID] [bigint] NOT NULL,
	[Version] [tinyint] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[vTransactionCurrency]  AS (CONVERT([char](3),json_value([Attributes],'$.trade.transaction_currency'))),
	[vTransactionAmount]  AS (CONVERT([decimal](18,8),json_value([Attributes],'$.trade.selling_amount'))),
	[vBaseCurrencyAmount]  AS (CONVERT([decimal](18,8),json_value([Attributes],'$.trade.selling_amount_GBP_Value'))),
	[vPaymentMethod]  AS (CONVERT([varchar](50),json_value([Attributes],'$.trade.payment_Method'))),
	[vCountryOfPayment]  AS (CONVERT([char](3),json_value([Attributes],'$.trade.country_of_fund'))),
	[vThirdPartyPayment]  AS (CONVERT([bit],case json_value([Attributes],'$.trade.third_party_payment') when 'false' then (0) when 'true' then (1)  end)),
	[vDebitorName]  AS (CONVERT([nvarchar](100),json_value([Attributes],'$.trade.debtor_name'))),
 CONSTRAINT [PK_PaymentInAttribute] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentInAttributeHistory](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PaymentInID] [bigint] NULL,
	[Version] [tinyint] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentInAttributeHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[paymentINAttributes_AttributesNotcompliant](
	[ID] [bigint] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentInComplianceHistory](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PaymentInID] [bigint] NOT NULL,
	[Version] [tinyint] NOT NULL,
	[Status] [tinyint] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[CreatedBy] [int] NOT NULL,
 CONSTRAINT [PK_PaymentInComplianceHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentInStatusReason](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PaymentInID] [bigint] NOT NULL,
	[StatusUpdateReasonID] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentInStatusReason] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentOut](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[AccountID] [int] NOT NULL,
	[ContactID] [int] NOT NULL,
	[ContractNumber] [varchar](25) NOT NULL,
	[TradePaymentID] [int] NOT NULL,
	[ComplianceStatus] [tinyint] NOT NULL,
	[ComplianceDoneOn] [datetime] NOT NULL,
	[Deleted] [bit] NOT NULL,
	[FraugsterStatus] [tinyint] NOT NULL,
	[SanctionStatus] [tinyint] NOT NULL,
	[BlacklistStatus] [tinyint] NOT NULL,
	[CustomCheckStatus] [tinyint] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[TransactionDate] [date] NOT NULL,
 CONSTRAINT [PK_PaymentOut] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentOutActivityLog](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PaymentOutID] [bigint] NOT NULL,
	[ActivityBy] [int] NOT NULL,
	[Timestamp] [datetime] NOT NULL,
	[Comments] [nvarchar](1024) NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentOutActivityLog] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentOutActivityLogDetail](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[ActivityID] [bigint] NOT NULL,
	[ActivityType] [int] NOT NULL,
	[Log] [nvarchar](4000) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentOutActivityLogDetail] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentOutAttribute](
	[ID] [bigint] NOT NULL,
	[Version] [tinyint] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[BeneAmount] [decimal](18, 8) NULL,
	[vValueDate]  AS (CONVERT([varchar](16),json_value([Attributes],'$.trade.value_date'))),
	[vMaturityDate]  AS (CONVERT([varchar](16),json_value([Attributes],'$.trade.maturity_date'))),
	[vSellingCurrency]  AS (CONVERT([char](3),json_value([Attributes],'$.trade.sell_currency'))),
	[vBuyingCurrency]  AS (CONVERT([char](3),json_value([Attributes],'$.trade.buy_currency'))),
	[vSellingAmount]  AS (CONVERT([decimal](18,8),json_value([Attributes],'$.trade.selling_amount'))),
	[vBuyingAmount]  AS (CONVERT([decimal](18,8),json_value([Attributes],'$.trade.buying_amount'))),
	[vReasonOfTransfer]  AS (CONVERT([nvarchar](100),json_value([Attributes],'$.trade.purpose_of_trade'))),
	[vBeneficiaryFirstName]  AS (CONVERT([nvarchar](500),json_value([Attributes],'$.beneficiary.first_name'))),
	[vBeneficiaryLastName]  AS (CONVERT([nvarchar](80),json_value([Attributes],'$.beneficiary.last_name'))),
	[vBeneficiaryCountry]  AS (CONVERT([char](3),json_value([Attributes],'$.beneficiary.country'))),
	[vTradeBeneficiaryID]  AS (CONVERT([varchar](12),json_value([Attributes],'$.beneficiary.beneficiary_id'))),
	[vTradeBankID]  AS (CONVERT([varchar](12),json_value([Attributes],'$.beneficiary.beneficiary_bankid'))),
	[vBankName]  AS (CONVERT([nvarchar](150),json_value([Attributes],'$.beneficiary.beneficary_bank_name'))),
	[vBeneficiaryAmount]  AS (CONVERT([decimal](18,8),json_value([Attributes],'$.beneficiary.amount'))),
 CONSTRAINT [PK_PaymentOutAttribute] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentOutAttributeHistory](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PaymentOutID] [bigint] NULL,
	[Version] [tinyint] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentOutAttributeHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[paymentoutAttributes_AttributesNotcompliant](
	[ID] [bigint] NOT NULL,
	[Attributes] [nvarchar](max) NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentOutComplianceHistory](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PaymentOutID] [bigint] NOT NULL,
	[Version] [tinyint] NOT NULL,
	[Status] [tinyint] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_PaymentOutComplianceHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PaymentOutStatusReason](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PaymentOutID] [bigint] NOT NULL,
	[StatusUpdateReasonID] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_PaymentOutStatusReason] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[PayOut_Temp](
	[OrganizationID] [int] NOT NULL,
	[AccountID] [decimal](18, 0) NOT NULL,
	[ContactID] [decimal](18, 0) NULL,
	[ContractNumber] [varchar](50) NOT NULL,
	[TradePaymentID] [decimal](18, 0) NOT NULL,
	[ComplianceStatus] [varchar](4) NOT NULL,
	[ComplianceDoneOn] [datetime] NULL,
	[FraugsterStatus] [int] NULL,
	[SanctionStatus] [varchar](4) NOT NULL,
	[BlacklistStatus] [varchar](4) NOT NULL,
	[CustomCheckStatus] [varchar](4) NOT NULL,
	[Version] [int] NOT NULL,
	[TransactionDate] [datetime] NOT NULL,
	[ValueDate] [datetime] NULL,
	[MaturityDate] [datetime] NOT NULL,
	[SellingCurrency] [varchar](3) NOT NULL,
	[BuyingCurrency] [varchar](3) NOT NULL,
	[SellingAmount] [decimal](18, 2) NOT NULL,
	[BuyingAmount] [decimal](18, 2) NOT NULL,
	[ReasonOfTransfer] [varchar](100) NOT NULL,
	[BeneficiaryFirstName] [varchar](150) NOT NULL,
	[BeneficiaryLastName] [varchar](50) NULL,
	[BeneficiaryCountry] [varchar](3) NOT NULL,
	[TradeBeneficiaryID] [decimal](18, 0) NOT NULL,
	[TradeBankID] [int] NOT NULL,
	[BankName] [varchar](100) NOT NULL,
	[Attributes] [int] NULL,
	[CreatedBy] [int] NULL,
	[CreatedOn] [datetime] NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [int] NULL,
	[Sentinel_RefID] [varchar](100) NULL,
	[OFAC_List] [varchar](200) NULL,
	[OFAC_Cntry] [varchar](200) NULL,
	[World_Check] [varchar](200) NULL,
	[Status] [varchar](4) NOT NULL,
	[EntityType] [varchar](11) NOT NULL,
	[CreatedDate] [datetime] NULL,
	[createdBy1] [int] NOT NULL,
	[UpdatedDate] [datetime] NULL,
	[UpdatedBy1] [int] NULL,
	[compAcc] [int] NULL,
	[CompCont] [int] NULL,
	[EMAIL] [varchar](50) NULL,
	[Account_NO] [varchar](100) NOT NULL,
	[Bank_Address] [varchar](512) NULL,
	[Swift_no] [varchar](100) NULL,
	[ReferenceNo] [varchar](100) NULL,
	[BENEAMT] [decimal](18, 2) NULL,
	[BASEAmount] [decimal](18, 2) NULL,
	[ReferenceId] [varchar](50) NULL,
	[summary] [varchar](2000) NULL,
	[ProviderResponse] [varchar](2000) NULL
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ProfileActivityLog](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Timestamp] [datetime] NOT NULL,
	[ActivityBy] [int] NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[AccountID] [int] NOT NULL,
	[ContactID] [int] NULL,
	[Comments] [nvarchar](1024) NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_ProfileActivityLog] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ProfileActivityLogDetail](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[ActivityID] [bigint] NOT NULL,
	[ActivityType] [int] NOT NULL,
	[Log] [nvarchar](4000) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_ProfileActivityLogDetail] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ProfileStatusReason](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[AccountID] [int] NOT NULL,
	[ContactID] [int] NULL,
	[StatusUpdateReasonID] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_ProfileStatusReason] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [CN_UniqueResourceLock] UNIQUE NONCLUSTERED 
(
	[ContactID] ASC,
	[StatusUpdateReasonID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[Role](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SSORoleID] [nvarchar](50) NOT NULL,
	[Description] [nvarchar](150) NOT NULL,
	[Active] [bit] NOT NULL,
	[Deleted] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_Role_SSORoleID] UNIQUE NONCLUSTERED 
(
	[SSORoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[RoleFunctionGroupMapping](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[RoleID] [int] NOT NULL,
	[FunctionGroupID] [int] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
 CONSTRAINT [PK_RoleFunctionGroupMapping] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_RFGM_RoleID] UNIQUE NONCLUSTERED 
(
	[RoleID] ASC,
	[FunctionGroupID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[ServiceStatusEnum](
	[ID] [tinyint] NOT NULL,
	[Status] [varchar](50) NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedON] [datetime] NOT NULL,
	[UpdatedBy] [int] NOT NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_ServiceStatusEnum] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[StatusBroadCastQueue](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[EntityType] [tinyint] NOT NULL,
	[AccountID] [int] NULL,
	[ContactID] [int] NULL,
	[PaymentInID] [bigint] NULL,
	[PaymentOutID] [bigint] NULL,
	[StatusJson] [nvarchar](4000) NOT NULL,
	[DeliveryStatus] [tinyint] NOT NULL,
	[DeliverOn] [datetime] NOT NULL,
	[RetryCount] [int] NOT NULL,
	[DeliveredOn] [datetime] NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_StatusBroadCastQueue] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[StatusUpdateReason](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Module] [nvarchar](50) NOT NULL,
	[Reason] [nvarchar](80) NOT NULL,
	[Active] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_StatusUpdateReason] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]



SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[User](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SSOUserID] [nvarchar](50) NOT NULL,
	[Active] [bit] NOT NULL,
	[Deleted] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY],
 CONSTRAINT [UQ_USER_SSOUSERID] UNIQUE NONCLUSTERED 
(
	[SSOUserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[VelocityRules](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[OrganizationID] [tinyint] NOT NULL,
	[CustType] [nvarchar](50) NOT NULL,
	[EventType] [nvarchar](50) NOT NULL,
	[CountThreshold] [int] NOT NULL,
	[AmountThreshold] [decimal](18, 4) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_VelocityRules] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [Comp].[WatchList](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Reason] [nvarchar](100) NOT NULL,
	[StopPaymentIn] [bit] NOT NULL,
	[StopPaymentOut] [bit] NOT NULL,
	[Locked] [bit] NOT NULL,
	[Deleted] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_WatchList] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]

---
SET ANSI_PADDING ON


CREATE NONCLUSTERED INDEX [IX_Account_CFXFilteredInactive] ON [Comp].[Account]
(
	[UpdatedOn] ASC
)
INCLUDE ( 	[Name],
	[EIDStatus],
	[BlacklistStatus],
	[FraugsterStatus],
	[SanctionStatus]) 
WHERE ([ComplianceStatus]=(4) AND [Type]=(1))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Account_AccountComplianceStatusEnum] ON [Comp].[Account]
(
	[ComplianceStatus] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Account_CFXFilteredUpdatedOn] ON [Comp].[Account]
(
	[UpdatedOn] DESC
)
WHERE ([Type] IN ((1), (3)))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ANSI_PADDING ON


CREATE UNIQUE NONCLUSTERED INDEX [IXFK_Account_CRMACID] ON [Comp].[Account]
(
	[OrganizationID] ASC,
	[CRMAccountID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE UNIQUE NONCLUSTERED INDEX [IXFK_Account_TRADEACID] ON [Comp].[Account]
(
	[OrganizationID] ASC,
	[TradeAccountID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ANSI_PADDING ON


CREATE UNIQUE NONCLUSTERED INDEX [IXFK_Account_TRADEACNUM] ON [Comp].[Account]
(
	[OrganizationID] ASC,
	[TradeAccountNumber] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Account_Type] ON [Comp].[Account]
(
	[Type] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Account_UpdatedOn] ON [Comp].[Account]
(
	[UpdatedOn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF


CREATE NONCLUSTERED INDEX [IX_AccountAttribute_vColumns] ON [Comp].[AccountAttribute]
(
	[CreatedOn] ASC
)
INCLUDE ( 	[vBuyCurrency],
	[vSellCurrency],
	[vsource],
	[vTransactionValue],
	[vCountry]) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF


CREATE NONCLUSTERED INDEX [IX_AccountAttribute_vSellCurrency] ON [Comp].[AccountAttribute]
(
	[vSellCurrency] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_AccountAttribute_Account] ON [Comp].[AccountAttribute]
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_AccountAttributeHistory_AccountAttribute] ON [Comp].[AccountAttributeHistory]
(
	[AccountID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ANSI_PADDING ON


CREATE UNIQUE NONCLUSTERED INDEX [UQ_BlackListData_Value] ON [Comp].[BlackListData]
(
	[BlackListType] ASC,
	[Value] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_ServiceProviderAttribute_ServiceProvider] ON [Comp].[Compliance_ServiceProviderAttribute]
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_compContactFilteredLast30DaysInactiveAccount] ON [Comp].[Contact]
(
	[AccountID] ASC
)
WHERE ([UpdatedOn]>'20170221' AND [ComplianceStatus]=(4))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ANSI_PADDING ON


CREATE NONCLUSTERED INDEX [IX_Contact_FilteredInactive] ON [Comp].[Contact]
(
	[UpdatedOn] ASC,
	[AccountID] ASC
)
INCLUDE ( 	[Name],
	[EIDStatus],
	[BlacklistStatus],
	[FraugsterStatus],
	[SanctionStatus]) 
WHERE ([ComplianceStatus]=(4))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Contact_Account] ON [Comp].[Contact]
(
	[AccountID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Contact_CB ] ON [Comp].[Contact]
(
	[CreatedBy] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Contact_ContactComplianceStatusEnum ] ON [Comp].[Contact]
(
	[ComplianceStatus] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Contact_UpdatedOn] ON [Comp].[Contact]
(
	[UpdatedOn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF


CREATE NONCLUSTERED INDEX [IX_CompContactAttributes_vEmail] ON [Comp].[ContactAttribute]
(
	[vEmail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF


CREATE NONCLUSTERED INDEX [IX_CompContactAttributes_vSecondEmail] ON [Comp].[ContactAttribute]
(
	[vSecondEmail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF


CREATE NONCLUSTERED INDEX [IX_contactattribute_vColumns] ON [Comp].[ContactAttribute]
(
	[CreatedOn] ASC
)
INCLUDE ( 	[vEmail],
	[vSecondEmail],
	[vOccupation],
	[vAddress]) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_ContactAttribute_Contact] ON [Comp].[ContactAttribute]
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_ContactAttributeHistory_ContactAttribute] ON [Comp].[ContactAttributeHistory]
(
	[ContactID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_CompContactWatchList_ReasonContact] ON [Comp].[ContactWatchList]
(
	[Reason] ASC,
	[Contact] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE UNIQUE NONCLUSTERED INDEX [UQ_ContactWatchList] ON [Comp].[ContactWatchList]
(
	[Reason] ASC,
	[Contact] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_CompEvent_FilteredAccountIdNoPayment] ON [Comp].[Event]
(
	[AccountID] ASC
)
INCLUDE ( 	[PaymentInID],
	[PaymentOutID]) 
WHERE ([paymentinid] IS NULL AND [paymentoutid] IS NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_CompEvent_FilteredAccountIdPaymentIn] ON [Comp].[Event]
(
	[AccountID] ASC,
	[PaymentInID] ASC
)
INCLUDE ( 	[PaymentOutID]) 
WHERE ([paymentoutid] IS NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_CompEvent_FilteredAccountIdPaymentOut] ON [Comp].[Event]
(
	[AccountID] ASC,
	[PaymentOutID] ASC
)
INCLUDE ( 	[PaymentInID]) 
WHERE ([paymentInID] IS NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Event_EventTypeEnum] ON [Comp].[Event]
(
	[EventType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Event_PaymentIn] ON [Comp].[Event]
(
	[PaymentInID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_Event_PaymentOut] ON [Comp].[Event]
(
	[PaymentOutID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_EventServiceLog_EntityId] ON [Comp].[EventServiceLog]
(
	[EntityID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IXFK_EventServiceLog_Event] ON [Comp].[EventServiceLog]
(
	[EventID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ANSI_PADDING ON


CREATE UNIQUE NONCLUSTERED INDEX [UQ_EventTypeEnum_Type] ON [Comp].[EventTypeEnum]
(
	[Type] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ANSI_PADDING ON


CREATE NONCLUSTERED INDEX [IX_PaymentIn_FilteredOnHold] ON [Comp].[PaymentIn]
(
	[Deleted] ASC
)
INCLUDE ( 	[AccountID],
	[ContactID],
	[TradeContractNumber],
	[BlacklistStatus],
	[FraugsterStatus],
	[SanctionStatus],
	[CustomCheckStatus]) 
WHERE ([COmplianceStatus]=(4))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE UNIQUE NONCLUSTERED INDEX [UQ_PaymentIn_ORG_PYMTIID] ON [Comp].[PaymentIn]
(
	[OrganizationID] ASC,
	[TradePaymentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF


CREATE NONCLUSTERED INDEX [IX_PaymentInAttribute_vColumns] ON [Comp].[PaymentInAttribute]
(
	[CreatedOn] ASC
)
INCLUDE ( 	[vTransactionCurrency],
	[vTransactionAmount],
	[vBaseCurrencyAmount],
	[vPaymentMethod],
	[vCountryOfPayment],
	[vThirdPartyPayment],
	[vDebitorName]) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ANSI_PADDING ON


CREATE NONCLUSTERED INDEX [IX_PaymentOut_FilteredOnHold] ON [Comp].[PaymentOut]
(
	[Deleted] ASC
)
INCLUDE ( 	[AccountID],
	[ContactID],
	[ContractNumber],
	[BlacklistStatus],
	[FraugsterStatus],
	[SanctionStatus],
	[CustomCheckStatus]) 
WHERE ([COmplianceStatus]=(4))
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_PaymentOut_TransactionDate] ON [Comp].[PaymentOut]
(
	[TransactionDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF


CREATE NONCLUSTERED INDEX [IX_PaymentOutAttribute_vColumns] ON [Comp].[PaymentOutAttribute]
(
	[CreatedOn] ASC
)
INCLUDE ( 	[vValueDate],
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
	[vBeneficiaryAmount]) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_CompProfileActivityLog_ContactId] ON [Comp].[ProfileActivityLog]
(
	[ContactID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

CREATE NONCLUSTERED INDEX [IX_CompProfileActivityLogDetail_ContactId] ON [Comp].[ProfileActivityLogDetail]
(
	[ActivityID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

/****** Object:  Index [IX_UserResourceLock_FilteredUnlock_ResourceId]    Script Date: 13/04/2017 09:28:28 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_UserResourceLock_FilteredUnlock_ResourceId] ON [Comp].[UserResourceLock]
(
 [ResourceType] ASC,
 [ResourceID] ASC
)
INCLUDE (  [CreatedBy],
 [CreatedOn],
 [WorkflowTime], [lockreleasedon])
WHERE ([lockreleasedon] IS NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100) ON [Idx]
GO
SET ANSI_PADDING ON


CREATE UNIQUE NONCLUSTERED INDEX [UQ_WatchListReason] ON [Comp].[WatchList]
(
	[Reason] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [Idx]

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF

/****** Object:  Index [IX_vCustomer]    Script Date: 13/04/2017 09:18:05 ******/
CREATE UNIQUE CLUSTERED INDEX [IX_vCustomer] ON [Comp].[vCustomer]
(
 [ContactId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100) ON [Idx]
GO

SET ARITHABORT ON
SET CONCAT_NULL_YIELDS_NULL ON
SET QUOTED_IDENTIFIER ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
SET NUMERIC_ROUNDABORT OFF

GO

/****** Object:  Index [IX_vCustomer_UpdatedOn]    Script Date: 13/04/2017 09:18:05 ******/
CREATE NONCLUSTERED INDEX [IX_vCustomer_UpdatedOn] ON [Comp].[vCustomer]
(
 [UpdatedOn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100) ON [Idx]
GO
CREATE FULLTEXT INDEX ON [Comp].[AccountAttribute](
[Attributes] LANGUAGE 'English')
KEY INDEX [PK_AccountAttribute]ON ([AccountAttributes], FILEGROUP [Ftsi])
WITH (CHANGE_TRACKING = AUTO, STOPLIST = SYSTEM)

/****** Object:  Index [IX_vCustomer_UpdatedOn]    Script Date: 13/04/2017 09:31:47 ******/
CREATE NONCLUSTERED INDEX [IX_vCustomer_Type] ON [Comp].[vCustomer]
(
 Type
)
include (UpdatedOn)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100) ON [Idx]
GO


CREATE FULLTEXT INDEX ON [Comp].[ContactAttribute](
[Attributes] LANGUAGE 'English')
KEY INDEX [PK_ContactAttribute]ON ([ContactAttributes], FILEGROUP [Ftsi])
WITH (CHANGE_TRACKING = AUTO, STOPLIST = SYSTEM)



CREATE FULLTEXT INDEX ON [Comp].[PaymentInAttribute](
[Attributes] LANGUAGE 'English')
KEY INDEX [PK_PaymentInAttribute]ON ([PaymentInAttributes], FILEGROUP [Ftsi])
WITH (CHANGE_TRACKING = AUTO, STOPLIST = SYSTEM)



CREATE FULLTEXT INDEX ON [Comp].[PaymentOutAttribute](
[Attributes] LANGUAGE 'English')
KEY INDEX [PK_PaymentOutAttribute]ON ([PaymentoutAttributes], FILEGROUP [Ftsi])
WITH (CHANGE_TRACKING = AUTO, STOPLIST = SYSTEM)


---
ALTER TABLE [Comp].[Account] ADD  CONSTRAINT [DF_Account_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Account] ADD  CONSTRAINT [DF_Account_WatchListStatus]  DEFAULT ((0)) FOR [WatchListStatus]

ALTER TABLE [Comp].[AccountAttributeHistory] ADD  CONSTRAINT [DF_AccountAttributeHistory_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[AccountComplianceStatusEnum] ADD  CONSTRAINT [DF_AccountComplianceStatusEnum_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ActivityTypeEnum] ADD  CONSTRAINT [DF_ActivityTypeEnum_Active]  DEFAULT ((1)) FOR [Active]

ALTER TABLE [Comp].[ActivityTypeEnum] ADD  CONSTRAINT [DF_ActivityTypeEnum_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[BlackListData] ADD  CONSTRAINT [DF_BlackListData_Created_On]  DEFAULT (getdate()) FOR [Created_On]

ALTER TABLE [Comp].[BlackListData] ADD  CONSTRAINT [DF_BlackListData_Updated_On]  DEFAULT (getdate()) FOR [Updated_On]

ALTER TABLE [Comp].[BlackListType] ADD  CONSTRAINT [DF_BlackListType_Relevance]  DEFAULT ((0)) FOR [Relevance]

ALTER TABLE [Comp].[Compliance_ServiceProvider] ADD  CONSTRAINT [DF_Compliance_ServiceProvider_Internal]  DEFAULT ((0)) FOR [Internal]

ALTER TABLE [Comp].[Compliance_ServiceProvider] ADD  CONSTRAINT [DF_Compliance_ServiceProvider_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Compliance_ServiceProviderAttribute] ADD  CONSTRAINT [DF_Compliance_ServiceProviderAttribute_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Compliance_ServiceTypeEnum] ADD  CONSTRAINT [DF_Compliance_ServiceTypeEnum_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Contact] ADD  CONSTRAINT [DF_Contact_Primary]  DEFAULT ((0)) FOR [Primary]

ALTER TABLE [Comp].[Contact] ADD  CONSTRAINT [DF_Contact_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ContactAttribute] ADD  CONSTRAINT [DF_ContactAttribute_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ContactComplianceHistory] ADD  CONSTRAINT [DF_ContactComplianceHistory_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ContactComplianceStatusEnum] ADD  CONSTRAINT [DF_ContactComplianceStatusEnum_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ContactWatchList] ADD  CONSTRAINT [DF_ContactWatchList_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Country] ADD  CONSTRAINT [DF_Country_RiskLevel]  DEFAULT ('H') FOR [RiskLevel]

ALTER TABLE [Comp].[Country] ADD  CONSTRAINT [DF_Country_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[CountryState] ADD  CONSTRAINT [DF_CountryState_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Currency] ADD  CONSTRAINT [DF_Currency_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[CurrencyCategory] ADD  CONSTRAINT [DF_CurrencyCategory_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[DeviceInfo] ADD  CONSTRAINT [DF_DeviceInfo_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[EntityTypeEnum] ADD  CONSTRAINT [DF_EntityTypeEnum_CreatedBy]  DEFAULT ((1)) FOR [CreatedBy]

ALTER TABLE [Comp].[EntityTypeEnum] ADD  CONSTRAINT [DF_EntityTypeEnum_CreatedOn]  DEFAULT (getdate()) FOR [CreatedON]

ALTER TABLE [Comp].[EntityTypeEnum] ADD  CONSTRAINT [DF_EntityTypeEnum_UpdatedBy]  DEFAULT ((1)) FOR [UpdatedBy]

ALTER TABLE [Comp].[EntityTypeEnum] ADD  CONSTRAINT [DF_EntityTypeEnum_UpdatedOn]  DEFAULT (getdate()) FOR [UpdatedOn]

ALTER TABLE [Comp].[EventServiceLog] ADD  CONSTRAINT [DF_EventServiceLog_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Function] ADD  CONSTRAINT [DF_Function_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[FunctionGroup] ADD  CONSTRAINT [DF_FunctionGroup_Active]  DEFAULT ((1)) FOR [Active]

ALTER TABLE [Comp].[FunctionGroup] ADD  CONSTRAINT [DF_FunctionGroup_Deleted]  DEFAULT ((0)) FOR [Deleted]

ALTER TABLE [Comp].[FunctionGroup] ADD  CONSTRAINT [DF_FunctionGroup_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[FunctionGroupMapping] ADD  CONSTRAINT [DF_FunctionGroupMapping_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Organization] ADD  CONSTRAINT [DF_Organization_Locked]  DEFAULT ((0)) FOR [Locked]

ALTER TABLE [Comp].[Organization] ADD  CONSTRAINT [DF_Organization_Deleted]  DEFAULT ((0)) FOR [Deleted]

ALTER TABLE [Comp].[Organization] ADD  CONSTRAINT [DF_Organization_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[OrganizationNew] ADD  CONSTRAINT [DF_OrganizationNew_Locked]  DEFAULT ((0)) FOR [Locked]

ALTER TABLE [Comp].[OrganizationNew] ADD  CONSTRAINT [DF_OrganizationNew_Deleted]  DEFAULT ((0)) FOR [Deleted]

ALTER TABLE [Comp].[OrganizationNew] ADD  CONSTRAINT [DF_OrganizationNew_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[OverideFunction] ADD  CONSTRAINT [DF_OverideFunction_Include]  DEFAULT ((0)) FOR [Include]

ALTER TABLE [Comp].[OverideFunction] ADD  CONSTRAINT [DF_OverideFunction_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[PaymentComplianceStatusEnum] ADD  CONSTRAINT [DF_PaymentComplianceStatusEnum_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[PaymentOut] ADD  CONSTRAINT [DF_PaymentOut_Deleted]  DEFAULT ((0)) FOR [Deleted]

ALTER TABLE [Comp].[PaymentOutStatusReason] ADD  CONSTRAINT [DF_PaymentOutStatusReason_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ProfileActivityLog] ADD  CONSTRAINT [DF_ProfileActivityLog_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ProfileActivityLogDetail] ADD  CONSTRAINT [DF_ProfileActivityLogDetail_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ProfileStatusReason] ADD  CONSTRAINT [DF_ProfileStatusReason_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Role] ADD  CONSTRAINT [DF_Role_Active]  DEFAULT ((1)) FOR [Active]

ALTER TABLE [Comp].[Role] ADD  CONSTRAINT [DF_Role_Deleted]  DEFAULT ((0)) FOR [Deleted]

ALTER TABLE [Comp].[Role] ADD  CONSTRAINT [DF_Role_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[RoleFunctionGroupMapping] ADD  CONSTRAINT [DF_RoleFunctionGroupMapping_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[ServiceStatusEnum] ADD  CONSTRAINT [DF_ServiceStatusEnum_CreatedBy]  DEFAULT ((1)) FOR [CreatedBy]

ALTER TABLE [Comp].[ServiceStatusEnum] ADD  CONSTRAINT [DF_ServiceStatusEnum_CreatedOn]  DEFAULT (getdate()) FOR [CreatedON]

ALTER TABLE [Comp].[ServiceStatusEnum] ADD  CONSTRAINT [DF_ServiceStatusEnum_UpdatedBy]  DEFAULT ((1)) FOR [UpdatedBy]

ALTER TABLE [Comp].[ServiceStatusEnum] ADD  CONSTRAINT [DF_ServiceStatusEnum_UpdatedOn]  DEFAULT (getdate()) FOR [UpdatedOn]

ALTER TABLE [Comp].[StatusBroadCastQueue] ADD  DEFAULT ((0)) FOR [RetryCount]

ALTER TABLE [Comp].[StatusBroadCastQueue] ADD  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[StatusUpdateReason] ADD  CONSTRAINT [DF_StatusUpdateReason_Active]  DEFAULT ((1)) FOR [Active]

ALTER TABLE [Comp].[StatusUpdateReason] ADD  CONSTRAINT [DF_StatusUpdateReason_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[User] ADD  CONSTRAINT [DF_User_Active]  DEFAULT ((1)) FOR [Active]

ALTER TABLE [Comp].[User] ADD  DEFAULT ((0)) FOR [Deleted]

ALTER TABLE [Comp].[User] ADD  CONSTRAINT [DF_User_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[UserResourceLock] ADD  CONSTRAINT [DF_UserResourceLock_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[UserResourceLock] ADD  CONSTRAINT [DF_UserResourceLock_ResourceType]  DEFAULT ((1)) FOR [ResourceType]

ALTER TABLE [Comp].[VelocityRules] ADD  CONSTRAINT [DF_VelocityRules_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[WatchList] ADD  CONSTRAINT [DF_WatchList_StopPaymentIn]  DEFAULT ((0)) FOR [StopPaymentIn]

ALTER TABLE [Comp].[WatchList] ADD  CONSTRAINT [DF_WatchList_StopPaymentOut]  DEFAULT ((0)) FOR [StopPaymentOut]

ALTER TABLE [Comp].[WatchList] ADD  CONSTRAINT [DF_WatchList_Locked]  DEFAULT ((0)) FOR [Locked]

ALTER TABLE [Comp].[WatchList] ADD  CONSTRAINT [DF_WatchList_Deleted]  DEFAULT ((0)) FOR [Deleted]

ALTER TABLE [Comp].[WatchList] ADD  CONSTRAINT [DF_WatchList_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn]

ALTER TABLE [Comp].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_AccountComplianceStatusEnum_ComplianceStatus] FOREIGN KEY([ComplianceStatus])
REFERENCES [Comp].[AccountComplianceStatusEnum] ([ID])

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [FK_Account_AccountComplianceStatusEnum_ComplianceStatus]

ALTER TABLE [Comp].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_BlackListStatus_ServiceStatusEnum_Id] FOREIGN KEY([BlacklistStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [FK_Account_BlackListStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_EIDStatus_ServiceStatusEnum_Id] FOREIGN KEY([EIDStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [FK_Account_EIDStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_FraugsterStatus_ServiceStatusEnum_Id] FOREIGN KEY([FraugsterStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [FK_Account_FraugsterStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[Account]  WITH NOCHECK ADD  CONSTRAINT [FK_Account_Organization_OrganizationID] FOREIGN KEY([OrganizationID])
REFERENCES [Comp].[Organization] ([ID])

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [FK_Account_Organization_OrganizationID]

ALTER TABLE [Comp].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_SanctionStatus_ServiceStatusEnum_Id] FOREIGN KEY([SanctionStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [FK_Account_SanctionStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[Account]  WITH NOCHECK ADD  CONSTRAINT [FK_Account_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [FK_Account_User_CreatedBy]

ALTER TABLE [Comp].[Account]  WITH NOCHECK ADD  CONSTRAINT [FK_Account_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [FK_Account_User_UpdatedBy]

ALTER TABLE [Comp].[AccountAttribute]  WITH NOCHECK ADD  CONSTRAINT [FK_AccountAttribute_Account_ID] FOREIGN KEY([ID])
REFERENCES [Comp].[Account] ([ID])

ALTER TABLE [Comp].[AccountAttribute] CHECK CONSTRAINT [FK_AccountAttribute_Account_ID]

ALTER TABLE [Comp].[AccountAttribute]  WITH NOCHECK ADD  CONSTRAINT [FK_AccountAttribute_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[AccountAttribute] CHECK CONSTRAINT [FK_AccountAttribute_User_CreatedBy]

ALTER TABLE [Comp].[AccountAttribute]  WITH NOCHECK ADD  CONSTRAINT [FK_AccountAttribute_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[AccountAttribute] CHECK CONSTRAINT [FK_AccountAttribute_User_UpdatedBy]

ALTER TABLE [Comp].[AccountAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_AccountAttributeHistory_AccountAttribute_AccountID] FOREIGN KEY([AccountID])
REFERENCES [Comp].[AccountAttribute] ([ID])

ALTER TABLE [Comp].[AccountAttributeHistory] CHECK CONSTRAINT [FK_AccountAttributeHistory_AccountAttribute_AccountID]

ALTER TABLE [Comp].[AccountAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_AccountAttributeHistory_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[AccountAttributeHistory] CHECK CONSTRAINT [FK_AccountAttributeHistory_User_CreatedBy]

ALTER TABLE [Comp].[AccountComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_AccountComplianceHistory_Account_AccountID] FOREIGN KEY([AccountID])
REFERENCES [Comp].[Account] ([ID])

ALTER TABLE [Comp].[AccountComplianceHistory] CHECK CONSTRAINT [FK_AccountComplianceHistory_Account_AccountID]

ALTER TABLE [Comp].[AccountComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_AccountComplianceHistory_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[AccountComplianceHistory] CHECK CONSTRAINT [FK_AccountComplianceHistory_User_CreatedBy]

ALTER TABLE [Comp].[AccountComplianceStatusEnum]  WITH CHECK ADD  CONSTRAINT [FK_AccountComplianceStatusEnum_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[AccountComplianceStatusEnum] CHECK CONSTRAINT [FK_AccountComplianceStatusEnum_User_CreatedBy]

ALTER TABLE [Comp].[AccountComplianceStatusEnum]  WITH CHECK ADD  CONSTRAINT [FK_AccountComplianceStatusEnum_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[AccountComplianceStatusEnum] CHECK CONSTRAINT [FK_AccountComplianceStatusEnum_User_UpdatedBy]

ALTER TABLE [Comp].[ActivityTypeEnum]  WITH CHECK ADD  CONSTRAINT [FK_ActivityTypeEnum_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ActivityTypeEnum] CHECK CONSTRAINT [FK_ActivityTypeEnum_User_CreatedBy]

ALTER TABLE [Comp].[ActivityTypeEnum]  WITH CHECK ADD  CONSTRAINT [FK_ActivityTypeEnum_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ActivityTypeEnum] CHECK CONSTRAINT [FK_ActivityTypeEnum_User_UpdatedBy]

ALTER TABLE [Comp].[BlackListData]  WITH CHECK ADD FOREIGN KEY([BlackListType])
REFERENCES [Comp].[BlackListType] ([ID])

ALTER TABLE [Comp].[BlackListData]  WITH CHECK ADD  CONSTRAINT [FK_BlackListData_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[BlackListData] CHECK CONSTRAINT [FK_BlackListData_User_CreatedBy]

ALTER TABLE [Comp].[BlackListData]  WITH CHECK ADD  CONSTRAINT [FK_BlackListData_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[BlackListData] CHECK CONSTRAINT [FK_BlackListData_User_UpdatedBy]

ALTER TABLE [Comp].[BlackListType]  WITH CHECK ADD  CONSTRAINT [FK_BlackListType_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[BlackListType] CHECK CONSTRAINT [FK_BlackListType_User_CreatedBy]

ALTER TABLE [Comp].[BlackListType]  WITH CHECK ADD  CONSTRAINT [FK_BlackListType_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[BlackListType] CHECK CONSTRAINT [FK_BlackListType_User_UpdatedBy]

ALTER TABLE [Comp].[Compliance_ServiceProvider]  WITH CHECK ADD  CONSTRAINT [FK_Compliance_ServiceProvider_Compliance_ServiceTypeEnum_ServiceType] FOREIGN KEY([ServiceType])
REFERENCES [Comp].[Compliance_ServiceTypeEnum] ([ID])

ALTER TABLE [Comp].[Compliance_ServiceProvider] CHECK CONSTRAINT [FK_Compliance_ServiceProvider_Compliance_ServiceTypeEnum_ServiceType]

ALTER TABLE [Comp].[Compliance_ServiceProvider]  WITH CHECK ADD  CONSTRAINT [FK_Compliance_ServiceProvider_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Compliance_ServiceProvider] CHECK CONSTRAINT [FK_Compliance_ServiceProvider_User_CreatedBy]

ALTER TABLE [Comp].[Compliance_ServiceProvider]  WITH CHECK ADD  CONSTRAINT [FK_Compliance_ServiceProvider_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Compliance_ServiceProvider] CHECK CONSTRAINT [FK_Compliance_ServiceProvider_User_UpdatedBy]

ALTER TABLE [Comp].[Compliance_ServiceProviderAttribute]  WITH CHECK ADD  CONSTRAINT [FK_Compliance_ServiceProviderAttribute_Compliance_ServiceProvider_ID] FOREIGN KEY([ID])
REFERENCES [Comp].[Compliance_ServiceProvider] ([ID])

ALTER TABLE [Comp].[Compliance_ServiceProviderAttribute] CHECK CONSTRAINT [FK_Compliance_ServiceProviderAttribute_Compliance_ServiceProvider_ID]

ALTER TABLE [Comp].[Compliance_ServiceProviderAttribute]  WITH CHECK ADD  CONSTRAINT [FK_Compliance_ServiceProviderAttribute_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Compliance_ServiceProviderAttribute] CHECK CONSTRAINT [FK_Compliance_ServiceProviderAttribute_User_CreatedBy]

ALTER TABLE [Comp].[Compliance_ServiceProviderAttribute]  WITH CHECK ADD  CONSTRAINT [FK_Compliance_ServiceProviderAttribute_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Compliance_ServiceProviderAttribute] CHECK CONSTRAINT [FK_Compliance_ServiceProviderAttribute_User_UpdatedBy]

ALTER TABLE [Comp].[Compliance_ServiceTypeEnum]  WITH CHECK ADD  CONSTRAINT [FK_Compliance_ServiceTypeEnum_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Compliance_ServiceTypeEnum] CHECK CONSTRAINT [FK_Compliance_ServiceTypeEnum_User_CreatedBy]

ALTER TABLE [Comp].[Compliance_ServiceTypeEnum]  WITH CHECK ADD  CONSTRAINT [FK_Compliance_ServiceTypeEnum_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Compliance_ServiceTypeEnum] CHECK CONSTRAINT [FK_Compliance_ServiceTypeEnum_User_UpdatedBy]

ALTER TABLE [Comp].[Contact]  WITH NOCHECK ADD  CONSTRAINT [FK_Contact_Account_AccountID] FOREIGN KEY([AccountID])
REFERENCES [Comp].[Account] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_Account_AccountID]

ALTER TABLE [Comp].[Contact]  WITH CHECK ADD  CONSTRAINT [FK_Contact_BlackListStatus_ServiceStatusEnum_Id] FOREIGN KEY([BlacklistStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_BlackListStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[Contact]  WITH NOCHECK ADD  CONSTRAINT [FK_Contact_ContactComplianceStatusEnum_ComplianceStatus] FOREIGN KEY([ComplianceStatus])
REFERENCES [Comp].[ContactComplianceStatusEnum] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_ContactComplianceStatusEnum_ComplianceStatus]

ALTER TABLE [Comp].[Contact]  WITH NOCHECK ADD  CONSTRAINT [FK_Contact_Country_Country] FOREIGN KEY([Country])
REFERENCES [Comp].[Country] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_Country_Country]

ALTER TABLE [Comp].[Contact]  WITH CHECK ADD  CONSTRAINT [FK_Contact_FraugsterStatus_ServiceStatusEnum_Id] FOREIGN KEY([FraugsterStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_FraugsterStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[Contact]  WITH NOCHECK ADD  CONSTRAINT [FK_Contact_Organization_OrganizationID] FOREIGN KEY([OrganizationID])
REFERENCES [Comp].[Organization] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_Organization_OrganizationID]

ALTER TABLE [Comp].[Contact]  WITH CHECK ADD  CONSTRAINT [FK_Contact_SanctionStatus_ServiceStatusEnum_Id] FOREIGN KEY([SanctionStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_SanctionStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[Contact]  WITH NOCHECK ADD  CONSTRAINT [FK_Contact_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_User_CreatedBy]

ALTER TABLE [Comp].[Contact]  WITH NOCHECK ADD  CONSTRAINT [FK_Contact_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Contact] CHECK CONSTRAINT [FK_Contact_User_UpdatedBy]

ALTER TABLE [Comp].[ContactAttribute]  WITH NOCHECK ADD  CONSTRAINT [FK_ContactAttribute_Contact_ID] FOREIGN KEY([ID])
REFERENCES [Comp].[Contact] ([ID])

ALTER TABLE [Comp].[ContactAttribute] CHECK CONSTRAINT [FK_ContactAttribute_Contact_ID]

ALTER TABLE [Comp].[ContactAttribute]  WITH NOCHECK ADD  CONSTRAINT [FK_ContactAttribute_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ContactAttribute] CHECK CONSTRAINT [FK_ContactAttribute_User_CreatedBy]

ALTER TABLE [Comp].[ContactAttribute]  WITH NOCHECK ADD  CONSTRAINT [FK_ContactAttribute_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ContactAttribute] CHECK CONSTRAINT [FK_ContactAttribute_User_UpdatedBy]

ALTER TABLE [Comp].[ContactAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_ContactAttributeHistory_ContactAttribute_ContactID] FOREIGN KEY([ContactID])
REFERENCES [Comp].[ContactAttribute] ([ID])

ALTER TABLE [Comp].[ContactAttributeHistory] CHECK CONSTRAINT [FK_ContactAttributeHistory_ContactAttribute_ContactID]

ALTER TABLE [Comp].[ContactAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_ContactAttributeHistory_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ContactAttributeHistory] CHECK CONSTRAINT [FK_ContactAttributeHistory_User_CreatedBy]

ALTER TABLE [Comp].[ContactComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_ContactComplianceHistory_Contact_ContactID] FOREIGN KEY([ContactID])
REFERENCES [Comp].[Contact] ([ID])

ALTER TABLE [Comp].[ContactComplianceHistory] CHECK CONSTRAINT [FK_ContactComplianceHistory_Contact_ContactID]

ALTER TABLE [Comp].[ContactComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_ContactComplianceHistory_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ContactComplianceHistory] CHECK CONSTRAINT [FK_ContactComplianceHistory_User_CreatedBy]

ALTER TABLE [Comp].[ContactComplianceStatusEnum]  WITH CHECK ADD  CONSTRAINT [FK_ContactComplianceStatusEnum_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ContactComplianceStatusEnum] CHECK CONSTRAINT [FK_ContactComplianceStatusEnum_User_CreatedBy]

ALTER TABLE [Comp].[ContactComplianceStatusEnum]  WITH CHECK ADD  CONSTRAINT [FK_ContactComplianceStatusEnum_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ContactComplianceStatusEnum] CHECK CONSTRAINT [FK_ContactComplianceStatusEnum_User_UpdatedBy]

ALTER TABLE [Comp].[ContactWatchList]  WITH CHECK ADD  CONSTRAINT [FK_ContactWatchList_Contact_Contact] FOREIGN KEY([Contact])
REFERENCES [Comp].[Contact] ([ID])

ALTER TABLE [Comp].[ContactWatchList] CHECK CONSTRAINT [FK_ContactWatchList_Contact_Contact]

ALTER TABLE [Comp].[ContactWatchList]  WITH CHECK ADD  CONSTRAINT [FK_ContactWatchList_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ContactWatchList] CHECK CONSTRAINT [FK_ContactWatchList_User_CreatedBy]

ALTER TABLE [Comp].[ContactWatchList]  WITH CHECK ADD  CONSTRAINT [FK_ContactWatchList_WatchList_Reason] FOREIGN KEY([Reason])
REFERENCES [Comp].[WatchList] ([ID])

ALTER TABLE [Comp].[ContactWatchList] CHECK CONSTRAINT [FK_ContactWatchList_WatchList_Reason]

ALTER TABLE [Comp].[Country]  WITH CHECK ADD  CONSTRAINT [FK_Country_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Country] CHECK CONSTRAINT [FK_Country_User_CreatedBy]

ALTER TABLE [Comp].[CountryState]  WITH CHECK ADD  CONSTRAINT [FK_CountryState_Country_Country] FOREIGN KEY([Country])
REFERENCES [Comp].[Country] ([ID])

ALTER TABLE [Comp].[CountryState] CHECK CONSTRAINT [FK_CountryState_Country_Country]

ALTER TABLE [Comp].[CountryState]  WITH CHECK ADD  CONSTRAINT [FK_CountryState_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[CountryState] CHECK CONSTRAINT [FK_CountryState_User_CreatedBy]

ALTER TABLE [Comp].[Currency]  WITH CHECK ADD  CONSTRAINT [FK_Currency_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Currency] CHECK CONSTRAINT [FK_Currency_User_CreatedBy]

ALTER TABLE [Comp].[CurrencyCategory]  WITH CHECK ADD  CONSTRAINT [FK_CurrencyCategory_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[CurrencyCategory] CHECK CONSTRAINT [FK_CurrencyCategory_User_CreatedBy]

ALTER TABLE [Comp].[DeviceInfo]  WITH CHECK ADD  CONSTRAINT [FK_DeviceInfo_Event_EventID] FOREIGN KEY([EventID])
REFERENCES [Comp].[Event] ([ID])

ALTER TABLE [Comp].[DeviceInfo] CHECK CONSTRAINT [FK_DeviceInfo_Event_EventID]

ALTER TABLE [Comp].[DeviceInfo]  WITH CHECK ADD  CONSTRAINT [FK_DeviceInfo_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[DeviceInfo] CHECK CONSTRAINT [FK_DeviceInfo_User_CreatedBy]

ALTER TABLE [Comp].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Document] CHECK CONSTRAINT [FK_Document_User_CreatedBy]

ALTER TABLE [Comp].[Event]  WITH CHECK ADD  CONSTRAINT [FK_Event_EventTypeEnum_EventType] FOREIGN KEY([EventType])
REFERENCES [Comp].[EventTypeEnum] ([ID])

ALTER TABLE [Comp].[Event] CHECK CONSTRAINT [FK_Event_EventTypeEnum_EventType]

ALTER TABLE [Comp].[Event]  WITH CHECK ADD  CONSTRAINT [FK_Event_Organization_OrganizationID] FOREIGN KEY([OrganizationID])
REFERENCES [Comp].[Organization] ([ID])

ALTER TABLE [Comp].[Event] CHECK CONSTRAINT [FK_Event_Organization_OrganizationID]

ALTER TABLE [Comp].[Event]  WITH CHECK ADD  CONSTRAINT [FK_Event_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Event] CHECK CONSTRAINT [FK_Event_User_CreatedBy]

ALTER TABLE [Comp].[EventServiceLog]  WITH CHECK ADD  CONSTRAINT [FK_EventServiceLog_Compliance_ServiceProvider_ServiceProvider] FOREIGN KEY([ServiceProvider])
REFERENCES [Comp].[Compliance_ServiceProvider] ([ID])

ALTER TABLE [Comp].[EventServiceLog] CHECK CONSTRAINT [FK_EventServiceLog_Compliance_ServiceProvider_ServiceProvider]

ALTER TABLE [Comp].[EventServiceLog]  WITH CHECK ADD  CONSTRAINT [FK_EventServiceLog_Compliance_ServiceTypeEnum_ServiceType] FOREIGN KEY([ServiceType])
REFERENCES [Comp].[Compliance_ServiceTypeEnum] ([ID])

ALTER TABLE [Comp].[EventServiceLog] CHECK CONSTRAINT [FK_EventServiceLog_Compliance_ServiceTypeEnum_ServiceType]

ALTER TABLE [Comp].[EventServiceLog]  WITH NOCHECK ADD  CONSTRAINT [FK_EventServiceLog_EntityType_EntityTypeEnum_Id] FOREIGN KEY([EntityType])
REFERENCES [Comp].[EntityTypeEnum] ([ID])

ALTER TABLE [Comp].[EventServiceLog] CHECK CONSTRAINT [FK_EventServiceLog_EntityType_EntityTypeEnum_Id]

ALTER TABLE [Comp].[EventServiceLog]  WITH CHECK ADD  CONSTRAINT [FK_EventServiceLog_Event_EventID] FOREIGN KEY([EventID])
REFERENCES [Comp].[Event] ([ID])

ALTER TABLE [Comp].[EventServiceLog] CHECK CONSTRAINT [FK_EventServiceLog_Event_EventID]

ALTER TABLE [Comp].[EventServiceLog]  WITH NOCHECK ADD  CONSTRAINT [FK_EventServiceLog_Status_ServiceStatusEnum_Id] FOREIGN KEY([Status])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[EventServiceLog] CHECK CONSTRAINT [FK_EventServiceLog_Status_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[EventServiceLog]  WITH CHECK ADD  CONSTRAINT [FK_EventServiceLog_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[EventServiceLog] CHECK CONSTRAINT [FK_EventServiceLog_User_CreatedBy]

ALTER TABLE [Comp].[EventServiceLog]  WITH CHECK ADD  CONSTRAINT [FK_EventServiceLog_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[EventServiceLog] CHECK CONSTRAINT [FK_EventServiceLog_User_UpdatedBy]

ALTER TABLE [Comp].[EventTypeEnum]  WITH CHECK ADD  CONSTRAINT [FK_EventTypeEnum_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[EventTypeEnum] CHECK CONSTRAINT [FK_EventTypeEnum_User_CreatedBy]

ALTER TABLE [Comp].[Function]  WITH CHECK ADD  CONSTRAINT [FK_Function_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Function] CHECK CONSTRAINT [FK_Function_User_CreatedBy]

ALTER TABLE [Comp].[Function]  WITH CHECK ADD  CONSTRAINT [FK_Function_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Function] CHECK CONSTRAINT [FK_Function_User_UpdatedBy]

ALTER TABLE [Comp].[FunctionGroup]  WITH CHECK ADD  CONSTRAINT [FK_FunctionGroup_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[FunctionGroup] CHECK CONSTRAINT [FK_FunctionGroup_User_CreatedBy]

ALTER TABLE [Comp].[FunctionGroup]  WITH CHECK ADD  CONSTRAINT [FK_FunctionGroup_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[FunctionGroup] CHECK CONSTRAINT [FK_FunctionGroup_User_UpdatedBy]

ALTER TABLE [Comp].[FunctionGroupMapping]  WITH CHECK ADD  CONSTRAINT [FK_FunctionGroupMapping_Function_FunctionID] FOREIGN KEY([FunctionID])
REFERENCES [Comp].[Function] ([ID])

ALTER TABLE [Comp].[FunctionGroupMapping] CHECK CONSTRAINT [FK_FunctionGroupMapping_Function_FunctionID]

ALTER TABLE [Comp].[FunctionGroupMapping]  WITH CHECK ADD  CONSTRAINT [FK_FunctionGroupMapping_FunctionGroup_FunctionGroupID] FOREIGN KEY([FunctionGroupID])
REFERENCES [Comp].[FunctionGroup] ([ID])

ALTER TABLE [Comp].[FunctionGroupMapping] CHECK CONSTRAINT [FK_FunctionGroupMapping_FunctionGroup_FunctionGroupID]

ALTER TABLE [Comp].[FunctionGroupMapping]  WITH CHECK ADD  CONSTRAINT [FK_FunctionGroupMapping_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[FunctionGroupMapping] CHECK CONSTRAINT [FK_FunctionGroupMapping_User_CreatedBy]

ALTER TABLE [Comp].[FunctionGroupMapping]  WITH CHECK ADD  CONSTRAINT [FK_FunctionGroupMapping_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[FunctionGroupMapping] CHECK CONSTRAINT [FK_FunctionGroupMapping_User_UpdatedBy]

ALTER TABLE [Comp].[KYC_CountryProviderMapping]  WITH CHECK ADD  CONSTRAINT [FK_KYC_CountryProviderMapping_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[KYC_CountryProviderMapping] CHECK CONSTRAINT [FK_KYC_CountryProviderMapping_User_CreatedBy]

ALTER TABLE [Comp].[KYC_CountryProviderMapping]  WITH CHECK ADD  CONSTRAINT [FK_KYC_CountryProviderMapping_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[KYC_CountryProviderMapping] CHECK CONSTRAINT [FK_KYC_CountryProviderMapping_User_UpdatedBy]

ALTER TABLE [Comp].[OrganizationNew]  WITH CHECK ADD  CONSTRAINT [FK_OrganizationNew_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[OrganizationNew] CHECK CONSTRAINT [FK_OrganizationNew_User_CreatedBy]

ALTER TABLE [Comp].[OrganizationNew]  WITH CHECK ADD  CONSTRAINT [FK_OrganizationNew_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[OrganizationNew] CHECK CONSTRAINT [FK_OrganizationNew_User_UpdatedBy]

ALTER TABLE [Comp].[OverideFunction]  WITH CHECK ADD  CONSTRAINT [FK_OverideFunction_Function_FunctionID] FOREIGN KEY([FunctionID])
REFERENCES [Comp].[Function] ([ID])

ALTER TABLE [Comp].[OverideFunction] CHECK CONSTRAINT [FK_OverideFunction_Function_FunctionID]

ALTER TABLE [Comp].[OverideFunction]  WITH CHECK ADD  CONSTRAINT [FK_OverideFunction_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[OverideFunction] CHECK CONSTRAINT [FK_OverideFunction_User_CreatedBy]

ALTER TABLE [Comp].[OverideFunction]  WITH CHECK ADD  CONSTRAINT [FK_OverideFunction_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[OverideFunction] CHECK CONSTRAINT [FK_OverideFunction_User_UpdatedBy]

ALTER TABLE [Comp].[OverideFunction]  WITH CHECK ADD  CONSTRAINT [FK_OverideFunction_User_UserID] FOREIGN KEY([UserID])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[OverideFunction] CHECK CONSTRAINT [FK_OverideFunction_User_UserID]

ALTER TABLE [Comp].[PaymentComplianceStatusEnum]  WITH CHECK ADD  CONSTRAINT [FK_PaymentComplianceStatusEnum_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentComplianceStatusEnum] CHECK CONSTRAINT [FK_PaymentComplianceStatusEnum_User_CreatedBy]

ALTER TABLE [Comp].[PaymentComplianceStatusEnum]  WITH CHECK ADD  CONSTRAINT [FK_PaymentComplianceStatusEnum_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentComplianceStatusEnum] CHECK CONSTRAINT [FK_PaymentComplianceStatusEnum_User_UpdatedBy]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_Account_AccountID] FOREIGN KEY([AccountID])
REFERENCES [Comp].[Account] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_Account_AccountID]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_BlackListStatus_ServiceStatusEnum_Id] FOREIGN KEY([BlacklistStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_BlackListStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_Contact_ContactID] FOREIGN KEY([ContactID])
REFERENCES [Comp].[Contact] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_Contact_ContactID]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_CustomCheckStatus_ServiceStatusEnum_Id] FOREIGN KEY([CustomCheckStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_CustomCheckStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_FraugsterStatus_ServiceStatusEnum_Id] FOREIGN KEY([FraugsterStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_FraugsterStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_Organization_OrganizationID] FOREIGN KEY([OrganizationID])
REFERENCES [Comp].[Organization] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_Organization_OrganizationID]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_SanctionStatus_ServiceStatusEnum_Id] FOREIGN KEY([SanctionStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_SanctionStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_User_CreatedBy]

ALTER TABLE [Comp].[PaymentIn]  WITH CHECK ADD  CONSTRAINT [FK_PaymentIn_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentIn] CHECK CONSTRAINT [FK_PaymentIn_User_UpdatedBy]

ALTER TABLE [Comp].[PaymentInActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInActivityLog_PaymentIn_PaymentInID] FOREIGN KEY([PaymentInID])
REFERENCES [Comp].[PaymentIn] ([ID])

ALTER TABLE [Comp].[PaymentInActivityLog] CHECK CONSTRAINT [FK_PaymentInActivityLog_PaymentIn_PaymentInID]

ALTER TABLE [Comp].[PaymentInActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInActivityLog_User_ActivityBy] FOREIGN KEY([ActivityBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInActivityLog] CHECK CONSTRAINT [FK_PaymentInActivityLog_User_ActivityBy]

ALTER TABLE [Comp].[PaymentInActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInActivityLog_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInActivityLog] CHECK CONSTRAINT [FK_PaymentInActivityLog_User_CreatedBy]

ALTER TABLE [Comp].[PaymentInActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInActivityLogDetail_ActivityTypeEnum_ActivityType] FOREIGN KEY([ActivityType])
REFERENCES [Comp].[ActivityTypeEnum] ([ID])

ALTER TABLE [Comp].[PaymentInActivityLogDetail] CHECK CONSTRAINT [FK_PaymentInActivityLogDetail_ActivityTypeEnum_ActivityType]

ALTER TABLE [Comp].[PaymentInActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInActivityLogDetail_PaymentInActivityLog_ActivityID] FOREIGN KEY([ActivityID])
REFERENCES [Comp].[PaymentInActivityLog] ([ID])

ALTER TABLE [Comp].[PaymentInActivityLogDetail] CHECK CONSTRAINT [FK_PaymentInActivityLogDetail_PaymentInActivityLog_ActivityID]

ALTER TABLE [Comp].[PaymentInActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInActivityLogDetail_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInActivityLogDetail] CHECK CONSTRAINT [FK_PaymentInActivityLogDetail_User_CreatedBy]

ALTER TABLE [Comp].[PaymentInAttribute]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInAttribute_PaymentIn_ID] FOREIGN KEY([ID])
REFERENCES [Comp].[PaymentIn] ([ID])

ALTER TABLE [Comp].[PaymentInAttribute] CHECK CONSTRAINT [FK_PaymentInAttribute_PaymentIn_ID]

ALTER TABLE [Comp].[PaymentInAttribute]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInAttribute_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInAttribute] CHECK CONSTRAINT [FK_PaymentInAttribute_User_CreatedBy]

ALTER TABLE [Comp].[PaymentInAttribute]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInAttribute_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInAttribute] CHECK CONSTRAINT [FK_PaymentInAttribute_User_UpdatedBy]

ALTER TABLE [Comp].[PaymentInAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInAttributeHistory_PaymentIn_PaymentInID] FOREIGN KEY([PaymentInID])
REFERENCES [Comp].[PaymentIn] ([ID])

ALTER TABLE [Comp].[PaymentInAttributeHistory] CHECK CONSTRAINT [FK_PaymentInAttributeHistory_PaymentIn_PaymentInID]

ALTER TABLE [Comp].[PaymentInAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInAttributeHistory_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInAttributeHistory] CHECK CONSTRAINT [FK_PaymentInAttributeHistory_User_CreatedBy]

ALTER TABLE [Comp].[PaymentInAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInAttributeHistory_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInAttributeHistory] CHECK CONSTRAINT [FK_PaymentInAttributeHistory_User_UpdatedBy]

ALTER TABLE [Comp].[PaymentInComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInComplianceHistory_PaymentComplianceStatusEnum_Status] FOREIGN KEY([Status])
REFERENCES [Comp].[PaymentComplianceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentInComplianceHistory] CHECK CONSTRAINT [FK_PaymentInComplianceHistory_PaymentComplianceStatusEnum_Status]

ALTER TABLE [Comp].[PaymentInComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInComplianceHistory_PaymentIn_PaymentInID] FOREIGN KEY([PaymentInID])
REFERENCES [Comp].[PaymentIn] ([ID])

ALTER TABLE [Comp].[PaymentInComplianceHistory] CHECK CONSTRAINT [FK_PaymentInComplianceHistory_PaymentIn_PaymentInID]

ALTER TABLE [Comp].[PaymentInComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInComplianceHistory_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInComplianceHistory] CHECK CONSTRAINT [FK_PaymentInComplianceHistory_User_CreatedBy]

ALTER TABLE [Comp].[PaymentInStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInStatusReason_PaymentIn_PaymentInID] FOREIGN KEY([PaymentInID])
REFERENCES [Comp].[PaymentIn] ([ID])

ALTER TABLE [Comp].[PaymentInStatusReason] CHECK CONSTRAINT [FK_PaymentInStatusReason_PaymentIn_PaymentInID]

ALTER TABLE [Comp].[PaymentInStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInStatusReason_StatusUpdateReason_StatusUpdateReasonID] FOREIGN KEY([StatusUpdateReasonID])
REFERENCES [Comp].[StatusUpdateReason] ([ID])

ALTER TABLE [Comp].[PaymentInStatusReason] CHECK CONSTRAINT [FK_PaymentInStatusReason_StatusUpdateReason_StatusUpdateReasonID]

ALTER TABLE [Comp].[PaymentInStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInStatusReason_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInStatusReason] CHECK CONSTRAINT [FK_PaymentInStatusReason_User_CreatedBy]

ALTER TABLE [Comp].[PaymentInStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_PaymentInStatusReason_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentInStatusReason] CHECK CONSTRAINT [FK_PaymentInStatusReason_User_UpdatedBy]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_Account_AccountID] FOREIGN KEY([AccountID])
REFERENCES [Comp].[Account] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_Account_AccountID]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_BlackListStatus_ServiceStatusEnum_Id] FOREIGN KEY([BlacklistStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_BlackListStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_Contact_ContactID] FOREIGN KEY([ContactID])
REFERENCES [Comp].[Contact] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_Contact_ContactID]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_CustomCheckStatus_ServiceStatusEnum_Id] FOREIGN KEY([CustomCheckStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_CustomCheckStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_FraugsterStatus_ServiceStatusEnum_Id] FOREIGN KEY([FraugsterStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_FraugsterStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_PaymentComplianceStatusEnum_ComplianceStatus] FOREIGN KEY([ComplianceStatus])
REFERENCES [Comp].[PaymentComplianceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_PaymentComplianceStatusEnum_ComplianceStatus]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_SanctionStatus_ServiceStatusEnum_Id] FOREIGN KEY([SanctionStatus])
REFERENCES [Comp].[ServiceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_SanctionStatus_ServiceStatusEnum_Id]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_User_CreatedBy]

ALTER TABLE [Comp].[PaymentOut]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOut_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOut] CHECK CONSTRAINT [FK_PaymentOut_User_UpdatedBy]

ALTER TABLE [Comp].[PaymentOutActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutActivityLog_PaymentOut_PaymentOutID] FOREIGN KEY([PaymentOutID])
REFERENCES [Comp].[PaymentOut] ([ID])

ALTER TABLE [Comp].[PaymentOutActivityLog] CHECK CONSTRAINT [FK_PaymentOutActivityLog_PaymentOut_PaymentOutID]

ALTER TABLE [Comp].[PaymentOutActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutActivityLog_User_ActivityBy] FOREIGN KEY([ActivityBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutActivityLog] CHECK CONSTRAINT [FK_PaymentOutActivityLog_User_ActivityBy]

ALTER TABLE [Comp].[PaymentOutActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutActivityLog_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutActivityLog] CHECK CONSTRAINT [FK_PaymentOutActivityLog_User_CreatedBy]

ALTER TABLE [Comp].[PaymentOutActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutActivityLogDetail_ActivityTypeEnum_ActivityType] FOREIGN KEY([ActivityType])
REFERENCES [Comp].[ActivityTypeEnum] ([ID])

ALTER TABLE [Comp].[PaymentOutActivityLogDetail] CHECK CONSTRAINT [FK_PaymentOutActivityLogDetail_ActivityTypeEnum_ActivityType]

ALTER TABLE [Comp].[PaymentOutActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutActivityLogDetail_PaymentOutActivityLog_ActivityID] FOREIGN KEY([ActivityID])
REFERENCES [Comp].[PaymentOutActivityLog] ([ID])

ALTER TABLE [Comp].[PaymentOutActivityLogDetail] CHECK CONSTRAINT [FK_PaymentOutActivityLogDetail_PaymentOutActivityLog_ActivityID]

ALTER TABLE [Comp].[PaymentOutActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutActivityLogDetail_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutActivityLogDetail] CHECK CONSTRAINT [FK_PaymentOutActivityLogDetail_User_CreatedBy]

ALTER TABLE [Comp].[PaymentOutAttribute]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutAttribute_PaymentOut_ID] FOREIGN KEY([ID])
REFERENCES [Comp].[PaymentOut] ([ID])

ALTER TABLE [Comp].[PaymentOutAttribute] CHECK CONSTRAINT [FK_PaymentOutAttribute_PaymentOut_ID]

ALTER TABLE [Comp].[PaymentOutAttribute]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutAttribute_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutAttribute] CHECK CONSTRAINT [FK_PaymentOutAttribute_User_CreatedBy]

ALTER TABLE [Comp].[PaymentOutAttribute]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutAttribute_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutAttribute] CHECK CONSTRAINT [FK_PaymentOutAttribute_User_UpdatedBy]

ALTER TABLE [Comp].[PaymentOutAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutAttributeHistory_PaymentOut_PaymentOutID] FOREIGN KEY([PaymentOutID])
REFERENCES [Comp].[PaymentOut] ([ID])

ALTER TABLE [Comp].[PaymentOutAttributeHistory] CHECK CONSTRAINT [FK_PaymentOutAttributeHistory_PaymentOut_PaymentOutID]

ALTER TABLE [Comp].[PaymentOutAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutAttributeHistory_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutAttributeHistory] CHECK CONSTRAINT [FK_PaymentOutAttributeHistory_User_CreatedBy]

ALTER TABLE [Comp].[PaymentOutAttributeHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutAttributeHistory_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutAttributeHistory] CHECK CONSTRAINT [FK_PaymentOutAttributeHistory_User_UpdatedBy]

ALTER TABLE [Comp].[PaymentOutComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutComplianceHistory_PaymentComplianceStatusEnum_Status] FOREIGN KEY([Status])
REFERENCES [Comp].[PaymentComplianceStatusEnum] ([ID])

ALTER TABLE [Comp].[PaymentOutComplianceHistory] CHECK CONSTRAINT [FK_PaymentOutComplianceHistory_PaymentComplianceStatusEnum_Status]

ALTER TABLE [Comp].[PaymentOutComplianceHistory]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutComplianceHistory_PaymentOut_PaymentOutID] FOREIGN KEY([PaymentOutID])
REFERENCES [Comp].[PaymentOut] ([ID])

ALTER TABLE [Comp].[PaymentOutComplianceHistory] CHECK CONSTRAINT [FK_PaymentOutComplianceHistory_PaymentOut_PaymentOutID]

ALTER TABLE [Comp].[PaymentOutStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutStatusReason_PaymentOut_PaymentOutID] FOREIGN KEY([PaymentOutID])
REFERENCES [Comp].[PaymentOut] ([ID])

ALTER TABLE [Comp].[PaymentOutStatusReason] CHECK CONSTRAINT [FK_PaymentOutStatusReason_PaymentOut_PaymentOutID]

ALTER TABLE [Comp].[PaymentOutStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutStatusReason_StatusUpdateReason_StatusUpdateReasonID] FOREIGN KEY([StatusUpdateReasonID])
REFERENCES [Comp].[StatusUpdateReason] ([ID])

ALTER TABLE [Comp].[PaymentOutStatusReason] CHECK CONSTRAINT [FK_PaymentOutStatusReason_StatusUpdateReason_StatusUpdateReasonID]

ALTER TABLE [Comp].[PaymentOutStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutStatusReason_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutStatusReason] CHECK CONSTRAINT [FK_PaymentOutStatusReason_User_CreatedBy]

ALTER TABLE [Comp].[PaymentOutStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOutStatusReason_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[PaymentOutStatusReason] CHECK CONSTRAINT [FK_PaymentOutStatusReason_User_UpdatedBy]

ALTER TABLE [Comp].[ProfileActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLog_Account_AccountID] FOREIGN KEY([AccountID])
REFERENCES [Comp].[Account] ([ID])

ALTER TABLE [Comp].[ProfileActivityLog] CHECK CONSTRAINT [FK_ProfileActivityLog_Account_AccountID]

ALTER TABLE [Comp].[ProfileActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLog_Contact_ContactID] FOREIGN KEY([ContactID])
REFERENCES [Comp].[Contact] ([ID])

ALTER TABLE [Comp].[ProfileActivityLog] CHECK CONSTRAINT [FK_ProfileActivityLog_Contact_ContactID]

ALTER TABLE [Comp].[ProfileActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLog_Organization_OrganizationID] FOREIGN KEY([OrganizationID])
REFERENCES [Comp].[Organization] ([ID])

ALTER TABLE [Comp].[ProfileActivityLog] CHECK CONSTRAINT [FK_ProfileActivityLog_Organization_OrganizationID]

ALTER TABLE [Comp].[ProfileActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLog_User_ActivityBy] FOREIGN KEY([ActivityBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ProfileActivityLog] CHECK CONSTRAINT [FK_ProfileActivityLog_User_ActivityBy]

ALTER TABLE [Comp].[ProfileActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLog_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ProfileActivityLog] CHECK CONSTRAINT [FK_ProfileActivityLog_User_CreatedBy]

ALTER TABLE [Comp].[ProfileActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLog_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ProfileActivityLog] CHECK CONSTRAINT [FK_ProfileActivityLog_User_UpdatedBy]

ALTER TABLE [Comp].[ProfileActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLogDetail_ActivityTypeEnum_ActivityType] FOREIGN KEY([ActivityType])
REFERENCES [Comp].[ActivityTypeEnum] ([ID])

ALTER TABLE [Comp].[ProfileActivityLogDetail] CHECK CONSTRAINT [FK_ProfileActivityLogDetail_ActivityTypeEnum_ActivityType]

ALTER TABLE [Comp].[ProfileActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLogDetail_ProfileActivityLog_ActivityID] FOREIGN KEY([ActivityID])
REFERENCES [Comp].[ProfileActivityLog] ([ID])

ALTER TABLE [Comp].[ProfileActivityLogDetail] CHECK CONSTRAINT [FK_ProfileActivityLogDetail_ProfileActivityLog_ActivityID]

ALTER TABLE [Comp].[ProfileActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLogDetail_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ProfileActivityLogDetail] CHECK CONSTRAINT [FK_ProfileActivityLogDetail_User_CreatedBy]

ALTER TABLE [Comp].[ProfileActivityLogDetail]  WITH CHECK ADD  CONSTRAINT [FK_ProfileActivityLogDetail_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ProfileActivityLogDetail] CHECK CONSTRAINT [FK_ProfileActivityLogDetail_User_UpdatedBy]

ALTER TABLE [Comp].[ProfileStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_ProfileStatusReason_Account_AccountID] FOREIGN KEY([AccountID])
REFERENCES [Comp].[Account] ([ID])

ALTER TABLE [Comp].[ProfileStatusReason] CHECK CONSTRAINT [FK_ProfileStatusReason_Account_AccountID]

ALTER TABLE [Comp].[ProfileStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_ProfileStatusReason_Contact_ContactID] FOREIGN KEY([ContactID])
REFERENCES [Comp].[Contact] ([ID])

ALTER TABLE [Comp].[ProfileStatusReason] CHECK CONSTRAINT [FK_ProfileStatusReason_Contact_ContactID]

ALTER TABLE [Comp].[ProfileStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_ProfileStatusReason_Organization_OrganizationID] FOREIGN KEY([OrganizationID])
REFERENCES [Comp].[Organization] ([ID])

ALTER TABLE [Comp].[ProfileStatusReason] CHECK CONSTRAINT [FK_ProfileStatusReason_Organization_OrganizationID]

ALTER TABLE [Comp].[ProfileStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_ProfileStatusReason_StatusUpdateReason_StatusUpdateReasonID] FOREIGN KEY([StatusUpdateReasonID])
REFERENCES [Comp].[StatusUpdateReason] ([ID])

ALTER TABLE [Comp].[ProfileStatusReason] CHECK CONSTRAINT [FK_ProfileStatusReason_StatusUpdateReason_StatusUpdateReasonID]

ALTER TABLE [Comp].[ProfileStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_ProfileStatusReason_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ProfileStatusReason] CHECK CONSTRAINT [FK_ProfileStatusReason_User_CreatedBy]

ALTER TABLE [Comp].[ProfileStatusReason]  WITH CHECK ADD  CONSTRAINT [FK_ProfileStatusReason_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[ProfileStatusReason] CHECK CONSTRAINT [FK_ProfileStatusReason_User_UpdatedBy]

ALTER TABLE [Comp].[Role]  WITH CHECK ADD  CONSTRAINT [FK_Role_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Role] CHECK CONSTRAINT [FK_Role_User_CreatedBy]

ALTER TABLE [Comp].[Role]  WITH CHECK ADD  CONSTRAINT [FK_Role_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[Role] CHECK CONSTRAINT [FK_Role_User_UpdatedBy]

ALTER TABLE [Comp].[RoleFunctionGroupMapping]  WITH CHECK ADD  CONSTRAINT [FK_RoleFunctionGroupMapping_FunctionGroup_FunctionGroupID] FOREIGN KEY([FunctionGroupID])
REFERENCES [Comp].[FunctionGroup] ([ID])

ALTER TABLE [Comp].[RoleFunctionGroupMapping] CHECK CONSTRAINT [FK_RoleFunctionGroupMapping_FunctionGroup_FunctionGroupID]

ALTER TABLE [Comp].[RoleFunctionGroupMapping]  WITH CHECK ADD  CONSTRAINT [FK_RoleFunctionGroupMapping_Role_RoleID] FOREIGN KEY([RoleID])
REFERENCES [Comp].[Role] ([ID])

ALTER TABLE [Comp].[RoleFunctionGroupMapping] CHECK CONSTRAINT [FK_RoleFunctionGroupMapping_Role_RoleID]

ALTER TABLE [Comp].[RoleFunctionGroupMapping]  WITH CHECK ADD  CONSTRAINT [FK_RoleFunctionGroupMapping_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[RoleFunctionGroupMapping] CHECK CONSTRAINT [FK_RoleFunctionGroupMapping_User_CreatedBy]

ALTER TABLE [Comp].[RoleFunctionGroupMapping]  WITH CHECK ADD  CONSTRAINT [FK_RoleFunctionGroupMapping_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[RoleFunctionGroupMapping] CHECK CONSTRAINT [FK_RoleFunctionGroupMapping_User_UpdatedBy]

ALTER TABLE [Comp].[StatusBroadCastQueue]  WITH CHECK ADD  CONSTRAINT [FK_SBCQ_AccountID] FOREIGN KEY([AccountID])
REFERENCES [Comp].[Account] ([ID])

ALTER TABLE [Comp].[StatusBroadCastQueue] CHECK CONSTRAINT [FK_SBCQ_AccountID]

ALTER TABLE [Comp].[StatusBroadCastQueue]  WITH CHECK ADD  CONSTRAINT [FK_SBCQ_ContactID] FOREIGN KEY([ContactID])
REFERENCES [Comp].[Contact] ([ID])

ALTER TABLE [Comp].[StatusBroadCastQueue] CHECK CONSTRAINT [FK_SBCQ_ContactID]

ALTER TABLE [Comp].[StatusBroadCastQueue]  WITH CHECK ADD  CONSTRAINT [FK_SBCQ_CREATEBY] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[StatusBroadCastQueue] CHECK CONSTRAINT [FK_SBCQ_CREATEBY]

ALTER TABLE [Comp].[StatusBroadCastQueue]  WITH CHECK ADD  CONSTRAINT [FK_SBCQ_OrganizationID] FOREIGN KEY([OrganizationID])
REFERENCES [Comp].[Organization] ([ID])

ALTER TABLE [Comp].[StatusBroadCastQueue] CHECK CONSTRAINT [FK_SBCQ_OrganizationID]

ALTER TABLE [Comp].[StatusBroadCastQueue]  WITH CHECK ADD  CONSTRAINT [FK_SBCQ_PIID] FOREIGN KEY([PaymentInID])
REFERENCES [Comp].[PaymentIn] ([ID])

ALTER TABLE [Comp].[StatusBroadCastQueue] CHECK CONSTRAINT [FK_SBCQ_PIID]

ALTER TABLE [Comp].[StatusBroadCastQueue]  WITH CHECK ADD  CONSTRAINT [FK_SBCQ_POID] FOREIGN KEY([PaymentOutID])
REFERENCES [Comp].[PaymentOut] ([ID])

ALTER TABLE [Comp].[StatusBroadCastQueue] CHECK CONSTRAINT [FK_SBCQ_POID]

ALTER TABLE [Comp].[StatusUpdateReason]  WITH CHECK ADD  CONSTRAINT [FK_StatusUpdateReason_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[StatusUpdateReason] CHECK CONSTRAINT [FK_StatusUpdateReason_User_CreatedBy]

ALTER TABLE [Comp].[StatusUpdateReason]  WITH CHECK ADD  CONSTRAINT [FK_StatusUpdateReason_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[StatusUpdateReason] CHECK CONSTRAINT [FK_StatusUpdateReason_User_UpdatedBy]

ALTER TABLE [Comp].[UserResourceLock]  WITH CHECK ADD  CONSTRAINT [FK_UserResourceLock_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[UserResourceLock] CHECK CONSTRAINT [FK_UserResourceLock_User_CreatedBy]

ALTER TABLE [Comp].[VelocityRules]  WITH CHECK ADD  CONSTRAINT [FK_VelocityRules_Organization_OrganizationID] FOREIGN KEY([OrganizationID])
REFERENCES [Comp].[Organization] ([ID])

ALTER TABLE [Comp].[VelocityRules] CHECK CONSTRAINT [FK_VelocityRules_Organization_OrganizationID]

ALTER TABLE [Comp].[VelocityRules]  WITH CHECK ADD  CONSTRAINT [FK_VelocityRules_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[VelocityRules] CHECK CONSTRAINT [FK_VelocityRules_User_CreatedBy]

ALTER TABLE [Comp].[VelocityRules]  WITH CHECK ADD  CONSTRAINT [FK_VelocityRules_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[VelocityRules] CHECK CONSTRAINT [FK_VelocityRules_User_UpdatedBy]

ALTER TABLE [Comp].[WatchList]  WITH CHECK ADD  CONSTRAINT [FK_WatchList_User_CreatedBy] FOREIGN KEY([CreatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[WatchList] CHECK CONSTRAINT [FK_WatchList_User_CreatedBy]

ALTER TABLE [Comp].[WatchList]  WITH CHECK ADD  CONSTRAINT [FK_WatchList_User_UpdatedBy] FOREIGN KEY([UpdatedBy])
REFERENCES [Comp].[User] ([ID])

ALTER TABLE [Comp].[WatchList] CHECK CONSTRAINT [FK_WatchList_User_UpdatedBy]

ALTER TABLE [Comp].[Account]  WITH CHECK ADD  CONSTRAINT [CK_Account_Type] CHECK  (([Type]<(4)))

ALTER TABLE [Comp].[Account] CHECK CONSTRAINT [CK_Account_Type]

ALTER TABLE [Comp].[AccountAttribute]  WITH CHECK ADD  CONSTRAINT [CK_AccountAttribute_Attributes] CHECK  ((isjson([attributes])>(0)))

ALTER TABLE [Comp].[AccountAttribute] CHECK CONSTRAINT [CK_AccountAttribute_Attributes]

ALTER TABLE [Comp].[ActivityTypeEnum]  WITH CHECK ADD  CONSTRAINT [CK_ActivityTypeEnum_Module] CHECK  (([Module]='PAYMENT_OUT' OR [Module]='PAYMENT_IN' OR [Module]='PROFILE'))

ALTER TABLE [Comp].[ActivityTypeEnum] CHECK CONSTRAINT [CK_ActivityTypeEnum_Module]

ALTER TABLE [Comp].[ContactAttribute]  WITH CHECK ADD  CONSTRAINT [CK_ContactAttribute_Attributes] CHECK  ((isjson([attributes])>(0)))

ALTER TABLE [Comp].[ContactAttribute] CHECK CONSTRAINT [CK_ContactAttribute_Attributes]

ALTER TABLE [Comp].[EventServiceLog]  WITH CHECK ADD  CONSTRAINT [CK_EventServiceLog_EntityType] CHECK  (([EntityType]<(6)))

ALTER TABLE [Comp].[EventServiceLog] CHECK CONSTRAINT [CK_EventServiceLog_EntityType]

ALTER TABLE [Comp].[PaymentInAttribute]  WITH CHECK ADD  CONSTRAINT [CK_PaymentInAttribute_Attributes] CHECK  ((isjson([attributes])>(0)))

ALTER TABLE [Comp].[PaymentInAttribute] CHECK CONSTRAINT [CK_PaymentInAttribute_Attributes]

ALTER TABLE [Comp].[PaymentOutAttribute]  WITH CHECK ADD  CONSTRAINT [CK_PaymentOuttAttribute_Attributes] CHECK  ((isjson([attributes])>(0)))

ALTER TABLE [Comp].[PaymentOutAttribute] CHECK CONSTRAINT [CK_PaymentOuttAttribute_Attributes]

ALTER TABLE [Comp].[StatusUpdateReason]  WITH CHECK ADD  CONSTRAINT [CK_StatusUpdateReason_Module] CHECK  (([Module]='ALL' OR [Module]='PAYMENT_OUT' OR [Module]='PAYMENT_IN' OR [Module]='ACCOUNT' OR [Module]='CONTACT'))

ALTER TABLE [Comp].[StatusUpdateReason] CHECK CONSTRAINT [CK_StatusUpdateReason_Module]

ALTER TABLE [Comp].[UserResourceLock]  WITH CHECK ADD  CONSTRAINT [CK_UserResourceLock_ResourceType] CHECK  (([ResourceType]<(5)))

ALTER TABLE [Comp].[UserResourceLock] CHECK CONSTRAINT [CK_UserResourceLock_ResourceType]

ALTER TABLE [Comp].[VelocityRules]  WITH CHECK ADD  CONSTRAINT [CK_VelocityRules_EventType] CHECK  (([EventType]='PAYMENT_OUT' OR [EventType]='PAYMENT_IN' OR [EventType]='ALL'))

ALTER TABLE [Comp].[VelocityRules] CHECK CONSTRAINT [CK_VelocityRules_EventType]

---
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ImplementNamingStandard] 
    @SELECT_Only        BIT = 1,
    @PrimaryKeys        BIT = 1,
    @ForeignKeys        BIT = 1,
    @Indexes            BIT = 1,
    @UniqueConstraints  BIT = 1,
    @DefaultConstraints BIT = 1,
    @CheckConstraints   BIT = 1
AS
BEGIN
    SET NOCOUNT ON;


    DECLARE @sql NVARCHAR(MAX), @cr CHAR(2);
    SELECT @sql = N'', @cr = CHAR(13) + CHAR(10);

    
    DECLARE @TableLimit TINYINT, @ColumnLimit TINYINT;
    SELECT @TableLimit = 128, @ColumnLimit = 10;


    IF @PrimaryKeys = 1
    BEGIN
        SELECT @sql = @sql + @cr + @cr + N'/* ---- Primary Keys ---- */' + @cr;
        SELECT @sql = @sql + @cr + N'EXEC sp_rename @objname = N''' 
            + SCHEMA_NAME(a.SCHEMA_Id) +'.'+ REPLACE(a.name, '''', '''''') + ''', @newname = N''PK_' 
            + LEFT(REPLACE(OBJECT_NAME(a.parent_object_id), '''', ''), @TableLimit) + ''';'
       FROM sys.key_constraints a
        WHERE a.type = 'PK'
        AND a.is_ms_shipped = 0;
    END


    IF @ForeignKeys = 1
    BEGIN
        SELECT @sql = @sql + @cr + @cr + N'/* ---- Foreign Keys ---- */' + @cr;
        SELECT @sql = @sql + @cr + N'EXEC sp_rename @objname = N''' 
             + SCHEMA_NAME(SCHEMA_Id) +'.'+ REPLACE(a.name, '''', '''''') + ''', @newname = N''FK_' 
            + LEFT(REPLACE(OBJECT_NAME(a.parent_object_id), '''', ''), @TableLimit)
            + '_' + LEFT(REPLACE(OBJECT_NAME(a.referenced_object_id), '''', ''), @TableLimit) + ''';'
        FROM sys.foreign_keys a
        WHERE a.is_ms_shipped = 0;
    END


    IF (@UniqueConstraints = 1 OR @Indexes = 1)
    BEGIN
        SELECT @sql = @sql + @cr + @cr + N'/* ---- Indexes / Unique Constraints ---- */' + @cr;
        SELECT @sql = @sql + @cr + N'EXEC sp_rename @objname = N''' + SCHEMA_NAME(b.schema_id) +'.' 
   + CASE is_unique_constraint WHEN 0 THEN
   QUOTENAME(REPLACE(OBJECT_NAME(i.[object_id]), '''', '''''')) + '.' ELSE '' END
            + QUOTENAME(REPLACE(i.name, '''', '''''')) + ''', @newname = N'''
            + CASE is_unique_constraint WHEN 1 THEN 'UQ_' ELSE 'IX_'
              + CASE is_unique WHEN 1 THEN 'U_'  ELSE '' END 
            END + CASE has_filter WHEN 1 THEN 'F_'  ELSE '' END
            + LEFT(REPLACE(OBJECT_NAME(i.[object_id]), '''', ''), @TableLimit) 
            + '_' + STUFF((SELECT '_' + LEFT(REPLACE(c.name, '''', ''), @ColumnLimit)
                FROM sys.columns AS c 
					   join sys.objects b on c.object_id = b.object_id 
                    INNER JOIN sys.index_columns AS ic
                    ON ic.column_id = c.column_id
                    AND ic.[object_id] = c.[object_id]
                WHERE ic.[object_id] = i.[object_id] 
                AND ic.index_id = i.index_id
                AND is_included_column = 0
                ORDER BY ic.index_column_id FOR XML PATH(''), 
                TYPE).value('.', 'nvarchar(max)'), 1, 1, '') +''';'
        FROM sys.indexes AS i
	   join sys.objects b on i.object_id = b.object_id 
        WHERE i.index_id > 0 AND i.is_primary_key = 0 AND i.type IN (1,2)
        AND OBJECTPROPERTY(i.[object_id], 'IsMsShipped') = 0;
    END


    IF @DefaultConstraints = 1
    BEGIN
        SELECT @sql = @sql + @cr + @cr + N'/* ---- DefaultConstraints ---- */' + @cr;
        SELECT @sql = @sql + @cr + N'EXEC sp_rename @objname = N''' + SCHEMA_NAME(schema_id) +'.' 
            + REPLACE(dc.name, '''', '''''') + ''', @newname = N''DF_' 
            + LEFT(REPLACE(OBJECT_NAME(dc.parent_object_id), '''',''), @TableLimit)
            + '_' + LEFT(REPLACE(c.name, '''', ''), @ColumnLimit) + ''';'
        FROM sys.default_constraints AS dc
        INNER JOIN sys.columns AS c
        ON dc.parent_object_id = c.[object_id]
        AND dc.parent_column_id = c.column_id
        AND dc.is_ms_shipped = 0;
    END


    IF @CheckConstraints = 1
    BEGIN
        SELECT @sql = @sql + @cr + @cr + N'/* ---- CheckConstraints ---- */' + @cr;
        SELECT @sql = @sql + @cr + N'EXEC sp_rename @objname = N''' + SCHEMA_NAME(schema_id) +'.' 
            + REPLACE(cc.name, '''', '''''') + ''', @newname = N''CK_' 
            + LEFT(REPLACE(OBJECT_NAME(cc.parent_object_id), '''',''), @TableLimit)
            + '_' + LEFT(REPLACE(c.name, '''', ''), @ColumnLimit) + ''';'
        FROM sys.check_constraints AS cc
        INNER JOIN sys.columns AS c
        ON cc.parent_object_id = c.[object_id]
        AND cc.parent_column_id = c.column_id
        AND cc.is_ms_shipped = 0;
    END


    SELECT @sql;


    IF @SELECT_Only = 0 AND @sql > N''
    BEGIN
        EXEC sp_executesql @sql;
    END
END

GO
USE [master]
GO
ALTER DATABASE [Compliance] SET  READ_WRITE 

