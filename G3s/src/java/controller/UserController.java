/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.accountFacade;
import dal.productFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.account;
import model.product;

/**
 *
 * @author duyba
 */
@WebServlet(name = "UserController", urlPatterns = {"/user"})
public class UserController extends HttpServlet {

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
            case "signin":
                //Processing code here
                //Foward request & respone to view
                System.out.println("test ne bao1");

                accountFacade af = new accountFacade();
                 {
                    try {
                        List<account> list = af.select();
                        System.out.println(list);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;
            case "signin_handler":
                request.getRequestDispatcher("/admin/index.do").forward(request, response);
                break;
            case "joinnow":
                //Processing code here
                //Foward request & respone to view
                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;
            case "joinnow_handler":
                joinnow_handler(request, response);
                break;
            default:

        }
    }

    protected void joinnow_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Lay thong tin register
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String tel = request.getParameter("tel");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            String passConfirm = request.getParameter("passConfirm");

            //Tao doi tuong account
            account acc = new account();
            acc.setName(username);
            acc.setAddress(address);
            acc.setPhone(tel);
            acc.setEmail(email);
            acc.setPassword(password);
            acc.setEnable(true);
            acc.setRole("ROLE_CUSTOMER");
            request.setAttribute("acc", acc);
            accountFacade af = new accountFacade();
            int doneCheck = 0;

            //Username check
            if (username == null || username.equals("")) {
                request.setAttribute("messageUser", "Username must not be blank.");
            } else {
                doneCheck++;
            }
            //Email check
            if (email == null || email.equals("")) {
                request.setAttribute("messageEmail", "Email must not be blank.");
            } else if (af.read(email) != null) {
                request.setAttribute("messageEmail", "Email is existed.");
            } else {
                doneCheck++;
            }
            //Phone check
            if (tel == null || tel.equals("")) {
                request.setAttribute("messagePhone", "Phone must not be blank.");
            } else {
                doneCheck++;
            }

            //Address check
            if (address == null || address.equals("")) {
                request.setAttribute("messageAddress", "Address must not be blank.");
            } else {
                doneCheck++;
            }

            //Password check
            if (password == null || password.equals("")) {
                request.setAttribute("messagePass", "Password must not be blank.");
            } else if (password.length() < 5) {
                request.setAttribute("messagePass", "Password must be at least 5 characters.");
            } else {
                doneCheck++;
            }

            //Confirm Password check
            if (passConfirm == null || passConfirm.equals("")) {
                request.setAttribute("messagePassComfirm", "Password Comfirm must not be blank.");
            } else if (!password.equals(passConfirm)) {
                request.setAttribute("messagePassComfirm", "Passwords do not match.");
            } else {
                doneCheck++;
            }

            //Create new account
            if (doneCheck == 6) {
                af.create(acc);
                request.setAttribute("messageDone", "Done");
            }

            request.getRequestDispatcher("/user/joinnow.do").forward(request, response);

        } catch (SQLException ex) {
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
