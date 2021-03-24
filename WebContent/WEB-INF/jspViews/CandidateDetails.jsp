<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>Candidate Details</h1>
		<table>
			<tr>
				<th>EmpName</th>
				<th>CandidateName</th>
				<th>CandidateSkill</th>
				<th>RefferalBonus</th>
			</tr>
			<c:forEach var="referral" items="${empReferralList}">
				<tr>
					<td><c:out value="${referral.empName}"/> </td>
					<td><c:out value="${referral.candName}"/></td>
					<td><c:out value="${referral.candSkill}"/></td>
					<td><c:out value="${referral.referralBonus}"/></td>
				</tr>
			</c:forEach>
		</table>
		<a href="index.jsp">HOME</a>
	</center>
</body>
</html>