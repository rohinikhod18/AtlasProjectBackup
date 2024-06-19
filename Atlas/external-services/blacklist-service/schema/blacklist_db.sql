USE [EnterpriseService]
GO

CREATE TABLE [BlackListType]
(
	[ID] 			        int IDENTITY(1,1) NOT NULL,
	[Type] 				    nvarchar(20) 	  NOT NULL,
	[Relevance] 			smallint  	 	  NOT NULL DEFAULT 0,
	[Created_On]       	    datetime  	  	      NULL,
	[Created_By]       	    nvarchar(25)  	      NULL,
	[Updated_On]       	    datetime     	      NULL,
	[Updated_By]       	    nvarchar(25)	      NULL,
    PRIMARY KEY (ID)
        
)
GO

CREATE TABLE [BlackListData]
(
	[ID] 					int IDENTITY(1,1) NOT NULL,
	[BlackListType]		    int 			  NOT NULL,
	[Value] 			    nvarchar(255) 	  NOT NULL,
	[Created_On]       	    datetime  	  		  NULL,
	[Created_By]       	    nvarchar(25)		  NULL,
	[Updated_On]       	    datetime   			  NULL,
	[Updated_By]       	    nvarchar(25)		  NULL,
    FOREIGN KEY ( BlackListType ) REFERENCES BlackListType(ID)
)
GO