var REG_QUQUE_PAGE_SIZE = 50;
var currentPage=1;
var regDetailsViewUrl = '#';

$(document).ready(function() {
	onSubNav('holistic-sub-nav');
	setFilterFieldsAsPerCriteria();
});

/**added to enable filter button on press of Enter key when search criteria is selected in filter */
addEventListener("keydown", function(event) {
if (event.keyCode === 13) {
    $("#reg_report_Filter").trigger('click');
    event.preventDefault();
}
});

function applyRegQueueSearchCriteria() {
	$('#gifloaderforregreport').css('visibility','visible');
	currentPage = 1;
	$('#currentpage').val(currentPage);
	
	/**
	 * Apply validation for keyword as keyword must contain colon(:)
	 */
	setFilterApply(true);
	setRequestOrigin(true);
	var request = getQueueSearchCriteriaObject();
	postRegistrationQueueAjax(request,true);

}

function postRegistrationQueueAjax(request,isFilterReq) {
	disableAllPaginationBlock();
	disableButton('reg_report_Filter');
	$.ajax({
		url : '/compliance-portal/regReportCriteria',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data, status, jqXHR) {
			$('#gifloaderforregreport').css('visibility','hidden');
			enableButton('reg_report_Filter');
			setRegQueueResponse(data, status, jqXHR,isFilterReq);			
		},
		error : function(data, status, jqXHR) {
			$('#gifloaderforregreport').css('visibility','hidden');
			enableButton('reg_report_Filter');
			setRegQueueErrorResponse(getJsonObject(data), status, jqXHR);
			enableAllPaginationBloack();
		}
	});
}

function setRegQueueResponse(response, status, jqXHR,isFilterReq) {
	var rows = '';
	 removeRegQueueData();
	 $.each(response.registrationQueue, function(index, registration) {
		 rows += getRegQueueRow(registration,index+1,response.user);
		});
	
	 $("#regQueueBody").append(rows);
	 var user = getJsonObject($("#userInfo").val());
	 if(!user.permissions.canViewRegistrationDetails){
			$('#regQueueBody a').removeAttr('onclick');
		}
	 if(response.page !== undefined && response.page !== null){
		 setQueuePageObjectText(response.page,isFilterReq);
		 $("#currentpage").val(response.page.currentPage);
		 enableAllPaginationBloack();
		 //to display total records found after applying filter neelesh pant
		if (isFilterReq) {
			getTotalRecordsAfterApplyingFilter();
			$('html, body').animate({scrollTop: '0px'}, 0);
		}
		if(response.page.totalPages > 1) {
			$("#paginationBlock").css('display','block');
		}
		$('#pageCountDetails').css('display','block');
	 } else {
		 	$("#paginationBlock").css('display','none');
			$('#pageCountDetails').css('display','none');
			$("#queueTotalRecords").text("0");
	 }
	 $("#searchCriteriaQueue").val(response.searchCriteria);
	 $("#isFromDetails").val(response.isFromDetails);
	 var keywordInput = $("input[name=keyword]");
	 var keywordValue = $(keywordInput).val();
	 if(keywordValue !== undefined && isMatchToRegEx('^[0-9]{6}$',keywordValue)) {
		 clearFiltersExceptKeywordSearch();
	 }
	 initUserLockedPopup();
	 
	 if(!response.user.permissions.canViewRegistrationDetails||response.user.permissions.isReadOnlyUser){
			
			$('#regQueueBody a').addClass('removeHyperLink');
	 }
	 
}

function setRegQueueErrorResponse(response, status, jqXHR) {

}

function removeRegQueueData() {
	$("#regQueueBody").empty();
}

function getRegQueueRow(registration,rowNumber,user) {
	var conId = registration.contactId;
	var accId = registration.accountId;
	var row;
	if(registration.locked && registration.lockedBy === user.name){
		row = '<tr class="owned talign" data-ot="You own(s) this record">';
	} else if (registration.locked){
		row = '<tr class="talign unavailable" data-ot="'+registration.lockedBy+' own(s) this record">';
	} else {
		row='<tr class="talign">';
	}
	
	row += getRowNumber(rowNumber,conId, getEmptyIfNull(registration.type), accId);
	row += getRegQueuClientNumberColumn(conId,getEmptyIfNull(registration.tradeAccountNum), getEmptyIfNull(registration.type), accId);
	row += getRegQueuClientNameColumn(conId,getEmptyIfNull(registration.contactName), getEmptyIfNull(registration.type), accId);
	row += getRegQueueTypeColumn(conId,getEmptyIfNull(registration.type), accId);
	row += getRegQueueCountryOfResidenceColumn(conId,getEmptyIfNull(registration.countryOfResidence), getEmptyIfNull(registration.type), accId);
	row += getRegQueueOrganizationColumn(conId,getEmptyIfNull(registration.organisation), getEmptyIfNull(registration.type), accId);
	row += getRegQueueRegisteredOnColumn(conId,getEmptyIfNull(registration.registeredOn), getEmptyIfNull(registration.type), accId);
	row += '</tr>';
	return row;
}

function getRowNumber(rowNumber, contactId, custType, accountId) {
	var regDetailsFunctionName = 'getHolisticViewDetails('+contactId+',\''+custType+'\',this, '+accountId+');'
	return '<td hidden="hidden"><a onclick="'+regDetailsFunctionName+'" >'+rowNumber+'</a></td>';
}

function getRegQueuClientNumberColumn(contactId, clientNumber, custType, accountId) {
	clientNumber = (clientNumber === null) ? '' : clientNumber;
	var regDetailsFunctionName = 'getHolisticViewDetails('+contactId+',\''+custType+'\',this, '+accountId+');'
	return '<td class="number rowColour"><a onclick="'+regDetailsFunctionName+'" >'+clientNumber+'</a></td>';
}

function getRegQueueRegisteredOnColumn(contactId, regOn, custType, accountId) {
	regOn = (regOn === null) ? '' : regOn;
	var regDetailsFunctionName = 'getHolisticViewDetails('+contactId+',\''+custType+'\',this, '+accountId+');'
	return '<td class="nowrap rowColour"><a onclick="'+regDetailsFunctionName+'" >'+regOn+'</a></td>';
}

function getRegQueuClientNameColumn(contactId,clientName, custType, accountId) {
	var regDetailsFunctionName = 'getHolisticViewDetails('+contactId+',\''+custType+'\',this, '+accountId+');'
	return '<td class="wordwrap rowColour"><a onclick="'+regDetailsFunctionName+'" >'+clientName+'</a></td>';
}

function getRegQueueTypeColumn(contactId, type, accountId) {
	var regDetailsFunctionName = 'getHolisticViewDetails('+contactId+',\''+type+'\',this, '+accountId+');'
	return '<td class="nowrap rowColour"><a onclick="'+regDetailsFunctionName+'" >'+type+'</a></td>';
}

function getRegQueueCountryOfResidenceColumn(contactId, countryOfResidence, custType, accountId) {
	var regDetailsFunctionName = 'getHolisticViewDetails('+contactId+',\''+custType+'\',this, '+accountId+');'
	return '<td><a '+regDetailsFunctionName+' >'+countryOfResidence+'</a></td>';
}

function getRegQueueOrganizationColumn(contactId , organisation, custType, accountId) {
	var regDetailsFunctionName = 'getHolisticViewDetails('+contactId+',\''+custType+'\',this, '+accountId+');'
	return '<td><a '+regDetailsFunctionName+' >'+organisation+'</a></td>';
}

function getHolisticViewDetails(contactId,custType,anchor,accountId) {
	/** set true if Origin is registration report queue page*/
	setRequestOrigin(true);
	var searchCriteria = getQueueSearchCriteriaObject();
	var columnIndex = $(anchor).parent().parent().index()+1;
	addField("currentRecord",setCurrentRecord(searchCriteria.page.minRecord,columnIndex),searchCriteria.page);
	$('#custType').val(custType);
	$('#contactId').val(contactId);
	$('#searchCriteria').val(getJsonString(searchCriteria));
	$('#accountId').val(accountId);
	$('#holisticQueueForm').submit();
}



function setCurrentRecord(minRecord,columnIndex) {
	return Number(minRecord) - 1 + columnIndex;
}

function getSelectedPage(pageNumber) {
	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	var  searchCriteria = getCriteriaForSelectedPage(pageNumber);
	postRegistrationQueueAjax(searchCriteria,false);
	
	if(pageNumber == 1)
	{
		disableSpecificPaginationBlock("firstPage");
		disableSpecificPaginationBlock("previousPage");
	}
	else if(pageNumber == lastPage)
	{
		disableSpecificPaginationBlock("nextPage");
		disableSpecificPaginationBlock("lastPage");
	}
}


function getPreviousPage() {
	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");
	
	var searchCriteria = getCriteriaForPreviousPage();
	if(searchCriteria.page.currentPage > 0){
	   	postRegistrationQueueAjax(searchCriteria,false);
	}
	    
	if(searchCriteria.page.currentPage == 1)
	{
	   	disableSpecificPaginationBlock("firstPage");
		disableSpecificPaginationBlock("previousPage");
	}
}

function getNextPage() {
	enableSpecificPaginationBlock("firstPage");
    enableSpecificPaginationBlock("previousPage");
    enableSpecificPaginationBlock("nextPage");
    enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	var	 searchCriteria = getCriteriaForNextPage();	 
	if(searchCriteria.page.currentPage != null){
		postRegistrationQueueAjax(searchCriteria,false);
	}
    
    if(searchCriteria.page.currentPage == lastPage)
    {
    	disableSpecificPaginationBlock("nextPage");
		disableSpecificPaginationBlock("lastPage");
    }
	
}

