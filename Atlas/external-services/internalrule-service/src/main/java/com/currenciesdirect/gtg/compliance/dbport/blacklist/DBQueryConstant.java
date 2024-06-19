package com.currenciesdirect.gtg.compliance.dbport.blacklist;

/**
 * The Enum DBQueryConstant.
 *
 * @author Rajesh
 */
public enum DBQueryConstant {

	/** The Constant INSERT_INTO_BLACKLIST_DATA. */
	INSERT_INTO_BLACKLIST_DATA("INSERT INTO BlackListData(BlackListType, Value, Created_On, CreatedBy, Updated_On, UpdatedBy, Deleted, Notes) VALUES((SELECT ID FROM BlackListType where Type=?), ?, ?, ?, ?, ?, ?, ?)"),

	/** The Constant DELETE_BLACKLIST_DATA. */
	DELETE_BLACKLIST_DATA("update BlacklistData set Deleted = 1, Updated_On = ? WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and Value=?"),	
	
	/** The Constant UPDATE_BALCKLIST_DATA. */
	UPDATE_BALCKLIST_DATA("UPDATE BlackListData SET Value=?,Updated_On=?,Updated_By=? WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and value=?"),

	/** The Constant SEARCH_BALCKLIST_DATA. */
	SEARCH_BALCKLIST_DATA("SELECT Value FROM BlackListData WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and Deleted = 0 and  REPLACE(LOWER(Value),' ', '')=?"),

	/** The Constant SEARCH_BALCKLIST_DATA for searching PHONE NUMBER. */
	SEARCH_BALCKLIST_DATA_FOR_PHONE_NUMBER("SELECT Value FROM BlackListData WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and Deleted = 0 and RIGHT(Value,9)=RIGHT(?,9)"),
	
	/** The Constant SEARCH_BALCKLIST_DATA for searching DOMAIN. */
	SEARCH_BALCKLIST_DATA_FOR_DOMAIN("SELECT Value as domains FROM BlackListdata WHERE BlackListType = 4 and Deleted = 0"),
	
	/** The Constant SEARCH_BALCKLIST_DATA_WILD_CARD. */
	SEARCH_BALCKLIST_DATA_WILD_CARD("SELECT created_on, Value, Notes FROM BlackListData WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and Value LIKE ? and Deleted = 0 order by Value"),

	/** The Constant GET_BLACKLIST_TYPE. */
	GET_BLACKLIST_TYPE("SELECT * FROM BlackListType"),
	
	/** The get blacklist data by type. */
	GET_BLACKLIST_DATA_BY_TYPE("select created_on, value, notes from BlackListData where BlacklistType in (select id from BlackListType where Type = ?) and Deleted = 0 order by value"),
	
	IS_BLACKLIST_DATA_PRESENT("select bdata.Id,btype.Type AS BlacklistType, bdata.Value, bdata.Deleted from BlacklistData bdata, BlacklistType btype where bdata.BlacklistType=(SELECT ID FROM BlackListType WHERE Type=?) and bdata.value = ? and btype.id = bdata.BlacklistType"),
	
	UPDATE_DELETE_STATUS("update BlacklistData set Deleted = 0, Updated_on = ? where id = ?"),

	GET_BLACKLISTPAYREF_PROVIDER_INIT_CONFIG_PROPERTY("select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=0 and b.servicetype=(SELECT ID FROM Compliance_ServiceTypeEnum WHERE Code='BLACKLIST_PAY_REF')");
	private String query;

	DBQueryConstant(String query) {
		this.query = query;
	}

	public String getQuery() {
		return this.query;
	}

}

