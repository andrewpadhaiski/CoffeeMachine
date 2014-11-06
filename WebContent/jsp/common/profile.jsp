<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="fragments/language.jsp"></c:import>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key="label.profile.title" /></title>
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:300&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	</head>
	<body>
		<c:import url="../common/fragments/header.jsp"></c:import>
		<c:import url="../common/fragments/navbar.jsp"></c:import>
		<section>	
			<aside> 
				<h2><fmt:message key="label.profile" /></h2>
			</aside>
			<c:import url="../common/fragments/userinfo.jsp"></c:import>
			<hr>
			<fmt:message key="label.profile.name" /><strong>${user.name }</strong><br>
			<fmt:message key="label.profile.login" /><strong>${user.login}</strong><br>
			<fmt:message key="label.profile.status" /><strong>${user.role}</strong>	
			<hr>
			<fmt:message key="label.profile.balance" /><strong>$${ user.balance}.</strong>
			<hr>
			<h3><fmt:message key="label.profile.message.account" /></h3>
			<aside>
				<form name="depositForm" method="POST" action="controller">
					<fmt:message key="label.profile.amount" /><br/>
					<input type="text" name="amount" value="" /><br/>
					<input type="hidden" name="command" value="deposit" />
					<p class="error">
							${ errorDepositMessage }
					</p>
					<input type="submit" value="<fmt:message key="label.profile.button.deposit" />"/>
				</form>
			</aside>
			<aside>
			<form name="withdrawForm" method="POST" action="controller">
				<fmt:message key="label.profile.amount" /><br/>
				<input type="text" name="amount" value="" /><br/>
				<input type="hidden" name="command" value="withdraw" />
				<p class="error">
						${ errorWithdrawMessage }
				</p>
				<input type="submit" value="<fmt:message key="label.profile.button.withdraw" />"/>
			</form>
			</aside>
		</section>
		<c:import url="../common/fragments/footer.jsp"></c:import>		
	</body>
</html>