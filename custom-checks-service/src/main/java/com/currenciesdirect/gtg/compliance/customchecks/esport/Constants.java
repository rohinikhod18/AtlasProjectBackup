package com.currenciesdirect.gtg.compliance.customchecks.esport;

public class Constants {
	
	public static final String  INDEX_FUNDSOUT = System.getProperty("elastic.index.fundsout");
	public static final String  INDEX_FUNDSIN = System.getProperty("elastic.index.fundsin");
	public static final String  INDEX_WHITELIST = System.getProperty("elastic.index.whitelist");
	public static final String  TYPE_CONTRACT = "contract";
	public static final String  TYPE_ACCOUNT = "account";
	
	public static final String  FIELD_WHITELIST_ACC_ID = "accountId";
	public static final String  FIELD_WHITELIST_ORG_CODE = "orgCode";
	
	public static final String  FIELD_ACC_ID = "accId";
	public static final String  FIELD_ORG_CODE = "org_code";
	public static final String  FIELD_DEAL_DATE = "trade.deal_date";
	public static final String  FIELD_IS_DELETED = "isDeleted";
	public static final String  FIELD_CREATED_ON = "createdOn";
	public static final String  FIELD_BENE_ACCOUNT_NUMBER = "beneficiary.account_number";
	public static final String  FIELD_BENE_FNAME = "beneficiary.first_name";
	public static final String  FIELD_BENE_LNAME = "beneficiary.last_name";
	public static final String  FIELD_TRADE_ACC_NUMBER = "trade.trade_account_number.keyword";
	public static final String  AGG_INTRA_DAY = "intraDay";
	public static final String  AGG_OTHER_ACCOUNTS = "otherAccountsWithBene";
	public static final String  AGG_TRADE_ACC_NUMBER = "tradeAccNumber";
	public static final String  FUNDS_OUT_ADD="FUNDS_OUT_ADD";
	public static final String  FUNDS_OUT_REPEAT="FUNDS_OUT_REPEAT";
	public static final String  FIELD_BENEFICIARY_COUNTRY="beneficiary.country";
	public static final String  FIELD_BUYING_CURRENCY="trade.buy_currency";
	public static final String  FIELD_BUYING_AMOUNT="beneficiary.amount";
	public static final String  VALUE_BENEFICIARY_COUNTRY="esp";
	public static final String  VALUE_BUYING_CURRENCY="eur";
	public static final String  VALUE_BUYING_AMOUNT="49000";
	public static final String  VALUE_PAYMENT_REFERENCE="beneficiary.payment_reference";
	public static final String  VALUE_TRADE_TRANSACTION_DATE="beneficiary.trans_ts";
	public static final String  VALUE_BENEFICIARY_TYPE="beneficiary.beneficiary_type";
	public static final String  FIELD_TRADE_ACCOUNT_NUMBER = "trade.trade_account_number";
	public static final String BENEFICIARY_ACCOUNT_NUMBER_KEYWORD = "beneficiary.account_number.keyword";
	public static final String AGG_COUNT_OF_BENE = "countOfBene";
	public static final String  AGG_BENE_ACC_NUMBER = "beneAccNumber";
	public static final String DASH_DETAILS_PAGE = " ----";
	
	private Constants(){
		
	}
}
