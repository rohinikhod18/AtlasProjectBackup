package com.currenciesdirect.gtg.compliance.customcheck.dbport;

/**
 * The Class CustomCheckQueryConstant.
 */
public class CustomCheckQueryConstant {

	/*
	 * Insert Query into Main Table
	 */

	public static final String INSERT_INTO_OCCUPATION = "INSERT INTO CustomCheck_Occupation(OrganizationCode,Occupation,Score,Version,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy) VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_SOURCE_OF_LEAD = "INSERT INTO CustomCheck_SourceOfLead(OrganizationCode,SourceOfLead,Score,Version,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy) VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_SOURCE_OF_FUND = "INSERT INTO CustomCheck_SourceOfFund(OrganizationCode,SourceOfFund,Score,Version,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy) VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_VALUE_OF_TRANSACTION = "INSERT INTO CustomCheck_ValueOfTransaction(OrganizationCode,ValueOfTransaction,Score,Version,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy)  VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_PURPOSE_OF_TRANSACTION = "INSERT INTO CustomCheck_PurposeOfTransaction(OrganizationCode,PurposeOfTransaction,Score,Version,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy)  VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_COUNTRIES_OF_TRADE = "INSERT INTO CustomCheck_CountriesOfTrade(OrganizationCode,CountriesOfTrade,Score,Version,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy)  VALUES (?,?,?,?,?,?,?,?);";

	/*
	 * Update Query in Main Table
	 */

	public static final String UPDATE_OCCUPATION = "UPDATE CustomCheck_Occupation SET OrganizationCode=?,Score=?,Version=Version+1 ,CreatedOn=?,CreatedBy=?,UpdatedOn=?,UpdatedBy=? WHERE Occupation=?";

	public static final String UPDATE_SOURCE_OF_LEAD = "UPDATE CustomCheck_SourceOfLead SET OrganizationCode=?,Score=?,Version=Version+1,CreatedOn=?,CreatedBy=?,UpdatedOn=?,UpdatedBy=? WHERE SourceOfLead=?";

	public static final String UPDATE_SOURCE_OF_FUND = "UPDATE CustomCheck_SourceOfFund SET OrganizationCode=?,Score=?,Version=Version+1,CreatedOn=?,CreatedBy=?,UpdatedOn=?,UpdatedBy=? WHERE SourceOfFund=?";

	public static final String UPDATE_VALUE_OF_TRANSACTION = "UPDATE CustomCheck_ValueOfTransaction SET OrganizationCode=?,Score=?,Version=Version+1,CreatedOn=?,CreatedBy=?,UpdatedOn=?,UpdatedBy=? WHERE ValueOfTransaction=?";

	public static final String UPDATE_PURPOSE_OF_TRANSACTION = "UPDATE CustomCheck_PurposeOfTransaction SET OrganizationCode=?,Score=?,Version=Version+1,CreatedOn=?,CreatedBy=?,UpdatedOn=?,UpdatedBy=? WHERE PurposeOfTransaction=?";

	public static final String UPDATE_COUNTRIES_OF_TRADE = "UPDATE CustomCheck_CountriesOfTrade SET OrganizationCode=?,Score=?,Version=Version+1,CreatedOn=?,CreatedBy=?,UpdatedOn=?,UpdatedBy=? WHERE CountriesOfTrade=?";

	/*
	 * Delete From Main Table
	 */
	public static final String DELETE_FROM_OCCUPATION = "DELETE FROM CustomCheck_Occupation WHERE Occupation=?";

	public static final String DELETE_FROM_SOURCE_OF_LEAD = "DELETE FROM CustomCheck_SourceOfLead WHERE SourceOfLead=?";

	public static final String DELETE_FROM_SOURCE_OF_FUND = "DELETE FROM CustomCheck_SourceOfFund WHERE SourceOfFund=?";

	public static final String DELETE_FROM_VALUE_OF_TRANSACTION = "DELETE FROM CustomCheck_ValueOfTransaction WHERE ValueOfTransaction=?";

	public static final String DELETE_FROM_PURPOSE_OF_TRANSACTION = "DELETE FROM CustomCheck_PurposeOfTransaction WHERE PurposeOfTransaction=?";

	public static final String DELETE_FROM_COUNTRIES_OF_TRADE = "DELETE FROM CustomCheck_CountriesOfTrade WHERE CountriesOfTrade=?";

	/*
	 * Insert into History Table
	 * 
	 */

	public static final String INSERT_INTO_OCCUPATION_HISTORY = "INSERT INTO CustomCheck_OccupationHistory(OrganizationCode,OccupationHistory,Score,Version,CreatedOn,CreatedBy,IsDeleted,SerialNo) VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_SOURCE_OF_LEAD_HISTORY = "INSERT INTO CustomCheck_SourceOfLeadHistory(OrganizationCode,SourceOfLeadHistory,Score,Version,CreatedOn,CreatedBy,IsDeleted,SerialNo) VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_SOURCE_OF_FUND_HISTORY = "INSERT INTO CustomCheck_SourceOfFundHistory(OrganizationCode,SourceOfFundHistory,Score,Version,CreatedOn,CreatedBy,IsDeleted,SerialNo) VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_VALUE_OF_TRANSACTION_HISTORY = "INSERT INTO CustomCheck_ValueOfTransactionHistory(OrganizationCode,ValueOfTransactionHistory,Score,Version,CreatedOn,CreatedBy,IsDeleted,SerialNo) VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_PURPOSE_OF_TRANSACTION_HISTORY = "INSERT INTO CustomCheck_PurposeOfTransactionHistory(OrganizationCode,PurposeOfTransactionHistory,Score,Version,CreatedOn,CreatedBy,IsDeleted,SerialNo) VALUES (?,?,?,?,?,?,?,?);";

	public static final String INSERT_INTO_COUNTRIES_OF_TRADE_HISTORY = "INSERT INTO CustomCheck_CountriesOfTradeHistory(OrganizationCode,CountriesOfTradeHistory,Score,Version,CreatedOn,CreatedBy,IsDeleted,SerialNo) VALUES (?,?,?,?,?,?,?,?);";

	/*
	 *  Select All Data From Table (Caching)
	 */
	
	public static final String SELECT_FROM_OCUPATION ="SELECT * FROM CustomCheck_Occupation ";
	
	public static final String SELECT_FROM_SOURCE_OF_LEAD ="SELECT * FROM CustomCheck_SourceOfLead";
	
	public static final String SELECT_FROM_SOURCE_OF_FUND ="SELECT * FROM CustomCheck_SourceOfFund";
	
	public static final String SELECT_FROM_VALUE_OF_TRANSACTION ="SELECT * FROM CustomCheck_ValueOfTransaction";
	
	public static final String SELECT_FROM_PURPOSE_OF_TRANSACTION ="SELECT * FROM CustomCheck_PurposeOfTransaction";
	
	public static final String SELECT_FROM_COUNTRIES_OF_TRADE ="SELECT * FROM CustomCheck_CountriesOfTrade";
	
	/*
	 * Select from Main table (Version)
	 */
	
	public static final String SELECT_VERSION_FROM_OCUPATION ="SELECT Version,Score,Id FROM CustomCheck_Occupation  WHERE Occupation =?";
	
	public static final String SELECT_VERSION_FROM_SOURCE_OF_LEAD ="SELECT Version,Score,Id FROM CustomCheck_SourceOfLead  WHERE SourceOfLead =?";
	
	public static final String SELECT_VERSION_FROM_SOURCE_OF_FUND ="SELECT Version,Score,Id FROM CustomCheck_SourceOfFund  WHERE SourceOfFund =?";
	
	public static final String SELECT_VERSION_FROM_VALUE_OF_TRANSACTION ="SELECT Version,Score,Id  FROM CustomCheck_ValueOfTransaction  WHERE ValueOfTransaction =?";
	
	public static final String SELECT_VERSION_FROM_PURPOSE_OF_TRANSACTION ="SELECT Version,Score,Id  FROM CustomCheck_PurposeOfTransaction  WHERE PurposeOfTransaction =?";
	
	public static final String SELECT_VERSION_FROM_COUNTRIES_OF_TRADE ="SELECT Version,Score,Id  FROM CustomCheck_CountriesOfTrade  WHERE CountriesOfTrade =?";
	
}
