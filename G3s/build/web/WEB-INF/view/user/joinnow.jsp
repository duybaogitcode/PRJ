<%-- 
    Document   : joinnow
    Created on : Feb 25, 2023, 4:04:14 AM
    Author     : duyba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container col-sm-6 mt-5">
    <h1>Sign in</h1>
    <form>
        <div class="form_details">
            <div class="form-control">
                <i class="fa-regular fa-user"></i>
                <input type="text" id="username" placeholder="Username" />
                <span></span>
                <small></small>
            </div>
            <div class="form-control">
                <i class="fa-regular fa-envelope"></i>
                <input type="email" id="email" placeholder="Email" />
                <span></span>
                <small></small>
            </div>
            <div class="form-control">
                <i class="fa-solid fa-phone"></i>
                <input type="tel" id="phone" placeholder="Phone" pattern="(((\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\b"/>
                <span></span>
                <small></small>
            </div>
            <div class="form-control">
                <i class="fa-regular fa-address-card"></i>
                <input type="text" id="address" placeholder="Address" />
                <span></span>
                <small></small>
            </div>
            <div class="form-control">
                <i class="fa-solid fa-lock"></i>
                <input type="password" id="password" placeholder="Password" />
                <i class="fa-regular fa-eye-slash" id="togglePassword1"></i>
                <span></span>
                <small></small>
            </div>
            <div class="form-control">
                <i class="fa-solid fa-lock"></i>
                <input type="password" id="confirmPassword" placeholder="Confirm password" />
                <i class="fa-regular fa-eye-slash" id="togglePassword2"></i>
                <span></span>
                <small></small>
            </div>
        </div>
        <!-- </div> -->
        <div class="form_footer">
            <input type="submit" value="Register" />
            <div class="signup_link">Already on G3S ? <a href="<c:url value ="/user/signin.do" />">Sign in</a></div>
        </div>
    </form>
</div>

<script src="<c:url value="/js/main.js"/>"></script>
<script src="https://kit.fontawesome.com/10fdb3b039.js" crossorigin="anonymous"></script>
