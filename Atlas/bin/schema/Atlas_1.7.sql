
/********************
 * 
 * SCRIPT FOR ADDING NEW ROLE - READ_ONLY_EXECUTIVE  and assigning its respective functions
 * 
 ***********************/

-----------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO Comp.Role
(SSORoleID, Description, Active, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('ATLAS_READ_ONLY_EXECUTIVE', 'ATLAS_READ_ONLY_EXECUTIVE', 1, 0, 1, getdate(), 1, getdate());
GO
-----------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO Comp.FunctionGroup
(Name, Active, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('ATLAS_READ_ONLY_EXECUTIVE', 1, 0, 1, getdate(), 1, getdate());
GO
-----------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO Comp.RoleFunctionGroupMapping
(RoleID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES((SELECT ID FROM comp.ROLE where SSORoleID = 'ATLAS_READ_ONLY_EXECUTIVE'), (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO

-----------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO Comp."Function"
(Name, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('isReadOnlyUser', 1, getdate(), 1, getdate());
GO
-----------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(1, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(2, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(3, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(4, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(5, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(6, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(7, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(8, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(9, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(11, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(12, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(13, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(14, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(15, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(16, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(18, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
INSERT INTO Comp.FunctionGroupMapping
(FunctionID, FunctionGroupID, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(19, (SELECT ID FROM Comp.FunctionGroup where Name = 'ATLAS_READ_ONLY_EXECUTIVE'), 1, getdate(), 1, getdate());
GO
-----------------------------------------------------------------------------------------------------------------------------------------



/********************
 * 
 * SCRIPT FOR Deactivating Atlas Users 
 * 
 ***********************/

UPDATE Comp."USER" SET Active = 0, Deleted = 1, UpdatedBy = 1, UpdatedOn = getDate()
where SSOUserID in ('mandeep.s','david.m','george.w','amy.c');
GO

-----------------------------------------------------------------------------------------------------------------------------------------

/********************
 * 
 * SCRIPT FOR Adding new Watchlist 'TP-TP approved'  
 * 
 ***********************/

INSERT INTO Comp.WatchList
(Reason, StopPaymentIn, StopPaymentOut, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('TP-TP approved', 1, 1, 0, 0, 1, getdate(), 1, getdate());
GO
-----------------------------------------------------------------------------------------------------------------------------------------


/********************
 * 
 * SCRIPT FOR FCG org onboarding  
 * 
 ***********************/

INSERT INTO Comp.Organization
( Code, Name, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('FCG', 'FCG', 0, 0, 1, (getdate()), 1, (getdate()));
GO
INSERT INTO Comp.[VelocityRules]([OrganizationID], [CustType], [EventType], [CountThreshold], [AmountThreshold], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
    VALUES( (select ID from Organization where Code='FCG'), N'PFX', N'PAYMENT_OUT', 5,  1001.1023, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)
GO
INSERT INTO Comp.[VelocityRules]([OrganizationID], [CustType], [EventType], [CountThreshold], [AmountThreshold], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
    VALUES( (select ID from Organization where Code='FCG'), N'CFX', N'PAYMENT_OUT', 5,  10000.1023, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)
GO

INSERT INTO Compliance_ServiceProvider (ServiceType, Code, Name, Internal, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
        VALUES( 1, 'FCG_KYC', 'KYC done by FCG', 0, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)
GO         
INSERT INTO Compliance_ServiceProvider (ServiceType, Code, Name, Internal, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
        VALUES( 7, 'FCG_SANCTION', 'SanctionCheck done by FCG', 0, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)
GO
-----------------------------------------------------------------------------------------------------------------------------------------

