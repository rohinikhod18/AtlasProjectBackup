-------------------------AT-3349 DB SQL Script-------------------------------------



--1.Add new column(LEUpdateDate) in Account table
ALTER TABLE [Comp].[Account] ADD [LEUpdateDate] datetime NULL;


--2.Add new column(POIExists) in Contact table
ALTER TABLE [Comp].[Contact] ADD [POIExists] INT NOT NULL DEFAULT(0);






-------------------------Rollback Query-----------------------------------------------


--1.Account table
ALTER TABLE [Comp].[Account] DROP COLUMN [LEUpdateDate];

--2.Contact table
ALTER TABLE [Comp].[Contact] DROP COLUMN [POIExists];
