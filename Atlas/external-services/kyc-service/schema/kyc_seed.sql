/** seed data for KYC_CountryProviderMapping start - Manish M. date - 31/5/16 3:21:43 PM IST ******/
USE [Compliance]
GO
/** seed data for KYC_ServiceTypeEnum start - Manish M. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[KYC_ServiceTypeEnum]([Code], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
    VALUES('KYC', 'admin_comm', '2016-6-5 6:42:30.0', 'admin_comm', '2016-6-5 6:42:30.0')
GO

/** seed data for KYC_ServiceProvider start - Manish M. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[KYC_ServiceProvider]([ServiceType], [Code], [Name], [Internal], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1,'LEXISNEXIS', 'InstantID International 2', 0, '2016-6-5 6:42:30.0', N'', '2016-6-5 6:42:30.0', N'')
GO

INSERT INTO [dbo].[KYC_ServiceProvider]([ServiceType], [Code], [Name], [Internal], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1,'GBGROUP', 'AuthenticateSP', 0, '2016-6-5 6:42:30.0', N'', '2016-6-5 6:42:30.0', N'')
GO

/** seed data for KYC_ServiceProvider end - Manish M. date - 07/6/16 3:08:43 PM IST ******/

/** seed data for KYC_ServiceProviderAttribute start - Manish M. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[KYC_ServiceProviderAttribute]([ID], [Attribute], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, '{"endPointUrl":"https://wsonline.seisint.com/WsIdentity?ver_=1.77","qname":"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","userName":"CURDIRXML","passWord":"B5967t33","socketTimeoutMillis":6000,"connectionTimeoutMillis":6000}', '2016-6-5 6:45:21.0','admin', '2016-6-5 6:45:21.0', 'admin')
GO
INSERT INTO [dbo].[KYC_ServiceProviderAttribute]([ID], [Attribute], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(2, '{"endPointUrl":"https://pilot.id3global.com/ID3gWS/ID3global.svc/Soap11_Auth","qname":"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","userName":"admin@currenciesdirect.com","passWord":"Stephen4kam2031!!","profileVersionIDList":[{"country":"UNITED KINGDOM","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"DENMARK","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"SWITZERLAND","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"GERMANY","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"NETHERLANDS","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"SPAIN","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"SWEDEN","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"}],"socketTimeoutMillis":6000,"connectionTimeoutMillis":6000}', '2016-6-5 6:45:21.0','admin', '2016-6-5 6:45:21.0', 'admin')
GO

/** seed data for KYC_ServiceProviderAttribute end - Manish M. date - 07/6/16 3:08:43 PM IST ******/
update KYC_ServiceProviderAttribute set Attribute = '{"endPointUrl":"https://pilot.id3global.com/ID3gWS/ID3global.svc/Soap11_Auth","qname":"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","userName":"admin@currenciesdirect.com","passWord":"Stephen4kam2030!!","profileVersionIDList":[{"country":"UNITED KINGDOM","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"DENMARK","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"SWITZERLAND","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"GERMANY","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"NETHERLANDS","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"SPAIN","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"SWEDEN","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"}]}' where ID=2

/** seed data for KYC_CountryProviderMapping start - Manish M. date - 31/5/16 3:21:43 PM IST ******/
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Denmark','GBGroup',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Germany','GBGroup',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Netherlands','GBGroup',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Norway','GBGroup',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Spain','GBGroup',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Sweden','GBGroup',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Switzerland','GBGroup',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('United Kingdom','GBGroup',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Australia','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('USA','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('China','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('New Zealand','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Canada','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('South Africa','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Japan','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Mexico','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Austria','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Brazil','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('Luxembourg','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')
GO
insert into KYC_CountryProviderMapping ([Country],[ProviderName],[CreatedOn],[CretedBy],[UpdatedOn],[UpdatedBy]) 
VALUES ('HongKong','LexisNexis',CURRENT_TIMESTAMP,'admin_comm',CURRENT_TIMESTAMP,'admin_comm')

/** seed data for KYC_CountryProviderMapping end - Manish M. date - 31/5/16 3:21:43 PM IST ******/