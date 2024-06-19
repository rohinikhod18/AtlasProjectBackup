--===================================================================================================
-- Atlas Release 1.37 SQL queries
--===================================================================================================

-- 1. AT-3245 Disable Canada KYC checks
DELETE FROM [Comp].[KYC_CountryProviderMapping] 
WHERE [Country] = 'Canada';

---------------------------------------------------------------------------------------------------

--===================================================================================================
-- Atlas Release 1.37 ***ROLLBACK*** SQL queries
--===================================================================================================

-- 1. ***ROLLBACK*** AT-3245 Disable Canada KYC checks
INSERT INTO [Comp].[KYC_CountryProviderMapping]
([Country], [ProviderName], [CreatedOn], [CreatedBy], [UpdatedOn], [UpdatedBy])
VALUES('Canada', 'CARBONSERVICE', CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1);

---------------------------------------------------------------------------------------------------
