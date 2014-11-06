<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />
<nav>
	<ul>
		<li>
			<form name="accountForm" method="POST" action="controller">
				<input type="hidden" name="command" value="profile" />
				<input type="submit" value="<fmt:message key="label.nav.profile" />"/>
			</form>
		</li>
		<li>
			<form name="coffeeshopForm" method="POST" action="controller">
				<input type="hidden" name="command" value="coffee" />
				<input type="submit" value="<fmt:message key="lavel.nav.coffeeshop" />"/>
			</form>
		</li>
		<li>
		<form name="ordersForm" method="POST" action="controller">
				<input type="hidden" name="command" value="orders" />
				<input type="submit" value="<fmt:message key="label.nav.orders" />"/>
			</form>
		</li>
	</ul>
</nav>