-- Query for AT-2318 Phase 1: Singapore LE Phase 1 Functional changes

-- 1. Add 3 Legal Entities

INSERT INTO [Comp].[LegalEntity]
(ParentID, Code, Name, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(0, 'CDLSG', 'Currencies Direct Singapore', ((0)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)
GO
INSERT INTO [Comp].[LegalEntity]
(ParentID, Code, Name, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(0, 'TORSG', 'TorFX Singapore', ((0)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)
GO
INSERT INTO [Comp].[LegalEntity]
(ParentID, Code, Name, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(0, 'FCGSG', 'FCG Singapore', ((0)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)
GO

-- 2. Add Mapping of these 3 Legal Entities

INSERT INTO [Comp].[OrgLegalEntityRelations]
(OrganizationID, LegalEntityID)
VALUES((SELECT ID FROM [Comp].Organization WHERE Code = 'Currencies Direct'), 
(SELECT ID FROM [Comp].[LegalEntity] WHERE Code = 'CDLSG'))
GO
INSERT INTO [Comp].[OrgLegalEntityRelations]
(OrganizationID, LegalEntityID)
VALUES((SELECT ID FROM [Comp].Organization WHERE Code = 'TorFX'), 
(SELECT ID FROM [Comp].[LegalEntity] WHERE Code = 'TORSG'))
GO
INSERT INTO [Comp].[OrgLegalEntityRelations]
(OrganizationID, LegalEntityID)
VALUES((SELECT ID FROM [Comp].Organization WHERE Code = 'FCG'), 
(SELECT ID FROM [Comp].[LegalEntity] WHERE Code = 'FCGSG'))
GO

 
 
 
 
 
 
 
-------------------------------------------------------------------------------------------------------
-- Incase of issue use below 
-- Rollback Script
 
DELETE FROM [Comp].[OrgLegalEntityRelations]
WHERE OrganizationID = (SELECT ID FROM [Comp].[Organization] WHERE Code = 'Currencies Direct')
AND LegalEntityID = (SELECT ID FROM [Comp].[LegalEntity] WHERE Code = 'CDLSG')
GO
DELETE FROM [Comp].[OrgLegalEntityRelations]
WHERE OrganizationID = (SELECT ID FROM [Comp].[Organization] WHERE Code = 'TorFX')
AND LegalEntityID = (SELECT ID FROM [Comp].[LegalEntity] WHERE Code = 'TORSG')
GO
DELETE FROM [Comp].[OrgLegalEntityRelations]
WHERE OrganizationID = (SELECT ID FROM [Comp].[Organization] WHERE Code = 'FCG')
AND LegalEntityID = (SELECT ID FROM [Comp].[LegalEntity] WHERE Code = 'FCGSG')
GO

DELETE FROM [Comp].[LegalEntity]
WHERE Code = 'CDLSG'
GO
DELETE FROM [Comp].[LegalEntity]
WHERE Code = 'TORSG'
GO
DELETE FROM [Comp].[LegalEntity]
WHERE Code = 'FCGSG'
GO
