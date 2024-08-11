<%@page import = "java.util.ArrayList"%>
<%@ page import="com.server.SearchResult" %>

<html>
<head>
    <link rel = "stylesheet" type = "text/css" href = "styles.css">
</head>
<body>

<form action = "Search">
    <input type = "text" name = "keyword">
    <button type = "submit">Search</button>
</form>
<div class = "resultTable">
    <table border = 2>
        <tr>
            <td>Title</td>
            <td>Link</td>
        </tr>
        <%
            //Get results from search servlet
            ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
            //Iterate for every data present in results array
            for(SearchResult result : results){
        %>
        <tr>
            <td><%out.println(result.getTitle());%></td>
            <td><a href="<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>