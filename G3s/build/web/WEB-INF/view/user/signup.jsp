<%-- 
    Document   : joinnow
    Created on : Feb 25, 2023, 4:04:14 AM
    Author     : duyba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="padding: 50px 0;">
    <div class="container col-sm-6 mt-5">
    <h1>Sign up</h1>
    <form action="<c:url value="/user/signup_handler.do" />">
        <div class="form_details">
            <div class="form-control">
                <i class="fa-regular fa-user"></i>
                <input name="username" type="text" id="username" placeholder="Username" value="${acc.name}" />
                <span></span>
                <small><i style="color:red;">${messageUser}</i></small>
            </div>
            <div class="form-control">
                <i class="fa-regular fa-envelope"></i>
                <input name="email"  type="email" id="email" placeholder="Email" value="${acc.email}"/>
                <span></span>
                <small><i style="color:red;">${messageEmail}</i></small>
            </div>
            <div class="form-control">
                <i class="fa-solid fa-phone"></i>
                <input name="tel"  type="tel" id="phone" placeholder="Phone" pattern="(((\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\b" value="${acc.phone}"/>
                <span></span>
                <small><i style="color:red;">${messagePhone}</i></small>
            </div>
            <div class="form-control">
                <i class="fa-regular fa-address-card"></i>
                <input name="address"  type="text" id="address" placeholder="Address" value="${acc.name}"/>
                <span></span>
                <small><i style="color:red;">${messageAddress}</i></small>
            </div>
            <div class="form-control">
                <i class="fa-solid fa-lock"></i>
                <input name="password" type="password" id="password" placeholder="Password" value="${acc.password}"/>
                <i class="fa-regular fa-eye-slash" id="togglePassword1"></i>
                <span></span>
                <small><i style="color:red;">${messagePass}</i></small>
            </div>
            <div class="form-control">
                <i class="fa-solid fa-lock"></i>
                <input name="passConfirm"  type="password" id="confirmPassword" placeholder="Confirm password" />
                <i class="fa-regular fa-eye-slash" id="togglePassword2"></i>
                <span></span>
                <small><i style="color:red;">${messagePassComfirm}</i></small>
            </div>
        </div>
        <i style="color:red;">${messageBlank}</i>
        <!-- </div> -->
        <div class="form_footer">
            <h5 style="color:green;">${messageDone}</h5>
            <input name="register" type="submit" value="Sign up" />
            <div class="signup_link">Already on G3S ? <a href="<c:url value ="/user/signin.do" />">Sign in</a></div>
        </div>
    </form>
</div>
</div>

<script src="<c:url value="/js/showPass.js"/>"></script>
<script src="https://kit.fontawesome.com/10fdb3b039.js" crossorigin="anonymous"></script>
