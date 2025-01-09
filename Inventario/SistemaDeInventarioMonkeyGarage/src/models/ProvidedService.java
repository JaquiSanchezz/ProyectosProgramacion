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
public class ProvidedService {

    private int id;
    private int clientId;
    private String clientName;
    private String clientLastName;
    private int serviceId;
    private String serviceName;
    private int transactionProvidedServices;
    private String currentPrice;
    private String note;
    private String delivered_date;
    private String limit_date;

    public ProvidedService() {
    }

    public ProvidedService(int id, int clientId, int serviceId, int transactionProvidedServices, String currentPrice, String note, String delivered_date, String limit_date) {
        this.id = id;
        this.clientId = clientId;
        this.serviceId = serviceId;
        this.transactionProvidedServices = transactionProvidedServices;
        this.currentPrice = currentPrice;
        this.note = note;
        this.delivered_date = delivered_date;
        this.limit_date = limit_date;
    }

    public ProvidedService(String custom, int clientId, int serviceId, int transactionProvidedServices, String currentPrice, String note, String delivered_date, String limit_date) {
        this.clientId = clientId;
        this.serviceId = serviceId;
        this.transactionProvidedServices = transactionProvidedServices;
        this.currentPrice = currentPrice;
        this.note = note;
        this.delivered_date = delivered_date;
        this.limit_date = limit_date;
    }

    public ProvidedService(int id, int clientId, String clientName, String clientLastName, int serviceId, String serviceName, int transactionProvidedServices, String currentPrice, String note, String delivered_date, String limit_date) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientLastName = clientLastName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.transactionProvidedServices = transactionProvidedServices;
        this.currentPrice = currentPrice;
        this.note = note;
        this.delivered_date = delivered_date;
        this.limit_date = limit_date;
    }

    public ProvidedService(int id, int serviceId, int transactionProvidedServices, String currentPrice, String note, String delivered_date, String limit_date) {
        this.id = id;
        this.serviceId = serviceId;
        this.transactionProvidedServices = transactionProvidedServices;
        this.currentPrice = currentPrice;
        this.note = note;
        this.delivered_date = delivered_date;
        this.limit_date = limit_date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getTransactionProvidedServices() {
        return transactionProvidedServices;
    }

    public void setTransactionProvidedServices(int transactionProvidedServices) {
        this.transactionProvidedServices = transactionProvidedServices;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDelivered_date() {
        return delivered_date;
    }

    public void setDelivered_date(String delivered_date) {
        this.delivered_date = delivered_date;
    }

    public String getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(String limit_date) {
        this.limit_date = limit_date;
    }

    public boolean createProvidedService(ProvidedService providedService) {

        boolean aux = false;
        String consulta = "INSERT INTO provided_services (transactionProvidedServices, clientId, serviceId, currentPrice, note, delivered_date, limit_date)  "
                + "VALUES (?, ?,?, ?, ?, ?, ?  )";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, providedService.getTransactionProvidedServices());
            sentence.setInt(2, providedService.getClientId());
            sentence.setInt(3, providedService.getServiceId());
            sentence.setString(4, providedService.getCurrentPrice());
            sentence.setString(5, providedService.getNote());
            sentence.setString(6, providedService.getDelivered_date());
            sentence.setString(7, providedService.getLimit_date());


            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean updateProvidedService(ProvidedService providedService){
        boolean aux = false;
        String consulta = "UPDATE provided_services SET transactionProvidedServices = ?, serviceId = ?, currentPrice = ?, note = ?, delivered_date = ?, limit_date = ? WHERE id = ? ";

        try {
            Connection conn = (Connection) new ConnectionDB().getConnection();

            PreparedStatement sentence = conn.prepareStatement(consulta);

            sentence.setInt(1, providedService.getTransactionProvidedServices());
            sentence.setInt(2, providedService.getServiceId());
            sentence.setString(3, providedService.getCurrentPrice());
            sentence.setString(4, providedService.getNote());
            sentence.setString(5, providedService.getDelivered_date());
            sentence.setString(6, providedService.getLimit_date());
            sentence.setInt(7, providedService.getId());

            int insert = sentence.executeUpdate();

            if (insert == 1) {
                aux = true;
            }

        } catch (SQLException e) {
            System.err.println(e);

        }

        return aux;

    }

    public boolean deleteProvidedService(int id){
        boolean aux = false;
        String consulta = "UPDATE provided_services SET deleted = true WHERE id = ? ";

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

    public ArrayList<ProvidedService> getAllProvidedServices() {
        ArrayList<ProvidedService> listProvidedServices = new ArrayList<ProvidedService>();
        String query = "SELECT * FROM provided_services WHERE deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProvidedService providedService = new ProvidedService(
                        rs.getInt("transactionProvidedServices"),
                        rs.getInt("clientId"),
                        rs.getInt("serviceId"),
                        rs.getString("currentPrice"),
                        rs.getString("note"),
                        rs.getString("delivered_date"),
                        rs.getString("limit_date")
                );
                listProvidedServices.add(providedService);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProvidedService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listProvidedServices;
    }

    public ArrayList<ProvidedService> getLastProvidedServices() {
        ArrayList<ProvidedService> listProvidedServices = new ArrayList<ProvidedService>();
        String query = "SELECT provided_services.id, provided_services.clientId, provided_services.serviceId, provided_services.transactionProvidedServices, provided_services.currentPrice, provided_services.note, provided_services.delivered_date, provided_services.limit_date, provided_services.deleted, services.name as serviceName, clients.name as clientName, clients.last_name as clientLastName FROM provided_services INNER JOIN services ON provided_services.serviceId = services.id INNER JOIN clients ON provided_services.clientId = clients.id WHERE provided_services.deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProvidedService providedService = new ProvidedService(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getString("clientName"),
                        rs.getString("clientLastName"),
                        rs.getInt("serviceId"),
                        rs.getString("serviceName"),
                        rs.getInt("transactionProvidedServices"),
                        rs.getString("currentPrice"),
                        rs.getString("note"),
                        rs.getString("delivered_date"),
                        rs.getString("limit_date")
                );
                listProvidedServices.add(providedService);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProvidedService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listProvidedServices;
    }

    public ArrayList<ProvidedService> searchProvidedServices(String search) {
        ArrayList<ProvidedService> listProvidedServices = new ArrayList<ProvidedService>();
        String query = "SELECT * FROM provided_services WHERE name LIKE '%"+search+"%' AND deleted = false";

        try {
            Connection conn = new ConnectionDB().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProvidedService providedService = new ProvidedService(
                        rs.getInt("transactionProvidedServices"),
                        rs.getInt("clientId"),
                        rs.getInt("serviceId"),
                        rs.getString("currentPrice"),
                        rs.getString("note"),
                        rs.getString("delivered_date"),
                        rs.getString("limit_date")
                        );
                listProvidedServices.add(providedService);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listProvidedServices;
    }


}
