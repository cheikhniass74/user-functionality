<%@include file="includes/header.jsp"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Please Sign up</h3>
	</div>
	<div class="panel-body">
		<form:form modelAttribute="signupForm" role= "form">
		<form:errors/>
			<div class="form-group">
				<form:label path="name">Name</form:label>
				<form:input path="name" class="form-control"
					placeholder="Enter your name" />
					<form:errors path ="name" cssClass ="error" />
				<p class="help-block">Enter your display name.</p>
			</div>
			<div class="form-group">
				<form:label path="emailAddress">Email address</form:label>
				<form:input path="emailAddress" type="email" class="form-control"
					id="exampleInputEmail1" placeholder="Enter email" />
					<form:errors path ="emailAddress" cssClass ="error" />
			</div>
			<div class="form-group">
				<form:label path="password">Password</form:label>
				<form:password path="password"  class="form-control"
					id="exampleInputPassword1" placeholder="Password" />
					<form:errors path ="password" cssClass ="error" />
			</div>

			<button type="submit" class="btn btn-default">Submit</button>
			
		</form:form>


	</div>
</div>
<%@include file="includes/footer.jsp"%>
