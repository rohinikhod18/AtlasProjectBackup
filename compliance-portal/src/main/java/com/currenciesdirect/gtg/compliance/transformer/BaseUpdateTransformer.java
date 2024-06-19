package com.currenciesdirect.gtg.compliance.transformer;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;

public class BaseUpdateTransformer {
	
	/**
	 * @param request
	 * @param addReasons
	 * @param deletedReasons
	 * @param previousReason
	 */
	protected void handlePaymentStatusReasonUpdateRequest(StatusReasonUpdateRequest[] request,
			List<String> addReasons, List<String> deletedReasons, List<String> previousReason) {
		
		for (StatusReasonUpdateRequest reason : request) {

			if (reason.getName() == null || reason.getName().isEmpty()
					|| reason.getPreValue().equals(reason.getUpdatedValue())) {
				if(reason.getPreValue() && reason.getUpdatedValue()){
					previousReason.add(reason.getName());
				}
				continue;
			}

			if (Boolean.TRUE.equals(reason.getPreValue()) && Boolean.FALSE.equals(reason.getUpdatedValue())) {
				deletedReasons.add(reason.getName());
			} else {
				addReasons.add(reason.getName());
			}
		}
	}

	/**
	 * @param request
	 * @param addWatchlist
	 * @param deletedWatchlist
	 */
	protected void handleWatchlistUpdateRequest(WatchlistUpdateRequest[] request, List<String> addWatchlist,
			List<String> deletedWatchlist) {
		
		for (WatchlistUpdateRequest watchlist : request) {
			boolean preVal = false;
			boolean updatedVal= false;
			if(watchlist.getPreValue()!=null){
				preVal = watchlist.getPreValue();
			}
			if(watchlist.getUpdatedValue()!=null){
				updatedVal = watchlist.getUpdatedValue();
			}
			if (watchlist.getName() == null || watchlist.getName().isEmpty()
					|| (preVal == updatedVal)) {
				continue;
			}

			if (preVal && !updatedVal) {
				deletedWatchlist.add(watchlist.getName());
			} else {
				addWatchlist.add(watchlist.getName());
			}
		}
		
	}
	
	/**
	 * @param request
	 * @return name
	 */
	protected String getUserName(BaseUpdateRequest request){ 
		if(request.getUser() != null){
			return request.getUser().getName();
		}
		return null;
	}
}
