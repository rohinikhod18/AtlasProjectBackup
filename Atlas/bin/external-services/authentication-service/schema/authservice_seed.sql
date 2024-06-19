USE [NGOP-SIT]
GO
INSERT INTO [dbo].[User]([Title], [FirstName], [MiddleName], [LastName], [PreferredName], [LoginName], [Gender], [DOB], [Email], [Category], 
[Password], [PasswordSalt], [Locked], [Deleted], [LoginStatus], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('Mr.', 'Admin', '', 'Admin', 'Admin', 'admin_comm', 'M', '2016-12-7 12:26:52.0', 'nelson.n@currenciesdirect.com', 5, 
'9S1oakXtWPhoQMQDBr96Y//d8dYKyM3m9q7jOAYfrFM=', 'xhJh0vBD72Y=', 0, 0, 'F', 1, '2016-12-7 12:26:52.0', 1, '2016-12-7 12:26:52.0')
GO
INSERT INTO [dbo].[Role]([Name], [OrganizationID], [ParentRoleID], [Description], [Locked], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
    VALUES('Internal Compliance User' , 1, null, 'Compliance Manager', 0,0, 1, '2016-12-7 12:26:52.0', 1, '2016-12-7 12:26:52.0')
GO
INSERT INTO [dbo].[UserRole]([RoleID], [UserID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
    VALUES( 56, 240, 1, '2016-12-7 12:26:52.0', 1, '2016-12-7 12:26:52.0')
GO
SET IDENTITY_INSERT dbo.Role ON
GO
INSERT INTO [dbo].[RoleFunction]([RoleID], [FunctionID], [CanView], [CanCreate], [CanModify], [CanDelete], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
    VALUES(56, 78, 1, 1, 1, 1, 1, '2016-12-7 12:26:52.0', 1, '2016-12-7 12:26:52.0')
GO
set IDENTITY_INSERT dbo.Role OFF
GO
INSERT INTO [dbo].[CoreFunction]([ID], [Name], [Category], [PaymentFunction], [CreatedBy], [CreatedOn]) 
    VALUES(78, 'Compliance', 1, null, 1, '2016-12-7 12:26:52.0')
GO
