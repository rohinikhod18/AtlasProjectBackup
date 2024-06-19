--Remove current permission from FunctionGroupMapping Table
DELETE FROM [Comp].[FunctionGroupMapping]
WHERE [FunctionID] = (SELECT [ID] FROM [Comp].[Function] WHERE [Name] = 'canViewDataAnonQueue')

DELETE FROM [Comp].[Function] 
WHERE [Name] = 'canViewDataAnonQueue';


--Role Table
INSERT INTO [Comp].[Role] 
([SSORoleID], [Description], [Active], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('ATLAS_DATA_ANON_INITIATOR', 'ATLAS_DATA_ANON_INITIATOR', ((1)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[Role] 
([SSORoleID], [Description], [Active], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('ATLAS_DATA_ANON_APPROVER', 'ATLAS_DATA_ANON_APPROVER', ((1)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

--Function Table
INSERT INTO [Comp].[Function]([Name], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('canInitiateDataAnon', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[Function]([Name], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('canApproveDataAnon', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);
																			  
--FunctionGroup Table
INSERT INTO [Comp].[FunctionGroup]
([Name], [Active], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES('Data Anonymisation Initiator', ((1)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroup]
([Name], [Active], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES('Data Anonymisation Approver', ((1)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


--FunctionGroupMapping Table
INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((select [ID] from [Comp].[Function] where [Name] = 'canInitiateDataAnon'), 
		(select [ID] from [Comp].[FunctionGroup] where [Name] = 'Data Anonymisation Initiator'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((select [ID] from [Comp].[Function] where [Name] = 'canApproveDataAnon'), 
		(select [ID] from [Comp].[FunctionGroup] where [Name] = 'Data Anonymisation Approver'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


--RoleFunctionGroupMapping Table
INSERT INTO [Comp].[RoleFunctionGroupMapping]
([RoleID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT ID FROM [Comp].[Role] WHERE [SSORoleID] = 'ATLAS_DATA_ANON_INITIATOR'), 
		(SELECT ID FROM [Comp].[FunctionGroup] WHERE [Name] = 'Data Anonymisation Initiator'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO [Comp].[RoleFunctionGroupMapping]
([RoleID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((SELECT ID FROM [Comp].[Role] WHERE [SSORoleID] = 'ATLAS_DATA_ANON_APPROVER'), 
		(SELECT ID FROM [Comp].[FunctionGroup] WHERE [Name] = 'Data Anonymisation Approver'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

--Rollback Script--

--RoleFunctionGroupMapping Table
DELETE [Comp].[RoleFunctionGroupMapping] 
where [FunctionGroupID] =
(select [ID] from [Comp].[FunctionGroup] where [Name] = 'Data Anonymisation Initiator');

DELETE [Comp].[RoleFunctionGroupMapping] 
where [FunctionGroupID] =
(select [ID] from [Comp].[FunctionGroup] where [Name] = 'Data Anonymisation Approver');
											
--Role Table
DELETE [Comp].[Role] 
where [SSORoleID] = 'ATLAS_DATA_ANON_INITIATOR';

DELETE [Comp].[Role] 
where [SSORoleID] = 'ATLAS_DATA_ANON_APPROVER';

--FunctionGroupMapping Table
DELETE [Comp].[FunctionGroupMapping] 
where [FunctionGroupID] =
(select [ID] from [Comp].[FunctionGroup] where [Name] = 'Data Anonymisation Initiator');

DELETE [Comp].[FunctionGroupMapping] 
where [FunctionGroupID] =
(select [ID] from [Comp].[FunctionGroup] where [Name] = 'Data Anonymisation Approver');

--Function Table
DELETE [Comp].[Function] 
where Name= 'canInitiateDataAnon';

DELETE [Comp].[Function] 
where Name= 'canApproveDataAnon';

--FunctionGroup Table
DELETE [Comp].[FunctionGroup] 
where Name= 'Data Anonymisation Initiator';

DELETE [Comp].[FunctionGroup] 
where Name= 'Data Anonymisation Approver';

INSERT INTO [Comp].[Function]([Name], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('canViewDataAnonQueue', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

--To add Previous permission to FunctionGroupMapping Table
INSERT INTO [Comp].[FunctionGroupMapping]([FunctionID], [FunctionGroupID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES((select [ID] from [Comp].[Function] where [Name] = 'canViewDataAnonQueue'), 
		(select [ID] from [Comp].[FunctionGroup] where [Name] = 'Departmental Heads'), 
		1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


						  
