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
        Account Management Table
    </div>
    <div class="card-body">  
        <div class="datatable-top"> 
            <div class="datatable-search">
                <input oninput="searchByName(this)" class="datatable-input me-5" name="searchInput" placeholder="Search..." type="search" title="Search within table">
            </div>
        </div>
        <div id="content_tableAccount">
            <table class="table table-striped"> 
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Password</th>
                        <th>Enable</th>
                        <th>Role</th>
                        <th>Operations</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="acc" items="${listPaging}">
                        <tr>
                            <td>${acc.id}</td>
                            <td>${acc.name}</td>
                            <td>${acc.address}</td>
                            <td>${acc.phone}</td>
                            <td>${acc.email}</td>
                            <td>${acc.password}</td>
                            <td>${acc.enable == true ? '<span class="enable_true">Enable</span>' : '<span class="enable_false">Disable</span>'}</td>
                            <td>${acc.role}</td>
                            <td>
                                <a href="<c:url value="/admin_account/edit.do?id=${acc.id}&email=${acc.email}"/>"><i class="fa-solid fa-pen-to-square"></i></a> |
                                <a href="<c:url value="/admin_account/delete.do?id=${acc.id}&name=${acc.name}"/>"><i class="fa-solid fa-trash-can"></i></a>
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
                                <a class="${i == indexPage ? "btn btn-secondary" : "btn-secondary"}" href="<c:url value="/admin_account/index.do?indexPage=${i}"/>" class="btn btn-secondary datatable-pagination-list-item-link">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/js/searchAdminAccount.js" />"></script>