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
                    <div class="card">
                        <p>${loop.index}</p>
                        <h4>${product.name}</h3>
                        <img src="<c:url value="/img/${product.image}" />">
                        <p><fmt:formatNumber value="${product.price}" type = "currency" /></p>
                    </div>
                </td>
                <c:if test="${(loop.index + 1) % 3 == 0 || loop.last}">
                </tr>
            </c:if>
        </c:forEach>
    </table>
    <c:forEach begin = "1" end = "${endPage}" var = "i">
        <a class="page-index" href="<c:url value ="/product/product.do?index=${i}" />">${i}</a>
    </c:forEach>
</div>




