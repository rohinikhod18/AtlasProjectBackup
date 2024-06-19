/** seed data for blacklist start - Rajesh S. date - 19/7/16 6.35 PM IST ******/
USE [EnterpriseService]
GO

/* blacklistType*/
INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('NAME', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('EMAIL', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('IPADDRESS', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('DOMAIN', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('ACC_NUMBER', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('PHONE_NUMBER', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

/* BlackListData*/

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, 'Vivian Dabrosio', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, 'Roxann Smail', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(2, 'vivian.da@bnt-soft.com', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(2, 'roxann.smail@bnt-soft.com', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(3, '123.3.4.5', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(3, '123.5.6.3', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(5, '1232456', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(5, '12678435', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(6, '7234567123', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(6, '7378123470', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(4, 'quora', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(4, 'buzzfeed', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO
/** seed data for Blacklist end - Rajesh S. date - 19/7/16 6.43 PM ******/
