/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.OrderDetailFacade;
import dal.OrderHeaderFacade;
import dal.ProductFacade;
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
import model.Account;
import model.Cart;
import model.Item;
import model.OrderDetail;
import model.Product;
import model.OrderHeader;

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

        switch (action) {
            case "buynow":
                //Processing code here
                try {
                    buynow(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "add2cart":
                //Processing code here
                //Foward request & respone to view
                try {
                    add2cart(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "cart":
                //Processing code here
                //Foward request & respone to view
                try {
                    cart(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "pay":
                //Processing code here
                try {
                    //check login here
                    if (!check_login(request, response)) {
                        response.sendRedirect(request.getContextPath() + "/user/signin.do");
                        break;
                    }
                    //in bill + luu don hang vao db here
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
                    } else {
                        response.sendRedirect(request.getContextPath() + "/watch/filter.do");
                        return;
                    }
                    break;
                case "add":
                    item.setQuantity(oldQuantity + 1);
                    break;
            }
        } else {
            //op = null khi request lan dau
            String idS = request.getParameter("id");
            int id = Integer.parseInt(idS);
            ProductFacade pf = new ProductFacade();
            Product product = pf.read(id);
            item = new Item(product, 1);
        }

        session.setAttribute("item", item);
        request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
    }

    protected void add2cart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String idS = request.getParameter("id");
        int id = Integer.parseInt(idS);
        ProductFacade pf = new ProductFacade();
        Product product = pf.read(id);
        Item item = new Item(product, 1);

        //Lay cart tu session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            //Neu trong session chua co cart thi tao moi
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cart.add(item);
        String urlParam = (String) session.getAttribute("urlParam");

        response.sendRedirect(request.getContextPath() + urlParam + "#" + id);
    }

    protected void cart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String op = request.getParameter("op");
        System.out.println(op);
        HttpSession session = request.getSession();

        //Lay cart tu session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            //Neu trong session chua co cart thi tao moi
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        if (op != null) {
            String idS = request.getParameter("id");
            System.out.println("test ids" + idS);
            int id = 0;
            if (idS != null && !idS.isEmpty()) {
                id = Integer.parseInt(idS);
            }
            ProductFacade pf = new ProductFacade();
            Product product = pf.read(id);
            System.out.println(product);
            Item item = new Item(product, 1);
            switch (op) {
                case "minus":
                    cart.minus(item);
                    break;
                case "add":
                    cart.add(item);
                    break;
                case "remove":
                    cart.remove(id);
                    break;
                case "empty":
                    cart.empty();
                    break;
            }
        }

        request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
    }

    protected void pay(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String op = request.getParameter("op");
        System.out.println(op);
        OrderDetailFacade odf = new OrderDetailFacade();
        OrderHeaderFacade ohf = new OrderHeaderFacade();
        String totals = request.getParameter("total");
        float total = 0;
        if (totals != null && !totals.isEmpty()) {
            total = Float.parseFloat(totals);
        }
        //Lay cart tu session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (op != null) {
            //case buynow
            cart = new Cart();
            Item item = (Item) session.getAttribute("item");
            cart.add(item);
            request.setAttribute("op", op);
        }

        //Tao don hang
        Account acc = (Account) session.getAttribute("account");
        OrderHeader oh = new OrderHeader("On-going", acc.getId(), total);
        System.out.println(total);
        //Luu thong tin don hang
        ohf.create(oh);
        List ohl = ohf.select();
        oh = (OrderHeader) ohl.get(ohl.size() - 1);
        for (int key : cart.getMap().keySet()) {
            Item item = cart.getMap().get(key);
            OrderDetail od = new OrderDetail(oh.getId(),
                    item.getProduct().getId(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    item.getProduct().getDiscount());

            odf.create(od);
        }

        request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        if (op == null) {
            session.setAttribute("cart", null);
        }
    }

    protected boolean check_login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        if (session.getAttribute("account") == null) {
            return false;
        }
        return true;
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
