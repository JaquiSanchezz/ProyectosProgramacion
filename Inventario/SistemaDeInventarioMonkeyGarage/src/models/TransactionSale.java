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
public class TransactionSale {

    private int id;
    private int clientId;
    private String clientName;
    private String clientLastName;
    private int quantity;
    private String total;
    private String paymentStatus;
    private String paymentType;
    private String created_at;
    private String updated_at;


    public TransactionSale() {
    }

    public TransactionSale(int clientId, int quantity, String total, String paymentStatus, String paymentType) {
        this.clientId = clientId;
        this.quantity = quantity;
        this.total = total;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
    }

    public TransactionSale(int id, int clientId, String clientName, String clientLastName, int quantity, String total, String paymentStatus, String paymentType, String created_at) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientLastName = clientLastName;
        this.quantity = quantity;
        this.total = total;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.created_at = created_at;
    }

    public TransactionSale(int id, int clientId, int quantity, String total, String paymentStatus, String paymentType, String created_at, String updated_at) {
        this.id = id;
        this.clientId = clientId;
        this.quantity = quantity;
        this.total = total;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public boolean createTransaction(TransactionSale transaction) {

        boolean aux = false;
        String consulta = "INSERT INTO transactions_sales (clientId, quantity, total, paymentStatus, paymentType)  "
                + "VALUES (?, ?, ?, ?, ? )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, transaction.getClientId());
            sentence.setInt(2, transaction.getQuantity());
            sentence.setString(3, transaction.getTotal());
            sentence.setString(4, transaction.getPaymentStatus());
            sentence.setString(5, transaction.getPaymentType());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public TransactionSale getLastSale(){
        String query = "SELECT * FROM transactions_sales WHERE deleted = false ORDER BY ID DESC LIMIT 1";
        TransactionSale transactionSale = new TransactionSale();

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionSale transaction = new TransactionSale(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getInt("quantity"),
                        rs.getString("total"),
                        rs.getString("paymentStatus"),
                        rs.getString("paymentType"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                transactionSale = transaction;

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionSale.class.getName()).log(Level.SEVERE, null, ex);
        }

        return transactionSale;
    }

    public boolean updateTransaction(TransactionSale transaction) {
        boolean aux = false;
        String consulta = "UPDATE transactions_sales SET clientId = ?, quantity = ?, total = ?, paymentStatus = ?, paymentType = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, transaction.getClientId());
            sentence.setInt(2, transaction.getQuantity());
            sentence.setString(3, transaction.getTotal());
            sentence.setString(4, transaction.getPaymentStatus());
            sentence.setString(5, transaction.getPaymentType());
            sentence.setInt(6, transaction.getId());
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
        String consulta = "UPDATE transactions_sales SET deleted = true WHERE id = ? ";

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

    public ArrayList<TransactionSale> getAllTransactions() {
        ArrayList<TransactionSale> listTransactions = new ArrayList<TransactionSale>();
        String query = "SELECT * FROM transactions_sales WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionSale transaction = new TransactionSale(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getInt("quantity"),
                        rs.getString("total"),
                        rs.getString("paymentStatus"),
                        rs.getString("paymentType"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionSale.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }

    public ArrayList<TransactionSale> getLastTransactions() {
        ArrayList<TransactionSale> listTransactions = new ArrayList<TransactionSale>();
        String query = "SELECT transactions_sales.id, transactions_sales.clientId, transactions_sales.quantity, transactions_sales.total, transactions_sales.paymentStatus, transactions_sales.paymentType,transactions_sales.deleted, transactions_sales.created_at, clients.name as clientName, clients.last_name as clientLastName FROM transactions_sales INNER JOIN clients ON transactions_sales.clientId = clients.id  WHERE MONTH(transactions_sales.created_at) = MONTH(CURRENT_TIMESTAMP) AND YEAR(transactions_sales.created_at) = YEAR(CURRENT_TIMESTAMP) AND transactions_sales.deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionSale transaction = new TransactionSale(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getString("clientName"),
                        rs.getString("clientLastName"),
                        rs.getInt("quantity"),
                        rs.getString("total"),
                        rs.getString("paymentStatus"),
                        rs.getString("paymentType"),
                        rs.getString("created_at")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionSale.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }

    public ArrayList<TransactionSale> searchTransactions(String search) {
        ArrayList<TransactionSale> listTransactions = new ArrayList<TransactionSale>();
        String query = "SELECT * FROM transactions_sales WHERE name LIKE '%" + search + "%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionSale transaction = new TransactionSale(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getInt("quantity"),
                        rs.getString("total"),
                        rs.getString("paymentStatus"),
                        rs.getString("paymentType"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionSale.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }




}
