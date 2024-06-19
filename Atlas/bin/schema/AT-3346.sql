-- AT-3346 SQL script.

-- 1. Add new column(EUFirstPayInEDD) in Country table with default 0
ALTER TABLE [Comp].[Country] ADD [EUFirstPayInEDD] bit DEFAULT 0 NOT NULL;


-- 2. Update the new column(EUFirstPayInEDD) to value 1 for certain countries
UPDATE [Comp].[Country]
SET [EUFirstPayInEDD] = '1'
WHERE [DisplayName] IN ('Austria','Belgium','Bulgaria','Croatia','Cyprus',
'Czech Republic','Denmark','Estonia','Finland','France',
'Germany','Greece','Hungary','Ireland','Italy',
'Latvia','Lithuania','Luxembourg','Malta','Netherlands',
'Poland','Portugal','Romania','Slovakia','Slovenia',
'Spain','Sweden','Norway','Liechtenstein','Iceland',
'Australia','Brazil','Canada','South Korea','USA',
'Hong Kong','India','Japan','Mexico','Singapore',
'South Africa','Switzerland','Mayotte','New Caledonia','French Polynesia',
'Aruba', 'Saint Pierre And Miquelon', 'Wallis And Futuna Islands', 'St. Maarten', 'Curacao', 
'Bonaire Sint Eustatius And Saba');

--3. Update table Account to add new column -FirstFundsInEDDCheck-
ALTER TABLE [Comp].[Account] ADD FirstFundsInEDDCheck tinyint DEFAULT 5 NOT NULL;


-------------------------Rollback Query-----------------------------------------------

-- 1. Remove column(EUFirstPayInEDD) from  Country table
ALTER TABLE [Comp].[Country] DROP COLUMN [EUFirstPayInEDD];

--2 Remove column (FirstFundsInEDDCheck) from Account table
ALTER TABLE [Comp].[Account] DROP COLUMN [FirstFundsInEDDCheck];
