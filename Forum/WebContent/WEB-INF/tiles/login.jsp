<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container">
	<h3>Login with username and password</h3><br>
	<form role="form" name='f'
		action='${pageContext.request.contextPath}/login' method='POST'>
		<div class="form-group">
			<label for="user">User</label> <input type="text" name="username"
				class="form-control" value="" />
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				name="password" class="form-control" value="" />
		</div>

		<button type="submit" class="btn btn-primary">Submit</button>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> 
	</form>
</div>
<div class="container">
	<a href="${pageContext.request.contextPath}/newaccount">New? Click here</a>
</div>