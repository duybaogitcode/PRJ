<%-- 
    Document   : signin
    Created on : Feb 16, 2023, 12:26:12 AM
    Author     : duyba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container col-sm-3 mt-5">
    <h1>Sign in</h1>
    <form action=<c:url value="/user/signin_handler.do" /> method="get">
        <div class="form_details">
            <div class="form-control form-control-signin">
                <input type="text" id="emailOrPhone" placeholder="Email address or phone number" />
                <span></span>
                <small></small>
            </div>
            <div class="form-control form-control-signin">
                <input type="password" id="password" placeholder="Password" />
                <i class="fa-regular fa-eye-slash" id="togglePassword1"></i>
                <span></span>
                <small></small>
            </div>
        </div>
        <!-- </div> -->
        <div class="form_footer">
            <input type="submit" value="Sign in" />
            <div class="signup_link">Already on G3S ? <a href="<c:url value ="/user/signin.do" />">Sign in</a></div>
        </div>
    </form>
</div>

<script src="https://kit.fontawesome.com/10fdb3b039.js" crossorigin="anonymous"></script>