package com.Accio;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    static Connection connection = null;

    // Get a database connection with default values
    public static Connection getConnection() {
        // If a connection is already established, return it
        if (connection != null) {
            return connection;
        }

        String db = "searchengineapp"; // Database name
        String user = "root";          // Database user
        String pwd = "Shubham@1208";      // Database password

        // Call the overloaded getConnection method to establish a connection
        return getConnection(db, user, pwd);
    }

    // Get a database connection with custom database, user, and password
    private static Connection getConnection(String db, String user, String pwd) {
        try {
            // Load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection to the database using the provided parameters
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
        } catch (Exception exception) {
            // Handle any exceptions that may occur during database connection
            exception.printStackTrace();
        }

        // Return the established database connection
        return connection;
    }
}