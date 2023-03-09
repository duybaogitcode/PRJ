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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.OrderHeader;

/**
 *
 * @author admin
 */
public class OrderHeaderFacade {

    public List<OrderHeader> select() throws SQLException {
        List<OrderHeader> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select * from OrderHeader");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            OrderHeader orh = new OrderHeader();
            orh.setId(rs.getInt("id"));
            orh.setDate(rs.getDate("date"));
            orh.setStatus(rs.getString("status"));
            orh.setCustomerId(rs.getInt("customerId"));
            //Them cate vao list
            list.add(orh);
        }
        con.close();
        return list;
    }

    public void create(OrderHeader orh) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("insert OrderHeader values(?,?,?,?)");
        stm.setInt(1, orh.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        stm.setString(2, sdf.format(orh.getDate()));
        stm.setString(3, orh.getStatus());
        stm.setInt(4, orh.getCustomerId());
        int count = stm.executeUpdate();
        con.close();
    }

    public OrderHeader read(String id) throws SQLException {
        OrderHeader orh = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from OrderHeader where id = ?");
        //Thực thi lệnh SELECT
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            orh = new OrderHeader();
            orh.setId(rs.getInt("id"));
            orh.setDate(rs.getDate("date"));
            orh.setStatus(rs.getString("status"));
            orh.setCustomerId(rs.getInt("customerId"));
        }
        con.close();
        return orh;
    }

    public void update(OrderHeader orh) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("update OrderHeader set date = ?, status = ?, customerId = ? where id = ?");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        stm.setString(1, sdf.format(orh.getDate()));
        stm.setString(2, orh.getStatus());
        stm.setInt(3, orh.getCustomerId());
        stm.setInt(4, orh.getId());
        int count = stm.executeUpdate();
        con.close();
    }

    public void delete(String id) throws SQLException {
        Connection con = DBContext.getConnection();
        // Prepare -> thay đổi dữ liệu
        PreparedStatement stm = con.prepareStatement("delete from OrderHeader where id = ?");
        stm.setString(1, id);
        int count = stm.executeUpdate();
        con.close();
    }
}
