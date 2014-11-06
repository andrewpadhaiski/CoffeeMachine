<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../common/fragments/language.jsp"></c:import>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key="label.orders.title" /></title>
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:300&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	</head>
	<body>
		<c:import url="../common/fragments/header.jsp"></c:import>
		<c:import url="../common/fragments/navbar.jsp"></c:import>
		<section>	
			<aside> 
				<h2><fmt:message key="label.orders" /></h2>
			</aside>
			<c:import url="../common/fragments/userinfo.jsp"></c:import>
			<hr>
			<c:if test="${orders.isEmpty() }">
				<fmt:message key="label.order.none" />
			</c:if>	
			<c:forEach var="order" items="${orders}" varStatus="status">
				<strong><fmt:message key="label.order.user" />${order.user.name }</strong> <br>
				<fmt:message key="label.order.beverage" /><strong><fmt:message key="${order.coffee.type.toString().toLowerCase() }" /></strong>	<br>
				<fmt:message key="label.order.sort" /><fmt:message key="${order.coffee.sort.toString().toLowerCase() }" /><br>
				<fmt:message key="label.order.addition" />
				<c:if test="${order.ingredients.isEmpty() }">
					<fmt:message key="label.order.none" />
				</c:if>	
				<c:forEach var="ingredient" items="${order.ingredients}" varStatus="status">
					<fmt:message key="${ingredient.type.toString().toLowerCase() }" /> - ${ingredient.quantity } <fmt:message key="label.order.serves" />
				</c:forEach>
			<br>
			<fmt:message key="label.order.date" /> ${order.date }<br>		
			<strong><fmt:message key="label.order.total" />$${ order.amount}</strong><br>	
			<hr>
			</c:forEach>
			<c:if test="${currentPage != 1}">
				<form name="PreviusForm" method="POST" action="controller">
					<input type="hidden" name="command" value="orders" />
					<input type="hidden" name="currentPage" value="${ currentPage - 1}" />
					<input type="submit" value="<fmt:message key="label.orders.previous" />"/>
				</form>
			</c:if>
            <c:forEach begin="1" end="${numberOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        ${i}
                    </c:when>
                    <c:otherwise>
               		<form name="PageForm" method="POST" action="controller">
						<input type="hidden" name="command" value="orders" />
						<input type="hidden" name="currentPage" value="${i}" />
						<input type="submit" value="${i}"/>
					</form>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
    		<c:if test="${currentPage lt numberOfPages}">
    			<form name="PageForm" method="POST" action="controller">
					<input type="hidden" name="command" value="orders" />
					<input type="hidden" name="currentPage" value="${currentPage + 1}" />
					<input type="submit" value="<fmt:message key="label.orders.previous" />"/>
				</form>
   			 </c:if>
		</section>
		<c:import url="../common/fragments/footer.jsp"></c:import>		
	</body>
</html>