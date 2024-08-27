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
        #myForm3{
            width: 500px;
            background-color: pink;
            margin-top: 25px;
            border-width: 2px;
            border-style: solid;
            border-color: darkmagenta;
        }
        label{
            color: darkmagenta;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
    <h2 style="color: darkgreen;font-weight: bold;margin-left: 5px">Update product</h2>

    <c:if test="${not empty error3}">
        <div class="alert alert-danger">Error: ${error3}</div>
    </c:if>

    <form id="myForm3" class="form-horizontal" role="form" method="post">
        <div class="form-group">
            <label class="control-label col-sm-2" for="name">Name:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name" value="<c:out value="${product.name}"/>">
                <span id="nameError" class="error"></span>
            </div>
            <label class="control-label col-sm-2" for="price">Price:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="price" name="price" value="${product.price}">
                <span id="priceError" class="error"></span>
            </div>
            <label class="control-label col-sm-2" for="stock">Stock:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="stock" name="stock" value="${product.stock}">
                <span id="stockError" class="error"></span>
            </div>
        </div>
        <% String error = (String) request.getAttribute("error9");
            if (error != null && error != "") { %>
        <div style="width: fit-content;margin: 12px" class="alert alert-danger">Error: <% out.print(error); %></div>
        <% } %>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-primary">Save</button>
                <input type="reset" value="Reset" class="btn btn-primary"/>
                <a href="<c:url value="/static/products-edit.html"/>" class="btn btn-warning">Cancel</a>
            </div>
        </div>
    </form>
        </div>
        <div class="col">
            <img src="/static/images/tom_cat.png" width="400" height="500" alt="cat.png">
        </div>
    </div>
</div>
<script>
    function validateForm(event){
        event.preventDefault();
        var name=document.getElementById('name').value;
        var price=document.getElementById('price').value;
        var stock=document.getElementById('stock').value;


        //reset error message
        document.getElementById('nameError').textContent='';
        document.getElementById('priceError').textContent='';
        document.getElementById('stockError').textContent='';

        var isValid=true;

        if(name.trim()===''){
            document.getElementById('nameError').textContent='Name is required';
            isValid=false;
        }
        if(price.trim()===''){
            document.getElementById('priceError').textContent='Price is required';
            isValid=false;
        }

        if(stock.trim()===''){
            document.getElementById('stockError').textContent='Stock is required';
            isValid=false;
        }

        if(isValid){
            document.getElementById('myForm3').submit();
        }
    }
    document.getElementById('myForm3').addEventListener('submit',validateForm);
</script>


</body>
</html>
