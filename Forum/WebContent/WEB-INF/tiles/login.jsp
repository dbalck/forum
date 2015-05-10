<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<h3>Login with Username and Password</h3>
	<form name='f' action='${pageContext.request.contextPath}/login' method='POST'>
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
			<input name="_csrf" type="hidden"
				value="d374a58a-c08f-40e8-92ee-5b38172ec0fe" />
		</table>
	</form>
