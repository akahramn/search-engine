package com.server;

import com.server.history.HistoryService;
import com.server.page.PageService;
import com.server.page.SearchResult;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/Search")
@RequiredArgsConstructor
public class Search extends HttpServlet {
    HistoryService historyService = new HistoryService();
    PageService pageService = new PageService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        try{
            //check keyword exist if not save if exist update frequency
            historyService.saveHistory(keyword);
            //get results from pages table
            List<SearchResult> results = pageService.getPages(keyword);

            request.setAttribute("results", results);
            request.getRequestDispatcher("/search.jsp").forward(request, response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        } catch (ServletException | IOException sqlException){
            sqlException.printStackTrace();
        }
    }
}
