package com.currenciesdirect.gtg.compliance.customerdatascan.dbport;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.IDBService;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanDeleteRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanUpdateRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerInsertRequestData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerInsertResponseData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerSearchRequestData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerSearchResponseData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.IRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanErrors;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;

/**
 * The Class DBServiceImpl.
 * 
 * @author Rajesh Shinde
 */
public class DBServiceImpl implements IDBService {
	static final Logger LOG = LoggerFactory.getLogger(DBServiceImpl.class);
	private static volatile IDBService iDocumentSearch;
	private ITransformer<DBModel, IRequest> transformToElasticModel;
	private static final String DEFAULT_BASE_PATH = "http://localhost:8081/";

	private DBServiceImpl() {
	}

	public static IDBService getInstance() {
		if (iDocumentSearch == null) {
			synchronized (DBServiceImpl.class) {
				if (iDocumentSearch == null) {
					iDocumentSearch = new DBServiceImpl();
				}
			}
		}
		return iDocumentSearch;
	}

	@Override
	public CustomerDataScanInsertResponse saveDocument(CustomerDataScanInsertRequest document)
			throws CustomerDataScanException {
		LOG.info("DBServiceImpl.saveDocument() :start");
		boolean isSuccess = true;
		String response = "";
		List<CustomerInsertResponseData> saveResponseDataList = new CopyOnWriteArrayList<>();
		try {
			for (CustomerInsertRequestData saveData : document.getSave()) {
				saveData.setUserName(document.getUserName());
				CustomerInsertResponseData saveResponseData;
				transformToElasticModel = new DBModelTransformer();
				String url = getBaseUrl()
						+ DBConstants.WHITELIST_CREATE_URL.replaceWithSfAccountId(saveData.getSfAccountID());
				DBModel model = transformToElasticModel.transform(saveData);
				response = RestRequestHandler.getInstance().sendRequest(url, DBConstants.PUT.getValue(), model,
						String.class);
				saveResponseData = parseSaveResponse(response, model);
				if (ResponseStatus.FAILED.getStatus().equals(saveResponseData.getStatus())) {
					isSuccess = false;
				}
				saveResponseData.setId(saveData.getId());

				saveResponseDataList.add(saveResponseData);
			}
			CustomerDataScanInsertResponse customerSaveRsponse = new CustomerDataScanInsertResponse();
			customerSaveRsponse.setSave(saveResponseDataList);
			if (isSuccess) {
				customerSaveRsponse.setStatus(ResponseStatus.SUCCESS.getStatus());
			} else {
				customerSaveRsponse.setStatus(ResponseStatus.FAILED.getStatus());
			}

			return customerSaveRsponse;

		} catch (CustomerDataScanException customerDataScanException) {
			throw customerDataScanException;
		} catch (Exception e) {
			throw new CustomerDataScanException(CustomerDataScanErrors.ERROR_WHILE_SENDING_REQUEST, e);
		}

	}

	@Override
	public CustomerDataScanSearchResponse searchDocument(CustomerDataScanSearchRequest document)
			throws CustomerDataScanException {
		LOG.info("DBServiceImpl.searchDocument() :start");
		CustomerDataScanSearchResponse searchResponse = new CustomerDataScanSearchResponse();
		List<CustomerSearchResponseData> searchDataList = new CopyOnWriteArrayList<>();
		ITransformer<CustomerSearchResponseData, CustomerSearchRequestData> transformResponse = null;
		boolean isSuccess = false;
		String request = null;
		String url = null;
		String response = null;
		try {
			url = getBaseUrl() + DBConstants.WHITELIST_SEARCH_URL.getValue();
			for (CustomerSearchRequestData search : document.getSearch()) {
				CustomerSearchResponseData responseData;
				SearchRequestGenerator generator = new SearchRequestGenerator();
				transformToElasticModel = new DBModelTransformer();
				request = generator.getElasticSearchRequest(transformToElasticModel.transform(search));
				response = RestRequestHandler.getInstance().sendRequest(url, DBConstants.POST.getValue(), request,
						String.class);
				transformResponse = new CustomerSearchResponseTransformer(response);
				responseData = transformResponse.transform(search);
				isSuccess = responseData.getStatus().equals(ResponseStatus.SUCCESS.getStatus()) ? true : false;
				searchDataList.add(responseData);
			}
			if(isSuccess){
				searchResponse.setStatus(ResponseStatus.SUCCESS.getStatus());
			} else {
				searchResponse.setStatus(ResponseStatus.FAILED.getStatus());
			}
			searchResponse.setSearch(searchDataList);

		} catch (CustomerDataScanException customerDataScanException) {
			throw customerDataScanException;
		} catch (Exception e) {
			throw new CustomerDataScanException(CustomerDataScanErrors.ERROR_WHILE_SENDING_REQUEST, e);
		}
		return searchResponse;
	}

	@Override
	public CustomerDataScanResponse updateDocument(CustomerDataScanUpdateRequest document)
			throws CustomerDataScanException {
		LOG.info("DBServiceImpl.updateDocument() :start");
		transformToElasticModel = new DBModelTransformer();
		String response = "";
		String request = null;
		try {
			String url = getBaseUrl()
					+ DBConstants.WHITELIST_UPDATE_URL.replaceWithSfAccountId(document.getSfAccountID());
			UpdateRequestGenerator generator = new UpdateRequestGenerator();
			DBModel model = transformToElasticModel.transform(document);
			request = generator.getElasticSearchUpdateRequest(model);
			response = RestRequestHandler.getInstance().sendRequest(url, DBConstants.POST.getValue(), request,
					String.class);
			return parseUpdateResponse(response, model);
		} catch (CustomerDataScanException customerDataScanException) {
			throw customerDataScanException;
		} catch (Exception e) {
			throw new CustomerDataScanException(CustomerDataScanErrors.ERROR_WHILE_SENDING_REQUEST, e);
		}
	}

	@Override
	public CustomerDataScanResponse deleteDocument(CustomerDataScanDeleteRequest document)
			throws CustomerDataScanException {
		LOG.info("DBServiceImpl.deleteDocument() :start");
		transformToElasticModel = new DBModelTransformer();
		String response = "";
		String request = null;
		try {
			String url = getBaseUrl()
					+ DBConstants.WHITELIST_DELETE_URL.replaceWithSfAccountId(document.getSfAccountID());
			UpdateRequestGenerator generator = new UpdateRequestGenerator();
			DBModel model = transformToElasticModel.transform(document);
			request = generator.getElasticSearchUpdateRequest(model);
			response = RestRequestHandler.getInstance().sendRequest(url, DBConstants.POST.getValue(), request,
					String.class);
			return parseDeleteResponse(response, model);
		} catch (CustomerDataScanException customerDataScanException) {
			throw customerDataScanException;
		} catch (Exception e) {
			throw new CustomerDataScanException(CustomerDataScanErrors.ERROR_WHILE_SENDING_REQUEST, e);
		}
	}

	private String getProperty(String propertyName) {
		String value = System.getProperty(propertyName);
		return (value != null) ? value.trim() : null;
	}

	private boolean saveIntoHistory(DBModel model, int version, String index) throws CustomerDataScanException {
		try {
			JSONObject jsonObject = new JSONObject(model);
			jsonObject.put(DBConstants.HISTORY_VERSION.getValue(), version);
			jsonObject.remove("updatedBy");
			jsonObject.remove("updateOn");
			String url = getBaseUrl() + index + "history/customer";
			String response = RestRequestHandler.getInstance().sendRequest(url, DBConstants.POST.getValue(),
					jsonObject.toString(), String.class);
			jsonObject = new JSONObject(response);
			if (jsonObject.getBoolean(DBConstants.CREATED.getValue())) {
				return true;
			}
		} catch (Exception exception) {
			throw new CustomerDataScanException(CustomerDataScanErrors.ERROR_WHILE_SENDING_REQUEST, exception);
		}

		return false;
	}

	private String getBaseUrl() {
		String baseUrl = new String(DEFAULT_BASE_PATH);
		if (getProperty("elasticsearch.ip") != null) {
			baseUrl = baseUrl.replace("localhost", getProperty("elasticsearch.ip"));
		}
		if (getProperty("elasticsearch.port") != null) {
			baseUrl = baseUrl.replace("8081", getProperty("elasticsearch.port"));
		}
		return baseUrl;
	}

	private CustomerInsertResponseData parseSaveResponse(String response, DBModel model)
			throws JSONException, CustomerDataScanException {
		JSONObject object = new JSONObject(response);
		CustomerInsertResponseData saveResponseData = new CustomerInsertResponseData();
		if (object.has(DBConstants.CREATED.getValue()) && object.getBoolean(DBConstants.CREATED.getValue())) {
			if (saveIntoHistory(model, object.getInt(DBConstants.VERSION.getValue()),
					DBConstants.WHITELIST_INDEX.getValue()))
				saveResponseData.setStatus(ResponseStatus.SUCCESS.getStatus());
		} else if (object.has(DBConstants.ERROR.getValue())
				&& object.getJSONObject(DBConstants.ERROR.getValue()).has(DBConstants.TYPE.getValue())
				&& DBConstants.DOCUMENT_ALREADY_EXIST_EXCEPTION.getValue()
						.equals(object.getJSONObject(DBConstants.ERROR.getValue()).get(DBConstants.TYPE.getValue()))) {
			saveResponseData.setStatus(ResponseStatus.FAILED.getStatus());
			saveResponseData
					.setErrorCode(CustomerDataScanErrors.ERROR_WHILE_INSERT_REQUEST_ALREADY_PRESENT.getErrorCode());
			saveResponseData.setErroDesciption(
					CustomerDataScanErrors.ERROR_WHILE_INSERT_REQUEST_ALREADY_PRESENT.getErrorDescription());
		} else {
			saveResponseData.setStatus(ResponseStatus.FAILED.getStatus());
			saveResponseData.setErrorCode(CustomerDataScanErrors.FAILED.getErrorCode());
			saveResponseData.setErroDesciption(CustomerDataScanErrors.FAILED.getErrorDescription());
		}
		return saveResponseData;
	}

	private CustomerDataScanResponse parseDeleteResponse(String response, DBModel model)
			throws JSONException, CustomerDataScanException {
		CustomerDataScanResponse deleteResponse = new CustomerDataScanResponse();
		JSONObject object = new JSONObject(response);
		if (object.has(DBConstants.VERSION.getValue())) {
			if (saveIntoHistory(model, object.getInt(DBConstants.VERSION.getValue()),
					DBConstants.WHITELIST_INDEX.getValue()))
				deleteResponse.setStatus(ResponseStatus.SUCCESS.getStatus());
		} else if (object.has(DBConstants.ERROR.getValue())
				&& object.getJSONObject(DBConstants.ERROR.getValue()).has(DBConstants.TYPE.getValue())
				&& DBConstants.DOCUMENT_MISSING_EXCEPTION.getValue()
						.equals(object.getJSONObject(DBConstants.ERROR.getValue()).get(DBConstants.TYPE.getValue()))) {
			throw new CustomerDataScanException(CustomerDataScanErrors.ERROR_WHILE_DELETE_REQUEST_NOT_FOUND);
		} else {
			deleteResponse.setStatus(ResponseStatus.FAILED.getStatus());
		}
		return deleteResponse;
	}

	private CustomerDataScanResponse parseUpdateResponse(String response, DBModel model)
			throws JSONException, CustomerDataScanException {
		CustomerDataScanResponse updateResponse = new CustomerDataScanResponse();
		JSONObject object = new JSONObject(response);
		if (object.has(DBConstants.VERSION.getValue())) {
			if (saveIntoHistory(model, object.getInt(DBConstants.VERSION.getValue()),
					DBConstants.WHITELIST_INDEX.getValue()))
				updateResponse.setStatus(ResponseStatus.SUCCESS.getStatus());
		} else if (object.has(DBConstants.ERROR.getValue())
				&& object.getJSONObject(DBConstants.ERROR.getValue()).has(DBConstants.TYPE.getValue())
				&& DBConstants.DOCUMENT_MISSING_EXCEPTION.getValue()
						.equals(object.getJSONObject(DBConstants.ERROR.getValue()).get(DBConstants.TYPE.getValue()))) {
			throw new CustomerDataScanException(CustomerDataScanErrors.ERROR_WHILE_UPDATE_REQUEST_NOT_FOUND);
		} else {
			updateResponse.setStatus(ResponseStatus.FAILED.getStatus());
		}
		return updateResponse;
	}

}
