package com.novatech.taskmanagement.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class TaskController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/task-list.jsp").forward(request, response);
    }
}
