UPDATE [comp].ActivityTypeEnum SET [TYPE] = 'FRAUDPREDICT_REPEAT' where [TYPE] = 'FRAUGSTER_REPEAT'

INSERT INTO [comp].StatusUpdateReason
(Module, Reason, Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('ALL', 'Awaiting POA Documents', 1, 1, getdate(), 1, getdate());

INSERT INTO [comp].StatusUpdateReason
(Module, Reason, Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('ALL', 'Awaiting POI Documents', 1, 1, getdate(), 1, getdate());

UPDATE ca SET [ATTRIBUTE] = '{"endPointUrl":"http://live-ml-api.currenciesdirect.local/fraudpredict/v1/signup", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"registrationThreesholdScore":"0.0037633646"}'
FROM [comp].Compliance_ServiceProviderAttribute ca JOIN [comp].Compliance_ServiceProvider cs ON cs.id=ca.id
WHERE cs.code='FRAUD_PREDICT_SIGNUP'

UPDATE ca SET [ATTRIBUTE] = '{"endPointUrl":"http://live-ml-api.currenciesdirect.local/fraudpredict/v1/payin", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"paymentInThreesholdScore":"0.024522840976715088"}'
FROM [comp].Compliance_ServiceProviderAttribute ca JOIN [comp].Compliance_ServiceProvider cs ON cs.id=ca.id
WHERE cs.code='FRAUD_PREDICT_FUNDSIN'

UPDATE ca SET [ATTRIBUTE] = '{"endPointUrl":"http://live-ml-api.currenciesdirect.local/fraudpredict/v1/payout", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"paymentOutThreesholdScore":"0.02225463092327118"}'
FROM [comp].Compliance_ServiceProviderAttribute ca JOIN [comp].Compliance_ServiceProvider cs ON cs.id=ca.id
WHERE cs.code='FRAUD_PREDICT_FUNDSOUT'


/*
 * 
 * 		ROLLBACK SCRIPT 
 * 
 * 
 */

UPDATE [comp].ActivityTypeEnum SET [TYPE] = 'FRAUGSTER_REPEAT' where [TYPE] = 'FRAUDPREDICT_REPEAT'

DELETE [comp].StatusUpdateReason
WHERE Reason = 'Awaiting POA Documents';

DELETE [comp].StatusUpdateReason
WHERE Reason = 'Awaiting POI Documents';

UPDATE ca SET [ATTRIBUTE] = '{"endPointUrl":"http://live-fraudpredict-api/v1/signup", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"registrationThreesholdScore":"0.0037633646"}'
FROM [comp].Compliance_ServiceProviderAttribute ca JOIN [comp].Compliance_ServiceProvider cs ON cs.id=ca.id
WHERE cs.code='FRAUD_PREDICT_SIGNUP';

UPDATE ca SET [ATTRIBUTE] = '{"endPointUrl":"http://live-fraudpredict-api/v1/payin", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"paymentInThreesholdScore":"0.024522840976715088"}'
FROM [comp].Compliance_ServiceProviderAttribute ca JOIN [comp].Compliance_ServiceProvider cs ON cs.id=ca.id
WHERE cs.code='FRAUD_PREDICT_FUNDSIN';

UPDATE ca SET [ATTRIBUTE] = '{"endPointUrl":"http://live-fraudpredict-api/v1/payout", "socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"paymentOutThreesholdScore":"0.02225463092327118"}'
FROM [comp].Compliance_ServiceProviderAttribute ca JOIN [comp].Compliance_ServiceProvider cs ON cs.id=ca.id
WHERE cs.code='FRAUD_PREDICT_FUNDSOUT';



