--Rollback Script--

----- START : AT-2449 Create new IT Analyst role -----

--RoleFunctionGroupMapping Table--
DELETE [Comp].[RoleFunctionGroupMapping] 
WHERE [FunctionGroupID] IN
(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst');

--FunctionGroupMapping Table--
DELETE [Comp].[FunctionGroupMapping] 
WHERE [FunctionGroupID] IN
(SELECT [ID] FROM [Comp].[FunctionGroup] WHERE [Name] = 'IT Analyst');

--FunctionGroup Table--
DELETE [Comp].[FunctionGroup] 
WHERE Name= 'IT Analyst'

--Function Table--
DELETE [Comp].[Function] 
WHERE Name= 'canNotLockAccount';

--Role Table--
DELETE [Comp].[Role] 
WHERE [SSORoleID] = 'IT_ANALYST';

----- END : AT-2449 Create new IT Analyst role -----
