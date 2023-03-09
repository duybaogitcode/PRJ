/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.CategoryFacade;
import dal.ProductFacade;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Category;
import model.Product;

/**
 *
 * @author duyba
 */
@WebServlet(name = "ProductController", urlPatterns = {"/watch"})
public class WatchesController extends HttpServlet {

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
        String controller = (String) request.getAttribute("controller");
        String action = (String) request.getAttribute("action");
        getListCate(request, response);
        switch (action) {
            case "index":
                request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
                break;
            case "filter":
                HttpSession session = request.getSession();
                session.setAttribute("urlParam", request.getQueryString());
                filter(request, response);
                break;
            case "checkout":
                System.out.println("checkout rồi nè");
                request.getRequestDispatcher("/watch/checkout.do").forward(request, response);
                break;
        }

    }

//    protected void showAll(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String indexPage = request.getParameter("index");
//        if (indexPage == null) {
//            indexPage = "1";
//        }
//
//        try {
//            int index = Integer.parseInt(indexPage);
//            ProductFacade pf = new ProductFacade();
//            int numPage = pf.getTotalProduct();
//            System.out.println("Numpage : " + numPage);
//            int endPage = numPage / 6;
//            System.out.println(endPage);
//            if (numPage % 6 != 0) {
//                endPage++;
//            }
//            List<Product> listPaging = pf.pagingProduct(index);
//            request.setAttribute("listPaging", listPaging);
//            request.setAttribute("endPage", endPage);
//            request.setAttribute("link", "watches");
//            request.getRequestDispatcher("/watch/index.do").forward(request, response);
//        } catch (SQLException ex) {
//            //Show the error page
//            request.setAttribute("message", ex.getMessage());
//            request.setAttribute("controller", "error");
//            request.setAttribute("action", "error");
//            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
//        }
//    }
    protected void getListCate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryFacade cf = new CategoryFacade();
        try {
            List<Category> listCate = cf.select();
            request.setAttribute("listCate", listCate);
        } catch (SQLException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        }
    }

    protected void filter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String indexPage = request.getParameter("index");
        String[] categoryIds = request.getParameterValues("category");
        System.out.println(categoryIds);
        String minPrice = request.getParameter("min");
        System.out.println(minPrice);
        String maxPrice = request.getParameter("max");
        System.out.println(maxPrice);
        if (indexPage == null) {
            indexPage = "1";
        }

        try {
            int index = Integer.parseInt(indexPage);
            ProductFacade pf = new ProductFacade();
            int numPage = pf.getTotalProduct(categoryIds, minPrice, maxPrice);
            System.out.println("Numpage : " + numPage);
            int endPage = numPage / 6;
            System.out.println(endPage);
            if (numPage % 6 != 0) {
                endPage++;
            }
            System.out.println(endPage);
            List<Product> listPaging = pf.pagingProduct(index, categoryIds, minPrice, maxPrice);
            request.setAttribute("listPaging", listPaging);
            request.setAttribute("endPage", endPage);
            request.setAttribute("link", "filter");
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("categoryIds", categoryIds);
            request.getRequestDispatcher("/watch/index.do").forward(request, response);
        } catch (SQLException ex) {
            //Show the error page
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
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
