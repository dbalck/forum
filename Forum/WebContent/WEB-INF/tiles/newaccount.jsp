<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<h2>Create new account</h2>
	<sf:form role="form" id="details" method="post"
		action="${pageContext.request.contextPath}/createaccount"
		modelAttribute="user">

		<div class="form-group">
			<label for="username">Username</label>
			<sf:input type="text" class="form-control" path="username" />
		</div>

		<div class="form-group">
			<label for="name">Name</label>
			<sf:input type="text" class="form-control" path="name" />
		</div>

		<div class="form-group">
			<label for="email">Email</label>
			<sf:input type="text" class="form-control" path="email" />
		</div>

		<div class="form-group">
			<label for="password">Password</label>
			<sf:input type="password" class="form-control" path="password" />
		</div>

		<div class="form-group">
			<label for="password">Confirm password</label> <input type="password"
				class="form-control" id="confirmpass" />
		</div>

		<button type="submit" class="btn btn-primary">Create account</button>

	</sf:form>
</div>