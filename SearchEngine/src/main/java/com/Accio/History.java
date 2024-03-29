package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/History")
public class History extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Establish a database connection
            Connection connection = DatabaseConnection.getConnection();

            // Execute an SQL query to retrieve history records
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM history;");

            // Create an ArrayList to store history results
            ArrayList<HistoryResult> results = new ArrayList<HistoryResult>();
            while (resultSet.next()) {
                HistoryResult historyResult = new HistoryResult();
                historyResult.setKeyword(resultSet.getString("keyword"));
                historyResult.setLink(resultSet.getString("link"));
                results.add(historyResult);
            }

            // Set history results as an attribute for the request
            request.setAttribute("results", results);

            // Forward the request to the "history.jsp" view
            request.getRequestDispatcher("/history.jsp").forward(request, response);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ServletException servletException) {
            servletException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}