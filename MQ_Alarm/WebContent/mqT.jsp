<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<meta HTTP-EQUIV="expires" CONTENT="0"> 
<link href="css/mq.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/jquery-1.12.2.min.js"></script>
<script type="text/javascript" src="js/mq.js"></script>
<title>MQ队列监控</title>
</head>
<body>

	<div class="wrap">
		<h1>DCSI当前队列深度</h1>
		<div class="textArea"><label id="currentDepth" style="font-size:60px;">0</label></div>
	</div>

</br>
<div class="container1">
	<div class="wrap">
		<h1 >当前设置</h1>
		<div class="textArea" style="padding-top:3px;padding-bottom:3px;"><label>预警深度：</label><label id="depthLimit"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>数据刷新间隔：</label><label id="inteval"></label></div>
	</div>
</div>

<div class="container2">
	<div class="wrap">
		<h1>九元航空旅客当前队列深度</h1>
		<div class="textArea"><label id="currentPsgDepth" style="font-size:60px;">0</label></div>
	</div>
	<div class="wrap">
		<h1>九元航空行李当前队列深度</h1>
		<div class="textArea"><label id="currentBagDepth" style="font-size:60px;">0</label></div>
	</div>
</div>
</br>
<div class="container3">
	<div class="wrap">
		<h1 >当前设置</h1>
		<div class="textArea" style="padding-top:3px;padding-bottom:3px;"><label>旅客预警深度：</label><label id="depthPsgLimit"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>数据刷新间隔：</label><label id="inteval"></label></div>
	</div>
</div>


</br>
<div class="container4">
	<div class="wrap">
		<h1>NEWAPP_ANJ深度</h1>
		<div class="textArea"><label id="currentANJDepth" style="font-size:60px;">0</label></div>
	</div>
	<div class="wrap">
		<h1>NEWAPP_ATSC深度</h1>
		<div class="textArea"><label id="currentATSCDepth" style="font-size:60px;">0</label></div>
	</div>
	<div class="wrap">
		<h1>NEWAPP_PNR深度</h1>
		<div class="textArea"><label id="currentPNRDepth" style="font-size:60px;">0</label></div>
	</div>
	<div class="wrap">
		<h1>NEWAPP_PSR深度</h1>
		<div class="textArea"><label id="currentPSRDepth" style="font-size:60px;">0</label></div>
	</div>
</div>
</br>
<div class="container5">
	<div class="wrap">
		<h1 >当前设置</h1>
		<div class="textArea" style="padding-top:3px;padding-bottom:3px;"><label>预警深度：</label><label id="depthLimitNEWAPP"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>数据刷新间隔：</label><label id="intevalNEWAPP"></label></div>
	</div>
</div>
</body>
</html>