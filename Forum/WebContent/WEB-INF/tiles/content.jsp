<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="author" content="Forum">
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/script/jquery-1.11.2.min.js"></script>
</head>
<body>
	<c:forEach var="row" items="${streams}">
	Stream Title: ${row.title()}<br />
	Stream timestamp: ${row.date()}<br />
		<br />
	</c:forEach>

	<c:forEach var="row" items="${articles}">
	Article Title: ${row.title()}<br />
	Article timestamp: ${row.date()}<br />
		<br />
	</c:forEach>
	<form method="post"
		action="${pageContext.request.contextPath}/fetchrss">
		<input value="fetch feeds" type="submit" class="btn"></input>
	</form>
	<br/>
</body>

</html>