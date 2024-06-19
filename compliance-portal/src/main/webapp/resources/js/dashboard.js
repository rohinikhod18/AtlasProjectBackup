	var regPFXGeography;
	var regCFXGeography;
	var regPFXBusiness;
	var regCFXBusiness;
	var regPFXFulfilment;
	var regCFXFulfilment;
	var payOutFulfilment;
	var payInFulfilment;
	var payOutBusiness;
	var payInBusiness;
	var pfxMapData=[];
	var cfxMapData=[];
	var pfxBusinessUnitData=[];
	var cfxBusinessUnitData=[];
	var pfxFulfilmentData=[];
	var cfxFulfilmentData=[];
	var paymentInBusinessUnitData=[];
	var paymentOutBusinessUnitData=[];
	var paymentInFulfilmentData=[];
	var paymentOutFulfilmentData=[];
	
		$(document).ready(function() {
			
			var pfxMapDataJsonString = $('#regPfxByGeographyJsonStringId').val();
			var cfxMapDataJsonString = $('#regCfxByGeographyJsonStringId').val();
			var pfxBusinessUnitJsonString = $('#regPfxByBusinessUnitJsonStringId').val();
			var cfxBusinessUnitJsonString = $('#regCfxByBusinessUnitJsonStringId').val();
			var pfxFulfilmentJsonString = $('#regPfxFulfilmentJsonStringId').val();
			var cfxFulfilmentJsonString = $('#regCfxFulfilmentJsonStringId').val();
			var paymentInBusinessUnitJsonString = $('#paymentInBusinessUnitJsonStringId').val();
			var paymentOutBusinessUnitJsonString = $('#paymentOutBusinessUnitJsonStringId').val();
			var paymentInFulfilmentJsonString = $('#paymentInFulfilmentJsonStringId').val();
			var paymentOutFulfilmentJsonString = $('#paymentOutFulfilmentJsonStringId').val();
			
			if(!getIsEmptyOrNull(pfxMapDataJsonString)){
				pfxMapData=isJson(pfxMapDataJsonString);
			}
			if(!getIsEmptyOrNull(cfxMapDataJsonString)){
				cfxMapData=isJson(cfxMapDataJsonString);
			}
			if(!getIsEmptyOrNull(pfxBusinessUnitJsonString)){
				pfxBusinessUnitData=isJson(pfxBusinessUnitJsonString);
			}
			if(!getIsEmptyOrNull(cfxBusinessUnitJsonString)){
				cfxBusinessUnitData=isJson(cfxBusinessUnitJsonString);
			}
			if(!getIsEmptyOrNull(paymentInBusinessUnitJsonString)){
				paymentInBusinessUnitData =isJson(paymentInBusinessUnitJsonString);	
			}
			if(!getIsEmptyOrNull(paymentOutBusinessUnitJsonString)){
				paymentOutBusinessUnitData=isJson(paymentOutBusinessUnitJsonString);
			}
			if(!getIsEmptyOrNull(paymentInFulfilmentJsonString)){
				paymentInFulfilmentData=isJson(paymentInFulfilmentJsonString);
			}
			if(!getIsEmptyOrNull(paymentOutFulfilmentJsonString)){
				paymentOutFulfilmentData=isJson(paymentOutFulfilmentJsonString);
			}
			if(!getIsEmptyOrNull(pfxFulfilmentJsonString)){
				pfxFulfilmentData=isJson(pfxFulfilmentJsonString);
			}
			if(!getIsEmptyOrNull(cfxFulfilmentJsonString)){
				cfxFulfilmentData=isJson(cfxFulfilmentJsonString);
			}	
			
			regPFXGeography = AmCharts.makeChart( "map-reg-pfx-geography", {

					"type": "map",
				  	"theme": "light",
				  	"colorSteps": 10,

				  	"areasSettings": {
				    	"unlistedAreasColor": "#ccc",
				    	"balloonText": "[[title]]: <strong>[[value]]</strong>",
				    	"autoZoom": true
				  	},

				  	"dataProvider": {
				    	"map": "worldLow",

				    	// follow ISO ALPHA-2 country codes, as at:
				    	// http://www.nationsonline.org/oneworld/country_code_list.htm
					    "areas" : pfxMapData
				  	},

				  	"valueLegend": {
				   		"right": 10,
				    	"minValue": 10,
				    	"maxValue":200
				  	}

				});
			
		 regCFXGeography = AmCharts.makeChart( "map-reg-cfx-geography", {

			  	"type": "map",
			  	"theme": "light",
			  	"colorSteps": 10,

			  	"areasSettings": {
			    	"unlistedAreasColor": "#ccc",
			    	"balloonText": "[[title]]: <strong>[[value]]</strong>",
			    	"autoZoom": true
			  	},

			  	"dataProvider": {

			    	"map": "worldLow",

			    	// follow ISO ALPHA-2 country codes, as at:
			    	// http://www.nationsonline.org/oneworld/country_code_list.htm
			    	"areas" : cfxMapData
			  	},

			  	"valueLegend": {
			    	"right": 10,
			    	"minValue": 10,
			    	"maxValue":200
			  	}

			});
		 

		 regPFXBusiness = AmCharts.makeChart( "bar-reg-pfx-business", {

				"type": "serial",
			  	"theme": "light",
			  	"colors":["#FFD356","#FFD356","#FFD356","#FFD356"],
			  	"fontFamily":"Roboto",
			  	"fontSize":11,
			  	"color":"#333",

			  	"dataProvider": pfxBusinessUnitData,

			  	"valueAxes": [
			  		{
			    		"gridColor": "#FFFFFF",
			    		"gridAlpha": 0.2,
			    		"dashLength": 0
			  		}
			  	],

			  	"gridAboveGraphs": true,
			  	"startDuration": 1,

			  	"graphs": [
				  	{
				    	"balloonText": "[[category]]: <b>[[value]]</b>",
				    	"fillAlphas": 1,
				    	"lineAlpha": 0.2,
				    	"type": "column",
				    	"valueField": "visits",
				    	"labelText": "[[value]]"
				  	}
			  	],

			  	"chartCursor": {
			    	"categoryBalloonEnabled": false,
			    	"cursorAlpha": 0,
			    	"zoomable": false
			  	},

			  	"categoryField": "legalEntity",

			  	"categoryAxis": {
			    	"gridPosition": "start",
			    	"gridAlpha": 0,
			    	"tickPosition": "start",
			    	"tickLength": 20
			  	},

			  	"export": {
			    	"enabled": true
			  	}

			});
		 
		  regCFXBusiness = AmCharts.makeChart( "bar-reg-cfx-business", {

			  	"type": "serial",
			  	"theme": "light",
			  	"colors":["#FFD356","#FFD356","#FFD356","#FFD356"],
			  	"fontFamily":"Roboto",
			  	"fontSize":11,
			  	"color":"#333",

			  	"dataProvider": cfxBusinessUnitData,

			  	"valueAxes": [
				  	{
				    	"gridColor": "#FFFFFF",
				    	"gridAlpha": 0.2,
				    	"dashLength": 0
				  	}
			  	],

			  	"gridAboveGraphs": true,
			  	"startDuration": 1,

			  	"graphs": [
				  	{
				    	"balloonText": "[[category]]: <b>[[value]]</b>",
				    	"fillAlphas": 1,
				    	"lineAlpha": 0.2,
				    	"type": "column",
				    	"valueField": "visits",
				    	"labelText": "[[value]]"
				  	}
			  	],

			  	"chartCursor": {
			    	"categoryBalloonEnabled": false,
			    	"cursorAlpha": 0,
			    	"zoomable": false
			  	},

			  	"categoryField": "legalEntity",

			  	"categoryAxis": {
			    	"gridPosition": "start",
			    	"gridAlpha": 0,
			    	"tickPosition": "start",
			    	"tickLength": 20
			  	},

			  	"export": {
			    	"enabled": true
			  	}

			});

			 regPFXFulfilment = AmCharts.makeChart( "donut-reg-pfx-fulfilment", {

				"type": "pie",
			  	"theme": "light",
			  	"colors":["#58D782","#ccc"],
			  	"fontFamily":"Roboto",
			  	"fontSize":14,
			  	"color":"#333",

			  	"dataProvider": pfxFulfilmentData,

			  	"titleField": "title",
			  	"valueField": "value",
			  	"labelRadius": 5,
			  	"radius": "42%",
			  	"innerRadius": "60%",
			  	"labelText": "[[title]] - [[value]]",

			  	"export": {
			    	"enabled": true
			  	}

			});

			 regCFXFulfilment = AmCharts.makeChart( "donut-reg-cfx-fulfilment", {

				"type": "pie",
			  	"theme": "light",
			  	"colors":["#58D782","#ccc"],
			  	"fontFamily":"Roboto",
			  	"fontSize":14,
			  	"color":"#333",

			  	"dataProvider": cfxFulfilmentData,

			  	"titleField": "title",
			  	"valueField": "value",
			  	"labelRadius": 5,
			  	"radius": "42%",
			  	"innerRadius": "60%",
			  	"labelText": "[[title]] - [[value]]",

			  	"export": {
			    	"enabled": true
			  	}

			});
			 

		payInBusiness = AmCharts.makeChart( "bar-pay-in-business", {

				  	"type": "serial",
				  	"theme": "light",
				  	"colors":["#FFD356","#FFD356","#FFD356","#FFD356"],
				  	"fontFamily":"Roboto",
				  	"fontSize":11,
				  	"color":"#333",

				  	"dataProvider": paymentInBusinessUnitData,

				  	"valueAxes": [
					  	{
					    	"gridColor": "#FFFFFF",
					    	"gridAlpha": 0.2,
					    	"dashLength": 0
					  	}
				  	],

				  	"gridAboveGraphs": true,
				  	"startDuration": 1,

				  	"graphs": [
					  	{
					    	"balloonText": "[[category]]: <b>[[value]]</b>",
					    	"fillAlphas": 1,
					    	"lineAlpha": 0.2,
					    	"type": "column",
					    	"valueField": "visits",
					    	"labelText": "[[value]]"
					  	}
				  	],

				  	"chartCursor": {
				    	"categoryBalloonEnabled": false,
				    	"cursorAlpha": 0,
				    	"zoomable": false
				  	},

				  	"categoryField": "legalEntity",

				  	"categoryAxis": {
				    	"gridPosition": "start",
				    	"gridAlpha": 0,
				    	"tickPosition": "start",
				    	"tickLength": 20
				  	},

				  	"export": {
				    	"enabled": true
				  	}

				});

		payOutBusiness = AmCharts.makeChart( "bar-pay-out-business", {

				  	"type": "serial",
				  	"theme": "light",
				  	"colors":["#FFD356","#FFD356","#FFD356","#FFD356"],
				  	"fontFamily":"Roboto",
				  	"fontSize":11,
				  	"color":"#333",

				  	"dataProvider": paymentOutBusinessUnitData,

				  	"valueAxes": [
					  	{
					    	"gridColor": "#FFFFFF",
					    	"gridAlpha": 0.2,
					    	"dashLength": 0
					  	}
				  	],

				  	"gridAboveGraphs": true,
				  	"startDuration": 1,

				  	"graphs": [
					  	{
					    	"balloonText": "[[category]]: <b>[[value]]</b>",
					    	"fillAlphas": 1,
					    	"lineAlpha": 0.2,
					    	"type": "column",
					    	"valueField": "visits",
					    	"labelText": "[[value]]"
					  	}
				  	],

				  	"chartCursor": {
				    	"categoryBalloonEnabled": false,
				    	"cursorAlpha": 0,
				    	"zoomable": false
				  	},

				  	"categoryField": "legalEntity",

				  	"categoryAxis": {
				    	"gridPosition": "start",
				    	"gridAlpha": 0,
				    	"tickPosition": "start",
				    	"tickLength": 20
				  	},

				  	"export": {
				    	"enabled": true
				  	}

				});

				 payInFulfilment = AmCharts.makeChart( "donut-pay-in-fulfilment", {

				  	"type": "pie",
				  	"theme": "light",
				  	"colors":["#58D782","#ccc"],
				  	"fontFamily":"Roboto",
				  	"fontSize":14,
				  	"color":"#333",

				  	"dataProvider": paymentInFulfilmentData,

				  	"titleField": "title",
				  	"valueField": "value",
				  	"labelRadius": 5,
				  	"radius": "42%",
				  	"innerRadius": "60%",
				  	"labelText": "[[title]] - [[value]]",

				  	"export": {
				    	"enabled": true
				  	}

				});

				 payOutFulfilment = AmCharts.makeChart( "donut-pay-out-fulfilment", {

				  	"type": "pie",
				  	"theme": "light",
				  	"colors":["#58D782","#ccc"],
				  	"fontFamily":"Roboto",
				  	"fontSize":14,
				  	"color":"#333",

				  	"dataProvider": paymentOutFulfilmentData,

				  	"titleField": "title",
				  	"valueField": "value",
				  	"labelRadius": 5,
				  	"radius": "42%",
				  	"innerRadius": "60%",
				  	"labelText": "[[title]] - [[value]]",

				  	"export": {
				    	"enabled": true
				  	}

				});

		 
		});

		function postRefreshDashboard() {
			
			$('#dashboardRefreshForm').submit();
		}
		
		function applyDashboardRegQueueSearchCriteria(type) {
			$('#custType').val(type);
			$('#regPfxQueueForm').submit();
		}

function getDashboardRegDetail(contactId,type,searchCriteria){
	$('#contactId').val(contactId);
	$('#custTypes').val(type);
	$('#searchCriteria').val(searchCriteria);
	$('#regDetailForm').submit();
}

function getDashboardPaymentInDetail(paymentInId,type,searchCriteria) {
	$('#paymentInId').val(paymentInId);
	$('#payIncustType').val(type);
	$('#searchCriterias').val(searchCriteria);
	$('#paymentInDetailForm').submit();
}

function getDashboardPaymentOutDetail(paymentOutId,type,searchCriteria) {
	$('#paymentOutId').val(paymentOutId);
	$('#payOutcustType').val(type);
	$('#searchSortCriteria').val(searchCriteria);
	$('#paymentOutDetailForm').submit();
}
		
