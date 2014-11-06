<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="by.epam.coffeemashine.util.messages" />  	
<header>
	<div class="right">
		<fmt:message key="label.header.lang" />
		<form name="ordersForm" method="POST" action="controller">
			<input type="hidden" name="command" value="language" />
			<input type="hidden" name="language" value="ru" />
			<input type="submit" value="<fmt:message key="label.header.lang.ru" />"/>
		</form>
		<form name="ordersForm" method="POST" action="controller">
			<input type="hidden" name="command" value="language" />
			<input type="hidden" name="language" value="en" />
			<input type="submit" value="<fmt:message key="label.header.lang.en" />"/>
		</form>
	</div>
	<h1><fmt:message key="label.header" /></h1>
	<p><fmt:message key="label.header.legend" /></p> 
</header>