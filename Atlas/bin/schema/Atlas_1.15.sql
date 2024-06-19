ALTER TABLE Comp.Contact ADD POI_NEEDED bit DEFAULT(0);

------- Queries added for Changed in Lexis Nexis -----------------

INSERT INTO Comp.Compliance_ServiceProvider
(ServiceType, Code, Name, Internal, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES(1, 'CARBONSERVICE', 'LexisNexis CarbonServices IdentityCheck', 0, 1, getdate(), 1, getdate());

INSERT INTO Comp.Compliance_ServiceProviderAttribute
(ID, [Attribute], CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES((SELECT id FROM Comp.Compliance_ServiceProvider WHERE CODE='CARBONSERVICE'), '{"endPointUrl":"https://staging.ws.idms.lexisnexis.com/carbon/ws","qname":"http://carbon.verid.ws.risk.lexisnexis.com/","userName":"Currencies_Direct_Limited_B2B_STG","passWord":"Dv9%Zfpr#","socketTimeoutMillis":25000,"connectionTimeoutMillis":25000,"alwaysPass":true,"credentialTypeUserName":"user27","credentialTypeDomain":"example.com","credentialTypeIpAddress":"127.0.0.1","identityVerificationAccountName":"41436","identityVerificationRuleset":"WF_Currencies_Direct_Limited_STG","accountTypeCustomerId":"90439049039-0349"}', 1, getdate(), 1, getdate());

UPDATE KYC_CountryProviderMapping SET ProviderName = 'CARBONSERVICE' where Country IN ('Australia','New Zealand','Canada','South Africa','Japan','Mexico','Austria','Brazil','Luxembourg','Hong Kong')

------- END of Queries added for Changed in Lexis Nexis -----------------



/***
 * 
 * ROLLBACK SCRIPT
 * 
 * 
 */

ALTER TABLE Comp.Contact DROP COLUMN POI_NEEDED;

DELETE FROM Comp.Compliance_ServiceProviderAttribute WHERE ID = (SELECT id FROM Comp.Compliance_ServiceProvider WHERE CODE='CARBONSERVICE');

DELETE FROM Comp.Compliance_ServiceProvider WHERE Code = 'CARBONSERVICE';

UPDATE KYC_CountryProviderMapping SET ProviderName = 'LEXISNEXIS' where Country IN ('Australia','New Zealand','Canada','South Africa','Japan','Mexico','Austria','Brazil','Luxembourg','Hong Kong')
