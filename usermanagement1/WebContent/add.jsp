<%@ tablib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tablib prefix="fast" uri="http://java.sun.com/jsp/jstl/fast"%>
<jsp:useBean id="user" class="ua.nure.kn.mironova.usermanagement.User" scope="session" />
<html>
	<head>
		<title>User management. Add
		</title>
	</head>
	<body>
		<form action="<%=request.getContextPath()%>/dd" method="post">
			<input type="hidden" name="id" value="${user.id}">
			First name <input type="text" name="firstName" value=""><br>
			Last name <input type="text" name="lastName" value=""><br>
			Date of Birth <input type="text" name="date" value=""><br>
			<input type="submit" name="okButton" value="Ok" />
			<input type="submit" name="cancelButton" value="Cancel" />
		</form>
		<c:if test="${requestScope.error != null}">
			<script>
	        alert('${requestScope.error}');
	    	</script>
		</c:if>
	</body>
</html>