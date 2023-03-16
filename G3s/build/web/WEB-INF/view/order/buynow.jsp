<%-- 
    Document   : buynow
    Created on : Mar 2, 2023, 7:42:09 PM
    Author     : giahu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="order-content">
    <table cellpadding="0" cellspacing="0" border="0" style="width: 70%; margin: 60px auto;">
    <c:set var="total" value="0"></c:set>
    <c:set var="total" value="${total + item.cost}"></c:set>
        <tr>

            <td>
                <h3 class="text-center">
                   ${item.product.name} <img src="<c:url value="/img/${item.product.image}" />" width="600" style="object-fit: cover;">
                </h3>
        </td>
        <td>
            <h4 style="font-weight: 400;">${item.product.description}</h4>

                <form action="<c:url value="/order/buynow.do?id=${item.product.id}"/>" method="POST">
                    <button class="btn-order" type="submit" name="op" value="minus">-</button>
                ${item.quantity}
                <button class="btn-order" type="submit" name="op" value="add">+</button>
            </form>

                <p>Discount : $${item.product.discount}</p>
                <p>Price: $${item.product.price}</p>
                
        </td>
    </tr>
    <tr>
        <td class="text-center" style="font-size: 20px; font-weight: 700; margin:">Total</td>
        <td style="font-size: 20px; font-weight: 700;">$<fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/></td>
    </tr>
</table>
</div>
<br>

<div class="order-contetnt-footer">
    <a class="btn-order-conti" href="<c:url value ="/watch/filter.do" />">Continue Shopping</a> 
    <a class="btn-order-order" href="<c:url value ="/order/pay.do?op=buynow&total=${total}" />">Order</a>
</div>
