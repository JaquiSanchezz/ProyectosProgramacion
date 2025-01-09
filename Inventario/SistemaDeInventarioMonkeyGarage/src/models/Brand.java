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
public class Brand {
    
    
    private int id;
    private String name;

    public Brand() {
    }

    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Brand(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public boolean createBrand(Brand brand) {

        boolean aux = false;
        String consulta = "INSERT INTO brands (name)  "
                + "VALUES (?  )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, brand.getName());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateBrand(Brand brand){
          boolean aux = false;
        String consulta = "UPDATE brands SET name = ?WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, brand.getName());
            sentence.setInt(2, brand.getId());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;
        
    }
    
    public boolean deleteBrand(int id){
          boolean aux = false;
        String consulta = "UPDATE brands SET deleted = true WHERE id = ? ";

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
    
    public ArrayList<Brand> getAllBrands() {
        ArrayList<Brand> listBrands = new ArrayList<Brand>();
        String query = "SELECT * FROM brands WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                listBrands.add(brand);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Brand.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listBrands;
    }
    
     public ArrayList<Brand> searchBrands(String search) {
        ArrayList<Brand> listBrands = new ArrayList<Brand>();
        String query = "SELECT * FROM brands WHERE name LIKE '%"+search+"%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                listBrands.add(brand);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Brand.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listBrands;
    }
    

   

}
