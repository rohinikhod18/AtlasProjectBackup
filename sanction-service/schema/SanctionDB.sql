USE [Compliance]
GO
CREATE TABLE [Sanction_ServiceTypeEnum]
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


CREATE TABLE [Sanction_ServiceProvider]
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
	FOREIGN KEY ([ServiceType]) REFERENCES [Sanction_ServiceTypeEnum] ([ID]) ON DELETE No Action ON UPDATE No Action
)
GO

CREATE TABLE [Sanction_ServiceProviderAttribute]
(
	[ID] int NOT NULL,
	[Attribute] nvarchar(max) NOT NULL,
        [Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,
	PRIMARY KEY CLUSTERED ([ID] ASC),
FOREIGN KEY ([ID]) REFERENCES [Sanction_ServiceProvider] ([ID]) ON DELETE No Action ON UPDATE No Action
)
GO
INSERT INTO [dbo].[Sanction_ServiceTypeEnum]([Code], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
    VALUES('Sanction', 'admin_comm', '2016-6-28 11:14:40.0', 'admin_comm', '2016-6-28 11:14:40.0')
GO


INSERT INTO [dbo].[Sanction_ServiceProvider]([ServiceType], [Code], [Name], [Internal], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1,'FINSCAN', 'FINSCAN SANCTION SERVICE', 0, '2016-08-26 03:22:30.0', 'admin_comm', '2016-08-26 03:22:30.0', 'admin_comm')
GO

INSERT INTO [dbo].[Sanction_ServiceProviderAttribute]([ID], [Attribute], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, '{"enpointUrl":"https://hosted5.finscan.com/isi_test/LSTServicesLookup.asmx","userName":"webservices","passWord":"webservices","socketTimeoutMillis":6000,"connectionTimeoutMillis":6000}', '2016-08-30 03:22:30.0','admin_comm', '2016-08-30 03:22:30.0', 'admin_comm')
GO

