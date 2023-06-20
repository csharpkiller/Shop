package org.example;

import java.sql.*;

public class SqlConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/productsbase";
    private static final String USERNAME = "rootroot";
    private static final String PASSWORD = "";
    private Connection connection;

    public void connect(){
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if(!connection.isClosed()){

            }
        } catch (SQLException e) {
            System.err.println("Error connection");
        }
    }

    public Connection getConnect(){
        return connection;
    }

    public void disconnect(){
        try {
            connection.close();

            if(connection.isClosed()){

            }
        } catch (SQLException e) {
            System.err.println("Error during closing");
        }
    }
}
