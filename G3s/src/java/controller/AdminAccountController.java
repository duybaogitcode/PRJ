/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import dal.AccountFacade;
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
import model.Account;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminAccountController", urlPatterns = {"/admin_account"})
public class AdminAccountController extends HttpServlet {

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
            case "search":
                search(request, response);
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
            case "logout": //Process the edit form
                logout(request, response);
                break;
        }
    }

    protected void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            AccountFacade af = new AccountFacade();

            //ListFull
            List<Account> list = af.select_1();
            int listSize = list.size();
            int endPage = (listSize % 10 == 0) ? (listSize / 10) : (listSize / 10 + 1);

            String indexPageStr = request.getParameter("indexPage");
            if (indexPageStr == null) {
                indexPageStr = "1";
            }
            int indexPage = Integer.parseInt(indexPageStr);

            //ListPaging
            List<Account> listPaging = af.pagingRead(indexPage);

            request.setAttribute("indexPage", indexPage);
            request.setAttribute("listPaging", listPaging);
            request.setAttribute("endPage", endPage);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
        } catch (SQLException ex) {

        }
    }

    protected void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String searchInput = request.getParameter("searchInput");
        AccountFacade af = new AccountFacade();

        List<Account> list = new ArrayList<>();
        list = af.searchFullType(searchInput);

//        int listSize = list.size();
//        int endPage = (listSize % 10 == 0) ? (listSize / 10) : (listSize / 10 + 1);
//
//        String indexPageStr = request.getParameter("indexPage");
//        if (indexPageStr == null) {
//            indexPageStr = "1";
//        }
//        int indexPage = Integer.parseInt(indexPageStr);
//
//        //ListPaging
//        List<Account> listPaging = af.pagingRead(indexPage);
        PrintWriter out = response.getWriter();
        out.print("<table class=\"table table-striped\"> \n"
                + "            <thead>\n"
                + "                <tr>\n"
                + "                    <th>Id</th>\n"
                + "                    <th>Name</th>\n"
                + "                    <th>Address</th>\n"
                + "                    <th>Phone</th>\n"
                + "                    <th>Email</th>\n"
                + "                    <th>Password</th>\n"
                + "                    <th>Enable</th>\n"
                + "                    <th>Role</th>\n"
                + "                    <th>Operations</th>\n"
                + "                </tr>\n"
                + "            </thead>\n"
                + "            <tbody>");
        for (Account acc : list) {
            String isEnableShow = null;
            String editURL = "/g3s/admin_account/edit.do?id=" + acc.getId() + "&email=" + acc.getEmail();
            String deleteURL = "/g3s/admin_account/delete.do?id=" + acc.getId() + "&name=" + acc.getName();

            if (acc.isEnable()) {
                isEnableShow = "<span class=\"enable_true\">Enable</span>";
            } else {
                isEnableShow = "<span class=\"enable_false\">Disable</span>";
            }

            out.print("                  <tr>\n"
                    + "                        <td>" + acc.getId() + "</td>\n"
                    + "                        <td>" + acc.getName() + "</td>\n"
                    + "                        <td>" + acc.getAddress() + "</td>\n"
                    + "                        <td>" + acc.getPhone() + "</td>\n"
                    + "                        <td>" + acc.getEmail() + "</td>\n"
                    + "                        <td>" + acc.getPassword() + "</td>\n"
                    + "                        <td>" + isEnableShow + "</td>\n"
                    + "                        <td>" + acc.getRole() + "</td>\n"
                    + "                        <td>\n"
                    + "                            <a href=\"" + editURL + "\"><i class=\"fa-solid fa-pen-to-square\"></i></a> |\n"
                    + "                            <a href=\"" + deleteURL + "\"><i class=\"fa-solid fa-trash-can\"></i></a>\n"
                    + "                        </td>\n"
                    + "                    </tr>\n");
        }
        out.print("</tbody>\n"
                + "        </table>");

        //Dang feed: Phan trang in search
//        out.print("</tbody>\n"
//                + "        </table>"
//                + "<div class=\"datatable-bottom\">\n"
//                + "                <nav class=\"datatable-pagination\">\n"
//                + "                    <ul class=\"datatable-pagination-list\">\n");
//        for (int i = 1; i <= endPage; i++) {
//            String pagingURL = "/g3s/admin_account/search.do?indexPage="+i+"&searchInput="+searchInput+"";
//            out.print("<li class=\"datatable-pagination-list-item datatable-active\">\n"
//                + "                                <a href=\""+pagingURL+"\" class=\"btn btn-secondary datatable-pagination-list-item-link\">"+i+"</a>\n"
//                + "                            </li>\n");
//        }
//        out.print("                    </ul>\n"
//                + "                </nav>\n"
//                + "            </div>");
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
                AccountFacade af = new AccountFacade();
                try {
                    af.delete(id);
                    response.sendRedirect(request.getContextPath() + "/admin_account/index.do");
                } catch (SQLException ex) {
                }
                break;
            case "no":
                response.sendRedirect(request.getContextPath() + "/admin_account/index.do");
                break;
        }
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Lay id tu client
            Object idStr = request.getParameter("id");
            Integer id = Integer.parseInt((String) idStr);
            //Lay email tu client
            String email_old = request.getParameter("email");
            //Lay thong tin account tu id cua client
            AccountFacade af = new AccountFacade();
            List<Account> accList = new ArrayList<>();
            accList = af.select_1();
            Account acc = null;
            for (Account a : accList) {
                if (a.getId() == id) {
                    //Ham read() su dung email de tim kiem
                    acc = af.read_1(a.getEmail());
                }
            }

            Account accOld = acc;

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
            request.setAttribute("accOld", accOld);
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
                    String email_old = request.getParameter("email_old");
                    String phone = request.getParameter("phone");
                    String address = request.getParameter("address");
                    String password = request.getParameter("password");
                    String enabled = request.getParameter("enabled");
                    String role = request.getParameter("role");

                    AccountFacade af = new AccountFacade();

                    //Check blank input and create the update account
                    Account accOld = af.read_1(email_old);
                    Account accUp = new Account();

                    //Id blank check
                    accUp.setId(id);

                    boolean nameBlankCheck = true;
                    boolean addressBlankCheck = true;
                    boolean phoneBlankCheck = true;
                    boolean emailBlankCheck = true;
                    boolean passwordBlankCheck = true;

                    //Name blank check
                    if (name.equals("") || name == null) {
                        nameBlankCheck = false;
                    }
                    if (nameBlankCheck != false) {
                        accUp.setName(name);
                    } else {
                        accUp.setName(accOld.getName());
                    }

                    //Address blank check
                    if (address.equals("") || address == null) {
                        addressBlankCheck = false;
                    }
                    if (addressBlankCheck != false) {
                        accUp.setAddress(address);
                    } else {
                        accUp.setAddress(accOld.getAddress());
                    }

                    //Phone blank check
                    if (phone.equals("") || phone == null) {
                        phoneBlankCheck = false;
                    }
                    if (phoneBlankCheck != false) {
                        accUp.setPhone(phone);
                    } else {
                        accUp.setPhone(accOld.getPhone());
                    }

                    //Password blank check
                    if (password.charAt(0) == ' ' || password.equals("") || password == null) {
                        passwordBlankCheck = false;
                    }
                    if (passwordBlankCheck != false) {
                        accUp.setPassword(password);
                    } else {
                        accUp.setPassword(accOld.getPassword());
                    }

                    //Email blank check and exist check
                    boolean emailExistCheck = true;

                    if (email.equals("") || email == null) {
                        emailBlankCheck = false;
                    }
                    if (emailBlankCheck != false) {
                        //Email exist check
                        List<Account> accList = new ArrayList<>();
                        accList = af.select_1();
                        for (Account accF : accList) {
                            if (!email.equals(accOld.getEmail()) && email.equals(accF.getEmail())) {
                                emailExistCheck = false;
                                break;
                            }
                        }
                        if (emailExistCheck == false) {
                            accUp.setEmail(accOld.getEmail());
                        } else {
                            accUp.setEmail(email);
                            accOld.setEmail(email);
                        }
                    } else {
                        accUp.setEmail(accOld.getEmail());
                    }

                    accUp.setRole(role);
                    if (enabled.equals("true")) {
                        accUp.setEnable(true);
                    } else {
                        accUp.setEnable(false);
                    }

                    //Enabled or Disabled
                    List<Boolean> eList = new ArrayList<>();
                    eList.add(Boolean.TRUE);
                    eList.add(Boolean.FALSE);

                    //ROLE_ADMIN or ROLE_CUSTOMER
                    List<String> rList = new ArrayList<>();
                    rList.add("ROLE_ADMIN");
                    rList.add("ROLE_CUSTOMER");

                    //Representation
                    request.setAttribute("eList", eList);
                    request.setAttribute("rList", rList);
                    request.setAttribute("acc", accUp);

                    if (emailExistCheck == false) {
                        request.setAttribute("accOld", accOld);
                        request.setAttribute("messageEmail", "Your email is exist. Try another.");
                        request.setAttribute("action", "edit");
                        request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
                    }

                    //Cap nhat toy vao database
                    af.update(accUp);

                    //Hien danh sach toy
                    response.sendRedirect(request.getContextPath() + "/admin_account/index.do");
                } catch (SQLException ex) {
                    request.setAttribute("message", "Can not save this account. Please check the account data.");
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);
                }
                break;
            case "cancel":
                response.sendRedirect(request.getContextPath() + "/admin_account/index.do");
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
            Logger.getLogger(AdminAccountController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdminAccountController.class.getName()).log(Level.SEVERE, null, ex);
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

    protected void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.setAttribute("controller", "user");
        request.setAttribute("action", "signin");
        request.getRequestDispatcher("/WEB-INF/layouts/main.jsp").forward(request, response);

    }
}
