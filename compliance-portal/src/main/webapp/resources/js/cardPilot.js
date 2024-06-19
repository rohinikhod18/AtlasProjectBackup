function cardPilot() {
	var currentPage = 1;
	var tbody=$('#cardPilotTablebody');
	
	if (tbody.children().length == 0) {
			
			$('#cardPilotLoadingGIF').css('display','block');
			var request = {};
			var cardPilotSearchCriteria={};
			var page={};
			var sort={};
			var filter ={};
			var size=10;									
			var tradeAccountNumber = $('#account_tradeAccountNum').text();
			var orgCode = $('#account_organisation').text();
			addField('account_number', tradeAccountNumber, request);
			addField('org_code', orgCode, request);
			addField('current_page',currentPage,page);
			addField('field_name','Date',sort);
			addField('is_ascend',false,sort);
			addField('size',size,page);
			addField('page',page,cardPilotSearchCriteria);
			addField('filter',filter,cardPilotSearchCriteria);
			addField('sort',sort,cardPilotSearchCriteria);
			addField('search_criteria',cardPilotSearchCriteria,request);
			
		    postCardPilotListRequest(request);	
     }
 }

function postCardPilotListRequest(request) {
		$('#cardPilotLoadingGIF').css('display','block');
		$.ajax({
			url : '/compliance-portal/getCardPilotList',
			type : 'POST',
			data : getJsonString(request),
			contentType : "application/json",
			success : function(data) {
				
			 if(data.card_activity_history_details === undefined || data.card_activity_history_details.length == 0){
				   var error='<div style="text-align: center;"><h5>Records Not Found</h5></div> ';
					$('#cardPilotTblId').empty();
					$('#cardPilotTblId').append(error);	
					$('#cardPilotLoadingGIF').css('display','none');
				}
		     if(data.response_code == 555){
				  var msg='<div style="text-align: center;"><h5>'+data.response_description+'</h5></div> ';
				  $('#cardPilotTblId').empty();
				  $('#cardPilotTblId').append(msg);
				  $('#cardPilotLoadingGIF').css('display','none');
			    }
		     else{	
					$('#customerWalletTablebody').empty();
					setCardActivityList(data, data.card_activity_history_details);
		         }
		         $('#cardPilotLoadingGIF').css('display','none');
          },
        error : function() { 
        	            var error='<div style="text-align: center;"><h5>Something Went Wrong</h5></div> ';
						$('#cardPilotTblId').empty();
						$('#cardPilotTblId').append(error);
						$('#cardPilotLoadingGIF').css('display','none');
				}
	});
}


function setCardActivityList(data,cardData) {
	$("#cardPilotTablebody").empty();
	$.each(cardData, function (i) {  
		var row = '<tr>';
			row+='<td class="breakword">'+getSingleDashIfNull(cardData[i].instruction_number)+'</td>';
			row+='<td class="breakword">'+getDateTimeFormatTZ(cardData[i].transactionDate)+'</td>';
			row+='<td class="breakword">'+getSingleDashIfNull(cardData[i].transactionAmount)+'</td>';
			row+='<td class="breakword">'+getSingleDashIfNull(cardData[i].transactionCurrency)+'</td>';
			row+='<td class="breakword">'+getSingleDashIfNull(cardData[i].merchantName)+'</td>';
			row+='<td class="breakword">'+getSingleDashIfNull(cardData[i].purchaseCategory)+'</td>';
			row+='<td class="breakword">'+getSingleDashIfNull(cardData[i].authMethod)+'</td>';
			row+='<td class="breakword">'+getSingleDashIfNull(cardData[i].cdPaymentLifecycleId)+'</td>';
			row+='</tr>';
		 $('#cardPilotTablebody').append(row);
	});
	setViewMoreDiv(data);
}

    function  setViewMoreDiv(data){
		$("#cardPilotViewMore").empty();
		var page = data.contract_note_search_criteria.page;

		var row ='	  <p>Showing '+
				'			 <strong id="cardPilotMinRecord">'+page.min_record+'</strong> <strong>-</strong> <strong id="cardPilotMaxRecord">'+page.max_record+'</strong> '+
			    '			 of <strong id="cardPilotTotalRecords">'+page.total_records+'</strong> '+
				'			records '+
				'		</p> ';
		$("#cardPilotViewMore").append(row);
		
		if(page.total_pages > 1){
			$("#cardPilotViewMoreBtn").empty();
			var firstPageBtn = '';
			if(page.current_page > 1){
				firstPageBtn = '<li onclick="getFirstOrLastPageOfCardPilot(\'1\',\''+data.account_number+'\',\''+data.org_code+'\')" class="pagination__jump" value="1"><a id="firstPageCardPilot"  title="First page"><i class="material-icons">first_page</i></a></li>'+							
		    				   '<li onClick="getPreviousPageOfCardPilot(\''+page.current_page+'\',\''+data.account_number+'\', \''+data.org_code+'\')" class="pagination__jump"><a id="previousPageCardPilot"  title="Previous page"><i class="material-icons">navigate_before</i></a></li>';
				
				if(page.current_page == page.total_pages){
					firstPageBtn += '<li onClick="getNextPageOfCardPilot(\''+page.current_page+'\',\''+data.account_number+'\', \''+data.org_code+'\')" class="pagination__jump--disabled"><a id="nextPageCardPilot"  title="Next page" class="disabled"><i class="material-icons">navigate_next</i></a></li>'+
						'<li  onClick="getFirstOrLastPageOfCardPilot(\''+page.total_pages+'\',\''+data.account_number+'\', \''+data.org_code+'\')" class="pagination__jump--disabled" value="'+page.total_records+'"><a id="lastPagCardPilote"  title="Last page" class="disabled"><i class="material-icons">last_page</i></a></li>';
				}	
				else{
					firstPageBtn +='<li onClick="getNextPageOfCardPilot(\''+page.current_page+'\',\''+data.account_number+'\', \''+data.org_code+'\')" class="pagination__jump"><a id="nextPageCardPilot"  title="Next page"><i class="material-icons">navigate_next</i></a></li>'+
						'<li  onClick="getFirstOrLastPageOfCardPilot(\''+page.total_pages+'\',\''+data.account_number+'\', \''+data.org_code+'\')" class="pagination__jump" value="'+page.total_records+'"><a id="lastPageCardPilot"  title="Last page"><i class="material-icons">last_page</i></a></li>';
					}	
			}
			else{
				firstPageBtn = '<li onclick="getFirstOrLastPageOfCardPilot(\'1\', \''+data.account_number+'\',\''+data.org_code+'\')" class="pagination__jump--disabled" value="1"><a id="firstPageCardPilot"  title="First page" class="disabled"><i class="material-icons">first_page</i></a></li>'+							
					    '<li onClick="getPreviousPageOfCardPilot(\''+page.current_page+'\',\''+data.account_number+'\', \''+data.org_code+'\')" class="pagination__jump--disabled"><a id="previousPageCardPilot"  title="Previous page" class="disabled"><i class="material-icons">navigate_before</i></a></li>'+
						'<li onClick="getNextPageOfCardPilot(\''+page.current_page+'\',\''+data.account_number+'\', \''+data.org_code+'\')" class="pagination__jump"><a id="nextPageCardPilot"  title="Next page"><i class="material-icons">navigate_next</i></a></li>'+
						'<li  onClick="getFirstOrLastPageOfCardPilot(\''+page.total_pages+'\',\''+data.account_number+'\', \''+data.org_code+'\')" class="pagination__jump" value='+page.total_records+'><a id="lastPageCardPilot"  title="Last page"><i class="material-icons">last_page</i></a></li>';
			
			}
			
			var viewMoreBtnDiv = '<ul class="horizontal containing pagination" id="paginationBlock">'+firstPageBtn+'</ul>';
			$("#cardPilotViewMoreBtn").append(viewMoreBtnDiv);
		}
  }

function getCardPilot(pageNumber, tradeAccountNumber, orgCode){
	
	var request = {};
	var cardPilotSearchCriteria={};
	var page={};
	var sort={};
	var filter ={};
	var size=10;
	
	var orgCode = $('#account_organisation').text();
	addField('account_number', tradeAccountNumber, request);
	addField('org_code', orgCode, request);
	addField('current_page',pageNumber,page);
	addField('size',size,page);
	addField('field_name','Date',sort);
	addField('is_ascend',false,sort);
	addField('page',page,cardPilotSearchCriteria);
	addField('filter',filter,cardPilotSearchCriteria);
	addField('sort',sort,cardPilotSearchCriteria);
	addField('search_criteria',cardPilotSearchCriteria,request);
    postCardPilotListRequest(request);	
}

function getNextPageOfCardPilot(nextPage,accountNumber,orgCode) {
	nextPage++;
	getCardPilot(nextPage,accountNumber,orgCode);
}

function getPreviousPageOfCardPilot(prevPage,accountNumber,orgCode) {
	prevPage--;
	getCardPilot(prevPage,accountNumber,orgCode);
}
	
function getFirstOrLastPageOfCardPilot(currentPage,accountNumber,orgCode){
	getCardPilot(currentPage,accountNumber,orgCode);	
}