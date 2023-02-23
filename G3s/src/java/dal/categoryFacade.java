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

/**
 *
 * @author duyba
 */
public class categoryFacade {

    public List<category> select() throws SQLException {
        List<category> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select * from Category");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            category cate = new category();
            cate.setId(rs.getInt("id"));
            cate.setName(rs.getString("name"));
            //Them cate vao list
            list.add(cate);
        }
        con.close();
        return list;
    }
}
