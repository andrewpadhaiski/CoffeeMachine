<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../common/fragments/language.jsp"></c:import>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key="label.coffee.title" /></title>
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
			<h3><fmt:message key="label.coffee.user.message.choose" /></h3>
			<table>
				<tr>
					<th><fmt:message key="label.coffee.user.table.beverage" /></th>
					<th><fmt:message key="label.coffee.user.table.sort" /></th>
					<th><fmt:message key="label.coffee.user.table.serves" /></th>
					<th><fmt:message key="label.coffee.user.table.price" /></th>
					<th><fmt:message key="label.coffee.user.table.action" /></th>
				</tr>
				<c:forEach var="coffee" items="${coffees}" varStatus="status">
					<tr>
						<td><strong> <fmt:message key="${coffee.type.toString().toLowerCase() }" /></strong></td>
						<td><fmt:message key="${coffee.sort.toString().toLowerCase() }" /></td>
						<td><c:out value="${coffee.quantity }"></c:out></td>
						<td><c:out value="$${coffee.price }"></c:out></td>
						<td>
							<form name="coffeeForm" method="POST" action="controller">
								<input type="hidden" name="command" value="user_ingredient" />
								<input type="hidden" name="coffeeId" value="<c:out value='${coffee.id }'></c:out>" />
								<input type="submit" value="<fmt:message key="label.coffee.user.table.button.select" />"/>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
			<p class="error">
				${ errorCoffeeMessage} 
			</p>
		</section>
		<c:import url="../common/fragments/footer.jsp"></c:import>	
	</body>
</html>