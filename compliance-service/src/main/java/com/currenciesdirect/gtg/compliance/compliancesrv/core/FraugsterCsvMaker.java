package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;



public class FraugsterCsvMaker  {
	@Autowired
	@Qualifier("fraugsterUploadDBServiceImpl")
	private IFraugsterUploadDBService ifraugsterUploadDBService;
	
	public Message<String> createCsv(Message<?> message) throws ComplianceException{
		Map<String,Object> headers = message.getHeaders();
		Map<String,Object> newHeader = new HashMap<>();
		newHeader.putAll(headers);
		newHeader.put("ids", "id");
		List<Integer> idlist=new ArrayList<>();
		String fragusterCsv=ifraugsterUploadDBService.getFraugster(idlist);	
		newHeader.put("ids", idlist);
		return MessageBuilder.withPayload(fragusterCsv).copyHeaders(newHeader).build();
	}
	
}
