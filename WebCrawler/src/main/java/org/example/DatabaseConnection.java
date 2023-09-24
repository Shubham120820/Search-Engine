package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Static connection instance for database
    static Connection connection = null;

    // Get a database connection using default user, password, and database name
    public static Connection getConnection() {
        // If a connection is already established, return it
        if (connection != null) {
            return connection;
        }

        // Default database connection parameters
        String user = "root";
        String pwd = "Shubham@1208";
        String db = "searchengineapp";

        // Call the overloaded getConnection method to establish a connection
        return getConnection(user, pwd, db);
    }

    // Get a database connection with custom user, password, and database name
    private static Connection getConnection(String user, String pwd, String db) {
        try {
            // Load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection to the database using the provided parameters
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
        } catch (SQLException | ClassNotFoundException sqlException) {
            // Handle any SQL or class loading exceptions that may occur
            sqlException.printStackTrace();
        }

        // Return the established database connection
        return connection;
   }
}
