<%--
  Created by IntelliJ IDEA.
  User: Adriana
  Date: 2/10/2024
  Time: 6:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>SignUp</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script>
            const passInput=document.getElementById("password");
            const toggleButton=document.getElementById("toggle-password")
            toggleButton.addEventListener('click',()=>{
            if(passInput.type==="password"){
                passInput.type="text";
                toggleButton.innerHTML='<i class="bi bi-eye"></i>';
            }else {
                passInput.type="password";
                toggleButton.innerHTML='<i class="bi bi-eye-slash"></i>';
            }
        });
            const passInput2=document.getElementById("password2");
            const toggleButton2=document.getElementById("toggle-password2")
            toggleButton.addEventListener('click',()=>{
                if(passInput2.type==="password"){
                    passInput2.type="text";
                    toggleButton2.innerHTML='<i class="bi bi-eye"></i>';
                }else {
                    passInput2.type="password";
                    toggleButton2.innerHTML='<i class="bi bi-eye-slash"></i>';
                }
            });
    </script>
    <style>
        #lg:hover{
            font-size: larger;
            cursor:pointer;
        }
        body{
            background-image: url("https://thumbs.dreamstime.com/z/blue-flowers-card-17637680.jpg");
            background-attachment: fixed;

        }
        .error{
            color: red;
            font-weight: bolder;
            font-size: small;
        }
        #main_div{
            background-color: whitesmoke;
            width: 695px;
            height: fit-content;
            border-style: solid;
            border-width: 2px;
            border-color:cadetblue;
        }
        #homepage2:hover{
            font-size: larger;
            cursor: pointer;
        }
        label{
            color: darkolivegreen;
        }
    </style>
</head>
<body>
<h3>
    <label>Go to homepage</label>
    <a id="homepage2" href="<c:url value="/homepage"/>" style="color: mediumblue;text-decoration: none;font-weight: bolder">Homepage</a>
</h3>
<div id="main_div" class="container">
    <h1 style="margin: 11px;color: mediumblue">Sign up page</h1>
    <p style="margin:11px;font-style: italic;color: mediumblue">Join our community</p>
    <form id="myForm" class="form-horizontal" role="form" method="post" action="/signup">
        <div class="form-group">
            <label class="control-label col-sm-2" for="name">Name:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name" value="">
                <span id="nameError" class="error"></span>
            </div>
            <label class="control-label col-sm-2" for="phone">Phone:</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" id="phone" name="phone" value="">
                <span id="phoneError" class="error"></span>
            </div>
            <label class="control-label col-sm-2" for="email">Email:</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="email" name="email" placeholder="jeni.braw@yahoo.com">
                <span id="emailError" class="error"></span>
            </div>
            <label class="control-label col-sm-2" for="birthday">Your_birthdate:</label>
            <div class="col-sm-10">
                <input type="date" class="form-control" id="birthday" name="birthday" placeholder="YY-MM-DD">
                <span id="birthdayError" class="error"></span>
            </div>
            <label class="control-label col-sm-2" for="username">UserName:</label>
            <br/>
            <p style="font-size: small;color: darkblue;margin: 12px">The username can contain alphanumeric, underscores, and hyphens, 3 to 16 characters long</p>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="username" name="username" value="">
                <span id="usernameError" class="error"></span>
            </div>
            <label class="control-label col-sm-2" for="password">Password:</label>
            <br/>
            <p style="font-size: small;color: darkblue;margin: 12px">The password should be at least 6 characters long and should contain a mixture of letters and numbers</p>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" name="password" value="" placeholder="Enter your password">
                <span class="password-toggle" id="toggle-password">
                   <i class="bi bi-eye-slash"></i>
                </span>
                <span id="passwordError" class="error"></span>
            </div>
            <label class="control-label col-sm-2" for="password">Confirm_password:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password2" name="password2" value="" placeholder="Enter again your password">
                <span class="password-toggle" id="toggle-password2">
                   <i class="bi bi-eye-slash"></i>
                </span>
                <span id="password2Error" class="error"></span>
            </div>

        </div>

        <% String error = (String) request.getAttribute("error1");
            if (error != null && error != "") { %>
        <div style="width: fit-content;margin: 12px" class="alert alert-danger">Error: <% out.print(error); %></div>
        <% } %>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-primary">Sign up</button>
                <input type="reset" value="Reset" class="btn btn-primary"/>
                <br/>
                <label>If you already have an account</label>
                <a id="lg" href="<c:url value="/login"/>" style="color: mediumblue;text-decoration: none;font-weight: bolder;">Login</a>
            </div>
        </div>
    </form>
</div>
<script>
    function validateForm(event){
        event.preventDefault();
        var name=document.getElementById('name').value;
        var phone=document.getElementById('phone').value;
        var email=document.getElementById('email').value;
        var birthday=document.getElementById('birthday').value;
        var username=document.getElementById('username').value;
        var password=document.getElementById('password').value;
        var password2=document.getElementById('password2').value;

        //reset error message
        document.getElementById('nameError').textContent='';
        document.getElementById('phoneError').textContent='';
        document.getElementById('emailError').textContent='';
        document.getElementById('birthdayError').textContent='';
        document.getElementById('usernameError').textContent='';
        document.getElementById('passwordError').textContent='';
        document.getElementById('password2Error').textContent='';

        var isValid=true;
        if(name.trim()===''){
            document.getElementById('nameError').textContent='Name is required';
            isValid=false;
        }
        if(phone.trim()===''){
            document.getElementById('phoneError').textContent='Phone is required';
            isValid=false;
        }
        if(email.trim()===''){
            document.getElementById('emailError').textContent='Email is required';
            isValid=false;
        }
        if(birthday.trim()===''){
            document.getElementById('birthdayError').textContent='Birthday is required';
            isValid=false;
        }
        if(username.trim()===''){
            document.getElementById('usernameError').textContent='Username is required';
            isValid=false;
        }
        if(password.trim()===''){
            document.getElementById('passwordError').textContent='Password is required';
            isValid=false;
        }
        if(password2.trim()===''){
            document.getElementById('password2Error').textContent='Confirmed password is required';
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
