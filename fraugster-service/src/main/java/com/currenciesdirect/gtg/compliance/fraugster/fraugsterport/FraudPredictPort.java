package com.currenciesdirect.gtg.compliance.fraugster.fraugsterport;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.fundsin.response.FraudPredictFundsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.fundsout.response.FraudPredictFundsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderOnUpdateSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderPaymentInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderPaymentOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderSignUpRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.signup.response.FraudPredictSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.fraugster.core.IFraudPredictProviderService;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;
import com.currenciesdirect.gtg.compliance.fraugster.util.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author swatij
 *
 */
public class FraudPredictPort implements IFraudPredictProviderService{

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FraudPredictPort.class);

	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler = ResteasyClientHandler.getInstance();

	/** The fraud predict transformer. */
	private FraudPredictTransformer fraudPredictTransformer = FraudPredictTransformer.getInstance();

	/** The i fraugster provider service. */
	private static IFraudPredictProviderService iFraudPredictProviderService = null;

	/**
	 * @return iFraudPredictProviderService
	 */
	public static IFraudPredictProviderService getInstance() {
		if (iFraudPredictProviderService == null) {
			iFraudPredictProviderService = new FraudPredictPort();
		}
		return iFraudPredictProviderService;
	}

	/**
	 * @param request
	 * @param fraugsterProviderProperty
	 * @return fraudDetectionResponse
	 * @throws FraugsterException
	 */
	@Override
	public FraugsterSignupContactResponse doFraudPredictSignupCheck(FraugsterSignupContactRequest request,
			FraugsterProviderProperty fraugsterProviderProperty) throws FraugsterException {
		String jsonProviderSignUpRequest;
		ResteasyClient client = null;
		ProviderSignUpRequest providerSignUpRequest = null;
		Response response = null;
		FraudPredictSignupResponse fraudPredictDataResponse = new FraudPredictSignupResponse();
		FraugsterSignupContactResponse fraudDetectionResponse = new FraugsterSignupContactResponse();
		try {
			LOG.debug("Fraudpredict Port STARTED : doFraudpredictCheck() ");
			/*client = resteasyClientHandler.getRetEasyClient();
			providerSignUpRequest = fraudPredictTransformer.transformFraudPredictSignupRequest(request);
			jsonProviderSignUpRequest = JsonConverterUtil.convertToJsonWithoutNull(providerSignUpRequest);
			LOG.warn(jsonProviderSignUpRequest);
			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request();
			response = clientrequest.post(Entity.entity(providerSignUpRequest, MediaType.APPLICATION_JSON)); */
			
			String json= "{\"frgTransId\":\"ebbf783b-e83c-4259-b22b-58b652fef830\","
					+ "\"score\":\"0.00019658374\",\"fraugsterApproved\":\"1.0\",\"id\":52774,"
					+ "\"status\":\"PASS\",\"percentageFromThreshold\":\"-97.41\","
					+ "\"decisionDrivers\":{\"txn_value\":{\"value\":\"2,000 - 5,000\","
					+ "\"featureImportance\":-0.2272942},\"source\":{\"value\":\"WEB\","
					+ "\"featureImportance\":0.2740615},\"reg_mode\":{\"value\":\"MOBILE\","
					+ "\"featureImportance\":-0.067475386},\"affiliate_name\":{\"value\":"
					+ "\"STP - TEMPORARY AFFILIATE CODE\",\"featureImportance\":-0.5263882},"
					+ "\"ad_campaign\":{\"value\":\"\",\"featureImportance\":-0.04260528},"
					+ "\"channel\":{\"value\":\"MOBILE\",\"featureImportance\":-0.44612977},"
					+ "\"ip_line_speed\":{\"value\":\"\",\"featureImportance\":0.09510705},"
					+ "\"search_engine\":{\"value\":\"\",\"featureImportance\":-0.09082934},"
					+ "\"gender_fullcontact\":{\"value\":\"MALE\",\"featureImportance\":-0.065191016},"
					+ "\"browser_lang\":{\"value\":\"\",\"featureImportance\":-0.11581852},"
					+ "\"screen_resolution\":{\"value\":\"750X1334\",\"featureImportance\":-0.03602964},"
					+ "\"country_of_residence\":{\"value\":\"GBR\",\"featureImportance\":-0.0832874},"
					+ "\"fullcontact_matched\":{\"value\":\"YES\",\"featureImportance\":-0.0848447},"
					+ "\"ip_longitude\":{\"value\":\"\",\"featureImportance\":-1.6565734},\"keywords\":{"
					+ "\"value\":\"\",\"featureImportance\":-0.060239006},\"ip_connection_type\":{\"value"
					+ "\":\"\",\"featureImportance\":-0.24046624},\"device_type\":{\"value\":\"IPHONE\","
					+ "\"featureImportance\":-0.005574821},\"op_country\":{\"value\":\"\","
					+ "\"featureImportance\":0.0},\"residential_status\":{\"value\":\"\","
					+ "\"featureImportance\":0.00016981395},\"address_type\":{\"value\":\"CURRENT ADDRESS"
					+ "\",\"featureImportance\":0.0},\"device_manufacturer\":{\"value\":\"APPLE\","
					+ "\"featureImportance\":0.009507372},\"ip_carrier\":{\"value\":\"\","
					+ "\"featureImportance\":-0.16261506},\"device_name\":{\"value\":\"BNT SOFT IPHONE\","
					+ "\"featureImportance\":-0.028641686},\"ip_anonymizer_status\":{\"value\":\"\","
					+ "\"featureImportance\":-0.033437513},\"ip_routing_type\":{\"value\":\"\","
					+ "\"featureImportance\":-0.046029653},\"eid_status\":{\"value\":\"1\","
					+ "\"featureImportance\":-0.15336347},\"age_range_fullcontact\":{\"value\":"
					+ "\"NONE\",\"featureImportance\":-0.111547545},\"location_country_fullcontact\":{"
					+ "\"value\":\"NONE\",\"featureImportance\":-0.0134919975},\"ip_latitude\":{\"value\":"
					+ "\"\",\"featureImportance\":-1.419807},\"browser_type\":{\"value\":"
					+ "\"\",\"featureImportance\":-0.025675036},\"title\":{\"value\":\"\","
					+ "\"featureImportance\":-0.19128892},\"referral_text\":{\"value\":"
					+ "\"NATURAL\",\"featureImportance\":-0.048767813},\"browser_online\":{"
					+ "\"value\":\"\",\"featureImportance\":0.0023156733},\"device_os_type\":{"
					+ "\"value\":\"APPLE IOS\",\"featureImportance\":-0.060722403},\"sub_source\":{"
					+ "\"value\":\"NATURAL\",\"featureImportance\":-0.27592996},\"browser_version\":{"
					+ "\"value\":\"\",\"featureImportance\":-0.026970118},\"email_domain\":{\"value\":"
					+ "\"GMAIL\",\"featureImportance\":-0.46747407},\"branch\":{\"value\":"
					+ "\"MOORGATE HO\",\"featureImportance\":-0.41487223},\"sanction_status\":{\"value\":"
					+ "\"1\",\"featureImportance\":0.06794018},\"turnover\":{\"featureImportance\":-0.22452144},"
					+ "\"region_suburb\":{\"value\":\"\",\"featureImportance\":-0.407228},"
					+ "\"social_profiles_count\":{\"value\":\"1.0\",\"featureImportance\":-0.17582603},"
					+ "\"state\":{\"value\":\"KEIGHLEY\",\"featureImportance\":-0.48905626}}}";
			
			ObjectMapper objectMapper =new ObjectMapper();
			fraudDetectionResponse = objectMapper.readValue(json, FraugsterSignupContactResponse.class);
			//fraudDetectionResponse = fraudPredictTransformer.transformFraudPredictSignupResponse(fraudPredictDataResponse);
			 LOG.warn("\n -------Fraugster Response Start -------- \n  {}", json);
			 LOG.warn(" \n -----------Fraugster Response End ---------");

		} /*
			 * catch (FraugsterException e) { throw e; }
			 */ catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_SIGNUP, e);
		} finally {

			if (response != null) {
				response.close();
			}
		}
		return fraudDetectionResponse;
	}

	/**
	 * @param response
	 * @return
	 * @throws FraugsterException
	 */
	private FraudPredictSignupResponse handleFraudPredictServerTansactionResponse(Response response)
			throws FraugsterException {

		FraudPredictSignupResponse dataResponse ;

		if (null != response) {
			if (response.getStatus() == 200) {
				dataResponse = response.readEntity(FraudPredictSignupResponse.class);
			} else if (response.getStatus() == 500) {
				throw new FraugsterException(FraugsterErrors.FRAUGSTER_SERVER_GENERIC_EXCEPTION);
			} else if (response.getStatus() == 400) {
				dataResponse = response.readEntity(FraudPredictSignupResponse.class);
			}
			else {
				throw new FraugsterException(FraugsterErrors.FAILED);
			}
		} else {
			throw new FraugsterException(FraugsterErrors.FAILED);
		}
		return dataResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.
	 * IFraudPredictProviderService#doFraudPredictOnUpdateCheck(com.currenciesdirect
	 * .gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest,
	 * com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterProviderProperty)
	 */
	@Override
	public FraugsterOnUpdateContactResponse doFraudPredictOnUpdateCheck(FraugsterOnUpdateContactRequest request,
			FraugsterProviderProperty fraugsterProviderProperty) throws FraugsterException {
		String jsonProviderOnUpdateSignupRequest;
		Response response = null;
		ResteasyClient client = null;
		ProviderOnUpdateSignupRequest providerOnUpdateSignupRequest = null;
		FraudPredictSignupResponse fraudPredictDataResponse = new FraudPredictSignupResponse();
		FraugsterOnUpdateContactResponse fraudDetectionResponse = new FraugsterOnUpdateContactResponse();

		try {

			LOG.debug("Fraudpredict Port STARTED : doFraudpredictCheck() ");
			client = resteasyClientHandler.getRetEasyClient();
			providerOnUpdateSignupRequest = fraudPredictTransformer.transformOnUpdateRequest(request);
			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request();
			jsonProviderOnUpdateSignupRequest = JsonConverterUtil.convertToJsonWithoutNull(providerOnUpdateSignupRequest);
			LOG.warn(jsonProviderOnUpdateSignupRequest);
			response = clientrequest.post(Entity.entity(providerOnUpdateSignupRequest, MediaType.APPLICATION_JSON));
			fraudPredictDataResponse = handleFraudPredictServerTansactionResponse(response);
			fraudDetectionResponse = fraudPredictTransformer.transformUpdateResponse(fraudPredictDataResponse);

		} catch (FraugsterException e) {
			throw e;
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_ONUPDATE, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return fraudDetectionResponse;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.
	 * IFraudPredictProviderService#doFraudPredictPaymentsInCheck(com.
	 * currenciesdirect.gtg.compliance.commons.domain.fraugster.
	 * FraugsterPaymentsInRequest,
	 * com.currenciesdirect.gtg.compliance.commons.domain.fraugster.
	 * FraugsterPaymentsInContactRequest,
	 * com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterProviderProperty)
	 */
	@Override
	public FraugsterPaymentsInContactResponse doFraudPredictPaymentsInCheck(FraugsterPaymentsInRequest request,
			FraugsterPaymentsInContactRequest contact, FraugsterProviderProperty fraugsterProviderProperty)
			throws FraugsterException {
		Response response = null;
		ResteasyClient client = null;
		FraugsterPaymentsInContactResponse fraudDetectionResponse = new FraugsterPaymentsInContactResponse();
		ProviderPaymentInRequest fraudPredictProviderRequest = null;
		FraudPredictFundsInResponse searchResponse = new FraudPredictFundsInResponse();
		try {
			LOG.debug("FraugsterPort STRATED : doFraugsterPaymentsInCheck() ");
			/*client = resteasyClientHandler.getRetEasyClient();
			fraudPredictProviderRequest = fraudPredictTransformer.transformPaymentsInRequest(request, contact);
			ObjectUtils.setNullFieldsToDefault(fraudPredictProviderRequest);
			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request();
			response = clientrequest.post(Entity.entity(fraudPredictProviderRequest, MediaType.APPLICATION_JSON));
			searchResponse = handleFraudPredictFundsInServerTansactionResponse(response);
			fraudDetectionResponse = fraudPredictTransformer.transformPaymentsInResponse(searchResponse);*/
			String json="{\"frgTransId\":\"ebbf783b-e83c-4259-b22b-58b652fef830\","
					+ "\"score\":\"0.00019658374\",\"fraugsterApproved\":\"1.0\",\"id\":52774,"
					+ "\"status\":\"PASS\",\"percentageFromThreshold\":\"-97.41\","
					+ "\"decisionDrivers\":{\"txn_value\":{\"value\":\"2,000 - 5,000\","
					+ "\"featureImportance\":-0.2272942},\"source\":{\"value\":\"WEB\","
					+ "\"featureImportance\":0.2740615},\"reg_mode\":{\"value\":\"MOBILE\","
					+ "\"featureImportance\":-0.067475386},\"affiliate_name\":{\"value\":"
					+ "\"STP - TEMPORARY AFFILIATE CODE\",\"featureImportance\":-0.5263882},"
					+ "\"ad_campaign\":{\"value\":\"\",\"featureImportance\":-0.04260528},"
					+ "\"channel\":{\"value\":\"MOBILE\",\"featureImportance\":-0.44612977},"
					+ "\"ip_line_speed\":{\"value\":\"\",\"featureImportance\":0.09510705},"
					+ "\"search_engine\":{\"value\":\"\",\"featureImportance\":-0.09082934},"
					+ "\"gender_fullcontact\":{\"value\":\"MALE\",\"featureImportance\":-0.065191016},"
					+ "\"browser_lang\":{\"value\":\"\",\"featureImportance\":-0.11581852},"
					+ "\"screen_resolution\":{\"value\":\"750X1334\",\"featureImportance\":-0.03602964},"
					+ "\"country_of_residence\":{\"value\":\"GBR\",\"featureImportance\":-0.0832874},"
					+ "\"fullcontact_matched\":{\"value\":\"YES\",\"featureImportance\":-0.0848447},"
					+ "\"ip_longitude\":{\"value\":\"\",\"featureImportance\":-1.6565734},\"keywords\":{"
					+ "\"value\":\"\",\"featureImportance\":-0.060239006},\"ip_connection_type\":{\"value"
					+ "\":\"\",\"featureImportance\":-0.24046624},\"device_type\":{\"value\":\"IPHONE\","
					+ "\"featureImportance\":-0.005574821},\"op_country\":{\"value\":\"\","
					+ "\"featureImportance\":0.0},\"residential_status\":{\"value\":\"\","
					+ "\"featureImportance\":0.00016981395},\"address_type\":{\"value\":\"CURRENT ADDRESS"
					+ "\",\"featureImportance\":0.0},\"device_manufacturer\":{\"value\":\"APPLE\","
					+ "\"featureImportance\":0.009507372},\"ip_carrier\":{\"value\":\"\","
					+ "\"featureImportance\":-0.16261506},\"device_name\":{\"value\":\"BNT SOFT IPHONE\","
					+ "\"featureImportance\":-0.028641686},\"ip_anonymizer_status\":{\"value\":\"\","
					+ "\"featureImportance\":-0.033437513},\"ip_routing_type\":{\"value\":\"\","
					+ "\"featureImportance\":-0.046029653},\"eid_status\":{\"value\":\"1\","
					+ "\"featureImportance\":-0.15336347},\"age_range_fullcontact\":{\"value\":"
					+ "\"NONE\",\"featureImportance\":-0.111547545},\"location_country_fullcontact\":{"
					+ "\"value\":\"NONE\",\"featureImportance\":-0.0134919975},\"ip_latitude\":{\"value\":"
					+ "\"\",\"featureImportance\":-1.419807},\"browser_type\":{\"value\":"
					+ "\"\",\"featureImportance\":-0.025675036},\"title\":{\"value\":\"\","
					+ "\"featureImportance\":-0.19128892},\"referral_text\":{\"value\":"
					+ "\"NATURAL\",\"featureImportance\":-0.048767813},\"browser_online\":{"
					+ "\"value\":\"\",\"featureImportance\":0.0023156733},\"device_os_type\":{"
					+ "\"value\":\"APPLE IOS\",\"featureImportance\":-0.060722403},\"sub_source\":{"
					+ "\"value\":\"NATURAL\",\"featureImportance\":-0.27592996},\"browser_version\":{"
					+ "\"value\":\"\",\"featureImportance\":-0.026970118},\"email_domain\":{\"value\":"
					+ "\"GMAIL\",\"featureImportance\":-0.46747407},\"branch\":{\"value\":"
					+ "\"MOORGATE HO\",\"featureImportance\":-0.41487223},\"sanction_status\":{\"value\":"
					+ "\"1\",\"featureImportance\":0.06794018},\"turnover\":{\"featureImportance\":-0.22452144},"
					+ "\"region_suburb\":{\"value\":\"\",\"featureImportance\":-0.407228},"
					+ "\"social_profiles_count\":{\"value\":\"1.0\",\"featureImportance\":-0.17582603},"
					+ "\"state\":{\"value\":\"KEIGHLEY\",\"featureImportance\":-0.48905626}}}";
			ObjectMapper objectMapper =new ObjectMapper();
			fraudDetectionResponse = objectMapper.readValue(json, FraugsterPaymentsInContactResponse.class);
			
			String jsonfraudDetection = JsonConverterUtil.convertToJsonWithNull(fraudDetectionResponse);
			 LOG.warn("\n -------Fraugster Response Start -------- \n  {}", jsonfraudDetection);
			 LOG.warn(" \n -----------Fraugster Response End ---------");

				/*
				 * } catch (FraugsterException e) { throw e;
				 */
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_PAYMENTIN, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return fraudDetectionResponse;

	}

	/**
	 * @param response
	 * @return dataResponse
	 * @throws FraugsterException
	 */
	private FraudPredictFundsInResponse handleFraudPredictFundsInServerTansactionResponse(Response response)
			throws FraugsterException {

		FraudPredictFundsInResponse dataResponse;

		if (null != response) {
			if (response.getStatus() == 200) {
				dataResponse = response.readEntity(FraudPredictFundsInResponse.class);
			} else if (response.getStatus() == 500) {
				throw new FraugsterException(FraugsterErrors.FRAUGSTER_SERVER_GENERIC_EXCEPTION);
			} else if (response.getStatus() == 400) {
				dataResponse = response.readEntity(FraudPredictFundsInResponse.class);
			} else {
				throw new FraugsterException(FraugsterErrors.FAILED);
			}
		}else {
                throw new FraugsterException(FraugsterErrors.FAILED);
		}
		return dataResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.
	 * IFraudPredictProviderService#doFraudPredictPaymentsOutCheck(com.
	 * currenciesdirect.gtg.compliance.commons.domain.fraugster.
	 * FraugsterPaymentsOutRequest,
	 * com.currenciesdirect.gtg.compliance.commons.domain.fraugster.
	 * FraugsterPaymentsOutContactRequest,
	 * com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterProviderProperty)
	 */
	@Override
	public FraugsterPaymentsOutContactResponse doFraudPredictPaymentsOutCheck(FraugsterPaymentsOutRequest request,
			FraugsterPaymentsOutContactRequest contact, FraugsterProviderProperty fraugsterProviderProperty)
			throws FraugsterException {
		Response response = null;
		ResteasyClient client = null;
		FraugsterPaymentsOutContactResponse fraudDetectionResponse = new FraugsterPaymentsOutContactResponse();
		ProviderPaymentOutRequest fraudPaymentsOutProviderRequest = null;
		FraudPredictFundsOutResponse dataResponse = new FraudPredictFundsOutResponse();
		try {
			LOG.debug("FraudPredict Port STRATED : doFraudPredictPaymentsOutCheck() ");
			/*client = resteasyClientHandler.getRetEasyClient();
			fraudPaymentsOutProviderRequest = fraudPredictTransformer.transformPaymentsOutRequest(request, contact);
			ResteasyWebTarget target1 = client.target(fraugsterProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request();
			response = clientrequest.post(Entity.entity(fraudPaymentsOutProviderRequest, MediaType.APPLICATION_JSON));
			dataResponse = handleFraudPredictFundsOutTansactionResponse(response);
			fraudDetectionResponse = fraudPredictTransformer.transformPaymentsOutResponse(dataResponse);
		} catch (FraugsterException e) {
			throw e;
		} */
			String json="{\"frgTransId\":\"ebbf783b-e83c-4259-b22b-58b652fef830\","
					+ "\"score\":\"0.00019658374\",\"fraugsterApproved\":\"1.0\",\"id\":52774,"
					+ "\"status\":\"PASS\",\"percentageFromThreshold\":\"-97.41\","
					+ "\"decisionDrivers\":{\"txn_value\":{\"value\":\"2,000 - 5,000\","
					+ "\"featureImportance\":-0.2272942},\"source\":{\"value\":\"WEB\","
					+ "\"featureImportance\":0.2740615},\"reg_mode\":{\"value\":\"MOBILE\","
					+ "\"featureImportance\":-0.067475386},\"affiliate_name\":{\"value\":"
					+ "\"STP - TEMPORARY AFFILIATE CODE\",\"featureImportance\":-0.5263882},"
					+ "\"ad_campaign\":{\"value\":\"\",\"featureImportance\":-0.04260528},"
					+ "\"channel\":{\"value\":\"MOBILE\",\"featureImportance\":-0.44612977},"
					+ "\"ip_line_speed\":{\"value\":\"\",\"featureImportance\":0.09510705},"
					+ "\"search_engine\":{\"value\":\"\",\"featureImportance\":-0.09082934},"
					+ "\"gender_fullcontact\":{\"value\":\"MALE\",\"featureImportance\":-0.065191016},"
					+ "\"browser_lang\":{\"value\":\"\",\"featureImportance\":-0.11581852},"
					+ "\"screen_resolution\":{\"value\":\"750X1334\",\"featureImportance\":-0.03602964},"
					+ "\"country_of_residence\":{\"value\":\"GBR\",\"featureImportance\":-0.0832874},"
					+ "\"fullcontact_matched\":{\"value\":\"YES\",\"featureImportance\":-0.0848447},"
					+ "\"ip_longitude\":{\"value\":\"\",\"featureImportance\":-1.6565734},\"keywords\":{"
					+ "\"value\":\"\",\"featureImportance\":-0.060239006},\"ip_connection_type\":{\"value"
					+ "\":\"\",\"featureImportance\":-0.24046624},\"device_type\":{\"value\":\"IPHONE\","
					+ "\"featureImportance\":-0.005574821},\"op_country\":{\"value\":\"\","
					+ "\"featureImportance\":0.0},\"residential_status\":{\"value\":\"\","
					+ "\"featureImportance\":0.00016981395},\"address_type\":{\"value\":\"CURRENT ADDRESS"
					+ "\",\"featureImportance\":0.0},\"device_manufacturer\":{\"value\":\"APPLE\","
					+ "\"featureImportance\":0.009507372},\"ip_carrier\":{\"value\":\"\","
					+ "\"featureImportance\":-0.16261506},\"device_name\":{\"value\":\"BNT SOFT IPHONE\","
					+ "\"featureImportance\":-0.028641686},\"ip_anonymizer_status\":{\"value\":\"\","
					+ "\"featureImportance\":-0.033437513},\"ip_routing_type\":{\"value\":\"\","
					+ "\"featureImportance\":-0.046029653},\"eid_status\":{\"value\":\"1\","
					+ "\"featureImportance\":-0.15336347},\"age_range_fullcontact\":{\"value\":"
					+ "\"NONE\",\"featureImportance\":-0.111547545},\"location_country_fullcontact\":{"
					+ "\"value\":\"NONE\",\"featureImportance\":-0.0134919975},\"ip_latitude\":{\"value\":"
					+ "\"\",\"featureImportance\":-1.419807},\"browser_type\":{\"value\":"
					+ "\"\",\"featureImportance\":-0.025675036},\"title\":{\"value\":\"\","
					+ "\"featureImportance\":-0.19128892},\"referral_text\":{\"value\":"
					+ "\"NATURAL\",\"featureImportance\":-0.048767813},\"browser_online\":{"
					+ "\"value\":\"\",\"featureImportance\":0.0023156733},\"device_os_type\":{"
					+ "\"value\":\"APPLE IOS\",\"featureImportance\":-0.060722403},\"sub_source\":{"
					+ "\"value\":\"NATURAL\",\"featureImportance\":-0.27592996},\"browser_version\":{"
					+ "\"value\":\"\",\"featureImportance\":-0.026970118},\"email_domain\":{\"value\":"
					+ "\"GMAIL\",\"featureImportance\":-0.46747407},\"branch\":{\"value\":"
					+ "\"MOORGATE HO\",\"featureImportance\":-0.41487223},\"sanction_status\":{\"value\":"
					+ "\"1\",\"featureImportance\":0.06794018},\"turnover\":{\"featureImportance\":-0.22452144},"
					+ "\"region_suburb\":{\"value\":\"\",\"featureImportance\":-0.407228},"
					+ "\"social_profiles_count\":{\"value\":\"1.0\",\"featureImportance\":-0.17582603},"
					+ "\"state\":{\"value\":\"KEIGHLEY\",\"featureImportance\":-0.48905626}}}";
			ObjectMapper objectMapper =new ObjectMapper();
			fraudDetectionResponse = objectMapper.readValue(json, FraugsterPaymentsOutContactResponse.class);
			
			String jsonfraudDetection = JsonConverterUtil.convertToJsonWithNull(fraudDetectionResponse);
			 LOG.warn("\n -------Fraugster Response Start -------- \n  {}", jsonfraudDetection);
			 LOG.warn(" \n -----------Fraugster Response End ---------");
			
		}catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_PAYMENTOUT, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return fraudDetectionResponse;

	}

	/**
	 * @param response
	 * @return dataResponse
	 * @throws FraugsterException
	 */
	private FraudPredictFundsOutResponse handleFraudPredictFundsOutTansactionResponse(Response response)
			throws FraugsterException {

		FraudPredictFundsOutResponse dataResponse ;

		if (null != response) {
			if (response.getStatus() == 200) {
				dataResponse = response.readEntity(FraudPredictFundsOutResponse.class);
			} else if (response.getStatus() == 500) {
				throw new FraugsterException(FraugsterErrors.FRAUGSTER_SERVER_GENERIC_EXCEPTION);
			} else if (response.getStatus() == 400) {
				dataResponse = response.readEntity(FraudPredictFundsOutResponse.class);
			}else {
				throw new FraugsterException(FraugsterErrors.FAILED);
			}
		}else{
			throw new FraugsterException(FraugsterErrors.FAILED);
		}
		return dataResponse;
	}

}
