package com.currenciesdirect.gtg.compliance.customchecks.esport;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class ESIndexingProcessor.
 */
public class ESIndexingProcessor {
	 private static final Logger LOG = LoggerFactory.getLogger(ESIndexingProcessor.class);

	/**
	 * @param indexName
	 * @param typeName
	 * @param docId
	 * @param source
	 */
	public void insertDocument(String indexName, String typeName, String docId, String source)throws CustomChecksException {
		try {
			IndexResponse response = ESTransportClient.getInstance().prepareIndex(indexName, typeName, docId)
					.setSource(source).execute().get();
			
			if(response.getShardInfo().getSuccessful() == 0){
				LOG.error("Error creating document: {}",response);
			}
		} catch (Exception ex) {
			LOG.error("Error creating document: ", ex);
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_INSERTING_DATA);
		}
	}

	/**
	 * @param indexName
	 * @param typeName
	 * @param docId
	 * @param source
	 */
	public void updateDocument(String indexName, String typeName, String docId, String source) throws CustomChecksException
	{
		try {
			UpdateResponse response = ESTransportClient.getInstance().prepareUpdate(indexName, typeName, docId)
					.setDoc(source)
					.execute().get();
			if (response.getResult()!=DocWriteResponse.Result.UPDATED) {
				LOG.error("Error Occured: {}",response);
			}
		} catch (Exception ex) {
			LOG.error("Error updating document: ", ex);
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_UPDATING_DATA);
		}
	}
	
	/**
	 * @param indexName
	 * @param typeName
	 * @param docId
	 * @param objectJson
	 */
	public void upsertDocument(String indexName, String typeName, String docId, String objectJson) throws CustomChecksException {
		try {
			IndexRequest indexRequest = new IndexRequest(indexName, typeName, docId).source(objectJson);
			UpdateRequest updateRequest = new UpdateRequest(indexName, typeName, docId).doc(objectJson)
					.upsert(indexRequest);
			UpdateResponse response = ESTransportClient.getInstance().update(updateRequest).get();
			if (!(response.getResult()==DocWriteResponse.Result.CREATED 
				||	response.getResult()==DocWriteResponse.Result.UPDATED
				||	response.getResult()==DocWriteResponse.Result.NOOP)) {
				LOG.error("Error upserting document: {}", response);
			}
		} catch (Exception ex) {
			LOG.error("Error  upserting document: ", ex);
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_UPSERTING_DATA);
		}
	}
	
	
}
