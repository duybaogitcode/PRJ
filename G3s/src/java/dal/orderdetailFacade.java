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
        PreparedStatement stm = con.prepareStatement("insert OrderDetail values(?,?,?,?,?)");
        stm.setInt(1, ord.getOrderHeaderId());
        stm.setInt(2, ord.getProductId());
        stm.setInt(3, ord.getQuantity());
        stm.setFloat(4, ord.getPrice());
        stm.setFloat(5, ord.getDiscount());
        stm.executeUpdate();
        con.close();
    }

    public OrderDetail read(String id) throws SQLException {
        OrderDetail ord = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from Product where orderHeaderId = ?");
        //Thực thi lệnh SELECT
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            ord = new OrderDetail();
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
        PreparedStatement stm = con.prepareStatement("update OrderDetail set productId = ?, quantity = ?, price = ?, discount = ?  where orderHeaderId = ?");
        stm.setInt(1, ord.getProductId());
        stm.setInt(2, ord.getQuantity());
        stm.setFloat(3, ord.getPrice());
        stm.setFloat(4, ord.getDiscount());
        stm.setInt(5, ord.getOrderHeaderId());
        int count = stm.executeUpdate();
        con.close();
    }

    public void delete(String id) throws SQLException {
        Connection con = DBContext.getConnection();
        // Prepare -> thay đổi dữ liệu
        PreparedStatement stm = con.prepareStatement("delete from OrderHeader where orderHeaderId = ?");
        stm.setString(1, id);
        int count = stm.executeUpdate();
        con.close();
    }

    public int getPercentageCategoryPerYear(String cateId, int year) throws SQLException {
        int res = 0;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select sum(quantity) as sumCategory from product join orderdetail on product.id = orderdetail.productid join orderheader on orderdetail.orderheaderid = orderheader.id where categoryid = ? and year(date) = ? group by categoryId");
        //Thực thi lệnh SELECT
        stm.setString(1, cateId);
        stm.setInt(2, year);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            res = rs.getInt("sumCategory");
        }
        con.close();
        return res;
    }
    
    public int getPercentageCategoryOverall(String cateId) throws SQLException {
        int res = 0;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select sum(quantity) as sumCategory from product join orderdetail on product.id = orderdetail.productid join orderheader on orderdetail.orderheaderid = orderheader.id where categoryid = ? group by categoryId");
        //Thực thi lệnh SELECT
        stm.setString(1, cateId);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            res = rs.getInt("sumCategory");
        }
        con.close();
        return res;
    }
}
