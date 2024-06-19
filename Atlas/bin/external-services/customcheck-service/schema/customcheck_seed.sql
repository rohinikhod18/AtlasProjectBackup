/** seed data for CustomCheck Service start - Abhijit G. date - 31/5/16 3:21:43 PM IST ******/
USE [EnterpriseService]
GO

/** seed data for CustomCheck_Occupation start - Abhijit G. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[CustomCheck_Occupation]( [OrganizationCode], [Occupation], [Score], [Version], [CreatedOn], [CreatedBy], [UpdatedOn], [UpdatedBy]) 
    VALUES( N'TORFX', N'Accountant', N'12 ', N'1', '2016-6-19 6:18:27.0', null, '2016-6-19 6:18:27.0',null)
GO

/** seed data for CustomCheck_CountriesOfTrade start - Abhijit G. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[CustomCheck_CountriesOfTrade]( [OrganizationCode], [CountriesOfTrade], [Score], [Version], [CreatedOn], [CreatedBy], [UpdatedOn], [UpdatedBy]) 
    VALUES( N'TORFX', N'Australia', N'5 ', N'1', '2016-6-19 6:21:50.0', null, '2016-6-19 6:21:50.0', null)
GO

/** seed data for CustomCheck_PurposeOfTransaction start - Abhijit G. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[CustomCheck_PurposeOfTransaction]([OrganizationCode], [PurposeOfTransaction], [Score], [Version], [CreatedOn], [CreatedBy], [UpdatedOn], [UpdatedBy]) 
    VALUES( N'TORFX', N'Business invoices', N'12', N'1', '2016-6-19 6:23:26.0', null, '2016-6-19 6:23:26.0',null)
GO

/** seed data for CustomCheck_SourceOfFund start - Abhijit G. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[CustomCheck_SourceOfFund]([OrganizationCode], [SourceOfFund], [Score], [Version], [CreatedOn], [CreatedBy], [UpdatedOn], [UpdatedBy]) 
    VALUES( N'TORFX', N'Savings', N'12', N'1', '2016-6-19 6:25:2.0', null, '2016-6-19 6:25:2.0', null)
GO

/** seed data for CustomCheck_SourceOfLead start - Abhijit G. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[CustomCheck_SourceOfLead]([OrganizationCode], [SourceOfLead], [Score], [Version], [CreatedOn], [CreatedBy], [UpdatedOn], [UpdatedBy]) 
    VALUES(N'TORFX', N'Internet', N' 7 ', N'1', '2016-6-19 6:26:42.0', null, '2016-6-19 6:26:42.0', null)
GO

/** seed data for CustomCheck_ValueOfTransaction start - Abhijit G. date - 07/6/16 3:08:43 PM IST ******/
INSERT INTO [dbo].[CustomCheck_ValueOfTransaction]( [OrganizationCode], [ValueOfTransaction], [Score], [Version], [CreatedOn], [CreatedBy], [UpdatedOn], [UpdatedBy]) 
    VALUES( N'TORFX', N'Under 2,000', N'5', N'1', '2016-6-19 6:28:22.0', null, '2016-6-19 6:28:22.0', null)
GO