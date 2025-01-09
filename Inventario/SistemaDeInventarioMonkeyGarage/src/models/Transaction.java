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
public class Transaction {

    private int id;
    private int modelId;
    private String type;
    private String model;
    private int quantity;
    private String created_at;
    private String updated_at;


    public Transaction() {
    }

    public Transaction(int modelId, String type, String model, int quantity, String created_at, String updated_at) {
        this.modelId = modelId;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Transaction(int modelId, String type, String model, int quantity) {
        this.modelId = modelId;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
    }

    public Transaction(int id, int modelId, String type, String model, int quantity) {
        this.id = id;
        this.modelId = modelId;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
    }

    public Transaction(int id, int modelId, String type, String model, int quantity, String created_at, String updated_at) {
        this.id = id;
        this.modelId = modelId;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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


    public boolean createTransaction(Transaction transaction) {

        boolean aux = false;
        String consulta = "INSERT INTO transactions (modelId, type, model, quantity)  "
                + "VALUES (?, ?, ?, ? )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, transaction.getModelId());
            sentence.setString(2, transaction.getType());
            sentence.setString(3, transaction.getModel());
            sentence.setInt(4, transaction.getQuantity());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateTransaction(Transaction transaction) {
        boolean aux = false;
        String consulta = "UPDATE transactions SET modelId = ?, type = ?, model = ?, quantity = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, transaction.getModelId());
            sentence.setString(2, transaction.getType());
            sentence.setString(3, transaction.getModel());
            sentence.setInt(4, transaction.getQuantity());
            sentence.setInt(5, transaction.getId());
            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean deleteTransaction(int id) {
        boolean aux = false;
        String consulta = "UPDATE transactions SET deleted = true WHERE id = ? ";

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

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> listTransactions = new ArrayList<Transaction>();
        String query = "SELECT * FROM transactions WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("modelId"),
                        rs.getString("type"),
                        rs.getString("model"),
                        rs.getInt("quantity"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }

    public ArrayList<Transaction> getAllMonthTransactions(int modelId, String type) {
        ArrayList<Transaction> listTransactions = new ArrayList<Transaction>();
        String query = "SELECT * FROM transactions WHERE MONTH(created_at) = MONTH(CURRENT_TIMESTAMP) AND YEAR(created_at) = YEAR(CURRENT_TIMESTAMP) AND modelId = ? AND model = ?";
        System.out.println(query);
        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,modelId);
            ps.setString(2,type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("modelId"),
                        rs.getString("type"),
                        rs.getString("model"),
                        rs.getInt("quantity"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }

    public ArrayList<Transaction> searchTransactions(String search) {
        ArrayList<Transaction> listTransactions = new ArrayList<Transaction>();
        String query = "SELECT * FROM transactions WHERE name LIKE '%" + search + "%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("modelId"),
                        rs.getString("type"),
                        rs.getString("model"),
                        rs.getInt("quantity"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }



}
