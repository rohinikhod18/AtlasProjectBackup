ALTER TABLE comp.WhiteListBeneficiary DROP COLUMN  Notes;

ALTER TABLE comp.WhiteListBeneficiary DROP CONSTRAINT UQ_WhiteListBeneficiary_AccountNumber ;

ALTER TABLE Comp.DeviceInfo DROP COLUMN ScreenResolution ;

DROP TABLE comp.ReproccessFailed
