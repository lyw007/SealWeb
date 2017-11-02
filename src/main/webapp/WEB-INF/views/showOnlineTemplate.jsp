<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pp100.seal.api.ApiParameter"%>
<%@ page import="com.pp100.seal.api.ApiEnterprise"%>
<%@ page import="com.pp100.seal.api.ApiPerson"%>
<%@ page import="com.pp100.seal.api.ApiGenerateContractParameter"%>

 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath() %>/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/jquery/addTable_apiperson.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/jquery/addTable_enterprise.js" type="text/javascript"></script>
<title>Insert title here</title>
<style>
#test{border-collapse:collapse;border:1px solid #ddd;}
#test th,#test td{border:1px solid #ddd;}
.btnHead{ 
    position: fixed;
    top: 0px;
    display: table;
    background-color: #2196F3;
    width: 89%;
    height: 50px;
}
.btnHead td{
text-align: center;
}
input[type='button']{
    border: 0;
    color: #fff;
    width:63px;
    height:28px;
    background: #33649e;
}
</style>
<%
ApiGenerateContractParameter apiGenerateContractParameter = (ApiGenerateContractParameter)request.getAttribute("apiGenerateContractParameter");  
Map<String, Object> contractContents = apiGenerateContractParameter.getValues();
List contractList = (List)contractContents.get("contractList");
Map contractMap = contractList == null || contractList.size() <=0 ? new HashMap() : (Map)contractList.get(0);
%>

<script type="text/javascript">
function save_contractcontent(){
	save_contractlist();

	var msg = $("#submitForm_contractcontent").serialize();  //获得后的msg的值：canshu1=xxx&canshu2=xxx&canshu3=xxx&canshu4=xxx&canshu5=xxx
    
    var json = "{";
    var msg2 = msg.split("&");   //先以“&”符号进行分割，得到一个key=value形式的数组
    var t = false;
    for(var i = 0; i<msg2.length; i++){
      var msg3 = msg2[i].split("=");  //再以“=”进行分割，得到key，value形式的数组
      for(var j = 0; j<msg3.length; j++){
        json+="\""+decodeURIComponent(msg3[j])+"\"";
        if(j+1 != msg3.length){
          json+=":";
        }
        if(t){
          json+="}";
          if(i+1 != msg2.length){  //表示是否到了当前行的最后一列
            json+=",{";
          }
          t=false;
        }
        if(msg3[j] == "~~~~~~~~~~"){  //这里的“canshu5”是你的表格的最后一列的input标签的name值，表示是否到了当前行的最后一个input
          t = true;
        }
      }
      if(!msg2[i].match("~~~~~~~~~~") && (i != msg2.length - 1)){  //同上
        json+=",";
      }
    }
    if($("#textareaJson_contractlist").val() != '[{"",]'){
        json += ",\"contractList\":" + $("#textareaJson_contractlist").val();
    }
    json+="}";
    $("#values").val(json);
}

    function toSubmit(){
    	save_contractcontent();
    	save_apiperson();
    	save_enterprise();
    	save_contractlist();
    	document.forms['submitForm_toseal'].submit();
    }
</script>
</head>
<body>
<form id="submitForm_toseal" name="submitForm_toseal" method="post" action="toSealOnlineContract">
<input type="hidden" name="apipersonJson" id="textareaJson_apiperson"/>
<input type="hidden" name="apienterpriseJson" id="textareaJson_enterprise"/>
<input type="hidden" name="apicontractlistJson" id="textareaJson_contractlist"/>
<input type="hidden" name="values" id="values"/>
<input type="hidden" name="applicationName" id="applicationName" value="${contractTemplateName }"/>


    <br/>
    <table cellpadding="3" cellspacing="1" width="90%" align="center" style="margin-bottom: 30px;">
        <tr class="btnHead">
            <td>合同盖章-需要什么内容就传什么内容！ 下面所有字段都是必填</td>
             <td><input type="button" onclick="toSubmit()" value="提交"></td>
        </tr>
    </table>
    
	<table cellpadding="3" cellspacing="1" width="90%" align="center"
		style="background-color: #b9d8f3;">
		<tr>
			<td>姓名或企业全称</td>
			<td><input type="text" name="name" value=""></td>
			<td>身份证号或企业住址机构代码(去掉“-”)、社会统一信用代码号</td>
			<td><input type="text" name="idNumberOrEnterpriserCode" value=""></td>
			<td>用户类型</td>
			<td colspan="3"><select name="userType">
                     <option value="PERSON">个人</option>
                     <option value="COMPANY">企业</option>
                 </select></td>
		</tr>
	</table>
	</form>
	<br />
	<table cellpadding="3" cellspacing="1" width="90%" align="center">
        <tr>
            <td>盖章人（个人）列表<div class="divright"><input type="button" onclick="addTable_apiPerson()" value="添加"></div></td>
        </tr>
    </table>
    <form id="submitForm_apiperson">
	<table cellpadding="3" cellspacing="1" width="90%" align="center"
		style="background-color: #b9d8f3;" id="myTable_apiperson">
		<tr>
			<td>姓名</td>
			<td>身份证号</td>
			<td>手机号</td>
			<td>合同模板替换关键字(身份证号加“~”关键字)</td>
			<td>操作</td>
		</tr>
	</table>
	</form>
	<br />
	<table class="conter" cellpadding="3" cellspacing="1" width="90%" align="center">
        <tr>
            <td>盖章人（企业）列表<input type="button" onclick="addTable_enterprise()" value="新增"></td>
        </tr>
    </table>
    <form id="submitForm_enterprise">
	<table cellpadding="3" cellspacing="1" width="90%" align="center"
		style="background-color: #b9d8f3;" id="myTable_enterprise">
		<tr>
			<td>公司全称</td>
			<td>手机号</td>
			<td>代码类型</td>
			<td>组织机构代码(去掉“-”)/社会信用代码号</td>
			<td>企业用户类型 注册类型</td>
			<td>法人代表/代理人姓名</td>
			<td>法人身份证号/代理人身份证号</td>
			<td>电子签章上行文 例如：XX专用章</td>
			<td>法人所在地</td>
			<td>邮箱</td>
			<td>公司类型</td>
			<td>替换关键字合同模板替换关键字(企业名称加“~” 关键字)</td>
		</tr>
	</table>
	</form>
	<br />
	<table cellpadding="3" cellspacing="1" width="90%" align="center">
        <tr>
            <td>合同填写所需的内容（1）</td>
        </tr>
    </table>
    <form id="submitForm_contractcontent">
    <table id="test" cellpadding="3" cellspacing="1" width="90%" align="center"
        style="background-color: #b9d8f3;">
        <%
        if(contractContents != null){
	        Iterator entries = contractContents.keySet().iterator();
	        while (entries.hasNext()) { 
	            String key = String.valueOf(entries.next());
	            if(!key.equals("contractList")){
        %>
        <tr>
            <td><%=contractContents.get(key) %></td>
            <td><input type="text" style="width:200px; height:20px;" name="<%=key %>" value=""></td>
        </tr>
        <%  
	            }
	        }
        } 
        %>
    </table>
    </form>
    <br />
    <table cellpadding="3" cellspacing="1" width="90%" align="center">
        <tr>
            <td>合同填写所需的内容（2）<% if(contractList != null && contractList.size() > 0) {%><input type="button" onclick="addTable_contractlist()" value="新增"><%} %></td>
        </tr>
    </table>
    <form id="submitForm_contractlist">
    <table class="conter" cellpadding="3" cellspacing="1" width="90%" align="center"
        style="background-color: #b9d8f3;"  id="myTable_contractlist">
        <tr>
        <%
        for(int i = 0 ; contractList != null && i < contractList.size() ;i ++){
            Map map = (Map)contractList.get(i);
            Iterator entries = map.keySet().iterator();
            int index = 1;
            while (entries.hasNext()) { 
                String key = String.valueOf(entries.next());
        %>
            <td ><%=map.get(key) %></td>
        <%
            }
        }
        %>
        <td >操作</td>
        </tr>
    </table>
    </form>
</body>
<script type="text/javascript">
	var num_contractlist = 0;
	var listsize = <%=contractMap.size() %>;
	function addTable_contractlist() {
	    var tableHtml = "";
	    tableHtml += '<tr id="tr'+num_contractlist+'">'
	    <%
	        String key = "";
	        Iterator entries = contractMap.keySet().iterator();
	        int index = 1;
            while (entries.hasNext()) { 
                key = String.valueOf(entries.next());
	    %>
	        + '<td><input class="addtd_contractlist" id="cnashu_contractlist'+<%=index%>+""+ num_contractlist + '" type="text" name="<%=key%>" value=""></td>'
	    <%
	        index++;
	        }
	    %>
	        + '<td><a style="cursor: pointer; color: blue;" onclick="removeTr_contractlist('
	        + num_contractlist
	
	        + ')">删除</a>' + '</td>' + '</tr>';
	
	    var elements = $("#myTable_contractlist").children().length; //表示id为“mtTable”的标签下的子标签的个数
	
	    $("#myTable_contractlist").children().eq(elements - 1).after(tableHtml); //在表头之后添加空白行
	    num_contractlist++;
	}
	//删除行
	function removeTr_contractlist(trNum) {
	    $("#tr" + trNum).remove();
	}
	
	function save_contractlist(){
		var msg = $("#submitForm_contractlist").serialize();  //获得后的msg的值：canshu1=xxx&canshu2=xxx&canshu3=xxx&canshu4=xxx&canshu5=xxx
        
        var json = "[{";
        var msg2 = msg.split("&");   //先以“&”符号进行分割，得到一个key=value形式的数组
        var t = false;
        for(var i = 0; i<msg2.length; i++){
          var msg3 = msg2[i].split("=");  //再以“=”进行分割，得到key，value形式的数组
          for(var j = 0; j<msg3.length; j++){
            json+="\""+decodeURIComponent(msg3[j])+"\"";
            if(j+1 != msg3.length){
              json+=":";
            }
            if(t){
              json+="}";
              if(i+1 != msg2.length){  //表示是否到了当前行的最后一列
                json+=",{";
              }
              t=false;
            }
            if(msg3[j] == "<%=key %>"){  //这里的“canshu5”是你的表格的最后一列的input标签的name值，表示是否到了当前行的最后一个input
              t = true;
            }
          }
	     if(!((i+1)%listsize == 0)){
            json+=",";
          }
        }
        json+="]";
        $("#textareaJson_contractlist").val(json);
    }
</script>
</html>