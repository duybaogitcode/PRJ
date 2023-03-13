/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Product;

/**
 *
 * @author Admin
 */
public class OrderFacade {

    public List<Order> select() throws SQLException {
        List<Order> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select orderheader.id, orderheader.date, status, product.name, quantity, total from orderheader join orderdetail on id = orderheaderid join product on productid = product.id");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            Order or = new Order();
            or.setId(rs.getInt("id"));
            or.setDate(rs.getTimestamp("date"));
            or.setStatus(rs.getString("status"));
            or.setProductName(rs.getString("name"));
            or.setQuantity(rs.getInt("quantity"));
            or.setTotal(rs.getFloat("total"));
            //Them cate vao list
            list.add(or);
        }
        con.close();
        return list;
    }

    public void delete(int id) throws SQLException {
        Connection con = DBContext.getConnection();
        // Prepare -> thay đổi dữ liệu
        PreparedStatement stm = con.prepareStatement("delete from orderdetail where orderHeaderId = ?");
        stm.setInt(1, id);
        int count = stm.executeUpdate();
        stm = con.prepareStatement("delete from OrderHeader where id = ?");
        stm.setInt(1, id);
        count = stm.executeUpdate();
        con.close();
    }

    public List<Order> pagingRead(int indexPage) throws SQLException {
        List<Order> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();

        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select orderheader.id, date, status, product.name, quantity, total from orderheader join orderdetail on id = orderheaderid join product on productid = product.id order by id offset ? rows fetch next 10 rows only");
        list = new ArrayList<>();
        //Thực thi lệnsh SELECT
        stm.setInt(1, (indexPage - 1) * 10);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            Order or = new Order();
            or.setId(rs.getInt("id"));
            or.setDate(rs.getTimestamp("date"));
            or.setStatus(rs.getString("status"));
            or.setProductName(rs.getString("name"));
            or.setQuantity(rs.getInt("quantity"));
            or.setTotal(rs.getFloat("total"));
            //Them cate vao list
            list.add(or);
        }
        con.close();
        return list;
    }
}
