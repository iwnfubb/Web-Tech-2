package swt.wt.lab2.servlet;

// Your Servlet implementation

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WTQuizServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException{
        RequestDispatcher view = request.getRequestDispatcher("question.html");
        view.forward(request, response);
    }
}