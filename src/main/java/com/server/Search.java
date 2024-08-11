package com.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        System.out.println(keyword);
        try{
            Connection connection = DatabaseConnection.getConnection();
            //add keyword into history table
            //PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?, ?)");
            //preparedStatement.setString(1, keyword);
            //preparedStatement.setString(2, "http://localhost:8080/SearchEngineAccio/Search?keyword="+keyword);
            //preparedStatement.executeUpdate();

            //get results from pages table
            ResultSet resultSet = connection.createStatement().executeQuery("select title, link, (length(lower(text))-length(replace(lower(text), '" + keyword + "', \"\")))/length('" + keyword + "') as countoccurence from page order by countoccurence desc limit 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            while (resultSet.next()){
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("title"));
                searchResult.setLink(resultSet.getString("link"));
                results.add(searchResult);
            }
            for(SearchResult result:results){
                System.out.println(result.getLink()+" "+result.getTitle()+"\n");
            }
            request.setAttribute("results", results);
            request.getRequestDispatcher("/search.jsp").forward(request, response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            System.out.println(out.toString());
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        catch (ServletException servletException){
            servletException.printStackTrace();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
