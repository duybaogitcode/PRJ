<%-- 
    Document   : buynow
    Created on : Mar 2, 2023, 7:42:09 PM
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
        <th>Cost</th>
    </tr>
    <c:set var="total" value="0"></c:set>
    <c:set var="total" value="${total + item.cost}"></c:set>
        <tr>

            <td>
                <img src="<c:url value="/img/${item.product.image}" />" width="">
            ${item.product.name}
        </td>
        <td>
            <form action="<c:url value="/order/buynow.do?id=${item.product.id}"/>" method="POST">
                <button type="submit" name="op" value="minus">-</button>
                ${item.quantity}
                <button type="submit" name="op" value="add">+</button>
            </form>
        </td>
        <td>$${item.product.price}</td>
        <td>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${item.cost}"/></td>
    </tr>
    <tr>
        <td colspan="3" align="left">Total</td>
        <td>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/></td>
    </tr>
</table>
<br>
<a href="<c:url value ="/order/pay.do?op=buynow" />">Order</a>
<a href="<c:url value ="/watch/filter.do" />">Continue Shopping</a>
