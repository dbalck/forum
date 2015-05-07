<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<title>Insert title here</title>
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/dofetch">
		Rss/Atom URL: <input name="url" type="text" /><br /> 
		<input type="radio" name="type" value="rss" checked>Rss<br/>
		<input type="radio" name="type" value="atom">Atom<br/>
		<input value="Fetch Stream" type="submit" class="btn"/><br/>
	</form>
</body>
</html>