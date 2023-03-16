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
            case "searchajax":
                searchajax(request, response);
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
            List<Account> list = af.select();
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

        try {
            String keyword = request.getParameter("keyword");
            System.out.println(keyword);

            AccountFacade af = new AccountFacade();
            List<Account> list = new ArrayList<>();
            String indexPageStr = request.getParameter("indexPage");
            if (indexPageStr == null) {
                indexPageStr = "1";
            }
            int indexPage = Integer.parseInt(indexPageStr);
            list = af.searchFullType(keyword, indexPage);
            int listSize = list.size();
            int endPage = (listSize % 10 == 0) ? (listSize / 10) : (listSize / 10 + 1);
            request.setAttribute("keyword", keyword);
            request.setAttribute("endPage", endPage);
            request.setAttribute("listPaging", list);
            request.getRequestDispatcher("/WEB-INF/layouts/admin.jsp").forward(request, response);

        } catch (Exception e) {
        }
    }

    protected void searchajax(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            String keyword = request.getParameter("keyword");
            System.out.println(keyword);

            AccountFacade af = new AccountFacade();
            List<Account> list = new ArrayList<>();
            String indexPageStr = request.getParameter("indexPage");
            if (indexPageStr == null) {
                indexPageStr = "1";
            }
            int indexPage = Integer.parseInt(indexPageStr);
            list = af.searchFullType(keyword, indexPage);
            int listSize = list.size();
            int endPage = (listSize % 10 == 0) ? (listSize / 10) : (listSize / 10 + 1);

            StringBuilder sb = new StringBuilder();
            sb.append("<div id=\"content_tableAccount\">");
            sb.append("<table class=\"table table-striped\">");
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th>Id</th>");
            sb.append("<th>Name</th>");
            sb.append("<th>Address</th>");
            sb.append("<th>Phone</th>");
            sb.append("<th>Email</th>");
            sb.append("<th>Password</th>");
            sb.append("<th>Enable</th>");
            sb.append("<th>Role</th>");
            sb.append("<th>Operations</th>");
            sb.append("</tr>");
            sb.append("</thead>");
            sb.append("<tbody>");

            for (Account acc : list) {
                sb.append("<tr>");
                sb.append("<td>").append(acc.getId()).append("</td>");
                sb.append("<td>").append(acc.getName()).append("</td>");
                sb.append("<td>").append(acc.getAddress()).append("</td>");
                sb.append("<td>").append(acc.getPhone()).append("</td>");
                sb.append("<td>").append(acc.getEmail()).append("</td>");
                sb.append("<td>").append(acc.getPassword()).append("</td>");
                sb.append("<td>").append(acc.isEnable() ? "<span class=\"enable_true\">Enable</span>" : "<span class=\"enable_false\">Disable</span>").append("</td>");
                sb.append("<td>").append(acc.getRole()).append("</td>");
                sb.append("<td>");
                sb.append("<a href=\"").append(request.getContextPath()).append("/admin_account/edit.do?id=").append(acc.getId()).append("&email=").append(acc.getEmail()).append("\"><i class=\"fa-solid fa-pen-to-square\"></i></a> |");
                sb.append("<a href=\"").append(request.getContextPath()).append("/admin_account/delete.do?id=").append(acc.getId()).append("&name=").append(acc.getName()).append("\"><i class=\"fa-solid fa-trash-can\"></i></a>");
                sb.append("</td>");
                sb.append("</tr>");
            }

            sb.append("</tbody>");
            sb.append("</table>");
            sb.append("<div class=\"datatable-bottom\">");
            sb.append("<nav class=\"datatable-pagination\">");
            sb.append("<ul class=\"datatable-pagination-list\">");

            for (int i = 1; i <= endPage; i++) {
                sb.append("<li class=\"datatable-pagination-list-item datatable-active\">");
                sb.append("<a class=\"").append(i == indexPage ? "btn btn-secondary" : "btn-secondary").append("\" href=\"").append(request.getContextPath()).append("/admin_account/search.do?indexPage=").append(i).append("&keyword=").append(keyword).append("\" class=\"btn btn-secondary datatable-pagination-list-item-link\">").append(i).append("</a>");
                sb.append("</li>");
            }

            sb.append("</ul>");
            sb.append("</nav>");
            sb.append("</div>");
            sb.append("</div>");

            response.setContentType("text/html");
            response.getWriter().write(sb.toString());
        } catch (Exception e) {
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
            String idS = request.getParameter("id");
            int id = Integer.parseInt(idS);
            //Lay email tu client
            String email_old = request.getParameter("email");
            //Lay thong tin account tu id cua client
            AccountFacade af = new AccountFacade();
            List<Account> accList = new ArrayList<>();
            accList = af.select();
            Account acc = null;
            for (Account a : accList) {
                if (a.getId() == id) {
                    //Ham read() su dung email de tim kiem
                    acc = af.read(a.getEmail());
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
                    Account accOld = af.read(email_old);
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
                        accList = af.select();
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

                    af.update(accUp);

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
