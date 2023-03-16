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
        <h2>Order Edit</h2>
    </div>
    <div class="card-body">
        <form action="<c:url value="/admin_order/edit_handler.do" />">
            <div class="mb-3 mt-3">
                <label for="id" class="form-label">Id: </label>
                <input type="text" class="form-control" id="id" value="${orderHeader.id}" disabled="">
                <input type="hidden" name="id" value="${orderHeader.id}">
            </div>
            <div class="mb-3">
                <label for="status" class="form-label">Status </label>
                <input required="" type="text" class="form-control" id="status" placeholder="Enter order status" name="status" value="${orderHeader.status}">
            </div>
            <button type="submit" class="btn btn-outline-success" name="op" value="update"><i class="fa-regular fa-circle-check"></i> Update</button>
            <button type="submit" class="btn btn-outline-danger" name="op" value="cancel"><i class="fa-regular fa-circle-xmark"></i> Cancel</button>
        </form>
        <i style="color: red;">${message}</i>
    </div>
</div>
