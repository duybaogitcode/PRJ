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
        <form action="<c:url value="/admin_product/edit_handler.do" />">
            <div class="mb-3 mt-3">
                <label for="id" class="form-label">Id: </label>
                <input type="text" class="form-control" id="id" value="${product.id}" disabled="">
                <input type="hidden" name="id" value="${product.id}">
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Name: </label>
                <input type="text" class="form-control" id="name" placeholder="Enter product name" name="name" value="${product.name}" required="">
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Description: </label>
                <input type="text" class="form-control" id="description" placeholder="Enter product description:" name="description" value="${product.description}" required="">
            </div>
            <div class="mb-3">
                <label for="img" class="form-label">Image link: </label>
                <input type="hidden" class="form-control" id="image" placeholder="Enter product img" name="imgHidden" value="${product.image}">
                <input type="file" class="form-control" id="image" placeholder="Enter product img" name="img">
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Price: </label>
                <input type="number" min="50" max="100000" class="form-control" id="price" placeholder="Enter product price" name="price" value="${product.price}" step="0.01" required="">
            </div>
            <div class="mb-3">
                <label for="discount" class="form-label">Discount: </label>
                <input type="number" min="0.01" max="0.99" class="form-control" id="discount" placeholder="Enter product discount" name="discount" step="0.01" value="${product.discount}" required=""> 
            </div>
            <div class="mb-3">
                <label for="categoryID" class="form-label">Category ID: </label>
                <select name="categoryID" class="form-control">
                    <c:forEach var="category" items="${listCate}">
                        <option value="${category.id}" ${category.id == product.categoryID ? "selected" : ""}>${category.name}</option>
                    </c:forEach>
                </select>
            </div> 
            <button type="submit" class="btn btn-outline-success" name="op" value="update"><i class="fa-regular fa-circle-check"></i> Update</button>
            <button type="submit" class="btn btn-outline-danger" name="op" value="cancel"><i class="fa-regular fa-circle-xmark"></i> Cancel</button>
        </form>
        <i style="color: red;">${message}</i>
    </div>
</div>

