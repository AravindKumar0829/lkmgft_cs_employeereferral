<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Referral Page</title>
<style>
.error {
	color: #ff0000;
	font-style: italic;
}
</style>
</head>
<body>
	<center>
		<h2>Make a Referral</h2>
		<form:form action="addEmployee.html" modelAttribute="empBean">
			<table>
				<tr>
					<th>Employee Name:</th>
					<td><form:input path="empName" /></td>
					<td><form:errors path="empName"></form:errors></td>
				</tr>
				<tr>
					<th>Date Of Reference[dd-MMM-yyyy]:</th>
					<td><form:input path="empDor" /></td>
					<td><form:errors path="empDor" /></td>
				</tr>
				<tr>
					<th>Candidate Name:</th>
					<td><form:input path="candName" /></td>
					<td><form:errors path="candName"></form:errors> </td>
				</tr>
				<tr>
					<th>Candidate Skill:</th>
					<td><form:select path="candSkill">
						<form:option value="">Select</form:option>
						<form:options items="${skillslist}"/>
					</form:select></td>
					<td><form:errors path="candSkill"></form:errors></td>
				</tr>
				<tr>
					<th>Candidate Level:</th>
					<td><form:input path="candLevel" /></td>
					<td><form:errors path="candLevel" ></form:errors></td>
				</tr>
				<tr>
					<td><a href="index.jsp">HOME</a></td>
					<td><input type="submit" /></td>
					<td></td>
				</tr>
			</table>
		</form:form>

	</center>
</body>
</html>