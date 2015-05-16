<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<header>
	<table>
		<tr class="row">
			<td class="col-sm-4"><a
				href="${pageContext.request.contextPath}/">
					<h2 id="logo">Forum</h2>
			</a></td>
			<td class="col-sm-4">
				<p class="text-center">A database of ideas</p>
			</td>


			<sec:authorize access="!isAuthenticated()">
				<td class="text-right col-sm-4"><a class="btn btn-primary"
					href="${pageContext.request.contextPath}/login">Login</a></td>
			</sec:authorize>

			<sec:authorize access="isAuthenticated()">
				<td class="text-right col-sm-4"><a class="btn btn-primary"
					href="${pageContext.request.contextPath}/logout">Logout</a></td>
			</sec:authorize>

			<sec:authorize access="isAuthenticated()">
				<td class="text-right col-sm-4"><a class="btn btn-primary"
					href="${pageContext.request.contextPath}/account">Account</a></td>
			</sec:authorize>

		</tr>
	</table>
</header>