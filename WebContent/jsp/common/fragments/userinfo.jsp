<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />

<aside class="right">
	<fmt:message key="label.user.name" /> <strong>${user.name}</strong> <br>
	<fmt:message key="label.user.balance" /> <strong>$${user.balance}</strong> <br>
	<form name="aboutForm" method="POST" action="controller">
		<input type="hidden" name="command" value="logout" />
		<input type="submit" value="<fmt:message key="label.user.button.logout" />"/>
	</form>
</aside>