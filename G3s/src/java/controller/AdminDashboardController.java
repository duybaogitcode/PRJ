/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.AccountFacade;
import dal.OrderDetailFacade;
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
import model.Account;
import model.Order;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin_dashboard"})
public class AdminDashboardController extends HttpServlet {

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
            case "index": {
                //Barchart:
                List<Double> overallBarList = new ArrayList<>();
                List<Double> listBar2020 = new ArrayList<>();
                List<Double> listBar2021 = new ArrayList<>();
                List<Double> listBar2022 = new ArrayList<>();
                List<Double> listBar2023 = new ArrayList<>();
                List<Double> listBar2024 = new ArrayList<>();

                OrderHeaderFacade ohf = new OrderHeaderFacade();
                int monthCounter = 1;

                //Read data of each list from database:
                //List of 2020: 
                try {
                    while (monthCounter <= 12) {
                        listBar2020.add(ohf.getTotalPerMoth(monthCounter, 2020));
                        monthCounter++;
                    }
                } catch (SQLException ex) {
                }
                monthCounter = 1;
                //List of 2021: 
                try {
                    while (monthCounter <= 12) {
                        listBar2021.add(ohf.getTotalPerMoth(monthCounter, 2021));
                        monthCounter++;
                    }
                } catch (SQLException ex) {
                }
                monthCounter = 1;
                //List of 2022: 
                try {
                    while (monthCounter <= 12) {
                        listBar2022.add(ohf.getTotalPerMoth(monthCounter, 2022));
                        monthCounter++;
                    }
                } catch (SQLException ex) {
                }
                monthCounter = 1;
                //List of 2023: 
                try {
                    while (monthCounter <= 12) {
                        listBar2023.add(ohf.getTotalPerMoth(monthCounter, 2023));
                        monthCounter++;
                    }
                } catch (SQLException ex) {
                }
                monthCounter = 1;
                //List of 2024: 
                try {
                    while (monthCounter <= 12) {
                        listBar2024.add(ohf.getTotalPerMoth(monthCounter, 2024));
                        monthCounter++;
                    }
                } catch (SQLException ex) {
                }
                monthCounter = 1;
                //List of Overall: 
                double total2020 = 0;
                for (double x : listBar2020) {
                    total2020 += x;
                }
                overallBarList.add(total2020);
                double total2021 = 0;
                for (double x : listBar2021) {
                    total2021 += x;
                }
                overallBarList.add(total2021);
                double total2022 = 0;
                for (double x : listBar2022) {
                    total2022 += x;
                }
                overallBarList.add(total2022);
                double total2023 = 0;
                for (double x : listBar2023) {
                    total2023 += x;
                }
                overallBarList.add(total2023);
                double total2024 = 0;
                for (double x : listBar2024) {
                    total2024 += x;
                }
                overallBarList.add(total2024);

                //----------------------------------------------------------
                //Piechart:
                List<Integer> overallPieList = new ArrayList<>();
                List<Integer> listPie2020 = new ArrayList<>();
                List<Integer> listPie2021 = new ArrayList<>();
                List<Integer> listPie2022 = new ArrayList<>();
                List<Integer> listPie2023 = new ArrayList<>();
                List<Integer> listPie2024 = new ArrayList<>();

                OrderDetailFacade odf = new OrderDetailFacade();

                try {
                    //2020:
                    listPie2020.add(odf.getPercentageCategoryPerYear("AW", 2020));
                    listPie2020.add(odf.getPercentageCategoryPerYear("BAT", 2020));
                    listPie2020.add(odf.getPercentageCategoryPerYear("SM", 2020));
                    listPie2020.add(odf.getPercentageCategoryPerYear("SP", 2020));

                    //2021:
                    listPie2021.add(odf.getPercentageCategoryPerYear("AW", 2021));
                    listPie2021.add(odf.getPercentageCategoryPerYear("BAT", 2021));
                    listPie2021.add(odf.getPercentageCategoryPerYear("SM", 2021));
                    listPie2021.add(odf.getPercentageCategoryPerYear("SP", 2021));
                    System.out.println(listPie2021);
                    //2022:
                    listPie2022.add(odf.getPercentageCategoryPerYear("AW", 2022));
                    listPie2022.add(odf.getPercentageCategoryPerYear("BAT", 2022));
                    listPie2022.add(odf.getPercentageCategoryPerYear("SM", 2022));
                    listPie2022.add(odf.getPercentageCategoryPerYear("SP", 2022));

                    //2023:
                    listPie2023.add(odf.getPercentageCategoryPerYear("AW", 2023));
                    listPie2023.add(odf.getPercentageCategoryPerYear("BAT", 2023));
                    listPie2023.add(odf.getPercentageCategoryPerYear("SM", 2023));
                    listPie2023.add(odf.getPercentageCategoryPerYear("SP", 2023));

                    //2024:
                    listPie2024.add(odf.getPercentageCategoryPerYear("AW", 2024));
                    listPie2024.add(odf.getPercentageCategoryPerYear("BAT", 2024));
                    listPie2024.add(odf.getPercentageCategoryPerYear("SM", 2024));
                    listPie2024.add(odf.getPercentageCategoryPerYear("SP", 2024));

                    //Overall
                    overallPieList.add(odf.getPercentageCategoryOverall("AW"));
                    overallPieList.add(odf.getPercentageCategoryOverall("BAT"));
                    overallPieList.add(odf.getPercentageCategoryOverall("SM"));
                    overallPieList.add(odf.getPercentageCategoryOverall("SP"));
                } catch (SQLException ex) {
                }

                //Lay so luong cua tung danh sach ve va in ra man hinh
                List<Account> accList = new ArrayList<>();
                List<Product> proList = new ArrayList<>();
                List<OrderHeader> orderHeaderList = new ArrayList<>();
                int accListSize = 0;
                int proListSize = 0;
                int orderHeaderListSize = 0;

                AccountFacade af = new AccountFacade();
                try {
                    accList = af.select();
                } catch (SQLException ex) {
                    Logger.getLogger(AdminDashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
                accListSize = accList.size();
                
                ProductFacade pf = new ProductFacade();
                try {
                    proList = pf.select();
                } catch (SQLException ex) {
                    Logger.getLogger(AdminDashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
                proListSize = proList.size();
                
                OrderHeaderFacade ohf1 = new OrderHeaderFacade();
                try {
                    orderHeaderList = ohf1.select();
                } catch (SQLException ex) {
                    Logger.getLogger(AdminDashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
                orderHeaderListSize = orderHeaderList.size();

                request.setAttribute("accListSize", accListSize);
                request.setAttribute("proListSize", proListSize);
                request.setAttribute("orderHeaderListSize", orderHeaderListSize);
                
                request.setAttribute("overallPieList", overallPieList);
                request.setAttribute("listPie2020", listPie2020);
                request.setAttribute("listPie2021", listPie2021);
                request.setAttribute("listPie2022", listPie2022);
                request.setAttribute("listPie2023", listPie2023);
                request.setAttribute("listPie2024", listPie2024);

                request.setAttribute("overallBarList", overallBarList);
                request.setAttribute("listBar2020", listBar2020);
                request.setAttribute("listBar2021", listBar2021);
                request.setAttribute("listBar2022", listBar2022);
                request.setAttribute("listBar2023", listBar2023);
                request.setAttribute("listBar2024", listBar2024);

                request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
                break;
            }
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
