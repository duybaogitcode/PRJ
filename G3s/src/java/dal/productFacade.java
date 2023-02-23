/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.category;
import model.product;

/**
 *
 * @author duyba
 */
public class productFacade {

    public List<product> select() throws SQLException {
        List<product> list = null;
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
}
