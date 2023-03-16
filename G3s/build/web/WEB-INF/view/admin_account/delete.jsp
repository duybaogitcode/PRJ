<%-- 
    Document   : delete
    Created on : Mar 3, 2023, 10:02:12 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="card mb-4">
    <div class="card-header">
        <!--        <i class="fas fa-table me-1"></i>-->
        <h2>Deletion Confirmation</h2>
    </div>
    <div class="card-body">
        <form action="<c:url value="/admin_account/delete_handler.do" />">
            <table>
                <thead>
                    <tr>
                        <th><input type="hidden" name="id" value="${id}" />
                            Are you sure to delete the account with name = ${name} ?<br/>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <p></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <button type="submit" class="btn btn-outline-success" name="op" value="yes"><i class="fa-regular fa-circle-check"></i> Yes</button>
                            <button type="submit" class="btn btn-outline-danger" name="op" value="no"><i class="fa-regular fa-circle-xmark"></i> No</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
