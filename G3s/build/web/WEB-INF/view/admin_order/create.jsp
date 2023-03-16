<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="card mb-4">
    <div class="card-header">
        <h2>Account Create</h2>
    </div>
    <div class="card-body">
        <form action="<c:url value="/admin_product/create_handler.do" />">
            <div class="mb-3">
                <label for="name" class="form-label">Name:</label>
                <input type="text" class="form-control" id="name" placeholder="Enter product name" name="name" value="${product.name}">
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Description:</label>
                <input type="text" class="form-control" id="description" placeholder="Enter product description" name="description" value="${product.description}">
            </div>
            <div class="mb-3">
                <label for="image" class="form-label">Image:</label>
                <input type="text" class="form-control" id="image" placeholder="Enter product image" name="image" value="${product.image}">
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Price:</label>
                <input type="number" step="0.1" class="form-control" id="price" placeholder="Enter product price" name="price" value="${product.price}">
            </div>
            <div class="mb-3">
                <label for="discount:" class="form-label">Discount:</label>
                <input type="number" step="0.1" class="form-control" id="price" placeholder="Enter product discount" name="discount" value="${product.discount}">
            </div>
            <div class="mb-3">
                <label for="categoryID" class="form-label">Category iD:</label>
                <select name="categoryID" class="form-control">
                    <c:forEach var="categoryID" items="${categoryIDList}">
                        <option value="${categoryID}" ${categoryID == product.categoryID ? "selected" : ""}>${categoryID}</option>
                    </c:forEach>
                </select>                
            </div>

            <button type="submit" class="btn btn-outline-success" name="op" value="create"><i class="bi bi-check-circle"></i> Create</button>
            <button type="submit" class="btn btn-outline-danger" name="op" value="cancel"><i class="bi bi-x-circle"></i> Cancel</button>
        </form>
        <i style="color: red;">${message}</i>
    </div>
</div>