
/*******************Query to create DataAnonActivityLogHistory table ****************/  

 CREATE TABLE Comp.DataAnonActivityLogHistory (
      ID int NOT NULL,
      Activity varchar(1024),
      Comment nvarchar(1024),
      ActivityBy int,
      ActivityDate datetime DEFAULT (getdate()),
      CONSTRAINT FK_DataAnonActivityLogHistory_ID FOREIGN KEY (ID) REFERENCES Comp.DataAnonActivityLog(ID)
)


/*** Query to migrate old records from DataAnonActivityLog to DataAnonActivityLogHistory table(for production db) ***/  

INSERT INTO Comp.DataAnonActivityLogHistory
(ID,Activity,Comment,ActivityBy,ActivityDate)
select
ID,'INITIALIZATION',InitialApprovalComment, InitialApprovalBy,InitialApprovalDate
FROM Comp.DataAnonActivityLog
UNION
select
ID,'APPROVAL',FinalApprovalComment,FinalApprovalBy,FinalApprovalDate
FROM Comp.DataAnonActivityLog
UNION
select
ID,'REJECTION',RejectedComment, RejectedBy,RejectedDate
FROM Comp.DataAnonActivityLog

