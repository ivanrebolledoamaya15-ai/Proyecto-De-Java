/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author MR CHAMAN
 */
public class ConexionBD {
    
    String  url = "jdbc:mysql://localhost/gestion_torneos";
    String username = "root";
    String password = "cbn2016";

    public ConexionBD() {
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public java.sql.Connection getConexion() throws java.sql.SQLException {
        return java.sql.DriverManager.getConnection(url, username, password);
    }
    
}
