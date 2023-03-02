/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.orderdetailFacade;
import dal.orderheaderFacade;
import dal.productFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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
import model.orderheader;

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
                //check login here
                
                //in bill + luu don hang vao db here
                try {
                    pay(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
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

        orderdetailFacade odf = new orderdetailFacade();
        orderheaderFacade ohf = new orderheaderFacade();

        //Lay cart tu session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            //Neu trong session chua co cart -> buynow
            cart = new Cart();
            session.setAttribute("cart", cart);
            Item item = (Item) session.getAttribute("item");
            
            cart.add(item);
        }

        //Luu thong tin don hang(uncomplete)
        for (int key : cart.getMap().keySet()) {
            Item item = cart.getMap().get(key);
            orderheader oh = new orderheader(1, new Date(), "ongoing", 2);
            orderdetail od = new orderdetail(1,
                    oh.getId(),
                    item.getProduct().getId(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    item.getProduct().getDiscount());
//            ohf.create(oh);
//            odf.create(od);
        }
        request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
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
