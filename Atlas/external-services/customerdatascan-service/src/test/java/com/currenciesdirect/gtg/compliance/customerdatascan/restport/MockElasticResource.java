package com.currenciesdirect.gtg.compliance.customerdatascan.restport;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.currenciesdirect.gtg.compliance.customerdatascan.dbport.DBModel;


@Path("/")
public class MockElasticResource {

	public static final String INSERT_SUCCESS = "{\"_index\": \"whitelist\",\"_type\": \"customer\",\"_id\": \"{id}\",\"_version\": 1,\"_shards\": {\"total\": 2,\"successful\": 1,\"failed\": 0},\"created\": true}";
	public static final String INSERT_ERROR	 = "{\"error\":{\"root_cause\":[{\"type\":\"document_already_exists_exception\",\"reason\":\"[customer][{id}]: document already exists\",\"index\":\"whitelist\",\"shard\":\"2\"}],\"type\":\"document_already_exists_exception\",\"reason\":\"[customer][4]: document already exists\",\"index\":\"whitelist\",\"shard\":\"2\"},\"status\":409}";
	public static final String UPDATE_SUCCESS = "{\"_index\":\"whitelist\",\"_type\":\"customer\",\"_id\":\"{id}\",\"_version\":2,\"_shards\":{\"total\":2,\"successful\":1,\"failed\":0}}";
	public static final String UPDATE_ERROR = "{\"error\":{\"root_cause\":[{\"type\":\"document_missing_exception\",\"reason\":\"[customer][{id}]: document missing\",\"index\":\"whitelist\",\"shard\":\"1\"}],\"type\":\"document_missing_exception\",\"reason\":\"[customer][100]: document missing\",\"index\":\"whitelist\",\"shard\":\"1\"},\"status\":404}";
	
	@PUT
	@Path("whitelist/customer/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveIntoMain(@PathParam("id")String id ,DBModel request){
		String success = null ;
		if(id.equals("2")){
			success = new String(INSERT_ERROR);
		} else {
			success = new String(INSERT_SUCCESS);
		}
		success = success.replace("{id}",id);
		return  Response.status(Status.OK).entity(success).build();
	}
	
	@POST
	@Path("whitelisthistory/customer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveIntoHistory(String request){
		String success= new String(INSERT_SUCCESS);
		 success= success.replace("{id}","AWXC12ARF4");
		return  Response.status(Status.OK).entity(success).build();
	}
	
	@POST
	@Path("whitelist/customer/{id}/_update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateIntoMain(@PathParam("id")String id ,String request){
		String success = null ;
		if(id.equals("2")){
			success = new String(UPDATE_ERROR);
		} else {
			success = new String(UPDATE_SUCCESS);
		}
		 success= success.replace("{id}",id);		
		 return  Response.status(Status.OK).entity(success).build();
	}
	
}
