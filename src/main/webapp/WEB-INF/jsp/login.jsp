<%@include file="includes/header.jsp"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Please Sign in</h3>
	</div>
	<div class="panel-body">
		<c:if test="${param.error!=null}">
			<div class="alert alert-danger" role="alert">Invalid username
				or password</div>
		</c:if>

		<c:if test="${param.logout != null}">
			<div class="alert alert-danger" role="alert">You have been
				logout</div>

		</c:if>
		<form:form role="form" method="post">
			<label for="username">Email address</label>
			<input id="username" name="username" type="email"
				class="form-control" placeholder="Enter email" />
			<p class="help-block">Enter your email address.</p>


			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					id="password" name="password" class="form-control"
					placeholder="Password" />
				<form:errors cssClass="error" path="password" />
			</div>


			<div class="form-group">
				<div class="checkbox">
					<label> <input type="checkbox" name = "_spring_security_remember_me"> Remember me
					</label>
				</div>
			</div>
			
			<button type="submit" class="btn btn-primary">Sign In</button>
			
			<a class="btn btn-default" href="/forgot-password">Forgot Password</a>
			
		</form:form>
	</div>
</div>
<%@include file="includes/footer.jsp"%>
