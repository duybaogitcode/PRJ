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
    <thead>
    <th>Product</th>
    <th>Quantity</th>
    <th>Price</th>
    <th>Cost</th>
</thead>
<c:set var="total" value="0"></c:set>
<c:choose>
    <c:when test="${op==null}">
        <tbody>
            <c:forEach var="item" items="${sessionScope.cart.items}">
                <c:set var="total" value="${total + item.cost}"></c:set>
                    <tr>
                        <td>
                            <img src="<c:url value="/img/${item.product.image}" />" width="">
                        ${item.product.name}
                    </td>
                    <td>${item.quantity}</td>
                    <td>$${item.product.price}</td>
                    <td>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${item.cost}"/></td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="3" align="left">Total</td>
                <td>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/></td>
            </tr>
        </tbody>
    </c:when>
    <c:otherwise>
        <tbody>
            <tr>
                <td>
                    <img src="<c:url value="/img/${sessionScope.item.product.image}" />" width="">
                    ${sessionScope.item.product.name}
                </td>
                <td>${sessionScope.item.quantity}</td>
                <td>${sessionScope.item.product.price}</td>
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${sessionScope.item.cost}"/></td>
            </tr>
            <tr>
                <td colspan="3" align="left">Total</td>
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${sessionScope.item.cost}"/></td>
            </tr>
        </tbody>
    </c:otherwise>
</c:choose>
</table>
<br>
<a href="<c:url value ="/watch/filter.do" />">Continue Shopping</a>
