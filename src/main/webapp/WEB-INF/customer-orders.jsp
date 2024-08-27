<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Customers</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"
          rel="stylesheet">


    <script src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>

    <script>
        function createOrder(customerId) {
            console.log("a facut cv")
            $.ajax({
                url: '/order',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                async: false,
                data: JSON.stringify({
                    id: customerId
                })
            });

            location.reload();
        }

        function deleteOrder(customerId, orderId) {
            console.log("a facut cv")
            $.ajax({
                url: '/order',
                type: 'DELETE',
                dataType: 'json',
                contentType: 'application/json',
                async: false,
                data: JSON.stringify({
                    customerId: customerId,
                    orderId: orderId
                })
            });
            location.reload();
        }
    </script>
</head>
<body>
<div class="container">
    <h2 style="color: darkgreen;font-weight: bold">Orders for <span style="color: darkred;font-weight: bolder">${customer.name}&nbsp;</span>&nbsp;
        <button class="btn btn-primary" onclick="createOrder(${customer.id})">Add New Order</button>
        <a href="<c:url value="/customer"/>" class="btn btn-primary">Back to User Profile</a></h2>
    <table class="table table-striped">
        <tr>
            <th>Number</th>
            <th>Value</th>
            <th>Placed</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><c:out value="${order.number}"/></td>
                <td>
                    <c:out value="${order.value}"/>
                </td>
                <td><c:out value="${order.placed}"/></td>
                <td>
                    <a href="<c:url value="/static/customer-order-edit-rest.html?customerId=${customer.id}&orderId=${order.id}"/> "
                       class="btn btn-info">Edit</a>
                    <button onclick="deleteOrder(${customer.id}, ${order.id})" class="btn btn-info">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
