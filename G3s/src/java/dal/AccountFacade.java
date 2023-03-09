/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author admin
 */
public class AccountFacade {

    public List<Account> select() throws SQLException {
        List<Account> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        Statement stm = con.createStatement();
        //Thực thi lệnh SELECT
        ResultSet rs = stm.executeQuery("select * from Account");
        list = new ArrayList<>();
        while (rs.next()) {
            //Doc mau tin hien hanh de vao doi tuong 
            Account acc = new Account();
            acc.setId(rs.getInt("id"));
            acc.setName(rs.getString("name"));
            acc.setAddress(rs.getString("address"));
            acc.setPhone(rs.getString("phone"));
            acc.setEmail(rs.getString("email"));
            acc.setPassword(rs.getString("password"));
            acc.setRole(rs.getString("role"));
            //Them cate vao list
            list.add(acc);
        }
        con.close();
        return list;
    }

    public void create(Account acc) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("insert Account values(?,?,?,?,?,?,?)");
        stm.setInt(1, acc.getId());
        stm.setString(2, acc.getName());
        stm.setString(3, acc.getAddress());
        stm.setString(4, acc.getPhone());
        stm.setString(5, acc.getEmail());
        stm.setString(6, acc.getPassword());
        stm.setString(7, acc.getRole());
        int count = stm.executeUpdate();
        con.close();
    }

    public Account read(String id) throws SQLException {
        Account acc = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from Account where id = ?");
        //Thực thi lệnh SELECT
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            acc = new Account();
            acc.setId(rs.getInt("id"));
            acc.setName(rs.getString("name"));
            acc.setAddress(rs.getString("address"));
            acc.setPhone(rs.getString("phone"));
            acc.setEmail(rs.getString("email"));
            acc.setPassword(rs.getString("password"));
            acc.setRole(rs.getString("role"));
        }
        con.close();
        return acc;
    }

    public void update(Account acc) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("update Account set name = ?, address = ?, phone = ?, email = ?, password =?, role = ? where id = ?");
        stm.setString(1, acc.getName());
        stm.setString(2, acc.getAddress());
        stm.setString(3, acc.getPhone());
        stm.setString(4, acc.getEmail());
        stm.setString(6, acc.getPassword());
        stm.setInt(7, acc.getId());
        int count = stm.executeUpdate();
        con.close();
    }

    public void delete(String id) throws SQLException {
        Connection con = DBContext.getConnection();
        // Prepare -> thay đổi dữ liệu
        PreparedStatement stm = con.prepareStatement("delete from Account where id = ?");
        stm.setString(1, id);
        int count = stm.executeUpdate();
        con.close();
    }

    public Account signin(String emailOrPhone, String password) throws SQLException, NoSuchAlgorithmException {
        Account acc = null;
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("select * from account where email = ? or phone = ? and password = ?");
        stm.setString(1, emailOrPhone);
        stm.setString(2, emailOrPhone);
        stm.setString(3, password);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            acc = new Account();
            acc.setId(rs.getInt("id"));
            acc.setName(rs.getString("name"));
            acc.setAddress(rs.getString("address"));
            acc.setPhone(rs.getString("phone"));
            acc.setEmail(rs.getString("email"));
            acc.setPassword(rs.getString("password"));
            acc.setRole(rs.getString("role"));
        }
        con.close();
        return acc;
    }
}
