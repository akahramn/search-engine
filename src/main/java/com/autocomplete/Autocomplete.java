package com.autocomplete;

import com.server.TomcatHostLifecycleListener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/autocomplete")
public class Autocomplete extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<String> result = TomcatHostLifecycleListener.trie.advanceSearch(keyword);
        System.out.println(result);

    }

}
