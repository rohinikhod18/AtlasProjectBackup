/* Insert Finscan Inactive Watchlist */

INSERT INTO WatchList
(Reason, StopPaymentIn, StopPaymentOut, Locked, Deleted, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn, Category)
VALUES('Finscan Inactive', ((1)), ((1)), ((0)), ((0)), 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP,1);

/* Query to create Index */

Create index IX_compEventServiceLog_FilteredEntityID
on Comp.eventservicelog
(
EntityID
)
include (EntityType)
Where (ServiceType = 7)

/------------------------------------------------------------------------------------------------------------/

/* Rollback Script */

DELETE FROM WatchList WHERE Reason ='Finscan Inactive';
