USE [EnterpriseService]
GO

/****** Object:  Table [dbo].[CustomCheck_Occupation]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_Occupation] ( 
	[Id]  [int]  IDENTITY(1,1)    NOT NULL,
	[OrganizationCode]	nvarchar(25)  NULL,
	[Occupation]      	nvarchar(25)  NULL,
	[Score]           	nvarchar(25)  NULL,
	[Version]         	nvarchar(25)  NULL,
	[CreatedOn]       	datetime      NULL,
	[CreatedBy]       	nvarchar(25)  NULL,
	[UpdatedOn]       	datetime      NULL,
	[UpdatedBy]       	nvarchar(25)  NULL 
	)
Go

/****** Object:  Table [dbo].[CustomCheck_SourceOfLead]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_SourceOfLead] ( 
	[Id]  [int]  IDENTITY(1,1)    NOT NULL,
	[OrganizationCode]	nvarchar(25)  NULL,
	[SourceOfLead]      nvarchar(25)  NULL,
	[Score]           	nvarchar(25)  NULL,
	[Version]         	nvarchar(25)  NULL,
	[CreatedOn]       	datetime      NULL,
	[CreatedBy]       	nvarchar(25)  NULL,
	[UpdatedOn]       	datetime      NULL,
	[UpdatedBy]       	nvarchar(25)  NULL
	)
Go

/****** Object:  Table [dbo].[CustomCheck_SourceOfFund]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_SourceOfFund] ( 
	[Id]  [int]  IDENTITY(1,1)    NOT NULL,
	[OrganizationCode]	nvarchar(25)  NULL,
	[SourceOfFund]      nvarchar(25)  NULL,
	[Score]           	nvarchar(25)  NULL,
	[Version]         	nvarchar(25)  NULL,
	[CreatedOn]       	datetime      NULL,
	[CreatedBy]         nvarchar(25)  NULL,
	[UpdatedOn]       	datetime      NULL,
	[UpdatedBy]       	nvarchar(25)  NULL 
	)
Go

/****** Object:  Table [dbo].[CustomCheck_ValueOfTransaction]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_ValueOfTransaction] ( 
	[Id]  [int]  IDENTITY(1,1)     NOT NULL,
	[OrganizationCode]	 nvarchar(25)  NULL,
	[ValueOfTransaction] nvarchar(25)  NULL,
	[Score]           	 nvarchar(25)  NULL,
	[Version]         	 nvarchar(25)  NULL,
	[CreatedOn]       	 datetime      NULL,
	[CreatedBy]        	nvarchar(25)   NULL,
	[UpdatedOn]        	datetime       NULL,
	[UpdatedBy]       	nvarchar(25)   NULL 
	)
Go

/****** Object:  Table [dbo].[CustomCheck_PurposeOfTransaction]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_PurposeOfTransaction] ( 
	[Id]  [int]  IDENTITY(1,1)      NOT NULL,
	[OrganizationCode]	nvarchar(25)    NULL,
	[PurposeOfTransaction]nvarchar(25)  NULL,
	[Score]           	nvarchar(25)    NULL,
	[Version]         	nvarchar(25)    NULL,
	[CreatedOn]       	datetime        NULL,
	[CreatedBy]       	nvarchar(25)    NULL,
	[UpdatedOn]       	datetime        NULL,
	[UpdatedBy]       	nvarchar(25)    NULL
	)
Go

/****** Object:  Table [dbo].[CustomCheck_CountriesOfTrade]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_CountriesOfTrade] ( 
	[Id]  [int]  IDENTITY(1,1)    NOT NULL,
	[OrganizationCode]	nvarchar(25)  NULL,
	[CountriesOfTrade]  nvarchar(25)  NULL,
	[Score]           	nvarchar(25)  NULL,
	[Version]         	nvarchar(25)  NULL,
	[CreatedOn]       	datetime      NULL,
	[CreatedBy]       	nvarchar(25)  NULL,
	[UpdatedOn]       	datetime      NULL,
	[UpdatedBy]       	nvarchar(25)  NULL 
	)
Go

/****** Object:  Table [dbo].[CustomCheck_OccupationHistory]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_OccupationHistory] ( 
	[Id]  [int]  IDENTITY(1,1)  NOT NULL,
	[OrganizationCode]	nvarchar(25)  NULL,
	[OccupationHistory] nvarchar(25)  NULL,
	[Score]           	nvarchar(25)  NULL,
	[Version]         	nvarchar(25)  NULL,
	[CreatedOn]       	datetime      NULL,
	[CreatedBy]       	nvarchar(25)  NULL,
    [IsDeleted]         bit           NULL
	)
Go

/****** Object:  Table [dbo].[CustomCheck_SourceOfLeadHistory]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_SourceOfLeadHistory] ( 
	[Id]  [int]  IDENTITY(1,1) NOT NULL,
	[OrganizationCode]	nvarchar(25)   NULL,
	[SourceOfLeadHistory] nvarchar(25) NULL,
	[Score]            nvarchar(25)    NULL,
	[Version]          nvarchar(25)    NULL,
	[CreatedOn]        datetime        NULL,
	[CreatedBy]        nvarchar(25)    NULL,
    [IsDeleted]        bit             NULL
	)
Go

/****** Object:  Table [dbo].[CustomCheck_SourceOfFundHistory]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_SourceOfFundHistory] ( 
	[Id]  [int]  IDENTITY(1,1) NOT NULL,
	[OrganizationCode]	nvarchar(25)    NULL,
	[SourceOfFundHistory] nvarchar(25)  NULL,
	[Score]           	nvarchar(25)    NULL,
	[Version]         	nvarchar(25)    NULL,
	[CreatedOn]       	datetime        NULL,
	[CreatedBy]       	nvarchar(25)    NULL,
    [IsDeleted]         bit             NULL
	)
Go

/****** Object:  Table [dbo].[CustomCheck_ValueOfTransactionHistory]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_ValueOfTransactionHistory] ( 
	[Id]  [int]  IDENTITY(1,1) NOT NULL,
	[OrganizationCode]	nvarchar(25)  NULL,
	[ValueOfTransactionHistory] nvarchar(25)   NULL,
	[Score]           	nvarchar(25)  NULL,
	[Version]         	nvarchar(25)  NULL,
	[CreatedOn]       	datetime      NULL,
	[CreatedBy]       	nvarchar(25)  NULL,
    [IsDeleted]         bit           NULL
	)
Go

/****** Object:  Table [dbo].[CustomCheck_PurposeOfTransactionHistory]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_PurposeOfTransactionHistory] ( 
	[Id]  [int]  IDENTITY(1,1) NOT NULL,
	[OrganizationCode]	nvarchar(25)   NULL,
	[PurposeOfTransactionHistory] nvarchar(25)   NULL,
	[Score]           	nvarchar(25)   NULL,
	[Version]         	nvarchar(25)   NULL,
	[CreatedOn]       	datetime       NULL,
	[CreatedBy]       	nvarchar(25)   NULL,
    [IsDeleted]         bit            NULL
	)
Go

/****** Object:  Table [dbo].[CustomCheck_CountriesOfTradeHistory]    Script Date: 07/6/16 3:05:43 PM IST ******/
CREATE TABLE [dbo].[CustomCheck_CountriesOfTradeHistory] ( 
	[Id]  [int]  IDENTITY(1,1) NOT NULL,
	[OrganizationCode]	nvarchar(25)  NULL,
	[CountriesOfTradeHistory] 	nvarchar(25)  NULL,
	[Score]           	nvarchar(25)  NULL,
	[Version]         	nvarchar(25)  NULL,
	[CreatedOn]       	datetime      NULL,
	[CreatedBy]       	nvarchar(25)  NULL,
    [IsDeleted]         bit           NULL
	)
Go
