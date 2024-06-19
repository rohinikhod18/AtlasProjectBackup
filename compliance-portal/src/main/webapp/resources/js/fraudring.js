function init(data,currentCrmContactId) {
	graphData = data;
                var neo4jd3 = new Neo4jd3('#neo4jd3', {
                    highlight: [
                    	{
                    		class: 'ROOT',
                    		property: 'SF ContactID',
                    		value: currentCrmContactId
                    	}
                    ],
                    icons: {},
                    images: {
                    	/*'ROOT' : 'resources/img/fraudring3.svg',
                    	'PERSON' : 'resources/img/fraudring3.svg'*/
                    },
                    minCollision: 60,
                    neo4jData: data,
                    nodeRadius: 25,
                    onNodeClick: function(node) {
                    	if(false == isNodeClickedOnce) {
                    		isNodeClickedOnce = true;
                    		allNodes = $('#fraud_ring_node_summary_details').val();
                    		clickedNodeId = node.id;
                        	labels = node.labels[0];
                        	displayTxt = node.properties.displayText;
                        	crmContactId = node.properties["SF ContactID"];
                        	tradeAccountNumber = node.properties["Client Number"];
                        	nodeStatus = node.properties.Status;
                			reason = node.properties.Reason;
                			orgCode = node.properties["Org. Code"];
                        	
                        	if(nodeTags.includes(labels)) {
                        		presetDialogBox();
                        		otherNodeAttributes(node);
                        		postSetDialogBox();
                        		isNodeClickedOnce=false;
                        	}
                        	else
                        		nodeSummaryExtendedView(node,allNodes);
                    	}
                    },
                    onNodeDoubleClick: function(node) {
                    	//showFraudRingGraph(node.properties.crmContactId);
                    }
                });
            }

var graphData,clickedNodeId,startNodeId,endNodeId;
var displayTxt,crmContactId,tradeAccountNumber,orgCode,nodeStatus,reason,labels,allNodes,isNodeClickedOnce = false;
var nodeProperties = {};
var nodePropertiesUIIds = ["Name","Client Number","Email","Address","Phone","Type","Status","Status Reason","CDH Entity","Mode of Registration","Registration Date","Registered Date"];
var nodeTags = ["EMAIL","PHONE","IP_ADDRESS","ADDRESS", "DEVICE_ID"];

function nodeSummaryExtendedView(node,allNodes) {
		presetDialogBox();
		if(getNodeSummary(node,allNodes)) {
			postSetDialogBox();
		}
		else {
			var request = {};
			addField('crmContactId',crmContactId,request);
			$.ajax({
				url : '/compliance-portal/getNodeDetails',
				type : 'POST',
				data : getJsonString(request),
				contentType: "application/json",
				success : function(data) {
					postSetDialogBox();
					getDeviceId(data);
					setNodeSummary(data,allNodes);
					populateNodeTable(nodeProperties);
				},
				error : function(data) {
					isNodeClickedOnce = false;
				}
			});
		}
}

//To add node summary table on right hand side of fraud ring dialog box
function presetDialogBox() {
	$("#neo4jd3").width('75%');
	$("#node_summary").css('display','block');
	$("#node_summary_table_div").css('display','none');
	$("#loaderForNodeSummary").css('display','block');
	$("#node_summary_table_body").empty();
}

function postSetDialogBox() {
	$("#loaderForNodeSummary").css('display','none');
	$("#node_summary_table_div").css('display','block');
}

/*To get the data from hidden field 'fraud_ring_node_summary_details' when user click on same node again
 * and check whether the node contactsfid is present in hidden variable array
*/
function getNodeSummary(node,allNodes) {
	var nodeArray = [],i;
	if(allNodes == "")
		return false;
	else {
		nodeArray = getJsonObject(allNodes);
		for(i = 0; i < nodeArray.length; i++) {
			if( nodeArray[i].contactSfId === crmContactId ) {
				nodeProperties = nodeArray[i];
				populateNodeTable(nodeProperties);
				return true;
			}
		}
		return false;
	}
}

//When user click on node. Add node data from properties and database to hidden variable 'fraud_ring_node_summary_details'
function setNodeSummary(data,allNodes){
	nodeProperties = {
			contactId : data.clientId,
			contactSfId : crmContactId,
			name : displayTxt,
			clientNumber : tradeAccountNumber,
			email : data.clientEmail,
			buildingName : data.clientBuildingName,
			postcode : data.clientPostcode,
			phone : data.clientPhone,
			deviceId : data.clientDeviceId,
			type : data.clientType,
			org : orgCode,
			status : nodeStatus,
			reason : reason,
			cdhEntity : data.entityCDH,
			modeOfRegistration : data.modeOfRegistration,
			registrationDate : data.registrationDate,
			registeredData : data.registeredDate
		};
	var nodeArray = [];
	if(allNodes == "")
		nodeArray.push(nodeProperties);
	else {
		nodeArray = getJsonObject(allNodes);
		nodeArray.push(nodeProperties);
	}
	$('#fraud_ring_node_summary_details').val(getJsonString(nodeArray));
}

function populateNodeTable(node){
	$("#node_summary_table").append("<tr><td style='width:50px'><strong>Name</strong></td><td>"+getDashIfNull(node.name)+"</td></tr>");
	if(getIsEmptyOrNull(node.clientNumber))
		$("#node_summary_table").append("<tr><td><strong>Client Number</strong></td><td>"+getDashIfNull(node.clientNumber)+"</td></tr>");
	else
		$("#node_summary_table").append("<tr><td><strong>Client Number</strong></td><td><a onclick='openFraudRingNodeRegDetails()' href='#'>"+node.clientNumber+"</a></td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Email</strong></td><td>"+getDashIfNull(node.email)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Address</strong></td><td>"+node.buildingName+", "+node.postcode+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Phone</strong></td><td>"+getDashIfNull(node.phone)+"</td></tr>");
	$("#node_summary_table").append("<tr style='word-break: break-word'><td><strong>Device-ID</strong></td><td>"+getDashIfNull(node.deviceId)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Type</strong></td><td>"+getDashIfNull(node.type)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Status</strong></td><td>"+getDashIfNull(node.status)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Status Reason</strong></td><td>"+getDashIfNull(node.reason)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Org. Code</strong></td><td>"+getDashIfNull(node.org)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>CDH Entity</strong></td><td>"+getDashIfNull(node.cdhEntity)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Mode of Registration</strong></td><td>"+getDashIfNull(node.modeOfRegistration)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Registration Date</strong></td><td>"+getDashIfNull(node.registrationDate)+"</td></tr>");
	$("#node_summary_table").append("<tr><td><strong>Registered Date</strong></td><td>"+getDashIfNull(node.registeredData)+"</td></tr>");
	isNodeClickedOnce=false;
}

function otherNodeAttributes(node){
	$("#node_summary_table").append("<tr><td style='width:50px'><strong>"+labels+"</strong></td><td>"+getDashIfNull(node.properties.displayText)+"</td></tr>");
}

function closeNodeSummary(){
	$('#node_summary').css('display','none');
	$("#neo4jd3").width('100%');
}

function openFraudRingNodeRegDetails(){
	$('#nodeContactId').val(nodeProperties.contactId);
	$('#nodeCustType').val(nodeProperties.type);
	$('#fraudRingNodeRegDetails').submit();
	$('#FraudRingPopups').dialog({position : 'top'});
}

function getDeviceId(data){
	var nodes = [], relationships = [], deviceNode = {},i;
	nodes = graphData.results[0].data[0].graph.nodes;
	relationships = graphData.results[0].data[0].graph.relationships;
	for(i = 0; i < relationships.length ; i++) {
		if('HAS_DEVICE_ID' == relationships[i].type && clickedNodeId == relationships[i].startNode) {
			addField('id',relationships[i].id,deviceNode);
			addField('startNode',relationships[i].startNode,deviceNode);
			addField('endNode',relationships[i].endNode,deviceNode);
			break;
		}
	}
	if(deviceNode.hasOwnProperty('id')) {
		endNodeId = deviceNode.endNode;
		for(i = 0; i < nodes.length ; i++) {
			if(endNodeId === nodes[i].id) {
				data.clientDeviceId = nodes[i].properties.displayText;
				break;
			}
		}
	}
}