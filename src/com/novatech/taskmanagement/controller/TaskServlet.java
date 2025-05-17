package com.novatech.taskmanagement.controller;

import com.novatech.taskmanagement.dao.TaskDAO;
import com.novatech.taskmanagement.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.List;

@WebServlet(name = "taskList", urlPatterns = {"/tasks"}, loadOnStartup = 1)
public class TaskServlet extends HttpServlet {

    private TaskDAO taskDAO;

    public void init() {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        try {
            if (action == null) {
                listTasks(request, response);
            } else {
                switch (action) {
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteTask(request, response);
                        break;
                    default:
                        listTasks(request, response);
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    private void listTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Task> tasks = taskDAO.getAllTasks();
        request.setAttribute("taskList", tasks);
        request.getRequestDispatcher("task-list.jsp").forward(request, response);
    }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        Task task = taskDAO.getTaskByID(id);
        request.setAttribute("task", task);
        request.getRequestDispatcher("task-form.jsp").forward(request, response);
    }


    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        taskDAO.deleteTask(id);
        response.sendRedirect("tasks");
    }

}