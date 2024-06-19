
package com.currenciesdirect.gtg.compliance.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;

import communicationapi.CommunicationAPI;
import communicationapi.CommunicationAPI_Service;
import communicationapi.EmailMessage;
import communicationapi.RequestHeader;
import communicationapi.ResponseStatusHeader;
import communicationapi.SmsMessage;


public class SoapClient implements Serializable {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(SoapClient.class);
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private static SoapClient soapClient = null;

	/** The wss out. */
	private static WSS4JOutInterceptor wssOut;
	
	/** The Constant SERVICE NAME*/
	private static final QName SERVICE_NAME = new QName("urn:CommunicationAPI", "CommunicationAPI");
	
	
	public static SoapClient getInstance() {
		synchronized (SoapClient.class) {
			if (soapClient == null) {
				soapClient = new SoapClient();
			}
		}
		return soapClient;
	}
	
    /**
     * @param serializable 
     * @param requestHeader 
     * @param isSent 
     * @param responseHead 
     * @param port 
     * @return result 
     */
    public Boolean sendEmail(Serializable serializable, 
    		RequestHeader requestHeader, Holder<java.lang.Boolean>
    isSent, Holder<ResponseStatusHeader> responseHead, CommunicationAPI port) {
	   Boolean result;
	   EmailMessage message = (EmailMessage) serializable;
	   try {
			afterPropertiesSet();
			addSecurityHeader(port);
			port.sendSynchronousEmail(requestHeader, message,
					isSent, responseHead);
			result = isSent.value;
			
		} catch (Exception e) {
			result = Boolean.FALSE; 
			LOG.error("Exception in sendEmail ", e);
		}
	   return result;
   }
   /**
 * @param serializable 
 * @param requestHeader 
 * @param isSent 
 * @param responseHead 
 * @param port 
 * @return result 
 */
public Boolean sendSms(Serializable serializable,
		RequestHeader requestHeader, Holder<java.lang.Boolean> isSent ,
		Holder<ResponseStatusHeader> responseHead, CommunicationAPI port) {
		boolean result;
		SmsMessage message = (SmsMessage) serializable;
		try {
			afterPropertiesSet();
			addSecurityHeader(port);
			port.sendSynchronousSMS(requestHeader, message, 
					isSent, responseHead);
			result = isSent.value;
			
			
		}  catch (Exception e) {
			result = false;
			LOG.error("Exception in sendSms ", e);
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	/**
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void afterPropertiesSet() {
		
		ClientPasswordCallback.password =System.getenv(Constants.EAMIL_PWD);
		
		Map ctx = new HashMap();
			ctx.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
			ctx.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
			ctx.put(WSHandlerConstants.USER, System.getenv(Constants.EAMIL_PWD));
			ctx.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName());
			ctx.put(WSHandlerConstants.MUST_UNDERSTAND, "false");
		wssOut = new WSS4JOutInterceptor(ctx);
	}

	

	/**
	 * The Class ClientPasswordCallback.
	 */
	public static class ClientPasswordCallback implements CallbackHandler {
		
		/** The password. */
		private static String password;

		/* (non-Javadoc)
		 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
		 */
		@Override
		public void handle(Callback[] callbacks) throws IOException,
				UnsupportedCallbackException {
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
			pc.setPassword(password);
		}
	}

	/**
	 * Adds the security header.
	 *
	 * @param port the port
	 * @throws Exception the exception
	 */
	private void addSecurityHeader(CommunicationAPI port){
		LOG.debug("addSecurityHeader() STARTED");
		try {
			Client proxy = ClientProxy.getClient(port);
			proxy.getOutInterceptors().add(wssOut);
			HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			httpClientPolicy.setConnectionTimeout(150000);
			httpClientPolicy.setReceiveTimeout(150000);
			conduit.setClient(httpClientPolicy);
		} catch (Exception e) {
			LOG.error(" Error In addSecurityHeader()", e);
		}
	}
	
	
	/**
	 * @param serializable 
	 * @param requestHeader 
	 * @param flowName 
	 * @return response 
	 * @throws MalformedURLException 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public <K> K execute(Serializable serializable, RequestHeader
			requestHeader, String flowName){		
 		LOG.debug("CommHubRequestDispatcher.execute() STARTED");
		
		String baseUrl = System.getProperty("baseEnterpriseUrl");
		 
		String urlForCommHubService = baseUrl+"/es-interface/CommunicationAPI/CommunicationAPI?WSDL";

		K response = null;
		
		URL url = null;
		try {
			url = new URL(urlForCommHubService);
		} catch (MalformedURLException e1) {
			LOG.error(" Error In execute()", e1);
		}
		
  		CommunicationAPI_Service commAPIService = new CommunicationAPI_Service(url, SERVICE_NAME);
		CommunicationAPI port = null;
		
		Service service = Service.create(url, SERVICE_NAME);
		CommunicationAPI communicationAPI = service.getPort(CommunicationAPI.class);
		 
		Holder<ResponseStatusHeader> responseHead = new Holder<>();
		Holder<java.lang.Boolean> isSent = new Holder<>();
		
		try {
			port = commAPIService.getCommunicationAPIPort();
			BindingProvider bp = (BindingProvider) communicationAPI;
			SOAPBinding binding = (SOAPBinding) bp.getBinding();
			binding.setMTOMEnabled(true);
			if (flowName.equals(Constants.SERVICE_FLOWNAME_SMS)) {
				if (serializable != null) {
					 response = (K) sendSms(serializable, requestHeader, isSent, responseHead, port);
				}
			} else if (flowName.equals(Constants.SERVICE_FLOWNAME_EMAIL)) {
				 response = sendSynchronousEmail(serializable, requestHeader, communicationAPI, responseHead, isSent);
			}
		} catch (Exception e) {
			LOG.error("Error in CommHubRequestDispatcher.execute()", e);
		} finally {
			try {
				if (port instanceof Closeable) {
					((Closeable) port).close();
				}
			} catch (Exception ex) {
				LOG.error("Error in CommHubRequestDispatcher.execute()", ex);
			}
		}
		return response;
	}
	/**
	 * @param serializable
	 * @param requestHeader
	 * @param communicationAPI
	 * @param responseHead
	 * @param isSent
	 * @return
	 */
	private <K> K sendSynchronousEmail(Serializable serializable, RequestHeader requestHeader,
			CommunicationAPI communicationAPI, Holder<ResponseStatusHeader> responseHead,
			Holder<java.lang.Boolean> isSent) {
		K response;
		 try{
				afterPropertiesSet();
				communicationAPI.sendSynchronousEmail(requestHeader,
		             (EmailMessage) serializable, isSent, responseHead);
				response = (K) isSent.value;
				
			}  catch (Exception e) {
				response =  (K) Boolean.FALSE; 
				LOG.error("Exception in sendEmail ", e);
			}
		return response;
	}
	
	
}