<%-- 
    Document   : index
    Created on : Mar 3, 2023, 10:02:10 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="card mb-4">
    <div class="card-header">
        <i class="fas fa-table me-1"></i>
        Order Management Table
    </div>
    <div class="card-body">  
        <div class="datatable-top">     
            <div class="datatable-search">
                <input oninput="searchByName(this)" class="datatable-input me-5" name="searchInput" placeholder="Search..." type="search" title="Search within table">
            </div>
        </div>
        <div id="content_tableOrder">
            <table class="table table-striped"> 
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Product name</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Operations</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${listPaging}">
                        <tr>
                            <td>${order.id != -1 ? order.id : ""}</td>
                            <td>
                                <fmt:formatDate value="${order.date}" pattern="dd-MM-yyyy"/>
                            </td>
                            <td>${order.status}</td>
                            <td>${order.productName}</td>
                            <td>${order.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${order.total}" type="currency"/>
                            </td>
                            <td>
                                <div ${order.id == -1 ? "hidden" : ""}>
                                    <a href="<c:url value="/admin_order/edit.do?id=${order.id}"/>"><i class="fa-solid fa-pen-to-square"></i></a> |
                                    <a href="<c:url value="/admin_order/delete.do?id=${order.id}"/>"><i class="fa-solid fa-trash-can"></i></a>
                                </div>
                            </td>
                        </tr>  
                    </c:forEach>
                </tbody>
            </table>
            <div class="datatable-bottom">
                <nav class="datatable-pagination">
                    <ul class="datatable-pagination-list">
                        <c:forEach begin="1" end="${endPage}" var="i">
                            <li class="datatable-pagination-list-item datatable-active">
                                <a class="${i == indexPage ? "btn btn-secondary" : "btn-secondary"}" href="<c:url value="/admin_order/index.do?indexPage=${i}"/>" class="btn btn-secondary datatable-pagination-list-item-link">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>

<%--<script src="<c:url value="/js/searchAdminProduct.js" />"></script>--%>

