<%@ tablib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>User managenent/Browse</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/browse" mwthod="post">
		<table id="userTable" style="border: 1px;">
			<tr>
				<th></th>
				<th>First name</th>
				<th>Last name</th>
				<th>Date of birth</th>
			</tr>
			<c:forEach var="user" items="${sessionScope.users}">
				<tr>
					<input type="radio" name="id" id="id" value="${user.id}" />
					<td>${user.firstName}</td>
					<td>${user.lastName}</td>
					<td>${user.dateofBirth}</td>
				</tr>
			</c:forEach>
		</table>
		<input type="submit" name="addButton" value="Add"/>
		<input type="submit" name="editButton" value="Edit"/>
		<input type="submit" name="deleteButton" value="Delete"/>
		<input type="submit" name="detailsButton" value="Details"/>
	</form>
	<c:if test="${requestScope.error != null}">
			<script>
	        alert('${requestScope.error}');
	    	</script>
		</c:if>
</body>
</html>