<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Customer</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
      crossorigin="anonymous">
	<style>
		.error{
			color: red;
			font-size: small;
		}
	</style>
</head>
<body>

	<div class="container">
		<h2 style="color: darkgreen;font-weight: bold">Edit Customer</h2>

		<c:if test="${not empty error3}">
            <div class="alert alert-danger">Error: ${error3}</div>
        </c:if>

		<form id="myForm" class="form-horizontal" role="form" method="post">
			<div class="form-group">
				<label class="control-label col-sm-2" for="name">Name:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name" value="<c:out value="${customer.name}"/>">
					<span id="nameError" class="error"></span>
				</div>
				<label class="control-label col-sm-2" for="phone">Phone:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="phone" name="phone" value="${customer.phone}">
					<span id="phoneError" class="error"></span>
                </div>
				<label class="control-label col-sm-2" for="phone">Birthday:</label>
				<div class="col-sm-10">
					<input type="date" class="form-control" id="date" name="date" value="${customer.birthDate}">
					<span id="birthdayError" class="error"></span>
				</div>
			</div>
			<% String error = (String) request.getAttribute("error4");
				if (error != null && error != "") { %>
			<div style="width: fit-content;margin: 12px" class="alert alert-danger">Error: <% out.print(error); %></div>
			<% } %>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-primary">Save</button>
					<input type="reset" value="Reset" class="btn btn-primary"/>
					<a href="<c:url value="/customer"/>" class="btn btn-warning">Cancel</a>
				</div>
			</div>
		</form>
	</div>
	<script>
		function validateForm(event){
			event.preventDefault();
			var name=document.getElementById('name').value;
			var phone=document.getElementById('phone').value;
			var birthday=document.getElementById('date').value;


			//reset error message
			document.getElementById('nameError').textContent='';
			document.getElementById('phoneError').textContent='';
			document.getElementById('birthdayError').textContent='';

			var isValid=true;

			if(name.trim()===''){
				document.getElementById('nameError').textContent='Name is required';
				isValid=false;
			}
			if(phone.trim()===''){
				document.getElementById('phoneError').textContent='Phone is required';
				isValid=false;
			}

			if(birthday.trim()===''){
				document.getElementById('birthdayError').textContent='Birthday is required';
				isValid=false;
			}

			if(isValid){
				document.getElementById('myForm').submit();
			}
		}
		document.getElementById('myForm').addEventListener('submit',validateForm);
	</script>


</body>
</html>
