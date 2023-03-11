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
        <table id="datatablesSimple">
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
            <tfoot>
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
            </tfoot>
            <tbody>
                <c:forEach var="acc" items="${list}" varStatus="loop">
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
                            <a href="<c:url value="/admin/edit.do?id=${acc.id}"/>"><i class="fa-solid fa-pen-to-square"></i></a> |
                            <a href="<c:url value="/admin/delete.do?id=${acc.id}&name=${acc.name}"/>"><i class="fa-solid fa-trash-can"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>