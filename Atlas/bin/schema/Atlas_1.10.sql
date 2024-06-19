CREATE TABLE [Comp].[WhiteListBeneficiary](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](150) ,
	[LastName] [nvarchar](150) ,
	[AccountNumber] [nvarchar](150) NOT NULL,
	[Deleted] [bit] NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedOn] [datetime] NOT NULL
 CONSTRAINT [PK_WhiteListBeneficiary] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, 
	ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY];



INSERT INTO Comp.ActivityTypeEnum (Module, "Type", Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('PROFILE', 'FRAUGSTER_REPEAT', 1, 1, getdate(), 1, getdate());

UPDATE Comp.ActivityTypeEnum SET [type]='FRAUGSTER_REPEAT' 
WHERE Module='PAYMENT_OUT' AND [type]='FRAUD_REPEAT' 

UPDATE Comp.ActivityTypeEnum SET [type]='FRAUGSTER_REPEAT' 
WHERE Module='PAYMENT_IN' AND [type]='FRAUD_REPEAT' 

INSERT INTO Comp.EventTypeEnum ("Type", CreatedBy, CreatedOn)
VALUES( 'FUNDSOUT_FRAUGSTER_RESEND', 1, getdate());

INSERT INTO Comp.EventTypeEnum ("Type", CreatedBy, CreatedOn)
VALUES( 'PROFILE_FRAUGSTER_RESEND', 1, getdate());

INSERT INTO Comp.EventTypeEnum ("Type", CreatedBy, CreatedOn)
VALUES( 'FUNDSIN_FRAUGSTER_RESEND', 1, getdate());



