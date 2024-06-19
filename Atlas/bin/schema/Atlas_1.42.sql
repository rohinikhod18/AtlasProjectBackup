-- SQL Script for Atlas release 1.42 --

-------------------------AT-3242 DB SQL Script-------------------------------------

--1.Add new column(InitialStatus) in PaymentIn table
ALTER TABLE [Comp].[PaymentIn] ADD [InitialStatus] VARCHAR (255);


--2.Add new column(InitialStatus) in PaymentOut table
ALTER TABLE [Comp].[PaymentOut] ADD [InitialStatus] VARCHAR (255);







-------------------------Rollback Query-----------------------------------------------

--1.PaymentIn table
ALTER TABLE [Comp].[PaymentIn] DROP COLUMN [InitialStatus];


--2.PaymentOut table
ALTER TABLE [Comp].[PaymentOut] DROP COLUMN [InitialStatus];
