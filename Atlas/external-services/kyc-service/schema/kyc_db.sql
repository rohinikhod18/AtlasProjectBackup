USE [Compliance]
GO
CREATE TABLE [KYC_ServiceTypeEnum]
(
	[ID] int Identity(1,1) NOT NULL,
	[Code] nvarchar(50) NOT NULL,
        [CreatedBy] nvarchar(50) NOT NULL,
	[CreatedOn] datetime NOT NULL,
	[UpdatedBy] nvarchar(50) NOT NULL,
	[UpdatedOn] datetime NOT NULL

PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],

)
GO
/****** Object:  Table [dbo].[KYC_ServiceProvider]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [KYC_ServiceProvider]
(
	[ID] int  Identity(1,1) NOT NULL,
	[ServiceType] int NOT NULL,
	[Code] nvarchar(20) NOT NULL,
	[Name] nvarchar(50) NOT NULL,
	[Internal] bit NOT NULL DEFAULT 0,
        [Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,

	PRIMARY KEY CLUSTERED ([ID] ASC),
	FOREIGN KEY ([ServiceType]) REFERENCES [KYC_ServiceTypeEnum] ([ID]) ON DELETE No Action ON UPDATE No Action
)
GO
/****** Object:  Table [dbo].[KYC_ServiceProviderAttribute]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [KYC_ServiceProviderAttribute]
(
	[ID] int NOT NULL,
	[Attribute] nvarchar(max) NOT NULL,
        [Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,
	PRIMARY KEY CLUSTERED ([ID] ASC),
FOREIGN KEY ([ID]) REFERENCES [KYC_ServiceProvider] ([ID]) ON DELETE No Action ON UPDATE No Action
)
GO

GO
/****** Object:  Table [dbo].[KYC_CountryProviderMapping]    Script Date: 31/5/16 3:21:43 PM IST ******/
CREATE TABLE [dbo].[KYC_CountryProviderMapping](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Country] [nvarchar](30) NOT NULL,
	[ProviderName] [nvarchar](30) NOT NULL,
	[CreatedOn] [datetime] NULL,
	[CretedBy] [nvarchar](30) NOT NULL,
	[UpdatedOn] [datetime] NULL,
	[UpdatedBy] [nvarchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
Go
