<%@ page import="siit.model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>Customers</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
      <style>
          #updateProd:hover{
              font-size: 20px;
          }
      </style>
  </head>

  <body>
  <% if ("admin".equals(request.getSession().getAttribute("role"))) {%>
  	<div class="page-header">
        <div class="pull-left">
            <%--            ${logged_user} = out.print(req.getAttribute("logged_user")) --%>
            <label style="color: darkgreen;font-weight: bold;font-size: 25px">Welcome <span style="color: darkred;font-weight: bolder">${logged_user}!</span> This is a list of customers.</label>&nbsp;
            <a href="/logout" class="btn btn-primary">Logout</a>
        </div>
  		<div class="clearfix"></div>
  	</div>
  <%}else {%>
  <div class="page-header">
      <div class="pull-left">
          <%--            ${logged_user} = out.print(req.getAttribute("logged_user")) --%>
          <label style="color: darkgreen;font-weight: bold;font-size: 25px">Welcome <span style="color: darkred;font-weight: bolder">${logged_user}!</span> This is your user profile.</label>&nbsp;
          <a href="/logout" class="btn btn-primary">Logout</a>
      </div>
      <div class="clearfix"></div>
  </div>
  <%}%>

    <br>

        <table class="table table-striped">
            <tr>
                <th>Name</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Birthday</th>
                <th>Actions</th>
            </tr>
            <c:forEach items="${customers}" var="customer">
                <tr>
                    <td><c:out value="${customer.name}" /></td>
                    <td><c:out value="${customer.phone}" /></td>
                    <td><c:out value="${customer.email}" /></td>
                    <td><c:out value="${customer.birthDate}" /></td>
                    <td>
                        <a href="<c:url value="/customer_edit?id=${customer.id}"/> " class="btn btn-info">Edit</a>
                        <a href="<c:url value="/order?id=${customer.id}"/> " class="btn btn-info">View Orders</a>
                    </td>
                </tr>
            </c:forEach>


<%--            <% List< Customer> customers = (List<Customer>) request.getAttribute("customers");--%>
<%--               for (Customer customer : customers){--%>
<%--            %>--%>

<%--                <tr>--%>
<%--                    <td><% out.print(customer.getName()); %></td>--%>
<%--                    <td><% out.print(customer.getPhone()); %></td>--%>
<%--                    <td><% out.print(customer.getEmail()); %></td>--%>
<%--                    <td><% out.print(customer.getBirthDate()); %></td>--%>
<%--                    <td>--%>
<%--                        <a href=" <% out.print("/customer_edit?id=" + customer.getId()); %> " class="btn btn-info">Edit</a>--%>
<%--                        <a href="<% out.print("/order?id="+ customer.getId()); %> " class="btn btn-info">View Orders</a>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--         <% } %>--%>
    </table>
  <% if ("admin".equals(request.getSession().getAttribute("role"))) {%>
  <div>
      <label style="font-weight: bolder;color: crimson;font-size: larger;font-style: italic">Here you can add, update, or delete products:</label>&nbsp;
      &nbsp;&nbsp;<a id="updateProd" href="<c:url value="/static/products-edit.html"/> " class="btn btn-primary">Click here</a>
  </div>
  <%}%>
  </body>
</html>
