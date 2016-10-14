<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="refresh" content="9"> 控制页面自动刷新频率-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<meta HTTP-EQUIV="expires" CONTENT="0"> 
<link rel="stylesheet" href="css/mm_health_nutr.css" type="text/css" />
<script type="text/javascript" src="js/jquery-1.12.2.min.js"></script>
<script type="text/javascript" src="js/mq.js"></script>
<script language="JavaScript" type="text/javascript">
//--------------- LOCALIZEABLE GLOBALS ---------------
var d=new Date();
var monthname=new Array("January","February","March","April","May","June","July","August","September","October","November","December");
//Ensure correct for language. English is "January 1, 2004"
var TODAY = monthname[d.getMonth()] + " " + d.getDate() + ", " + d.getFullYear();
//---------------   END LOCALIZEABLE   ---------------
</script>
</head>
<body bgcolor="#F4FFE4">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
  <tr bgcolor="#D5EDB3">
    <td colspan="2" rowspan="2">&nbsp;</td>
    <td width="602" height="50" id="logo" valign="bottom" align="center" nowrap="nowrap">MQ监控页面</td>
    <td width="211">&nbsp;</td>
  </tr>

  <tr bgcolor="#D5EDB3">
    <td height="51" id="tagline" valign="top" align="center">NINEAIR / NEWAPP/ DCSI</td>
	<td width="224">&nbsp;</td>
  </tr>

  <tr>
    <td colspan="4" bgcolor="#5C743D"><img src="mm_spacer.gif" alt="" width="1" height="2" border="0" /></td>
  </tr>

  <tr>
    <td colspan="4" bgcolor="#99CC66" background="mm_dashed_line.gif"><img src="mm_dashed_line.gif" alt="line decor" width="4" height="3" border="0" /></td>
  </tr>

  <tr bgcolor="#99CC66">
  <td>&nbsp;</td>
  	<td colspan="3" id="dateformat" height="20"><a href="javascript:;">HOME</a>&nbsp;&nbsp;:&nbsp;&nbsp;<script language="JavaScript" type="text/javascript">
      document.write(TODAY);	</script>	</td>
  </tr>

  <tr>
    <td colspan="4" bgcolor="#99CC66" background="mm_dashed_line.gif"><img src="mm_dashed_line.gif" alt="line decor" width="4" height="3" border="0" /></td>
  </tr>

  <tr>
    <td colspan="4" bgcolor="#5C743D"><img src="mm_spacer.gif" alt="" width="1" height="2" border="0" /></td>
  </tr>
 <tr>
    <td width="149">&nbsp;</td>
    <td colspan="2" valign="top">&nbsp;<br />
    &nbsp;<br />
    <table border="1" bordercolor="#CCCCCC"" cellspacing="0" cellpadding="2" width="850" align="center">
        <tr>
          <td height="20" colspan="7" class="pageName">九元航空旅客和行李监控</td>
        </tr>
		<tr>
          <td width="22%" height="20">旅客当前队列深度</td>
		  <td>&nbsp;</td>
		  <td width="22%" height="20"><label id="currentPsgDepth"  style="font-size:20px;color:blue">0</label></td>
	
		  <td>&nbsp;</td>
		  <td width="22%" height="20">行李当前队列深度</td>
		  <td>&nbsp;</td>
		  <td width="22%" height="20"><p><label id="currentBagDepth" style="font-size:20px;color:blue" >0</label></p>
	      </td>
        </tr>
		<tr>
          <td height="20" valign="top" nowrap="nowrap" class="detailText"><a href="javascript:;"></a>刷新时间<br /></td>
		  <td>&nbsp;</td>
		   <td class="detailText" valign="top" nowrap="nowrap"><label>预警深度：</label><label id="depthLimit_NineAir"></label></td>
		   <td>&nbsp;</td>
	      <td class="detailText" valign="top" nowrap="nowrap"><label>数据刷新间隔：</label><label id="inteval_NineAir"></label>
          </td>
		 <td>&nbsp;</td>
	      <td class="detailText" valign="top" nowrap="nowrap">&nbsp;</td>
        </tr>
		<tr>
			<td height="20" colspan="7"><span class="pageName">DCSI旅客监控</span></td>
		</tr>

		<tr>
          <td height="20">旅客当前队列深度</td>
		  <td>&nbsp;</td>
		  <td height="20"><label id="currentDepth" style="font-size:20px;color:blue">0</label></td>
		  <td>&nbsp;</td>
		  <td height="20">&nbsp;</td>
		  <td>&nbsp;</td>
		  <td height="20">&nbsp;</td>
        </tr>
		<tr>
          <td height="20" valign="top" nowrap="nowrap" class="detailText">刷新时间</td>
		  <td>&nbsp;</td>
	      <td class="detailText" valign="top" nowrap="nowrap"><label>预警深度：</label><label id="depthLimit"></label></td>
		 <td>&nbsp;</td>
	      <td class="detailText" valign="top" nowrap="nowrap"><label>数据刷新间隔：</label><label id="inteval"></label></td>
		 <td>&nbsp;</td>
	      <td class="detailText" valign="top" nowrap="nowrap"><a href="javascript:;"></a></td>
        </tr>
		  <tr>
			<td height="20" colspan="7"><span class="pageName">NEWAPP监控</span></td>
		</tr>
		<tr>
          <td height="20">安检队列深度</td>
		  <td>&nbsp;</td>
		  <td height="20"><label id="currentANJDepth" style="font-size:20px;color:blue">0</label></td>
		  <td>&nbsp;</td>
		  <td height="20">航延队列深度</td>
		  <td>&nbsp;</td>
		  <td height="20"><label id="currentATSCDepth" style="font-size:20px;color:blue">0</label></td>
        </tr>
        <tr>
          <td height="20">旅客订座队列深度</td>
		  <td>&nbsp;</td>
		  <td height="20"><label id="currentPNRDepth" style="font-size:20px;color:blue">0</label></td>
		  <td>&nbsp;</td>
		  <td height="20">旅客队列深度</td>
		  <td>&nbsp;</td>
		  <td height="20"><label id="currentPSRDepth" style="font-size:20px;color:blue">0</label></td>
        </tr>
        <tr>
          <td height="43" valign="top" nowrap="nowrap" class="detailText">刷新时间</td>
		  <td>&nbsp;</td>
	      <td class="detailText" valign="top" nowrap="nowrap"><label>预警深度：</label><label id="depthLimitNEWAPP"></label></td>
		 <td>&nbsp;</td>
	      <td class="detailText" valign="top" nowrap="nowrap"><label>数据刷新间隔：</label><label id="intevalNEWAPP"></label></td>
		 <td>&nbsp;</td>
	      <td class="detailText" valign="top" nowrap="nowrap"><a href="javascript:;"></a></td>
        </tr>
      </table>
	</td>
    <td width="224">&nbsp;</td>
  </tr>

 <tr>
    <td width="149" height="140">&nbsp;</td>
    <td width="233">&nbsp;</td>
    <td width="420">&nbsp;</td>
	<td width="224">&nbsp;</td>
  </tr>

</table>
</body>
</html>
