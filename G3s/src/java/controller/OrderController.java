/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.orderdetailFacade;
import dal.productFacade;
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
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Item;
import model.orderdetail;
import model.Product;

/**
 *
 * @author giahu
 */
@WebServlet(name = "OrderController", urlPatterns = {"/order"})
public class OrderController extends HttpServlet {

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

        String controller = (String) request.getAttribute("controller");
        String action = (String) request.getAttribute("action");
        HttpSession session = request.getSession();

        switch (action) {
            case "buynow":
                //Processing code here
                //Foward request & respone to view

                try {
                    buynow(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "cart":
                //Processing code here
                //Foward request & respone to view
                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;
            case "pay":
                //Processing code here
                //Foward request & respone to view

                orderdetailFacade odf = new orderdetailFacade();
                productFacade prf = new productFacade();

                try {
                    orderdetail ord = odf.read(request.getParameter("id"));
                    System.out.println(ord);
                    Product pdt = prf.read(String.valueOf(ord.getProductId()));
                    System.out.println(pdt);
                    session.setAttribute("orderdetail", pdt);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }

                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;

            default:
                //Show error page
                request.setAttribute("Message", "Invalid");
                //set view name
                request.setAttribute("action", "error");
                request.setAttribute("controller", "error");
                //Foward request to layout, de trong web info thi client ko truy cap dc
                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        }
    }

    protected void buynow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String op = request.getParameter("op");
        HttpSession session = request.getSession();
        Item item = null;
        
        if (op != null) {
            item = (Item) session.getAttribute("item");
            int oldQuantity = item.getQuantity();
            switch (op) {
                case "minus":
                    if (oldQuantity > 1) {
                        item.setQuantity(oldQuantity - 1);
                    }
                    break;
                case "add":
                    item.setQuantity(oldQuantity + 1);
                    break;
            }
        } else {
            //op = null khi request lan dau
            String id = request.getParameter("id");
            productFacade pf = new productFacade();
            Product product = pf.read(id);
            item = new Item(product, 1);
        }
        
        session.setAttribute("item", item);
        request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
    }

    protected void pay(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        productFacade pf = new productFacade();

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            //Neu trong session chua co cart thi tao moi
            cart = new Cart();
            session.setAttribute("cart", cart);
//            cart.add(item);
        }

        request.getRequestDispatcher("/buynow.jsp").forward(request, response);
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
