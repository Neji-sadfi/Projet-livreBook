package com.example.livrebook.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/livrebook";

    public static DbConnection instance;
    private Connection cnx;

    private DbConnection() {
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connection établie !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        };
    }

    public static DbConnection getInstance(){
        return instance == null ? new DbConnection() : instance;
    }

    public Connection getCnx(){
        return cnx;
    }
}