CREATE TABLE [dbo].[CustomerDataScan_Service_Details] ( 
	[Id]  				[int]  IDENTITY(1,1) NOT NULL,
    [Correlation_Id]	nvarchar(25)  NULL,
	[Organization_Code]	nvarchar(25)  NULL,
	[Source]        	nvarchar(25)  NULL,
    [Request_Type]      nvarchar(25)  NULL,
    [Operation]         nvarchar(25)  NULL,
    [Request]           nvarchar(MAX) NULL,
	[Response]         	nvarchar(MAX) NULL,
	[Created_On]       	datetime  	  NULL,
	[Created_By]       	int           NULL,
	[Updated_On]       	datetime      NULL,
	[Updated_By]       	int           NULL,
	)
Go