
--AT-1647 Query
UPDATE [Comp].[WatchList]
SET [StopPaymentIn] = 0
WHERE [Reason] = 'Inter-company transfers only';

-------------------------------------------------------------------------

-- Rollback Script
UPDATE [Comp].[WatchList]
SET [StopPaymentIn] = 1
WHERE [Reason] = 'Inter-company transfers only';

-------------------------------------------------------------------------
