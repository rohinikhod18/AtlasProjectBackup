
----- START : AT-2449 Create new IT Analyst role -----

--Role Table--
INSERT INTO [Comp].[Role] 
([SSORoleID], [Description], [Active], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('IT_ANALYST', 'IT_ANALYST', ((1)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

--Function Table--
INSERT INTO [Comp].[Function]([Name], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('canNotLockAccount', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

--Function Group Table--
INSERT INTO [Comp].[FunctionGroup]
([Name], [Active], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES('IT Analyst', ((1)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

--RoleFunctionGroupMapping Table--
INSERT INTO [Comp].[RoleFunctionGroupMapping]
([RoleID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT ID FROM [Comp].[Role] WHERE [SSORoleID] = 'IT_ANALYST'), 
		(SELECT ID FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

--FunctionGroupMapping Table--
INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canNotLockAccount'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canWorkOnCFX'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canWorkOnPFX'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewRegistrationQueue'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewPaymentInQueue'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewPaymentOutQueue'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewRegistrationReport'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewPaymentInReport'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewPaymentOutReport'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canDoAdministration'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewWorkEfficiancyReport'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewDashboard'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canManageCustom'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canManageFraud'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canManageEID'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canManageSanction'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canManageBlackList'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canManageWatchListCategory1'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canManageWatchListCategory2'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canManageBeneficiary'), 
		(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

----- END: AT-2449 Create new IT Analyst role -----

