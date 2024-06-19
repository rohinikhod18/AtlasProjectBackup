-- ***** Atlas release 1.36 scripts (06 October 2020) ***** --

-- USE [Compliance]

--For AT-3161--
--1. Update StopPaymentIn/OUT Staus for Fraud Predict Watchlist--

UPDATE [Comp].[WatchList] 
SET [StopPaymentIn] = 0, [StopPaymentOut] = 0, [UpdatedBy] = 1, [UpdatedOn] = CURRENT_TIMESTAMP 
WHERE [Reason] = 'Fraudpredict high risk of fraud';

--2. Update fraudPredictCustomCheckEnabled flag in Compliance_ServiceProviderAttribute table under FRAUD_PREDICT_SIGNUP--

UPDATE [Comp].[Compliance_ServiceProviderAttribute]
SET [Attribute] = '{"endPointUrl":"http://live-ml-api.currenciesdirect.local/fraudpredict/v1/signup","socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"registrationThreesholdScore":"0.007600000000000007","fraudPredictCustomCheckEnabled":true}'
, [UpdatedBy] = 1, [UpdatedOn] = CURRENT_TIMESTAMP
WHERE [ID] = (Select [ID] FROM [Comp].[Compliance_ServiceProvider] where [Code] = 'FRAUD_PREDICT_SIGNUP');

--For AT-3199--
--3. SET  hlFlag For migration-- Until migration setting default flag to false

UPDATE [Comp].[Compliance_ServiceProviderAttribute]
SET [Attribute] = 
'{"endPointUrl":"https://www.id3global.com/ID3gWS/ID3global.svc/Soap11_Auth","qname":"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","userName":"sysadmin@currenciesdirect.com","passWord":"Deltabrav02018!!","profileVersionIDList":[{"country":"UNITED KINGDOM","id":"82b9e896-e162-4933-a5a9-06b84c19fc2a"},{"country":"DENMARK","id":"8a53c583-a7a5-4d92-93dc-5b1575921b01"},{"country":"SWITZERLAND","id":"69230f77-2792-4e01-8fe7-0945092cc613"},{"country":"GERMANY","id":"a510863a-e29b-4fb3-bfab-77c8637b0c12"},{"country":"NETHERLANDS","id":"c16f7960-493b-42ed-8456-b91db5a60f29"},{"country":"SPAIN","id":"b5693836-fa0f-42e2-bcc3-6806b82d4fbb"},{"country":"SWEDEN","id":"3d7e8f4d-57a4-4e5b-863b-492fd68867e0"},{"country":"NORWAY","id":"ecb11786-ba32-4447-8591-9bf6e8420a50"}],"socketTimeoutMillis":25000,"connectionTimeoutMillis":25000,"hlFlag":false}'
, [UpdatedBy] = 1, [UpdatedOn] = CURRENT_TIMESTAMP
WHERE [ID] = (Select [ID] FROM [Comp].[Compliance_ServiceProvider] where [Code] = 'GBGROUP');

--====================================================================================================================================--

-- Atlas release 1.36 **ROLLBACK** scripts

--For AT-3161--
--1. ROLLBACK: Update StopPaymentIn/OUT Staus for Fraud Predict Watchlist--
UPDATE [Comp].[WatchList] 
SET [StopPaymentIn] = 1, [StopPaymentOut] = 1, [UpdatedBy] = 1, [UpdatedOn] = CURRENT_TIMESTAMP 
WHERE [Reason] = 'Fraudpredict high risk of fraud';


--2. ROLLBACK: Update fraudPredictCustomCheckEnabled flag in Compliance_ServiceProviderAttribute table under FRAUD_PREDICT_SIGNUP--
UPDATE [Comp].[Compliance_ServiceProviderAttribute]
SET [Attribute] = '{"endPointUrl":"http://live-ml-api.currenciesdirect.local/fraudpredict/v1/signup","socketTimeoutMillis":6000,"connectionTimeoutMillis":6000,"registrationThreesholdScore":"0.007600000000000007"}'
, [UpdatedBy] = 1, [UpdatedOn] = CURRENT_TIMESTAMP
WHERE [ID] = (Select [ID] FROM [Comp].[Compliance_ServiceProvider] where [Code] = 'FRAUD_PREDICT_SIGNUP');


--For AT-3199--
--3. ROLLBACK: SET  hlFlag For migration-- Until migration setting default flag to false
UPDATE [Comp].[Compliance_ServiceProviderAttribute]
SET [Attribute] = 
'{"endPointUrl":"https://www.id3global.com/ID3gWS/ID3global.svc/Soap11_Auth","qname":"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","userName":"sysadmin@currenciesdirect.com","passWord":"Deltabrav02018!!","profileVersionIDList":[{"country":"UNITED KINGDOM","id":"82b9e896-e162-4933-a5a9-06b84c19fc2a"},{"country":"DENMARK","id":"8a53c583-a7a5-4d92-93dc-5b1575921b01"},{"country":"SWITZERLAND","id":"69230f77-2792-4e01-8fe7-0945092cc613"},{"country":"GERMANY","id":"a510863a-e29b-4fb3-bfab-77c8637b0c12"},{"country":"NETHERLANDS","id":"c16f7960-493b-42ed-8456-b91db5a60f29"},{"country":"SPAIN","id":"b5693836-fa0f-42e2-bcc3-6806b82d4fbb"},{"country":"SWEDEN","id":"3d7e8f4d-57a4-4e5b-863b-492fd68867e0"},{"country":"NORWAY","id":"ecb11786-ba32-4447-8591-9bf6e8420a50"}],"socketTimeoutMillis":25000,"connectionTimeoutMillis":25000}'
, [UpdatedBy] = 1, [UpdatedOn] = CURRENT_TIMESTAMP
WHERE [ID] = (Select [ID] FROM [Comp].[Compliance_ServiceProvider] where [Code] = 'GBGROUP');

--====================================================================================================================================--
