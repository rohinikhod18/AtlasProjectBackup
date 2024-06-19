/**
 * 
 */
package com.currenciesdirect.gtg.compliance.quovaport;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.currenciesdirect.gtg.compliance.core.IpProviderService;
import com.currenciesdirect.gtg.compliance.core.domain.CustomerLocation;
import com.currenciesdirect.gtg.compliance.core.domain.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.IpRequestData;
import com.currenciesdirect.gtg.compliance.exception.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.IpException;

/**
 * @author manish
 * 
 */
public class QouvaPort implements IpProviderService {

	private static IpProviderService ipProviderService = new QouvaPort();

	/** The Constant LOG. */
	static final Logger LOG = org.slf4j.LoggerFactory
			.getLogger(QouvaPort.class);

	private QouvaPort() {

	}

	public static IpProviderService getInstance() {
		return ipProviderService;
	}

	@Override
	public CustomerLocation getCustomerLocationFromIp(IpRequestData ipRequest,
			IpProviderProperty ipProviderProperty) throws IpException {
		String url = null;
		HttpGet httpget = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		CustomerLocation customerLocation = new CustomerLocation();
		try {
			url = generateIpProviderUrl(ipProviderProperty,
					ipRequest.getIpAddress());
			HttpClient httpclient = new DefaultHttpClient();
			// Create an HTTP GET request
			httpget = new HttpGet(url);
			// Execute the request
			httpget.getRequestLine();

			response = httpclient.execute(httpget);

			entity = response.getEntity();
			// Print the response
			System.out.println(response.getStatusLine());
			if (entity != null) {
				customerLocation = transformToRequestObject(entity);
			} else {
				throw new IpException(IpErrors.ERROR_WHILE_CALLING_IP_PROVIDER);
			}

		} catch (IpException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new IpException(IpErrors.ERROR_WHILE_CALLING_IP_PROVIDER, exception);
		}

		return customerLocation;
	}

	private String generateIpProviderUrl(IpProviderProperty ipProviderProperty,
			String ipAddress) throws IpException {
		String url = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			long timeInSeconds = (long) (System.currentTimeMillis() / 1000);
			String input = ipProviderProperty.getApiKey()
					+ ipProviderProperty.getSecret() + timeInSeconds;
			md.update(input.getBytes());

			String sig = String.format("%032x", new BigInteger(1, md.digest()));

			url = ipProviderProperty.getEnpointUrl()
					+ ipProviderProperty.getApiVersion()
					+ ipProviderProperty.getMethodName() + ipAddress
					+ "?apikey=" + ipProviderProperty.getApiKey() + "&sig="
					+ sig;
			LOG.info("*****URL*******" + url);
		} catch (Exception exception) {
			throw new IpException(IpErrors.ERROR_WHILE_GENERATING_PROVIDER_URL,exception);
		}
		return url;
	}

	private CustomerLocation transformToRequestObject(HttpEntity entity)
			throws IpException {
		CustomerLocation customerLocation = new CustomerLocation();
		try {
			InputStream inputStream = entity.getContent();
			// Process the response
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				sb.append(line);
			}
			String res = sb.toString();

			InputSource is = new InputSource();
			Document doc = null;
			is.setCharacterStream(new StringReader(res));
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(is);
			NodeList nList = doc.getElementsByTagName("ipinfo");
			Node nNode = nList.item(0);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				customerLocation.setIp_address(eElement
						.getElementsByTagName("ip_address").item(0)
						.getTextContent());
				customerLocation.setPostal_code(eElement
						.getElementsByTagName("postal_code").item(0)
						.getTextContent());
				customerLocation.setIp_country_code(eElement
						.getElementsByTagName("country_code").item(0)
						.getTextContent());
				customerLocation.setLatitude(eElement
						.getElementsByTagName("latitude").item(0)
						.getTextContent());
				customerLocation.setLongitude(eElement
						.getElementsByTagName("longitude").item(0)
						.getTextContent());
				customerLocation.setIp_country(eElement
						.getElementsByTagName("country").item(0)
						.getTextContent());
				customerLocation.setIp_city(eElement
						.getElementsByTagName("city").item(0).getTextContent());
				customerLocation.setIp_city_cf(eElement
						.getElementsByTagName("city_cf").item(0)
						.getTextContent());
				customerLocation.setIp_routing_type(eElement
						.getElementsByTagName("ip_routing_type").item(0)
						.getTextContent());
				customerLocation.setIp_anonymizer_status(eElement
						.getElementsByTagName("anonymizer_status").item(0)
						.getTextContent());
			}
		} catch (Exception exception) {
			throw new IpException(
					IpErrors.ERROR_WHILE_TRANSFORMING_IP_PROVIDER_RESPONSE,
					exception);
		}
		return customerLocation;

	}

}
