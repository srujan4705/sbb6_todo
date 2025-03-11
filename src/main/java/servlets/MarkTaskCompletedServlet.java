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


@WebServlet("/MarkTaskCompletedServlet")
public class MarkTaskCompletedServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    ServletContext context = getServletContext();

    try {
        String regIdStr = request.getParameter("regId");
        String taskIdStr = request.getParameter("taskId");

        // Check if parameters are null or empty
        if (regIdStr == null || regIdStr.isEmpty() || taskIdStr == null || taskIdStr.isEmpty()) {
            out.println("Error: Missing parameters (regId or taskId)");
            return;
        }

        int regId = Integer.parseInt(regIdStr);
        int taskId = Integer.parseInt(taskIdStr);

        ToDoDAO dao = ToDoDAOImpl.getInstance();
        if (dao == null) {
            out.println("Error: DAO instance is null. Check DB connection.");
            return;
        }

        boolean flag = dao.markTaskCompleted(regId, taskId);
        if (flag) {
            context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
        } else {
            out.println("Transaction Failed, Task not marked as completed.");
        }
    } catch (NumberFormatException e) {
        out.println("Error: Invalid number format for regId or taskId.");
        e.printStackTrace();
    } catch (Exception e) {
        out.println("Unexpected error occurred.");
        e.printStackTrace();
    }
  }
}
