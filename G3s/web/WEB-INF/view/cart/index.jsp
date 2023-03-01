<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h2>Shopping Cart</h2>
<table class="table table-striped">
    <thead>
        <tr> 
            <th class="text-right">No.</th>    
            <th>Delete</th>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>

        </tr>
    </thead>
    <tbody>
        <tr>
            <c:forEach var="pr" items="${list}" varStatus="loop">
                <td  class="text-right">${loop.count}</td>
                <td><a href="<c:url value="/cart/delete.do?id="/>">Delete</a></td>
                <td>${pr.id}</td>
                <td>${pr.name}</td>
                <td>${pr.price}</td>
                <td>${pr.quantity}</td>
                <td>${pr.price*pr.quantity}</td>
            </c:forEach>
        </tr>

    </tbody>
</table>
