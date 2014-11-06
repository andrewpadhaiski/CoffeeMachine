<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../common/fragments/language.jsp"></c:import>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key="label.thanks.title" /></title>
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:300&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	</head>
	<body>
		<c:import url="../common/fragments/header.jsp"></c:import>
		<c:import url="../common/fragments/navbar.jsp"></c:import>
		<section>	
			<aside> 
				<h2><fmt:message key="label.coffee.shop" /></h2>
			</aside>
			<c:import url="../common/fragments/userinfo.jsp"></c:import>
			<hr>
			<h3><fmt:message key="label.thanks.message.thanks" /></h3>
			<p><fmt:message key="label.thanks.message.check" /></p>
		</section>
		<c:import url="../common/fragments/footer.jsp"></c:import>		
	</body>
</html>