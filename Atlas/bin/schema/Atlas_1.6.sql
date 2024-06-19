
------ Jira: AT-1089 & 1090 --------
CREATE TABLE [AccountVelocity](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[AccountId][int] NOT NULL,
	[CountThreshold] int NOT NULL,
	[AmountThreshold] decimal(18,4) NOT NULL,
	[CreatedOn] datetime NOT NULL DEFAULT (getdate()),
	[CreatedBy] [int] NOT NULL,
	[UpdatedBy] [int] NULL,
	[UpdatedOn] [datetime] NULL,
CONSTRAINT [PK_AccountVelocity] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY]

/********************
 * 
 * AT-999 contract number validation in current payment in and payment out 
 * 
 ***********************/

ALTER TABLE [Comp].PaymentOut ALTER COLUMN ContractNumber VARCHAR(30) NOT NULL;

ALTER TABLE [Comp].PaymentIn ALTER COLUMN TradeContractNumber VARCHAR(30) NOT NULL;


INSERT INTO WatchList (Reason, StopPaymentIn, StopPaymentOut, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn ) VALUES ('Account Info Updated', 1, 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

---------------- AT-998 ------------------
ALTER TABLE Account ADD LegacyTradeAccountNumber [varchar](30) NULL;

---------------- AT-857 ------------------
-- Move All Extreme Risk Countries to Sanctioned, so that they are not added to HRC Whitelist
UPDATE
	[Comp].[Country]
SET
	RiskLevel = 'S'
WHERE
	DisplayName IN(
		'Bahamas',
		'Belize',
		'Bolivia',
		'Bosnia And Herzegowina',
		'Burundi',
		'Central African Republic',
		'Chad',
		'Colombia',
		'Costa Rica',
		'Dominican Republic',
		'Ecuador',
		'Egypt',
		'El Salvador',
		'Eritrea',
		'Guatemala',
		'Guinea',
		'Guinea-Bissau',
		'Guyana',
		'Haiti',
		'Honduras',
		'Jamaica',
		'Jordan',
		'Kuwait',
		'Lao People''S Democratic Republic',
		'Mali',
		'Nicaragua',
		'Niger',
		'Nigeria',
		'Pakistan',
		'Palestinian Territory',
		'Panama',
		'Peru',
		'Philippines',
		'Sierra Leone',
		'Tunisia',
		'Uganda',
		'Vanuatu'
	);
	
UPDATE
	[Comp].[Country]
SET
	RiskLevel = 'H'
WHERE
	DisplayName IN(
		'Bulgaria',
		'Croatia',
		'Liechtenstein',
		'Thailand'
	);	

UPDATE
	[Comp].[Country]
SET
	RiskLevel = 'L'
WHERE
	DisplayName IN(
		'Alderney'
	);		
