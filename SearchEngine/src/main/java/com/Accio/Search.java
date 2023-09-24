package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Get the keyword parameter from the request
        String keyword = request.getParameter("keyword");
        System.out.println(keyword);

        try {
            // Establish a database connection
            Connection connection = DatabaseConnection.getConnection();

            // Add keyword into the history table
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO history VALUES(?, ?);");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/SearchEngine/Search?keyword=" + keyword);
            preparedStatement.executeUpdate();

            // Get search results from the pages table
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT pageTitle, pageLink, (LENGTH(LOWER(pageText))-LENGTH(REPLACE(LOWER(pageText), '" + keyword + "', \"\")))/LENGTH('" + keyword + "') AS countoccurrence FROM pages ORDER BY countoccurrence DESC LIMIT 30;");

            // Create an ArrayList to store search results
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setPageTitle(resultSet.getString("pageTitle"));
                searchResult.setPageLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }

            // Print search results to the server console
            for (SearchResult result : results) {
                System.out.println(result.getPageLink() + " " + result.getPageTitle() + "\n");
            }

            // Set search results as an attribute for the request
            request.setAttribute("results", results);

            // Forward the request to the "search.jsp" view
            request.getRequestDispatcher("/search.jsp").forward(request, response);

            // Set the response content type to HTML
            response.setContentType("text/html");

            // Get a PrintWriter to write the response content
            PrintWriter out = response.getWriter();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ServletException servletException) {
            servletException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}