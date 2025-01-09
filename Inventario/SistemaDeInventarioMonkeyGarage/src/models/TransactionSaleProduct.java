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
public class TransactionSaleProduct {


    private int id;
    private int transactionId;
    private int productId;
    private String currentPrice;
    private int quantity;

    public TransactionSaleProduct() {
    }

    public TransactionSaleProduct(int transactionId, int productId, String currentPrice, int quantity) {
        this.transactionId = transactionId;
        this.productId = productId;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
    }

    public TransactionSaleProduct(int id, int transactionId, int productId, String currentPrice, int quantity) {
        this.id = id;
        this.transactionId = transactionId;
        this.productId = productId;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public boolean createTransactionSalesProducts(TransactionSaleProduct transaction) {

        boolean aux = false;
        String consulta = "INSERT INTO transactions_sales_products (transactionId, productId, currentPrice, quantity)  "
                + "VALUES (?, ?, ?, ? )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, transaction.getTransactionId());
            sentence.setInt(2, transaction.getProductId());
            sentence.setString(3, transaction.getCurrentPrice());
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

    public boolean updateTransactionSalesProducts(TransactionSaleProduct transaction) {
        boolean aux = false;
        String consulta = "UPDATE transactions_sales_products SET transactionId = ?, productId = ?, currentPrice = ?, quantity = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, transaction.getTransactionId());
            sentence.setInt(2, transaction.getProductId());
            sentence.setString(3, transaction.getCurrentPrice());
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

    public boolean deleteTransactionSalesProducts(int id) {
        boolean aux = false;
        String consulta = "UPDATE transactions_sales_products SET deleted = true WHERE id = ? ";

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

    public ArrayList<TransactionSaleProduct> getAllTransactionSalesProducts() {
        ArrayList<TransactionSaleProduct> listTransactions = new ArrayList<TransactionSaleProduct>();
        String query = "SELECT * FROM transactions_sales_products WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionSaleProduct transaction = new TransactionSaleProduct(
                        rs.getInt("id"),
                        rs.getInt("transactionId"),
                        rs.getInt("productId"),
                        rs.getString("currentPrice"),
                        rs.getInt("quantity")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionSaleProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }

    public ArrayList<TransactionSaleProduct> searchTransactionSalesProducts(String search) {
        ArrayList<TransactionSaleProduct> listTransactions = new ArrayList<TransactionSaleProduct>();
        String query = "SELECT * FROM transactions_sales_products WHERE name LIKE '%" + search + "%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionSaleProduct transaction = new TransactionSaleProduct(
                        rs.getInt("id"),
                        rs.getInt("transactionId"),
                        rs.getInt("productId"),
                        rs.getString("currentPrice"),
                        rs.getInt("quantity")
                );
                listTransactions.add(transaction);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TransactionSaleProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTransactions;
    }



}
