/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.OrderFacade;
import dal.OrderHeaderFacade;
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
import model.Order;
import model.OrderHeader;
import model.Product;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminOrderController", urlPatterns = {"/admin_order"})
public class AdminOrderController extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            String controller = (String) request.getAttribute("controller");
            String action = (String) request.getAttribute("action");
            switch (action) {
                case "index":
                    index(request, response);
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
        } catch (SQLException ex) {
            Logger.getLogger(AdminOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            OrderFacade of = new OrderFacade();

            //ListFull
            List<Order> list = of.select();

            int listSize = list.size();
            int endPage = (listSize % 10 == 0) ? (listSize / 10) : (listSize / 10 + 1);

            String indexPageStr = request.getParameter("indexPage");
            if (indexPageStr == null) {
                indexPageStr = "1";
            }
            int indexPage = Integer.parseInt(indexPageStr);
            //ListPaging
            List<Order> listPaging = of.pagingRead(indexPage);
            List<Integer> listId = new ArrayList<>();
            for (Order x : listPaging) {
                if (!listId.contains(x.getId())) {
                    listId.add(x.getId());
                    System.out.println(x.getId() + "Khong trung");
                } else {
                    x.setId(-1);
                    System.out.println(x.getId() + "Trung");
                }
            }

            request.setAttribute("indexPage", indexPage);
            request.setAttribute("listPaging", listPaging);
            request.setAttribute("endPage", endPage);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        } catch (SQLException ex) {

        }
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
    }

    protected void delete_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String op = request.getParameter("op");
        switch (op) {
            case "yes":
                int id = Integer.parseInt(request.getParameter("id"));
                OrderFacade of = new OrderFacade();
                try {
                    of.delete(id);
                    response.sendRedirect(request.getContextPath() + "/admin_order/index.do");
                } catch (SQLException ex) {
                }
                break;
            case "no":
                response.sendRedirect(request.getContextPath() + "/admin_order/index.do");
                break;
        }
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Lay id tu client
            String id = request.getParameter("id");

            //Lay thong tin account tu id cua client
            OrderHeaderFacade ohf = new OrderHeaderFacade();
            OrderHeader orderHeader = ohf.read(id);

            //Hien edit form
            request.setAttribute("orderHeader", orderHeader);
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
                    String status = request.getParameter("status");

                    OrderHeaderFacade ohf = new OrderHeaderFacade();
                    ohf.update(id, status);

                    //Hien danh sach order
                    response.sendRedirect(request.getContextPath() + "/admin_order/index.do");
                } catch (SQLException ex) {
                }
                break;
            case "cancel":
                response.sendRedirect(request.getContextPath() + "/admin_order/index.do");
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
