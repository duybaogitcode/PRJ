/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import dal.accountFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.account;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {

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
            accountFacade af = new accountFacade();
            List<account> list = af.select();
            request.setAttribute("list", list);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        } catch (SQLException ex) {

        }
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
                accountFacade af = new accountFacade();
                try {
                    af.delete(id);
                    //C1: Cho hien trang /toy/index.do
                    /*
                            List<Toy> list = tf.select();
                            request.setAttribute("list", list);
                            request.setAttribute("action", "index");
                            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                     */
                    //C2
                    response.sendRedirect(request.getContextPath() + "/admin/index.do");
                } catch (SQLException ex) {
                }
                break;
            case "no":
                response.sendRedirect(request.getContextPath() + "/admin/index.do");
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
            accountFacade af = new accountFacade();
            List<account> accList = new ArrayList<>();
            accList = af.select();
            account acc = null;
            for (account a : accList) {
                if (a.getId() == id) {
                    //Ham read() su dung email de tim kiem
                    acc = af.read(a.getEmail());
                }
            }

            //Enabled or Disabled
            List<Boolean> eList = new ArrayList<>();
            eList.add(Boolean.TRUE);
            eList.add(Boolean.FALSE);

            //Enabled or Disabled
            List<String> rList = new ArrayList<>();
            rList.add("ROLE_ADMIN");
            rList.add("ROLE_CUSTOMER");

            //Hien edit form
            request.setAttribute("eList", eList);
            request.setAttribute("rList", rList);
            request.setAttribute("acc", acc);
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
                    int id = Integer.parseInt(request.getParameter("id"));
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String phone = request.getParameter("phone");
                    String address = request.getParameter("address");
                    String password = request.getParameter("password");
                    String enabled = request.getParameter("enabled");
                    String role = request.getParameter("role");
                    
                    accountFacade af = new accountFacade();
                    
                    //Tao doi tuong toi
                    account acc = new account();
                    acc.setId(id);
                    acc.setEmail(email);
                    acc.setAddress(address);
                    acc.setPhone(phone);
                    acc.setName(name);
                    acc.setPassword(password);
                    acc.setRole(role);
                    if(enabled.equals("true")) acc.setEnable(true);
                    else acc.setEnable(false);

                    //Enabled or Disabled
                    List<Boolean> eList = new ArrayList<>();
                    eList.add(Boolean.TRUE);
                    eList.add(Boolean.FALSE);

                    //Enabled or Disabled
                    List<String> rList = new ArrayList<>();
                    rList.add("ROLE_ADMIN");
                    rList.add("ROLE_CUSTOMER");

                    //Hien edit form
                    request.setAttribute("eList", eList);
                    request.setAttribute("rList", rList);
                    request.setAttribute("acc", acc);
                    
                    //Cap nhat toy vao database
                    af.update(acc);
                    //Hien danh sach toy
                    response.sendRedirect(request.getContextPath() + "/admin/index.do");
                } catch (SQLException ex) {
                    request.setAttribute("message", "Can not save this account. Please check the account data.");
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
                }
                break;
            case "cancel":
                response.sendRedirect(request.getContextPath() + "/admin/index.do");
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
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
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
