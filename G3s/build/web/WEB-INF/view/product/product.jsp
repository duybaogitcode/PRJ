<%-- 
    Document   : product
    Created on : Feb 23, 2023, 9:28:15 AM
    Author     : duyba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="show-product">
    <c:forEach var="product" items="${list}" varStatus="loop">
        <c:if test="${loop.index % 3 == 0}">
            <div class="row">
            </c:if>
            <div class="col-md-4">
                <h3>${product.name}</h3>
                <p>${product.description}</p>
                <p>${product.price}</p>
                <img src="<c:url value="/img/${product.image}" />">
            </div>
            <c:if test="${(loop.index + 1) % 3 == 0 || loop.last}">
            </div>
        </c:if>
    </c:forEach>
</div>



