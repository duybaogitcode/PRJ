/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;

/**
 *
 * @author duyba
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String txtSearch = request.getParameter("txt");
        ProductFacade pf = new ProductFacade();
        String name = request.getParameter("txt");
        System.out.println("search test");
        try {
            List<Product> list = pf.getListPrByName(name);
            PrintWriter out = response.getWriter();
            out.println("<table>");
            for (int i = 0; i < list.size(); i++) {
                Product pr = list.get(i);
                out.println("<td>");
                out.println("<div class=\"show-product-box\">");
                out.println("<h3>" + pr.getName() + "</h3>");
                out.println("<img src=\"/img/" + pr.getImage() + "\">");
                out.println("<p style=\"font-weight: 700;\">" + pr.getCategoryID() + "</p>");
                out.println("<p class=\"price\">" + pr.getPrice() + "</p>");
                out.println("<div class=\"show-product-box-btn\">");
                out.println("<a href=\"#\" class=\"show-product-box-btn-buy\">Buy now</a>");
                out.println("<a href=\"#\" class=\"show-product-box-btn-add\" >Add to cart</a>");
                out.println("</div>");
                out.println("</div>");
                out.println("</td>");
                if ((i + 1) % 3 == 0 || i == list.size() - 1) {
                    out.println("</tr>");
                }
            }
            out.println("</table>");

        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
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
