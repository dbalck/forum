<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

	<form method="post" action="${pageContext.request.contextPath}/dofetch">
		Rss/Atom URL: <input name="url" type="text" /><br /> 
		<input type="radio" name="type" value="rss" checked>Rss<br/>
		<input type="radio" name="type" value="atom">Atom<br/>
		<input value="Fetch Stream" type="submit" class="btn"/><br/>
	</form>
