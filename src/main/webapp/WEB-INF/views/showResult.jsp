
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    查看文件服务器访问地址：
    <a
        href="<%=request.getContextPath() %>/seal/showContractById?contractId=${contractId }">${contractId }</a>
    <br /> 注：由于签字签章采用异步处理逻辑，请等一分钟之后进行查看
</body>
</html>
