
<%@include file="includes/header.jsp"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Reset Password</h3>
	</div>
	<div class="panel-body">
		<form:form modelAttribute="forgotPasswordForm" role="form">
			<form:errors />
			<div class="form-group">
				<form:label path="email">Email address</form:label>
				<form:input path="email" type="email" class="form-control"
					id="exampleInputEmail1" placeholder="Enter email" />
				<form:errors path="email" cssClass="error" />
			</div>
			
			<button type="submit" class="btn btn-default">ResetPassword</button>
		</form:form>
		<%@include file="includes/footer.jsp"%>