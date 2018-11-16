package com.step.forum.servlet;

import com.step.forum.dao.TopicDaoImpl;
import com.step.forum.model.Topic;
import com.step.forum.service.TopicService;
import com.step.forum.service.TopicServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "")
public class HomeServlet extends HttpServlet {

    private TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Topic> topicList = topicService.getAllTopic();
        request.setAttribute("topicList", topicList);

        String message = (String) request.getSession().getAttribute("message");

        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }

        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);

    }
}