USE [Compliance]
CREATE LOGIN Compliance WITH PASSWORD = 'Currencies01'
GO
if not exists(select * from sys.database_principals where name = 'compliance')
	CREATE USER [compliance] FOR LOGIN [compliance] WITH DEFAULT_SCHEMA=[comp]
GO
ALTER ROLE [db_owner] ADD MEMBER [compliance]
GO
ALTER USER [Compliance] WITH DEFAULT_SCHEMA = [Comp];
GO