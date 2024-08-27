<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>Login</title>
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
  </script>
  <style>
    #sup:hover{
      font-size: larger;
      cursor:pointer;
    }
    #homepage:hover{
      font-size: larger;
      cursor:pointer;
    }
    #main_div{
      background-color: white;
      margin-top: 45px;
      width: 400px;
      height: 430px;
      border-style: solid;
      border-width: 2px;
      border-color: dimgray;
    }
    #main_div_left{
      background-color: darkolivegreen;
      text-align: center;
      width: fit-content;
      height: fit-content;

    }
    #left_side{
     background-color: antiquewhite;
    }
    #right_side{
      background-color: darkolivegreen;
    }
    .full-height{
      height: 100vh;
    }
  </style>
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <div class="col full-height" id="right_side">
<h3>
  <label>Go to homepage</label>
  <a id="homepage" href="<c:url value="/homepage"/>" style="color: mediumblue;text-decoration: none;font-weight: bolder">Homepage</a>
</h3>
      <div id="main_div_left" class="container">
        <div id="image">
          <img src="https://images.pexels.com/photos/1749303/pexels-photo-1749303.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="image.jpg" width="350" height="250">
        </div>
        <div id="text" style="margin-top: 50px">
          <h1 style="color: whitesmoke">Join our Community</h1>
          <h4 style="color: whitesmoke;margin-top: 15px">Log in and get started to order!</h4>

        </div>

      </div>
    </div>
    <div class="col full-height" id="left_side">
<div id="main_div" class="container">
  <h1 style="margin: 11px;color: mediumblue">Log in page</h1>
  <form class="form-horizontal" role="form" method="post" action="/login">
    <div class="form-group">
      <label class="control-label col-sm-2" for="username">User:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="username" name="username" value="">
      </div>
      <label class="control-label col-sm-2" for="password">Password:</label>
      <div class="col-sm-10">
        <input type="password" class="form-control" id="password" name="password" value="">
        <span class="password-toggle" id="toggle-password">
                   <i class="bi bi-eye-slash"></i>
                </span>
      </div>
    </div>

    <% String error = (String) request.getAttribute("error");
      if (error != null && error != "") { %>
    <div style="width: fit-content;margin: 12px" class="alert alert-danger">Error: <% out.print(error); %></div>
    <% } %>

    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button class="btn btn-primary">Login</button>
        <br/>
        <label>Create an account</label>
        <a id="sup" href="<c:url value="/signup"/>" style="color: mediumblue;text-decoration: none;font-weight: bolder">Sign up</a>

      </div>
    </div>
  </form>
</div>
    </div>
  </div>
</div>
</body>
</html>
