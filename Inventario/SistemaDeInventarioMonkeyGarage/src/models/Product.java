/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaqui Sanchez
 */
public class Product {
    
    private int id;
    private int brandId;
    private String brandName;
    private String name;
    private String description;
    private String cost;
    private String selling_price;
    private int quantity;
    private String unit;

    public Product() {
    }

    public Product(int brandId, String name, String description, String cost, String selling_price, int quantity, String unit) {
        this.brandId = brandId;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.selling_price = selling_price;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Product(int id, int brandId, String brandName, String name, String description, String cost, String selling_price, int quantity, String unit) {
        this.id = id;
        this.brandId = brandId;
        this.brandName = brandName;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.selling_price = selling_price;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Product(int id, int brandId, String name, String description, String cost, String selling_price, int quantity, String unit) {
        this.id = id;
        this.brandId = brandId;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.selling_price = selling_price;
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean createProduct(Product product) {

        boolean aux = false;
        String consulta = "INSERT INTO products (brandId, name, description, cost, selling_price, quantity, unit)  "
                + "VALUES (?, ?,?, ?, ?, ?, ?  )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, product.getBrandId());
            sentence.setString(2, product.getName());
            sentence.setString(3, product.getDescription());
            sentence.setString(4, product.getCost());
            sentence.setString(5, product.getSelling_price());
            sentence.setInt(6, product.getQuantity());
            sentence.setString(7, product.getUnit());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateProduct(Product product){
        boolean aux = false;
        String consulta = "UPDATE products SET brandId = ?, name = ?, description = ?, cost = ?, selling_price = ?, quantity = ?, unit = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, product.getBrandId());
            sentence.setString(2, product.getName());
            sentence.setString(3, product.getDescription());
            sentence.setString(4, product.getCost());
            sentence.setString(5, product.getSelling_price());
            sentence.setInt(6, product.getQuantity());
            sentence.setString(7, product.getUnit());
            sentence.setInt(8, product.getBrandId());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean deleteProduct(int id){
        boolean aux = false;
        String consulta = "UPDATE products SET deleted = true WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);
            sentence.setInt(1, id);
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> listProducts = new ArrayList<Product>();
        String query = "SELECT products.id, products.brandId, products.name, products.description, products.cost, products.selling_price, products.cost, products.quantity, products.unit, products.deleted, brands.name as brandName  FROM products INNER JOIN brands ON products.brandId = brands.id WHERE products.deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandId"),
                        rs.getString("brandName"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("cost"),
                        rs.getString("selling_price"),
                        rs.getInt("quantity"),
                        rs.getString("unit")
                );
                listProducts.add(product);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listProducts;
    }

    public ArrayList<Product> searchProducts(String search) {
        ArrayList<Product> listProducts = new ArrayList<Product>();
        String query = "SELECT products.id, products.brandId, products.name, products.description, products.cost, products.selling_price, products.cost, products.quantity, products.unit, products.deleted, brands.name as brandName  FROM products INNER JOIN brands ON products.brandId = brands.id WHERE products.name LIKE '%"+search+"%' AND products.deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("brandId"),
                        rs.getString("brandName"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("cost"),
                        rs.getString("selling_price"),
                        rs.getInt("quantity"),
                        rs.getString("unit")
                );
                listProducts.add(product);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listProducts;
    }


}
