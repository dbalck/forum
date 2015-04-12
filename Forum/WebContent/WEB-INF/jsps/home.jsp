<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<sql:query var="rs" dataSource="jdbc/ForumDB">
select id, title, link, description from channel
</sql:query>

<c:forEach var="row" items="${rs.rows}">

	ID: ${row.id}<br/>
    Title: ${row.title}<br/>
    Link: ${row.link}<br/>
    Description: ${row.description}<br/>
    
</c:forEach>


</body>
</html>