$("#client-wallets").click(function(){
	var tbody=$('#customerWalletTablebody');
	if (tbody.children().length == 0) {
			
			$('#walletTableLoadingGIF').css('display','block');
			var request = {};
			var tradeAccountNumber = $('#account_tradeAccountNum').text();
			var orgCode = $('#account_organisation').text();
			addField('account_number', tradeAccountNumber, request);
			addField('org_code', orgCode, request);
		    postWalletBalanceListRequest(request);	
     }
 });

function postWalletBalanceListRequest(request) {
		$.ajax({
			url : '/compliance-portal/getCustomerAllWalletList',
			type : 'POST',
			data : getJsonString(request),
			contentType : "application/json",
			success : function(data) {
				
			 if(data.wallet_list === undefined || data.wallet_list.length == 0){
				   var error='<div style="text-align: center;"><h5>Records Not Found</h5></div> ';
					$('#walletid').empty();
					$('#walletid').append(error);	
				}
		     if(data.response_code == 555){
				  var msg='<div style="text-align: center;"><h5>'+data.response_description+'</h5></div> ';
				  $('#walletid').empty();
				  $('#walletid').append(msg);
			    }
		     else{	
					$('#customerWalletTablebody').empty();
					setWalletBalanceListRecords(data, data.wallet_list);
					$('#walletTableLoadingGIF').css('display','none');
		         }
          },
        error : function() { 
        	            //$('#main-content__body_negative').empty();
        	            //document.getElementById('main-content__body_negative').style.display = 'block';
			           // $('#main-content__body_negative').append('<p>Service is Not Available</p>');
			        	var error='<div style="text-align: center;"><h5>Something Went Wrong</h5></div> ';
						$('#walletid').empty();
						$('#walletid').append(error);
				}
	});
}


function setWalletBalanceListRecords(data,walletData) {
	var currentPage = 1;
	$.each(walletData, function (i) {      
	var row = '<tr>';
			row+='<td><a onClick="getWalletTransatction(\''+walletData[i].wallet_number+'\',\''+data.org_code+'\',\''+data.account_number+'\'\,'+currentPage+')" href="javascript:void(0);"><img class="droplist__flag" src="resources/img/flags/flag_'+walletData[i].wallet_currency.toLowerCase()+'.png" height="13" alt="'+walletData[i].wallet_currency+'&nbsp&nbsp&nbsp">'+walletData[i].wallet_currency+'</a></td>';
			row+='<td><a onClick="getWalletTransatction(\''+walletData[i].wallet_number+'\',\''+data.org_code+'\',\''+data.account_number+'\'\,'+currentPage+')" href="javascript:void(0);">'+walletData[i].wallet_number+'</a></td>';
			
			if(walletData[i].available_balance >=0){
			row+='<td class="number positive-cell"><a onClick="getWalletTransatction(\''+walletData[i].wallet_number+'\',\''+data.org_code+'\',\''+data.account_number+'\'\,'+currentPage+')" href="javascript:void(0);">'+walletData[i].walletAvailableBalance+'</a></td>';
			}
			else{
			row+='<td class="number negative-cell"><a onClick="getWalletTransatction(\''+walletData[i].wallet_number+'\',\''+data.org_code+'\',\''+data.account_number+'\'\,'+currentPage+')" href="javascript:void(0);">'+walletData[i].walletAvailableBalance+'</a></td>';
			}
			row+='<td class="number"><a onClick="getWalletTransatction(\''+walletData[i].wallet_number+'\',\''+data.org_code+'\',\''+data.account_number+'\'\,'+currentPage+')" href="javascript:void(0);">'+walletData[i].walletTotalBalance+'</a></td>';
			row+='</tr>';
		 $('#customerWalletTablebody').append(row);
	});
  }

function getWalletTransatction(walletNumber,orgCode,accountNumber,currentPage) {
	var walletRequest={};
	var walletSearchCriteria={};
	var page={};
	var size=5;
	var offset = 0;
	addField('current_page',currentPage,page);
	addField('size',size,page);
	addField('offset',offset,page);
	addField('wallet_number',walletNumber,walletRequest);
	addField('account_number',accountNumber,walletRequest);
	addField('org_code',orgCode,walletRequest);
	addField('page',page,walletSearchCriteria);
	addField('search_criteria',walletSearchCriteria,walletRequest);
	postWalletTransactionRequest(walletRequest);		
}
	
function postWalletTransactionRequest(request){
	$.ajax({
		url : '/compliance-portal/getCustomerWalletTransactionDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
					setWalletTransationDetails(data);
             },
		 error : function(data) {
			    $('#main-content__body_negative').empty();
	            document.getElementById('main-content__body_negative').style.display = 'block';
	            $('#main-content__body_negative').append('<p>Service is Not Available</p>');
			}
      });
}

function setWalletTransationDetails(data) {
	$("#WalletView").addClass("popupLinks");
	var view =$(data).find('#modal-wallet').html();
	$("#WalletView").empty();
	$("#WalletView").append(view);
	$("#walletdataccordian").css('display', 'block');
	$("#WalletView").attr('readonly', true);
	$("#Walletpopups").dialog({
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
				$("#modal-mask").addClass("modal-mask--hidden");
				$("#WalletView").html('');
			}
		}
	});
}

function getNextPageOfWallet(nextPage,walletnumber,accountNumber,orgCode) {
	nextPage++;
	getWalletTransatction(walletnumber,orgCode,accountNumber,nextPage);
}

function getPreviousPageOfWallet(prevPage,walletnumber,accountNumber,orgCode) {
	prevPage--;
	getWalletTransatction(walletnumber,orgCode,accountNumber,prevPage);
}
	
function getFirstOrLastPageOfWallet(currentPage,walletnumber,accountNumber,orgCode){
	getWalletTransatction(walletnumber,orgCode,accountNumber,currentPage);	
}