<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<sf:form id="details" method="post"
	action="${pageContext.request.contextPath}/createaccount"
	modelAttribute="user">

	<table class="formtable">
		<tr>
			<td class="label">Username:
			<td />
			<td><sf:input class="control" path="username" name="username"
					type="text" /><br />
			<td />
		<tr />
		<tr>
			<td class="label">Name:<td />
			<td><sf:input class="control" path="name" name="name"
					type="text" />
			<td />
		<tr />

		<tr>
			<td class="label">Email:
			<td />
			<td><sf:input class="control" path="email" name="email"
					type="text" />
			<td />
		<tr />
		<tr>
			<td class="label">Password:
			<td />
			<td><sf:input id="password" class="control" path="password"
					name="password" type="password" />
			<td />
		<tr />
		<tr>
			<td class="label">Confirm password:
			<td />
			<td><input id="confirmpass" class="control" name="confirmpass"
				type="password" />
				<div id="matchpass"></div>
			<td />
		<tr />
		<tr>
			<td class="label">
			<td />
			<td><input class="control" value="Create Account" type="submit" />
			<td />
		<tr />
	</table>

</sf:form>