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
import model.product;

/**
 *
 * @author duyba
 */
public class productFacade {

    private List<product> list = null;

    public List<product> select() throws SQLException {

        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select * from product");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            product pr = new product();
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

    public product selectById(int id) {
       //cach1
        for (product pro : list) {
            if (pro.getId() == id) {
                return pro;
            }

        }
        return null;
        //cach 2
//        List<product> list = new ArrayList<>();
//        String sql = "select id , name,  description, img, price,discount, categoryId  from product WHERE id=?";
//        try {
//            Connection con = DBContext.getConnection();
//            //Tạo đối tượng statement
//            Statement stm = con.createStatement();
//            ResultSet rs = stm.executeQuery(sql);
//            while (rs.next()) {
//
//                return(new product(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getFloat(5), rs.getFloat(6), rs.getString(7)));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return null;
    }

    public List<product> pagingProduct(int index) throws SQLException {
        List<product> list = null;
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
            product pr = new product();
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
