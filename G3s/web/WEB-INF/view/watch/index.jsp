<%-- 
    Document   : product
    Created on : Feb 23, 2023, 9:28:15 AM
    Author     : duyba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div class="show-product">
    <div class="filter col-md-4">
        <div class="filter-titile">
            <h4>Filters</h4>
            <i class="icon-filter"><ion-icon name="grid"id="filter-icon" onclick="show()"></ion-icon></i>
        </div>   

        <form method="get" action="<c:url value="/watch/filter.do" />" id="hidden" style="display: none">
            <h5>Category</h5>
            <div class="cate-form">
                <c:set var="allChecked" value=""/>
                <c:if test="${categoryIds == null}">
                    <c:set var="allChecked" value="checked"/>
                </c:if>
                <input type="checkbox" id="all" name="all" value="all" ${allChecked}> All<br>
                <c:forEach var="category" items="${listCate}">
                    <c:set var="checked" value=""/>
                    <c:if test="${not empty categoryIds}">
                        <c:forEach var="categoryId" items="${categoryIds}">
                            <c:if test="${categoryId eq category.id}">
                                <c:set var="checked" value="checked"/>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <input type="checkbox" id="category" name="category" value="${category.id}" ${checked}>${category.name}<br>
                </c:forEach>

                <h5>Sort By</h5>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="sort" id="sort-price-asc" value="price_asc">
                    <label class="form-check-label" for="sort-price-asc">
                        Price: Low to High
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="sort" id="sort-price-desc" value="price_desc">
                    <label class="form-check-label" for="sort-price-desc">
                        Price: High to Low
                    </label>
                </div>
            </div>

            <h5>Price Range</h5>
            <p>User slider or enter min and max price</p>
            <div class="price-input">
                <div class="field">
                    <span>Min</span>
                    <input type="number" class="input-min" id="min" name="min" value="${minPrice}" step="0.1">
                    <p>${sessionScope.minPrice}</p>
                </div>
                <div class="field">
                    <span>Max</span>
                    <input type="number" class="input-max" id="max" name="max" value="${maxPrice}" step="0.1">
                </div>
            </div>
            </br>
            <div class="slider">
                <div class="progress">
                </div>
            </div>
            <div class="range-input">
                <input type="range" class="range-min" min="0" max="10000" value="${minPrice}">
                <input type="range" class="range-max" min="0" max="10000" value="${maxPrice}">
            </div>
            </br>
            <button type="submit" class="btn btn-primary">Filter</button>
        </form>
        <script>
            function show() {
                if (document.getElementById("hidden").style.display === "none") {
                    document.getElementById("hidden").style.display = "block";
                    return;
                } else {
                    document.getElementById("hidden").style.display = "none";
                    return;
                }
            }
            const rangeInput = document.querySelectorAll(".range-input input");
            const priceInput = document.querySelectorAll(".price-input input");
            const progress = document.querySelector(".slider .progress");

            let priceGap = 100;

            rangeInput.forEach(input => {
                input.addEventListener("input", e => {
                    let minVal = parseInt(rangeInput[0].value);
                    let maxVal = parseInt(rangeInput[1].value);
                    if (maxVal - minVal < priceGap) {
                        if (input.className === "range-min") {
                            rangeInput[0].value = maxVal - priceGap;
                        } else {
                            rangeInput[1].value = minVal + priceGap;
                        }
                    } else {
                        priceInput[0].value = minVal;
                        priceInput[1].value = maxVal;
                        progress.style.left = (minVal / rangeInput[0].max) * 100 + "%";
                        progress.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
                        progress.style.width = ((maxVal - minVal) / rangeInput[1].max) * 100 + "%";
                    }
                });

            });

            priceInput.forEach(input => {
                input.addEventListener("input", () => {
                    let minVal = parseInt(priceInput[0].value);
                    let maxVal = parseInt(priceInput[1].value);
                    if (maxVal - minVal >= priceGap && maxVal <= 10000) {
                        rangeInput[0].value = minVal;
                        rangeInput[1].value = maxVal;
                        progress.style.left = (minVal / rangeInput[0].max) * 100 + "%";
                        progress.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
                        progress.style.width = ((maxVal - minVal) / rangeInput[1].max) * 100 + "%";
                    }
                });
            });

            var allCheckbox = document.getElementById("all");
            var categoryCheckboxes = document.getElementsByName("category");

            allCheckbox.addEventListener("change", function () {
                if (allCheckbox.checked) {
                    for (var i = 0; i < categoryCheckboxes.length; i++) {
                        categoryCheckboxes[i].checked = false;
                    }
                }
            });

            for (var i = 0; i < categoryCheckboxes.length; i++) {
                categoryCheckboxes[i].addEventListener("change", function () {
                    if (this.checked) {
                        allCheckbox.checked = false;
                    }
                });
            }
        </script>
    </div>
    <hr>

    <c:if test="${empty listPaging}">
        <img src="<c:url value="/img/no-product-found.png" />" style="display: block; margin: 0 auto; width: 50%;">
    </c:if>

    <c:if test="${not empty listPaging}">
        <table>
            <c:forEach var="product" items="${listPaging}" varStatus="loop">

                <c:if test="${loop.index % 3 == 0}">
                    <tr>
                    </c:if>
                    <td>
                        <div class="show-product-box">
                            <!-- show danh sách sản phẩm-->
                            <h3>${product.name}</h3>
                            <img src="<c:url value="/img/${product.image}" />">
                            </br>
                            <c:forEach var="cate" items="${listCate}">
                                <c:if test="${cate.id == product.categoryID}">
                                    <p style="font-weight: 700;" >${cate.name}</p>
                                </c:if>
                            </c:forEach>
                            <p class="price"><fmt:formatNumber value="${product.price}" type = "currency" /></p>

                            <div class="show-product-box-btn">
                                <a href="<c:url value ="/order/buynow.do?id=${product.id}"/>" class="show-product-box-btn-buy">Buy now</a>
                                <a href="<c:url value ="/order/add2cart.do?id=${product.id}"/>" class="show-product-box-btn-add">Add to cart</a>
                            </div>
                        </div>
                    </td>

                    <c:if test="${(loop.index + 1) % 3 == 0 || loop.last}">
                    </tr>
                </c:if>
            </c:forEach>
        </table>

        <div class="index">
            <c:forEach begin="1" end="${endPage}" var="i">
                <c:url var="url" value="/watch/${link}.do">     
                    <c:param name="index" value="${i}"/>
                    <c:forEach var="categoryId" items="${categoryIds}" varStatus="status">
                        <c:param name="category" value="${categoryId}" />
                    </c:forEach>   
                    <c:param name="min" value="${minPrice}"/>
                    <c:param name="max" value="${maxPrice}"/>
                </c:url>
                <a class="page-index" href="${url}">${i}</a>
            </c:forEach>
        </div>
    </c:if>
</div>




