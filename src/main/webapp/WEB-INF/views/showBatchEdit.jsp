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
<title>Insert title here</title>
<style>

    /* Basic Grey */
    .basic-grey {
    margin-left:auto;
    margin-right:auto;
    max-width: 500px;
    background: #F7F7F7;
    padding: 25px 15px 25px 10px;
    font: 12px Georgia, "Times New Roman", Times, serif;
    color: #888;
    text-shadow: 1px 1px 1px #FFF;
    border:1px solid #E4E4E4;
    }
    .basic-grey h1 {
    font-size: 25px;
    padding: 0px 0px 10px 40px;
    display: block;
    border-bottom:1px solid #E4E4E4;
    margin: -10px -15px 30px -10px;;
    color: #888;
    }
    .basic-grey h1>span {
    display: block;
    font-size: 11px;
    }
    .basic-grey label {
    display: block;
    margin: 0px;
    }
    .basic-grey label>span {
    float: left;
    width: 20%;
    text-align: right;
    padding-right: 10px;
    margin-top: 10px;
    color: #888;
    }
    .basic-grey input[type="text"], .basic-grey input[type="email"], .basic-grey textarea, .basic-grey select {
    border: 1px solid #DADADA;
    color: #888;
    height: 30px;
    margin-bottom: 16px;
    margin-right: 6px;
    margin-top: 2px;
    outline: 0 none;
    padding: 3px 3px 3px 5px;
    width: 70%;
    font-size: 12px;
    line-height:15px;
    box-shadow: inset 0px 1px 4px #ECECEC;
    -moz-box-shadow: inset 0px 1px 4px #ECECEC;
    -webkit-box-shadow: inset 0px 1px 4px #ECECEC;
    }
    .basic-grey textarea{
    padding: 5px 3px 3px 5px;
    }
    .basic-grey select {
    background: #FFF url('down-arrow.png') no-repeat right;
    background: #FFF url('down-arrow.png') no-repeat right);
    appearance:none;
    -webkit-appearance:none;
    -moz-appearance: none;
    text-indent: 0.01px;
    text-overflow: '';
    width: 70%;
    height: 35px;
    line-height: 25px;
    }
    .basic-grey textarea{
    height:100px;
    }
    .basic-grey .button {
    background: #E27575;
    border: none;
    padding: 10px 25px 10px 25px;
    color: #FFF;
    box-shadow: 1px 1px 5px #B6B6B6;
    border-radius: 3px;
    text-shadow: 1px 1px 1px #9E3F3F;
    cursor: pointer;
    }
    .basic-grey .button:hover {
    background: #CF7A7A
    }

</style>
<script>
    function updateStatus() {
        document.forms[0].operateType.value = "update";
        document.forms[0].submit();
    }
</script>
</head>
<body>
<center>
<h1>
     <span>异常信息查看/编辑</span>
</h1>
 
<form id="showBatchEdit" name="showBatchEdit" method="post" action="showBatchEdit">
<input type="hidden" id="contractBatchId" name="contractBatchId" value="${contractBatch.id }"/>
<input type="hidden" id="operateType" name="operateType" value=""/>
<table cellpadding="3" cellspacing="1" width="90%" align="center">
    <tr>
        <td>请求参数:</td>
        <td>
            <textarea rows="6" cols="200" id="parametersJson" name="parametersJson">${contractBatch.parameters }</textarea>
        </td>
    </tr>
    <tr>
        <td>时间:</td>
        <td>${contractBatch.time }</td>
    </tr>
    <tr>
        <td>状态:</td>
        <td>${contractBatch.status }</td>
    </tr>
    <tr>
        <td>模板:</td>
        <td>${contractBatch.template.templateName }</td>
    </tr>
    <tr>
        <td colspan="2" align="center"><input type="button" value="修改状态" onclick="updateStatus()"/></td>
    </tr>
</table>
</form>
</center>
</body>
</html>