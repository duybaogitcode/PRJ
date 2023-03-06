/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.CategoryFacade;
import dal.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

/**
 *
 * @author duyba
 */
@WebServlet(name = "SearchController", urlPatterns = {"/search"})
public class SearchController extends HttpServlet {

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
            throws ServletException, IOException {
        System.out.println("kodc");
        String keyword = request.getParameter("keyword");
        List<Product> products = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        CategoryFacade ctf = new CategoryFacade();
        ProductFacade pf = new ProductFacade();
        try {
            categories = ctf.select();
            products = pf.getListPrByName(keyword);
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(keyword);

        int count = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        for (Product product : products) {
            if (count % 3 == 0) {
                sb.append("<tr>");
            }
            sb.append("<td>");
            sb.append("<div class=\"show-product-box\">");
            sb.append("<h3>").append(product.getName()).append("</h3>");
            sb.append("<img src=\"").append(request.getContextPath()).append("/img/").append(product.getImage()).append("\" height=\"300\" width=\"300\" />");
            sb.append("<br />");
            for (Category category : categories) {
                if (category.getId() == product.getCategoryID()) {
                    sb.append("<p style=\"font-weight: 700;\">").append(category.getName()).append("</p>");
                    break;
                }
            }
            sb.append("<p class=\"price\">").append(product.getPrice()).append("$").append("</p>");
            sb.append("<div class=\"show-product-box-btn\">");
            sb.append("<a href=\"").append(request.getContextPath()).append("/order/buynow.do?id=").append(product.getId()).append("\" class=\"show-product-box-btn-buy\">Buy now</a>");
            sb.append("<a href=\"").append(request.getContextPath()).append("/order/add2cart.do?id=").append(product.getId()).append("\" class=\"show-product-box-btn-add\">Add to cart</a>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</td>");
            count++;
            if (count % 3 == 0 || count == products.size()) {
                sb.append("</tr>");
            }
        }
        sb.append("</table>");

        response.setContentType("text/html");
        response.getWriter().write(sb.toString());
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
        processRequest(request, response);
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
        processRequest(request, response);
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
