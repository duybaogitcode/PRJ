/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
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
            ProductFacade pf = new ProductFacade();

            //ListFull
            List<Product> list = pf.select();
            int listSize = list.size();
            int endPage = (listSize % 10 == 0) ? (listSize / 10) : (listSize / 10 + 1);

            String indexPageStr = request.getParameter("indexPage");
            if (indexPageStr == null) {
                indexPageStr = "1";
            }
            int indexPage = Integer.parseInt(indexPageStr);

            //ListPaging
            List<Product> listPaging = pf.pagingRead(indexPage);

            request.setAttribute("indexPage", indexPage);
            request.setAttribute("listPaging", listPaging);
            request.setAttribute("endPage", endPage);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        } catch (SQLException ex) {

        }
    }

    protected void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Category 
        List<String> categoryIDList = new ArrayList<>();
        categoryIDList.add("AW");
        categoryIDList.add("BAT");
        categoryIDList.add("SM");
        categoryIDList.add("SP");

        //Hien edit form
        request.setAttribute("categoryIDList", categoryIDList);
        request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
    }

    protected void create_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String op = request.getParameter("op");
        switch (op) {
            case "create":
                try {
                    //Lay data tu client
                    String id = request.getParameter("id");
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    String image = request.getParameter("image");
                    String priceStr = request.getParameter("price");
                    String discountStr = request.getParameter("discount");
                    String categoryID = request.getParameter("categoryID");
                    float price = 0, discount = 0;
                    int okCheck = 0;

                    Product product = new Product();

                    boolean nameBlankCheck = true;
                    boolean descriptionBlankCheck = true;
                    boolean imgBlankCheck = true;
                    boolean priceBlankCheck = true;
                    boolean discountBlankCheck = true;

                    //Name blank check
                    if (name.equals("") || name == null) {
                        nameBlankCheck = false;
                    }
                    if (nameBlankCheck != false) {
                        product.setName(name);
                        okCheck++;
                    }

                    //Description blank check
                    if (description == null) {
                        descriptionBlankCheck = false;
                    }
                    if (descriptionBlankCheck != false) {
                        product.setDescription(description);
                        okCheck++;
                    }

                    //Img blank check
                    if (image.equals("") || image == null) {
                        imgBlankCheck = false;
                    }
                    if (imgBlankCheck != false) {
                        product.setImage(image);
                        okCheck++;
                    }

                    //Price blank check
                    if (priceStr.equals("") || priceStr == null) {
                        priceBlankCheck = false;
                    }
                    if (priceBlankCheck != false) {
                        price = Float.parseFloat(priceStr);
                        product.setPrice(price);
                        okCheck++;
                    }

                    //Discount blank check
                    if (discountStr.equals("") || discountStr == null) {
                        discountBlankCheck = false;
                    }
                    if (discountBlankCheck != false) {
                        discount = Float.parseFloat(discountStr);
                        product.setDiscount(discount);
                        okCheck++;
                    }

                    product.setCategoryID(categoryID);

                    //Category 
                    List<String> categoryIDList = new ArrayList<>();
                    categoryIDList.add("AW");
                    categoryIDList.add("BAT");
                    categoryIDList.add("SM");
                    categoryIDList.add("SP");

                    //Representation
                    request.setAttribute("categoryIDList", categoryIDList);
                    request.setAttribute("product", product);

                    if (okCheck == 5) {
                        ProductFacade pf = new ProductFacade();
                        pf.create(product);

                    } else {
                        request.setAttribute("message", "Your data is not enpty. Please try again.");
                        request.setAttribute("action", "create");
                        request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
                    }

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
            throws ServletException, IOException, SQLException {
        String searchInput = request.getParameter("searchInput");
        ProductFacade pf = new ProductFacade();
        List<Product> list = new ArrayList<>();
        list = pf.searchFullType(searchInput);
        PrintWriter out = response.getWriter();
        out.print("<table class=\"table table-striped\"> \n"
                + "                <thead>\n"
                + "                    <tr>\n"
                + "                        <th>Id</th>\n"
                + "                        <th>Name</th>\n"
                + "                        <th>Description</th>\n"
                + "                        <th>Image</th>\n"
                + "                        <th>Price</th>\n"
                + "                        <th>Discount</th>\n"
                + "                        <th>Category Id</th>\n"
                + "                        <th>Operations</th>\n"
                + "                    </tr>\n"
                + "                </thead>\n"
                + "                <tbody>");
        for (Product product : list) {
            String editURL = "/g3s/admin_product/edit.do?id=" + product.getId();
            String deleteURL = "/g3s/admin_product/delete.do?id=" + product.getId() + "&name=" + product.getName();
            String imgURL = "";
            out.print("<tr>\n"
                    + "                            <td>" + product.getId() + "</td>\n"
                    + "                            <td>" + product.getName() + "</td>\n"
                    + "                            <td>" + product.getDescription() + "</td>\n"
                    //                    + "                            <td><img src=\"" + imgURL + "\"/>\" width=\"90px\" height=\"90px\"></td>\n"
                    + "                            <td>" + product.getImage() + "</td>\n"
                    + "                            <td>\n"
                    //                    + "                                <fmt:formatNumber value=\"" + product.getPrice() + "\" type=\"currency\"/>\n"
                    + "                                " + product.getPrice() + "\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    //                    + "                                <fmt:formatNumber value=\"" + product.getDiscount() + "\" type=\"currency\"/>\n"
                    + "                                " + product.getDiscount() + "\n"
                    + "                            </td>\n"
                    + "                            <td>" + product.getCategoryID() + "</td>\n"
                    + "                            <td>\n"
                    + "                                <a href=\"" + editURL + "\"><i class=\"fa-solid fa-pen-to-square\"></i></a> |\n"
                    + "                                <a href=\"" + deleteURL + "\"/>\"><i class=\"fa-solid fa-trash-can\"></i></a>\n"
                    + "                            </td>\n"
                    + "                        </tr>\n");
        }
        out.print("</tbody>\n"
                + "        </table>");
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
                String id = request.getParameter("id");
                ProductFacade pf = new ProductFacade();
                try {
                    pf.delete_1(id);
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
            Object idStr = request.getParameter("id");
            Integer id = Integer.parseInt((String) idStr);

            //Lay thong tin account tu id cua client
            ProductFacade pf = new ProductFacade();
            Product product = pf.read_1(id);

            //Category 
            List<String> categoryIDList = new ArrayList<>();
            categoryIDList.add("AW");
            categoryIDList.add("BAT");
            categoryIDList.add("SM");
            categoryIDList.add("SP");

            //Hien edit form
            request.setAttribute("categoryIDList", categoryIDList);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        } catch (SQLException ex) {
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
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    String img = request.getParameter("img");
                    String priceStr = request.getParameter("price");
                    float price;
                    String discountStr = request.getParameter("discount");
                    float discount;
                    String categoryID = request.getParameter("categoryID");

                    ProductFacade pf = new ProductFacade();

                    //Check blank input and create the update account
                    Product productOld = pf.read(idStr);
                    Product productUp = new Product();

                    productUp.setId(id);

                    boolean nameBlankCheck = true;
                    boolean descriptionBlankCheck = true;
                    boolean imgBlankCheck = true;
                    boolean priceBlankCheck = true;
                    boolean discountBlankCheck = true;

                    //Name blank check
                    if (name.equals("") || name == null) {
                        nameBlankCheck = false;
                    }
                    if (nameBlankCheck != false) {
                        productUp.setName(name);
                    } else {
                        productUp.setName(productOld.getName());
                    }
                    //Description blank check
                    if (description == null) {
                        descriptionBlankCheck = false;
                    }
                    if (descriptionBlankCheck != false) {
                        productUp.setDescription(description);
                    } else {
                        productUp.setDescription(productOld.getDescription());
                    }
                    //Img blank check
                    if (img.equals("") || img == null) {
                        imgBlankCheck = false;
                    }
                    if (imgBlankCheck != false) {
                        productUp.setImage(img);
                    } else {
                        productUp.setImage(productOld.getImage());
                    }

                    //Price blank check
                    if (priceStr.equals("") || priceStr == null) {
                        priceBlankCheck = false;
                    }
                    if (priceBlankCheck != false) {
                        price = Float.parseFloat(priceStr);
                        productUp.setPrice(price);
                    } else {
                        productUp.setPrice(productOld.getPrice());
                    }

                    //Discount blank check
                    if (discountStr.equals("") || discountStr == null) {
                        discountBlankCheck = false;
                    }
                    if (discountBlankCheck != false) {
                        discount = Float.parseFloat(discountStr);
                        productUp.setDiscount(discount);
                    } else {
                        productUp.setDiscount(productOld.getDiscount());
                    }
                    productUp.setCategoryID(categoryID);

                    //Category 
                    List<String> categoryIDList = new ArrayList<>();
                    categoryIDList.add("AW");
                    categoryIDList.add("BAT");
                    categoryIDList.add("SM");
                    categoryIDList.add("SP");

                    //Representation
                    request.setAttribute("categoryIDList", categoryIDList);
                    request.setAttribute("product", productUp);
                    //Cap nhat toy vao database
                    pf.update_1(productUp);

                    //Hien danh sach toy
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
