----------------queries to set fix Threshold for Fraudsight--------------------------

INSERT INTO Comp.Compliance_ServiceTypeEnum (Code,CreatedBy,CreatedOn,UpdatedBy,UpdatedOn)
VALUES('FRAUDSIGHTSCORE',1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP);

INSERT INTO Comp.Compliance_ServiceProvider (ServiceType, Code, Name, Internal, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES ((SELECT ID FROM Comp.Compliance_ServiceTypeEnum WHERE Code = 'FRAUDSIGHTSCORE'), 'FRAUDSIGHTSCORE', 'Check Payment fraud sight score', 1, 1,CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP);

INSERT INTO Comp.Compliance_ServiceProviderAttribute (ID, Attribute, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES((select ID from Comp.Compliance_ServiceProvider where code = 'FRAUDSIGHTSCORE'), '{"requestType":"FRAUDSIGHT_CHECK","low-risk":0.86,"review":0.86,"high-risk":0.25}', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP)


----------------to add new column in PaymentIn table--------------------------
ALTER TABLE Comp.PaymentIn
ADD DebitCardFraudCheckStatus tinyint;

----------------to get RG status from eventServiceLog to (DebitCardFraudCheckStatus column)PaymentIn table--------------------------
WITH ExistingRgStatus AS(
SELECT pin.ID as paymentId,pin.DebitCardFraudCheckStatus ,esl.Status AS existingStatus
FROM Comp.PaymentIn pin
JOIN Comp.Event e on e.PaymentInID =pin.ID 
JOIN Comp.EventServiceLog esl on esl.EventID = e.ID 
WHERE esl.ServiceType =11 
)
UPDATE PaymentIn 
SET DebitCardFraudCheckStatus=ers.existingStatus 
FROM ExistingRgStatus ers
JOIN Comp.PaymentIn pi2 ON pi2.ID = ers.paymentId
----------------------------------------------------------------------------------------------------------------------------------------

----------------------------Rollback Script--------------------------------------------------------
DELETE FROM Comp.Compliance_ServiceProviderAttribute
WHERE ID=(SELECT ID FROM Comp.Compliance_ServiceProvider WHERE Code='FRAUDSIGHTSCORE')

DELETE FROM Comp.Compliance_ServiceProvider
WHERE ServiceType=(SELECT ID FROM Comp.Compliance_ServiceTypeEnum WHERE Code = 'FRAUDSIGHTSCORE')

DELETE FROM Comp.Compliance_ServiceTypeEnum WHERE Code = 'FRAUDSIGHTSCORE'

ALTER TABLE Comp.PaymentIn
DROP COLUMN DebitCardFraudCheckStatus;

