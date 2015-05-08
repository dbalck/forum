<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2>Streams</h2>
<table class="table table-striped">
	<thead>
		<tr class="row">
			<th class="col-sm-4">Title</th>
			<th class="col-sm-4">Last Updated</th>
			<th class="col-sm-4">Link</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="row" items="${streams}">
			<tr class="row">
				<td class="col-sm-4">${row.title()}</td>
				<td class="col-sm-4">${row.date()}</td>
				<td class="col-sm-4">${row.link()}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<h2>Articles</h2>
<table class="table table-striped">
	<thead>
		<tr class="row">
			<th class="col-sm-4">Title</th>
			<th class="col-sm-4">Date</th>
			<th class="col-sm-4">Link</th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="row" items="${articles}">
			<tr class="row">
				<td class="col-sm-4">${row.title()}</td>
				<td class="col-sm-4">${row.date()}</td>
				<td class="col-sm-4">${row.link()}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<br />
