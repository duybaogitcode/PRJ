package org.apache.jsp.WEB_002dINF.view.cart;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class cart_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<div id=\"main\">\n");
      out.write("    <div id=\"head\">\n");
      out.write("        <img src=\"images/banner.jpg\" width=\"1057px\" height=\"200px\" />\n");
      out.write("    </div>\n");
      out.write("    <div id=\"head-link\">\n");
      out.write("        <div id='menungang'>\n");
      out.write("            <ul>\n");
      out.write("                <li class='last'><a href=\"index.jsp\"><span>Trang\n");
      out.write("                            chủ</span></a></li>\n");
      out.write("                <li class='last'><a href=\"product.jsp\"><span>Sản phẩm</span></a></li>\n");
      out.write("                <li class='last'><a href=\"cart.jsp\"><span>Giỏ hàng</span></a></li>\n");
      out.write("                <li class='last'><a href=\"search_page.jsp\"><span>Tìm kiếm</span></a></li>\n");
      out.write("                <li class='last' style=\"float: right;\"><a href=\"login.jsp\"><span>Đăng\n");
      out.write("                            xuất</span></a></li>\n");
      out.write("                <li class='last' style=\"float: right;\">\n");
      out.write("                    <a href=\"update_user.jsp?username=<-%=username%>\">\n");
      out.write("                        <span><--%= username%--></span>\n");
      out.write("                    </a>\n");
      out.write("                </li>\t\t\t\n");
      out.write("            </ul>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <div id=\"content\">\n");
      out.write("\n");
      out.write("\n");
      out.write("        <div class=\"shopping-cart\">\n");
      out.write("\n");
      out.write("            <div class=\"column-labels\">\n");
      out.write("                <label class=\"product-image\">Hình ảnh</label> <label\n");
      out.write("                    class=\"product-details\">Product</label> <label\n");
      out.write("                    class=\"product-price\">Giá bán</label> <label\n");
      out.write("                    class=\"product-quantity\">Số lượng</label><label\n");
      out.write("                    class=\"product-line-price\">Tổng tiền</label>\n");
      out.write("            </div>\n");
      out.write("            <--%\n");
      out.write("            ProductDAOImpl productDAO = new ProductDAOImpl();\n");
      out.write("\n");
      out.write("            NumberFormat nf = NumberFormat.getInstance();\n");
      out.write("            nf.setMinimumIntegerDigits(0);\n");
      out.write("            double total = 0;\n");
      out.write("            ArrayList<Cart> cart = null;\n");
      out.write("                if (session.getAttribute(\"cart\") != null) {\n");
      out.write("                cart = (ArrayList<Cart>) session\n");
      out.write("                    .getAttribute(\"cart\");\n");
      out.write("                    }\n");
      out.write("                    %-->\n");
      out.write("                    <--%\n");
      out.write("                    if (cart != null) {\n");
      out.write("                    for (Cart c : cart) {\n");
      out.write("                    total = total\n");
      out.write("                    + (c.getQuantity() * productDAO.getProduct(\n");
      out.write("                    c.getP().getMa_san_pham()).getGia_ban());\n");
      out.write("                    %-->\n");
      out.write("                    <div class=\"product\">\n");
      out.write("                        <div class=\"product-image\">\n");
      out.write("                            <img\n");
      out.write("                                src=\"sanpham/<--%=productDAO.getProduct(c.getP().getMa_san_pham())\n");
      out.write("                                .getHinh_anh()%-->\">\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"product-details\">\n");
      out.write("                            <div class=\"product-title\">\n");
      out.write("                                <--%=productDAO.getProduct(c.getP().getMa_san_pham())\n");
      out.write("                                .getTen_san_pham()%-->\n");
      out.write("                            </div>\n");
      out.write("                            <p class=\"product-description\"></p>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"product-price\"><--%=nf.format(productDAO.getProduct(\n");
      out.write("                            c.getP().getMa_san_pham()).getGia_ban())%-->\n");
      out.write("                            VNĐ\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"product-quantity cart_quantity_button\">\n");
      out.write("                            <a class=\"cart_quantity_up\" href=\"GioHangServlet?command=deleteCart&ma_san_pham=<-%=c.getP().getMa_san_pham()%>\"> - </a>\n");
      out.write("                            <input class=\"cart_quantity_input\" type=\"number\" value=\"<--%=c.getQuantity()%>\" disabled=\"disabled\">\n");
      out.write("                            <a class=\"cart_quantity_up\" href=\"GioHangServlet?command=addCart&ma_san_pham=<-%=c.getP().getMa_san_pham()%>\"> + </a>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"product-line-price\" style=\"text-align: right\"><--%=nf.format(productDAO.getProduct(\n");
      out.write("                            c.getP().getMa_san_pham()).getGia_ban()\n");
      out.write("                            * c.getQuantity())%-->\n");
      out.write("                            VNĐ\n");
      out.write("\n");
      out.write("                            <a\n");
      out.write("                                href=\"GioHangServlet?command=removeCart&ma_san_pham=<--%=c.getP().getMa_san_pham()%-->\"><img style=\"margin-left: 10px\"\n");
      out.write("                                                                                                                         src=\"images/delete.png\"></a>\n");
      out.write("                        </div>\n");
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                    ");

                        }
                    }
                    
      out.write("\n");
      out.write("                    <div class=\"totals\">\n");
      out.write("                        <div class=\"totals-item\">\n");
      out.write("                            <label>Tổng hóa đơn</label>\n");
      out.write("                            <div class=\"totals-value\" id=\"cart-subtotal\">");
      out.print(nf.format(total));
      out.write("\n");
      out.write("                                VNĐ\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <--%if (cart.size() > 0) {%-->\n");
      out.write("                    <a class=\"checkout\" href=\"history.jsp\" style=\"text-decoration: none;\">Lịch sử</a>\n");
      out.write("                    <a class=\"checkout\" href=\"ConfirmServlet?username=");
      out.print(username);
      out.write("\" style=\"text-decoration: none;\">Thanh\n");
      out.write("                        toán</a>\n");
      out.write("                    <--%} else { %-->\n");
      out.write("                    <a class=\"checkout\" href=\"history.jsp\" style=\"text-decoration: none;\">Lịch sử</a>\n");
      out.write("                    <a class=\"checkout\" href=\"product.jsp\" style=\"text-decoration: none;\">Thanh\n");
      out.write("                        toán</a>\n");
      out.write("                    <--%}%>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                    <div id=\"footer\">");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
      out.write("</div>\n");
      out.write("                    </div>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
