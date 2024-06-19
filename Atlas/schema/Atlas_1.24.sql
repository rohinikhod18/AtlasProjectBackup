-- Queries to implement JIRA Custom report implementation from Atlas sprint 1.24 --

SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE comp.[SavedSearch](
    [ID] [int] IDENTITY(1,1) NOT NULL,
    [PageType] [varchar](20) NOT NULL,
    [SearchName] [varchar](20) NOT NULL,
    [SearchCriteria] [varchar](max) NOT NULL,
    [CreatedBy] [int] NOT NULL,
    [CreatedOn] [datetime] NOT NULL,
    [UpdatedBy] [int] NULL,
    [UpdatedOn] [datetime] NOT NULL,
 CONSTRAINT [PK_SavedSearch] PRIMARY KEY CLUSTERED 
(
    [ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 100, DATA_COMPRESSION = PAGE) ON [PRIMARY]
) ON [PRIMARY];



ALTER TABLE comp.[SavedSearch] ADD  CONSTRAINT [DF_SavedSearch_CreatedOn]  DEFAULT (getdate()) FOR [CreatedOn];

ALTER TABLE comp.[SavedSearch]  WITH NOCHECK ADD  CONSTRAINT [FK_SavedSearch_User_CreatedBy] FOREIGN KEY([CreatedBy]) REFERENCES [User] ([ID]);

ALTER TABLE comp.[SavedSearch] CHECK CONSTRAINT [FK_SavedSearch_User_CreatedBy];

ALTER TABLE comp.[SavedSearch]  WITH NOCHECK ADD  CONSTRAINT [FK_SavedSearch_User_UpdatedBy] FOREIGN KEY([UpdatedBy]) REFERENCES [User] ([ID]);

ALTER TABLE comp.[SavedSearch] CHECK CONSTRAINT [FK_SavedSearch_User_UpdatedBy];

/** Query to give DELETE permission on SavedSearch table since 
 * programmatically its entries needs to be deleted */

GRANT DELETE ON OBJECT::comp.[SavedSearch] TO compliance;

/** Query to give DELETE permission on ReproccessFailed table since it gets used 
for bulk repeat checks */

GRANT DELETE ON OBJECT::comp.[ReproccessFailed] TO compliance;