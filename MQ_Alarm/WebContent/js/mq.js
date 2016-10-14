$(document).ready(function(){
	setInterval(queryDepth,5000); //前端页面刷新时间
	setInterval(queryPsgDepth,5000); //前端页面刷新时间
	setInterval(queryBagDepth,5000); //前端页面刷新时间
	setInterval(queryANJDepth,5000);
	setInterval(queryATSCDepth,5000);
	setInterval(queryPNRDepth,5000);
	setInterval(queryPSRDepth,5000);
	queryDepth();
	queryDepthLimit();
	queryInteval();
	queryPsgDepth();
	queryBagDepth();
	queryNineAirDepthLimit();
	queryNineAirInteval();
	queryANJDepth();
	queryATSCDepth();
	queryPNRDepth();
	queryPSRDepth();
	queryNEWAPPDepthLimit();
	queryNEWAPPInteval();
});

//DCSI的加载项
function queryDepth(){
	$("#currentDepth").load("./QueryDepth?" + new Date().getTime());
}

function queryDepthLimit(){
	$("#depthLimit").load("./QueryDepthLimit?" + new Date().getTime());
}

function queryInteval(){
	$("#inteval").load("./QueryInteval?" + new Date().getTime());
}



//九元航空的加载项
function queryPsgDepth(){
	$("#currentPsgDepth").load("./QueryPsgDepth?" + new Date().getTime());
}
function queryBagDepth(){
	$("#currentBagDepth").load("./QueryBagDepth?" + new Date().getTime());
}
function queryNineAirDepthLimit(){
	$("#depthLimit_NineAir").load("./QueryNineAirDepthLimit?" + new Date().getTime());
}
function queryNineAirInteval(){
	$("#inteval_NineAir").load("./QueryNineAirInteval?" + new Date().getTime());
}

//NEWAPP的加载项
function queryANJDepth(){
	$("#currentANJDepth").load("./QueryANJDepth?"+new Date().getTime());
}
function queryATSCDepth(){
	$("#currentATSCDepth").load("./QueryATSCDepth?"+new Date().getTime());
}
function queryPNRDepth(){
	$("#currentPNRDepth").load("./QueryPNRDepth?"+new Date().getTime());
}
function queryPSRDepth(){
	$("#currentPSRDepth").load("./QueryPSRDepth?"+new Date().getTime());
}
function queryNEWAPPDepthLimit(){
	$("#depthLimitNEWAPP").load("./QueryNEWAPPDepthLimit?" + new Date().getTime());
}
function queryNEWAPPInteval(){
	$("#intevalNEWAPP").load("./QueryNEWAPPInteval?" + new Date().getTime());
}
