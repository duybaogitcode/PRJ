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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        HttpSession session = request.getSession();
        switch (action) {
            case "filter":
                session.setAttribute("urlParam", "/watch/filter.do?" + request.getQueryString());
                filter(request, response);
                break;
            case "search":
                session.setAttribute("urlParam", "/watch/search.do?" + request.getQueryString());
                search(request, response);
                break;
            case "searchajax":
                System.out.println("test dc search");
                searchByAjax(request, response);
                break;

        }

    }
//
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
//            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
//        } catch (SQLException ex) {
//            //Show the error page
//            request.setAttribute("message", ex.getMessage());
//            request.setAttribute("controller", "error");
//            request.setAttribute("action", "error");
//            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
//        }
//    }

    protected void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
    }

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
        String sort = request.getParameter("sort");
        System.out.println(sort);
        if (sort == null) {
            sort = "none";
        }
        if (indexPage == null) {
            indexPage = "1";
        }

        try {
            int index = Integer.parseInt(indexPage);
            ProductFacade pf = new ProductFacade();
            int numPage = pf.getTotalProduct(categoryIds, minPrice, maxPrice);
            int endPage = numPage / 6;

            if (numPage % 6 != 0) {
                endPage++;
            }
            System.out.println(endPage);
            List<Product> listPaging = pf.pagingProduct(index, categoryIds, minPrice, maxPrice, sort);
            request.setAttribute("listPaging", listPaging);
            request.setAttribute("endPage", endPage);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("categoryIds", categoryIds);
            request.setAttribute("sort", sort);
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        } catch (SQLException ex) {
            //Show the error page
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        }
    }

    protected void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String indexS = request.getParameter("index");
        String keyword = request.getParameter("keyword");

        List<Product> listSearchPaging = new ArrayList<>();
        ProductFacade pf = new ProductFacade();

        try {
            int numPage = pf.getTotalProduct(keyword);
            int endPage = numPage / 6;
            if (numPage % 6 != 0) {
                endPage++;
            }
            int index = Integer.parseInt(indexS);
            listSearchPaging = pf.pagingProduct(index, keyword);
            request.setAttribute("listSearchPaging", listSearchPaging);
            request.setAttribute("endPage", endPage);
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        }

    }

    protected void searchByAjax(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("test dc ajax");
        String keyword = request.getParameter("keyword");
        List<Product> products = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        CategoryFacade ctf = new CategoryFacade();
        ProductFacade pf = new ProductFacade();
        int endPage = 1;
        try {
            int numPage = pf.getTotalProduct(keyword);
            endPage = numPage / 6;
            if (numPage % 6 != 0) {
                endPage++;
            }
            categories = ctf.select();
            products = pf.pagingProduct(1, keyword);
        } catch (SQLException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("controller", "error");
            request.setAttribute("action", "error");
            request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);
        }
        System.out.println(keyword);

        int count = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("<div  id=\"show-product-table\" >");
        sb.append("<table>");
        for (Product product : products) {
            if (count % 3 == 0) {
                sb.append("<tr>");
            }
            sb.append("<td>");
            sb.append("<div class=\"show-product-box\">");
            sb.append("<h3>").append(product.getName()).append("</h3>");
            sb.append("<img src=\"").append(request.getContextPath()).append("/img/").append(product.getImage()).append("\" height=\"300\" width=\"300\" />");
            sb.append("<br />");
            for (Category category : categories) {
                if (category.getId() == product.getCategoryID()) {
                    sb.append("<p style=\"font-weight: 700;\">").append(category.getName()).append("</p>");
                    break;
                }
            }
            sb.append("<p class=\"price\">").append(product.getPrice()).append("$").append("</p>");
            sb.append("<div class=\"show-product-box-btn\">");
            sb.append("<a href=\"").append(request.getContextPath()).append("/order/buynow.do?id=").append(product.getId()).append("\" class=\"show-product-box-btn-buy\">Buy now</a>");
            sb.append("<a href=\"").append(request.getContextPath()).append("/order/add2cart.do?id=").append(product.getId()).append("\" class=\"show-product-box-btn-add\">Add to cart</a>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</td>");
            count++;
            if (count % 3 == 0 || count == products.size()) {
                sb.append("</tr>");
            }
        }
        sb.append("</table>");
        sb.append("</div>");
        sb.append("<hr/>");
        sb.append("<div class=\"index\">");
        for (int i = 1; i <= endPage; i++) {
            sb.append("<a class=\"page-index\" href=\"")
                    .append(request.getContextPath())
                    .append("/watch/search.do?index=")
                    .append(i)
                    .append("&keyword=")
                    .append(keyword)
                    .append("\">")
                    .append(i)
                    .append("</a>");

        }
        sb.append("</div>");
        response.setContentType("text/html");
        response.getWriter().write(sb.toString());
        HttpSession session = request.getSession();
        session.setAttribute("urlParam", "/watch/search.do?index=1&keyword=" + keyword);
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
