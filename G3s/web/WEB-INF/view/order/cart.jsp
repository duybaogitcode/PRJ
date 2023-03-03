<%-- 
    Document   : cart
    Created on : Feb 27, 2023, 5:25:35 PM
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
    <c:forEach var="item" items="${sessionScope.cart.items}">
        <c:set var="total" value="${total + item.cost}"></c:set>
            <tr>

            <td>
                <form action="<c:url value="/order/cart.do?id=${item.product.id}"/>" method="POST">
                <img src="<c:url value="/img/${item.product.image}" />" width="">
                ${item.product.name}
                <button type="submit" name="op" value="remove">x</button>
                </form>
            </td>
            <td>
                <form action="<c:url value="/order/cart.do?id=${item.product.id}"/>" method="POST">
                    <button type="submit" name="op" value="minus">-</button>
                    ${item.quantity}
                    <button type="submit" name="op" value="add">+</button>
                </form>
            </td>
            <td>${item.product.price}</td>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.cost}"/></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="3" align="left">Total</td>
        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/></td>
    </tr>
</table>
<br>
<c:if test="${total>0}">
    <a href="<c:url value ="/order/pay.do" />">Order</a>
    <a href="<c:url value ="/order/cart.do?op=empty" />">Empty cart</a>
</c:if>
    <a href="<c:url value ="/watch/index.do" />">Continue Shopping</a>