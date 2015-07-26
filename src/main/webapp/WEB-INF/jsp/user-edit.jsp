<%@include file="includes/header.jsp"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Edit Profile</h3>
	</div>
	<div class="panel-body">
		<form:form modelAttribute="userEditForm" class="form-horizontal"
			role="form">
			<form:errors />
			<div class="form-group">
				<form:label path="name">Name</form:label>
				<form:input path="name" class="form-control"
					placeholder="Enter your name" />
				<form:errors path="name" cssClass="error" />
				<p class="help-block">Enter your display name.</p>
			</div>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div class="form-group">
					<form:label path="roles">Roles</form:label>
					<form:input path="roles" class="form-control"
						id="exampleInputPassword1" placeholder="Roles" />
					<form:errors path="roles" cssClass="error" />
				</div>
			</sec:authorize>
			<div class="form-group">
				<button type="submit" class="btn btn-default">update</button>
			</div>

		</form:form>
	</div>



</div>
<%@include file="includes/footer.jsp"%>