 CREATE TABLE [dbo].[AuditLog] ( 
	[CorrelationId]	nvarchar(50) NOT NULL,
	[Source]      	nvarchar(50) NOT NULL,
	[Request]       nvarchar(MAX) NOT NULL,
	[Response]      nvarchar(MAX) NOT NULL,
	[CreatedDate]   datetime NOT NULL,
	[UpdatedDate]   int NOT NULL
	)
Go
