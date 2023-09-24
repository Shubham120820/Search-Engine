package org.example;

import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Indexer {
    // Static connection instance for the database
    static Connection connection = null;

    // Constructor to index a web page
    Indexer(Document document, String url) {
        // Extract information from the HTML document
        String title = document.title();
        String link = url;
        String text = document.text();

        try {
            // Get a database connection using the DatabaseConnection class
            connection = DatabaseConnection.getConnection();

            // Create a prepared statement for inserting data into the 'pages' table
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO pages VALUES(?,?,?);");

            // Set the values for the prepared statement
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, link);
            preparedStatement.setString(3, text);

            // Execute the SQL insert statement
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            // Handle any SQL exceptions that may occur during indexing
            sqlException.printStackTrace();
    }
  }
}