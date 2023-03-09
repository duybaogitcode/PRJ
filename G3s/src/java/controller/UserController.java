/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.AccountFacade;
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
import javax.servlet.http.HttpSession;
import model.Account;
import model.Product;

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

                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;
            case "signin_handler":
                signin_handler(request, response);
                break;
            case "admin":
                admin(request, response);
                break;
            case "signup":
                //Processing code here
                //Foward request & respone to view
                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;
            case "signup_handler":
                signup_handler(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
                //Show error page
                request.setAttribute("Message", "Invalid");
        }
    }

    protected void signin_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String emailOrPhone = request.getParameter("emailOrPhone");
            String password = request.getParameter("password");
            //Kiem tra thong trong DB
            AccountFacade af = new AccountFacade();
            Account acc = af.signin(emailOrPhone, password);
            if (acc == null) {
                //Neu login khong thanh cong thi quay ve login form 
                //de nhap lai thong tin
                request.setAttribute("message", "Incorrect email or password.");
                request.getRequestDispatcher("/user/signin.do").forward(request, response);
            } else {
                //Neu login thanh cong
                //Luu account vao session
                HttpSession session = request.getSession();
                session.setAttribute("account", acc);
                session.setAttribute("role", acc.getRole());
                if (acc.getRole().equals("ROLE_ADMIN")) {
                    //forward to admin page
                    response.sendRedirect(request.getContextPath() + "/admin_account/index.do");
                } else {
                    //Quay ve Home Page
                    response.sendRedirect(request.getContextPath() + "/home/index.do");
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message", ex.toString());
            request.getRequestDispatcher("/user/signin.do").forward(request, response);
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Xoa session
        HttpSession session = request.getSession();
        session.invalidate();
        //Quay ve trang chu
        response.sendRedirect(request.getContextPath() + "/home/index.do");
    }

    protected void admin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("role") == null) {
            request.getRequestDispatcher("/user/signin.do").forward(request, response);
        } else if (!session.getAttribute("role").equals("ROLE_ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/home/index.do");
        }
    }

    protected void signup_handler(HttpServletRequest request, HttpServletResponse response)
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
            Account acc = new Account();
            acc.setName(username);
            acc.setAddress(address);
            acc.setPhone(tel);
            acc.setEmail(email);
            acc.setPassword(password);
            acc.setEnable(true);
            acc.setRole("ROLE_CUSTOMER");
            request.setAttribute("acc", acc);
            AccountFacade af = new AccountFacade();
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
            } else if (af.read_1(email) != null) {
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
                request.setAttribute("messageDone", "Sign up success. Please Sign in.");
            }

            request.getRequestDispatcher("/user/signup.do").forward(request, response);

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
