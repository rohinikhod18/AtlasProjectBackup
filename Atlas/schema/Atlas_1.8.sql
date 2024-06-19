/********************
 * 
 * SCRIPT FOR ADDING NEW WATCHLIST_CATEGORY , FUNCTIONS AND ROLES. 
 * 
 * Jira: AT-1127  
 * 
 ***********************/
ALTER TABLE Comp.WatchList ADD Category tinyint ;

update Comp.watchlist set category=2 where reason='PEP';

update Comp.watchlist set category=1 where reason='High risk industry';

update Comp.watchlist set category=2 where reason='CD Inc Client';

update Comp.watchlist set category=2 where reason='SDD Completed on Account';

update Comp.watchlist set category=1 where reason='Client has been a victim of fraud';

update Comp.watchlist set category=2 where reason='CFX Medium Risk';

update Comp.watchlist set category=1 where reason='Cash based industry';

update Comp.watchlist set category=1 where reason='Exception to acceptance policy';

update Comp.watchlist set category=1 where reason='TF risks identified';

update Comp.watchlist set category=1 where reason='SAR submission on account';

update Comp.watchlist set category=1 where reason='Production Order on Account';

update Comp.watchlist set category=2 where reason='Pending KYC on Account';

update Comp.watchlist set category=2 where reason='US Client List B client';

update Comp.watchlist set category=1 where reason='E Tailer Client Monitoring of Trades';

update Comp.watchlist set category=1 where reason='Sanction Pending on Client';

update Comp.watchlist set category=1 where reason='Fraugster high risk of fraud';

update Comp.watchlist set category=2 where reason='Travel Company invoice required for all trades';

update Comp.watchlist set category=1 where reason='CFX - High Risk';

update Comp.watchlist set category=1 where reason='Victim of fraud /Vulnerable Monitoring of trades required';

update Comp.watchlist set category=1 where reason='High Risk Country';

update Comp.watchlist set category=2 where reason='E-Tailer Client - Documentation Required';

update Comp.watchlist set category=2 where reason='E-Tailer Client - VAT Required';

update Comp.watchlist set category=2 where reason='Inter-company transfers only';

update Comp.watchlist set category=1 where reason='Monitor Trading Activity / Beneficiary';

update Comp.watchlist set category=2 where reason='Account Info Updated';


DELETE FROM Comp.FunctionGroupMapping  WHERE Functionid in(select id from comp.[function] where name='canManageWatchlist');

DELETE FROM Comp.[function]  WHERE name='canManageWatchlist';

INSERT INTO Comp.[function] (Name , CreatedBy ,UpdatedBy, UpdatedOn ) VALUES ('canManageWatchListCategory1' ,1 , 1, getdate());

INSERT INTO Comp.[function] (Name , CreatedBy ,UpdatedBy, UpdatedOn) VALUES ('canManageWatchListCategory2' ,1 , 1, getdate());


insert into comp.[functiongroupmapping] (functionid,functiongroupid,createdby,updatedby,updatedon) values(
(select id from comp.[function] where name='canManageWatchListCategory1'),5,1,1,getdate());

insert into comp.[functiongroupmapping] (functionid,functiongroupid,createdby,updatedby,updatedon) values(
(select id from comp.[function] where name='canManageWatchListCategory2'),5,1,1,getdate());


insert into comp.[functiongroupmapping] (functionid,functiongroupid,createdby,updatedby,updatedon) values(
(select id from comp.[function] where name='canManageWatchListCategory1'),4,1,1,getdate());

insert into comp.[functiongroupmapping] (functionid,functiongroupid,createdby,updatedby,updatedon) values(
(select id from comp.[function] where name='canManageWatchListCategory2'),4,1,1,getdate());


insert into comp.[functiongroupmapping] (functionid,functiongroupid,createdby,updatedby,updatedon) values(
(select id from comp.[function] where name='canManageWatchListCategory1'),3,1,1,getdate());

insert into comp.[functiongroupmapping] (functionid,functiongroupid,createdby,updatedby,updatedon) values(
(select id from comp.[function] where name='canManageWatchListCategory2'),3,1,1,getdate());


insert into comp.[functiongroupmapping] (functionid,functiongroupid,createdby,updatedby,updatedon) values(
(select id from comp.[function] where name='canManageWatchListCategory1'),7,1,1,getdate());

insert into comp.[functiongroupmapping] (functionid,functiongroupid,createdby,updatedby,updatedon) values(
(select id from comp.[function] where name='canManageWatchListCategory2'),7,1,1,getdate());




/********************
 * 
 * SCRIPT FOR ADDING STPFlag. 
 * 
 * Jira: AT-1214  
 * 
 ***********************/

alter table Comp.Account add STPFlag bit DEFAULT ((0));

alter table Comp.Contact add STPFlag bit DEFAULT ((0));

alter table Comp.PaymentIn add STPFlag bit DEFAULT ((0));

alter table Comp.PaymentOut add STPFlag bit DEFAULT ((0));


/********************
 * 
 * SCRIPT FOR stream lining Provider properties 
 *   
 ***********************/

UPDATE  Comp.Compliance_ServiceProviderAttribute
SET attribute= REPLACE(attribute, 'endpointUrl', 'endPointUrl' );

UPDATE  Comp.Compliance_ServiceProviderAttribute
SET attribute= REPLACE(attribute, 'enpointUrl', 'endPointUrl' );


/********************
 * 
 * SCRIPT FOR AT-1192 - New reasons for Inactive 
 *   
 ***********************/
INSERT INTO Comp.StatusUpdateReason (Module, Reason, Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('ALL', 'Confirmed Identity Fraud', 1, 1, getdate(), 1, getdate());
INSERT INTO Comp.StatusUpdateReason (Module, Reason, Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('ALL', 'Suspected Identity Fraud', 1, 1, getdate(), 1, getdate());


/********************
 * 
 * SCRIPT FOR COLUMNS SIZE CHANGES
 *   
 ***********************/
ALTER TABLE FraugsterSchedularData ALTER COLUMN AtlasID VARCHAR(50);


/********************
 * 
 * SCRIPT FOR ADD NEW ACTIVITY TYPE
 *   
 ***********************/

insert into [ActivityTypeEnum] (module,[type],active,createdby,UpdatedBy, UpdatedOn) 
values('PROFILE','SIGNUP_STP',1,1, 1, getdate());

insert into [ActivityTypeEnum] (module,[type],active,createdby,UpdatedBy, UpdatedOn) 
values('PAYMENT_IN','PAYMENT_IN_STP',1,1, 1, getdate());

insert into [ActivityTypeEnum] (module,[type],active,createdby,UpdatedBy, UpdatedOn) 
values('PAYMENT_OUT','PAYMENT_OUT_STP',1,1, 1, getdate());

insert into [ActivityTypeEnum] (module,[type],active,createdby,UpdatedBy, UpdatedOn) 
values('PROFILE','UPDATE_SIGNUP_STP',1,1, 1, getdate());
