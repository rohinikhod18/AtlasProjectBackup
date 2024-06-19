USE [Compliance]
/***************************IP SEED *************************/
GO
INSERT INTO [dbo].[IP_ServiceTypeEnum]([Code], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) 
    VALUES('IP', 'admin_comm', '2016-07-27 03:22:30.0', 'admin_comm', '2016-07-27 03:22:30.0')
GO

INSERT INTO [dbo].[IP_ServiceProvider]([ServiceType], [Code], [Name], [Internal], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1,'QUOVA', ' Neustar IP Geolocation Service', 0, '2016-07-27 03:22:30.0', 'admin_comm', '2016-07-27 03:22:30.0', 'admin_comm')
GO

INSERT INTO [dbo].[IP_ServiceProviderAttribute]([ID], [Attribute], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, '{"endPointUrl":"http://api.quova.com/","apiVersion":"geodirectory/v1/","methodName":"ipinfo/","apiKey":"200.1.RnJvbTo.RnJvbTo.D3AGJURg7QS8WcV6PLFVKWxp3MTtu72IWI495","secret":"Cu553nty"}', '2016-07-27 03:22:30.0','admin_comm', '2016-07-27 03:22:30.0', 'admin_comm')
GO


create view vw_bulk_insert_test
as
select Postcode,Latitude,Longitude,Created_On,Created_By,Updated_On,Updated_By from IP_PostCodeLocation
go
BULK INSERT vw_bulk_insert_test
FROM 'D:\IP_PostCodeLocation.csv'
WITH
(
FIELDTERMINATOR =',',
ROWTERMINATOR = '\n'
)
go
GO
/***************************** BLACKLIST SEED *********************/
/* blacklistType*/
INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('NAME', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('EMAIL', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('IPADDRESS', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('DOMAIN', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('ACC_NUMBER', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListType]([Type], [Relevance], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES('PHONE_NUMBER', 1, '2016-6-19 6:30:38.0', 'admin_comm', '2016-6-19 6:30:38.0','admin_comm')
GO

/* BlackListData*/

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, 'Vivian Dabrosio', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(1, 'Roxann Smail', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(2, 'vivian.da@bnt-soft.com', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(2, 'roxann.smail@bnt-soft.com', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(3, '123.3.4.5', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(3, '123.5.6.3', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(5, '1232456', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(5, '12678435', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(6, '7234567123', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(6, '7378123470', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(4, 'quora', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

INSERT INTO [dbo].[BlackListData]([BlackListType], [Value], [Created_On], [Created_By], [Updated_On], [Updated_By]) 
    VALUES(4, 'buzzfeed', '2016-6-19 6:36:34.0', 'admin_comm', '2016-6-19 6:36:34.0','admin_comm')
GO

*-----------------------------------------------------Country Seed Data-------------------------------------------------------------------------*/

INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (1,'GBR','UK','826','GB','44','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (2,'USA','USA','840','US','1','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (3,'ESP','Spain','724','ES','34','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (4,'ARE','United Arab Emirates','784','AE','971','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (5,'AUS','Australia','036','AU','61','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (6,'BGR','Bulgaria','100','BG','359','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (7,'BHR','Bahrain','048','BH','973','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (8,'BRA','Brazil','076','BR','55','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (9,'BWA','Botswana','072','BW','267','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (10,'CAN','Canada','124','CA','1','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (11,'CHE','Switzerland','756','CH','41','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (12,'IND','India','356','IN','91','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (13,'ZAF','South Africa','710','ZA','27','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (14,'ALA','ÃLand Islands','248','AX','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (15,'AFG','Afghanistan','004','AF','93','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (16,'ALB','Albania','008','AL','355','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (17,'DZA','Algeria','012','DZ','213','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (18,'ASM','American Samoa','016','AS','684','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (19,'AND','Andorra','020','AD','376','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (20,'AGO','Angola','024','AO','244','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (21,'AIA','Anguilla','660','AI','264','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (22,'ATA','Antarctica','010','AQ','672','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (23,'ATG','Antigua And Barbuda','028','AG','268','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (24,'ARG','Argentina','032','AR','54','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (25,'ARM','Armenia','051','AM','374','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (26,'ABW','Aruba','553','AW','297','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (27,'AUT','Austria','040','AT','43','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (28,'AZE','Azerbaijan','031','AZ','994','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (29,'BHS','Bahamas','044','BS','1242','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (30,'BGD','Bangladesh','050','BD','880','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (31,'BRB','Barbados','052','BB','1246','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (32,'BLR','Belarus','112','BY','375','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (33,'BEL','Belgium','056','BE','32','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (34,'BLZ','Belize','084','BZ','501','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (35,'BEN','Benin','204','BJ','229','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (36,'BMU','Bermuda','060','BM','1441','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (37,'BTN','Bhutan','064','BT','975','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (38,'BOL','Bolivia','068','BO','591','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (39,'BES','Bonaire Sint Eustatius And Saba','535','BQ','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (40,'BIH','Bosnia And Herzegowina','070','BA','387','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (41,'BVT','Bouvet Island','074','BV','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (42,'IOT','British Indian Ocean Territory','086','IO','91','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (43,'BRN','Brunei Darussalam','096','BN','673','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (44,'BFA','Burkina Faso','854','BF','226','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (45,'BDI','Burundi','108','BI','257','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (46,'KHM','Cambodia','116','KH','855','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (47,'CMR','Cameroon','120','CM','237','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (48,'CPV','Cape Verde','132','CV','238','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (49,'CYM','Cayman Islands','136','KY','1345','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (50,'CAF','Central African Republic','140','CF','236','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (51,'TCD','Chad','148','TD','235','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (52,'CHL','Chile','152','CL','56','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (53,'CHN','China','156','CN','86','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (54,'CXR','Christmas Island','162','CX','1176','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (55,'CCK','Cocos (Keeling) Islands','166','CC','672','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (56,'COL','Colombia','170','CO','57','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (57,'COM','Comoros','174','KM','269','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (58,'COG','Congo','178','CG','242','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (59,'COD','Congo The Democratic Republic Of The','180','CD','242','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (60,'COK','Cook Islands','184','CK','682','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (61,'CRI','Costa Rica','188','CR','506','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (62,'CIV','Cote D''Ivoire','384','CI','225','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (63,'HRV','Croatia','191','HR','385','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (64,'CUB','Cuba','192','CU','53','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (65,'CUW','Curacao','531','CW','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (66,'CYP','Cyprus','196','CY','357','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (67,'CZE','Czech Republic','203','CZ','420','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (68,'DNK','Denmark','208','DK','45','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (69,'DJI','Djibouti','262','DJ','253','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (70,'DMA','Dominica','212','DM','001-761','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (71,'DOM','Dominican Republic','214','DO','001-761','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (72,'ECU','Ecuador','218','EC','593','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (73,'EGY','Egypt','818','EG','20','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (74,'SLV','El Salvador','222','SV','503','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (75,'GNQ','Equatorial Guinea','226','GQ','240','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (76,'ERI','Eritrea','232','ER','291','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (77,'EST','Estonia','233','EE','372','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (78,'ETH','Ethiopia','231','ET','251','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (79,'FLK','Falkland Islands','238','FK','500','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (80,'FRO','Faroe Islands','234','FO','298','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (81,'FJI','Fiji','242','FJ','679','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (82,'FIN','Finland','246','FI','358','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (83,'FRA','France','250','FR','33','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (84,'GUF','French Guiana','254','GF','594','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (85,'PYF','French Polynesia','258','PF','689','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (86,'ATF','French Southern Territories','260','TF','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (87,'GAB','Gabon','266','GA','241','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (88,'GMB','Gambia','270','GM','220','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (89,'GEO','Georgia','268','GE','995','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (90,'DEU','Germany','276','DE','49','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (91,'GHA','Ghana','288','GH','233','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (92,'GIB','Gibraltar','292','GI','350','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (93,'GRC','Greece','300','GR','30','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (94,'GRL','Greenland','304','GL','299','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (95,'GRD','Grenada','308','GD','001-473','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (96,'GLP','Guadeloupe','312','GP','590','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (97,'GUM','Guam','316','GU','671','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (98,'GTM','Guatemala','320','GT','502','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (99,'GGY','Guernsey','831','GG','0','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (100,'GIN','Guinea','324','GN','224','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (101,'GNB','Guinea-Bissau','624','GW','245','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (102,'GUY','Guyana','328','GY','592','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (103,'HTI','Haiti','332','HT','509','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (104,'HMD','Heard And Mc Donald Islands','334','HM','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (105,'VAT','Holy See (Vatican City State)','336','VA','39','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (106,'HND','Honduras','340','HN','504','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (107,'HKG','Hong Kong','344','HK','852','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (108,'HUN','Hungary','348','HU','36','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (109,'ISL','Iceland','352','IS','354','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (110,'IDN','Indonesia','360','ID','62','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (111,'IRN','Iran (Islamic Republic Of)','364','IR','98','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (112,'IRQ','Iraq','368','IQ','964','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (113,'IRL','Ireland','372','IE','353','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (114,'IMN','Isle of Man','833','IM','0','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (115,'ISR','Israel','376','IL','972','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (116,'ITA','Italy','380','IT','39','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (117,'JAM','Jamaica','388','JM','001-876','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (118,'JPN','Japan','392','JP','81','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (119,'JEY','Jersey','832','JE','0','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (120,'JOR','Jordan','400','JO','962','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (121,'KAZ','Kazakhstan','398','KZ','7','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (122,'KEN','Kenya','404','KE','254','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (123,'KIR','Kiribati','296','KI','686','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (124,'PRK','Korea Democratic People''S Republic Of','408','KP','850','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (125,'KOR','South Korea','410','KR','82','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (126,'KWT','Kuwait','414','KW','965','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (127,'KGZ','Kyrgyzstan','417','KG','996','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (128,'LAO','Lao People''S Democratic Republic','418','LA','856','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (129,'LVA','Latvia','428','LV','371','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (130,'LBN','Lebanon','422','LB','961','S',0,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (131,'LSO','Lesotho','426','LS','266','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (132,'LBR','Liberia','430','LR','231','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (133,'LBY','Libya','434','LY','218','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (134,'LIE','Liechtenstein','438','LI','423','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (135,'LTU','Lithuania','440','LT','370','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (136,'LUX','Luxembourg','442','LU','352','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (137,'MAC','Macau','446','MO','853','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (138,'MKD','Macedonia','807','MK','389','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (139,'MDG','Madagascar','450','MG','261','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (140,'MWI','Malawi','454','MW','265','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (141,'MYS','Malaysia','458','MY','60','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (142,'MDV','Maldives','462','MV','960','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (143,'MLI','Mali','466','ML','223','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (144,'MLT','Malta','470','MT','356','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (145,'MHL','Marshall Islands','485','MH','692','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (146,'MTQ','Martinique','474','MQ','596','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (147,'MRT','Mauritania','478','MR','222','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (148,'MUS','Mauritius','480','MU','230','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (149,'MYT','Mayotte','175','YT','269','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (150,'MEX','Mexico','484','MX','52','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (151,'FSM','Micronesia','583','FM','691','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (152,'MDA','Moldova','498','MD','373','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (153,'MCO','Monaco','492','MC','377','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (154,'MNG','Mongolia','496','MN','976','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (155,'MNE','Montenegro','499','ME','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (156,'MSR','Montserrat','500','MS','1664','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (157,'MAR','Morocco','504','MA','212','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (158,'MOZ','Mozambique','508','MZ','258','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (159,'MMR','Myanmar','104','MM','95','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (160,'NAM','Namibia','516','NA','264','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (161,'NRU','Nauru','520','NR','674','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (162,'NPL','Nepal','524','NP','977','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (163,'ANT','Netherland Antilles','528','AN','31','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (164,'NCL','New Caledonia','540','NC','687','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (165,'NZL','New Zealand','554','NZ','64','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (166,'NIC','Nicaragua','558','NI','505','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (167,'NER','Niger','562','NE','227','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (168,'NGA','Nigeria','566','NG','234','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (169,'NIU','Niue','570','NU','683','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (170,'NFK','Norfolk Islands','574','NF','00672-3','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (171,'MNP','Northern Mariana Islands','580','MP','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (172,'NOR','Norway','578','NO','47','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (173,'OMN','Oman','512','OM','968','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (174,'PAK','Pakistan','586','PK','92','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (175,'PLW','Palau','585','PW','680','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (176,'PSE','Palestinian Territory','275','PS','970','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (177,'PAN','Panama','591','PA','507','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (178,'PNG','Papua New Guinea','598','PG','675','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (179,'PRY','Paraguay','600','PY','595','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (180,'PER','Peru','604','PE','51','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (181,'PHL','Philippines','608','PH','63','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (182,'PCN','Pitcairn','612','PN','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (183,'POL','Poland','616','PL','48','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (184,'PRT','Portugal','620','PT','351','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (185,'PRI','Puerto Rica','630','PR','1787','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (186,'QAT','Qatar','634','QA','974','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (187,'REU','Reunion','638','RE','262','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (188,'ROU','Romania','642','RO','40','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (189,'RUS','Russian Federation','643','RU','7','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (190,'RWA','Rwanda','646','RW','250','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (191,'SHN','Saint Helena Ascension And Tristan Da Cunha','654','SH','290','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (192,'BLM','Saint BarthãLemy','652','BL','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (193,'KNA','St Kitts and Nevis','659','KN','1869','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (194,'LCA','St Lucia','662','LC','1758','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (195,'SPM','Saint Pierre And Miquelon','666','PM','508','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (196,'VCT','Saint Vincent and the Grenadines','670','VC','1809','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (197,'WSM','Samoa','882','WS','685','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (198,'SMR','San Marino','674','SM','378','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (199,'STP','Sao Tome And Principe','678','ST','239','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (200,'SAU','Saudi Arabia','682','SA','966','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (201,'SEN','Senegal','686','SN','221','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (202,'SRB','Serbia','688','RS','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (203,'SYC','Seychelles','690','SC','248','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (204,'SLE','Sierra Leone','694','SL','232','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (205,'SGP','Singapore','702','SG','65','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (206,'SXM','St. Maarten','534','SX','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (207,'SVK','Slovakia','703','SK','421','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (208,'SVN','Slovenia','705','SI','386','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (209,'SLB','Solomon Islands','90 ','SB','677','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (210,'SOM','Somalia','706','SO','252','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (211,'SGS','South Georgia And The South Sandwich Islands','239','GS','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (212,'SSD','South Sudan','728','SS','0','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (213,'LKA','Sri Lanka','144','LK','94','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (214,'SDN','Sudan','729','SD','249','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (215,'SUR','Suriname','740','SR','597','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (216,'SJM','Svalbard And Jan Mayen Islands','744','SJ','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (217,'SWZ','Swaziland','748','SZ','268','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (218,'SWE','Sweden','752','SE','46','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (219,'SYR','Syrian Arab Republic','760','SY','963','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (220,'TWN','Taiwan','158','TW','886','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (221,'TJK','Tajikistan','762','TJ','992','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (222,'TZA','Tanzania United Republic Of','834','TZ','255','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (223,'THA','Thailand','764','TH','66','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (224,'TLS','Timor-Leste','626','TL','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (225,'TGO','Togo','768','TG','228','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (226,'TKL','Tokelau','772','TK','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (227,'TON','Tongo','776','TO','676','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (228,'TTO','Trinidad And Tobago','780','TT','1868','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (229,'TUN','Tunisia','788','TN','216','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (230,'TUR','Turkey','792','TR','90','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (231,'TKM','Turkmenistan','795','TM','993','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (232,'TCA','Turks and Caicos','796','TC','1649','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (233,'TUV','Tuvalu','798','TV','688','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (234,'UGA','Uganda','800','UG','256','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (235,'UKR','Ukraine','804','UA','380','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (236,'UMI','United States Minor Outlying Islands','581','UM','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (237,'URY','Uruguay','858','UY','598','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (238,'UZB','Uzbekistan','860','UZ','998','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (239,'VUT','Vanuatu','548','VU','678','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (240,'VEN','Venezuela Bolivarian Republic Of','862','VE','58','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (241,'VNM','Vietnam','704','VN','84','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (242,'VGB','British Virgin Islands','092','VG','001-284','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (243,'VIR','US Virgin Islands','850','VI','001-340','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (244,'WLF','Wallis And Futuna Islands','876','WF','681','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (245,'ESH','Western Sahara','732','EH','0','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (246,'YEM','Yemen','887','YE','967','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (247,'ZMB','Zambia','894','ZM','260','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (248,'ZWE','Zimbabwe','716','ZW','263','S',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (249,'NLD','Netherlands','528','NL','31','L',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (250,'GBA','Alderney','826','  ','441481','H',1,1,'2014-04-23 09:47:14:283'); GO
INSERT INTO Country(ID,Code,DisplayName,ISOCode,ShortCode,ISDCode,RiskLevel,Active,CreatedBy,CreatedOn) VALUES (251,'GEA','AbKhazia','643','AB','840','H',1,1,'2014-04-23 09:47:14:283'); GO

/*-----------------------------------------------------Country State Seed Data----------------------------------- --*/

INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Alabama','AL',1,1,1,'2014-07-18 18:56:43:920',9); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Alaska','AK',1,0,1,'2014-07-18 18:56:43:920',10); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'American Samoa','AS',1,1,1,'2014-07-18 18:56:43:920',11); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Arizona','AZ',1,0,1,'2014-07-18 18:56:43:920',12); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Arkansas','AR',1,0,1,'2014-07-18 18:56:43:920',13); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'California','CA',1,1,1,'2014-07-18 18:56:43:920',14); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Colorado','CO',1,0,1,'2014-07-18 18:56:43:920',15); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Connecticut','CT',1,1,1,'2014-07-18 18:56:43:920',16); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Delaware','DE',1,1,1,'2014-07-18 18:56:43:920',17); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'District of Columbia','DC',1,1,1,'2014-07-18 18:56:43:920',18); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Florida','FL',1,2,1,'2014-07-18 18:56:43:920',19); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Georgia','GA',1,2,1,'2014-07-18 18:56:43:920',20); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Guam','GU',1,0,1,'2014-07-18 18:56:43:920',21); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Hawaii','HI',1,1,1,'2014-07-18 18:56:43:920',22); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Idaho','ID',1,1,1,'2014-07-18 18:56:43:920',23); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Illinois','IL',1,0,1,'2014-07-18 18:56:43:920',24); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Indiana','IN',1,1,1,'2014-07-18 18:56:43:920',25); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Iowa','IA',1,0,1,'2014-07-18 18:56:43:920',26); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Kansas','KS',1,1,1,'2014-07-18 18:56:43:920',27); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Kentucky','KY',1,1,1,'2014-07-18 18:56:43:920',28); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Louisiana','LA',1,1,1,'2014-07-18 18:56:43:920',29); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Maine','ME',1,0,1,'2014-07-18 18:56:43:920',30); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Maryland','MD',1,1,1,'2014-07-18 18:56:43:920',31); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Massachusetts','MA',1,1,1,'2014-07-18 18:56:43:920',32); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Michigan','MI',1,1,1,'2014-07-18 18:56:43:920',33); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Minnesota','MN',1,1,1,'2014-07-18 18:56:43:920',34); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Mississippi','MS',1,1,1,'2014-07-18 18:56:43:920',35); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Missouri','MO',1,1,1,'2014-07-18 18:56:43:920',36); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Montana','MT',1,2,1,'2014-07-18 18:56:43:920',37); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Nebraska','NE',1,1,1,'2014-07-18 18:56:43:920',38); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Nevada','NV',1,1,1,'2014-07-18 18:56:43:920',39); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'New Hampshire','NH',1,1,1,'2014-07-18 18:56:43:920',40); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'New Jersey','NJ',1,1,1,'2014-07-18 18:56:43:920',41); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'New Mexico','NM',1,2,1,'2014-07-18 18:56:43:920',42); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'New York','NY',1,1,1,'2014-07-18 18:56:43:920',43); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'North Carolina','NC',1,2,1,'2014-07-18 18:56:43:920',44); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'North Dakota','ND',1,1,1,'2014-07-18 18:56:43:920',45); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Northern Mariana Islands','MP',1,0,1,'2014-07-18 18:56:43:920',46); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Ohio','OH',1,0,1,'2014-07-18 18:56:43:920',47); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Oklahoma','OK',1,0,1,'2014-07-18 18:56:43:920',48); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Oregon','OR',1,1,1,'2014-07-18 18:56:43:920',49); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Pennsylvania','PA',1,0,1,'2014-07-18 18:56:43:920',50); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Puerto Rico','PR',1,1,1,'2014-07-18 18:56:43:920',51); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Rhode Island','RI',1,0,1,'2014-07-18 18:56:43:920',52); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'South Carolina','SC',1,2,1,'2014-07-18 18:56:43:920',53); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'South Dakota','SD',1,1,1,'2014-07-18 18:56:43:920',54); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Tennessee','TN',1,1,1,'2014-07-18 18:56:43:920',55); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Texas','TX',1,0,1,'2014-07-18 18:56:43:920',56); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'U.S. Virgin Islands','VI',1,0,1,'2014-07-18 18:56:43:920',57); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Utah','UT',1,1,1,'2014-07-18 18:56:43:920',58); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Vermont','VT',1,0,1,'2014-07-18 18:56:43:920',59); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Virginia','VA',1,1,1,'2014-07-18 18:56:43:920',60); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Washington','WA',1,0,1,'2014-07-18 18:56:43:920',61); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'West Virginia','WV',1,0,1,'2014-07-18 18:56:43:920',62); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Wisconsin','WI',1,2,1,'2014-07-18 18:56:43:920',63); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (2,'Wyoming','WY',1,1,1,'2014-07-18 18:56:43:920',64); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (5,'Australian Capital Territory','ACT',1,2,1,'2015-02-13 10:40:40:430',1); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (5,'New South Wales','NSW',1,2,1,'2015-02-13 10:40:40:430',2); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (5,'Northern Territory','NT',1,2,1,'2015-02-13 10:40:40:430',3); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (5,'Queensland','QLD',1,2,1,'2015-02-13 10:40:40:430',4); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (5,'South Australia','SA',1,2,1,'2015-02-13 10:40:40:430',5); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (5,'Tasmania','TAS',1,2,1,'2015-02-13 10:40:40:430',6); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (5,'Victoria','VIC',1,2,1,'2015-02-13 10:40:40:430',7); GO
INSERT INTO Countrystate(Country,DisplayName,Code,Active,CDLicenseStatus,CreatedBy,CreatedOn,TradeStateID) VALUES (5,'Western Australia','WA',1,2,1,'2015-02-13 10:40:40:430',8); GO

