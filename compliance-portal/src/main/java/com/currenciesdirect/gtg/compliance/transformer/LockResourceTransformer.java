package com.currenciesdirect.gtg.compliance.transformer;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceRequest;

/**
 * The Class LockResourceTransformer.
 */
@Component("lockResourceTransformer")
public class LockResourceTransformer implements ITransform<LockResourceDBDto, LockResourceRequest> {

	@Override
	public LockResourceDBDto transform(LockResourceRequest lockResourceRequest) {
		LockResourceDBDto lockResource = new LockResourceDBDto();
		
		lockResource.setId(lockResourceRequest.getUserResourceId());
		lockResource.setUserId(lockResourceRequest.getUser().getName());
		lockResource.setResourceId(lockResourceRequest.getResourceId());
		lockResource.setResourceType(lockResourceRequest.getResourceType());
		lockResource.setCreatedBy(lockResourceRequest.getUser().getName());
		lockResource.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		if (lockResourceRequest.getUserResourceId()==null && lockResourceRequest.getLock()){
			lockResource.setLockReleasedOn(null);
		}else {
			lockResource.setLockReleasedOn(new Timestamp(System.currentTimeMillis()));
		}	
		return lockResource;
	}

}
