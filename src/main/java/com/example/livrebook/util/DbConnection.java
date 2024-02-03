package com.example.livrebook.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/livrebook";

    private static DbConnection instance;
    private Connection cnx;

    private DbConnection() {
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            System.err.println("Error establishing connection: " + e.getMessage());
        }
    }

    public static synchronized DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
