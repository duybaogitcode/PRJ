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
import model.customer;
import model.orderdetail;

/**
 *
 * @author admin
 */
public class orderdetailFacade {
   public List<orderdetail> select() throws SQLException {
        List<orderdetail> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select * from OrderDetail");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            orderdetail ord = new orderdetail();
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
  }
