-------------------------------------------------------------------------------------------------
-- Atlas 1.31 release DB Scripts
-------------------------------------------------------------------------------------------------

-- AT-2620, AT-2707
UPDATE [Comp].[Compliance_ServiceProviderAttribute]
SET [Attribute]='{"endPointUrl":"https://hosted5.finscan.com/isi/wrapper/v4.8.1/LSTServicesLookup.asmx?wsdl","userName":"webservices","passWord":"webservices12","socketTimeoutMillis":120000,"connectionTimeoutMillis":120000}', 
[UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=17;
-------------------------------------------------------------------------------------------------

-- AT-2804
-- *** NOTE : No WHERE Clause as all rows to be updated ****
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=99, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP;
-------------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------------
-- ***** RollBack Script *****
-------------------------------------------------------------------------------------------------

-- AT-2620, AT-2707
UPDATE [Comp].[Compliance_ServiceProviderAttribute]
SET [Attribute]='{"endPointUrl":"https://hosted5.finscan.com/isi/LSTServicesLookup.asmx","userName":"webservices","passWord":"webservices12","socketTimeoutMillis":120000,"connectionTimeoutMillis":120000}', 
[UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=17;
-------------------------------------------------------------------------------------------------

-- AT-2804

UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=2, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=1;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=5, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=2;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=2, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=3;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=5, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=4;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=2, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=5;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=3, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=6;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=2, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=7;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=2, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=8;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=2, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=9;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=5, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=10;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=2, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=11;
UPDATE [Comp].[VelocityRules]
SET [CountThreshold]=2, [UpdatedBy]=1, [UpdatedOn]=CURRENT_TIMESTAMP
WHERE [ID]=12;
-------------------------------------------------------------------------------------------------