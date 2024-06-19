var searchCriteria;
var currentPage=1;

$(document).ready(function() {
	onSubNav('payIn-report-sub-nav');
	/*disableSpecificPaginationBlock("firstPage");
	disableSpecificPaginationBlock("previousPage");*/
	setFilterFieldsAsPerCriteria();
});

/**added to enable filter button on press of Enter key when search criteria is selected in filter */
addEventListener("keyup", function(event) {
if (event.keyCode == 13) {
    document.getElementById("payIn_report_Filter").click();
}
});


function searchPayInQueueSearchCriteria() {
	$('#payInQueueForm, #pageCountDetails, #paginationBlock').css('display','none');
	$('#loadingobject').css('display','block');
	
	$('#gifloaderforPayinreport').css('visibility','visible');	
	currentPage = 1;
	$('#currentpage').val(currentPage);
	setFilterApply(true);
	var request = getQueueSearchCriteriaObject();
	searchCriteria = request;
	postPaymentInQueueAjax(request,true);
}

function searchPayInQueueSearchCriteriaForClear() {
	
	$('#payInQueueForm, #pageCountDetails, #paginationBlock').css('display','none');
	$('#loadingobject').css('display','block');
	
	$('#gifloaderforPayinreport').css('visibility','visible');	
	currentPage = 1;
	$('#currentpage').val(currentPage);
	setFilterApply(false);
	var request = getQueueSearchCriteriaObject();
	addField('isLandingPage',true,request);
	searchCriteria = request;
	postPaymentInQueueAjax(request,true);
}


function postPaymentInQueueAjax(request,isFilterReq) {
	disableAllPaginationBlock();
	disableButton('payIn_report_Filter');
	$.ajax({
		url : '/compliance-portal/paymentInReportCriteria',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data, status, jqXHR) {
			$('#gifloaderforPayinreport').css('visibility','hidden');
			enableButton('payIn_report_Filter');
			setPayInQueueResponse(data, status, jqXHR,isFilterReq);
			enableAllPaginationBloack();
			//to display total records found after applying filter neelesh pant
			 if (isFilterReq) {
					getTotalRecordsAfterApplyingFilter();
					$('html, body').animate({scrollTop: '0px'}, 0);
				}
			$('#loadingobject').css('display','none');
			$('#payInQueueForm').css('display','block');
		},
		error : function(data, status, jqXHR) {
			$('#gifloaderforPayinreport').css('visibility','hidden');
			enableButton('payIn_report_Filter');
			enableAllPaginationBloack();
			$('#loadingobject').css('display','none');
			$('#payInQueueForm').css('display','block');
		}
	});
}

function setPayInQueueResponse(response, status, jqXHR,isFilterReq){
	var rows = '';
	removePayInQueueData();
	$.each(response.paymentInQueue, function(index, paymentIn) {
		 rows += getPayInQueueRow(paymentIn,index+1,response.user);
		});
	 $("#payInQueueBody").append(rows);
	 var user = getJsonObject(getValueById('userInfo'));
	 if(!user.permissions.canViewPaymentInDetails){
			$('#payInQueueBody a').removeAttr('onclick');
	 }
	 
	 if(response.page !== undefined && response.page !== null){
		 setQueuePageObjectText(response.page,isFilterReq);
		 $("#currentpage").val(response.page.currentPage);
		 //Code commented since pagination was showing still having 0 records
		 //enableAllPaginationBloack();		
		 //$("#paginationBlock").css('display','block');
		 $('#pageCountDetails').css('display','block');
	 } else {
		 	$("#paginationBlock").css('display','none');
			$('#pageCountDetails').css('display','none');
			$("#queueTotalRecords").text("0");
	 }
	 $("#searchCriteriaQueue").val(response.searchCriteria);
	 initUserLockedPopup();
	 
	 if(!response.user.permissions.canViewRegistrationDetails||response.user.permissions.isReadOnlyUser){
			
			$('#payInQueueBody a').addClass('removeHyperLink');
	 }
}

function removePayInQueueData() {
	$("#payInQueueBody").empty();
}

function getPayInQueueRow(paymentIn,rowNumber,user) {
	
	var conId = paymentIn.paymentInId;
	var row;
	if(paymentIn.locked && paymentIn.lockedBy === user.name){
		row = '<tr class="owned talign" data-ot="You own(s) this record">';
	} else if (paymentIn.locked){
		row = '<tr class="talign unavailable" data-ot="'+paymentIn.lockedBy+' own(s) this record">';
	} else {
		row='<tr class="talign">';
	}
	
	row += getRowNumber(rowNumber,conId,getEmptyIfNull(paymentIn.type));
	row += getPayInQueueClientNumberColumn(conId,getEmptyIfNull(paymentIn.transactionId),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueDateColumn(conId,getEmptyIfNull(paymentIn.date,getEmptyIfNull(paymentIn.type)));
	row += getPayInQueueClientIdColumn(conId,getEmptyIfNull(paymentIn.clientId),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueClientNameColumn(conId,getEmptyIfNull(paymentIn.contactName),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueTypeColumn(conId,getEmptyIfNull(paymentIn.type),getEmptyIfNull(paymentIn.type));
	
	row += getPayInQueueOrganizationColumn(conId,getEmptyIfNull(paymentIn.organization),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueLegalEntityColumn(conId,getEmptyIfNull(paymentIn.legalEntity),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueSellCurrencyColumn(conId,getEmptyIfNull(paymentIn.sellCurrency));
	row += getPayInQueueAmountColumn(conId,getEmptyIfNull(paymentIn.amount),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueMethodColumn(conId,getEmptyIfNull(paymentIn.method),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueCountryColumn(conId,getEmptyIfNull(paymentIn.countryFullName),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueOverallStatusColumn(conId,getEmptyIfNull(paymentIn.overallStatus),getEmptyIfNull(paymentIn.type));
	row += getPayInQueueWatchlistColumn(conId,paymentIn.watchlist,getEmptyIfNull(paymentIn.type));
	row += getPayInQueueFraugsterColumn(conId,paymentIn.fraugster,getEmptyIfNull(paymentIn.type));
	row += getPayInQueueSanctionColumn(conId,paymentIn.sanction,getEmptyIfNull(paymentIn.type));
	row += getPayInQueueBlacklistColumn(conId,paymentIn.blacklist,getEmptyIfNull(paymentIn.type));
	row += getPayInQueueCustomCheckColumn(conId,paymentIn.customCheck,getEmptyIfNull(paymentIn.type)); 
	row += getPayInQueueRiskScoreStatusColumn(conId,paymentIn.riskStatus,getEmptyIfNull(paymentIn.type)); 
	row += getPayInQueueIntuitionColumn(conId,paymentIn.intuitionStatus,getEmptyIfNull(paymentIn.type)); //AT-4607
	
	row += '</tr>';
	return row;
}

function getRowNumber(rowNumber,paymentInId,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td  hidden="hidden"><a onclick="'+payInDetailsFunctionName+'" >'+rowNumber+'</a></td>';
}

function getPayInQueueClientNumberColumn(paymentInId,clientNumber,custType) {
	clientNumber = (clientNumber === null) ? '' : clientNumber;
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td class="nowrap number"><a onclick="'+payInDetailsFunctionName+'" >'+clientNumber+'</a></td>';
}

function getPayInQueueDateColumn(paymentInId, payInOn,custType) {
	payInOn = (payInOn === null) ? '' : payInOn;
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td class="nowrap"><a onclick="'+payInDetailsFunctionName+'" >'+payInOn+'</a></td>';
}

function getPayInQueueClientNameColumn(paymentInId,clientName,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td class="wordwrapfixwidth"><a onclick="'+payInDetailsFunctionName+'" >'+clientName+'</a></td>';
}

function getPayInQueueTypeColumn(paymentInId, type,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td class="nowrap"><a onclick="'+payInDetailsFunctionName+'" >'+type+'</a></td>';
}

function getPayInQueueOrganizationColumn(paymentInId, organization,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td><a onclick="'+payInDetailsFunctionName+'" >'+organization+'</a></td>';
}

function getPayInQueueLegalEntityColumn(paymentInId, legalEntity,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td><a onclick="'+payInDetailsFunctionName+'" >'+legalEntity+'</a></td>';
}

function getPayInQueueClientIdColumn(paymentInId, clientId,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td class="number"><a onclick="'+payInDetailsFunctionName+'" >'+clientId+'</a></td>';
}

function getPayInQueueSellCurrencyColumn(paymentInId , sellCurrency,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td><a onclick="'+payInDetailsFunctionName+'" >'+sellCurrency+'</a></td>';
}

function getPayInQueueAmountColumn(paymentInId, amount,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td class="nowrap number"><a onclick="'+payInDetailsFunctionName+'" >'+amount+'</a></td>';
}

function getPayInQueueMethodColumn(paymentInId, method,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td class="wordwrapfixwidth"><a onclick="'+payInDetailsFunctionName+'" >'+method+'</a></td>';
}

function getPayInQueueCountryColumn(paymentInId, country,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td class="nowrap"><a onclick="'+payInDetailsFunctionName+'" >'+country+'</a></td>';
}

function getPayInQueueOverallStatusColumn(paymentInId, overallStatus,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	return '<td><a onclick="'+payInDetailsFunctionName+'" >'+overallStatus+'</a></td>';
}

function getPayInQueueWatchlistColumn(paymentInId, watchlist,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	if(watchlist !== null && watchlist.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payInDetailsFunctionName+'" "><i class="material-icons">check</i></a></td>';
	} else if(watchlist !== null && watchlist.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">not_interested</i></a></td>';
	} else if(watchlist !== null && watchlist.toUpperCase() === 'WATCH_LIST') {
		return	'<td class="amber-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payInDetailsFunctionName+'" "><i class="material-icons">clear</i></a></td>';
	}
	 
}
function getPayInQueueFraugsterColumn(paymentInId,fraugster,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	if(fraugster !== null && fraugster.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(fraugster !== null && fraugster.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payInDetailsFunctionName+'" "><i class="material-icons">clear</i></a></td>';
	}
}

function getPayInQueueSanctionColumn(paymentInId,sanction,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	if(sanction !== null && sanction.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(sanction !== null && sanction.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
}

function getPayInQueueBlacklistColumn(paymentInId,blacklist,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	if(blacklist !== null && blacklist.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(blacklist !== null && blacklist.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
}

function getPayInQueueCustomCheckColumn(paymentInId,customCheck,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	if(customCheck !== null && customCheck.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(customCheck !== null && customCheck.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
}
function getPayInQueueRiskScoreStatusColumn(paymentInId,riskStatus,custType) {
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	if(riskStatus !== null && riskStatus.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payInDetailsFunctionName+'" "><i class="material-icons">check</i></a></td>';
	} else if(riskStatus !== null && riskStatus.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payInDetailsFunctionName+'" "><i class="material-icons">clear</i></a></td>';
	}
}

//AT-4607
function getPayInQueueIntuitionColumn(paymentInId,intuitionStatus,custType) { 
	var payInDetailsFunctionName = 'getPaymentInQueueDetails('+paymentInId+',\''+custType+'\',this);'
	if(intuitionStatus !== null && intuitionStatus.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payInDetailsFunctionName+'" "><i class="material-icons">check</i></a></td>';
	} else if(intuitionStatus === null || intuitionStatus.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payInDetailsFunctionName+'" ><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payInDetailsFunctionName+'" "><i class="material-icons">clear</i></a></td>';
	}
}

function setCurrentRecord(minRecord,columnIndex) {
	return Number(minRecord) - 1 + columnIndex;
}

function clearAndApplyFilter(){
	clearFilters();
	setFilterApply(false);
	searchPayInQueueSearchCriteriaForClear();
		setDefaultDateValue();
		//AT-3319
		defaultDateFilter=true;
}

function sortByField(fieldName){
	var searchCriteria =  getSortObjectWithSearchCriteria(fieldName);
	postPaymentInQueueAjax(searchCriteria,true);
	console.log(searchCriteria);
}

function getNextPage() {
	enableSpecificPaginationBlock("firstPage");
    enableSpecificPaginationBlock("previousPage");
    enableSpecificPaginationBlock("nextPage");
    enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	var searchCriteria = getCriteriaForNextPage();
		if(searchCriteria.page.currentPage != null){
			postPaymentInQueueAjax(searchCriteria,false);
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
	   postPaymentInQueueAjax(searchCriteria,false);
   }
   
   if(searchCriteria.page.currentPage == 1)
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
	var searchCriteria = getCriteriaForSelectedPage(pageNumber);
	postPaymentInQueueAjax(searchCriteria,false);
	
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

function getPaymentInQueueDetails(paymentInId,custType,anchor) {
	/** set true if Origin is paymentIn report queue page*/
	setRequestOrigin(true);
	var searchCriteria =  getQueueSearchCriteriaObject();
	var columnIndex = $(anchor).parent().parent().index()+1;
	addField("currentRecord",setCurrentRecord(searchCriteria.page.minRecord,columnIndex),searchCriteria.page);
	$('#custType').val(custType);
	$('#paymentInId').val(paymentInId);
	$('#searchCriteria').val(getJsonString(searchCriteria));
	$('#payInQueueForm').submit();
}

function searchPayInReportDownLoadSearchCriteria() {
	currentPage = 1;
	var request = getQueueSearchCriteriaObject();
	searchCriteria = request;
	$('#downloadSearchCriteria').val(getJsonString(searchCriteria));
	$('#payInDownLoadForm').submit();
}

function checkTotalRecordsAndDownload() {
	var totalRecords = Number(getTextById("queueTotalRecords"));
	if(totalRecords > 5000)
	{
		var theModalMask = $('#modal-mask');
		$("#downloadReportMessage").text('More than 5000 records found. Please change filter criteria to limit search results.');
		$("#downloadReportMessage").attr('readonly', true);
			$("#downloadReportPopup").dialog({
				modal : true,
				draggable : false,
				resizable : false,
				show : 'blind',
				hide : 'blind',
				width : ($(window).width()-50),
				height : ($(window).height()-50),
				dialogClass : 'ui-dialog-osx',
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
						$(theModalMask).removeClass("modal-mask--visible").addClass("modal-mask--hidden");
					}
				}
			});
	}else if (totalRecords === 0) {
		var theModalMask = $('#modal-mask');
		$("#downloadReportMessage").text(
				'Please change filter criteria to limit search results.');
		$("#downloadReportMessage").attr('readonly', true);
		$("#downloadReportPopup").dialog(
				{
					modal : true,
					draggable : false,
					resizable : false,
					show : 'blind',
					hide : 'blind',
					width : ($(window).width()-50),
					height : ($(window).height()-50),
					dialogClass : 'ui-dialog-osx',
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
							$(theModalMask).removeClass("modal-mask--visible")
									.addClass("modal-mask--hidden");
						}
					}
				});
	} else{
		searchPayInReportDownLoadSearchCriteria();
	}
}
