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

/**
 *
 * @author admin
 */
public class CustomerFacade {

    public List<Customer> select() throws SQLException {
        List<Customer> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select * from Customer");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            Customer cus = new Customer();
            cus.setId(rs.getInt("id"));
            cus.setCatagory(rs.getString("catagory"));
            cus.setShipToAddress(rs.getString("shipToAddress"));
            //Them cate vao list
            list.add(cus);
        }
        con.close();
        return list;
    }

    public void create(Customer cus) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("insert Customer values(?,?,?)");
        stm.setInt(1, cus.getId());
        stm.setString(2, cus.getCatagory());
        stm.setString(3, cus.getShipToAddress());
        int count = stm.executeUpdate();
        con.close();
    }

    public Customer read(String id) throws SQLException {
        Customer cus = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from Customer where id = ?");
        //Thực thi lệnh SELECT
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            cus = new Customer();
            cus.setId(rs.getInt("id"));
            cus.setCatagory(rs.getString("catagory"));
            cus.setShipToAddress("shipToAddress");
        }
        con.close();
        return cus;
    }

    public void update(Customer cus) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("update Customer set catagory = ?, shipToAddress = ? where id = ?");
        stm.setString(1, cus.getCatagory());
        stm.setString(2, cus.getShipToAddress());
        stm.setInt(3, cus.getId());
        int count = stm.executeUpdate();
        con.close();
    }

    public void delete(String id) throws SQLException {
        Connection con = DBContext.getConnection();
        // Prepare -> thay đổi dữ liệu
        PreparedStatement stm = con.prepareStatement("delete from Customer where id = ?");
        stm.setString(1, id);
        int count = stm.executeUpdate();
        con.close();
    }
}
