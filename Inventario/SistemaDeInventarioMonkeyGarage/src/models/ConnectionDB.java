/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Constants;

/**
 *
 * @author Jaqui Sanchez
 */
public class ConnectionDB {
    
     private final String base = "inventario";
    private final String user = "root";
    private final String password = "rodriguez";
    private final String url = "jdbc:mysql://"+new Constants().IPSERVER+":3306/"+base;
    private java.sql.Connection con = null;
    
    
    /***
     * Obtener la conexion a la base de datos
     * @return 
     */
    public java.sql.Connection getConnection(){
        
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            con = (java.sql.Connection) DriverManager.getConnection(this.url, this.user, this.password);
        }catch(SQLException e){
            System.err.println(e);
        }catch(Exception ex){
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            
        }
                
        return con;
        
    }
    
}
