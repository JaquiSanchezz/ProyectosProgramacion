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
 * @author Jaqui Sanchez
 */
public class CategoryTool {
    private int id;
    private String name;
    private String description;


    public CategoryTool() {
    }

    public CategoryTool(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CategoryTool(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean createCategoryTool(CategoryTool categoryTool) {

        boolean aux = false;
        String consulta = "INSERT INTO categorie_tools (name, description)  "
                + "VALUES (?, ?  )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, categoryTool.getName());
            sentence.setString(2, categoryTool.getDescription());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateCategoryTool(CategoryTool categoryTool) {
        boolean aux = false;
        String consulta = "UPDATE categorie_tools SET name = ?, description = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, categoryTool.getName());
            sentence.setString(2, categoryTool.getDescription());
            sentence.setInt(3, categoryTool.getId());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean deleteCategoryTool(int id) {
        boolean aux = false;
        String consulta = "UPDATE categorie_tools SET deleted = true WHERE id = ? ";

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

    public ArrayList<CategoryTool> getAllCategoryTool() {
        ArrayList<CategoryTool> listCategoryTools = new ArrayList<CategoryTool>();
        String query = "SELECT * FROM categorie_tools WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CategoryTool categoryTool = new CategoryTool(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
                listCategoryTools.add(categoryTool);

            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoryTool.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listCategoryTools;
    }

    public ArrayList<CategoryTool> searchCategoryTools(String search) {
        ArrayList<CategoryTool> listCategoryTools = new ArrayList<CategoryTool>();
        String query = "SELECT * FROM categorie_tools WHERE name LIKE '%" + search + "%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CategoryTool categoryTool = new CategoryTool(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
                listCategoryTools.add(categoryTool);

            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoryTool.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listCategoryTools;
    }


}
