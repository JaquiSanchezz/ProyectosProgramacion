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
public class TransactionProvidedService {

    private int id;
    private int clientId;
    private int quantity;
    private String total;
    private String status;
    private String paymentStatus;
    private String paymentType;
    private String created_at;
    private String updated_at;

    public TransactionProvidedService() {
    }


    public TransactionProvidedService(int clientId, int quantity, String total, String status, String paymentStatus, String paymentType) {
        this.clientId = clientId;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
    }

    public TransactionProvidedService(int id, int clientId, int quantity, String total, String status, String paymentStatus, String paymentType, String created_at, String updated_at) {
        this.id = id;
        this.clientId = clientId;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public boolean createTransaction(TransactionProvidedService transaction) {

        boolean aux = false;
        String consulta = "INSERT INTO transactions_provided_services (clientId, quantity, total, status, paymentStatus, paymentType)  "
                + "VALUES (?, ?, ?, ?, ?, ? )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, transaction.getClientId());
            sentence.setInt(2, transaction.getQuantity());
            sentence.setString(3, transaction.getTotal());
            sentence.setString(4, transaction.getStatus());
            sentence.setString(5, transaction.getPaymentStatus());
            sentence.setString(6, transaction.getPaymentType());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateTransaction(TransactionProvidedService transaction) {
        boolean aux = false;
        String consulta = "UPDATE transactions_provided_services SET clientId = ?, quantity = ?, total = ?, status = ?, paymentStatus = ?, paymentType = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, transaction.getClientId());
            sentence.setInt(2, transaction.getQuantity());
            sentence.setString(3, transaction.getTotal());
            sentence.setString(4, transaction.getStatus());
            sentence.setString(5, transaction.getPaymentStatus());
            sentence.setString(6, transaction.getPaymentType());
            sentence.setInt(7, transaction.getId());
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
        String consulta = "UPDATE transactions_provided_services SET deleted = true WHERE id = ? ";

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

    public ArrayList<TransactionProvidedService> getAllTransactions() {
        ArrayList<TransactionProvidedService> listTransactions = new ArrayList<TransactionProvidedService>();
        String query = "SELECT * FROM transactions_provided_services WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionProvidedService transaction = new TransactionProvidedService(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getInt("quantity"),
                        rs.getString("total"),
                        rs.getString("status"),
                        rs.getString("paymentStatus"),
                        rs.getString("paymentType"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionProvidedService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }

    public TransactionProvidedService getLastTransaction() {
        TransactionProvidedService transactionProvidedService = new TransactionProvidedService();
        String query = "SELECT * FROM transactions_provided_services WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionProvidedService transaction = new TransactionProvidedService(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getInt("quantity"),
                        rs.getString("total"),
                        rs.getString("status"),
                        rs.getString("paymentStatus"),
                        rs.getString("paymentType"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                transactionProvidedService = transaction;

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionProvidedService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return transactionProvidedService;
    }

    public ArrayList<TransactionProvidedService> searchTransactions(String search) {
        ArrayList<TransactionProvidedService> listTransactions = new ArrayList<TransactionProvidedService>();
        String query = "SELECT * FROM transactions_provided_services WHERE name LIKE '%" + search + "%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionProvidedService transaction = new TransactionProvidedService(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getInt("quantity"),
                        rs.getString("total"),
                        rs.getString("status"),
                        rs.getString("paymentStatus"),
                        rs.getString("paymentType"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionProvidedService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }


}
