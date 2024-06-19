var searchQueueCriteria;
var REG_QUQUE_PAGE_SIZE = 50;
var currentPage=1;
var prePage = 1;
var isFilterApply=false;
var isRequestFromReportQueue=false;
var isFromClearFilter = false;
var isOnFocusCalled = false;
var selectedSaveSearchId = "";
var pageType;
var defaultDateFilter=true;

$(document).ready(
		function() {
			if (("" !== $("#saveSearchName-4").val() || null !== $(
					"#saveSearchName-4").val())
					&& 'Please select' === removeWhiteSpaces($(
							'#rad_save_selected').text())
					&& undefined !== $("#saveSearchName-4").val() && 
					$('#saveSearchIndex-4').val() >= 4) {
				$('#save_search_button').addClass('button--disabled');
			}
			
			if (undefined === $("#saveSearchName-0").val()){
				$('#delete_search_button').addClass('button--disabled');
			}
			if($('#pageType').val()== "RegistrationReport" || $('#pageType').val()=="PaymentInReport" || $('#pageType').val()=="PaymentOutReport"){
			if(defaultDateFilter) {
	        	setDefaultDateValue();
	        	$('#defaultMonth').css('visibility','visible');
	        }
	        }
});

function setDefaultSortCriteria(){}

function getCriteriaForNextPage() {
	if(searchQueueCriteria === undefined ) {
		searchQueueCriteria = getQueueSearchCriteriaObject();
	}
	var lastPage = Number($("#queueTotalPages").val());
	if (lastPage >= currentPage + 1) {
		prePage = currentPage;
		currentPage = currentPage + 1;
		if (searchQueueCriteria.page !== undefined) {
			searchQueueCriteria.page.currentPage = currentPage;
		} else {
			searchQueueCriteria.page = getQueuePageObject();
		}
	}
	return searchQueueCriteria;
}

function getCriteriaForPreviousPage() {
	if(searchQueueCriteria === undefined ) {
		searchQueueCriteria = getQueueSearchCriteriaObject();
	}
	
	if((currentPage - 1) > 0  ) {
		prePage = currentPage;
		currentPage = currentPage - 1;
		if ( searchQueueCriteria.page !== undefined) {
			searchQueueCriteria.page.currentPage = currentPage;
		} else {
			searchQueueCriteria.page = getQueuePageObject();
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
		searchQueueCriteria = getQueueSearchCriteriaObject();
	}else if(isFilterForDashboard === "true"){
		searchQueueCriteria = getDashboardSearchCriteriaObject();
	}
		if (searchQueueCriteria.page !== undefined ) {
			searchQueueCriteria.page.currentPage = currentPage;
			searchQueueCriteria.page.current_page = currentPage;
		} else {
			searchQueueCriteria.page = getQueuePageObject();
	}
 return searchQueueCriteria;
}

function setQueuePageObjectText(page,isFilterReq) {
	if(searchQueueCriteria === undefined){
		searchQueueCriteria = {};
	}
	searchQueueCriteria['page'] = page;
	$("#queueMinRecord").text(page.minRecord);
	$("#queueMaxRecord").text(page.maxRecord);
	$("#queueTotalRecords").text(page.totalRecords);
	$("#queueTotalPages").text(page.totalPages);
	$("#queueTotalPages").val(page.totalPages);
	if(page.totalPages <= 1 ){
		$("#paginationBlock").hide();
	} else {
		$("#paginationBlock").show();
		if(isFilterReq){
			setPaginationBlock(page);
			currentPage = 1;
			prePage = 1;
		}/* else {
			populateSelectedPage();
		}*/
	}
	
	return page;
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
		'<li  onClick="getSelectedPage('+page.totalPages+')" class="pagination__jump" value="'+page.totalPages+'"><a href="#"'+
			'title="Last page"><i class="material-icons">last_page</i></a></li>';
}

function getModifiablePaginationBlock(page){
	var block ='';
	for( var i=1 ; i<=page.totalPages ;i++){
		if( i === 1) {
			block += '<li id="page'+i+'" onClick="getSelectedPage('+(i)+')" class="pagination__page--on" value="'+(i)+'"><a href="#"'+
			'title="Page'+(i)+'">'+(i)+'</a></li>';
		} else {
			if(i === 11){
				block += '<li class="pagination__more"><a href="#"><i class="material-icons">more_horiz</i></a></li>';
			}
			if(i >= 11 && page.totalPages !== i){
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



function getQueueSearchCriteriaObject() {
	var presort;
	if(searchQueueCriteria !== undefined && searchQueueCriteria !== null && searchQueueCriteria.filter !== undefined && searchQueueCriteria.filter !== null){
		presort = searchQueueCriteria.filter.sort;
	}
	searchQueueCriteria = {};
	addField('filter',getQueueFilterObject(),searchQueueCriteria);
	addField('page',getQueuePageObject(),searchQueueCriteria);
	addField('isFilterApply',isFilterApply,searchQueueCriteria);
	addField('isRequestFromReportPage',isRequestFromReportQueue,searchQueueCriteria);
	addField('isFromClearFilter',isFromClearFilter,searchQueueCriteria);
	
	if(searchQueueCriteria !== undefined && searchQueueCriteria !== null && searchQueueCriteria.filter !== undefined && searchQueueCriteria.filter !== null){
		searchQueueCriteria.filter.sort = presort;
	}
	console.log('searchCriteria: '+ JSON.stringify(searchQueueCriteria));
	return searchQueueCriteria;
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
	
	//AT-3319
	if(((filter.keyword!=undefined))&&(defaultDateFilter==true))
	{
		if(($('#pageType').val()== "RegistrationReport") || $('#pageType').val()=="PaymentInReport" || $('#pageType').val()=="PaymentOutReport")
			{
			 filter.dateTo="";
			  filter.dateFrom="";
			  resetDateText();
				addDatePicker();
				defaultDateFilter=false;
				$('#defaultMonth').css('visibility','hidden');
			}
	}
	else if((filter.keyword==undefined)&&(defaultDateFilter==false)&&(filter.dateFilterType==undefined))
	{
		setDefaultDateValue();
		if(($('#pageType').val()== "RegistrationReport"))
			{
			filter.dateTo=$("#date-registered-to").val();
			filter.dateFrom=$("#date-registered-from").val(); 
			} 
		if(($('#pageType').val()== "PaymentInReport")||($('#pageType').val()== "PaymentOutReport"))
		{
		filter.dateTo=$("#date-payment-to").val();
		filter.dateFrom=$("#date-payment-from").val(); 
		} 
		defaultDateFilter=true;
		$('#defaultMonth').css('visibility','visible');
	}
	else if(filter.dateFilterType!=undefined)
	{
	$('#defaultMonth').css('visibility','hidden');
	}
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

function getQueuePageObject() {
	var page = {};
	var minRecord = Number($("#queueMinRecord").text());
	var maxRecord = Number($("#queueMaxRecord").text());
	var totalRecords = Number($("#queueTotalRecords").text());
	var totalPages = Number($("#queueTotalPages").val());
	
	currentPage= Number($("#currentpage").val());
	//var pageSize = REG_QUQUE_PAGE_SIZE;
	addField("minRecord",minRecord,page);
	addField("maxRecord",maxRecord,page);
	addField("totalRecords",totalRecords,page);
	addField("currentPage",currentPage,page);
	addField("totalPages",totalPages,page);
	//addField("pageSize",pageSize,page);
	console.log('page: '+ JSON.stringify(page));
	
	return page;
}


function getSortObjectWithSearchCriteria(fieldName) {
	currentPage = 1;
	$('#currentpage').val(currentPage);
	setFilterApply(true);
	if(searchQueueCriteria === undefined || searchQueueCriteria.filter === undefined || searchQueueCriteria.filter.sort === undefined){
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
	addField("isAscend",isAsc,sort);
	addField("fieldName",fieldName,sort);
	if(searchQueueCriteria === undefined || searchQueueCriteria.page === undefined || searchQueueCriteria.page === null){
		searchQueueCriteria = getQueueSearchCriteriaObject();
	} 
	
	if(searchQueueCriteria !== undefined){
		addField("sort",sort,searchQueueCriteria.filter);
		if(searchQueueCriteria.page !== undefined){
			searchQueueCriteria.page.currentPage = 1;
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

function addChosenFilterText(filterInput){
}

function removeChosenFilterText(filterInput){
	
}


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
	addField('page',getQueuePageObject(),searchQueueCriteria);
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

function setFilterFieldsAsPerCriteria(){
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to, #value-date-payment-from, #value-date-payment-to").datepicker('option', 'dateFormat', 'dd/mm/yy');
	var searchCriteria = getJsonObject(getValueById('searchCriteriaQueue'));
	var currPg =1 ;
	if(searchCriteria.page !== undefined && searchCriteria.page !== null){
		currPg = searchCriteria.page.currentPage;
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
		$('#paginationBlock').css('display','block');
		$('#pageCountDetails').css('display','block');
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
/*$( "#txt-search-keyword" )
.focusout(function() {
	if(!isOnFocusCalled){
		isOnFocusCalled = true;
		setCustTypeCriteriaAsPerClientId($( "#txt-search-keyword" ).val());
	}
});
*/

function applySelectedSavedSearch(id) {
	$('#save_search_button').removeClass('button--disabled');
	clearFilters();
	selectedSaveSearchId = id;
	var filter = $('#'+id).val();
	$('#rad_save_selected').text($('#label-'+id).text());
	filter = getJsonObject(filter);
	if(filter.hasOwnProperty('dateFilterType')) {
		if(!(filter['dateFilterType'] == 'Custom')) {
			filter = adjustDates(filter);
		}
	}
	for(var name in filter) {
	    var value = filter[name];
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
	if('RegistrationReport' === $('#pageType').val() || 'RegistrationQueue' === $('#pageType').val())
		applyRegQueueSearchCriteria();
	if('PaymentInReport' === $('#pageType').val() || 'PaymentInQueue' === $('#pageType').val())
		searchPayInQueueSearchCriteria();
	if('PaymentOutReport' === $('#pageType').val() || 'PaymentOutQueue' === $('#pageType').val())
		applyPaymentOutSearchCriteria();
	if('DataAnonymisation' === $('#pageType').val())
		applyDataAnonQueueSearchCriteria();
}

function savedSearch(request) {
	var saveSearchName = removeWhiteSpaces($("#saveSearchName").val());
	var pageType = $('#pageType').val();
	addField('pageType',pageType,request);
	addField('saveSearchName',saveSearchName,request);
	
	$.ajax({
		url: '/compliance-portal/savedSearch',
		type: 'POST',
		data: getJsonString(request),
		contentType: "application/json",
		success: function(data) {
			document.location.reload();
		},
		error: function(data) {
		}
	});
}

function deleteSaveSearchPopup() {
	var saveSearchName = removeWhiteSpaces($('#rad_save_selected').text());
	if('Please select' == saveSearchName){
		alert("Please select a saved search to delete");
		return;
	}
	$('#confirmSaveDelete').text(saveSearchName);
	$("#deleteSearchPopups").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-800),
		height : ($(window).height()-470),
		dialogClass : 'ui-dialog-osx',
		buttons :[{
			text: 'OK',
			click : function() {
				deleteSavedSearch(saveSearchName);
				$(this).dialog("close");
			}
		},
		{
			text: 'Cancel',
			click : function() {
				$(this).dialog("close");
			}
		}]
	});
}

function saveSearchPopup(){
	var request = getQueueSearchCriteriaObject();
	delete request.filter.sort;
	delete request.filter.savedSearch;
	if( request.filter.hasOwnProperty('dateFilterType') && (request.filter.dateFilterType[0] === 'Custom' && !request.filter.hasOwnProperty('dateFrom') && !request.filter.hasOwnProperty('dateTo'))) {
		alert("Please set dateFrom and dateTo");
		return;
	}
	if(Object.keys(request.filter).length == 0) {
		alert("Please select some filters first");
		return;
	}
	$("#errorDiv").css('display', 'none');
	$("#saveSearchName").val('');
	var saveSearchName = removeWhiteSpaces($('#rad_save_selected').text());
	if('Please select' == saveSearchName){
		$("#saveSearchPopups").dialog({
			modal : true,
			draggable : false,
			resizable : false,
			show : 'blind',
			hide : 'blind',
			width : (500),
			height : (280),
			dialogClass : 'ui-dialog-osx',
			buttons :[{
				text : 'OK',
				id : 'save-search-OK',
				click : function() {
					if (checkIfOnlySpaceEntered('saveSearchName')) {
						$("#saveSearchName").val('');
						$("#errorDescription").text("Please enter the name");
						$("#errorDiv").css('display', 'block');
					} else {
						if (!checkIfNameAllreadyExist($("#saveSearchName").val())) {
							savedSearch(request);
							$(this).dialog("close");
						}
					}
				}
			},
			{	
				text: 'Cancel',
				click : function() {
					enableButton('reg_report_Filter');
					enableButton('payIn_report_Filter');
					enableButton('payOut_report_Filter');
					enableButton('reg_queue_Filter');
					enableButton('payIn_queue_Filter');
					enableButton('payOut_queue_Filter');
					$(this).dialog("close");
					document.location.reload();
				}
			}],
			open: function() {
				disableButton('reg_report_Filter');
				disableButton('payIn_report_Filter');
				disableButton('payOut_report_Filter');
				disableButton('reg_queue_Filter');
				disableButton('payIn_queue_Filter');
				disableButton('payOut_queue_Filter');
			    $("#saveSearchPopups").on('keypress',function(e) {
			      if (e.keyCode == $.ui.keyCode.ENTER) {
			        $('#save-search-OK').trigger("click");
			      }
			    });
			 }
		});
	}
	else {
		$('#confirmSaveUpdate').text(saveSearchName);
		$('#confirmSaveUpdate').attr('readonly',true);
		$('#confirmSaveUpdate').addClass('input-disabled');
		var newFilter = request.filter;
		var existingFilter = getJsonObject($('#'+selectedSaveSearchId).val());
		if(isObjectEquals(newFilter,existingFilter)) {
			alert("No changes in the existing filter");
			return;
		}
		updateSavedSearchPopup(saveSearchName,request);
	}
}

function updateSavedSearchPopup(saveSearchName,request){
	$("#updateSearchPopup").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-800),
		height : ($(window).height()-470),
		dialogClass : 'ui-dialog-osx',
		buttons :[{
			text: 'Yes',
			click : function() {
				updateSavedSearch(saveSearchName,request);
				$(this).dialog("close");
			}
		},
		{
			text: 'No',
			click : function() {
				$(this).dialog("close");
			}
		}]
	});
}

function updateSavedSearch(saveSearchName,request){
	var pageType = $('#pageType').val();
	addField('pageType',pageType,request);
	addField('saveSearchName',saveSearchName,request);
	
	$.ajax({
		url: '/compliance-portal/updateSavedSearch',
		type: 'POST',
		data: getJsonString(request),
		contentType: "application/json",
		success: function(data) {
			document.location.reload();
		},
		error: function(data) {
		}
	});
}

function deleteSavedSearch(saveSearchName) {
	var request = {};
	var pageType = $('#pageType').val();
	addField('pageType',pageType,request);
	addField('saveSearchName',saveSearchName,request);
	
	$.ajax({
		url: '/compliance-portal/deleteSavedSearch',
		type: 'POST',
		data: getJsonString(request),
		contentType: "application/json",
		success: function(data) {
			document.location.reload();
		},
		error: function(data) {
		}
	});
}

function resetDateText() {
	if(elementAvailable("date-payment-from")) {
		document.getElementById("date-payment-from").value = '';
	}
	if(elementAvailable("date-payment-to")) {
		 document.getElementById("date-payment-to").value = '';
	}
	if(elementAvailable("date-registered-from")) {
		document.getElementById("date-registered-from").value = '';
	}
	if(elementAvailable("date-registered-to")) {
		document.getElementById("date-registered-to").value = '';
	}
}

function setTodayDateValue() {
	addDatePicker();
	var currentDate = new Date();
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to").datepicker("setDate",currentDate);
	defaultDateFilter=false;
	removeDatePicker();
}

function setYesterdayDateValue() {
	addDatePicker();
	var previousdate = new Date();
	previousdate.setDate(previousdate.getDate() - 1);  
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to").datepicker("setDate",previousdate);
	defaultDateFilter=false;
	removeDatePicker();
}

function setThisWeekDateValue() {
	addDatePicker();
	var currentDate = new Date();
	var diff = currentDate.getDate() - currentDate.getDay() + (currentDate.getDay() === 0 ? -6 : 1);
	var tdate = new Date();
	var firstDateOfWeek = new Date(tdate.setDate(diff));
	$("#date-payment-from, #date-registered-from").datepicker("setDate",firstDateOfWeek);
	$("#date-payment-to, #date-registered-to").datepicker("setDate",currentDate);
	defaultDateFilter=false;
	removeDatePicker();
}

function setThisMonthDateValue() {
	addDatePicker();
	var currentDate = new Date();
	var firstDateOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
	$("#date-payment-from, #date-registered-from").datepicker("setDate",firstDateOfMonth);
	$("#date-payment-to, #date-registered-to").datepicker("setDate",currentDate);
	defaultDateFilter=false;
	removeDatePicker();
}

function setThisYearDateValue() {
	addDatePicker();
	var currentDate = new Date();
	var firstDateOfYear = new Date(currentDate.getFullYear(), 0, 1);
	$("#date-payment-from, #date-registered-from").datepicker("setDate",firstDateOfYear);
	$("#date-payment-to, #date-registered-to").datepicker("setDate",currentDate);
	defaultDateFilter=false;
	removeDatePicker();
}

function setCustomDateValue() {
	addDatePicker();
	defaultDateFilter=false;
	resetDateText();
}

function setDefaultDateValue() {
	addDatePicker();
	var currentDate = new Date();
	var defaultDate = new Date();
	defaultDate.setMonth(defaultDate.getMonth()-6);
	$("#date-payment-from, #date-registered-from").datepicker("setDate",defaultDate);
	$("#date-payment-to, #date-registered-to").datepicker("setDate",currentDate);
	removeDatePicker();
}

function setDateValue(filter, dateFrom, dateTo) {
	filter['dateFrom'] = formatDate(dateFrom);
	filter['dateTo'] = formatDate(dateTo);
	return filter;
}

function adjustDates(filter) {
	var currentDate = new Date();
	if(filter['dateFilterType'] == 'Today') {
		filter = setDateValue(filter, currentDate, currentDate);
	}
	if(filter['dateFilterType'] == 'Yesterday') {
		var previousdate = new Date();
		previousdate.setDate(previousdate.getDate() - 1);
		filter = setDateValue(filter, previousdate, previousdate);
	}
	if(filter['dateFilterType'] == 'ThisWeek') {
		var diff = currentDate.getDate() - currentDate.getDay() + (currentDate.getDay() === 0 ? -6 : 1);
		var tdate = new Date();
		var firstDateOfWeek = new Date(tdate.setDate(diff));
		filter = setDateValue(filter, firstDateOfWeek, currentDate);
	}
	if(filter['dateFilterType'] == 'ThisMonth') {
		var firstDateOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
		filter = setDateValue(filter, firstDateOfMonth, currentDate);
	}
	if(filter['dateFilterType'] == 'ThisYear') {
		var firstDateOfYear = new Date(currentDate.getFullYear(), 0, 1);
		filter = setDateValue(filter, firstDateOfYear, currentDate);
	}
	return filter;
}

function checkIfNameAllreadyExist(saveSearchName){
	if(undefined !== $("#saveSearchName-0").val())
		var index0 = removeWhiteSpaces($("#saveSearchName-0").val().toUpperCase());
	if(undefined !== $("#saveSearchName-1").val())
		var index1 = removeWhiteSpaces($("#saveSearchName-1").val().toUpperCase());
	if(undefined !== $("#saveSearchName-2").val())
		var index2 = removeWhiteSpaces($("#saveSearchName-2").val().toUpperCase());
	if(undefined !== $("#saveSearchName-3").val())
		var index3 = removeWhiteSpaces($("#saveSearchName-3").val().toUpperCase());
	if(undefined !== $("#saveSearchName-4").val())
		var index4 = removeWhiteSpaces($("#saveSearchName-4").val().toUpperCase());
	saveSearchName = removeWhiteSpaces(saveSearchName.toUpperCase());
	if (saveSearchName === index0 || saveSearchName === index1
			|| saveSearchName === index2 || saveSearchName === index3
			|| saveSearchName === index4){
		$("#errorDescription").text("Name already exists");
		$("#errorDiv").css('display', 'block');
		return true;
	}
	else
		return false;
}

function removeDatePicker(){
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to").datepicker("destroy");
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to").attr('readonly',true);
}

function addDatePicker(){
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to").removeAttr('readonly');
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to, #value-date-payment-from, #value-date-payment-to").datepicker();
}