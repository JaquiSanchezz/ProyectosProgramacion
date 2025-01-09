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
import utils.Security;

/**
 *
 * @author Jaqui Sanchez
 */
public class Client {

    private int id;
    private String name;
    private String last_name;
    private String email;
    private String phone;

    public Client() {
    }

    public Client(int id) {
        this.id = id;
    }
    
    

    public Client(int id, String name, String last_name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
    }

    public Client(String name, String last_name, String email, String phone) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean createClient(Client client) {

        boolean aux = false;
        String consulta = "INSERT INTO clients (name, last_name, email, phone )  "
                + "VALUES (?, ? ,? ,? )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, client.getName());
            sentence.setString(2, client.getLast_name());
            sentence.setString(3, client.getEmail());
            sentence.setString(4, client.getPhone());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateClient(Client client){
          boolean aux = false;
        String consulta = "UPDATE clients SET name = ?, last_name = ?, email = ?, phone = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, client.getName());
            sentence.setString(2, client.getLast_name());
            sentence.setString(3, client.getEmail());
            sentence.setString(4, client.getPhone());
            sentence.setInt(5, client.getId());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;
        
    }
    
    public boolean deleteClient(Client client){
          boolean aux = false;
        String consulta = "UPDATE clients SET deleted = true WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

          
            sentence.setInt(1, client.getId());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;
        
    }
    
    public ArrayList<Client> getAllClients() {
        ArrayList<Client> listClients = new ArrayList<Client>();
        String query = "SELECT * FROM clients WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                listClients.add(client);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClients;
    }
    
     public ArrayList<Client> searchClients(String search) {
        ArrayList<Client> listClients = new ArrayList<Client>();
        String query = "SELECT * FROM clients WHERE name LIKE '%"+search+"%' OR last_name LIKE '%"+search+"%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                listClients.add(client);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClients;
    }
    
}
