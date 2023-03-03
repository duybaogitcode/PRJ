<%-- 
    Document   : product
    Created on : Feb 23, 2023, 9:28:15 AM
    Author     : duyba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="show-product">
    <table>
        <c:forEach var="product" items="${listPaging}" varStatus="loop">
            <c:if test="${loop.index % 3 == 0}">
                <tr>
                </c:if>
                <td>
                    <div class="show-product-box">
                        <h3>${product.name}</h3>
                        <img src="<c:url value="/img/${product.image}" />">
                        </br>
                        <p class="price"><fmt:formatNumber value="${product.price}" type = "currency" /></p>
                        <div class="star">
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="far fa-star"></i>
                        </div>

                        <div class="show-product-box-btn">
                            <a href="<c:url value ="/order/buynow.do?id=${product.id}"/>" class="show-product-box-btn-buy">Buy now</a>
                            <a href="<c:url value ="/order/add2cart.do?id=${product.id}"/>" class="show-product-box-btn-add">Add to cart</a>
                        </div>
                    </div>
                </td>
                <c:if test="${(loop.index + 1) % 3 == 0 || loop.last}">
                </tr>
            </c:if>
        </c:forEach>
    </table>
    <div class="index">
        <c:forEach begin="1" end="${endPage}" var = "i">
            <a class="page-index" href="<c:url value='/watch/index.do?index=${i}'/>">${i}</a>
        </c:forEach>
    </div>
</div>




