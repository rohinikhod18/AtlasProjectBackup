

######################## AT-3649 SQL Query #########################


ALTER Table Comp.PaymentOut ADD PaymentReferenceStatus tinyint


INSERT INTO Comp.Compliance_ServiceTypeEnum
(Code, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('BLACKLIST_PAY_REF', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO Comp.Compliance_ServiceProvider
(ServiceType, Code, Name, Internal, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES((Select ID From Compliance_ServiceTypeEnum WHERE Code = 'BLACKLIST_PAY_REF'), 'BLACKLIST_PAY_REF', 'Pay Reference Check', 0, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


INSERT INTO Comp.Compliance_ServiceProviderAttribute
(ID, [Attribute], CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES((Select ID From Compliance_ServiceProvider WHERE Code = 'BLACKLIST_PAY_REF'), '{"requestType":"BLACKLIST_PAY_REF", "endPointUrl":"https://uatpatmatch.currenciesdirect.com/patmatch/v1/paymentref/match", "fixedThreshold":89}', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


INSERT INTO Comp.ActivityTypeEnum
(Module, [Type], Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('PAYMENT_OUT', 'PAYMENT_REFERENCE_REPEAT', 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


INSERT INTO Comp.EventTypeEnum
([Type], CreatedBy, CreatedOn)
VALUES('FUNDSOUT_PAYMENT_REFERENCE_RESEND', 1, CURRENT_TIMESTAMP);




########################### Rollback SQL Query #############################

ALTER Table Comp.PaymentOut DROP COLUMN PaymentReferenceStatus


DELETE FROM Comp.Compliance_ServiceProviderAttribute WHERE ID = SELECT ID From Comp.Compliance_ServiceProvider Where Code = 'BLACKLIST_PAY_REF';


DELETE FROM Comp.Compliance_ServiceTypeEnum WHERE ID = SELECT ID From Comp.Compliance_ServiceTypeEnum Where Code = 'BLACKLIST_PAY_REF';


DELETE FROM Comp.Compliance_ServiceProvider WHERE ID = SELECT ID From Comp.Compliance_ServiceProvider Where Code = 'BLACKLIST_PAY_REF';


DELETE FROM Comp.ActivityTypeEnum WHERE [Type] = 'PAYMENT_REFERENCE_REPEAT';

DELETE FROM Comp.EventTypeEnum WHERE [Type] = 'FUNDSOUT_PAYMENT_REFERENCE_RESEND'
