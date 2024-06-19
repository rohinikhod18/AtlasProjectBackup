sUSE [EnterpriseService]
GO

/****** Object:  Table [dbo].[IP_ServiceProvider]    Script Date: 27/7/16 3:05:43 PM IST ******/
CREATE TABLE [IP_ServiceProvider]
(
	[ID] int NOT NULL,
	[Code] nvarchar(20) NOT NULL,
	[Name] nvarchar(50) NOT NULL,
	[Internal] bit NOT NULL DEFAULT 0,
        [Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,

	PRIMARY KEY CLUSTERED ([ID] ASC)
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

INSERT INTO [dbo].[IP_ServiceProvider]([ID], [Code], [Name], [Internal], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1,'QUOVA', ' Neustar IP Geolocation Service', 0, '2016-07-27 03:22:30.0', 'admin_comm', '2016-07-27 03:22:30.0', 'admin_comm')
GO

INSERT INTO [dbo].[IP_ServiceProviderAttribute]([ID], [Attribute], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, '{"endPointUrl":"https://wsonline.seisint.com/WsIdentity?ver_=1.77","qname":"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","userName":"CURDIRDEVXML","passWord":"B5967t33"}', '2016-07-27 03:22:30.0','admin_comm', '2016-07-27 03:22:30.0', 'admin_comm')
GO

/**************************************************************************************************/
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

GO


INSERT INTO [dbo].[IP_PostCodeLocation]([Postcode], [Latitude], [Longitude], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('IP237BJ', 52.32359895, 1.141920292, '2016-6-28 7:17:53.0', 'admin_comm', '2016-6-28 7:17:53.0', 'admin_comm')
GO

create view vw_bulk_insert_test
as
select Postcode,Latitude,Longitude,Created_On,Created_By,Updated_On,Updated_By from IP_PostCodeLocation
go
BULK INSERT vw_bulk_insert_test
FROM 'D:\IP_PostCodeLocation.csv'
WITH
(
FIELDTERMINATOR =',',
ROWTERMINATOR = '\n'
)
go
SELECT [ID]
      ,[Postcode]
      ,[Latitude]
      ,[Longitude]
      ,[Created_On]
      ,[Created_By]
      ,[Updated_On]
      ,[Updated_By]
  FROM [Enter_Test].[dbo].[IP_PostCodeLocation]
GO
