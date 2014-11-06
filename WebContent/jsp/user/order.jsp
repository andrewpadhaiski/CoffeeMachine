<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../common/fragments/language.jsp"></c:import>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key="label.order.title" /></title>
		<link href='http://fonts.googleapis.com/css?family=Lobster|Open+Sans:300&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
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
			<h3><fmt:message key="label.order.message.confirm" /></h3>
			<fmt:message key="label.order.beverage" /><strong><fmt:message key="${order.coffee.type.toString().toLowerCase() }" /></strong>	<br>
			<fmt:message key="label.order.sort" /><fmt:message key="${order.coffee.sort.toString().toLowerCase() }" />	<br>
			<fmt:message key="label.order.addition" />
			<c:if test="${order.ingredients.isEmpty() }">
				<fmt:message key="label.order.none" />
			</c:if>	
			<c:forEach var="ingredient" items="${order.ingredients}" varStatus="status">
				<fmt:message key="${ingredient.type.toString().toLowerCase() }" /> - ${ingredient.quantity } <fmt:message key="label.order.serves" />
			</c:forEach>
			<br>
			<fmt:message key="label.order.date" /> ${order.date }<br>
			<hr>		
			<strong><fmt:message key="label.order.total" /> $${ order.amount}</strong><br>	
			<hr>
			<p class="error">
				${ errorOrderMessage} 
			</p>
			<form name="coffeeForm" method="POST" action="controller">
				<input type="hidden" name="command" value="user_confirm" />
				<input type="submit" value="<fmt:message key="label.order.button.confirm" />"/>
			</form>
		</section>
		<c:import url="../common/fragments/footer.jsp"></c:import>	
	</body>
</html>