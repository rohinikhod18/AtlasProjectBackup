/*******************  Update Contact table columns (AT-2986) ****************/

ALTER TABLE Comp.Contact ADD PayInWatchListStatus tinyint DEFAULT ((0)), PayOutWatchListStatus tinyint DEFAULT ((0));

/****Query To Migrate Data****/

WITH accpaymentstatus AS (
SELECT
c.ID as conID,
a.PayInWatchListStatus as payin,
a.PayOutWatchListStatus as payout
FROM Comp.Account a
JOIN Comp.Contact c on c.AccountID = a.ID
JOIN Comp.ContactWatchList cw on cw.Contact = c.ID
WHERE a.[Type] = 2)

UPDATE
Comp.Contact
set
PayInWatchListStatus = payin,
PayOutWatchListStatus = payout
FROM accpaymentstatus aps
JOIN Comp.Contact con ON con.ID = aps.ConID


/*==================================================================================================================================*/

/********Rollback for: QUERY TO MIGRATE DATA*******************/

WITH accpaymentstatus AS (
SELECT
c.ID as conID,
a.PayInWatchListStatus as payin,
a.PayOutWatchListStatus as payout
FROM Comp.Account a
JOIN Comp.Contact c on c.AccountID = a.ID
JOIN Comp.ContactWatchList cw on cw.Contact = c.ID
WHERE a.[Type] = 2)

UPDATE
Comp.Contact
set
PayInWatchListStatus = NULL,
PayOutWatchListStatus = NULL
FROM accpaymentstatus aps
JOIN Comp.Contact con ON con.ID = aps.ConID

/********Rollback for: to delete columns added in contact table *******************/

ALTER TABLE Comp.Contact DROP PayInWatchListStatus, PayOutWatchListStatus;

