-- Queries for Atlas sprint 1.25 --

/****************************************************************
 *
 * SCRIPT FOR update device id datatype and size
 * AT-1952 Increase device_info table size from 100 to 300
 *
 ***************************************************************/

ALTER TABLE [DeviceInfo] ALTER COLUMN [DeviceID] varchar(300);

/****************************************************************
 *
 * SCRIPT FOR New reasons added into StatusReasonUpdate table for Contact 'INACTIVE'
 * AT-2049 Add new inactive or reject reason
 *
 ***************************************************************/

INSERT INTO [StatusUpdateReason]([Module], [Reason], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
 VALUES('ALL', 'Suspected fake documents – Onfido', 1, CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP)

INSERT INTO [StatusUpdateReason]([Module], [Reason], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
 VALUES('ALL', 'Confirmed fake documents – Onfido', 1, CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP)


/** AT-2047 - Account status changes in Atlas according to onfido results*/
INSERT INTO [Watchlist] ([Reason], [StopPaymentIn], [StopPaymentOut], [Locked], [Deleted], [CreatedBy], [CreatedOn], [UpdatedBy],[UpdatedOn], [Category]) VALUES('Onfido Suspect',1,1,0,0,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,1);

INSERT INTO [Compliance_ServiceTypeEnum] ([Code],[CreatedBy],[CreatedOn],[UpdatedBy],[UpdatedOn])
VALUES('ONFIDO',1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP);

INSERT INTO [Compliance_ServiceProvider] ([ServiceType], [Code], [Name], [Internal], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])
VALUES ((SELECT ID FROM [Compliance_ServiceTypeEnum] WHERE Code = 'ONFIDO'), 'ONFIDO', 'Profile Status Update', 0, 1,CURRENT_TIMESTAMP, 1,CURRENT_TIMESTAMP);

INSERT INTO [EventTypeEnum] ([Type],[CreatedBy],[CreatedOn])
VALUES('PROFILE_STATUS_UPDATE',1,CURRENT_TIMESTAMP);

INSERT INTO [StatusUpdateReason] ([Module],[Reason],[Active],[CreatedBy],[CreatedOn],[UpdatedBy],[UpdatedOn]) 
VALUES ('ALL','Onfido Reject',1,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP);

INSERT INTO [StatusUpdateReason] ([Module],[Reason],[Active],[CreatedBy],[CreatedOn],[UpdatedBy],[UpdatedOn]) 
VALUES ('ALL','Onfido Caution',1,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP);

INSERT INTO [StatusUpdateReason] ([Module],[Reason],[Active],[CreatedBy],[CreatedOn],[UpdatedBy],[UpdatedOn]) 
VALUES ('ALL','Onfido Suspect',1,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP);

/** AT-2048 - Onfido status Search functionality on registration pages*/
ALTER TABLE [Account] ADD DocumentVerifiedStatus tinyint;

/** AT-2050 - Onfido response need to show in tabular format */
INSERT INTO [ActivityTypeEnum] ([Module] ,[Type], [Active], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
VALUES('PROFILE','ONFIDO_UPDATE',1,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP);




/** RollBack Script */
DELETE FROM [StatusUpdateReason] WHERE [Reason] = 'Suspected fake documents – Onfido'
DELETE FROM [StatusUpdateReason] WHERE [Reason] = 'Confirmed fake documents – Onfido'

DELETE FROM [Watchlist] WHERE [Reason] = 'Onfido Suspect'
DELETE FROM [Compliance_ServiceTypeEnum] WHERE [Code] = 'ONFIDO'
DELETE FROM [Compliance_ServiceProvider] WHERE [Code] = 'ONFIDO' AND [Name] = 'Profile Status Update'
DELETE FROM [EventTypeEnum] WHERE [Type] = 'PROFILE_STATUS_UPDATE'
																									   
DELETE FROM [StatusUpdateReason] WHERE [Reason] = 'Onfido Reject'
DELETE FROM [StatusUpdateReason] WHERE [Reason] = 'Onfido Caution'
DELETE FROM [StatusUpdateReason] WHERE [Reason] = 'Onfido Suspect'
																									
ALTER TABLE [Account] DROP COLUMN [DocumentVerifiedStatus];
																									
DELETE FROM [ActivityTypeEnum] WHERE [Module] = 'PROFILE' AND [Type] = 'ONFIDO_UPDATE'
