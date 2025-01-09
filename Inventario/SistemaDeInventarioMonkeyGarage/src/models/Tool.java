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
public class Tool {

    private int id;
    private int categoryId;
    private String categoryName;
    private String name;
    private String description;
    private int quantity;
    private String status;

    public Tool() {
    }

    public Tool(int categoryId, String name, String description, int quantity, String status) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
    }

    public Tool(int id, int categoryId, String name, String description, int quantity, String status) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
    }

    public Tool(int id, int categoryId, String categoryName, String name, String description, int quantity, String status) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean createTool(Tool tool) {

        boolean aux = false;
        String consulta = "INSERT INTO inventory_tools (categoryId, name, description, quantity, status)  "
                + "VALUES (?, ?, ?, ?, ? )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, tool.getCategoryId());
            sentence.setString(2, tool.getName());
            sentence.setString(3, tool.getDescription());
            sentence.setInt(4, tool.getQuantity());
            sentence.setString(5, tool.getStatus());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateTool(Tool tool) {
        boolean aux = false;
        String consulta = "UPDATE inventory_tools SET categoryId = ?, name = ?, description = ?, quantity = ?, status = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, tool.getCategoryId());
            sentence.setString(2, tool.getName());
            sentence.setString(3, tool.getDescription());
            sentence.setInt(4, tool.getQuantity());
            sentence.setString(5, tool.getStatus());
            sentence.setInt(6, tool.getId());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean deleteTool(int id) {
        boolean aux = false;
        String consulta = "UPDATE inventory_tools SET deleted = true WHERE id = ? ";

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

    public ArrayList<Tool> getAllTools() {
        ArrayList<Tool> listTools = new ArrayList<Tool>();
        String query = "SELECT inventory_tools.id, inventory_tools.categoryId, inventory_tools.name, inventory_tools.description, inventory_tools.quantity, inventory_tools.status, inventory_tools.deleted, categorie_tools.name as categoryName FROM inventory_tools INNER JOIN categorie_tools ON inventory_tools.categoryId = categorie_tools.id WHERE inventory_tools.deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tool tool = new Tool(
                        rs.getInt("id"),
                        rs.getInt("categoryId"),
                        rs.getString("categoryName"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                );
                listTools.add(tool);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTools;
    }

    public ArrayList<Tool> searchTools(String search) {
        ArrayList<Tool> listTools = new ArrayList<Tool>();
        String query = "SELECT inventory_tools.id, inventory_tools.categoryId, inventory_tools.name, inventory_tools.description, inventory_tools.quantity, inventory_tools.status, inventory_tools.deleted, categorie_tools.name as categoryName FROM inventory_tools INNER JOIN categorie_tools ON inventory_tools.categoryId = categorie_tools.id WHERE inventory_tools.name LIKE '%" + search + "%' AND inventory_tools.deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tool tool = new Tool(
                        rs.getInt("id"),
                        rs.getInt("categoryId"),
                        rs.getString("categoryName"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                );
                listTools.add(tool);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTools;
    }


}
