<%-- 
    Document   : newjspedit
    Created on : Mar 3, 2023, 10:39:15 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="card mb-4">
    <div class="card-header">
        <!--        <i class="fas fa-table me-1"></i>-->
        <h2>Account Edit</h2>
    </div>
    <div class="card-body">
        <form action="<c:url value="/admin/edit_handler.do" />">
            <div class="mb-3 mt-3">
                <label for="id" class="form-label">Id: </label>
                <input type="text" class="form-control" id="id" value="${acc.id}" disabled="">
                <input type="hidden" name="id" value="${acc.id}">
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Name: </label>
                <input type="text" class="form-control" id="name" placeholder="Enter account name" name="name" value="${acc.name}">
            </div>
            <div class="mb-3">
                <label for="address" class="form-label">Address: </label>
                <input type="text" class="form-control" id="address" placeholder="Enter account address" name="address" value="${acc.address}">
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Phone: </label>
                <input type="text" class="form-control" id="phone" placeholder="Enter account phone" name="phone" value="${acc.phone}">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email: </label>
                <input type="text" class="form-control" id="email" placeholder="Enter account email" name="email" value="${acc.email}">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password: </label>
                <input type="text" class="form-control" id="password" placeholder="Enter account password" name="password" value="${acc.password}">
            </div>
            <div class="mb-3">
                <label for="enabled" class="form-label">Enabled: </label>
                <select name="enabled" class="form-control">
                    <c:forEach var="enabled" items="${eList}">
                        <option value="${enabled}" ${enabled == acc.enable ? "selected" : ""}>${enabled == true ? "Enabled" : "Disabled"}</option>
                    </c:forEach>
                </select>
            </div> 
            <div class="mb-3">
                <label for="role" class="form-label">Role: </label>
                <select name="role" class="form-control">
                    <c:forEach var="role" items="${rList}">
                        <option value="${role}" ${role == acc.role ? "selected" : ""}>${role}</option>
                    </c:forEach>
                </select>
            </div>     
            <button type="submit" class="btn btn-outline-success" name="op" value="update"><i class="fa-regular fa-circle-check"></i> Update</button>
            <button type="submit" class="btn btn-outline-danger" name="op" value="cancel"><i class="fa-regular fa-circle-xmark"></i> Cancel</button>
        </form>
            <i style="color: red;">${message}</i>
    </div>
</div>
