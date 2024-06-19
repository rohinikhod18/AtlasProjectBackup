/* AT-2248 */

/*Create table*/
CREATE TABLE Comp.EventServiceLogSummary( 
	EntityServiceHash bigint NOT NULL,
	Status tinyint NOT NULL,
	EventServiceLogID  bigint NOT NULL,
	CONSTRAINT PK_EventServiceLogSummary PRIMARY KEY (EntityServiceHash)
);
ALTER TABLE Comp.EventServiceLogSummary ADD CONSTRAINT FK_EventServiceLogSummar_Status_ServiceStatusEnum_Id FOREIGN KEY (Status) REFERENCES Comp.ServiceStatusEnum(ID) ;
ALTER TABLE Comp.EventServiceLogSummary ADD CONSTRAINT FK_EventServiceLogSummar_eslID_EventServiceLog_Id FOREIGN KEY (EventServiceLogID) REFERENCES Comp.EventServiceLog(ID) ;

/* Bulk Update for Contact Fragster */

WITH ConAccID  AS(
	SELECT id AS contactID, AccountID FROM Comp.Contact --ORDER BY id DESC 
), SelectedID AS
( SELECT Row_number() OVER (PARTITION BY esl.EntityID ORDER BY esl.updatedon DESC) rnum ,        
	esl.entityType,esl.EntityID, esl.id AS eventServiceLogId, ServiceType, Status
	FROM Comp.EventServiceLog esl JOIN Comp.Event e ON E.id=esl.eventid  and e.eventType IN (1,2,3)
		JOIN ConAccID c ON e.accountid=c.accountid AND esl.EntityID =c.contactid AND esl.EntityType = 3
	WHERE  esl.ServiceType=6 And esl.status NOT IN(5,8,9) 
	and JSON_VALUE(esl.ProviderResponse, '$.frgTransId') is not null
)
INSERT INTO Comp.EventServiceLogSummary (EntityServiceHash, Status, EventServiceLogID)
SELECT EntityId * 10000 + EntityType * 100 + ServiceType AS EntityServiceHash, status, eventServiceLogId 
FROM SelectedID WHERE rnum=1

/* Bulk Update for Account Sanction */

WITH ConAccID AS(
	SELECT id AS contactID, AccountID FROM Comp.Contact --ORDER BY id DESC 
), SelectedID AS
( SELECT Row_number() OVER (PARTITION BY esl.EntityID ORDER BY esl.updatedon DESC) rnum ,        
	esl.entityType,esl.EntityID, esl.id AS eventServiceLogId, ServiceType, Status
	FROM Comp.EventServiceLog esl JOIN Comp.Event e ON E.id=esl.eventid  and e.eventType IN (1,2,3)
		JOIN ConAccID c ON e.accountid=c.accountid AND esl.EntityID =c.AccountID AND esl.EntityType = 1
	WHERE esl.ServiceType = 7 And esl.status NOT IN(5,8,9) 
	and JSON_VALUE(esl.Summary, '$.sanctionId') is not null
)
INSERT INTO Comp.EventServiceLogSummary (EntityServiceHash, Status, EventServiceLogID)
SELECT EntityId * 10000 + EntityType * 100 + ServiceType AS EntityServiceHash, status, eventServiceLogId 
FROM SelectedID 
WHERE rnum=1
AND NOT EXISTS (
	SELECT esls.EntityServiceHash 
	FROM Comp.EventServiceLogSummary esls 
	WHERE esls.EntityServiceHash = EntityId * 10000 + EntityType * 100 + ServiceType);
	
/* Bulk Update for Contact Sanction */

WITH SelectedID AS ( 
	SELECT Row_number() OVER (PARTITION BY esl.EntityID ORDER BY esl.updatedon DESC) rnum ,
		esl.entityType,
		esl.EntityID, 
		esl.id AS eventServiceLogId, 
		ServiceType, 
		Status
	FROM Comp.EventServiceLog esl JOIN Comp.Event e ON E.id=esl.eventid and e.eventType IN (1,2,3,5,7)
	WHERE esl.ServiceType = 7 
	AND esl.EntityType = 3
)
INSERT INTO Comp.EventServiceLogSummary (EntityServiceHash, Status, EventServiceLogID)
SELECT EntityId * 10000 + EntityType * 100 + ServiceType AS EntityServiceHash, status, eventServiceLogId 
FROM SelectedID WHERE rnum=1
AND NOT EXISTS (
	SELECT esls.EntityServiceHash, esls.EventServiceLogId
	FROM Comp.EventServiceLogSummary esls 
	WHERE esls.EntityServiceHash = EntityId * 10000 + EntityType * 100 + ServiceType
	AND esls.EventServiceLogId = eventServiceLogId);

/*Bulk Update of Beneficiary Sanction*/

WITH SelectedID AS (
	SELECT ROW_NUMBER() over (PARTITION BY esl.EntityID ORDER BY esl.updatedon DESC) rnum,
		esl.entityType,
		esl.EntityID,
		esl.id AS eventServiceLogId,
		esl.ServiceType,
		esl.Status
	FROM Comp.EventServiceLog esl
	JOIN Comp.Event e on e.ID = esl.EventID AND e.eventType = 7
	where esl.EntityType = 2
	AND esl.ServiceType = 7
)
INSERT INTO Comp.EventServiceLogSummary (EntityServiceHash, Status, EventServiceLogID)
SELECT EntityId * 10000 + EntityType * 100 + ServiceType AS EntityServiceHash, status, eventServiceLogId
FROM SelectedID WHERE rnum=1
AND NOT EXISTS (
	SELECT esls.EntityServiceHash, esls.EventServiceLogId
	FROM Comp.EventServiceLogSummary esls
	WHERE esls.EntityServiceHash = EntityId * 10000 + EntityType * 100 + ServiceType
	AND esls.EventServiceLogId = eventServiceLogId);
	
/*Bulk Update of Bank Sanction*/

WITH SelectedID AS (
	SELECT ROW_NUMBER() over (PARTITION BY esl.EntityID ORDER BY esl.updatedon DESC) rnum,
		esl.entityType,
		esl.EntityID,
		esl.id AS eventServiceLogId,
		esl.ServiceType,
		esl.Status
	FROM Comp.EventServiceLog esl
	JOIN Comp.Event e on e.ID = esl.EventID AND e.eventType = 7
	where esl.EntityType = 4
	AND esl.ServiceType = 7
)
INSERT INTO Comp.EventServiceLogSummary (EntityServiceHash, Status, EventServiceLogID)
SELECT EntityId * 10000 + EntityType * 100 + ServiceType AS EntityServiceHash, status, eventServiceLogId
FROM SelectedID WHERE rnum=1
AND NOT EXISTS (
	SELECT esls.EntityServiceHash, esls.EventServiceLogId
	FROM Comp.EventServiceLogSummary esls
	WHERE esls.EntityServiceHash = EntityId * 10000 + EntityType * 100 + ServiceType
	AND esls.EventServiceLogId = eventServiceLogId);

------------------------------------------------------------------------------------------------------------------------------
/*Rollback Script*/

Alter table Comp.EventServiceLogSummary DROP CONSTRAINT FK_EventServiceLogSummar_Status_ServiceStatusEnum_Id

Alter table Comp.EventServiceLogSummary DROP CONSTRAINT FK_EventServiceLogSummar_eslID_EventServiceLog_Id

DROP TABLE Comp.EventServiceLogSummary;
