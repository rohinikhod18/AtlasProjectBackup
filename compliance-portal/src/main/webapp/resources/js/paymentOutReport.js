var REG_QUQUE_PAGE_SIZE = 50;
var currentPage=1;
var regDetailsViewUrl = '#';
var fieldName;

$(document).ready(function() {
	onSubNav('payOut-report-sub-nav');
	/*disableSpecificPaginationBlock("firstPage");
	disableSpecificPaginationBlock("previousPage");*/
	setFilterFieldsAsPerCriteria();
});

/**added to enable filter button on press of Enter key when search criteria is selected in filter */
addEventListener("keyup", function(event) {
if (event.keyCode == 13) {
    document.getElementById("payOut_report_Filter").click();
}
});


function applyPaymentOutSearchCriteria() {
	$('#payOutQueueForm, #pageCountDetails, #paginationBlock').css('display','none');
	$('#loadingobject').css('display','block');
	
	$('#gifloaderforPayOutreport').css('visibility','visible');
	currentPage = 1;
	$('#currentpage').val(currentPage);
	setFilterApply(true);
	var request = getQueueSearchCriteriaObject();
	postPaymentOutQueueAjax(request,true);
}

function applyPaymentOutSearchCriteriaForClear() {
	$('#payOutQueueForm, #pageCountDetails, #paginationBlock').css('display','none');
	$('#loadingobject').css('display','block');
	
	$('#gifloaderforPayOutreport').css('visibility','visible');
	currentPage = 1;
	$('#currentpage').val(currentPage);
	var request = getQueueSearchCriteriaObject();
	addField('isLandingPage',true,request);
	postPaymentOutQueueAjax(request,true);
}

function clearAndApplyFilter(){
	clearFilters();
	setFilterApply(false);
	applyPaymentOutSearchCriteriaForClear();
		setDefaultDateValue();
		//AT-3319
		defaultDateFilter=true;
}

function getNextPage() {
	enableSpecificPaginationBlock("firstPage");
    enableSpecificPaginationBlock("previousPage");
    enableSpecificPaginationBlock("nextPage");
    enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	 var searchCriteria = getCriteriaForNextPage();
		if(searchCriteria.page.currentPage != null){
			postPaymentOutQueueAjax(searchCriteria,false);
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
    	postPaymentOutQueueAjax(searchCriteria,false);
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
	postPaymentOutQueueAjax(searchCriteria,false);
	
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

function postPaymentOutQueueAjax(request,isFilterReq) {
	disableAllPaginationBlock();
	disableButton('payOut_report_Filter');
	$.ajax({
		url : '/compliance-portal/paymentOutReportCriteria',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data, status, jqXHR) {
			$('#gifloaderforPayOutreport').css('visibility','hidden');
			enableButton('payOut_report_Filter');
			setPaymentOutResponse(data, status, jqXHR,isFilterReq);
			 //to display total records found after applying filter neelesh pant
			if (isFilterReq) {
				getTotalRecordsAfterApplyingFilter();
				$('html, body').animate({scrollTop: '0px'}, 0);
			}
			enableAllPaginationBloack();
			$('#loadingobject').css('display','none');
			$('#payOutQueueForm').css('display','block');
		},
		error : function(data, status, jqXHR) {
			$('#gifloaderforPayOutreport').css('visibility','hidden');
			enableButton('payOut_report_Filter');
			setPaymentOutErrorResponse(getJsonObject(data), status, jqXHR);
			enableAllPaginationBloack();
			$('#loadingobject').css('display','none');
			$('#payOutQueueForm').css('display','block');
		}
	});
}

function setPaymentOutResponse(response, status, jqXHR,isFilterReq) {
	var rows = '';
	 removePaymentOutData();
	 $.each(response.paymentOutQueue, function(index, paymentOut) {
		 rows += getPaymentOutRow(paymentOut,index+1,response.user);
		});
	 $("#payOutQueueBody").append(rows);
	 var user = getJsonObject($("#userInfo").val());
	 if(!user.permissions.canViewPaymentOutDetails){
			$('#payOutQueueBody a').removeAttr('onclick');
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
			
			$('#payOutQueueBody a').addClass('removeHyperLink');
	 }
}

function setPaymentOutErrorResponse(response, status, jqXHR) {

}

function getPaymentOutSearchCriteriaObject() {
	var searchCriteria = {};
	addField('filter',getPaymentOutFilterObject(),searchCriteria);
	addField('page',getPaymentOutPageObject(),searchCriteria);
	//addField('sort',getSortObject(),searchCriteria);
	console.log('searchCriteria: '+ JSON.stringify(searchCriteria));
	return searchCriteria;
}

function getPaymentOutSearchFilter(filter,page) {
	var searchCriteria = {};
	addField('filter',filter,searchCriteria);
	addField('page',page,searchCriteria);
	console.log('searchCriteria: '+ JSON.stringify(searchCriteria));
	return searchCriteria;
}

function getPaymentOutSearchCriteriaForPage(page) {
	var searchCriteria = {};
	addField('page',page,searchCriteria);
	console.log('searchCriteria: '+ JSON.stringify(searchCriteria));
	return searchCriteria;
}

function getPaymentOutFilterObject() {
	var filter = $('#payOutQueueFilterForm').serializeObject();
	console.log('Filter: '+ JSON.stringify(filter));
	return filter;
}

function getPaymentOutPageObject() {
	var page = {};
	var minRecord = Number($("#payOutQueueMinRecord").text());
	var maxRecord = Number($("#payOutQueueMaxRecord").text());
	var totalRecords = Number($("#payOutQueueTotalRecords").text());
	var totalPages = Number($("#payOutQueueTotalPages").val());
	var pageSize = REG_QUQUE_PAGE_SIZE;
	addField("minRecord",minRecord,page);
	addField("maxRecord",maxRecord,page);
	addField("totalRecords",totalRecords,page);
	addField("currentPage",currentPage,page);
	addField("totalPages",totalPages,page);
	addField("pageSize",pageSize,page);
	console.log('page: '+ JSON.stringify(page));
	
	return page;
}

function setPaymentOutPageObjectText(page) {
	// searchCriteria.page = page;
	$("#payOutQueueMinRecord").text(page.minRecord);
	$("#payOutQueueMaxRecord").text(page.maxRecord);
	$("#payOutQueueTotalRecords").text(page.totalRecords);
	$("#payOutQueueTotalPages").val(page.totalPages);
	return page;
}

function removePaymentOutData() {
	$("#payOutQueueBody").empty();
}

function getPaymentOutRow(paymentOut,rowNumber,user) {
	var payId = paymentOut.paymentOutId;
	var row;
	if(paymentOut.locked && paymentOut.lockedBy === user.name){
		row = '<tr class="owned talign" data-ot="You own(s) this record">';
	} else if (paymentOut.locked){
		row = '<tr class="talign unavailable" data-ot="'+paymentOut.lockedBy+' own(s) this record">';
	} else {
		row='<tr class="talign">';
	}
	row += getRowNumber(rowNumber,payId, getEmptyIfNull(paymentOut.type));
	row += getPaymentOutTransactionNumberColumn(payId,getEmptyIfNull(paymentOut.transactionId), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutClientNumberColumn(payId,getEmptyIfNull(paymentOut.clientId), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutRegisteredOnColumn(payId,getEmptyIfNull(paymentOut.date), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutClientNameColumn(payId,getEmptyIfNull(paymentOut.contactName), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutOrgColumn(payId,getEmptyIfNull(paymentOut.organisation), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutLegalEntityColumn(payId,getEmptyIfNull(paymentOut.legalEntity), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutValueDateColumn(payId,getEmptyIfNull(paymentOut.maturityDate), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutTypeColumn(payId,getEmptyIfNull(paymentOut.type), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutBuyCurrencyColumn(payId,getEmptyIfNull(paymentOut.buyCurrency), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutAmountColumn(payId,getEmptyIfNull(paymentOut.amount), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutBeneficiaryColumn(payId,getEmptyIfNull(paymentOut.beneficiary), getEmptyIfNull(paymentOut.type));
	row += getPaymentOutCountryColumn(payId,paymentOut.country, getEmptyIfNull(paymentOut.type));
	row += getPaymentOutOverallStatusColumn(payId,paymentOut.overallStatus, getEmptyIfNull(paymentOut.type));
	row += getPaymentOutWatchListColumn(payId,paymentOut.watchlist, getEmptyIfNull(paymentOut.type));
	row += getPaymentOutFraugsterColumn(payId,paymentOut.fraugster, getEmptyIfNull(paymentOut.type));
	row += getPaymentOutSanctionColumn(payId,paymentOut.sanction,paymentOut.blacklistPayRef, getEmptyIfNull(paymentOut.type));
	row += getPaymentOutBlacklistColumn(payId,paymentOut.blacklist, getEmptyIfNull(paymentOut.type));
	/**Added custom check status column , fixed issue AT -464: Abhijit G*/
	row += getPaymentOutCustomCheckColumn(payId,paymentOut.customCheck, getEmptyIfNull(paymentOut.type));
	row += getPaymentOutIntuitionColumn(payId,paymentOut.intuitionStatus, getEmptyIfNull(paymentOut.type)); //AT-4607
	row += '</tr>';
	return row;
}

function getRowNumber(rowNumber,paymentOutId, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td  hidden="hidden"><a onclick="'+payOutDetailsFunctionName+'" >'+rowNumber+'</a></td>';
}

function getPaymentOutTransactionNumberColumn(paymentOutId,transactionNumber, custType) {
	transactionNumber = (transactionNumber === null) ? '' : transactionNumber;
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="number"><a onclick="'+payOutDetailsFunctionName+'" >'+transactionNumber+'</a></td>';
}

function getPaymentOutAmountColumn(paymentOutId,amount, custType) {
	amount = (amount === null) ? '' : amount;
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="number"><a onclick="'+payOutDetailsFunctionName+'" >'+amount+'</a></td>';
}

function getPaymentOutCountryColumn(paymentOutId,country, custType) {
	country = (country === null) ? '' : country;
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="wordwrapfixwidth"><a onclick="'+payOutDetailsFunctionName+'" >'+country+'</a></td>';
}

function getPaymentOutOverallStatusColumn(paymentOutId,overallStatus, custType) {
	overallStatus = (overallStatus === null) ? '' : overallStatus;
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td><a onclick="'+payOutDetailsFunctionName+'" >'+overallStatus+'</a></td>';
}

function getPaymentOutBeneficiaryColumn(paymentOutId,beneficiary, custType) {
	beneficiary = (beneficiary === null) ? '' : beneficiary;
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="wordwrapfixwidth"><a onclick="'+payOutDetailsFunctionName+'" >'+beneficiary+'</a></td>';
}

function getPaymentOutClientNumberColumn(paymentOutId,clientNumber, custType) {
	clientNumber = (clientNumber === null) ? '' : clientNumber;
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="number"><a onclick="'+payOutDetailsFunctionName+'" >'+clientNumber+'</a></td>';
}

function getPaymentOutRegisteredOnColumn(paymentOutId, regOn, custType) {
	regOn = (regOn === null) ? '' : regOn;
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="nowrap"><a onclick="'+payOutDetailsFunctionName+'" >'+regOn+'</a></td>';
}

function getPaymentOutClientNameColumn(paymentOutId,clientName, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="wordwrapfixwidth"><a onclick="'+payOutDetailsFunctionName+'" >'+clientName+'</a></td>';
}

function getPaymentOutOrgColumn(paymentOutId,org, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="wordwrap"><a onclick="'+payOutDetailsFunctionName+'" >'+org+'</a></td>';
}

function getPaymentOutLegalEntityColumn(paymentOutId,legalEntity, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="wordwrap"><a onclick="'+payOutDetailsFunctionName+'" >'+legalEntity+'</a></td>';
}

function getPaymentOutValueDateColumn(paymentOutId,maturityDate, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="wordwrap"><a onclick="'+payOutDetailsFunctionName+'" >'+maturityDate+'</a></td>';
}

function getPaymentOutTypeColumn(paymentOutId, type, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td><a onclick="'+payOutDetailsFunctionName+'" >'+type+'</a></td>';
}

function getPaymentOutBuyCurrencyColumn(paymentOutId, buyCurrency, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td><a onclick="'+payOutDetailsFunctionName+'" >'+buyCurrency+'</a></td>';
}

function getPaymentOutSellCurrencyColumn(paymentOutId , sellCurrency, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td><a onclick="'+payOutDetailsFunctionName+'" >'+sellCurrency+'</a></td>';
}

function getPaymentOutSourceColumn(paymentOutId, source, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="nowrap"><a onclick="'+payOutDetailsFunctionName+'" >'+source+'</a></td>';
}

function getPaymentOutTransValueColumn(paymentOutId, transValue, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\',this);'
	return '<td class="nowrap"><a onclick="'+payOutDetailsFunctionName+'" >'+transValue+'</a></td>	';
}

function getPaymentOutFraugsterColumn(paymentOutId,fraugster, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\,this);'
	if(fraugster !== null && fraugster.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(fraugster !== null && fraugster.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">clear</i></a></td>';
	}
}

function getPaymentOutSanctionColumn(paymentOutId,sanction,blacklistPayRef,custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\,this);'
	if((sanction !== null && sanction.toUpperCase() === 'PASS') && (blacklistPayRef !== null && blacklistPayRef.toUpperCase() === 'PASS' || blacklistPayRef.toUpperCase() === 'NOT_REQUIRED' || blacklistPayRef.toUpperCase() === 'NOT_PERFORMED')) {
		return	'<td class="yes-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	}
	else if((sanction !== null && sanction.toUpperCase() === 'NOT_REQUIRED') && (blacklistPayRef !== null && blacklistPayRef.toUpperCase() === 'FAIL')) {
		return	'<td class="no-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
	else if((sanction !== null && sanction.toUpperCase() === 'FAIL'|| sanction.toUpperCase() === 'SERVICEFAILURE') && (blacklistPayRef !== null && blacklistPayRef.toUpperCase() === 'PASS')){
		return	'<td class="no-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
	else if((sanction !== null && sanction.toUpperCase() === 'PASS') && (blacklistPayRef !== null && blacklistPayRef.toUpperCase() === 'FAIL' || blacklistPayRef.toUpperCase() === 'SERVICEFAILURE')){
		return	'<td class="amber-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
	else if((sanction !== null && sanction.toUpperCase() === 'FAIL'|| sanction.toUpperCase() === 'SERVICEFAILURE') && (blacklistPayRef !== null && blacklistPayRef.toUpperCase() === 'FAIL' || blacklistPayRef.toUpperCase() === 'SERVICEFAILURE' || blacklistPayRef.toUpperCase() === 'NOT_REQUIRED' || blacklistPayRef.toUpperCase() === 'NOT_PERFORMED')){
		return	'<td class="no-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
	else if((sanction !== null && sanction.toUpperCase() === 'NOT_REQUIRED'|| sanction !== null && sanction.toUpperCase() === 'NOT_PERFORMED') && (blacklistPayRef !== null && blacklistPayRef.toUpperCase() === 'NOT_REQUIRED' || blacklistPayRef.toUpperCase() === 'NOT_PERFORMED')) {
		return	'<td class="na-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">not_interested</i></a></td>';
	}
}

function getPaymentOutBlacklistColumn(paymentOutId,blacklist, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\,this);'
	if(blacklist !== null && blacklist.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(blacklist !== null && blacklist.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
}

//At-4607
function getPaymentOutIntuitionColumn(paymentOutId,intuitionStatus, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\,this);'
	if(intuitionStatus !== null && intuitionStatus.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(intuitionStatus === null || intuitionStatus.toUpperCase() === 'NOT_REQUIRED' ) {
		return	'<td class="na-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
}

function getPaymentOutWatchListColumn(paymentOutId,watchList, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\,this);'
	if(watchList !== null && watchList.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(watchList !== null && watchList.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">not_interested</i></a></td>';
	} else if(watchList !== null && watchList.toUpperCase() === 'WATCH_LIST') {
		return	'<td class="amber-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">clear</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">clear</i></a></td>';
	}
}

function getPaymentOutCustomCheckColumn(paymentOutId,customCheck, custType) {
	var payOutDetailsFunctionName = 'getPaymentOutQueueDetails('+paymentOutId+',\''+custType+'\,this);'
	if(customCheck !== null && customCheck.toUpperCase() === 'PASS') {
		return	'<td class="yes-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">check</i></a></td>';
	} else if(customCheck !== null && customCheck.toUpperCase() === 'NOT_REQUIRED') {
		return	'<td class="na-cell"><a onclick="'+payOutDetailsFunctionName+'" "><i class="material-icons">not_interested</i></a></td>';
	} else {
		return  '<td class="no-cell"><a onclick="'+payOutDetailsFunctionName+'" ><i class="material-icons">clear</i></a></td>';
	}
}

function getPaymentOutQueueDetails(paymentOutId,custType,anchor) {
	/** set true if Origin is paymentOut report queue page*/
	setRequestOrigin(true);
	var	searchCriteria =   getQueueSearchCriteriaObject();
	var columnIndex = $(anchor).parent().parent().index()+1;
	addField("currentRecord",setCurrentRecord(searchCriteria.page.minRecord,columnIndex),searchCriteria.page);
	
	$('#custType').val(custType);
	$('#paymentOutId').val(paymentOutId);
	$('#searchCriteria').val(getJsonString(searchCriteria));
	$('#payOutQueueForm').submit();
	//$(anchor).attr("href", url); 
}

function setCurrentRecord(minRecord,columnIndex) {
	return Number(minRecord) - 1 + columnIndex;
}

function getSortData(sortParameter) {
	fieldName = sortParameter;
	var request = getQueueSearchCriteriaObject();
	searchCriteria = request;
	postPaymentOutQueueAjax(request);
}

function sortByField(fieldName){
	var searchCriteria =  getSortObjectWithSearchCriteria(fieldName);
	postPaymentOutQueueAjax(searchCriteria,true);
	console.log(searchCriteria);
}

function searchPayOutReportDownLoadSearchCriteria() {
	currentPage = 1;
	var request = getQueueSearchCriteriaObject();
	searchCriteria = request;
	$('#downloadSearchCriteria').val(getJsonString(searchCriteria));
	$('#payOutReportDownLoadForm').submit();
}

function checkTotalRecordsAndDownload() {
	var totalRecords = Number(getTextById("queueTotalRecords"));
	if (totalRecords > 5000) {
		var theModalMask = $('#modal-mask');
		$("#downloadReportMessage")
				.text(
						'More than 5000 records found. Please change filter criteria to limit search results.');
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
	} else if (totalRecords === 0) {
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
	} else {
		searchPayOutReportDownLoadSearchCriteria();
	}
}

