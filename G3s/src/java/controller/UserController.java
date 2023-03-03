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
                //Foward request & respone to view
                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;
            case "signin_handler":
                signin_handler(request, response);
                break;
            case "admin":
                admin(request, response);
                break;
            case "joinnow":
                //Foward request & respone to view
                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;
            case "logout":
                logout(request, response);
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

    protected void signin_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String emailOrPhone = request.getParameter("emailOrPhone");
            String password = request.getParameter("password");
            //Kiem tra thong trong DB
            accountFacade af = new accountFacade();
            account acc = af.signin(emailOrPhone, password);
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
        if(session.getAttribute("role")==null){
          request.getRequestDispatcher("/user/signin.do").forward(request, response);
        }else if(!session.getAttribute("role").equals("ROLE_ADMIN")){
            response.sendRedirect(request.getContextPath() + "/home/index.do");
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
