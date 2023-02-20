<%-- 
    Document   : index
    Created on : Feb 6, 2023, 10:10:40 AM
    Author     : duyba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="content" style="background-image: url('<c:url value="/img/background.jpg" />');">   
    <div class="para-content">
        <h1>The right time for life
            </br>Luxury Watches
        </h1>
        <p>Find your dream watch on the leading marketplace for luxury watches.</p>
        <button id="btn-content">Buy Now</button>
    </div>
</div>

    <div class="content2" id="trending">   
    <h3 class="text-center" style="padding-top: 30px;">TOP TRENDING</h3>
    <div class="row" style="margin-top: 50px;">

  <div class="para-content2 col-md-4 py-3 py-md-0">
            <div class="card">
                <img class="image" src="<c:url value="/img/card1.jpg" />" height="400px" style="object-fit: cover;">
                <div class="card-body">
                    <h5 class="card-title text-center">Xiaomi Watch S1 Active</h5>
                    <p class="text-center">Xiaomi Watch S1 Active - raise the next generation of smart watches.
                        To begin its new technological era, Xiaomi has launched the 
                        Xiaomi Watch S1 Active smartwatch. 
                        This product is a new design that hits the smartwatch market 
                        of Xiaomi and opens a new wave for mid- and high-end smart watches.</p>
                    <div class="text-center"><button id="btn-content">View More</button></div>
                </div>
            </div>
        </div>

        <!--        card2        -->
        <div class="col-md-4 py-3 py-md-0">
            <div class="card">
                <img class="image"  src="<c:url value="/img/card2.jpg" />" height="400px" style="object-fit: cover;">
                <div class="card-body">
                    <h5 class="card-title text-center">Apple Watch SE 2022</h5>
                    <p class="text-center">Continuing to be a smart watch product in the mid-range segment, 
                        the Apple Watch SE 2022 is the successor to the previously launched Apple Watch SE. 
                        Apple Watch SE 2022 smart watch is equipped with 
                        Apple S8 chip and impressive features such as intelligent fall and accident detection...</p>
                    <div class="text-center"><button id="btn-content">View More</button></div>
                </div>
            </div>
        </div>

        <!--     card3   -->
        <div class="col-md-4 py-3 py-md-0">
            <div class="card">
                <img class="image" src="<c:url value="/img/card3.jpg" />" height="400px" style="object-fit: cover;">
                <div class="card-body">
                    <h5 class="card-title text-center">Amazfit GTS 4 Mini</h5>
                    <p class="text-center">The Amazfit GTS 4 Mini smartwatch is an improved version of the GTS 2 Mini watch 
                        generation. Since its official launch, this Amazfit smartwatch product possesses many special 
                        and outstanding features with many impressive color versions.</p>
                    <div class="text-center"><button id="btn-content">View More</button></div>
                </div>
            </div>
        </div>
    </div>
</div>
