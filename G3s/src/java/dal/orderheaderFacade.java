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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Order;
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
            orh.setDate(rs.getTimestamp("date"));
            orh.setStatus(rs.getString("status"));
            orh.setCustomerId(rs.getInt("customerId"));
            orh.setTotal(rs.getFloat("total"));
            //Them cate vao list
            list.add(orh);
        }
        con.close();
        return list;
    }

    public void create(OrderHeader orh) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("insert OrderHeader values(?,?,?,?)");
        Calendar cal = Calendar.getInstance();
        Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        stm.setTimestamp(1, timestamp);
        stm.setString(2, orh.getStatus());
        stm.setInt(3, orh.getCustomerId());
        stm.setFloat(4, orh.getTotal());
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
            orh.setDate(rs.getTimestamp("date"));
            orh.setStatus(rs.getString("status"));
            orh.setCustomerId(rs.getInt("customerId"));
            orh.setTotal(rs.getFloat("total"));
        }
        con.close();
        return orh;
    }

    public void update(OrderHeader orh) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("update OrderHeader set status = ?, customerId = ? where id = ?");
        stm.setString(1, orh.getStatus());
        stm.setInt(2, orh.getCustomerId());
        stm.setInt(3, orh.getId());
        int count = stm.executeUpdate();
        con.close();
    }
    
    public void update_1(int id, String status) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("update OrderHeader set status = ? where id = ?");
        stm.setString(1, status);
        stm.setInt(2, id);
        int count = stm.executeUpdate();
        con.close();
    }

    public void delete(String id) throws SQLException {
        Connection con = DBContext.getConnection();
        // Prepare -> thay đổi dữ liệu
        PreparedStatement stm = con.prepareStatement("delete from OrderHeader where id = ?");
        stm.setString(1, id);
        stm.executeUpdate();
        con.close();
    }
    
    public double getTotalPerMoth(int month, int year) throws SQLException {
        double res = 0;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select sum(total) as sumTotal from orderheader where month(date) = ? and year(date) = ?");
        //Thực thi lệnh SELECT
        stm.setInt(1, month);
        stm.setInt(2, year);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            res = rs.getInt("sumTotal");
        }
        con.close();
        return res;
    }
    
}