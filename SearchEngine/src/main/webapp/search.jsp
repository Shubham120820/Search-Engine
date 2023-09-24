<%@page import="java.util.ArrayList"%>
<%@page import="com.Accio.SearchResult"%>
<!DOCTYPE html>
<html>
<head>
    <!-- Include an external CSS stylesheet called 'styles.css' -->
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<!-- Main heading -->
<h1>Simple Search Engine</h1>

<!-- Search form with a text input and a submit button -->
<form action="Search">
    <!-- Text input field for entering keywords -->
    <input type="text" name="keyword">
    <!-- Submit button to initiate the search -->
    <button type="submit">Search</button>
</form>

<!-- History form with a submit button -->
<form action="History">
    <!-- Submit button to view search history -->
    <button type="submit">History</button>
</form>

<!-- Display search results in a table -->
<div class="resultTable">
    <table border="2">
        <tr>
            <td>Title</td>
            <td>Link</td>
        </tr>
        <!-- Retrieve and iterate through the search results -->
        <%
            // Get results from the search servlet
            ArrayList<SearchResult> results = (ArrayList<SearchResult>) request.getAttribute("results");
            // Iterate for every data present in the results array
            for (SearchResult result : results) {
        %>
        <tr>
            <td><%= result.getPageTitle() %></td>
            <td><a href="<%= result.getPageLink() %>"><%= result.getPageLink() %></a></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>