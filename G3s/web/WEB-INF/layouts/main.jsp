<%-- 
    Document   : main
    Created on : Feb 14, 2023, 1:25:15 AM
    Author     : duyba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en-US" scope="session" />
<link href="<c:url value="/css/site.css"/>" rel="stylesheet" type="text/css"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Latest compiled JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <script type="module" src="https://unpkg.com/ionicons@4.5.10-0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule="" src="https://unpkg.com/ionicons@4.5.10-0/dist/ionicons/ionicons.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="header">

                <div class="header-left">

                    <i><ion-icon name="contact"></ion-icon></i>
                            <c:choose>
                                <c:when test="${sessionScope.account==null}">
                            <a href="<c:url value ="/user/joinnow.do" />"class="btn-header-left">Join Now</a>
                            <span>/</span>
                            <a href="<c:url value ="/user/signin.do" />" class="btn-header-left">Sign in</a> 
                        </c:when>
                        <c:otherwise>
                            ${sessionScope.account.name} |
                            <a href="<c:url value ="/user/logout.do" />" class="btn-header-left">Log out</a> 
                        </c:otherwise>
                    </c:choose>

                </div>

                <div class="logo">

                    <svg width="120" height="100" viewBox="0 0 402 194" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M204 62C204 27.7584 231.758 0 266 0H340C374.242 0 402 27.7583 402 62V132C402 166.242 374.242 194 340 194H266C231.758 194 204 166.242 204 132V62Z" fill="#0FA9EB"/>
                    <g clip-path="url(#clip0_0_1)">
                    <path d="M236.083 79.0815H253.496C250.736 63.6648 269.317 59.2177 276.218 66.8272C283.438 75.2273 276.43 92.0571 260.291 88.4698V100.428C278.236 96.6722 288.853 115.152 277.174 124.837C271.992 130.925 252.541 130.371 253.39 114.263L235.021 114.164C234.066 140.55 264.539 151.717 288.216 138.277C304.355 129.186 307.116 100.625 285.349 92.5216C307.222 81.6509 299.365 49.0388 270.166 49.0388C245.873 48.1395 234.278 61.4907 236.083 79.0815Z" fill="white"/>
                    </g>
                    <g clip-path="url(#clip1_0_1)">
                    <path d="M349.4 52L364.5 113.9C366.5 112.8 362.3 136.2 345.3 141.4L330.3 80C332.2 64.6 339.6 56.7 349.4 52Z" fill="#FFFEFE"/>
                    <path d="M307 124.3H336.1L341 141.4H311.2L307 124.3Z" fill="#FFFEFE"/>
                    <path d="M354 52H383.1L388 69.1H358.2L354 52Z" fill="#FFFEFE"/>
                    </g>
                    <g clip-path="url(#clip2_0_1)">
                    <path d="M108.402 91.5367V145.338H122.283C122.083 164.044 88.6289 167.748 87.081 145.338V49.9124C91.475 29.6327 120.985 31.5773 122.283 49.9124V67.5529H182.95V18.0112C72.7007 -30.3731 0 26.1138 0 100.519C5.14297 183.953 95.4696 218.354 183 175.48V92.0923L108.402 91.5367Z" fill="#0FA9EB"/>
                    </g>
                    <defs>
                    <clipPath id="clip0_0_1">
                        <rect width="66" height="95" fill="white" transform="translate(235 49)"/>
                    </clipPath>
                    <clipPath id="clip1_0_1">
                        <rect width="81" height="89.4" fill="white" transform="translate(307 52)"/>
                    </clipPath>
                    <clipPath id="clip2_0_1">
                        <rect width="183" height="194" fill="white"/>
                    </clipPath>
                    </defs>
                    </svg>

                </div>

                <div class="icons">
                    <i style="font-size: 30px;"><ion-icon name="search"></ion-icon></i>
                    <input type="text" id="searchInput" />
                    <a href="<c:url value="/order/cart.do"/>"<i style="font-size: 30px;"><ion-icon name="cart"></ion-icon></i></a>(${sessionScope.cart.totalQuantity})


                </div>

            </div>


            <script>
               var searchInput = document.getElementById("searchInput");
                var searchResults = document.getElementById("searchResults");


                searchInput.addEventListener("input", function () {
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            var table = document.getElementById("show-product-table");
                            table.innerHTML = xhr.responseText;
                        }
                    };
                    xhr.open("GET", "/g3s/watch/searchajax.do?keyword=" + searchInput.value);
                    xhr.send();
                });

            </script>


            <div class="row navigation">
                <nav class="navbar navbar-expand-sm ">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value ="/home/index.do" />"><ion-icon name="home"></ion-icon> Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value ="/watch/filter.do" />"><ion-icon name="watch"></ion-icon> Watches</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/g3s/#trending"><ion-icon name="trending-up"></ion-icon> Trending</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/g3s/#sale"><ion-icon name="cash"></ion-icon></ion-icon> Sale</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/g3s/#about"><ion-icon name="person"></ion-icon> About Us</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"><ion-icon name="call"></ion-icon> Contact</a>
                        </li>
                    </ul>
                </nav>
            </div>        



            <div class= "row main-content">
                <jsp:include page = "/WEB-INF/view/${controller}/${action}.jsp" />
            </div>
            <div class="row footer">
                <div class="col-sm-2">
                    <svg width="30" height="50" viewBox="0 0 402 194" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M204 62C204 27.7584 231.758 0 266 0H340C374.242 0 402 27.7583 402 62V132C402 166.242 374.242 194 340 194H266C231.758 194 204 166.242 204 132V62Z" fill="#0FA9EB"/>
                    <g clip-path="url(#clip0_0_1)">
                    <path d="M236.083 79.0815H253.496C250.736 63.6648 269.317 59.2177 276.218 66.8272C283.438 75.2273 276.43 92.0571 260.291 88.4698V100.428C278.236 96.6722 288.853 115.152 277.174 124.837C271.992 130.925 252.541 130.371 253.39 114.263L235.021 114.164C234.066 140.55 264.539 151.717 288.216 138.277C304.355 129.186 307.116 100.625 285.349 92.5216C307.222 81.6509 299.365 49.0388 270.166 49.0388C245.873 48.1395 234.278 61.4907 236.083 79.0815Z" fill="white"/>
                    </g>
                    <g clip-path="url(#clip1_0_1)">
                    <path d="M349.4 52L364.5 113.9C366.5 112.8 362.3 136.2 345.3 141.4L330.3 80C332.2 64.6 339.6 56.7 349.4 52Z" fill="#FFFEFE"/>
                    <path d="M307 124.3H336.1L341 141.4H311.2L307 124.3Z" fill="#FFFEFE"/>
                    <path d="M354 52H383.1L388 69.1H358.2L354 52Z" fill="#FFFEFE"/>
                    </g>
                    <g clip-path="url(#clip2_0_1)">
                    <path d="M108.402 91.5367V145.338H122.283C122.083 164.044 88.6289 167.748 87.081 145.338V49.9124C91.475 29.6327 120.985 31.5773 122.283 49.9124V67.5529H182.95V18.0112C72.7007 -30.3731 0 26.1138 0 100.519C5.14297 183.953 95.4696 218.354 183 175.48V92.0923L108.402 91.5367Z" fill="#0FA9EB"/>
                    </g>
                    <defs>
                    <clipPath id="clip0_0_1">
                        <rect width="66" height="95" fill="white" transform="translate(235 49)"/>
                    </clipPath>
                    <clipPath id="clip1_0_1">
                        <rect width="81" height="89.4" fill="white" transform="translate(307 52)"/>
                    </clipPath>
                    <clipPath id="clip2_0_1">
                        <rect width="183" height="194" fill="white"/>
                    </clipPath>
                    </defs>
                    </svg>
                    <a href="#">© 2023</a>
                </div>
                <div class="col-sm-4">
                    <a href="#">Address: E2a-7, D1, Long Thanh My, Thu Duc City, Ho Chi Minh City</a>
                </div>
                <div class="col-sm-3">
                    <a href="#">Email: g3s@gmail.com</a>
                </div>
                <div class="col-sm-3">
                    <a href="#">Hotline: 0987654321</a> 
                </div>

            </div>
        </div>

    </body>
</body>
</html>
