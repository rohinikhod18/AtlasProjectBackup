<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true" %>
<div id="modal-wallet" class="modal--hidden modal--large wallet-modal slip--full slip--open ui-resizable ui-resizable-disabled" style="display:inherit">
    <div id="walletdataccordian">			
					
							<h3>
							<c:if test="${not empty walletTransactionDetails.walletTransactionList }">
								<img class="droplist__flag" src="resources/img/flags/flag_${fn:toLowerCase(walletTransactionDetails.wallet.walletCurrency)}.png" height="16" alt="${walletTransactionDetails.wallet.walletCurrency}"> 
								${walletTransactionDetails.wallet.walletCurrency} - ${walletTransactionDetails.wallet.walletNumber}	
							</c:if>	
						</h3>

							<div class="grid">

								<div class="grid__row">

									<div class="grid__col--8">
										
										<div class="boxpanel">

											<table class="micro space-after">
												<thead>
													<tr>
														<th>Reference</th>
														<th>Date/Time</th>
														<th>Type</th>
														<th class="number">Amount</th>
													</tr>
												</thead>
												<tbody>																
									<c:forEach var="listValue"
											items="${walletTransactionDetails.walletTransactionList}"  varStatus="loop">
											<tr id="walletTranRow">
											  <td>${listValue.reference}</td>
											  <td class="nowrap">${listValue.transDate}</td>
											  <td class="nowrap">${listValue.entryType}</td>
	                                          <td class="number">${listValue.walletAmount}</td>
										 </tr>
									</c:forEach>
								</tbody>

					</table>
							<c:if test="${empty walletTransactionDetails.walletTransactionList}">
							<p>No Transaction Details Found</p>
							</c:if>
							<c:if test="${not empty walletTransactionDetails.walletTransactionList}">
							  <p>Showing
					               <c:choose>
										<c:when test="${walletTransactionDetails.walletSearchCriteria.page.totalRecords gt walletTransactionDetails.walletSearchCriteria.page.pageSize}">
											<strong id="walletMinRecord">${walletTransactionDetails.walletSearchCriteria.page.minRecord}</strong> <strong>-</strong> <strong id="walletMaxRecord">${walletTransactionDetails.walletSearchCriteria.page.maxRecord}</strong>
										</c:when>
										<c:otherwise>
										     <strong id="walletMinRecord">${walletTransactionDetails.walletSearchCriteria.page.minRecord}</strong> <strong>-</strong> <strong id="walletMaxRecord">${walletTransactionDetails.walletSearchCriteria.page.maxRecord}</strong>
										</c:otherwise>
									</c:choose>
									of <strong id="walletTotalRecords">${walletTransactionDetails.walletSearchCriteria.page.totalRecords}</strong>
									records
								</p>	
							</c:if>								
<!--  <ul class="horizontal containing pagination">
	<li class="pagination__jump--disabled">
		<a href="#" title="First page"><i class="material-icons">first_page</i></a>
	</li>
	<li class="pagination__jump">
		<a href="#" title="Previous page"><i class="material-icons">navigate_before</i></a>
	</li>
	<li class="pagination__jump">
		<a href="#" title="Next page"><i class="material-icons">navigate_next</i></a>
	</li>
	<li class="pagination__jump">
		<a href="#" title="Last page"><i class="material-icons">last_page</i></a>
	</li>
</ul>
-->


   <ul class="horizontal containing pagination" id="paginationBlock">
   <c:choose>
   <c:when test="${walletTransactionDetails.walletSearchCriteria.page.totalPages gt 1}">
	<c:choose>
		<c:when test="${walletTransactionDetails.walletSearchCriteria.page.currentPage gt 1}">
			<li onclick="getFirstOrLastPageOfWallet('1', '${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}','${walletTransactionDetails.orgCode}')" class="pagination__jump" value="1"><a id="firstPage" href="#"
					title="First page"><i class="material-icons">first_page</i></a>
			</li>							
		    <li onClick="getPreviousPageOfWallet('${walletTransactionDetails.walletSearchCriteria.page.currentPage}','${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}', '${walletTransactionDetails.orgCode}')" class="pagination__jump"><a id="previousPage" href="#"
					title="Previous page"><i class="material-icons">navigate_before</i></a>
			</li>
			<c:choose>
					<c:when test="${walletTransactionDetails.walletSearchCriteria.page.currentPage eq walletTransactionDetails.walletSearchCriteria.page.totalPages}">	
						<li onClick="getNextPageOfWallet('${walletTransactionDetails.walletSearchCriteria.page.currentPage}','${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}','${walletTransactionDetails.orgCode}')" class="pagination__jump--disabled"><a id="nextPage" href="#"
							title="Next page" class="disabled"><i class="material-icons">navigate_next</i></a>
						</li>
										
						<li  onClick="getFirstOrLastPageOfWallet('${walletTransactionDetails.walletSearchCriteria.page.totalPages}','${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}', '${walletTransactionDetails.orgCode}')" class="pagination__jump--disabled" value="${walletTransactionDetails.walletSearchCriteria.page.totalRecords}"><a id="lastPage" href="#"
			title="Last page" class="disabled"><i class="material-icons">last_page</i></a>
					</c:when>
					<c:otherwise>	
						<li onClick="getNextPageOfWallet('${walletTransactionDetails.walletSearchCriteria.page.currentPage}','${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}','${walletTransactionDetails.orgCode}')" class="pagination__jump"><a id="nextPage" href="#"
							title="Next page"><i class="material-icons">navigate_next</i></a>
						</li>
										
						<li  onClick="getFirstOrLastPageOfWallet('${walletTransactionDetails.walletSearchCriteria.page.totalPages}','${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}', '${walletTransactionDetails.orgCode}')" class="pagination__jump" value="${walletTransactionDetails.walletSearchCriteria.page.totalRecords}"><a id="lastPage" href="#"
							title="Last page"><i class="material-icons">last_page</i></a>
						</li>
					</c:otherwise>
			</c:choose>	
		</c:when>
		<c:otherwise>
			<li onclick="getFirstOrLastPageOfWallet('1', '${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}','${walletTransactionDetails.orgCode}')" class="pagination__jump--disabled" value="1"><a id="firstPage" href="#"
					title="First page" class="disabled"><i class="material-icons">first_page</i></a>
			</li>							
		    <li onClick="getPreviousPageOfWallet('${walletTransactionDetails.walletSearchCriteria.page.currentPage}','${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}', '${walletTransactionDetails.orgCode}')" class="pagination__jump--disabled"><a id="previousPage" href="#"
					title="Previous page" class="disabled"><i class="material-icons">navigate_before</i></a>
			</li>
			
			<li onClick="getNextPageOfWallet('${walletTransactionDetails.walletSearchCriteria.page.currentPage}','${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}','${walletTransactionDetails.orgCode}')" class="pagination__jump"><a id="nextPage" href="#"
					title="Next page"><i class="material-icons">navigate_next</i></a>
			</li>
										
			<li  onClick="getFirstOrLastPageOfWallet('${walletTransactionDetails.walletSearchCriteria.page.totalPages}','${walletTransactionDetails.wallet.walletNumber}','${walletTransactionDetails.accountNumber}', '${walletTransactionDetails.orgCode}')" class="pagination__jump" value="${walletTransactionDetails.walletSearchCriteria.page.totalRecords}"><a id="lastPage" href="#"
					title="Last page"><i class="material-icons">last_page</i></a>
			</li>
		</c:otherwise>
	</c:choose>
	</c:when>
		</c:choose>
     </ul>
		
							</div>

									</div>

									<div class="grid__col--4 grid__col--pad-left">

										<div class="pagepanel">

											<div class="splitpanel">
												
												<div class="splitpanel__section copy">
													<h4>Wallet balance</h4>
													<p class="xl positive"><strong>${walletTransactionDetails.wallet.walletTotalBalance}&nbsp;${walletTransactionDetails.wallet.walletCurrency} </strong></p>
												</div>

												<div class="splitpanel__section copy">
													<h4>Available to spend</h4>
													<p>${walletTransactionDetails.wallet.walletAvailableBalance}&nbsp;${walletTransactionDetails.wallet.walletCurrency}</p>
												</div>

											</div>

										</div>

									</div>

								</div>

							</div>
						 </div>
						</div>