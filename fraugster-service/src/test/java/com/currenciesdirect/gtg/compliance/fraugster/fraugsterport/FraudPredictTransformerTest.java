package com.currenciesdirect.gtg.compliance.fraugster.fraugsterport;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.response.Feature;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.signup.response.DecisionDrivers;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.signup.response.FraudPredictSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

public class FraudPredictTransformerTest {

	@InjectMocks
	FraudPredictTransformer fraudPredictTransformer;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	public FraugsterSignupContactRequest fraugsterSignupContactRequestMocking() {
		FraugsterSignupContactRequest fraugsterSignupContactRequest = new FraugsterSignupContactRequest();
		fraugsterSignupContactRequest.setEventType("Profile new Registration");
		fraugsterSignupContactRequest.setCustID("432918");
		fraugsterSignupContactRequest.setTransactionID("0201000000304748");
		return fraugsterSignupContactRequest;
	}

	public FraudPredictSignupResponse fraudPredictSignupResponseMocking() {
		FraudPredictSignupResponse response = new FraudPredictSignupResponse();
		response.setPercentageFromThreshold(BigDecimal.valueOf(-97.65));
		response.setFrgTransId("15b39be7-7ff4-463c-9369-08da61d14eef");
		response.setFraudPredictApproved(1.0f);
		response.setMessage("--");
		response.setDecisionDrivers(decisionDriversSignUpMocking());
		response.setErrorCode("--");
		String[] error = { "--", "--" };
		response.setErrors(error);
		response.setStatus("PASS");
		return response;
	}

	public FraudPredictSignupResponse fraudPredictSignupResponseMock() {
		FraudPredictSignupResponse response = new FraudPredictSignupResponse();
		response.setFrgTransId("15b39be7-7ff4-463c-9369-08da61d14eef");
		response.setFraudPredictApproved(1.0f);
		response.setMessage("--");
		response.setDecisionDrivers(decisionDriversSignUpMocking());
		response.setErrorCode("--");
		String[] error = { "--", "--" };
		response.setErrors(error);
		response.setStatus("PASS");
		return response;
	}

	public FraugsterOnUpdateContactResponse fraugsterOnUpdateContactResponseMocking() {
		FraugsterOnUpdateContactResponse fraudDetectionResponse = new FraugsterOnUpdateContactResponse();
		fraudDetectionResponse.setPercentageFromThreshold("-97.65");
		fraudDetectionResponse.setFrgTransId("15b39be7-7ff4-463c-9369-08da61d14eef");
		fraudDetectionResponse.setMessage("--");
		fraudDetectionResponse.setFraugsterApproved("1.0");
		fraudDetectionResponse.setDecisionDrivers(decisionDriversSignUpMocking());
		String[] error = { "--", "--" };
		fraudDetectionResponse.setError(error);
		fraudDetectionResponse.setStatus("PASS");
		return fraudDetectionResponse;
	}

	public FraugsterSignupContactResponse fraudDetectionResponseMocking() {
		FraugsterSignupContactResponse fraudDetectionResponse = new FraugsterSignupContactResponse();
		fraudDetectionResponse.setStatus("PASS");
		fraudDetectionResponse.setScore("");
		fraudDetectionResponse.setErrorDescription("");
		fraudDetectionResponse.setErrorCode("--");
		return fraudDetectionResponse;
	}

	private DecisionDrivers decisionDriversSignUpMocking() {
		DecisionDrivers decisionDrivers = new DecisionDrivers();
		Feature feature = new Feature();
		feature.setValue("Norway");
		decisionDrivers.setCountryOfResidence(feature);
		feature.setValue("Mr");
		decisionDrivers.setTitle(feature);
		feature.setValue("5000-10000");
		decisionDrivers.setTxnValue(feature);
		feature.setValue("Sowerby Bridge");
		decisionDrivers.setAza(feature);
		return decisionDrivers;
	}

	@Test
	public void testTransformFraudPredictSignupResponse() {
		FraugsterSignupContactResponse expectedResult = fraudDetectionResponseMocking();
		try {
			FraugsterSignupContactResponse actualResult = fraudPredictTransformer
					.transformFraudPredictSignupResponse(fraudPredictSignupResponseMocking());
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testTransformUpdateResponse() {
		FraugsterOnUpdateContactResponse expectedResult = fraugsterOnUpdateContactResponseMocking();
		try {
			FraugsterOnUpdateContactResponse actualResult = fraudPredictTransformer.transformUpdateResponse(fraudPredictSignupResponseMocking());
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (FraugsterException e) {
			System.out.println(e);
		}
	}

	

}
