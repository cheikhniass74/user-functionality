
<%@include file="includes/header.jsp"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Change Email</h3>
	</div>
	<div class="panel-body">
		<form:form modelAttribute="changeEmailForm" role="form">
			<form:errors />
			<div class="form-group">
				<form:label path="email">Email address</form:label>
				<form:input path="email" type="email" class="form-control"
					id="exampleInputEmail1" placeholder="Enter email" />
				<form:errors path="email" cssClass="error" />
			</div>

			<div class="form-group">
				<form:label path="retypeEmail">Email address</form:label>
				<form:input path="retypeEmail" type="email" class="form-control"
					id="exampleInputEmail1" placeholder="Enter email" />
				<form:errors path="retypeEmail" cssClass="error" />
			</div>
			<button type="submit" class="btn btn-default">Change Email</button>
		</form:form>
		<%@include file="includes/footer.jsp"%>