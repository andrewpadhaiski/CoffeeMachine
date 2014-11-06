<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${language eq 'ru'}">
	    <fmt:setLocale value="ru_RU" scope="session" />
	</c:when>
	<c:when test="${language eq 'en'}">
	    <fmt:setLocale value="en_US" scope="session" />
	</c:when>
	<c:otherwise>
	    <fmt:setLocale value="en_US" scope="session" />
</c:otherwise>
</c:choose>    	
