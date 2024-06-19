-- DB Schema updates
CREATE TABLE [Comp].LegalEntity (
	ID tinyint IDENTITY(1,1) NOT NULL,
	ParentID int NULL,
	Code nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	Name nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	Locked bit DEFAULT 0 NOT NULL,
	Deleted bit DEFAULT 0 NOT NULL,
	CreatedBy int NOT NULL,
	CreatedOn datetime DEFAULT getdate() NOT NULL,
	UpdatedBy int NULL,
	UpdatedOn datetime NOT NULL,
	BaseCurrency smallint NOT NULL,
	CONSTRAINT PK_LegalEntity PRIMARY KEY (ID)
    CONSTRAINT FK_LegalEntity_Currency_CurrencyID FOREIGN KEY (BaseCurrency) REFERENCES [Comp].Currency(ID)
)

CREATE TABLE [Comp].OrgLegalEntityRelations (
	ID tinyint IDENTITY(1,1) NOT NULL,
	OrganizationID tinyint NOT NULL,
	LegalEntityID tinyint NOT NULL,
	CONSTRAINT PK_OrgLegalEntityRelations PRIMARY KEY (ID),
    CONSTRAINT FK_OrgLegalEntity_Organization_OrganizationID FOREIGN KEY (OrganizationID) REFERENCES [Comp].Organization(ID),
    CONSTRAINT FK_OrgLegalEntity_LegalEntity_LegalEntityID FOREIGN KEY (LegalEntityID) REFERENCES [Comp].LegalEntity(ID)
)

ALTER TABLE [Comp].Account ADD LegalEntityID tinyint;

ALTER TABLE [Comp].Account ADD OldOrganizationID tinyint;

ALTER TABLE [Comp].Account WITH NOCHECK ADD CONSTRAINT [FK_Account_LegalEntity_LegalEntityID] FOREIGN KEY([LegalEntityID]) REFERENCES [LegalEntity] ([ID]);

ALTER TABLE [Comp].PaymentIn ADD LegalEntityID tinyint;

ALTER TABLE [Comp].PaymentIn WITH NOCHECK ADD CONSTRAINT [FK_PaymentIn_LegalEntity_LegalEntityID] FOREIGN KEY([LegalEntityID]) REFERENCES [LegalEntity] ([ID]);

ALTER TABLE [Comp].PaymentOut ADD LegalEntityID tinyint;

ALTER TABLE [Comp].PaymentOut WITH NOCHECK ADD CONSTRAINT [FK_PaymentOut_LegalEntity_LegalEntityID] FOREIGN KEY([LegalEntityID]) REFERENCES [LegalEntity] ([ID]);

-- Data updates
-- TorFX and TorFXAU merger
UPDATE [Comp].Account SET OrganizationID = (SELECT id FROM [Comp].Organization WHERE code='TorFX')
FROM [Comp].Account a JOIN [Comp].Organization o ON a.OrganizationID = o.ID 
WHERE o.Code = 'TorFXOz';



--	** All customers who are having FCGGB and TORGB will be moved on to the CDLGB.
UPDATE [Comp].AccountAttribute SET Attributes=JSON_MODIFY(Attributes, '$.organization_legal_entity', 'CDLGB')
FROM [Comp].Account a JOIN [Comp].AccountAttribute aa ON a.id=aa.id WHERE LegalEntity IN('FCGGB','TORGB');

UPDATE [Comp].Account SET LegalEntity='CDLGB' WHERE LegalEntity IN('FCGGB','TORGB');

--	Update Legal Entity values
UPDATE [Comp].Account SET LegalEntityID = e.id
FROM [Comp].Account a JOIN [Comp].LegalEntity e ON a.LegalEntity = e.code
WHERE a.LegalEntity IS NOT NULL;


-- 	Update Payments tables
UPDATE [Comp].PaymentIn SET LegalEntityID = a.LegalEntityID
FROM [Comp].Account a JOIN [Comp].PaymentIn p ON a.id = p.AccountID
WHERE a.LegalEntity IS NOT NULL;

UPDATE 
[Comp].PaymentInAttribute SET 
Attributes=JSON_MODIFY(Attributes,'$.trade.organization_legal_entity',a.LegalEntity)
FROM [Comp].PaymentIn p JOIN [comp].PaymentInAttribute pia ON p.id = pia.id
JOIN [comp].Account a ON p.AccountId = a.id

UPDATE [Comp].PaymentOut SET LegalEntityID = a.LegalEntityID
FROM [Comp].Account a JOIN [Comp].PaymentOut p ON a.id = p.AccountID
WHERE a.LegalEntity IS NOT NULL;

UPDATE 
[Comp].PaymentOutAttribute SET 
Attributes=JSON_MODIFY(Attributes,'$.trade.organization_legal_entity',a.LegalEntity)
FROM [Comp].PaymentOut p JOIN [comp].PaymentOutAttribute poa ON p.id = poa.id
JOIN [comp].Account a ON p.AccountId = a.id

--	ALTER TABLE [Comp].Account DROP COLUMN LegalEntity;
--	ALTER TABLE [Comp].Account ALTER COLUMN LegalEntityID NOT NULL;
--	ALTER TABLE [Comp].PaymentIn ALTER COLUMN LegalEntityID NOT NULL;
--	ALTER TABLE [Comp].PaymentOut ALTER COLUMN LegalEntityID NOT NULL;
