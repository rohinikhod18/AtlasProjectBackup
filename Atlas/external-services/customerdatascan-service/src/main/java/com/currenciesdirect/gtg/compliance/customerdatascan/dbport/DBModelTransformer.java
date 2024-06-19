package com.currenciesdirect.gtg.compliance.customerdatascan.dbport;

import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanDeleteRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanUpdateRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerInsertRequestData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerSearchRequestData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.IRequest;


/**
 * The Class DBModelTransformer.
 */
public class DBModelTransformer implements ITransformer<DBModel, IRequest> {

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.customerdatascan.dbport.ITransformer#transform(java.lang.Object)
	 */
	@Override
	public DBModel transform(IRequest request) {
		if (request instanceof CustomerInsertRequestData) {
			CustomerInsertRequestData requestObject = (CustomerInsertRequestData) request;
			return transformToDBModel(requestObject);
		} else if (request instanceof CustomerDataScanUpdateRequest) {
			CustomerDataScanUpdateRequest requestObject = (CustomerDataScanUpdateRequest) request;
			return transformToDBModel(requestObject);
		} else if (request instanceof CustomerSearchRequestData) {
			CustomerSearchRequestData requestObject = (CustomerSearchRequestData) request;
			return transformToDBModel(requestObject);
		} else if (request instanceof CustomerDataScanDeleteRequest) {
			CustomerDataScanDeleteRequest requestObject = (CustomerDataScanDeleteRequest) request;
			return transformToDBModel(requestObject);
		} else {
			return null;
		}
	}

	/**
	 * Transform to DB model.
	 *
	 * @param request the request
	 * @return the DB model
	 */
	private DBModel transformToDBModel(CustomerInsertRequestData request) {
		DBModel dbModel = new DBModel();
		dbModel.setAddress(request.getAddress());
		dbModel.setAuroraAccountID(request.getAuroraAccountID());
		dbModel.setCountryOfResidence(request.getCountryOfResidence());
		dbModel.setDigitalFootPrint(request.getDigitalFootPrint());
		dbModel.setDobDay(request.getDobDay());
		dbModel.setDobMonth(request.getDobMonth());
		dbModel.setDobYear(request.getDobYear());
		dbModel.setEmail(request.getEmail());
		dbModel.setFamilyNameAtBirth(request.getFamilyNameAtBirth());
		dbModel.setFamilyNameAtCitizenship(request.getFamilyNameAtCitizenship());
		dbModel.setFax(request.getFax());
		dbModel.setGender(request.getGender());
		dbModel.setIpAddress(request.getIpAddress());
		dbModel.setName(request.getName());
		dbModel.setNationality(request.getNationality());
		dbModel.setOrgCode(request.getOrgCode());
		dbModel.setPhone(request.getPhone());
		dbModel.setPlaceOfBirth(request.getPlaceOfBirth());
		dbModel.setSfAccountID(request.getSfAccountID());
		dbModel.setCreatedBy(request.getUserName());
		dbModel.setCreatedOn(new Timestamp(System.currentTimeMillis())
				.toString());
		dbModel.setUpdatedBy(request.getUserName());
		dbModel.setUpdateOn(new Timestamp(System.currentTimeMillis())
				.toString());
		dbModel.setIsDeleted("false");
		return dbModel;
	}

	/**
	 * Transform to DB model.
	 *
	 * @param request the request
	 * @return the DB model
	 */
	private DBModel transformToDBModel(CustomerDataScanUpdateRequest request) {
		DBModel dbModel = new DBModel();
		dbModel.setAddress(request.getAddress());
		dbModel.setAuroraAccountID(request.getAuroraAccountID());
		dbModel.setCountryOfResidence(request.getCountryOfResidence());
		dbModel.setDigitalFootPrint(request.getDigitalFootPrint());
		dbModel.setDobDay(request.getDobDay());
		dbModel.setDobMonth(request.getDobMonth());
		dbModel.setDobYear(request.getDobYear());
		dbModel.setEmail(request.getEmail());
		dbModel.setFamilyNameAtBirth(request.getFamilyNameAtBirth());
		dbModel.setFamilyNameAtCitizenship(request.getFamilyNameAtCitizenship());
		dbModel.setFax(request.getFax());
		dbModel.setGender(request.getGender());
		dbModel.setIpAddress(request.getIpAddress());
		dbModel.setName(request.getName());
		dbModel.setNationality(request.getNationality());
		dbModel.setOrgCode(request.getOrgCode());
		dbModel.setPhone(request.getPhone());
		dbModel.setPlaceOfBirth(request.getPlaceOfBirth());
		dbModel.setSfAccountID(request.getSfAccountID());
		dbModel.setUpdatedBy(request.getUserName());
		dbModel.setUpdateOn(new Timestamp(System.currentTimeMillis())
				.toString());
		dbModel.setIsDeleted("false");
		return dbModel;
	}

	/**
	 * Transform to DB model.
	 *
	 * @param request the request
	 * @return the DB model
	 */
	private DBModel transformToDBModel(CustomerSearchRequestData request) {
		DBModel dbModel = new DBModel();
		dbModel.setAddress(request.getAddress());
		dbModel.setAuroraAccountID(request.getAuroraAccountID());
		dbModel.setCountryOfResidence(request.getCountryOfResidence());
		dbModel.setDigitalFootPrint(request.getDigitalFootPrint());
		dbModel.setDobDay(request.getDobDay());
		dbModel.setDobMonth(request.getDobMonth());
		dbModel.setDobYear(request.getDobYear());
		dbModel.setEmail(request.getEmail());
		dbModel.setFamilyNameAtBirth(request.getFamilyNameAtBirth());
		dbModel.setFamilyNameAtCitizenship(request.getFamilyNameAtCitizenship());
		dbModel.setFax(request.getFax());
		dbModel.setGender(request.getGender());
		dbModel.setIpAddress(request.getIpAddress());
		dbModel.setName(request.getName());
		dbModel.setNationality(request.getNationality());
		dbModel.setPhone(request.getPhone());
		dbModel.setPlaceOfBirth(request.getPlaceOfBirth());
		dbModel.setSfAccountID(request.getSfAccountID());
		return dbModel;
	}

	/**
	 * Transform to DB model.
	 *
	 * @param request the request
	 * @return the DB model
	 */
	private DBModel transformToDBModel(CustomerDataScanDeleteRequest request) {
		DBModel dbModel = new DBModel();
		dbModel.setIsDeleted("true");
		dbModel.setSfAccountID(request.getSfAccountID());
		return dbModel;
	}
}
