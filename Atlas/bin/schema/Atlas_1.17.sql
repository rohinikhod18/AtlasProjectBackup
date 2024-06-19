-------------------Script for stop KYC check------------------------------------

DELETE From KYC_CountryProviderMapping where Country IN 
('Denmark','Netherlands','Switzerland','Japan','Luxembourg','Brazil','Hong Kong','Mexico')

/**
 * Column added to Contact table for Delete Contact API (AT-1604)
 * */

ALTER TABLE Contact ADD Deleted BIT;

/**
 * Delete Contact API (AT-1604) changes - start
 * */

INSERT INTO EventTypeEnum
([Type], CreatedBy, CreatedOn)
VALUES('PROFILE_DELETE_CONTACT', 1, getDate());

INSERT INTO ActivityTypeEnum
(Module, [Type], Active, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
VALUES('PROFILE', 'DELETE_CONTACT', ((1)), 1, (getdate()), 1, getdate());

/**
 * Delete Contact API (AT-1604) changes - END
 * */