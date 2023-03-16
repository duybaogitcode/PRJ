<%-- 
    Document   : ccc
    Created on : Mar 3, 2023, 8:28:44 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en-US" scope="session" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="<c:url value="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" />" rel="stylesheet" />
        <link href="<c:url value="/css/styles.css" />" rel="stylesheet" />
        <script src="<c:url value="https://use.fontawesome.com/releases/v6.1.0/js/all.js" />" crossorigin="anonymous"></script>

        <!-- Latest compiled and minified CSS -->
        <link href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" />" rel="stylesheet">

        <!-- Latest compiled JavaScript -->
        <script src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" />"></script>
        <link rel="stylesheet" href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css" />">

    </head>   
    <c:if test="${sessionScope.account==null}">
        <jsp:forward page="/user/signin.do" />
    </c:if>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3" href="#">G3S Adminator</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <div class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0"></div>
            <!-- Navbar-->
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i><span>${sessionScope.account.name}</span></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="<c:url value="/user/logout.do"/>">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <a class="nav-link" href="<c:url value="/admin_dashboard/index.do" />">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-gauge"></i> </div>
                                Dashboard
                            </a>
                            <a class="nav-link" href="<c:url value="/admin_account/index.do" />">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-file-invoice"></i> </div>
                                Accounts
                            </a>
                            <a class="nav-link" href="<c:url value="/admin_product/index.do" />">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-store"></i> </div>
                                Products
                            </a>
                            <a class="nav-link" href="<c:url value="/admin_order/index.do" />">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-cash-register"></i> </div>
                                Orders
                            </a>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        ${sessionScope.account.name}
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">

                <jsp:include page = "/WEB-INF/view/${controller}/${action}.jsp" />

                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; G3s</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>

        <script src="<c:url value="/js/scriptsAdmin.js" />"></script>
        <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js" />"></script>
    </body>
</html>