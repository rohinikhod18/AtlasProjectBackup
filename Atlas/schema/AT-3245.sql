


--*********Delete canada country from KYC_CountryProviderMapping*********

DELETE FROM Comp.KYC_CountryProviderMapping where Country='Canada';


--------------------------------------------------------------------------------------------------
--*********ROLLBACK : Delete canada country from KYC_CountryProviderMapping*********
INSERT INTO Comp.KYC_CountryProviderMapping
(Country, ProviderName, CreatedOn, CreatedBy, UpdatedOn, UpdatedBy)
VALUES('Canada', 'CARBONSERVICE', CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1);
