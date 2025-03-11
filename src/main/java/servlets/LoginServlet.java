package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ToDoDAO;
import dao.ToDoDAOImpl;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    ServletContext ctxt = getServletContext();
    HttpSession session = request.getSession();

    // Get email & password from request
    String email = request.getParameter("email").trim();
    String pass = request.getParameter("pass").trim();

    // Check if email or password is empty
    if (email.isEmpty() || pass.isEmpty()) {
      request.setAttribute("loginError", "Email/Password cannot be empty.");
      ctxt.getRequestDispatcher("/Login.jsp").forward(request, response);
      return;
    }

    // Validate user login
    ToDoDAO dao = ToDoDAOImpl.getInstance();
    int regId = dao.login(email, pass);

    if (regId > 0) {
      session.setAttribute("regId", regId);
      ctxt.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
    } else {
      request.setAttribute("loginError", "Invalid Email or Password.");
      ctxt.getRequestDispatcher("/Login.jsp").forward(request, response);
    }
  }
}
