INSERT INTO Comp.EventTypeEnum ("Type",CreatedBy, CreatedOn) 
VALUES('FUNDSIN_BLACKLIST_RESEND',1,getdate());

INSERT INTO Comp.ActivityTypeEnum(Module,"Type",CreatedBy,CreatedOn,UpdatedBy,UpdatedOn) 
VALUES('PAYMENT_IN','BLACKLIST_REPEAT',1,getdate(),1,getdate());

INSERT INTO Comp.EventTypeEnum ("Type",CreatedBy, CreatedOn) 
VALUES('FUNDSOUT_BLACKLIST_RESEND',1,getdate());

INSERT INTO Comp.ActivityTypeEnum(Module,"Type",CreatedBy,CreatedOn,UpdatedBy,UpdatedOn) 
VALUES('PAYMENT_OUT','BLACKLIST_REPEAT',1,getdate(),1,getdate());

INSERT INTO [Comp].[EventTypeEnum]("Type",CreatedBy,CreatedOn) VALUES('PROFILE_BLACKLIST_RESEND',1,getDate());

INSERT INTO Comp.ActivityTypeEnum(Module,"Type",CreatedBy,CreatedOn,UpdatedBy,UpdatedOn) 
VALUES('PROFILE','BLACKLIST_REPEAT',1,getdate(),1,getdate());

INSERT INTO Comp.PaymentComplianceStatusEnum
(Status, Description, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('REVERSED', 'Payment is reversed', 1, (getdate()), 1, (getdate()));

ALTER TABLE blacklistdata ADD Notes NVARCHAR(2000);