<%-- 
    Document   : paybill
    Created on : Feb 27, 2023, 5:24:31 PM
    Author     : giahu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1>Thanks for Order</h1>
<br>
<table cellpadding="0" cellspacing="0" border="0">
    <tr>
        <th>Product</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Subtotal</th>
    </tr>
    <c:set var="total" value="0"></c:set>
    <c:forEach var="item" items="${sessionScope.cart.items}">
    <c:set var="total" value="${total + item.cost}"></c:set>
        <tr>

            <td>
                <img src="<c:url value="/img/${item.product.image}" />" width="">
            ${item.product.name}
        </td>
        <td>${item.quantity}</td>
        <td>${item.product.price}</td>
        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.cost}"/></td>
    </tr>
    </c:forEach>
    <tr>
        <td colspan="3" align="left">Total</td>
        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.cost}"/></td>
    </tr>
</table>
<br>
<a href="<c:url value ="/watch/index.do" />">Continue Shopping</a>
