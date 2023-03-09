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
import model.Customer;
import model.OrderDetail;

/**
 *
 * @author admin
 */
public class OrderDetailFacade {
   public List<OrderDetail> select() throws SQLException {
        List<OrderDetail> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select * from OrderDetail");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            OrderDetail ord = new OrderDetail();
            ord.setId(rs.getInt("id"));
            ord.setOrderHeaderId(rs.getInt("orderHeaderId"));
            ord.setProductId(rs.getInt("productId"));         
            ord.setQuantity(rs.getInt("quantity"));
            ord.setPrice(rs.getFloat("price"));
            ord.setDiscount(rs.getFloat("discount"));
            //Them cate vao list
            list.add(ord);
        }
        con.close();
        return list;
    }
    
    public void create(OrderDetail ord) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("insert OrderDetail values(?,?,?,?,?,?)");
        stm.setInt(1, ord.getId());
        stm.setInt(2, ord.getOrderHeaderId());
        stm.setInt(3, ord.getProductId());
        stm.setInt(4, ord.getQuantity());
        stm.setFloat(5, ord.getPrice());
        stm.setFloat(6, ord.getDiscount());
        int count = stm.executeUpdate();
        con.close();
    }

    public OrderDetail read(String id) throws SQLException {
        OrderDetail ord = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from Product where id = ?");
        //Thực thi lệnh SELECT
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            ord = new OrderDetail();
            ord.setId(rs.getInt("id"));
            ord.setOrderHeaderId(rs.getInt("orderHeaderId"));
            ord.setProductId(rs.getInt("productId"));
            ord.setQuantity(rs.getInt("quantity"));
            ord.setPrice(rs.getInt("price"));
            ord.setDiscount(rs.getInt("discount"));
        }
        con.close();
        return ord;
    }

    public void update(OrderDetail ord) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("update OrderDetail set orderHeaderId = ?, productId = ?, quantity = ?, price = ?, discount = ?  where id = ?");
        stm.setInt(1, ord.getOrderHeaderId());
        stm.setInt(2, ord.getProductId());
        stm.setInt(3, ord.getQuantity());
        stm.setFloat(4, ord.getPrice());
        stm.setFloat(5, ord.getDiscount());
        stm.setInt(6, ord.getId());
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
