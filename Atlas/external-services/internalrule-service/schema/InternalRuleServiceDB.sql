USE [Compliance]
GO
/*************************************IP TABLES************************************************/
CREATE TABLE [IP_ServiceTypeEnum]
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
/****** Object:  Table [dbo].[IP_ServiceProvider]    Script Date: 27/7/16 3:05:43 PM IST ******/
CREATE TABLE [IP_ServiceProvider]
(
	[ID] int Identity(1,1) NOT NULL,
	[ServiceType] int NOT NULL,
	[Code] nvarchar(20) NOT NULL,
	[Name] nvarchar(50) NOT NULL,
	[Internal] bit NOT NULL DEFAULT 0,
        [Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,

	PRIMARY KEY CLUSTERED ([ID] ASC),
	FOREIGN KEY ([ServiceType]) REFERENCES [IP_ServiceTypeEnum] ([ID]) ON DELETE No Action ON UPDATE No Action
)
GO
/****** Object:  Table [dbo].[IP_ServiceProviderAttribute]    Script Date: 27/7/16 3:05:43 PM IST ******/
CREATE TABLE [IP_ServiceProviderAttribute]
(
	[ID] int NOT NULL,
	[Attribute] nvarchar(max) NOT NULL,
        [Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,
	PRIMARY KEY CLUSTERED ([ID] ASC),
FOREIGN KEY ([ID]) REFERENCES [IP_ServiceProvider] ([ID]) ON DELETE No Action ON UPDATE No Action
)
GO



/*********Object:  Table [dbo].[IP_PostCodeLocation]    Script Date: 27/7/16 3:05:43 PM IST**********/
CREATE TABLE [IP_PostCodeLocation]
(
	[ID] int IDENTITY(1,1) NOT NULL,
	[Postcode] nvarchar(20) NOT NULL,
	[Latitude] float  NOT NULL,
	[Longitude] float NOT NULL,
     [Created_On]       	 datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,

	PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]




/*********************************************BLACKLIST TABLES*****************************************************/
CREATE TABLE [BlackListType]
(
	[ID] 			        int IDENTITY(1,1) NOT NULL,
	[Type] 				    nvarchar(20) 	  NOT NULL,
	[Relevance] 			smallint  	 	  NOT NULL DEFAULT 0,
	[Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,
    PRIMARY KEY (ID)
        
)
GO

CREATE TABLE [BlackListData]
(
	[ID] 					int IDENTITY(1,1) NOT NULL,
	[BlackListType]		    int 			  NOT NULL,
	[Value] 			    nvarchar(255) 	  NOT NULL,
	[Created_On]       	    datetime  	  		  NULL,
	[Created_By]       	    nvarchar(25)		  NULL,
	[Updated_On]       	    datetime   			  NULL,
	[Updated_By]       	    nvarchar(25)		  NULL,
    FOREIGN KEY ( BlackListType ) REFERENCES BlackListType(ID)
)
GO

/*--------------------------------------------------------Country ---------------------------------------------*/


CREATE TABLE [dbo].[Country](
	[ID] [smallint] NOT NULL,
	[Code] [nchar](3) NOT NULL,
	[DisplayName] [nvarchar](50) NOT NULL,
	[ISOCode] [nchar](3) NOT NULL,
	[ShortCode] [nchar](2) NOT NULL,
	[ISDCode] [nvarchar](15) NOT NULL,
	[RiskLevel] [nchar](1) NOT NULL DEFAULT 'H',
	[Active] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT [PK_Country] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 100) ON [PRIMARY],
 CONSTRAINT [UQ_Country_Code] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 100) ON [PRIMARY],
 CONSTRAINT [UQ_Country_DisplayName] UNIQUE NONCLUSTERED 
(
	[DisplayName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 100) ON [PRIMARY]
) ON [PRIMARY]
GO

/*--------------------------------------------------------CountryState---------------------------------------------*/


CREATE TABLE [dbo].[CountryState](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Country] [smallint] NOT NULL CONSTRAINT FK_CountryState_CountryID FOREIGN KEY REFERENCES Country(ID),
	[DisplayName] [nvarchar](50) NOT NULL,
	[Code] [nvarchar](15) NULL,
	[Active] [bit] NOT NULL,
	[CDLicenseStatus] [tinyint] NOT NULL,
        [TradeStateID] [int],
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT [PK_CountryState] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 100) ON [PRIMARY])
GO


