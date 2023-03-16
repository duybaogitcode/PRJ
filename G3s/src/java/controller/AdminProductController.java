/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import dal.CategoryFacade;
import dal.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Category;
import model.Product;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminProductController", urlPatterns = {"/admin_product"})
public class AdminProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String controller = (String) request.getAttribute("controller");
        String action = (String) request.getAttribute("action");
        getListCate(request, response);
        System.out.println("day là action" + action);
        switch (action) {
            case "index":
                index(request, response);
                break;
            case "create":
                create(request, response);
                break;
            case "create_handler":
                create_handler(request, response);
                break;
            case "search":
                search(request, response);
                break;
            case "searchajax":
                searchajax(request, response);
                break;
            case "delete":  //Show comfirmation form
                delete(request, response);
                break;
            case "delete_handler": //Process deletion comfirmation form
                delete_handler(request, response);
                break;
            case "edit": //Show edit form
                edit(request, response);
                break;
            case "edit_handler": //Process the edit form
                edit_handler(request, response);
                break;

        }
    }

    protected void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            String indexS = request.getParameter("indexPage");
            if (indexS == null) {
                indexS = "1";
            }
            int index = Integer.parseInt(indexS);
            ProductFacade pf = new ProductFacade();
            int total = pf.getTotalProduct();
            int endPage = total / 10;
            if (total % 10 != 0) {
                endPage++;
            }
            List<Product> listPaging = pf.pagingAdmin(index);
            request.setAttribute("endPage", endPage);
            request.setAttribute("listPaging", listPaging);
            request.setAttribute("indexPage", index);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("action", "create");
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        }
    }

    protected void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
    }

    protected void create_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String op = request.getParameter("op");
        switch (op) {
            case "create":
                try {
                    //Lay data tu client
                    String ids = request.getParameter("id");
                    int id = Integer.parseInt(ids);
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    String image = request.getParameter("image");
                    String priceStr = request.getParameter("price");
                    String discountStr = request.getParameter("discount");
                    String categoryID = request.getParameter("categoryID");
                    float price = Float.parseFloat(priceStr);
                    float discount = Float.parseFloat(discountStr);
                    ProductFacade pf = new ProductFacade();
                    Product productCr = new Product(id, name, image, description, price, discount, categoryID);
                    System.out.println(productCr);
                    pf.create(productCr);
                    response.sendRedirect(request.getContextPath() + "/admin_product/index.do");
                } catch (Exception e) {
                    request.setAttribute("message", "Can not save this product. Please check the product data.");
                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
                }
                break;
            case "cancel":
                response.sendRedirect(request.getContextPath() + "/admin_product/index.do");
                break;
        }
    }

    protected void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        int index = Integer.parseInt(request.getParameter("indexPage"));
        ProductFacade pf = new ProductFacade();
        List<Product> list = new ArrayList<>();
        try {
            list = pf.searchFullType(keyword, index);
            int total = pf.totalSearchFullType(keyword);
            int endPage = total / 10;
            if (total % 10 != 0) {
                endPage++;
            }
            request.setAttribute("keyword", keyword);
            request.setAttribute("listPaging", list);
            request.setAttribute("endPage", endPage);
            request.setAttribute("indexPage", index);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        }

    }

    protected void searchajax(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String keyword = request.getParameter("keyword");
        ProductFacade pf = new ProductFacade();
        List<Product> list = new ArrayList<>();
        CategoryFacade cf = new CategoryFacade();
        List<Category> listCate = new ArrayList<>();
        int endPage = 1;

        try {
            list = pf.searchFullType(keyword, 1);
            int total = pf.totalSearchFullType(keyword);
            System.out.println(total);
            endPage = total / 10;
            System.out.println(endPage);
            if (total % 10 != 0) {
                endPage++;
            }
            listCate = cf.select();
        } catch (SQLException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        }

        System.out.println("keyword la : " + keyword);

        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"content_tableProduct\">");
        sb.append("<table class=\"table table-striped\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Id</th>");
        sb.append("<th>Name</th>");
        sb.append("<th>Description</th>");
        sb.append("<th>Image</th>");
        sb.append("<th>Price</th>");
        sb.append("<th>Discount</th>");
        sb.append("<th>Category</th>");
        sb.append("<th>Operations</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");

        for (Product product : list) {
            sb.append("<tr>");
            sb.append("<td>").append(product.getId()).append("</td>");
            sb.append("<td>").append(product.getName()).append("</td>");
            sb.append("<td>").append(product.getDescription()).append("</td>");
            sb.append("<td><img src=\"/g3s/img/").append(product.getImage()).append("\" width=\"90px\" height=\"90px\" style=\"object-fit: cover;\"></td>");
            sb.append("<td>").append(product.getPrice()).append("</td>");
            sb.append("<td>").append(product.getDiscount()).append("</td>");
            sb.append("<td>");
            for (Category category : listCate) {
                if (category.getId() == product.getCategoryID()) {
                    sb.append("<p>").append(category.getName()).append("</p>");
                    break;
                }
            }
            sb.append("</td>");
            sb.append("<td>");
            sb.append("<a href=\"/g3s/admin_product/edit.do?id=").append(product.getId()).append("\"><i class=\"fa-solid fa-pen-to-square\"></i></a> |");
            sb.append("<a href=\"/g3s/admin_product/delete.do?id=").append(product.getId()).append("&name=").append(product.getName()).append("\"><i class=\"fa-solid fa-trash-can\"></i></a>");
            sb.append("</td>");
            sb.append("</tr>");
        }

        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("<div class=\"datatable-bottom\">");
        sb.append("<nav class=\"datatable-pagination\">");
        sb.append("<ul class=\"datatable-pagination-list\">");

        for (int i = 1; i <= endPage; i++) {
            sb.append("<li class=\"datatable-pagination-list-item datatable-active\">");
            sb.append("<a href=\"").append(request.getContextPath()).append("/admin_product/search.do?indexPage=").append(i).append("&keyword=").append(keyword).append("\" class=\"btn btn-secondary datatable-pagination-list-item-link\">").append(i).append("</a>");

            sb.append("</li>");
        }

        sb.append("</ul>");
        sb.append("</nav>");
        sb.append("</div>");
        sb.append("</div>");

        response.setContentType("text/html");
        response.getWriter().write(sb.toString());
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        request.setAttribute("id", id);
        request.setAttribute("name", name);
        request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
    }

    protected void delete_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String op = request.getParameter("op");
        switch (op) {
            case "yes":
                String idS = request.getParameter("id");
                int id = Integer.parseInt(idS);
                ProductFacade pf = new ProductFacade();
                try {
                    pf.delete(id);
                    response.sendRedirect(request.getContextPath() + "/admin_product/index.do");
                } catch (SQLException ex) {
                }
                break;
            case "no":
                response.sendRedirect(request.getContextPath() + "/admin_product/index.do");
                break;
        }
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Lay id tu client
            String idS = request.getParameter("id");
            int id = Integer.parseInt(idS);
            ProductFacade pf = new ProductFacade();
            Product product = pf.read(id);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
        }
    }

    protected void edit_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String op = request.getParameter("op");
        switch (op) {
            case "update":
                try {
                    String idStr = request.getParameter("id");
                    int id = Integer.parseInt(idStr);
                    ProductFacade pf = new ProductFacade();
                    Product productOld = pf.read(id);
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    String imageHidden = request.getParameter("imgHidden");
                    System.out.println("day la hidden"+ imageHidden);
                    String img = request.getParameter("img");
                    if(img==null || img.equals("")){
                        img = imageHidden;
                    }
                    System.out.println(img);
                    String priceStr = request.getParameter("price");
                    float price = Float.parseFloat(priceStr);
                    String discountStr = request.getParameter("discount");
                    float discount = Float.parseFloat(discountStr);
                    String categoryID = request.getParameter("categoryID");
                    Product productUpdate = new Product(id, name, img, description, price, discount, categoryID);
                    pf.update(productUpdate);
                    //Check blank input and create the update account
                    response.sendRedirect(request.getContextPath() + "/admin_product/index.do");
                } catch (SQLException ex) {
                    request.setAttribute("message", "Can not save this product. Please check the product data.");
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
                }
                break;
            case "cancel":
                response.sendRedirect(request.getContextPath() + "/admin_product/index.do");
                break;
        }

    }

    protected void getListCate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryFacade cf = new CategoryFacade();
        try {
            List<Category> listCate = cf.select();
            request.setAttribute("listCate", listCate);
        } catch (SQLException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
