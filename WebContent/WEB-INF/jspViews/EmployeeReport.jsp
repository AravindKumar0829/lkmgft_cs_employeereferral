<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
	<form:form action="dateRangeReport.html" modelAttribute="dateRange">
	<table>
	<tr>
	<th>From Date  (dd-MMM_yyyy)</th>
	<td><form:input path="fromDate"/></td>
	</tr>
	
	<tr>
	<th>To Date (dd-MMM_yyyy)</th>
	<td><form:input path="toDate"/></td>
	</tr>
	<tr>
	<th><a href="index.jsp">HOME</a> </th>
	<td><input type="submit" value="Submit"/></td>
	</tr>
	
	</table>
	${errMsg}
	
	</form:form>
</center>
</body>
</html>