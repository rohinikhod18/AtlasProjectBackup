USE [Compliance]
GO
CREATE TABLE [Fraugster_ServiceTypeEnum]
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


/****** Object:  Table [dbo].[Fraugster_ServiceProvider]    Script Date: 26/8/16 3:05:43 PM IST ******/
CREATE TABLE [Fraugster_ServiceProvider]
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
	FOREIGN KEY ([ServiceType]) REFERENCES [Fraugster_ServiceTypeEnum] ([ID]) ON DELETE No Action ON UPDATE No Action
)
GO
/****** Object:  Table [dbo].[Fraugster_ServiceProviderAttribute]    Script Date: 26/8/16 3:05:43 PM IST ******/
CREATE TABLE [Fraugster_ServiceProviderAttribute]
(
	[ID] int NOT NULL,
	[Attribute] nvarchar(max) NOT NULL,
        [Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,
	PRIMARY KEY CLUSTERED ([ID] ASC),
FOREIGN KEY ([ID]) REFERENCES [Fraugster_ServiceProvider] ([ID]) ON DELETE No Action ON UPDATE No Action
)
GO
INSERT INTO [dbo].[Fraugster_ServiceTypeEnum]([Code], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
    VALUES('FRAUGSTER', 'admin_comm', '2016-6-28 11:14:40.0', 'admin_comm', '2016-6-28 11:14:40.0')
GO


INSERT INTO [dbo].[Fraugster_ServiceProvider]([ServiceType], [Code], [Name], [Internal], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1,'LOGIN_LOGOUT', 'Identity fraud detection Login/logout Api', 0, '2016-08-26 03:22:30.0', 'admin_comm', '2016-08-26 03:22:30.0', 'admin_comm')
GO
INSERT INTO [dbo].[Fraugster_ServiceProvider]([ServiceType], [Code], [Name], [Internal], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1,'SIGNUP', 'Identity fraud detection signup Api', 0, '2016-08-26 03:22:30.0', 'admin_comm', '2016-08-26 03:22:30.0', 'admin_comm')
GO

INSERT INTO [dbo].[Fraugster_ServiceProviderAttribute]([ID], [Attribute], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, '{"endpointUrl":"http://localhost:8000/api/v1/sessions","userName":"test","passWord":"password123","socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"fraugsterInactivityRefreshTime":40}', '2016-07-27 03:22:30.0','admin_comm', '2016-07-27 03:22:30.0', 'admin_comm')
GO
INSERT INTO [dbo].[Fraugster_ServiceProviderAttribute]([ID], [Attribute], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(2, '{"endpointUrl":"http://localhost:8000/api/v1/transaction","userName":"test","passWord":"password123","socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"threesholdScore":0.50}', '2016-07-27 03:22:30.0','admin_comm', '2016-07-27 03:22:30.0', 'admin_comm')
GO

