<%-- 
    Document   : paybill
    Created on : Feb 27, 2023, 5:24:31 PM
    Author     : giahu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table cellpadding="0" cellspacing="0" border="0">
    <tr>
        <th>Product</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Subtotal</th>
    </tr>
    <c:set var="total" value="0"></c:set>
    <c:forEach var="item" items="${sessionScope.cart}">
        <c:set var="total" value="${total + orderdetail.price * orderdetail.quantity * orderdetail.discount}"></c:set>
            <tr>

                <td>
                    <img src="<c:url value="/img/${orderdetail.image}" />" width="">
                ${orderdetail.name}
            </td>
            <td>${orderdetail.quantity}</td>
            <td>${orderdetail.price}</td>
            <td>${orderdetail.price * orderdetail.quantity * orderdetail.discount}</td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="4" align="right">Total</td>
        <td>${total}</td>
    </tr>
</table>
<br>
<a href="<c:url value ="/watch/index.do"/>">Continue Shopping</a>
