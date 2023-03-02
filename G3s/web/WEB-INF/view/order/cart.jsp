<%-- 
    Document   : cart
    Created on : Feb 27, 2023, 5:25:35 PM
    Author     : giahu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="main">
    <div id="head">
        <img src="images/banner.jpg" width="1057px" height="200px" />
    </div>
    <div id="head-link">
        <div id='menungang'>
            <ul>
                <li class='last'><a href=""><span>Trang
                            chủ</span></a></li>
                <li class='last'><a href=""><span>Sản phẩm</span></a></li>
                <li class='last'><a href=""><span>Giỏ hàng</span></a></li>
                <li class='last'><a href=""><span>Tìm kiếm</span></a></li>
                <li class='last' style="float: right;"><a href=""><span>Đăng
                            xuất</span></a></li>
                <li class='last' style="float: right;">
                    <a href="">
                        <span><--%= username%--></span>
                    </a>
                </li>			
            </ul>
        </div>
    </div>
    <div id="">


        <div class="">

            <div class="">
                <label class="">Hình ảnh</label> <label
                    class="">Product</label> <label
                    class="">Giá bán</label> <label
                    class="">Số lượng</label><label
                    class="">Tổng tiền</label>
            </div>
            <--%
            ProductDAOImpl productDAO = new ProductDAOImpl();

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumIntegerDigits(0);
            double total = 0;
            ArrayList<Cart> cart = null;
                if (session.getAttribute("cart") != null) {
                cart = (ArrayList<Cart>) session
                    .getAttribute("cart");
                    }
                    %-->
                    <--%
                    if (cart != null) {
                    for (Cart c : cart) {
                    total = total
                    + (c.getQuantity() * productDAO.getProduct(
                    c.getP().getMa_san_pham()).getGia_ban());
                    %-->
                    <div class="product">
                        <div class="product-image">
                            <img
                                src="sanpham/<--%=productDAO.getProduct(c.getP().getMa_san_pham())
                                .getHinh_anh()%-->">
                        </div>
                        <div class="product-details">
                            <div class="product-title">
                                <--%=productDAO.getProduct(c.getP().getMa_san_pham())
                                .getTen_san_pham()%-->
                            </div>
                            <p class="product-description"></p>
                        </div>
                        <div class="product-price"><--%=nf.format(productDAO.getProduct(
                            c.getP().getMa_san_pham()).getGia_ban())%-->
                            VNĐ
                        </div>
                        <div class="product-quantity cart_quantity_button">
                            <a class="cart_quantity_up" href="GioHangServlet?command=deleteCart&ma_san_pham=<-%=c.getP().getMa_san_pham()%>"> - </a>
                            <input class="cart_quantity_input" type="number" value="<--%=c.getQuantity()%>" disabled="disabled">
                            <a class="cart_quantity_up" href="GioHangServlet?command=addCart&ma_san_pham=<-%=c.getP().getMa_san_pham()%>"> + </a>
                        </div>
                        <div class="product-line-price" style="text-align: right"><--%=nf.format(productDAO.getProduct(
                            c.getP().getMa_san_pham()).getGia_ban()
                            * c.getQuantity())%-->
                            VNĐ

                            <a
                                href="GioHangServlet?command=removeCart&ma_san_pham=<--%=c.getP().getMa_san_pham()%-->"><img style="margin-left: 10px"
                                                                                                                         src="images/delete.png"></a>
                        </div>

                    </div>
                    <%
                        }
                    }
                    %>
                    <div class="totals">
                        <div class="totals-item">
                            <label>Tổng hóa đơn</label>
                            <div class="totals-value" id="cart-subtotal"><%=nf.format(total)%>
                                VNĐ
                            </div>
                        </div>
                    </div>
                    <--%if (cart.size() > 0) {%-->
                    <a class="checkout" href="history.jsp" style="text-decoration: none;">Lịch sử</a>
                    <a class="checkout" href="ConfirmServlet?username=<%=username%>" style="text-decoration: none;">Thanh
                        toán</a>
                    <--%} else { %-->
                    <a class="checkout" href="history.jsp" style="text-decoration: none;">Lịch sử</a>
                    <a class="checkout" href="product.jsp" style="text-decoration: none;">Thanh
                        toán</a>
                    <--%}%>
                    </div>

                    </div>
                    <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
                    </div>
