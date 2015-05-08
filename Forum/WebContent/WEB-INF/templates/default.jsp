<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css"/>
	<script src="${pageContext.request.contextPath}/static/scripts/contentController.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
</head>
<body>

	<div>
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</div>

	<div class="container">
		<tiles:insertAttribute name="content"></tiles:insertAttribute>
	</div>

	<div>
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	</div>
<body />
<script>
	$(document).ready(function() {
		contentController.init();
	});
</script>
</html>