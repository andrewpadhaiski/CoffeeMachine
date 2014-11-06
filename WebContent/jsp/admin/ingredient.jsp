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
			<form name="coffeeForm" method="POST" action="controller">
				<input type="hidden" name="command" value="coffee" />
				<input type="submit" value="<fmt:message key="label.admin.button.fill.coffee" />"/>
			</form>
			<fmt:message key="label.admin.button.fill.ingredient" />
			<table>
				<tr>
					<th><fmt:message key="label.ingredient.table.ingredient" /></th>
					<th><fmt:message key="label.ingredient.table.price" /></th>
					<th><fmt:message key="label.ingredient.table.serves" /></th>
					<th><fmt:message key="label.coffee.user.table.action" /></th>
				</tr>
				<c:forEach var="ingredient" items="${ingredients}" varStatus="status">
					<tr>
						<td><strong><fmt:message key="${ingredient.type.toString().toLowerCase() }" /></strong></td>
						<td><c:out value="$${ingredient.price }"></c:out></td>
						<td><c:out value="${ingredient.quantity }"></c:out></td>
						<td>
							<form name="coffeeForm" method="POST" action="controller">
								<input type="hidden" name="command" value="admin_ingredient_fill" />
								<input type="hidden" name="ingredientId" value="<c:out value='${ingredient.id }'></c:out>" />
								<input type="number" name="quantity" min="1" max="10" />
								<input type="submit" value="<fmt:message key="label.admin.button.fill" />"/>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
			<p class="error">
				${ errorIngredientMessage} 
			</p>
		</section>
		<c:import url="../common/fragments/footer.jsp"></c:import>	
	</body>
</html>