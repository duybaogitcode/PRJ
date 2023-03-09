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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import model.Account;
import model.OrderDetail;
import model.Product;

/**
 *
 * @author duyba
 */
public class ProductFacade {

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

    public void create(Product product) throws SQLException {
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        PreparedStatement stm = con.prepareStatement("insert product values(?, ?, ?, ?, ?, ?)");
        //Thực thi lệnh sql
        stm.setString(1, product.getName());
        stm.setString(2, product.getDescription());
        stm.setString(3, product.getImage());
        stm.setFloat(4, product.getPrice());
        stm.setFloat(5, product.getDiscount());
        stm.setString(6, product.getCategoryID());
        int rs = stm.executeUpdate();
        con.close();
    }

    public int getTotalProduct() throws SQLException {
        Connection con = DBContext.getConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select count(*) as numOfProduct from product ");
        while (rs.next()) {
            return rs.getInt("numOfProduct");
        }
        return 0;
    }

    public List<Product> getListPrByName(String name) throws SQLException {
        List<Product> list = null;
        Connection con = DBContext.getConnection();
        String query = "%" + name + "%";
        PreparedStatement stm = con.prepareStatement("select * from product where name like ?");
        stm.setString(1, query);
        ResultSet rs = stm.executeQuery();
        list = new ArrayList<>();
        while (rs.next()) {
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

    public int getTotalProduct(String[] categoryIds, String minPrice, String maxPrice) throws SQLException {
        Connection con = DBContext.getConnection();
        Statement stm = con.createStatement();
        String query = "select count(*) as numOfProduct from Product where 1=1 ";
        if (categoryIds != null) {
            StringJoiner sj = new StringJoiner(",");
            for (String categoryId : categoryIds) {
                sj.add("'" + categoryId + "'");
            }
            query = query + "AND categoryId IN (" + sj.toString() + ")";

        }
        if (minPrice != null && !minPrice.isEmpty()) {
            query = query + "AND price >= " + minPrice;
        }

        if (maxPrice != null && !maxPrice.isEmpty()) {
            query = query + " AND price <= " + maxPrice;
        }
        System.out.println(query);
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            return rs.getInt("numOfProduct");
        }
        con.close();
        return 0;
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

    public Product getProductByID(String id) {
        List<Product> list = new ArrayList<>();
        String sql = "select id , name, description, image, price, discount,categoryId from product WHERE id=?";
        try {
            Connection con = DBContext.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                return (new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getFloat(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Product> getListByFilter(String[] categoryIds, String minPrice, String maxPrice) throws SQLException {
        List<Product> list = null;
        Connection con = DBContext.getConnection();
        Statement stm = con.createStatement();
        String query = "select * from Product where 1=1 ";
        if (categoryIds != null) {
            StringJoiner sj = new StringJoiner(",");
            for (String categoryId : categoryIds) {
                sj.add("'" + categoryId + "'");
            }
            query = query + "AND categoryId IN (" + sj.toString() + ")";

        }
        if (minPrice != null && !minPrice.isEmpty()) {
            query = query + "AND price >= " + minPrice;
        }

        if (maxPrice != null && !maxPrice.isEmpty()) {
            query = query + " AND price <= " + maxPrice;
        }
        System.out.println(query);
        ResultSet rs = stm.executeQuery(query);
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

    public List<Product> pagingProduct(int index, String[] categoryIds, String minPrice, String maxPrice) throws SQLException {
        List<Product> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng statement
        String query = " ";
        if (categoryIds != null) {
            StringJoiner sj = new StringJoiner(",");
            for (String categoryId : categoryIds) {
                sj.add("'" + categoryId + "'");
            }
            query = query + "AND categoryId IN (" + sj.toString() + ") ";

        }
        if (minPrice != null && !minPrice.isEmpty()) {
            query = query + "AND price >= " + minPrice;
        }

        if (maxPrice != null && !maxPrice.isEmpty()) {
            query = query + " AND price <= " + maxPrice;
        }

        System.out.println(query);
        PreparedStatement stm = con.prepareStatement("select * from product where 1=1" + query + "\n order by id\n"
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

    public Product read_1(int id) throws SQLException {
        Product pr = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from Product where id = ?");
        //Thực thi lệnh SELECT
        stm.setInt(1, id);
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

    public void update(OrderDetail ord) throws SQLException {
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

    public void update_1(Product product) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("update Product set name = ?, description = ?, image = ?, price = ?, discount =?, categoryId = ? where id = ?");
        stm.setString(1, product.getName());
        stm.setNString(2, product.getDescription());
        stm.setString(3, product.getImage());
        stm.setFloat(4, product.getPrice());
        stm.setFloat(5, product.getDiscount());
        stm.setString(6, product.getCategoryID());
        stm.setInt(7, product.getId());
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

    public void delete_1(String id) throws SQLException {
        Connection con = DBContext.getConnection();
        // Prepare -> thay đổi dữ liệu
        PreparedStatement stm = con.prepareStatement("delete from product where id = ?");
        stm.setString(1, id);
        int count = stm.executeUpdate();
        con.close();
    }
    
    public List<Product> searchFullType(String searchInput) throws SQLException {

        List<Product> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();

        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from product where id like ? or name like ? or description like ? or image like ? or price like ? or discount like ? or categoryId like ?");
        list = new ArrayList<>();
        //Thực thi lệnsh SELECT
        stm.setString(1, "%" + searchInput + "%");
        stm.setString(2, "%" + searchInput + "%");
        stm.setString(3, "%" + searchInput + "%");
        stm.setString(4, "%" + searchInput + "%");
        stm.setString(5, "%" + searchInput + "%");
        stm.setString(6, "%" + searchInput + "%");
        stm.setString(7, "%" + searchInput + "%");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
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
    
    public List<Product> pagingRead(int indexPage) throws SQLException {
        List<Product> list = null;
        //Tạo connection để kết nối vào DBMS
        Connection con = DBContext.getConnection();

        //Tạo đối tượng PreparedStatement
        PreparedStatement stm = con.prepareStatement("select * from product order by id offset ? rows fetch next 10 rows only");
        list = new ArrayList<>();
        //Thực thi lệnsh SELECT
        stm.setInt(1, (indexPage - 1) * 10);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
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
