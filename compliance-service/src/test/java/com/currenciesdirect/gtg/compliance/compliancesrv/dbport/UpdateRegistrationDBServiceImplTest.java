package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class UpdateRegistrationDBServiceImplTest {
	
	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;

	@Mock
	AbstractDao abstractDao;
	
	@Autowired
	protected static ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	@Mock
	HttpServletRequest httpRequest;
	
	@Mock
	Message<MessageContext> message;
	
	@InjectMocks
	UpdateRegistrationDBServiceImpl updateRegistrationDBServiceImpl;

	@Before
	public void setUp() {
		try {
			int[] count = { 1 };
			MockitoAnnotations.initMocks(this);
			when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.executeBatch()).thenReturn(count);
			Mockito.when(rs.next()).thenReturn(true);
			Mockito.when(preparedStatement.execute()).thenReturn(true);
			Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
			Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(rs);
			Mockito.when(rs.getInt(1)).thenReturn(50882).thenReturn(50882).thenReturn(0).thenReturn(0);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private String buildETVRequestJson(String fileName) throws IOException {
		Resource jsonFile = resourceLoader.getResource(fileName);
		String jsonData = IOUtils.toString(jsonFile.getInputStream(),"UTF-8");
		return jsonData;
	}

	public Message<MessageContext> getMessageContext() {
		BaseController baseController = new BaseController();
		MessageContext messageContext = new MessageContext();
		String signUpRequestJson;
		try {
			signUpRequestJson = buildETVRequestJson("classpath:NewRegistration.json");
			RegistrationServiceRequest regAccount = JsonConverterUtil.convertToObject(RegistrationServiceRequest.class,
					signUpRequestJson);
			List<Contact> contact = new ArrayList<>();
			regAccount.setOrgId(2);
			regAccount.addAttribute("tradeAccountIdList", new ArrayList<>());
			regAccount.addAttribute("tradeAccountNumberList", new ArrayList<>());
			regAccount.addAttribute("BuyCurrencyId", 16);
			regAccount.addAttribute("SellCurrencyId", 2);
			regAccount.addAttribute("oldContacts", contact);
			regAccount.addAttribute("tradeContactidList", new ArrayList<>());
			regAccount.addAttribute("CountryID", 2);
			Account account = regAccount.getAccount();
			account.setVersion(0);
			account.setId(50882);
			regAccount.setAccount(account);
			List<Contact> contact1 = regAccount.getAccount().getContacts();
			contact1.get(0).setVersion(0);
			contact1.get(0).setId(51754);
			List<String> arrlist = new ArrayList<>();
			arrlist.add("Origin");
			arrlist.add("Authorization");
			arrlist.add("Accept");
			arrlist.add("Accept");
			arrlist.add("Connection");
			arrlist.add("Host");
			arrlist.add("Accept-Language");
			arrlist.add("Accept-Encoding");
			arrlist.add("Accept-Encoding");
			arrlist.add("Content-Type");
			Enumeration<String> headerNames = Collections.enumeration(arrlist);
			List<String> headerlist = new ArrayList<>();
			UserProfile user = new UserProfile();
			user.setName("CD User");
			user.setGivenName("CD");
			user.setEmail("cd_api_user@currenciesdirect.com");
			user.setFamilyName("User");
			user.setPreferredUserName("cd_api_user");
			user.setUserID(18);
			headerlist.add("chrome-extension://eipdnjedkpcnlmmdfdkgfpljanehloah");
			headerlist.add("Bearer ");
			headerlist.add("*/*");
			headerlist.add(
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.106 Safari/537.36");
			headerlist.add("keep-alive");
			headerlist.add("172.31.22.84:8080");
			headerlist.add("en-GB,en-US;q=0.9,en;q=0.8");
			headerlist.add("gzip, deflate");
			headerlist.add("5457");
			headerlist.add("application/json");
			Enumeration<String> header = Collections.enumeration(headerlist);
			when(httpRequest.getHeaderNames()).thenReturn(headerNames);
			when(httpRequest.getHeaders(anyString())).thenReturn(header);
			messageContext = baseController.buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
					ServiceInterfaceType.PROFILE, OperationEnum.NEW_REGISTRATION, "Currencies Direct", regAccount);
			MessageExchange gateWayMessageExchange = messageContext.getMessageCollection().get(0).getMessageExchange();

			Map<String, Object> messageHeaders = new HashMap<>();
			messageHeaders.put(MessageContextHeaders.GATEWAY_REQUEST_RECEIVE_TIME,
					messageContext.getAuditInfo().getCreatedOn());
			messageHeaders.put(MessageContextHeaders.GATEWAY_SERVICE_ID, gateWayMessageExchange.getServiceInterface());
			messageHeaders.put(MessageContextHeaders.GATEWAY_OPERATION, gateWayMessageExchange.getOperation().name());
			messageHeaders.put(MessageContextHeaders.GATEWAY_REQUEST_ORG, messageContext.getOrgCode());
			messageHeaders.put(MessageContextHeaders.GATEWAY_USER, user);
			messageHeaders.put(MessageContextHeaders.OSR_ID, gateWayMessageExchange.getRequest().getOsrId());
			messageHeaders.put("correlationID", UUID.randomUUID().toString());
			Message<MessageContext> message = MessageBuilder.withPayload(messageContext).copyHeaders(messageHeaders)
					.build();

			return message;
		} catch (IOException e) {
			System.out.println(e);
		}
		return message;
	}

	@Test
	public void testUpdateContactAttributesMessageOfMessageContext() {
		Message<MessageContext> actualResult = updateRegistrationDBServiceImpl
				.updateContactAttributes(getMessageContext());
		assertEquals("26b4c045-5b47-42e4-bb2e-7173e30641ea",
				actualResult.getPayload().getMessageCollection().get(0).getMessageExchange().getRequest().getOsrId());
	}

}
