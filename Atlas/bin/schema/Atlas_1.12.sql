ALTER TABLE comp.WhiteListBeneficiary ADD Notes nvarchar(2000);

ALTER TABLE comp.WhiteListBeneficiary ADD CONSTRAINT UQ_WhiteListBeneficiary_AccountNumber UNIQUE (AccountNumber);

----------------------------------------------- Queries for FraudPredict System--------------------------------------------------

ALTER TABLE Comp.DeviceInfo ADD ScreenResolution VARCHAR(20);

INSERT INTO Comp.Compliance_ServiceProvider(servicetype, code,name,internal, createdby,createdon,updatedby, updatedon)
VALUES(6,'FRAUDPREDICT_SERVICE','fraud prediction',1,1,getdate(),1,getdate());

INSERT INTO Comp.Compliance_ServiceProvider(servicetype, code,name,internal, createdby,createdon,updatedby, updatedon)
VALUES(6,'FRAUD_PREDICT_SIGNUP','fraud predict signup api',0,1,getdate(),1,getdate());


INSERT INTO Comp.Compliance_ServiceProvider(servicetype, code,name,internal, createdby,createdon,updatedby, updatedon)
VALUES(6,'FRAUD_PREDICT_FUNDSIN','fraud predict fundsin api',0,1,getdate(),1,getdate());


INSERT INTO Comp.Compliance_ServiceProvider(servicetype, code,name,internal, createdby,createdon,updatedby, updatedon)
VALUES(6,'FRAUD_PREDICT_FUNDSOUT','fraud predict fundsout api',0,1,getdate(),1,getdate());


INSERT INTO Comp.Compliance_ServiceProviderAttribute(id,"attribute",createdby,createdon,updatedby,updatedon)
VALUES (24,'{"requestType":"FRAUD_PREDICT_SIGNUP_SERVICE","endPointUrl":"http://atlas/fraugster-service/fraudDetection_signup"}',1,getdate(),1,getdate());


INSERT INTO Comp.Compliance_ServiceProviderAttribute(id,"attribute",createdby,createdon,updatedby,updatedon)
VALUES (25,'{"requestType":"FRAUD_PREDICT_ONUPDATE_SERVICE","endPointUrl":"http://atlas/fraugster-service/fraudDetection_onUpdate"}',1,getdate(),1,getdate());


INSERT INTO Comp.Compliance_ServiceProviderAttribute(id,"attribute",createdby,createdon,updatedby,updatedon)
VALUES (26,'{"requestType":"FRAUD_PREDICT_FUNDSOUT","endPointUrl":"http://atlas/fraugster-service/fraudDetection_paymentsOut"}',1,getdate(),1,getdate());

INSERT INTO Comp.Compliance_ServiceProviderAttribute(id,"attribute",createdby,createdon,updatedby,updatedon)
VALUES (27,'{"requestType":"FRAUD_PREDICT_FUNDSIN","endPointUrl":"http://atlas/fraugster-service/fraudDetection_paymentsIn"}',1,getdate(),1,getdate());


INSERT INTO Comp.Compliance_ServiceProviderAttribute(id,"attribute",createdby,createdon,updatedby,updatedon)
VALUES (34,'{"endPointUrl":"http://live-fraudpredict-api/v1/signup", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"registrationThreesholdScore":"0.0037633646"}',1,getdate(),1,getdate());


INSERT INTO Comp.Compliance_ServiceProviderAttribute(id,"attribute",createdby,createdon,updatedby,updatedon)
VALUES (35,'{"endPointUrl":"http://live-fraudpredict-api/v1/payin", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"paymentInThreesholdScore":"0.019247167"}',1,getdate(),1,getdate());


INSERT INTO Comp.Compliance_ServiceProviderAttribute(id,"attribute",createdby,createdon,updatedby,updatedon)
VALUES (36,'{"endPointUrl":"http://live-fraudpredict-api/v1/payout", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"paymentOutThreesholdScore":"0.017917799"}',1,getdate(),1,getdate());

-----------------------------------------Bulk Recheck Reprocess Failed Table-------------------------------------
CREATE TABLE comp.ReproccessFailed(
			ID int NOT NULL IDENTITY(1,	1),
			TransType tinyint,
			BatchId int,
			TransId bigint NOT NULL,
			Status tinyint,
			RetryCount int,
			CreatedBy int NOT NULL,
			CreatedOn datetime,
			CONSTRAINT PK_ReproccessFailed PRIMARY KEY(ID),
			CONSTRAINT [CN_UniqueReproccessFailed] UNIQUE NONCLUSTERED(
				[TransType] ASC,
				[TransId] ASC
			)
		)

------------------------------------------------------------------------------------------------------------------

-------------------------------------- FundsOut recheck failures --------------------------------------------------
INSERT INTO comp.EventTypeEnum ("Type", CreatedBy, CreatedOn) VALUES('FUNDSOUT_RECHECK_FAILURES', 1, getDate());

INSERT INTO comp.ActivityTypeEnum (Module, "Type", Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('PAYMENT_OUT', 'ADMIN_BULK_RECHECK', 1, 1, getdate(), 1, getdate());
-------------------------------------- FundsOut recheck failures --------------------------------------------------

-------------------------------------- Fundsin recheck failures --------------------------------------------------
INSERT INTO comp.EventTypeEnum ("Type", CreatedBy, CreatedOn) VALUES('FUNDSIN_RECHECK_FAILURES', 1, getDate());

INSERT INTO comp.ActivityTypeEnum (Module, "Type", Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('PAYMENT_IN', 'ADMIN_BULK_RECHECK', 1, 1, getdate(), 1, getdate());
-----------------------------------------------------------------------------------------------------------------

-------------------------------------- Fundsin recheck failures --------------------------------------------------
INSERT INTO comp.EventTypeEnum ("Type", CreatedBy, CreatedOn) VALUES('PROFILE_RECHECK_FAILURES', 1, getDate());

INSERT INTO comp.ActivityTypeEnum (Module, "Type", Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('PROFILE', 'ADMIN_BULK_RECHECK', 1, 1, getdate(), 1, getdate());
-----------------------------------------------------------------------------------------------------------------

