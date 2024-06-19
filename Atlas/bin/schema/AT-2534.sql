
/*******************  Update DataAnonActivityLog table columns (AT-2534) ****************/

ALTER TABLE Comp.DataAnonActivityLog ADD RejectedComment nvarchar(1024) NULL;

ALTER TABLE Comp.DataAnonActivityLog ADD RejectedBy int NULL;

ALTER TABLE Comp.DataAnonActivityLog ADD RejectedDate datetime DEFAULT getdate() NULL; 

/*******************  Query to rename DataAnonActivityLog table ****************/ 

exec sp_rename 'DataAnonActivityLog', 'DataAnonActivityLog_old'

/*******************  Query to create new DataAnonActivityLog table (changed data type of ID)****************/  
 
CREATE TABLE Comp.DataAnonActivityLog (
	ID int NOT NULL IDENTITY(1,1),
	AccountID int NOT NULL,
	ContactID int NOT NULL,
	InitialApprovalComment nvarchar(1024) NOT NULL,
	InitialApprovalBy int NOT NULL,
	InitialApprovalDate datetime DEFAULT (getdate()) NOT NULL,
	FinalApprovalComment nvarchar(1024),
	FinalApprovalBy int,
	FinalApprovalDate datetime DEFAULT (getdate()),
	CreatedBy int NOT NULL,
	CreatedOn datetime DEFAULT (getdate()) NOT NULL,
	UpdatedBy int,
	UpdatedOn datetime NOT NULL,
	RejectedComment nvarchar(1024),
	RejectedBy int,
	RejectedDate datetime DEFAULT (getdate()),
	CONSTRAINT PK_DataAnonActivityLog_NEW PRIMARY KEY (ID),
	CONSTRAINT FK_DataAnonActivityLog_Account_AccountID_NEW FOREIGN KEY (AccountID) REFERENCES Comp.Account(ID),
        CONSTRAINT FK_DataAnonActivityLog_Contact_ContactID_NEW FOREIGN KEY (ContactID) REFERENCES Comp.Contact(ID)
) 

/*******************  Query to Insert data in DataAnonActivityLog table ****************/  

INSERT into Comp.DataAnonActivityLog
(AccountID, ContactID, InitialApprovalComment, InitialApprovalBy,InitialApprovalDate, FinalApprovalComment,
FinalApprovalBy,FinalApprovalDate, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn,RejectedComment, RejectedBy,RejectedDate)
select 
(AccountID, ContactID, InitialApprovalComment, InitialApprovalBy,InitialApprovalDate, FinalApprovalComment,
FinalApprovalBy,FinalApprovalDate, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn,RejectedComment, RejectedBy,RejectedDate)
from Comp.DataAnonActivityLog_old

