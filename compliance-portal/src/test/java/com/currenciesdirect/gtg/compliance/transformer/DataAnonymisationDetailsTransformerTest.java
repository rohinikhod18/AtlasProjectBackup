package com.currenciesdirect.gtg.compliance.transformer;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.core.domain.AccountWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.CustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.GlobalCheck;
import com.currenciesdirect.gtg.compliance.core.domain.InternalRule;
import com.currenciesdirect.gtg.compliance.core.domain.IpCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Kyc;
import com.currenciesdirect.gtg.compliance.core.domain.Onfido;
import com.currenciesdirect.gtg.compliance.core.domain.PaginationData;
import com.currenciesdirect.gtg.compliance.core.domain.PaginationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDetailsDto;

public class DataAnonymisationDetailsTransformerTest {

	@InjectMocks
	DataAnonymisationDetailsTransformer dataAnonymisationDetailsTransformer;
	

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

	}

	public DataAnonymisationDetailsDBDto getDataAnonymisationDetailsDBDto()
	{
		List<String> kycSupportedCountryList= new ArrayList<>();
		String country="";
		String country1="";
		kycSupportedCountryList.add(country);
		kycSupportedCountryList.add(country1);
		List<UploadDocumentTypeDBDto> documentList= new ArrayList<>();
		UploadDocumentTypeDBDto uploadDocumentTypeDBDto= new UploadDocumentTypeDBDto();
		uploadDocumentTypeDBDto.setId(1);
		uploadDocumentTypeDBDto.setDocumentName("Drivers License ");
		uploadDocumentTypeDBDto.setCategory("PIA");
		documentList.add(uploadDocumentTypeDBDto);
		List<StatusReasonData> statusReasonDataList= new ArrayList<>();
		StatusReasonData statusReasonData= new StatusReasonData();
		statusReasonData.setName("");
		statusReasonData.setValue(true);
		statusReasonDataList.add(statusReasonData);
		StatusReason statusReason= new StatusReason();
		statusReason.setStatusReasonData(statusReasonDataList);
		WatchListData watchlistData = new WatchListData();
		watchlistData.setId(1);
		watchlistData.setName("abc");
		List<WatchListData> watchlistDatas = new ArrayList<>();
		watchlistDatas.add(watchlistData);
		Watchlist watchlist = new Watchlist();
		watchlist.setWatchlistData(watchlistDatas);
		List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
		ActivityLogDataWrapper data= new ActivityLogDataWrapper();
		data.setActivityType("PROFILE ADD_COMMENT");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/11/2020 13:05:06");
		activityLogData.add(data);
		ActivityLogs  activityLogs= new ActivityLogs();
		activityLogs.setActivityLogData(activityLogData);
		PaginationDetails paginationDetails= new PaginationDetails();
		 PaginationData prevRecord= new PaginationData();
		 prevRecord.setAccountid("0");
		 prevRecord.setCustType("PFX");
		 prevRecord.setId("0");
		 PaginationData nextRecord= new PaginationData();;
		 nextRecord.setAccountid("1");
		 nextRecord.setCustType("PFX");
		 nextRecord.setId("1");
		 paginationDetails.setPrevRecord(prevRecord);
		 paginationDetails.setNextRecord(nextRecord);
		String summary="{\"status\":\"PASS\",\"ip\":\"NOT_REQUIRED\",\"email\":\"NOT_REQUIRED\",\"accountNumber\":\"NOT_REQUIRED\",\"name\":\"false\",\"domain\":\"false\",\"webSite\":\"false\"}";
		String summary1="{\"status\":\"NOT_PERFORMED\",\"eidCheck\":false,\"verifiactionResult\":\"Not Available\",\"referenceId\":\"001-C-0000000004\",\"dob\":\"1918-11-18\",\"checkedOn\":\"2017-04-04 21:18:20.197\"}";
		String summary2="{\"status\":\"PENDING\",\"statusDescription\":\"Lookup PENDING: \\nINFO: 19 PENDING records found\",\"ofacList\":\"No match found\",\"resultsCount\":19,\"pendingCount\":19,\"sanctionId\":\"001-C-0000000004\",\"contactId\":4,\"worldCheck\":\"Match found\",\"providerResponse\":\"{\\\"status\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"message\\\":\\\"Lookup PENDING: \\\\nINFO: 19 PENDING records found\\\",\\\"returned\\\":0,\\\"notReturned\\\":0,\\\"resultsCount\\\":0,\\\"hitCount\\\":0,\\\"pendingCount\\\":0,\\\"safeCount\\\":0,\\\"version\\\":\\\"v4.1.1.0 - Released February 7, 2017\\\",\\\"responses\\\":[{\\\"status\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"message\\\":\\\"Lookup PENDING: \\\\nINFO: 19 PENDING records found\\\",\\\"returned\\\":19,\\\"notReturned\\\":0,\\\"resultsCount\\\":19,\\\"hitCount\\\":0,\\\"pendingCount\\\":19,\\\"safeCount\\\":0,\\\"complianceRecords\\\":[{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20110513\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"1003486\\\",\\\"listProfileKey\\\":1676369,\\\"linkSingleStringName\\\":\\\"Jason BOND\\\",\\\"listParentSingleStringName\\\":\\\"JASON BOND\\\",\\\"listCategory\\\":\\\"CRIME - OTHER\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1987-02-20\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"CEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"033\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[\\\"FBI\\\",\\\"USDOJ\\\"],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20120403\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"1043250\\\",\\\"listProfileKey\\\":1715400,\\\"linkSingleStringName\\\":\\\"James Henry III BOND\\\",\\\"listParentSingleStringName\\\":\\\"JAMES HENRY III BOND\\\",\\\"listCategory\\\":\\\"INDIVIDUAL\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"EEEEBBEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"024\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[\\\"FINRA\\\"],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20140608\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"1132914\\\",\\\"listProfileKey\\\":1805917,\\\"linkSingleStringName\\\":\\\"James Bond\\\",\\\"listParentSingleStringName\\\":\\\"JAMES RODRIGO ALVIM\\\",\\\"listCategory\\\":\\\"CRIME - NARCOTICS\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1977-08-31\\\",\\\"listCountries\\\":\\\"BRAZIL\\\",\\\"rankString\\\":\\\"EEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"028\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"No\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20150611\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"1666497\\\",\\\"listProfileKey\\\":2351987,\\\"linkSingleStringName\\\":\\\"James BOND\\\",\\\"listParentSingleStringName\\\":\\\"JAMES BOND\\\",\\\"listCategory\\\":\\\"POLITICAL INDIVIDUAL\\\",\\\"listPEPCategory\\\":\\\"PEP N\\\",\\\"listDOBs\\\":\\\"\\\",\\\"listCountries\\\":\\\"GUYANA\\\",\\\"rankString\\\":\\\"EEEEBBEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"024\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_PEP_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20140708\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"1707515\\\",\\\"listProfileKey\\\":2395047,\\\"linkSingleStringName\\\":\\\"Stephen James BOND\\\",\\\"listParentSingleStringName\\\":\\\"STEPHEN JAMES BOND\\\",\\\"listCategory\\\":\\\"CRIME - OTHER\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1984-03-12\\\",\\\"listCountries\\\":\\\"UNITED KINGDOM\\\",\\\"rankString\\\":\\\"EEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"028\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20130404\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"1742866\\\",\\\"listProfileKey\\\":2432145,\\\"linkSingleStringName\\\":\\\"Jason F BOND\\\",\\\"listParentSingleStringName\\\":\\\"JASON F BOND\\\",\\\"listCategory\\\":\\\"INDIVIDUAL\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"CEEEBBEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"029\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20120904\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"1834533\\\",\\\"listProfileKey\\\":2530434,\\\"linkSingleStringName\\\":\\\"James Edward BOND\\\",\\\"listParentSingleStringName\\\":\\\"JAMES EDWARD BOND\\\",\\\"listCategory\\\":\\\"LEGAL\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"\\\",\\\"listCountries\\\":\\\"CANADA\\\",\\\"rankString\\\":\\\"EEEEBBEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"024\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[\\\"CADL\\\"],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20131112\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"2163636\\\",\\\"listProfileKey\\\":2883862,\\\"linkSingleStringName\\\":\\\"Nicholas James BOND\\\",\\\"listParentSingleStringName\\\":\\\"NICHOLAS BOND\\\",\\\"listCategory\\\":\\\"POLITICAL INDIVIDUAL\\\",\\\"listPEPCategory\\\":\\\"PEP SN\\\",\\\"listDOBs\\\":\\\"\\\",\\\"listCountries\\\":\\\"UNITED KINGDOM\\\",\\\"rankString\\\":\\\"EEEEBBEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"024\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"No\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_PEP_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20141209\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"2237222\\\",\\\"listProfileKey\\\":2961974,\\\"linkSingleStringName\\\":\\\"Joshua Anthony BOND\\\",\\\"listParentSingleStringName\\\":\\\"JOSHUA ANTHONY BOND\\\",\\\"listCategory\\\":\\\"CRIME - NARCOTICS\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1989-03-06\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"CEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"033\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[\\\"FBI\\\",\\\"USDOJ\\\"],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20140925\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"2415040\\\",\\\"listProfileKey\\\":3152449,\\\"linkSingleStringName\\\":\\\"Thomas James BONE\\\",\\\"listParentSingleStringName\\\":\\\"THOMAS BONE\\\",\\\"listCategory\\\":\\\"INDIVIDUAL\\\",\\\"listPEPCategory\\\":\\\"PEP N-R\\\",\\\"listDOBs\\\":\\\"2001-01-01\\\",\\\"listCountries\\\":\\\"UNITED KINGDOM;Kettering, Northamptonshire, United Kingdom\\\",\\\"rankString\\\":\\\"ECEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"033\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"No\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_PEP_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20140911\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"2417454\\\",\\\"listProfileKey\\\":3155080,\\\"linkSingleStringName\\\":\\\"Christopher James BOND\\\",\\\"listParentSingleStringName\\\":\\\"CHRISTOPHER JAMES BOND\\\",\\\"listCategory\\\":\\\"CRIME - FINANCIAL\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1981-12-16\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"EEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"028\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[\\\"HHS\\\"],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20141023\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"2458733\\\",\\\"listProfileKey\\\":3200255,\\\"linkSingleStringName\\\":\\\"James Patrick BONA\\\",\\\"listParentSingleStringName\\\":\\\"JAMES PATRICK BONA\\\",\\\"listCategory\\\":\\\"CRIME - FINANCIAL\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"ECEEBBEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"029\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20141114\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"2482214\\\",\\\"listProfileKey\\\":3225713,\\\"linkSingleStringName\\\":\\\"James Jeffrey BOND\\\",\\\"listParentSingleStringName\\\":\\\"JAMES JEFFREY BOND\\\",\\\"listCategory\\\":\\\"CRIME - NARCOTICS\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1984-11-13\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"EEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"028\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20150917\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"2663390\\\",\\\"listProfileKey\\\":3423156,\\\"linkSingleStringName\\\":\\\"Jason BOND\\\",\\\"listParentSingleStringName\\\":\\\"JASON BOND\\\",\\\"listCategory\\\":\\\"CRIME - NARCOTICS\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1982-09-16\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"CEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"033\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[\\\"USDOJ\\\"],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20120504\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"551873\\\",\\\"listProfileKey\\\":3934334,\\\"linkSingleStringName\\\":\\\"James Bond\\\",\\\"listParentSingleStringName\\\":\\\"FRANCISCO EFRAIN MIRANDA PENUELAS\\\",\\\"listCategory\\\":\\\"INDIVIDUAL\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1972-01-22\\\",\\\"listCountries\\\":\\\"MEXICO\\\",\\\"rankString\\\":\\\"EEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"028\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"No\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20141119\\\",\\\"listModifyDate\\\":\\\"19/08/2016 15:31:23\\\",\\\"listProfileId\\\":\\\"669190\\\",\\\"listProfileKey\\\":4049060,\\\"linkSingleStringName\\\":\\\"James Bond 008\\\",\\\"listParentSingleStringName\\\":\\\"AL HADJ ABDULLAH MOH SABBER MACAPAAR\\\",\\\"listCategory\\\":\\\"TERRORISM\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1962-00-00\\\",\\\"listCountries\\\":\\\"PHILIPPINES\\\",\\\"rankString\\\":\\\"EEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"028\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"No\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20170214\\\",\\\"listModifyDate\\\":\\\"15/02/2017 19:14:46\\\",\\\"listProfileId\\\":\\\"782366\\\",\\\"listProfileKey\\\":4161758,\\\"linkSingleStringName\\\":\\\"James Austin BOONE\\\",\\\"listParentSingleStringName\\\":\\\"JAMES AUSTIN BOONE\\\",\\\"listCategory\\\":\\\"INDIVIDUAL\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1990-01-14\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"ECEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"033\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20161007\\\",\\\"listModifyDate\\\":\\\"10/10/2016 08:12:29\\\",\\\"listProfileId\\\":\\\"3179654\\\",\\\"listProfileKey\\\":4873984,\\\"linkSingleStringName\\\":\\\"David Joseph BOND\\\",\\\"listParentSingleStringName\\\":\\\"DAVID BOND\\\",\\\"listCategory\\\":\\\"INDIVIDUAL\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1985-11-01\\\",\\\"listCountries\\\":\\\"USA\\\",\\\"rankString\\\":\\\"CEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"033\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"No\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[\\\"USDOJ\\\"],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"},{\\\"pairStatus\\\":\\\"New\\\",\\\"pairReason\\\":\\\"New\\\",\\\"pairComments\\\":\\\"Name Line: James Bond\\\\nID: 001-C-0000000004\\\\nReason: Name Search Match\\\",\\\"applicationDisplayName\\\":\\\"Clients\\\",\\\"applicationId\\\":\\\"CLNTS\\\",\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"clientFullName\\\":\\\"James Bond\\\",\\\"listKey\\\":\\\"2000\\\",\\\"listName\\\":\\\"World-Check\\\",\\\"listId\\\":\\\"WC\\\",\\\"listVersion\\\":\\\"20170316\\\",\\\"listModifyDate\\\":\\\"17/03/2017 19:06:07\\\",\\\"listProfileId\\\":\\\"3297093\\\",\\\"listProfileKey\\\":5147100,\\\"linkSingleStringName\\\":\\\"James Bond 00711\\\",\\\"listParentSingleStringName\\\":\\\"JAYDE SHELDON\\\",\\\"listCategory\\\":\\\"CRIME - OTHER\\\",\\\"listPEPCategory\\\":\\\"\\\",\\\"listDOBs\\\":\\\"1984-01-17\\\",\\\"listCountries\\\":\\\"SOUTH AFRICA\\\",\\\"rankString\\\":\\\"EEEEBNEBEE\\\",\\\"ranktype\\\":\\\"A\\\",\\\"rankweight\\\":\\\"028\\\",\\\"pairLoadDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"origin\\\":\\\"SEARCH\\\",\\\"secondsviewed\\\":\\\"0\\\",\\\"initialUser\\\":\\\"\\\",\\\"isPairParentFlag\\\":{\\\"value\\\":\\\"No\\\"},\\\"pairMetSearchCriteriaFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"editableDueToAssignmentFlag\\\":{\\\"value\\\":\\\"Yes\\\"},\\\"modifyDate\\\":\\\"04/04/2017 11:48:23\\\",\\\"modifiedByUser\\\":\\\"webservices\\\",\\\"pairReportType\\\":{\\\"value\\\":\\\"None\\\"},\\\"finscanCategory\\\":[\\\"WC_LowRisk_Individuals\\\"],\\\"wrapperStatus\\\":{\\\"value\\\":\\\"PENDING\\\"},\\\"sourceLists\\\":[\\\"SAPS\\\"],\\\"eaddressTo\\\":\\\"\\\",\\\"eaddressCC\\\":\\\"\\\"}],\\\"clientId\\\":\\\"001-C-0000000004\\\",\\\"clientKey\\\":5146596,\\\"version\\\":\\\"v4.1.1.0 - Released February 7, 2017\\\",\\\"isiReserved\\\":\\\"\\\"}],\\\"isiReserved\\\":\\\"\\\"}\",\"providerName\":\"FINSCAN\",\"providerMethod\":\"SLLookupMulti\"}";
		String	summary3="{\"errorMsg\":\"\",\"frgTransId\":\"P_1ETTSpiUbnetSDx5XZgA\",\"score\":\"0.99\",\"fraugsterApproved\":\"0\",\"investigationPoints\":[\"susp_bill_ad\",\"susp_machine\"],\"id\":4,\"status\":\"WATCH_LIST\"}";
		String	summary4="{\"status\":\"PASS\",\"ip\":\"NOT_REQUIRED\",\"email\":\"NOT_REQUIRED\",\"accountNumber\":\"NOT_REQUIRED\",\"name\":\"false\",\"domain\":\"false\",\"webSite\":\"false\"}";
		Map<String, List<EventDBDto>> eventDBDtoList= new HashMap<>();
		List<EventDBDto> listEvent= new ArrayList<>();
		EventDBDto eventDBDto= new EventDBDto();
		eventDBDto.setSummary(summary);
		eventDBDto.setServiceType("IP");
		eventDBDto.setStatus("Pass");
		EventDBDto eventDBDto1= new EventDBDto();
		eventDBDto1.setSummary(summary1);
		eventDBDto1.setServiceType("KYC");
		eventDBDto1.setStatus("Not Performed");
		EventDBDto eventDBDto2= new EventDBDto();
		eventDBDto2.setSummary(summary2);
		eventDBDto2.setServiceType("SANCTION");
		eventDBDto2.setStatus("Pending");
		EventDBDto eventDBDto3= new EventDBDto();
		eventDBDto3.setSummary(summary3);
		eventDBDto3.setServiceType("FRAUGSTER");
		eventDBDto3.setStatus("Watchlist");
		EventDBDto eventDBDto4= new EventDBDto();
		eventDBDto4.setSummary(summary4);
		eventDBDto4.setServiceType(Constants.BLACKLIST);
		eventDBDto4.setStatus("Pass");
		listEvent.add(eventDBDto);
		listEvent.add(eventDBDto1);
       listEvent.add(eventDBDto2);
		listEvent.add(eventDBDto3);
		listEvent.add(eventDBDto4);
		eventDBDtoList.put(Constants.IP, listEvent);
		eventDBDtoList.put(Constants.KYC, listEvent);
		eventDBDtoList.put(Constants.SANCTION, listEvent);
		eventDBDtoList.put(Constants.FRAUGSTER, listEvent);
		eventDBDtoList.put(Constants.BLACKLIST, listEvent);
		List<ContactWrapper> otherContacts= new ArrayList<>();
		ContactWrapper contactWrapper=new ContactWrapper();
		contactWrapper.setAccountId(1);
		contactWrapper.setAddress("MISSOURI, HP19 3EQ");
		contactWrapper.setCity("MISSOURI");
		otherContacts.add(contactWrapper);
		String contact="{\"contact_sf_id\":\"980411111111110003\","
				+ "\"trade_contact_id\":2261075,\"title\":\"Mr\",\"pref_name\":\"\","
				+ "\"first_name\":\"Sujit\",\"middle_name\":\"\",\"last_name\":"
				+ "\"Bond\",\"second_surname\":\"\","
				+ "\"mothers_surname\":\"\",\"dob\":\"1918-11-18\",\"position_of_significance\":"
				+ "\"\",\"authorised_signatory\":\"\",\"job_title\":\"Other\","
				+ "\"address_type\":\"Current Address\",\"address1\":\", MISSOURI, HP19 3EQ, GBR\","
				+ "\"building_name\":\"Rainham House\",\"street\":\"\",\"post_code\":\"HP19 3EQ\","
				+ "\"post_code_lat\":0.0,\"post_code_long\":0.0,\"country_of_residence\":"
				+ "\"GBR\",\"home_phone\":\"\",\"work_phone\":\"\",\"work_phone_ext\":"
				+ "\"\",\"mobile_phone\":\"+91-9922666897\",\"primary_phone\":\"\",\"email\":\"ab@ab.com\","
				+ "\"second_email\":\"\",\"designation\":\"Other\",\"ip_address\":"
				+ "\"184.22.212.147\",\"ip_address_latitude\":\"0.00\",\"ip_address_longitude\":"
				+ "\"0.00\",\"is_primary_contact\":true,\"country_of_nationality\":"
				+ "\"BRN\",\"gender\":\"Male\",\"occupation\":\"Other\",\"passport_number\":"
				+ "\"\",\"passport_country_code\":\"\",\"passport_exipry_date\":\"\","
				+ "\"passport_full_name\":\"\",\"passport_mrz_line_1\":\"\",\"passport_mrz_line_2\":"
				+ "\"\",\"passport_birth_family_name\":\"\",\"passport_name_at_citizen\":"
				+ "\"\",\"passport_birth_place\":\"\",\"driving_license\":\"\","
				+ "\"driving_version_number\":\"\",\"driving_license_card_number\":"
				+ "\"\",\"driving_license_country\":\"\",\"driving_state_code\":\"\","
				+ "\"driving_expiry\":\"\",\"medicare_card_number\":\"\",\"medicare_ref_number\":"
				+ "\"\",\"australia_rta_card_number\":\"\",\"state_province_county\":"
				+ "\"MISSOURI\",\"municipality_of_birth\":\"\",\"country_of_birth\":\"\","
				+ "\"state_of_birth\":\"\",\"civic_number\":\"\",\"sub_building\":\"\","
				+ "\"unit_number\":\"\",\"sub_city\":\"\",\"region_suburb\":\"\","
				+ "\"national_id_type\":\"\",\"national_id_number\":\"\",\"years_in_address\":\"\","
				+ "\"residential_status\":\"\",\"version\":2,\"update_method\":\"MANUAL\","
				+ "\"district\":\"\",\"area_number\":\"\",\"aza\":\"\",\"prefecture\":\"\",\"floor_number\":\"\"}";
		String accountAttrib="{\"onQueue\":\"true\",\"acc_sf_id\":\"0010O00001nN1lkQAC\",\"trade_acc_id\":1,"
				+ "\"trade_acc_number\":\"5201I528352\",\"branch\":\"MoorgateHO\",\"channel\":"
				+ "\"Website\",\"cust_type\":\"CFX\",\"act_name\":\"EUROTRADESUPPLYLIMITED\","
				+ "\"country_interest\":\"\",\"trade_name\":\"EUROTRADESUPPLYLIMITED\",\"trasaction_purpose\":"
				+ "\"PurchaseofGoods\",\"op_country\":\"\",\"turnover\":\"900000\",\"service_req\":"
				+ "\"Transfer\",\"buying_currency\":\"EUR\",\"selling_currency\":\"USD\",\"txn_value\":"
				+ "\"\",\"source_of_fund\":\"\",\"avg_txn\":20000,\"source\":\"Internet\",\"sub_source"
				+ "\":\"PayPerClickV1\",\"referral\":\"\",\"referral_text\":\"Roger and Associates\",\"extended_referral\":"
				+ "\"\",\"ad_campaign\":\"Brand\",\"keywords\":\"currencies+direct\",\"search_engine\":"
				+ "\"\",\"website\":\"www.eurotradesupply.co.uk\",\"affiliate_name\":\"MarketingHO\","
				+ "\"reg_mode\":\"InPerson\",\"organization_legal_entity\":\"CDLGB\",\"version\":2,"
				+ "\"reg_date_time\":\"2019-03-12T16:44:16Z\",\"company\":{\"billing_address\":"
				+ "\"60ParkhurstAvenue,Manchester,GreaterManchester,M403QN,UK\",\"company_phone\":"
				+ "\"+44-7858290477\",\"shipping_address\":\"\",\"vat_no\":\"\",\"country_region\":"
				+ "\"\",\"country_of_establishment\":\"GBR\",\"corporate_type\":\"Ltd\",\"registration_no\":"
				+ "\"11580449\",\"incorporation_date\":\"2018-09-20\",\"industry\":\"AlcoholandTobacco"
				+ "\",\"etailer\":\"false\",\"option\":\"false\",\"type_of_financial_account\":\"\","
				+ "\"ccj\":\"false\",\"ongoing_due_diligence_date\":\"\",\"est_no_transactions_pcm\":"
				+ "\"1\"},\"corporate_compliance\":{\"sic\":\"46341\",\"sic_desc\":\"UKSIC2007\","
				+ "\"former_name\":\"\",\"legal_form\":\"\",\"foreign_owned_company\":\"\",\"net_worth"
				+ "\":\"0\",\"fixed_assets\":\"0\",\"total_liabilities_and_equities\":\"0\","
				+ "\"total_share_holders\":\"1\",\"global_ultimate_DUNS\":\"\",\"global_ultimate_name"
				+ "\":\"\",\"global_ultimate_country\":\"\",\"registration_date\":\"\",\"match_name\":"
				+ "\"EUROTRADESUPPLYLIMITED\",\"iso_country_code_2_digit\":\"\",\"iso_country_code_3_digit\":"
				+ "\"\",\"statement_date\":\"0\",\"gross_income\":\"0\",\"net_income\":\"0\","
				+ "\"total_current_assets\":\"0\",\"total_assets\":\"0\",\"total_long_term_liabilities"
				+ "\":\"0\",\"total_current_liabilities\":\"0\",\"total_matched_shareholders\":\"\","
				+ "\"financial_strength\":\"O3\"},\"risk_profile\":{\"country_risk_indicator\":"
				+ "\"\",\"risk_trend\":\"\",\"risk_direction\":\"\",\"updated_risk\":"
				+ "\"\",\"failure_score\":\"1353\",\"delinquency_failure_score\":\"\","
				+ "\"continent\":\"\",\"country\":\"GB\",\"state_country\":\"\",\"duns_number\":"
				+ "\"224386640\",\"trading_styles\":\"\",\"us1987_primary_SIC_4_digit\":"
				+ "\"46341\",\"financial_figures_month\":\"\",\"financial_figures_year\":\"\",\"financial_year_end_date\":\"\","
				+ "\"annual_sales\":\"\",\"modelled_annual_sales\":\"\",\"net_worth_amount\":\"\","
				+ "\"modelled_net_worth\":\"\",\"location_type\":\"\",\"import_export_indicator\":\""
				+ "\",\"domestic_ultimate_record\":\"\",\"global_ultimate_record\":\"\","
				+ "\"group_structure_number_of_levels\":\"\",\"headquarter_details\":\"\","
				+ "\"immediate_parent_details\":\"\",\"domestic_ultimate_parent_details\":\"\","
				+ "\"global_ultimate_parent_details\":\"\",\"credit_limit\":\"14,000\","
				+ "\"risk_rating\":\"O3\",\"profit_loss\":\"\"},\"affiliate_number\":\"A00A0399\"}";
		
		DataAnonymisationDetailsDBDto DataAnonymisationDetailsDBDto= new DataAnonymisationDetailsDBDto();
	
		DataAnonymisationDetailsDBDto.setContactAttrib(contact);
		DataAnonymisationDetailsDBDto.setEventDBDto(eventDBDtoList);
		DataAnonymisationDetailsDBDto.setOtherContacts(otherContacts);
		DataAnonymisationDetailsDBDto.setPaginationDetails(paginationDetails);
		DataAnonymisationDetailsDBDto.setActivityLogs(activityLogs);
		DataAnonymisationDetailsDBDto.setWatachList(watchlist);
		DataAnonymisationDetailsDBDto.setContactStatusReason(statusReason);
		DataAnonymisationDetailsDBDto.setLockedBy("cd.comp.system");
		DataAnonymisationDetailsDBDto.setUserResourceId(666537);
		DataAnonymisationDetailsDBDto.setIsOnQueue(true);
		DataAnonymisationDetailsDBDto.setDocumentList(documentList);
		DataAnonymisationDetailsDBDto.setAlertComplianceLog("");
		DataAnonymisationDetailsDBDto.setKycSupportedCountryList(kycSupportedCountryList);
		DataAnonymisationDetailsDBDto.setTradeContactId("937649");
		DataAnonymisationDetailsDBDto.setCustomCheckStatus("4");
		DataAnonymisationDetailsDBDto.setAccountAttrib(accountAttrib);
		DataAnonymisationDetailsDBDto.setRegistrationInDate(Timestamp.valueOf("2009-11-10 05:39:22"));
		DataAnonymisationDetailsDBDto.setComplianceExpiry(Timestamp.valueOf("2020-05-30 04:08:26"));
		return DataAnonymisationDetailsDBDto;
		
	}
	
	
	public DataAnonymisationDetailsDto getDataAnonymisationDetailsDto()
	{
		List<UploadDocumentTypeDBDto> documentList= new ArrayList<>();
		UploadDocumentTypeDBDto uploadDocumentTypeDBDto= new UploadDocumentTypeDBDto();
		uploadDocumentTypeDBDto.setId(1);
		uploadDocumentTypeDBDto.setDocumentName("Drivers License ");
		uploadDocumentTypeDBDto.setCategory("PIA");
		documentList.add(uploadDocumentTypeDBDto);
		List<StatusReasonData> statusReasonDataList= new ArrayList<>();
		StatusReasonData statusReasonData= new StatusReasonData();
		statusReasonData.setName("");
		statusReasonData.setValue(true);
		statusReasonDataList.add(statusReasonData);
		StatusReason statusReason= new StatusReason();
		statusReason.setStatusReasonData(statusReasonDataList);
		WatchListData watchlistData = new WatchListData();
		watchlistData.setId(1);
		watchlistData.setName("abc");
		List<WatchListData> watchlistDatas = new ArrayList<>();
		watchlistDatas.add(watchlistData);
		Watchlist watchlist = new Watchlist();
		watchlist.setWatchlistData(watchlistDatas);
		List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
		ActivityLogDataWrapper data= new ActivityLogDataWrapper();
		data.setActivityType("PROFILE ADD_COMMENT");
		data.setCreatedBy("cd.comp.system");
		data.setCreatedOn("01/11/2020 13:05:06");
		activityLogData.add(data);
		ActivityLogs  activityLogs= new ActivityLogs();
		activityLogs.setActivityLogData(activityLogData);
		InternalRule internalRule= new InternalRule();
		CustomCheck customCheck= new CustomCheck();
		CountryCheck countryCheck= new CountryCheck();
		countryCheck.setCountryCheckTotalRecords(1);
		customCheck.setCountryCheck(countryCheck);
		customCheck.setCustomCheckTotalRecords(1);
		customCheck.setFailCount(0);
		GlobalCheck globalCheck= new GlobalCheck();
		globalCheck.setGlobalCheckTotalRecords(0);
		IpCheck ipCheck= new IpCheck();
		ipCheck.setIpCheckTotalRecords(0);
		customCheck.setGlobalCheck(globalCheck);
		customCheck.setIpCheck(ipCheck);
		internalRule.setCustomCheck(customCheck);
		Blacklist blacklist= new Blacklist();
		blacklist.setAccNumberMatchedData("");
		blacklist.setDomain("false");
		blacklist.setDomainMatchedData("");
		blacklist.setEmail("Not Required");
		blacklist.setEmailMatchedData("");
		blacklist.setFailCount(1);
		blacklist.setIp("Not Required");
		blacklist.setIpMatchedData("");
		blacklist.setIsRequired(true);
		blacklist.setName("false");
		blacklist.setNameMatchedData("");
		blacklist.setStatus(false);
		blacklist.setPassCount(2);
		blacklist.setWebsiteMatchedData("");
		blacklist.setPhone(false);
		blacklist.setPhoneMatchedData("");
		internalRule.setBlacklist(blacklist);
		  ContactWrapper currentContact= new ContactWrapper();
		  currentContact.setTradeContactID(937649);
		  currentContact.setDob("18/11/1918");
		  currentContact.setPhoneWork("----");
		  currentContact.setEmail("ab@ab.com");
		  currentContact.setIpAddress("184.22.212.147");
		  currentContact.setNationality("BRN");
		  currentContact.setOccupation("Other");
		AccountWrapper account= new AccountWrapper();
		
		account.setClientType("CFX");
		account.setCurrencyPair("USD  -  EUR");
		account.setEstimTransValue(" ----");
		account.setPurposeOfTran("PurchaseofGoods");
		account.setServiceRequired("Transfer");
		account.setSource("Internet");
		account.setSourceOfFund("----");
		account.setRefferalText("Roger and Associates");
		account.setAffiliateName("MarketingHO");
		account.setRegMode("Internet");
		account.setComplianceStatus("INACTIVE");
		account.setId(1);
		account.setOrgCode("CDLGB");
		account.setRegCompleteAccount("");
		account.setRegistrationInDate("2009-11-10 05:39:22");
		account.setComplianceExpiry("2020-05-30 04:08:26");
		account.setLegalEntity("CDLGB");
		Kyc kyc= new Kyc();
		kyc.setKycTotalRecords(0);
		Fraugster fraugster=new Fraugster();
		fraugster.setFraugsterTotalRecords(0);
		Onfido onfido=new Onfido();
		onfido.setOnfidoTotalRecords(0);
		List<ContactWrapper> otherContacts= new ArrayList<>();
		ContactWrapper contactWrapper= new ContactWrapper();
		contactWrapper.setAccountId(1);
		contactWrapper.setAddress("MISSOURI, HP19 3EQ");
		contactWrapper.setCity("MISSOURI");
		otherContacts.add(contactWrapper);
		PaginationDetails paginationDetails= new PaginationDetails();
		 PaginationData prevRecord= new PaginationData();
		 prevRecord.setAccountid("0");
		 prevRecord.setCustType("PFX");
		 prevRecord.setId("0");
		 PaginationData nextRecord= new PaginationData();;
		 nextRecord.setAccountid("1");
		 nextRecord.setCustType("PFX");
		 nextRecord.setId("1");
		 paginationDetails.setPrevRecord(prevRecord);
		 paginationDetails.setNextRecord(nextRecord);
		DataAnonymisationDetailsDto dataAnonymisationDetailsDto= new DataAnonymisationDetailsDto();
		dataAnonymisationDetailsDto.setAccount(account);
		dataAnonymisationDetailsDto.setCurrentContact(currentContact);
		dataAnonymisationDetailsDto.setInternalRule(internalRule);
		dataAnonymisationDetailsDto.setKyc(kyc);
		dataAnonymisationDetailsDto.setFraugster(fraugster);
		dataAnonymisationDetailsDto.setOnfido(onfido);
		dataAnonymisationDetailsDto.setOtherContacts(otherContacts);
		dataAnonymisationDetailsDto.setPaginationDetails(paginationDetails);
		dataAnonymisationDetailsDto.setActivityLogs(activityLogs);
		dataAnonymisationDetailsDto.setWatchlist(watchlist);
		dataAnonymisationDetailsDto.setStatusReason(statusReason);
		dataAnonymisationDetailsDto.setLocked(true);
		dataAnonymisationDetailsDto.setLockedBy("cd.comp.system");
		dataAnonymisationDetailsDto.setUserResourceId(666537);
		dataAnonymisationDetailsDto.setDocumentList(documentList);
		dataAnonymisationDetailsDto.setAlertComplianceLog("");
		dataAnonymisationDetailsDto.setIsOnQueue(true);
		return dataAnonymisationDetailsDto;
		
	}
	@Test
	public void testTransform() {
		DataAnonymisationDetailsDto expectedResult=getDataAnonymisationDetailsDto();
		DataAnonymisationDetailsDto actualResult=dataAnonymisationDetailsTransformer.transform(getDataAnonymisationDetailsDBDto());
		assertEquals(expectedResult.getInternalRule().getBlacklist().getDomain(),actualResult.getInternalRule().getBlacklist().getDomain());
		assertEquals(expectedResult.getInternalRule().getBlacklist().getName(),actualResult.getInternalRule().getBlacklist().getName());
		assertEquals(expectedResult.getInternalRule().getBlacklist().getEmail(),actualResult.getInternalRule().getBlacklist().getEmail());
		assertEquals(expectedResult.getInternalRule().getBlacklist().getIp(),actualResult.getInternalRule().getBlacklist().getIp());
		
	}

}
