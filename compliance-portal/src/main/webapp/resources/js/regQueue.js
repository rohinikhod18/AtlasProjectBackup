var REG_QUQUE_PAGE_SIZE = 50;
var currentPage=1;
var regDetailsViewUrl = '#';

$(document).ready(function() {
	onSubNav('reg-sub-nav');
	/*disableSpecificPaginationBlock("firstPage");
	disableSpecificPaginationBlock("previousPage");*/
	setFilterFieldsAsPerCriteria();
});


/**added to enable filter button on press of Enter key when search criteria is selected in filter */
addEventListener("keyup", function(event) {
if (event.keyCode == 13) {
    document.getElementById("reg_queue_Filter").click();
}
});

function applyRegQueueSearchCriteria() {
	
	$('#regQueueForm, #pageCountDetails, #paginationBlock').css('display','none');
	$('#loadingobject').css('display','block');

	$('#gifloaderforqueue').css('visibility','visible');	
	currentPage = 1;
	setFilterApply(true);
	$('#currentpage').val(currentPage);

	// Added isFilterForDashboard to apply filter and pagination from dashboard
	$('#isDashboardSearchCriteria').val(false);
	var request = getQueueSearchCriteriaObject();
	postRegistrationQueueAjax(request,true);
}

function clearAndApplyFilter(){
	clearFilters();  
	setFilterApply(false);
	applyRegQueueSearchCriteria();
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

function getPreviousPage() {
		
	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");
	
	var searchCriteria = getCriteriaForPreviousPage();
	if(searchCriteria.page.currentPage > 0){
	   	postRegistrationQueueAjax(searchCriteria,false);
	}
	    
	if(searchCriteria.page.currentPage === 1)
	{
	   	disableSpecificPaginationBlock("firstPage");
		disableSpecificPaginationBlock("previousPage");
	}
}

function getSelectedPage(pageNumber) {
		
	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	var  searchCriteria = getCriteriaForSelectedPage(pageNumber);
	postRegistrationQueueAjax(searchCriteria,false);
	
	if(pageNumber === 1)
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

function postRegistrationQueueAjax(request,isFilterReq) {
	disableAllPaginationBlock();
	disableButton('reg_queue_Filter');
	$.ajax({
		url : '/compliance-portal/regQueue',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data, status, jqXHR) {
			$('#gifloaderforqueue').css('visibility','hidden');
			
			enableButton('reg_queue_Filter');
			setRegQueueResponse(data, status, jqXHR,isFilterReq);
			
			 enableAllPaginationBloack();
			 //to display total records found after applying filter -Neelesh pant
			if (isFilterReq) {
				getTotalRecordsAfterApplyingFilter();
				$('html, body').animate({scrollTop: '0px'}, 0);
			}
			$('#loadingobject').css('display','none');
			$('#regQueueForm,#pageCountDetails').css('display','block');
		},
		error : function(data, status, jqXHR) {
			$('#gifloaderforqueue').css('visibility','hidden');
			enableButton('reg_queue_Filter');
			setRegQueueErrorResponse(getJsonObject(data), status, jqXHR);
			enableAllPaginationBloack();
			$('#loadingobject').css('display','none');
			$('#regQueueForm,#pageCountDetails').css('display','block');
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
	 setQueuePageObjectText(response.page,isFilterReq);
	 $("#searchCriteriaQueue").val(response.searchCriteria);
	 $("#currentpage").val(response.page.currentPage);
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

function removeRegQueueData() {
	$("#regQueueBody").empty();
}

function getRegQueueRow(registration,rowNumber,user) {
	var conId = registration.contactId;
	var row;
	if(registration.locked && registration.lockedBy === user.name){
		row = '<tr class="owned talign" data-ot="You own(s) this record">';
	} else if (registration.locked){
		row = '<tr class="talign unavailable" data-ot="'+registration.lockedBy+' own(s) this record">';
	} else {
		row='<tr class="talign">';
	}
	row += getRowNumber(rowNumber,conId, getEmptyIfNull(registration.type));
	row += getRegQueuClientNumberColumn(conId,getEmptyIfNull(registration.tradeAccountNum), getEmptyIfNull(registration.type));
	row += getRegQueueRegisteredOnColumn(conId,getEmptyIfNull(registration.registeredOn), getEmptyIfNull(registration.type));
	row += getRegQueuClientNameColumn(conId,getEmptyIfNull(registration.contactName), getEmptyIfNull(registration.type));
	row += getRegQueueTypeColumn(conId,getEmptyIfNull(registration.type));
	row += getRegQueueCountryOfResidenceColumn(conId,getEmptyIfNull(registration.countryOfResidence), getEmptyIfNull(registration.type));
	row += getRegQueueOrganizationColumn(conId,getEmptyIfNull(registration.organisation), getEmptyIfNull(registration.type));
	row += getRegQueueLegalEntityColumn(conId,getEmptyIfNull(registration.legalEntity), getEmptyIfNull(registration.type));
	row += getRegQueueNewOrUpdatedColumn(conId,getEmptyIfNull(registration.newOrUpdated), getEmptyIfNull(registration.type));
	row += getRegQueueRegistrationInDateColumn(conId,getEmptyIfNull(registration.registeredDate), getEmptyIfNull(registration.type));
	row += getRegQueueTransValueColumn(conId,getEmptyIfNull(registration.transactionValue), getEmptyIfNull(registration.type));
	row += getRegQueueEidCheckColumn(conId,registration.eidCheck, getEmptyIfNull(registration.type));
	row += getRegQueueFraugsterColumn(conId,registration.fraugster, getEmptyIfNull(registration.type));
	row += getRegQueueSanctionColumn(conId,registration.sanction, getEmptyIfNull(registration.type));
	row += getRegQueueBlacklistColumn(conId,registration.blacklist, getEmptyIfNull(registration.type));
	row += getRegQueueCustomCheckColumn(conId,registration.customCheck, getEmptyIfNull(registration.type));
	row += '</tr>';
	return row;
}

function getRowNumber(rowNumber, contactId, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td  hidden="hidden"><a '+regDetailsFunctionName+' >'+rowNumber+'</a></td>';
}

function getRegQueuClientNumberColumn(contactId, clientNumber, custType) {
	clientNumber = (clientNumber === null) ? '' : clientNumber;
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td class="number"><a '+regDetailsFunctionName+' >'+clientNumber+'</a></td>';
}

function getRegQueueRegisteredOnColumn(contactId, regOn, custType) {
	regOn = (regOn === null) ? '' : regOn;
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td class="nowrap"><a '+regDetailsFunctionName+' >'+regOn+'</a></td>';
}

function getRegQueuClientNameColumn(contactId,clientName, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td class="wordwrapfixwidth"><a '+regDetailsFunctionName+' >'+clientName+'</a></td>';
}

function getRegQueueTypeColumn(contactId, type) {
	var regDetailsFunctionName = 'getRegistrationQueueDetails('+contactId+',\''+type+'\',this);'
	return '<td><a '+regDetailsFunctionName+' >'+type+'</a></td>';
}

function getRegQueueCountryOfResidenceColumn(contactId, countryOfResidence, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td><a '+regDetailsFunctionName+' >'+countryOfResidence+'</a></td>';
}

function getRegQueueOrganizationColumn(contactId , organisation, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td><a '+regDetailsFunctionName+' >'+organisation+'</a></td>';
}

function getRegQueueLegalEntityColumn(contactId , legalEntity, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td><a '+regDetailsFunctionName+' >'+legalEntity+'</a></td>';
}

function getRegQueueNewOrUpdatedColumn(contactId, newOrUpdated, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td class="nowrap"><a '+regDetailsFunctionName+' >'+newOrUpdated+'</a></td>';
}

function getRegQueueRegistrationInDateColumn(contactId, registeredDate, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td style="text-align: center" class="nowrap"><a '+regDetailsFunctionName+' >'+registeredDate+'</a></td>';
}

function getRegQueueTransValueColumn(contactId, transValue, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td class="nowrap number"><a '+regDetailsFunctionName+' >'+transValue+'</a></td>	';
}
function getRegQueueEidCheckColumn(contactId, eidCheck, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	
	if(eidCheck !== null && eidCheck.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a '+regDetailsFunctionName+' "><i class="material-icons">check</i></a></td>';
	}else if(eidCheck !== null && eidCheck.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a '+regDetailsFunctionName+' "><i class="material-icons">not_interested</i></a></td>';
	}else {
		return  '<td class="no-cell"><a '+regDetailsFunctionName+' "><i class="material-icons">clear</i></a></td>';
	}
	 
}
function getRegQueueFraugsterColumn(contactId,fraugster, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	if(fraugster !== null && fraugster.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a '+regDetailsFunctionName+' ><i class="material-icons">check</i></a></td>';
	} else if(fraugster !== null && fraugster.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a '+regDetailsFunctionName+' ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a '+regDetailsFunctionName+' "><i class="material-icons">clear</i></a></td>';
	}
}

function getRegQueueSanctionColumn(contactId,sanction, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	if(sanction !== null && sanction.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a '+regDetailsFunctionName+' ><i class="material-icons">check</i></a></td>';
	} else if(sanction !== null && sanction.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a '+regDetailsFunctionName+' ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a '+regDetailsFunctionName+' ><i class="material-icons">clear</i></a></td>';
	}
}

function getRegQueueBlacklistColumn(contactId,blacklist, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	if(blacklist !== null && blacklist.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a '+regDetailsFunctionName+' ><i class="material-icons">check</i></a></td>';
	} else if(blacklist !== null && blacklist.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a '+regDetailsFunctionName+' ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a '+regDetailsFunctionName+' ><i class="material-icons">clear</i></a></td>';
	}
}
function getRegQueueCustomCheckColumn(contactId,customCheck, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	if(customCheck !== null && customCheck.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+regDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(customCheck !== null && customCheck.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+regDetailsFunctionName+'" "><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+regDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
}
function getRegistrationQueueDetails(contactId,custType,anchor) {
	/** set false Origin is registration queue page*/
	setRequestOrigin(false);
	var searchCriteria = getQueueSearchCriteriaObject();
	var columnIndex = $(anchor).parent().parent().index()+1;
	addField("currentRecord",setCurrentRecord(searchCriteria.page.minRecord,columnIndex),searchCriteria.page);
	$('#custType').val(custType);
	$('#contactId').val(contactId);
	$('#searchCriteria').val(getJsonString(searchCriteria));
	$('#regQueueForm').submit();
}

function setCurrentRecord(minRecord,columnIndex) {
	return Number(minRecord) - 1 + columnIndex;
}

function sortByField(fieldName){
	var searchCriteria =  getSortObjectWithSearchCriteria(fieldName);
	postRegistrationQueueAjax(searchCriteria,true);
	console.log(searchCriteria);
}

function isDetailsPermission(){
	var user = getJsonObject($("#userInfo").val());
	return user.permissions.canViewRegistrationDetails;
}

function getFunctionDetails(contactId,custType){
	if(isDetailsPermission()){
		return 'onclick="getRegistrationQueueDetails('+contactId+',\''+custType+'\',this);"';
	}
	return '';
}


