package com.currenciesdirect.gtg.compliance.customerdatascan.restport;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanDeleteRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanUpdateRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerInsertRequestData;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.ResponseStatus;



public class RestPortTest {

    public static CustomerDataScanRestPort restPort = new CustomerDataScanRestPort();
    public static MockElasticResource elasticPort = new MockElasticResource();
    public static RestServer server;
   
    @BeforeClass
    public static void beforeClass() throws Exception {
    	int port = System.getProperty("elasticsearch.port") != null ? Integer.parseInt(System.getProperty("elasticsearch.port")) : 8081;
        server = RestServer.create(port,restPort,elasticPort);
     }
    @AfterClass
    public static void afterClass() throws Exception {
        server.close();
    }
	
	@Test
	public void testDuplicateInsertRequest(){
		CustomerDataScanInsertRequest request = new CustomerDataScanInsertRequest();
		CustomerInsertRequestData insertData = new CustomerInsertRequestData();
		insertData.setSfAccountID("2");
		insertData.setAuroraAccountID("2");
		List<CustomerInsertRequestData> insertDataList = new ArrayList<>();
		insertDataList.add(insertData);
		request.setSave(insertDataList);
        Response response = server.newRequest("/customer/save").request().buildPost(Entity.entity(request, MediaType.APPLICATION_JSON)).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        CustomerDataScanInsertResponse myModel = response.readEntity(CustomerDataScanInsertResponse.class);
        assertEquals(ResponseStatus.FAILED.getStatus(), myModel.getStatus());

	}
	
	@Test
	public void testInsertRequest(){
		CustomerDataScanInsertRequest request = new CustomerDataScanInsertRequest();
		CustomerInsertRequestData insertData = new CustomerInsertRequestData();
		insertData.setSfAccountID("1");
		insertData.setAuroraAccountID("2");
		List<CustomerInsertRequestData> insertDataList = new ArrayList<>();
		insertDataList.add(insertData);
		request.setSave(insertDataList);
        Response response = server.newRequest("/customer/save").request().buildPost(Entity.entity(request, MediaType.APPLICATION_JSON)).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        CustomerDataScanInsertResponse myModel = response.readEntity(CustomerDataScanInsertResponse.class);
        assertEquals(ResponseStatus.SUCCESS.getStatus(), myModel.getStatus());
	}
	
  @Test
	public void testUpdateRequest(){
		CustomerDataScanUpdateRequest request = new CustomerDataScanUpdateRequest();
    	request.setAuroraAccountID("auroraId");
    	request.setSfAccountID("1");
        Response response = server.newRequest("/customer/update").request().buildPost(Entity.entity(request, MediaType.APPLICATION_JSON)).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        CustomerDataScanResponse myModel = response.readEntity(CustomerDataScanResponse.class);
        assertEquals(ResponseStatus.SUCCESS.getStatus(), myModel.getStatus());
	}
  
  @Test
	public void testUpdateMissingRecordRequest(){
		CustomerDataScanUpdateRequest request = new CustomerDataScanUpdateRequest();
    	request.setAuroraAccountID("auroraId");
    	request.setSfAccountID("2");
        Response response = server.newRequest("/customer/update").request().buildPost(Entity.entity(request, MediaType.APPLICATION_JSON)).invoke();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        CustomerDataScanResponse myModel = response.readEntity(CustomerDataScanResponse.class);
        assertEquals(ResponseStatus.FAILED.getStatus(), myModel.getStatus());
	}
	
  @Test
	public void testDeleteRequest(){
		CustomerDataScanDeleteRequest request = new CustomerDataScanDeleteRequest();
    	request.setSfAccountID("1");
        Response response = server.newRequest("/customer/delete").request().buildPost(Entity.entity(request, MediaType.APPLICATION_JSON)).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        CustomerDataScanResponse myModel = response.readEntity(CustomerDataScanResponse.class);
        assertEquals(ResponseStatus.SUCCESS.getStatus(), myModel.getStatus());
	} 
  
  @Test
	public void testDeleteMissingRecordRequest(){
		CustomerDataScanDeleteRequest request = new CustomerDataScanDeleteRequest();
    	request.setSfAccountID("2");
        Response response = server.newRequest("/customer/delete").request().buildPost(Entity.entity(request, MediaType.APPLICATION_JSON)).invoke();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        CustomerDataScanResponse myModel = response.readEntity(CustomerDataScanResponse.class);
        assertEquals(ResponseStatus.FAILED.getStatus(), myModel.getStatus());
	}
	
}
