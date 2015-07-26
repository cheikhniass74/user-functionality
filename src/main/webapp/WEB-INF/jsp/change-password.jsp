
<%@include file="includes/header.jsp"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Change Password</h3>
	</div>
	<div class="panel-body">
		<form:form modelAttribute="changePasswordForm" role="form">
			<form:errors />
			<div class="form-group">
				<form:label path="password">Password</form:label>
				<form:password path="password"  class="form-control"
					id="exampleInputPassword1" placeholder="Password" />
					<form:errors path ="password" cssClass ="error" />
			</div>
			
			<div class="form-group">
				<form:label path="retypePassword">Password</form:label>
				<form:password path="retypePassword"  class="form-control"
					id="exampleInputPassword1" placeholder="Password" />
					<form:errors path ="retypePassword" cssClass ="error" />
			</div>
			
			<button type="submit" class="btn btn-default">Change password</button>
		</form:form>
		<%@include file="includes/footer.jsp"%>