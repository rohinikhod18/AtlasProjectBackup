package com.currenciesdirect.gtg.compliance.dbport.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.BusinessUnit;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.Dashboard;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.Fulfilment;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.FulfilmentGraph;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.Geography;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.PaymentDashboard;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.QueueRecordsPerCountry;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.QueueRecordsPerLegalEntity;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.RegistrationDashboard;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.TimelineSnapshot;
import com.currenciesdirect.gtg.compliance.core.report.IDashboardDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Class DashboardDBServiceImpl.
 *
 */
@Component("dashboardDBServiceImpl")
public class DashboardDBServiceImpl extends  AbstractDao implements IDashboardDBService {

	private static final Logger LOG = LoggerFactory.getLogger(DashboardDBServiceImpl.class);
	/**
	 * Business -- 
	 * To display compliance geographical , business unit,  fulfillment and time line snapshot information 
	 * for PFX and CFX registration, Payment in and Payment out on compliance dashboard page.
	 * to show geographical graph we shows as per PFX , CFX compliance registration records.
	 * business unit are calculate against organizations.
	 * fulfillment are calculated on the activity perform on each record (i.e changing status to or from Active to Reject for Registration and 
	 * for Paymentin/Paymentout CLEAR, SEIZE and REJECT)
	 * time line snapshot is calculated on all queue pending records.
	 * 
	 * Implementation---
	 * 1) get tresholdTime from "dashboard.fulfillment.threshold.time" to get all dashboard information.
	 *  if "dashboard.fulfillment.threshold.time" property is not present in comliance.properties file 
	 *  then it takes DEFAULT_FULFILLMENT_TRESHOLD_TIME.
	 *  2)call getRegistrationDashboard() to set geographical,  business unit and time line snapshot information for PFX and CFX registration.
	 *  3) call getPaymentInDashboard() to set   business unit and time line snapshot information for payment in flow
	 *  4) call getPaymentOutDashboard() to set   business unit and time line snapshot information for payment out flow
	 *  5) call dashboardFulfilment() to set fulfillment information for registration , payment in and payment out flow.
	 *  6) set current time as Refresh time.
	 *  
 	 */
	@Override
	public Dashboard getDashboard() throws CompliancePortalException {
		Connection connection = null;
		Dashboard dashboard = new Dashboard();
		
		try {
			connection = getConnection(Boolean.TRUE);
			dashboard.setTresholdFulfimentTime(SearchCriteriaUtil.getDashBoardThresholdFulfillmentTime());
			dashboard.setRegDashboard(getRegistrationDashboard(connection));
			dashboard.setPaymentInDashboard(getPaymentInDashboard(connection));
			dashboard.setPaymentOutDashboard(getPaymentOutDashboard(connection));
			dashboardFulfilment(dashboard,connection);
			dashboard.setRefreshOn(DateTimeFormatter.getTime(new Timestamp(System.currentTimeMillis()).toString()));
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return dashboard;
	}

	private PaymentDashboard getPaymentOutDashboard(Connection connection) throws CompliancePortalException {
		Dashboard dashboard=new Dashboard();
		PaymentDashboard paymentOutDashboard = new PaymentDashboard();
		try {	
			getPaymentOutByLegalEntity(paymentOutDashboard,connection);
			getPaymentOutTimelineSnapshot(paymentOutDashboard,connection);
			
			dashboard.setPaymentOutDashboard(paymentOutDashboard);
			if(null != dashboard.getPaymentOutBusinessUnitJsonString()){
				paymentOutDashboard.setBusinessUnitJsonString(dashboard.getPaymentOutBusinessUnitJsonString());	
			}
			
		} catch (Exception e) {
			LOG.debug("Error while fetching data in getPaymentOutDashboard() method",e);
		} 
		return paymentOutDashboard;
	}

	//AT-2355  Dash board should show legal entity view
	private void getPaymentOutByLegalEntity(PaymentDashboard paymentOutDashboard, Connection connection) throws CompliancePortalException {
		ArrayList<QueueRecordsPerLegalEntity> queueRecordsLegalEntityPaymentOutList = new ArrayList<>();
		BusinessUnit businessUnit = new BusinessUnit();
		int totalRecords =0;	
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =connection.prepareStatement(RegistrationReportQueryConstant.GET_PAYMENTOUT_DASHBOARD_BY_LEGALENTITY.getQuery());
			rs = statement.executeQuery();
			while(rs.next()){
				QueueRecordsPerLegalEntity queueRecordsLegalEntityPaymentOut = new QueueRecordsPerLegalEntity();
				queueRecordsLegalEntityPaymentOut.setLegalEntity(rs.getString(DashboardDBColumns.LEGALENTITY.getName()));
				queueRecordsLegalEntityPaymentOut.setVisits(rs.getInt(DashboardDBColumns.RECORDS.getName()));
				queueRecordsLegalEntityPaymentOutList.add(queueRecordsLegalEntityPaymentOut);
				totalRecords = totalRecords + queueRecordsLegalEntityPaymentOut.getVisits();
			}
			businessUnit.setQueueRecordsPerLegalEntity(queueRecordsLegalEntityPaymentOutList);
			paymentOutDashboard.setBusinessUnit(businessUnit);
			paymentOutDashboard.setTotalRecords(totalRecords);
		}catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
	}

	private PaymentDashboard getPaymentInDashboard(Connection connection) throws CompliancePortalException {
		Dashboard dashboard=new Dashboard();
		PaymentDashboard paymentInDashboard = new PaymentDashboard();			
		try {			
			getPaymentInByLegalEntity(paymentInDashboard,connection);
			getPaymentInTimelineSnapshot(paymentInDashboard,connection);
			dashboard.setPaymentInDashboard(paymentInDashboard);
			if(null != dashboard.getPaymentInBusinessUnitJsonString()){
				paymentInDashboard.setBusinessUnitJsonString(dashboard.getPaymentInBusinessUnitJsonString());
			}
			
		} catch (Exception e) {
			LOG.debug("Error while fetching data getPaymentInDashboard() method",e);
		} 
		return paymentInDashboard;
	}

	//AT-2355  Dash board should show legal entity view
	private void getPaymentInByLegalEntity(PaymentDashboard paymentInDashboard, Connection connection) throws CompliancePortalException {
		ArrayList<QueueRecordsPerLegalEntity> queueRecordsLegalEntityPaymentInList = new ArrayList<>();
		BusinessUnit businessUnit = new BusinessUnit();
		int totalRecords =0;	
		PreparedStatement statement = null;
		ResultSet rs = null;		
		
		try {
			statement =connection.prepareStatement(RegistrationReportQueryConstant.GET_PAYMENTIN_DASHBOARD_BY_LEGALENTITY.getQuery());
			rs = statement.executeQuery();
			while(rs.next()){
				QueueRecordsPerLegalEntity queueRecordsLegalEntityPaymentIn = new QueueRecordsPerLegalEntity();
				queueRecordsLegalEntityPaymentIn.setLegalEntity(rs.getString(DashboardDBColumns.LEGALENTITY.getName()));
				queueRecordsLegalEntityPaymentIn.setVisits(rs.getInt(DashboardDBColumns.RECORDS.getName()));
				queueRecordsLegalEntityPaymentInList.add(queueRecordsLegalEntityPaymentIn);
				totalRecords =totalRecords + queueRecordsLegalEntityPaymentIn.getVisits();
			}
			businessUnit.setQueueRecordsPerLegalEntity(queueRecordsLegalEntityPaymentInList);
			paymentInDashboard.setBusinessUnit(businessUnit);
			paymentInDashboard.setTotalRecords(totalRecords);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		
	}

	private RegistrationDashboard getRegistrationDashboard( Connection connection) throws CompliancePortalException {
		Dashboard dashboard=new Dashboard();
		RegistrationDashboard registrationDashboard = new RegistrationDashboard();
		
		try {
			getRegByCountry(registrationDashboard,connection);
			getRegByLegalEntity(registrationDashboard,connection);
			getRegTimelineSnapshot(registrationDashboard,connection);
			dashboard.setRegDashboard(registrationDashboard);
		
			if(null != dashboard.getRegPfxByBusinessUnitJsonString()){
				registrationDashboard.setRegPfxByBusinessUnitJsonString(dashboard.getRegPfxByBusinessUnitJsonString());
			}
			if(null != dashboard.getRegCfxByBusinessUnitJsonString()){
				registrationDashboard.setRegCfxByBusinessUnitJsonString(dashboard.getRegCfxByBusinessUnitJsonString());	
			}
			if(null != dashboard.getRegPfxByGeographyAsJson()){
				registrationDashboard.setRegPfxByGeographyJsonString(dashboard.getRegPfxByGeographyAsJson());
			}
			if(null != dashboard.getRegCfxByGeographyAsJson()){
				registrationDashboard.setRegCfxByGeographyJsonString(dashboard.getRegCfxByGeographyAsJson());
			}
			
		}catch(Exception e){
			LOG.debug("Error while fetching data getRegistrationDashboard() method",e);
		}
		return registrationDashboard;
	}


	private void getRegTimelineSnapshot(RegistrationDashboard registrationDashboard, Connection connection) throws CompliancePortalException {
		TimelineSnapshot pfxTimelineSnapshot = new TimelineSnapshot();
		TimelineSnapshot cfxTimelineSnapshot = new TimelineSnapshot();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =connection.prepareStatement(RegistrationReportQueryConstant.GET_REGISTRATION_DASHBOARD_TIMELINE_SNAP_SHOT.getQuery());
			rs= statement.executeQuery();
			while (rs.next()) {
				if(rs.getString(DashboardDBColumns.CUSTOMER_TYPE.getName()).equalsIgnoreCase(Constants.CUST_TYPE_PFX)){
					pfxTimelineSnapshot.setAvgRecordAge(rs.getString(DashboardDBColumns.AGE.getName()));
					pfxTimelineSnapshot.setNewPfxContactId(rs.getString(DashboardDBColumns.LAST_CONTACT_ID.getName()));
					pfxTimelineSnapshot.setOldPfxContactId(rs.getString(DashboardDBColumns.FIRST_CONTACT_ID.getName()));
					pfxTimelineSnapshot.setOldestRecord(rs.getString(DashboardDBColumns.OLD_CONTACT_AGE.getName()));
					pfxTimelineSnapshot.setNewestRecord(rs.getString(DashboardDBColumns.NEW_CONTACT_AGE.getName()));
					pfxTimelineSnapshot.setOldCustomerType(Constants.CUST_TYPE_PFX);
					pfxTimelineSnapshot.setNewCustomerType(Constants.CUST_TYPE_PFX);
				}else{
					cfxTimelineSnapshot.setAvgRecordAge(rs.getString(DashboardDBColumns.AGE.getName()));
					cfxTimelineSnapshot.setNewCfxContactId(rs.getString(DashboardDBColumns.LAST_CONTACT_ID.getName()));
					cfxTimelineSnapshot.setOldCfxContactId(rs.getString(DashboardDBColumns.FIRST_CONTACT_ID.getName()));
					cfxTimelineSnapshot.setOldestRecord(rs.getString(DashboardDBColumns.OLD_CONTACT_AGE.getName()));
					cfxTimelineSnapshot.setNewestRecord(rs.getString(DashboardDBColumns.NEW_CONTACT_AGE.getName()));
					cfxTimelineSnapshot.setOldCustomerType(Constants.CUST_TYPE_CFX);
					cfxTimelineSnapshot.setNewCustomerType(Constants.CUST_TYPE_CFX);
				}
			}
			
			registrationDashboard.setRegPfxTimelineSnapshot(pfxTimelineSnapshot);
			registrationDashboard.setRegCfxTimelineSnapshot(cfxTimelineSnapshot);
			
		}catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);	
		}finally {
			closePrepareStatement(statement);
			closeResultset(rs);
		}
	
	}
	
	private void getPaymentInTimelineSnapshot(PaymentDashboard paymentInDashboard, Connection connection) throws CompliancePortalException {
		TimelineSnapshot timelineSnapshot = new TimelineSnapshot();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =connection.prepareStatement(RegistrationReportQueryConstant.GET_PAYMENTIN_DASHBOARD_TIMELINE_SNAP_SHOT.getQuery());
			rs= statement.executeQuery();
			while (rs.next()) {
				timelineSnapshot.setAvgRecordAge(rs.getString(DashboardDBColumns.AGE.getName()));
				timelineSnapshot.setNewPfxContactId(rs.getString(DashboardDBColumns.LAST_PAYMENTIN_ID.getName()));
				timelineSnapshot.setOldPfxContactId(rs.getString(DashboardDBColumns.FIRST_PAYMENTIN_ID.getName()));
				timelineSnapshot.setOldestRecord(rs.getString(DashboardDBColumns.OLD_PAYMENTIN_AGE.getName()));
				timelineSnapshot.setNewestRecord(rs.getString(DashboardDBColumns.NEW_PAYMENTIN_AGE.getName()));
				timelineSnapshot.setOldCustomerType(Constants.CUST_TYPE_PFX);
				timelineSnapshot.setNewCustomerType(Constants.CUST_TYPE_PFX);
			}
			paymentInDashboard.setTimelineSnapshot(timelineSnapshot);
			
		}catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);	
		}finally {
			closePrepareStatement(statement);
			closeResultset(rs);
		}
	
	}
	
	
	private void getPaymentOutTimelineSnapshot(PaymentDashboard paymentoutDashboard, Connection connection) throws CompliancePortalException {
		TimelineSnapshot timelineSnapshot = new TimelineSnapshot();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =connection.prepareStatement(RegistrationReportQueryConstant.GET_PAYMENTOUT_DASHBOARD_TIMELINE_SNAP_SHOT.getQuery());
			rs= statement.executeQuery();
			while (rs.next()) {
				timelineSnapshot.setAvgRecordAge(rs.getString(DashboardDBColumns.AGE.getName()));
				timelineSnapshot.setNewPfxContactId(rs.getString(DashboardDBColumns.LAST_PAYMENTOUT_ID.getName()));
				timelineSnapshot.setOldPfxContactId(rs.getString(DashboardDBColumns.FIRST_PAYMENTOUT_ID.getName()));
				timelineSnapshot.setOldestRecord(rs.getString(DashboardDBColumns.OLD_PAYMENTOUT_AGE.getName()));
				timelineSnapshot.setNewestRecord(rs.getString(DashboardDBColumns.NEW_PAYMENTOUT_AGE.getName()));
				timelineSnapshot.setOldCustomerType(Constants.CUST_TYPE_PFX);
				timelineSnapshot.setNewCustomerType(Constants.CUST_TYPE_PFX);
			}
			
			paymentoutDashboard.setTimelineSnapshot(timelineSnapshot);
			
		}catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);	
		}finally {
			closePrepareStatement(statement);
			closeResultset(rs);
		}
	
	}
	
	private void getRegByCountry(RegistrationDashboard registrationDashboard, Connection connection) throws CompliancePortalException {
		ArrayList<QueueRecordsPerCountry> queueRecordsPfxCountries = new ArrayList<>();
		ArrayList<QueueRecordsPerCountry> queueRecordsCfxCountries = new ArrayList<>();
		Geography geographyPfx = new Geography();
		Geography geographyCfx = new Geography();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement =connection.prepareStatement(RegistrationReportQueryConstant.GET_REGISTRATION_DASHBOARD_BY_COUNTRY.getQuery());
			rs= statement.executeQuery();
			while (rs.next()) {
				if(Constants.CUST_TYPE_PFX.equalsIgnoreCase(rs.getString(DashboardDBColumns.CUSTOMER_TYPE.getName()))){
					QueueRecordsPerCountry queueRecordsPfxCountryCode = new QueueRecordsPerCountry();
					queueRecordsPfxCountryCode.setCountryName(rs.getString(DashboardDBColumns.COUNTRY_DISPLAY_NAME.getName()));
					queueRecordsPfxCountryCode.setId(rs.getString(DashboardDBColumns.COUNTRY_SHORT_CODE.getName()));
					queueRecordsPfxCountryCode.setValue(rs.getInt(DashboardDBColumns.COUNTRY_WISE_TOTAL_RECORDS.getName()));
					queueRecordsPfxCountries.add(queueRecordsPfxCountryCode);
				}else {
					QueueRecordsPerCountry queueRecordsCfxCountryCode = new QueueRecordsPerCountry();
					queueRecordsCfxCountryCode.setCountryName(rs.getString(DashboardDBColumns.COUNTRY_DISPLAY_NAME.getName()));
					queueRecordsCfxCountryCode.setId(rs.getString(DashboardDBColumns.COUNTRY_SHORT_CODE.getName()));
					queueRecordsCfxCountryCode.setValue(rs.getInt(DashboardDBColumns.COUNTRY_WISE_TOTAL_RECORDS.getName()));
					queueRecordsCfxCountries.add(queueRecordsCfxCountryCode);
				}
			}
			geographyPfx.setQueueRecordsPerCountry(queueRecordsPfxCountries);
			geographyCfx.setQueueRecordsPerCountry(queueRecordsCfxCountries);
			registrationDashboard.setRegPfxByGeography(geographyPfx);
			registrationDashboard.setRegCfxByGeography(geographyCfx);
		}catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);	
		}finally {
			closePrepareStatement(statement);
			closeResultset(rs);
		}
	}

	//AT-2355  Dash board should show legal entity view
	private void getRegByLegalEntity(RegistrationDashboard registrationDashboard, Connection connection) throws CompliancePortalException {		
		ArrayList<QueueRecordsPerLegalEntity> queueRecordsLegalEntityListPfx = new ArrayList<>();		
		ArrayList<QueueRecordsPerLegalEntity> queueRecordsLegalEntityListCfx = new ArrayList<>();		
		BusinessUnit businessUnitPfx=new BusinessUnit();
		BusinessUnit businessUnitCfx=new BusinessUnit();
		int totalRecords =0;
		int totalPfxRecords =0;
		int totalCfxRecords =0;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement =connection.prepareStatement(RegistrationReportQueryConstant.GET_REGISTRATION_DASHBOARD_BY_LEGALENTITY.getQuery());
			rs = statement.executeQuery();			
			while (rs.next()) {
				
				if(Constants.CUST_TYPE_PFX.equalsIgnoreCase(rs.getString(DashboardDBColumns.CUSTOMER_TYPE.getName()))){
					QueueRecordsPerLegalEntity queueRecordsPerLegalEntityPfx = new QueueRecordsPerLegalEntity();
					queueRecordsPerLegalEntityPfx.setLegalEntity(rs.getString(DashboardDBColumns.LEGALENTITY.getName()));
					queueRecordsPerLegalEntityPfx.setVisits(rs.getInt(DashboardDBColumns.BUSINESS_WISE_TOTAL_RECORDS.getName()));	
					queueRecordsLegalEntityListPfx.add(queueRecordsPerLegalEntityPfx);
					totalRecords = totalRecords + queueRecordsPerLegalEntityPfx.getVisits();
					totalPfxRecords = totalPfxRecords + queueRecordsPerLegalEntityPfx.getVisits();
				}else {
					QueueRecordsPerLegalEntity queueRecordsPerLegalEntityCfx = new QueueRecordsPerLegalEntity();
					queueRecordsPerLegalEntityCfx.setLegalEntity(rs.getString(DashboardDBColumns.LEGALENTITY.getName()));
					queueRecordsPerLegalEntityCfx.setVisits(rs.getInt(DashboardDBColumns.BUSINESS_WISE_TOTAL_RECORDS.getName()));	
					queueRecordsLegalEntityListCfx.add(queueRecordsPerLegalEntityCfx);
					totalRecords = totalRecords + queueRecordsPerLegalEntityCfx.getVisits();
					totalCfxRecords = totalCfxRecords + queueRecordsPerLegalEntityCfx.getVisits();
				}
				
			}
			businessUnitPfx.setQueueRecordsPerLegalEntity(queueRecordsLegalEntityListPfx);
			businessUnitCfx.setQueueRecordsPerLegalEntity(queueRecordsLegalEntityListCfx);
			registrationDashboard.setRegPfxByBusinessUnit(businessUnitPfx);
			registrationDashboard.setRegCfxByBusinessUnit(businessUnitCfx);
			registrationDashboard.setTotalRegRecords(totalRecords);
			registrationDashboard.setTotalPfxRecords(totalPfxRecords);
			registrationDashboard.setTotalCfxRecords(totalCfxRecords);
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);	
		}finally {
			closePrepareStatement(statement);
			closeResultset(rs);
		}
	}	

	private void dashboardFulfilment(Dashboard dashboard, Connection connection) throws CompliancePortalException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection
					.prepareStatement(RegistrationReportQueryConstant.GET_CLEARED_RECORD_DETAILS.getQuery());
			statement.setTimestamp(1, Timestamp.valueOf(dashboard.getTresholdFulfimentTime()));
			statement.setTimestamp(2, Timestamp.valueOf(dashboard.getTresholdFulfimentTime()));
			rs = statement.executeQuery();

			getDashboardFulFillment(dashboard, rs);

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closePrepareStatement(statement);
		}
	}
	
	private void getDashboardFulFillment(Dashboard dashboard, ResultSet rs) {
		Fulfilment regPfxFulfillment = new Fulfilment();
		Fulfilment regCfxFulfillment = new Fulfilment();
		Fulfilment paymentInFulfillment = new Fulfilment();
		Fulfilment paymentOutFulfillment = new Fulfilment();
		Integer totalHours=0;
		Integer totalPFXFulfilledRecords=0;
		Integer totalCFXFulfilledRecords=0;
		Integer totalPaymentInFulfilledRecords=0;
		Integer totalPaymentOutFulfilledRecords=0;
		Integer pfxRecordsClearedPerHour=0;
		Integer cfxRecordsClearedPerHour=0;
		Integer paymentInRecordsClearedPerHour=0;
		Integer paymentOutRecordsClearedPerHour=0;
		Integer pfxAvgCleringTime=0;
		Integer cfxAvgCleringTime=0;
		Integer paymentInAvgCleringTime=0;
		Integer paymentOutAvgCleringTime=0;
		try {
			
			while (rs.next()) {

				if (rs.getString(DashboardDBColumns.RESOURCE_TYPE.getName()).equalsIgnoreCase(Constants.CONTACT)) {

					totalPFXFulfilledRecords = rs.getInt(DashboardDBColumns.TOTAL_RECORDS.getName());
					totalHours = rs.getInt(DashboardDBColumns.TOTAL_HOURS.getName());
					pfxRecordsClearedPerHour = totalPFXFulfilledRecords / totalHours;
					pfxAvgCleringTime = rs.getInt(DashboardDBColumns.AVG_CLEARING_TIME.getName());
					
				} else if (rs.getString(DashboardDBColumns.RESOURCE_TYPE.getName()).equalsIgnoreCase(Constants.ACCOUNT)) {
					
					totalCFXFulfilledRecords = rs.getInt(DashboardDBColumns.TOTAL_RECORDS.getName());
					totalHours = rs.getInt(DashboardDBColumns.TOTAL_HOURS.getName());
					cfxRecordsClearedPerHour = totalCFXFulfilledRecords / totalHours;
					cfxAvgCleringTime = rs.getInt(DashboardDBColumns.AVG_CLEARING_TIME.getName());
					
				} else if (rs.getString(DashboardDBColumns.RESOURCE_TYPE.getName()).equalsIgnoreCase(Constants.PAYMENT_OUT)) {
					
					totalPaymentOutFulfilledRecords = rs.getInt(DashboardDBColumns.TOTAL_RECORDS.getName());
					totalHours = rs.getInt(DashboardDBColumns.TOTAL_HOURS.getName());
					paymentOutRecordsClearedPerHour = totalPaymentOutFulfilledRecords / totalHours;
					paymentOutAvgCleringTime = rs.getInt(DashboardDBColumns.AVG_CLEARING_TIME.getName());
					
				} else if (rs.getString(DashboardDBColumns.RESOURCE_TYPE.getName()).equalsIgnoreCase(Constants.PAYMENT_IN)) {
					
					totalPaymentInFulfilledRecords = rs.getInt(DashboardDBColumns.TOTAL_RECORDS.getName());
					totalHours = rs.getInt(DashboardDBColumns.TOTAL_HOURS.getName());
					paymentInRecordsClearedPerHour = totalPaymentInFulfilledRecords / totalHours;
					paymentInAvgCleringTime = rs.getInt(DashboardDBColumns.AVG_CLEARING_TIME.getName());
					
				}
			}
			
			regPfxFulfillment.setFromTime(DateTimeFormatter.getHours(dashboard.getTresholdFulfimentTime()));
			regPfxFulfillment.setClearedToday(totalPFXFulfilledRecords);
			regPfxFulfillment.setAvgPerHour(pfxRecordsClearedPerHour);
			regPfxFulfillment.setAvgClearingTime(pfxAvgCleringTime);
			regPfxFulfillment.setFulfilmentGraph(setFulfilmentGraph(totalPFXFulfilledRecords, dashboard.getRegDashboard().getTotalPfxRecords()));
			
			regCfxFulfillment.setFromTime(DateTimeFormatter.getHours(dashboard.getTresholdFulfimentTime()));
			regCfxFulfillment.setClearedToday(totalCFXFulfilledRecords);
			regCfxFulfillment.setAvgPerHour(cfxRecordsClearedPerHour);
			regCfxFulfillment.setAvgClearingTime(cfxAvgCleringTime);
			regCfxFulfillment.setFulfilmentGraph(setFulfilmentGraph(totalCFXFulfilledRecords, dashboard.getRegDashboard().getTotalCfxRecords()));
		
			paymentInFulfillment.setFromTime(DateTimeFormatter.getHours(dashboard.getTresholdFulfimentTime()));
			paymentInFulfillment.setClearedToday(totalPaymentInFulfilledRecords);
			paymentInFulfillment.setAvgPerHour(paymentInRecordsClearedPerHour);
			paymentInFulfillment.setAvgClearingTime(paymentInAvgCleringTime);
			paymentInFulfillment.setFulfilmentGraph(setFulfilmentGraph(totalPaymentInFulfilledRecords, dashboard.getPaymentInDashboard().getTotalRecords()));
			
			paymentOutFulfillment.setFromTime(DateTimeFormatter.getHours(dashboard.getTresholdFulfimentTime()));
			paymentOutFulfillment.setClearedToday(totalPaymentOutFulfilledRecords);
			paymentOutFulfillment.setAvgPerHour(paymentOutRecordsClearedPerHour);
			paymentOutFulfillment.setAvgClearingTime(paymentOutAvgCleringTime);
			paymentOutFulfillment.setFulfilmentGraph(setFulfilmentGraph(totalPaymentOutFulfilledRecords,  dashboard.getPaymentOutDashboard().getTotalRecords()));
			
			dashboard.getRegDashboard().setRegPfxFulfilment(regPfxFulfillment);
			dashboard.getRegDashboard().setRegCfxFulfilment(regCfxFulfillment);
			dashboard.getPaymentInDashboard().setFulfilment(paymentInFulfillment);
			dashboard.getPaymentOutDashboard().setFulfilment(paymentOutFulfillment);
			if(null != dashboard.getRegPfxFulfilmentJsonString()){
				dashboard.getRegDashboard().setRegPfxFulfilmentJsonString(dashboard.getRegPfxFulfilmentJsonString());
			}
			if(null != dashboard.getRegCfxFulfilmentJsonString()){
				dashboard.getRegDashboard().setRegCfxFulfilmentJsonString(dashboard.getRegCfxFulfilmentJsonString());	
			}
			if(null != dashboard.getPaymentInFulfilmentJsonString()){
				dashboard.getPaymentInDashboard().setFulfilmentJsonString(dashboard.getPaymentInFulfilmentJsonString());
			}
			if(null != dashboard.getPaymentOutFulfilmentJsonString()){
				dashboard.getPaymentOutDashboard().setFulfilmentJsonString(dashboard.getPaymentOutFulfilmentJsonString());	
			}
			
			
		} catch (Exception e) {
			LOG.debug("Error while fetching data in getDashboardFulFillment() method",e);
		}
		finally {
			closeResultset(rs);
		}
	}
	

	private List<FulfilmentGraph> setFulfilmentGraph(Integer fulfilledValue, Integer remaingValue) {
		
		FulfilmentGraph fulfilmentGraphFullfilled = new FulfilmentGraph();
		FulfilmentGraph fulfilmentGraphRemaing = new FulfilmentGraph();
		List<FulfilmentGraph> fulfilmentGraphList = new ArrayList<>();

		fulfilmentGraphFullfilled.setTitle(Constants.DASHBOARD_FULFILLMENT_GRAPH_FULFILLED_VALUE);
		fulfilmentGraphFullfilled.setValue(fulfilledValue);

		fulfilmentGraphRemaing.setTitle(Constants.DASHBOARD_FULFILLMENT_GRAPH_REMAINING_VALUE);
		fulfilmentGraphRemaing.setValue(remaingValue);

		fulfilmentGraphList.add(fulfilmentGraphFullfilled);
		fulfilmentGraphList.add(fulfilmentGraphRemaing);

		return fulfilmentGraphList;

	}
	
}
