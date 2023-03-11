<%-- 
    Document   : cart
    Created on : Feb 27, 2023, 5:25:35 PM
    Author     : giahu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    table, th, td{
        border:1px solid black;
    }
    table{
        border-collapse:collapse;
    }
</style>

<div class="order-content">
    <table cellspacing="50" style="width: 45%; margin: 50px auto;">
        <tr>
            <th class="text-center">Product</th>
            <th class="text-center">Quantity</th>
            <th class="text-center">Price</th>
            <th class="text-center">Cost</th>
        </tr>
        <c:set var="total" value="0"></c:set>
        <c:forEach var="item" items="${sessionScope.cart.items}">
            <c:set var="total" value="${total + item.cost}"></c:set>
                <tr border = "1">

                    <td class="text-center" style="width: 300px;">
                        <form action="<c:url value="/order/cart.do?id=${item.product.id}"/>" method="POST">
                        <h5>${item.product.name} <button class="btn-order" type="submit" name="op" value="remove">x</button>
                            <img src="<c:url value="/img/${item.product.image}"/>" style="height: 100px; width: 150px" /></h5>
                    </form>
                </td>
                <td class="text-center" style="width: 200px;">
                    <form action="<c:url value="/order/cart.do?id=${item.product.id}"/>" method="POST">
                        <button class="btn-order" type="submit" name="op" value="minus">-</button>
                        ${item.quantity}
                        <button class="btn-order"  type="submit" name="op" value="add">+</button>
                    </form>
                </td>
                <td class="text-center" style="width: 150px;">$${item.product.price}</td>
                <td class="text-center" style="width: 150px;">$<fmt:formatNumber type="number" maxFractionDigits="2" value="${item.cost}"/></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" align="left">Total</td>
            <td>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/></td>
        </tr>
    </table>
</div>
<br/>
<div class="order-contetnt-footer">
    <a class="btn-order-conti" href="<c:url value ="/watch/filter.do" />">Continue Shopping</a>
    <c:if test="${total>0}">
        <a class="btn-order-order" href="<c:url value ="/order/pay.do?total=${total}" />">Order</a>
        <a class="btn-order-empty" href="<c:url value ="/order/cart.do?op=empty" />">Empty cart</a>
    </c:if>
</div>
