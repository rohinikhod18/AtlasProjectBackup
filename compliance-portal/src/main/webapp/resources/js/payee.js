var searchQueueCriteria;
var REG_QUQUE_PAGE_SIZE = 50;
var currentPage=1;
var lastPage = 1;
var prePage = 1;
var isFilterApply=false;
var isRequestFromReportQueue=false;
var isFromClearFilter = false;
var isOnFocusCalled = false;

$(document).ready(function() {
	onSubNav('bene-report-sub-nav');
	var beneListFromDetails = getValueById('beneListFromDetails');
	setValueById('beneListFromDetailsAlternate', beneListFromDetails);
	beneListFromDetails = JSON.parse(beneListFromDetails);
	if("true" == $('#payeeListCheck').val())
		$('#payee_system_message').css('display','block');
	else
		$('#payee_system_message').css('display','none');
	setFilterFieldsAsPerCriteria(beneListFromDetails);
});

/**
 * added to enable filter button on press of Enter key when search criteria is
 * selected in filter
 */
addEventListener("keyup", function(event) {
	if (event.keyCode == 13) {
		document.getElementById("bene_report_Filter").click();
	}
});

function isEmpty(value) {
	return (value === "" || value === undefined) ? true : false;
}

function getPayeeDetails(payeeid, accountNumber, organizationName, request) {
	var sourceApplication = "Atlas";
	var orgCode = organizationName;
	var accNumber = accountNumber;
	var payeeId = payeeid;
	var payee = {};
	var payeeDetailsRequest = {};
	addField('source_application', sourceApplication, payeeDetailsRequest);
	addField('payee', payee, payeeDetailsRequest);
	addField('org_code', orgCode, payee);
	addField('account_number', accNumber, payee);
	addField('payee_id', payeeId, payee);
	var searchCriteria = getPayeeSearchCriteriaObject();
	var columnIndex = $(request).parent().parent().index()+1;
	addField("current_record",setCurrentRecord(searchCriteria.page.min_record,columnIndex),searchCriteria.page);
	$('#searchCriteria').val(getJsonString(searchCriteria));
	$("#payeeDetailsRequest").val(getJsonString(payeeDetailsRequest));
	$("#payeeReportForm").submit();
}

function setCurrentRecord(minRecord,columnIndex) {
	return Number(minRecord) - 1 + columnIndex;
}

function getNextPage() {
	$('#payeeListTableBody,#pageCountDetails,#paginationBlock').css('visibility','hidden');
	$('#loadingobject').css('display','block');
	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	var searchCriteria = getCriteriaForNextPage();
	if (searchCriteria.page.current_page != null) {
		postBeneListPageAjax(searchCriteria, false);
	}
	if (searchCriteria.page.current_page == lastPage) {
		disableSpecificPaginationBlock("nextPage");
		disableSpecificPaginationBlock("lastPage");
	}
}

function getPreviousPage() {
	$('#payeeListTableBody,#pageCountDetails,#paginationBlock').css('visibility','hidden');
	$('#loadingobject').css('display','block');
	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");
	var searchCriteria = getCriteriaForPreviousPage();
	if (searchCriteria.page.current_page > 0) {
		postBeneListPageAjax(searchCriteria, false);
	}
	if (searchCriteria.page.current_page == 1) {
		disableSpecificPaginationBlock("firstPage");
		disableSpecificPaginationBlock("previousPage");
	}
}

function getSelectedPage(pageNumber) {
	$('#payeeListTableBody,#pageCountDetails,#paginationBlock').css('visibility','hidden');
	$('#loadingobject').css('display','block');
	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	var searchCriteria = getCriteriaForSelectedPage(pageNumber);

	postBeneListPageAjax(searchCriteria, false);

	if (pageNumber == 1) {
		disableSpecificPaginationBlock("firstPage");
		disableSpecificPaginationBlock("previousPage");
	} else if (pageNumber == lastPage) {
		disableSpecificPaginationBlock("nextPage");
		disableSpecificPaginationBlock("lastPage");
	}
}

function addCurrentPageForPagination(searchCriteria) {
	var searchCriteriaRequest = {};
	var page = {};
	var currentPage;
	var totalPages;
	currentPage = searchCriteria.page.current_page;
	totalPages = searchCriteria.page.total_pages;
	addField('page', page, searchCriteriaRequest);
	addField('current_page', currentPage, page);
	addField('total_pages', totalPages, page);
	return searchCriteriaRequest;
}

function postBeneListPageAjax(searchCriteriaRequest, isFilterReq) {
	$.ajax({
		url : '/compliance-portal/beneReportApply',
		type : 'POST',
		data : getJsonString(searchCriteriaRequest),
		contentType : "application/json",
		success : function(data) {
			setPayeeResponse(data, isFilterReq);
			$('#loadingobject').css('display','none');
			$('#payeeListTableBody,#pageCountDetails,#paginationBlock').css('visibility','visible');
		},
		error : function(data) {
			alert("Error resending", data);
		}
	});
}

function searchPayeeSearchCriteria() {
	$('#payee_system_message').css('display','none');
	$('#gifloaderforpayinfilter').css('visibility','visible');
	$('#payeeListTableBody,#pageCountDetails,#paginationBlock').css('visibility','hidden');
	$('#loadingobject').css('display','block');
	disableButton('bene_report_Filter');
	var searchCriteria;
	setFilterApply(true);
	var request = getPayeeSearchCriteriaObject();
	searchCriteria = request;
	$("searchCriteria").val(getJsonString)
	postPayeeAjax(request,isFilterApply);
}

function getPayeeSearchCriteriaObject() {
	var presort;
	if (searchQueueCriteria !== undefined && searchQueueCriteria !== null && searchQueueCriteria.filter !== undefined && searchQueueCriteria.filter !== null) {
		presort = searchQueueCriteria.filter.sort;
	}
	searchQueueCriteria = {};
	addField('filter', getPayeeFilterObject(), searchQueueCriteria);
	addField('page',getPayeeQueuePageObject(),searchQueueCriteria);
	addField('isFilterApply',isFilterApply,searchQueueCriteria);
	addField('isFromClearFilter',isFromClearFilter,searchQueueCriteria);
	
	if (searchQueueCriteria !== undefined && searchQueueCriteria !== null && searchQueueCriteria.filter !== undefined && searchQueueCriteria.filter !== null) {
		searchQueueCriteria.filter.sort = presort;
	}
	console.log('searchCriteria: ' + JSON.stringify(searchQueueCriteria));
	return searchQueueCriteria;
}

function getPayeeFilterObject() {
	var keywordInput = $("input[name=keyword]"); 
	var applyCfxEtailer = false;
	var filter = $('#beneFilterForm').serializeObject();
	console.log('Filter: ' + JSON.stringify(filter));
	return filter;
}

function postPayeeAjax(request,isFilterApply) {
	$.ajax({
		url : '/compliance-portal/beneReportApply',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setPayeeResponse(data,isFilterApply);
			if(!data.payee_list) {
				$('#gifloaderforpayinfilter').css('visibility','hidden');
				$('#loadingobject').css('display','none');
				/*$('#payee_system_message').css('display','block');*/
				enableButton('bene_report_Filter');
			}
			else
				setPayeeResponseMessage(data);
		}
	});
}

function setPayeeResponse(response, isFilterReq) {
	var rows = '';
	removePayeeData();
	$.each(response.payee_list, function(index, payee) {
		rows += getPayeeRow(payee, index + 1);
	});

	$("#payeeListTable").append(rows);

	if(null == response.search_criteria){
		var searchCriteria = {};
		var page = {};
		var currentPage = 1;
		var currentRecord = 0;
		var maxRecord = 0;
		var minRecord = 0;
		var pageSize = 20;
		var totalPages = 0;
		var totalRecords= 0;
		
		addField('page',page,searchCriteria);
		addField('current_page',currentPage,page);
		addField('max_record',maxRecord,page);
		addField('min_record',minRecord,page);
		addField('total_pages',totalPages,page);
		addField('total_records',totalRecords,page);
		if(true === response.isSystemNotAvailaible)
			$('#payee_system_message').css('display','block');
		else
			setPayeeQueuePageObjectText(searchCriteria.page, isFilterReq);
		$("#currentpage").val(searchCriteria.page.current_page);
		$("#searchCriteriaQueue").val(searchCriteria);
	}
	else {
		if(true === response.isSystemNotAvailaible)
			$('#payee_system_message').css('display','block');
		else
			setPayeeQueuePageObjectText(response.search_criteria.page, isFilterReq);
		$("#currentpage").val(response.search_criteria.page.current_page);
		$("#searchCriteriaQueue").val(response.search_criteria);
	}
}

function removePayeeData() {
	$("#payeeListTable").empty();
}

function getPayeeRow(payee, rowNumber) {
	var conId = payee.payee_id;
	var row;
	row = '<tr class="available talign">';
	$.each(payee.payee_payment_method_list,function(index, payeePaymentMethodList) {
						row += getPayeeLastDateColumn(conId,payee.account_number,payee.org_code,getEmptyIfNull(payeePaymentMethodList.payee_bank.updatedOnDateForUI));
						row += getPayeeOrganizationColumn(conId,payee.account_number, payee.org_code,getEmptyIfNull(payee.org_code));
						row += getPayeeNameColumn(conId, payee.account_number,payee.org_code, getEmptyIfNull(payee.fullName));
						row += getPayeeThirdPartyColumn(conId,payee.account_number, payee.org_code,getEmptyIfNull(payee.self));
						row += getPayeeAccountNumberColumn(conId,payee.account_number, payee.org_code,getEmptyIfNull(payeePaymentMethodList.payee_bank.account_number),getEmptyIfNull(payeePaymentMethodList.payee_bank.iban));
						row += getPayeeSwiftCodeColumn(conId,payee.account_number,payee.org_code,getEmptyIfNull(payeePaymentMethodList.payee_bank.bank_bic));
						row += getPayeeCurrencyColumn(conId,payee.account_number,payee.org_code,getEmptyIfNull(payeePaymentMethodList.payee_bank.payee_currency_code));
						row += getPayeeCountryColumn(conId,payee.account_number, payee.org_code,getEmptyIfNull(payee.country_name));
						row += getPayeeClientNameColumn(conId,payee.account_number,payee.org_code,getEmptyIfNull(payeePaymentMethodList.payee_bank.account_name));
						row += '</tr>';
					});
	return row;
}

function getPayeeLastDateColumn(payeeId, accountNumber, organizationName,lastPaidDate) {
	lastPaidDate = (lastPaidDate === null || lastPaidDate === "") ? '-------' : lastPaidDate;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\''	+ accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td><a onclick="' + payeeDetailsFunctionName + '" href="#" >' + lastPaidDate + '</a></td>';
}

function getPayeeOrganizationColumn(payeeId, accountNumber, organizationName,org_code) {
	org_code = (org_code === null || org_code === "") ? '-------' : org_code;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\''	+ accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td><a onclick="' + payeeDetailsFunctionName + '" href="#" >' + org_code + '</a></td>';
}

function getPayeeNameColumn(payeeId, accountNumber, organizationName, fullName) {
	fullName = (fullName === null || fullName === "") ? '-------' : fullName;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\''	+ accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td class="nowrap"><a onclick="' + payeeDetailsFunctionName	+ '" href="#" >' + fullName + '</a></td>';
}

function getPayeeThirdPartyColumn(payeeId, accountNumber, organizationName,self) {
	self = (self === null || self === "" || self == true) ? 'No' :'Yes' ;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\'' + accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td class="wordwrapfixwidth"><a onclick="' + payeeDetailsFunctionName + '" href="#" >' + self + '</a></td>';
}

function getPayeeAccountNumberColumn(payeeId, accountNumber, organizationName,account_number, iban) {
	account_number = (account_number === null || account_number === "") ? (iban === null || iban === "") ? '-------': iban : account_number;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\''	+ accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td class="nowrap"><a onclick="' + payeeDetailsFunctionName	+ '" href="#" >' + account_number + '</a></td>';
}

function getPayeeSwiftCodeColumn(payeeId, accountNumber, organizationName,bankBIC) {
	bankBIC = (bankBIC === null || bankBIC === "") ? '-------' : bankBIC;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\''	+ accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td><a onclick="' + payeeDetailsFunctionName + '" href="#" >'+ bankBIC + '</a></td>';
}

function getPayeeCurrencyColumn(payeeId, accountNumber, organizationName,payeeCurrencyCode) {
	payeeCurrencyCode = (payeeCurrencyCode === null || payeeCurrencyCode === "") ? '-------': payeeCurrencyCode;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\''	+ accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td><a onclick="' + payeeDetailsFunctionName + '" href="#" >'+ payeeCurrencyCode + '</a></td>';
}

function getPayeeCountryColumn(payeeId, accountNumber, organizationName,countryName) {
	countryName = (countryName === null || countryName === "") ? '-------': countryName;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\'' + accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td><a onclick="' + payeeDetailsFunctionName + '" href="#" >' + countryName + '</a></td>';
}

function getPayeeClientNameColumn(payeeId, accountNumber, organizationName,	accountName) {
	accountName = (accountName === null || accountName === "") ? '-------' : accountName;
	var payeeDetailsFunctionName = 'getPayeeDetails(' + payeeId + ',\'' + accountNumber + '\',\'' + organizationName + '\',this);'
	return '<td><a onclick="' + payeeDetailsFunctionName + '" href="#" >' + accountName + '</a></td>';
}

function setPayeeQueuePageObjectText(page, isFilterReq) {
	if (searchQueueCriteria === undefined) {
		searchQueueCriteria = {};
	}
	searchQueueCriteria['page'] = page;
	$("#queueMinRecord").text(page.min_record);
	$("#queueMaxRecord").text(page.max_record);
	$("#queueTotalRecords").text(page.total_records);
	$("#queueTotalPages").text(page.total_pages);
	$("#queueTotalPages").val(page.total_pages);
	$('#pageCountDetails').css('visibility','visible');
	$("#payeeListTableBody").css('visibility','visible');
	if (page.total_pages <= 1) {
		$("#paginationBlock").hide();
			if (isFilterReq) {
				$("#pageCountDetails").show();
			}
	} else {
		if (isFilterReq) {
			$("#pageCountDetails").show();
			$("#paginationBlock").show();
			setPaginationBlock(page);
			currentPage = 1;
			prePage = 1;
		}
	}
	return page;
}

function clearAndApplyFilter() {
	clearFilters();
	window["CurrenciesDirect"].Forms.resetForm('beneFilterForm');
	searchPayeeSearchCriteria();
}

function getPayeeQueuePageObject() {
	var page = {};
	var minRecord = Number($("#queueMinRecord").text());
	var maxRecord = Number($("#queueMaxRecord").text());
	var totalRecords = Number($("#queueTotalRecords").text());
	var totalPages = Number($("#queueTotalPages").val());
	currentPage= Number($("#currentpage").val());
	//var pageSize = REG_QUQUE_PAGE_SIZE;
	addField("min_record",minRecord,page);
	addField("max_record",maxRecord,page);
	addField("total_records",totalRecords,page);
	addField("current_page",currentPage,page);
	addField("total_pages",totalPages,page);
	//addField("pageSize",pageSize,page);
	console.log('page: '+ JSON.stringify(page));
	return page;
}

function getCriteriaForNextPage() {
	if(searchQueueCriteria === undefined ) {
		searchQueueCriteria = getPayeeSearchCriteriaObject();
	}
	var lastPage = Number($("#queueTotalPages").val());
	if (lastPage >= currentPage + 1) {
		prePage = currentPage;
		currentPage = currentPage + 1;
		if (searchQueueCriteria.page !== undefined) {
			searchQueueCriteria.page.current_page = currentPage;
		} else {
			searchQueueCriteria.page = getPayeeQueuePageObject();
		}
	}
	return searchQueueCriteria;
}

function getCriteriaForPreviousPage() {
	if(searchQueueCriteria === undefined ) {
		searchQueueCriteria = getPayeeSearchCriteriaObject();
	}
	if((currentPage - 1) > 0  ) {
		prePage = currentPage;
		currentPage = currentPage - 1;
		if ( searchQueueCriteria.page !== undefined) {
			searchQueueCriteria.page.current_page = currentPage;
		} else {
			searchQueueCriteria.page = getPayeeQueuePageObject();
		}
	}
	return searchQueueCriteria;
}

function getCriteriaForSelectedPage(pageNumber) {
	$('#currentpage').val(pageNumber);
	prePage = currentPage;
	currentPage = pageNumber;
	// Added isFilterForDashboard to apply filter and pagination from dashboard
	var isFilterForDashboard=$('#isDashboardSearchCriteria').val(); 
	if(searchQueueCriteria === undefined && (isFilterForDashboard === "false" 
		|| isFilterForDashboard ===undefined || isFilterForDashboard === "")) {
		searchQueueCriteria = getPayeeSearchCriteriaObject();
	}else if(isFilterForDashboard === "true"){
		searchQueueCriteria = getDashboardSearchCriteriaObject();
	}
		if (searchQueueCriteria.page !== undefined ) {
			searchQueueCriteria.page.current_page = currentPage;
		} else {
			searchQueueCriteria.page = getPayeeQueuePageObject();
	}
 return searchQueueCriteria;
}

function setPaginationBlock(page){
	$("#paginationBlock").empty();
	 $("#paginationBlock").append(getConstantPaginationBlock(page));
}

function getConstantPaginationBlock(page){
	//--disabled
	return '<li onclick="getSelectedPage(1)" class="pagination__jump" value="1"><a href="#"'+
		'title="First page"><i class="material-icons">first_page</i></a>'+
		'</li>'+
		'<li onClick="getPreviousPage()" class="pagination__jump"><a href="#"'+
			'title="Previous page"><i class="material-icons">navigate_before</i></a>'+
		'</li>'+
		'<li onClick="getNextPage()" class="pagination__jump"><a href="#"'+
			'title="Next page"><i class="material-icons">navigate_next</i></a></li>'+
		'<li  onClick="getSelectedPage('+page.total_pages+')" class="pagination__jump" value="'+page.total_pages+'"><a href="#"'+
			'title="Last page"><i class="material-icons">last_page</i></a></li>';
}

function getModifiablePaginationBlock(page){
	var block ='';
	for( var i=1 ; i<=page.total_pages ;i++){
		if( i === 1) {
			block += '<li id="page'+i+'" onClick="getSelectedPage('+(i)+')" class="pagination__page--on" value="'+(i)+'"><a href="#"'+
			'title="Page'+(i)+'">'+(i)+'</a></li>';
		} else {
			if(i === 11){
				block += '<li class="pagination__more"><a href="#"><i class="material-icons">more_horiz</i></a></li>';
			}
			if(i >= 11 && page.total_pages !== i){
				block += '<li id="page'+i+'" onClick="getSelectedPage('+(i)+')" class="pagination__page pagination__page--hidden"" value="'+(i)+'"><a href="#"'+
				'title="Page'+(i)+'">'+(i)+'</a></li>';
			} else {
				block += '<li id="page'+i+'" onClick="getSelectedPage('+(i)+')" class="pagination__page" value="'+(i)+'"><a href="#"'+
				'title="Page'+(i)+'">'+(i)+'</a></li>';
			}
		}
		
	}
	return block;
}

function populateSelectedPage(){
	$("#page"+currentPage).removeClass('pagination__page');
	$("#page"+currentPage).addClass('pagination__page--on');
	if(prePage !== currentPage ) {
		$("#page"+prePage).removeClass('pagination__page--on');
		$("#page"+prePage).addClass('pagination__page');
	}
}

/***
 * whenever filter gets apply make isFilterApply true 
 * if isFilterApply true then default queue date time criteria is not applied
 * @param applyFilter
 */
function setFilterApply(applyFilter){
	isFilterApply = applyFilter;
}

/**
 * set isRequestFromReportQueue true if details page request is from report queue 
 * @param isRequestFromReport
 */
function setRequestOrigin(isRequestFromReport){
	isRequestFromReportQueue = isRequestFromReport;
}

function getQueueFilterObject() {
	var keywordInput = $("input[name=keyword]");
	var applyCfxEtailer = false;
	var filter = $('#queueFilterForm').serializeObject();
	console.log('Filter: '+ JSON.stringify(filter));
	return filter;
}

function setCustTypeCriteriaAsPerClientId(keywordValue){
	var ele;
	if(isCfxClientId(keywordValue)){
		ele =  $( "input[name='custType[]'][value='CFX']" );
		  if(ele.length > 0){
		    	ele.prop('checked', true);
		    	ele.trigger("change");
		    	return true;
		    }
		/*ele =  $( "input[name='custType[]'][value='CFX (etailer)']" );
		  if(ele.length > 0){
		    	ele.prop('checked', true);
		    	ele.trigger("change");
		    }*/
	} else {
		 ele =  $( "input[name='custType[]'][value='PFX']" );
		  if(ele.length > 0){
		    	ele.prop('checked', true);
		    	ele.trigger("change");
		    }
	}
}

function isCfxClientId(keywordValue){
	if(keywordValue.indexOf("I") !== -1){
		return false;
	} else {
		return true;
	}
}

function getSortObjectWithSearchCriteria(fieldName) {
	currentPage = 1;
	$('#currentpage').val(currentPage);
	setFilterApply(true);
	if(searchQueueCriteria === undefined || searchQueueCriteria.filter === undefined || searchQueueCriteria.sort === undefined){
		var isDefaultDescSort = $("#col_"+fieldName).hasClass("default-sort-desc");
		var isDefaultAscSort = $("#col_"+fieldName).hasClass("default-sort-asc");
		if(isDefaultDescSort){
			$("#col_"+fieldName).data("isAsc",false);
		}
		if(isDefaultAscSort){
			$("#col_"+fieldName).data("isAsc",true);
		}
	}
	var isAsc = $("#col_"+fieldName).data("isAsc");
	isAsc = updateSortIcon(isAsc,fieldName);
	var sort = {};
	var filter = {};
	addField("is_ascend",isAsc,sort);
	addField("field_name",fieldName,sort);
	if(searchQueueCriteria === undefined || searchQueueCriteria.page === undefined || searchQueueCriteria.page === null){
		searchQueueCriteria = getPayeeSearchCriteriaObject();
	} 
	if(searchQueueCriteria !== undefined){
		addField("sort",sort,searchQueueCriteria);
		if(searchQueueCriteria.filter === undefined){
			addField("filter",filter,searchQueueCriteria);
		}
		if(searchQueueCriteria.page !== undefined){
			searchQueueCriteria.page.current_page = 1;
		}
	}
	return searchQueueCriteria;
}

function updateSortIcon(isAsc,fieldName){
	$('*[id*=col_'+fieldName+']').each(function() {
	    $(this).attr("class", "");
	});
	if(isAsc === undefined || isAsc === false) {
		isAsc = true;
		$('*[id*=col_'+fieldName+']').each(function() {
		    $(this).attr("class", "sorted asc");
		});
	} else {
		isAsc = false;
		$('*[id*=col_'+fieldName+']').each(function() {
		    $(this).attr("class", "sorted desc");
		});
	}
	$("#col_"+fieldName).data("isAsc",isAsc);
	return isAsc;
}

function setSortIcon(isAsc,fieldName){
	$('*[id*=col_'+fieldName+']').each(function() {
	    $(this).attr("class", "");
	});
	if(isAsc === undefined || isAsc === false) {
		$('*[id*=col_'+fieldName+']').each(function() {
		    $(this).attr("class", "sorted desc");
		});
	} else {
		$('*[id*=col_'+fieldName+']').each(function() {
		    $(this).attr("class", "sorted asc");
		});
	}
	$("#col_"+fieldName).data("isAsc",isAsc);
	return isAsc;
}

function clearFilters(){
	window["CurrenciesDirect"].Forms.resetForm('queueFilterForm');
	window["CurrenciesDirect"].Tables.removeTableFiltersList();
	$(".pill-choice__choice--on").removeClass("pill-choice__choice--on");
    $('.multilist__search').val('');
	$('.multilist__options li').removeClass("hidden");
	$('.singlelist__search').val('');
	$('.singlelist__options li').removeClass("hidden");
	if(searchQueueCriteria !== undefined && searchQueueCriteria.filter !== undefined){
		searchQueueCriteria.filter.sort = undefined;
	}
}

function initUserLockedPopup(){
	Opentip.findElements();
}

$(document).on('change','.filterInput', function() {
	if($(this).is('input[type="checkbox"]:checked')){
		addChosenFilterCheckbox($(this));
	} else if($(this).is('input[type="checkbox"]')) {
		removeChosenFilterCheckbox($(this));
	} else {
		var value = $(this).val();
		if(value.length === 0){
			removeChosenFilterCheckbox($(this));
		} else {
			addChosenFilterCheckbox($(this));
		}
	}
		setFilterAppliedHeader();
});

$(document).on('click','#queueFilterForm .multilist__pill', function() {
			var chkBx = $(this).hasClass('multilist__pill') ? $('#'+$(this).data('id-to-remove')) : $(this)
			removeChosenFilterCheckbox(chkBx);
			setFilterAppliedHeader();
});

function addChosenFilterCheckbox(filterInput){
	var filterName = getFilterText(filterInput);
	var count = getCountOfFilter(filterName);
	if(count === undefined || count === null || count === 0){
		if($('#addedFilters .section--results').length === 0){
			addAppliedFilterBlock(filterName);
			incCountOfFilter(filterName);
		} else {
			appendAddedFilter(filterName);
			incCountOfFilter(filterName);
		}
	} else {
		incCountOfFilter(filterName);
	}
}

function removeChosenFilterCheckbox(filterInput){
	var filterName = getFilterText(filterInput);
	var count = getCountOfFilter(filterName);
	if(count === 1){
		removeDeletedFilter(filterName);
		decCountOfFilter(filterName);
	} else {
		decCountOfFilter(filterName);
	}
}

function addChosenFilterText(filterInput){}

function removeChosenFilterText(filterInput){}

function addAppliedFilterBlock(filterName){
	var block = '<section class="section--results">'+
		'<h2 id="totalfilteredresults"></h2>'+ 
		'<p class="flush-margin"><strong id="filterCount">1 filters applied</strong></p>'+
		'<ul class="containing space-after delete-list">'+
			'<li id="filter'+getFilterNameWithoutSpace(filterName)+'"><a href="#"><i class="material-icons icon--medium">close</i></a>'+filterName+'</li>'+
		'</ul>'+
		'<a href="#" class="button--secondary button--small" onclick="clearAndApplyFilter()">Clear all</a>'+
		/*'<img id="ajax-loader-clear-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/>'+*/
		'<span class="section--results-arrow"></span>'+
	'</section>';
	$('#addedFilters').append(block);
}

function appendAddedFilter(filterName){
	var liBlock = '<li id="filter'+getFilterNameWithoutSpace(filterName)+'"><a href="#"><i class="material-icons icon--medium">close</i></a>'+filterName+'</li>';
	$('#addedFilters ul').append(liBlock);
}

function removeDeletedFilter(filterName){
	$("#filter"+getFilterNameWithoutSpace(filterName)).remove();
}

function incCountOfFilter(filterName){
	var count = getCountOfFilter(filterName);
	if(count === undefined || count === null){
		count = 1;
	} else {
		count++;
	}
	setCountOfFilter(filterName,count);
}

function decCountOfFilter(filterName){
	var count = getCountOfFilter(filterName);
	if(count === undefined || count === null){
		count = 0;
	} else {
		count--;
	}
	setCountOfFilter(filterName,count);
}

function getCountOfFilter(filterName){
	return  $("#filter"+getFilterNameWithoutSpace(filterName)).data("count");
}

function setCountOfFilter(filterName,count){
	$("#filter"+getFilterNameWithoutSpace(filterName)).data("count",count);
}

function getFilterText(filterInput){
	return $(filterInput).closest('.filterInputWrap').find('.filterInputHeader').text();
}

function getFilterHeader(filterInput){
	return $(filterInput).closest('.filterInputWrap').find('.filterInputHeader');
}

function getFilterNameWithoutSpace(filterName){
	return filterName.split(' ').join('');
}

function getCountOfAppliedFilter(){
	return $('#addedFilters .section--results ul li').length;
}

function setFilterAppliedHeader(){
  $("#filterCount").text(getCountOfAppliedFilter()+' filters applied');
}

function getDashboardSearchCriteriaObject(){
	var searchQueueCriteria = {};
	addField('filter',getDashboardFilterObject(),searchQueueCriteria);
	addField('page',getPayeeQueuePageObject(),searchQueueCriteria);
	console.log('searchCriteria: '+ JSON.stringify(searchQueueCriteria));
	return searchQueueCriteria;
}

function getDashboardFilterObject(){
	var filter={};
	var dashboardCustType=$('#custTypeForDashboardFilter').val();
	var custType=[dashboardCustType];
	addField('custType',custType,filter);
	return filter;
}
//this function is called to display the total records found in add applied filter block -Neelesh pant
function getTotalRecordsAfterApplyingFilter(){
	 var total = $('#queueTotalRecords').text();
	 $('#totalfilteredresults').html(total+ '  ' + 'records found');
}

function setFilterFieldsAsPerCriteria(detailsToList){
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to, #value-date-payment-from, #value-date-payment-to").datepicker('option', 'dateFormat', 'dd/mm/yy');
	var searchCriteria = getJsonObject(getValueById('searchCriteriaQueue'));
	setPayeeQueuePageObjectText(searchCriteria.page,searchCriteria.isFilterApply);
	var currPg =1 ;
	if(searchCriteria.page !== undefined && searchCriteria.page !== null){
		currPg = searchCriteria.page.current_page;
		currentPage = currPg;
	}
	if(currPg === undefined){
		currPg = 1;
	}
	$('#currentpage').val(currPg);
	if(currPg==1)
		{
		disableSpecificPaginationBlock("firstPage");
		disableSpecificPaginationBlock("previousPage");
		}
	
	if(!searchCriteria.isFilterApply){
		return;
	}
	setFilterApply(searchCriteria.isFilterApply);
	searchCriteria.filter.userProfile = null;
	for(var name in searchCriteria.filter) {
	    var value = searchCriteria.filter[name];
	    if(value === null || value === undefined || value === ''){
	    	continue;
	    }
	    for(var v in value) {
	    	 var ele =  $( "input[name='"+name+"[]'][value='"+value[v]+"']" );
	 	    if(ele.length > 0){
	 	    	ele.prop('checked', true);
	 	    	ele.trigger("change");
	 	    	continue;
	 	    }
	    }
	    if(ele.length === 0){
	    	ele = $( "input[name='"+name+"']" );
	    	ele.val(value);
	    	ele.trigger("change");
	    }
	}
	var isFromDetails = $('#isFromDetails').val();
	if(isFromDetails === 'true'){
		$('#paginationBlock').css('display','none');
		$('#pageCountDetails').css('display','none');
	}
	searchQueueCriteria = searchCriteria;
	if(searchQueueCriteria !== undefined 
			&& searchQueueCriteria.filter !== undefined
			&& searchQueueCriteria.filter.sort !== undefined
			&& searchQueueCriteria.filter.sort.fieldName !== undefined){
		setSortIcon(searchQueueCriteria.filter.sort.isAscend, searchQueueCriteria.filter.sort.fieldName)
	}
}

/***
 * whenever filter gets apply make isFilterApply true 
 * if isFilterApply true then default queue date time criteria is not applied
 * @param applyFilter
 */
function isRequestFromClearAllFilters(clearFilter){
	isFromClearFilter = clearFilter;
}

function clearFiltersExceptKeywordSearch(){
	window["CurrenciesDirect"].Tables.removeTableFiltersList();
	$(".pill-choice__choice--on").removeClass("pill-choice__choice--on");
    $('.multilist__search').val('');
	$('.multilist__options li').removeClass("hidden");
	$('.singlelist__search').val('');
	$('.singlelist__options li').removeClass("hidden");
	if(searchQueueCriteria !== undefined && searchQueueCriteria.filter !== undefined){
		searchQueueCriteria.filter.sort = undefined;
	}
}

function sortByField(fieldName){
	var searchCriteria =  getSortObjectWithSearchCriteria(fieldName);
	postPayeeAjax(searchCriteria,true);
}

function setPayeeResponseMessage(data){
	$('#payee_system_message').css('display','none');
	$("#searchCriteriaQueue").val(data.searchCriteriaForUI);
	$('#gifloaderforpayinfilter').css('visibility','hidden');
	$('#loadingobject').css('display','none');
	$('#payeeListTableBody,#pageCountDetails,#paginationBlock').css('visibility','visible');
	enableButton('bene_report_Filter');
}