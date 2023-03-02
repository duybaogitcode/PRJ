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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.category;
import model.orderdetail;
import model.Product;

/**
 *
 * @author duyba
 */
public class productFacade {

    public List<Product> select() throws SQLException {
        List<Product> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select * from product");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            Product pr = new Product();
            pr.setId(rs.getInt("id"));
            pr.setName(rs.getString("name"));
            pr.setDescription(rs.getNString("description"));
            pr.setImage(rs.getString("image"));
            pr.setPrice(rs.getFloat("price"));
            pr.setDiscount(rs.getFloat("discount"));
            pr.setCategoryID(rs.getString("categoryId"));
            list.add(pr);
        }
        con.close();
        return list;
    }
    
    public void create(orderdetail ord) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("insert orderDetail values(?,?,?,?,?,?)");
        stm.setInt(1, ord.getId());
        stm.setInt(2, ord.getOrderHeaderId());
        stm.setInt(3, ord.getProductId());
        stm.setInt(4, ord.getQuantity());
        stm.setFloat(5, ord.getPrice());
        stm.setFloat(6, ord.getDiscount());
        int count = stm.executeUpdate();
        con.close();
    }

    public Product read(String id) throws SQLException {
        Product pr = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from Product where id = ?");
        //Thực thi lệnh SELECT
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            pr = new Product();
            pr.setId(rs.getInt("id"));
            pr.setName(rs.getString("name"));
            pr.setDescription(rs.getNString("description"));
            pr.setImage(rs.getString("image"));
            pr.setPrice(rs.getFloat("price"));
            pr.setDiscount(rs.getFloat("discount"));
            pr.setCategoryID(rs.getString("categoryId"));
        }
        con.close();
        return pr;
    }

    public void update(orderdetail ord) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("update orderDetail set orderHeaderId = ?, productId = ?, quantity = ?, price = ?, discount = ?  where id = ?");
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
        PreparedStatement stm = con.prepareStatement("delete from orderHeader where id = ?");
        stm.setString(1, id);
        int count = stm.executeUpdate();
        con.close();
    }

    public List<Product> pagingProduct(int index) throws SQLException {
        List<Product> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement

        PreparedStatement stm = con.prepareStatement("select * from product\n order by id\n"
                + "offset ?  rows fetch next 6 rows only;");
        stm.setInt(1, (index - 1) * 6);
        ResultSet rs = stm.executeQuery();
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            Product pr = new Product();
            pr.setId(rs.getInt("id"));
            pr.setName(rs.getString("name"));
            pr.setDescription(rs.getNString("description"));
            pr.setImage(rs.getString("image"));
            pr.setPrice(rs.getFloat("price"));
            pr.setDiscount(rs.getFloat("discount"));
            pr.setCategoryID(rs.getString("categoryId"));
            list.add(pr);
        }
        con.close();
        return list;
    }
}
