<%-- 
    Document   : index
    Created on : Mar 2, 2023, 5:02:14 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table table-striped">
    <thead>
        <tr>
            <th>No.</th>
            <th>ID</th>
            <th>Name</th>
            <th>Unit Price</th>
            <th>Quantity</th>
            <th>Total</th
            <th>Delete</th>
            <th>Update</th>

        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${sessionScope.cart.items}" varStatus="loop">
            <tr>
                <td  class="text-right">${loop.count}</td>
                <td>${item.pr.id}</td>
                <td>>${item.pr.name}</td>
                <td>>${item.pr.unitPrice}</td>
                <td>>${item.quantity}</td>
                <td>>${item.total}</td>
                <td>
                    <a href="<c:url value="/cart/delete.do?id=${item.pr.id}"/>">Delete</a>
                </td>
                <td>  
                    <a href="<c:url value="/cart/update.do?id=${item.pr.id}"/>">Update</a>
                </td>

            </tr>
            </tr>
        </c:forEach>
    </tbody>
</table>
<button type="submit" value="<c:url value="/cart/empty.do"/>">
