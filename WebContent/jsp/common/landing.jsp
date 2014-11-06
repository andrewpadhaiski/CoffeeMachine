<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="fragments/language.jsp"></c:import>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key="label.title.landing" /></title>
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:300&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	</head>
	<body>
		<c:import url="fragments/header.jsp"></c:import>
		<section class="wide">	
			<hr>
			<form name="loginForm" method="POST" action="controller">
				<input type="hidden" name="command" value="login" />
				<fmt:message key="label.login" /><br/>
				<input type="text" name="login" value=""/><br/>
				<fmt:message key="label.password" /><br/>
				<input type="password" name="password" value=""/><br/>
				<p class="error">
					${ errorLoginMessage }
					${ wrongAction }
					${ nullPage }
				</p>
				<input type="submit" value="<fmt:message key="label.button.signin" />"/>
			</form><br>	
			<hr>	
			<p>
				<i> <fmt:message key="label.message.signup" /></i>
			</p>	
			<form name="registerForm" method="POST" action="controller">
				<input type="hidden" name="command" value="register" />
				<fmt:message key="label.name" /><br/>
				<input type="text" name="username" value="" /><br/>
				<fmt:message key="label.login" /><br/>
				<input type="text" name="login" value=""/><br/>
				<fmt:message key="label.password" /><br/>
				<input type="password" name="password" value="" /><br/>
				<fmt:message key="label.repeat.password" /><br/>
				<input type="password" name="repeatedPassword" value="" /><br/>
				<p class="error">
					${ errorRegisterMessage }
				</p>
				<input type="submit" value="<fmt:message key="label.button.signup" />"/>
			</form>
			<hr>
		</section>
		<c:import url="fragments/footer.jsp"></c:import>	
	</body>
</html>