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
public class User {

    private int id;
    private String name;
    private String last_name;
    private String email;
    private String username;
    private String password;
    private String created_at;
    private String updated_at;

    public User() {
    }

    public User(int id, String name, String last_name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(int id, String name, String last_name, String email, String username) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
    }

    public User(int id, String name, String last_name, String email, String username, String password, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(String name, String last_name, String email, String username, String password) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public boolean loginUser(User user) {

        String query = "SELECT * FROM users where email = ? AND password = ? AND deleted = false";
        boolean checkUser = false;

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement sentence = conn.prepareStatement(query);
            sentence.setString(1, user.getEmail());
            sentence.setString(2, Security.cifrar(user.getPassword()));
            ResultSet rs = sentence.executeQuery();

            if (rs.next()) {
                checkUser = true;
            }
        } catch (SQLException e) {

        }

        return checkUser;

    }

    public boolean registerUser(User user) {

        boolean aux = false;
        String consulta = "INSERT INTO users (name, last_name, email, username, password )  "
                + "VALUES (?, ? ,? ,?, ? )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, user.getName());
            sentence.setString(2, user.getLast_name());
            sentence.setString(3, user.getEmail());
            sentence.setString(4, user.getUsername());
            sentence.setString(5, Security.cifrar(user.getPassword()));
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateUserWithOutPassword(User user) {

        boolean aux = false;
        String consulta = "UPDATE users SET name = ?, last_name = ?, email = ?, username = ? WHERE id = ?";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, user.getName());
            sentence.setString(2, user.getLast_name());
            sentence.setString(3, user.getEmail());
            sentence.setString(4, user.getUsername());
            sentence.setInt(5, user.getId());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateUserAll(User user) {

        boolean aux = false;
        String consulta = "UPDATE users SET name = ?, last_name = ?, email = ?, username = ?, password = ? WHERE id = ?";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setString(1, user.getName());
            sentence.setString(2, user.getLast_name());
            sentence.setString(3, user.getEmail());
            sentence.setString(4, user.getUsername());
            sentence.setString(5, Security.cifrar(user.getPassword()));
            sentence.setInt(6, user.getId());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean deleteUser(int id) {
        boolean aux = false;
        String consulta = "UPDATE users SET deleted = true WHERE id = ? ";

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

    public User getData(String email) {
        User user = new User();

        String query = "SELECT * FROM users where email = ?";
        boolean checkUser = false;

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement sentence = conn.prepareStatement(query);
            sentence.setString(1, email);
            ResultSet rs = sentence.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLast_name(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {

        }

        return user;

    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> listUser = new ArrayList<User>();
        String query = "SELECT * FROM users WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"));
                listUser.add(user);

            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listUser;
    }

    public ArrayList<User> searchUsers(String search) {
        ArrayList<User> listUser = new ArrayList<User>();
        String query = "SELECT * FROM users WHERE name LIKE '%" + search + "%' OR last_name LIKE '%" + search + "%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"));
                listUser.add(user);

            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listUser;
    }

}
