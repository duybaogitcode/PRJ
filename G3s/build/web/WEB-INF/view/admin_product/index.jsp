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
        Product Management Table
    </div>
    <div class="card-body">  
        <div class="datatable-top"> 
            <div class="datatable-active">
                <a class="btn btn-outline-dark" href="<c:url value="/admin_product/create.do"/>"><i class="fa-solid fa-plus"></i> Create</a>
            </div>
            <div class="datatable-search">
                <input oninput="searchByName(this)" class="datatable-input me-5" name="searchInput" placeholder="Search..." type="search" title="Search within table">
            </div>
        </div>
        <div id="content_tableProduct">
            <table class="table table-striped"> 
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Image</th>
                        <th>Price</th>
                        <th>Discount</th>
                        <th>Category Id</th>
                        <th>Operations</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${listPaging}">
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>${product.description}</td>
                            <td><img src="<c:url value="/img/${product.image}"/>" width="90px" height="90px"></td>
                            <td>
                                <fmt:formatNumber value="${product.price}" type="currency"/>
                            </td>
                            <td>
                                <fmt:formatNumber value="${product.discount}" type="currency"/>
                            </td>
                            <td>${product.categoryID}</td>
                            <td>
                                <a href="<c:url value="/admin_product/edit.do?id=${product.id}"/>"><i class="fa-solid fa-pen-to-square"></i></a> |
                                <a href="<c:url value="/admin_product/delete.do?id=${product.id}&name=${product.name}"/>"><i class="fa-solid fa-trash-can"></i></a>
                            </td>
                        </tr>  
                    </c:forEach>
                </tbody>
            </table>
            <div class="datatable-bottom">
                <%--                <div class="datatable-info">Showing ${indexPage} to ${indexPage * 10} of ${listSize} entries</div>--%>
                <nav class="datatable-pagination">
                    <ul class="datatable-pagination-list">
                        <c:forEach begin="1" end="${endPage}" var="i">
                            <li class="datatable-pagination-list-item datatable-active">
                                <a class="${i == indexPage ? "btn btn-secondary" : "btn-secondary"}" href="<c:url value="/admin_product/index.do?indexPage=${i}"/>" class="btn btn-secondary datatable-pagination-list-item-link">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/js/searchAdminProduct.js" />"></script>

