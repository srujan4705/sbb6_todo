<%@page import="beans.Task"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Task Manager</title>
    <style>
        /* General Styles */
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: #333;
            text-align: center;
            margin: 0;
            padding: 20px;
        }

        /* Welcome Message */
        .welcome {
            text-align: right;
            margin-bottom: 20px;
            font-size: 16px;
            color: white;
        }
        
        .welcome a {
            color: #ff4d4d;
            text-decoration: none;
            font-weight: bold;
            transition: 0.3s;
        }

        .welcome a:hover {
            text-decoration: underline;
        }

        /* Form Container */
        .form-container {
            width: 400px;
            margin: 20px auto;
            padding: 20px;
            background: white;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
        }

        .form-container h2 {
            color: #007bff;
        }

        .input-group {
            text-align: left;
            margin-bottom: 15px;
        }

        .input-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .input-group input,
        .input-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            outline: none;
        }

        .input-group input:focus,
        .input-group select:focus {
            border-color: #667eea;
            box-shadow: 0px 0px 5px rgba(102, 126, 234, 0.5);
        }

        /* Buttons */
        .btn {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: 0.3s;
            margin-top: 10px;
        }

        .btn-add {
            background-color: #28a745;
            color: white;
        }

        .btn-add:hover {
            background-color: #218838;
        }

        .btn-reset {
            background-color: #dc3545;
            color: white;
        }

        .btn-reset:hover {
            background-color: #b02a37;
        }

        /* Table Styling */
        table {
            width: 80%;
            margin: auto;
            background: white;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: center;
            font-size: 16px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        /* Task Completion Styling */
        tr.completed {
            background-color: #d4edda;
            color: #155724;
        }

        .completed td {
            text-decoration: line-through;
        }

        .status-completed {
            color: green;
            font-weight: bold;
        }

        .status-in-progress {
            color: orange;
            font-weight: bold;
        }

        .status-not-started {
            color: red;
            font-weight: bold;
        }

        /* Complete Task Button */
        .btn-complete {
            background-color: #ffc107;
            color: black;
            padding: 5px 10px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
        }

        .btn-complete:hover {
            background-color: #e0a800;
        }
    </style>
</head>
<body>

    <div class="welcome">
        <%
            Object obj = session.getAttribute("regId");
            Integer in = (Integer) obj;
            int regId = in.intValue();
            
            dao.ToDoDAO dao1 = dao.ToDoDAOImpl.getInstance();
            String flname = dao1.getFLNameByRegID(regId);
        %>
        Welcome <%=flname%>, <a href="./LogoutServlet">Logout</a>
    </div>

    <div class="form-container">
        <h2>Add a Task</h2>
        <form method="post" action="./AddTaskServlet">
            <div class="input-group">
                <label>Task Name:</label>
                <input type="text" name="taskName" required>
            </div>

            <div class="input-group">
                <label>Task Date:</label>
                <input type="date" name="taskDate" required>
            </div>

            <div class="input-group">
                <label>Task Status:</label>
                <select name="taskStatus">
                    <option value="1">Not Yet Started</option>
                    <option value="2">In Progress</option>
                    <option value="3">Completed</option>
                </select>
            </div>

            <button type="submit" class="btn btn-add">Add Task</button>
            <button type="reset" class="btn btn-reset">Clear</button>
        </form>
    </div>

    <%
        List<Task> tasks = dao1.findTasksByRegId(regId);
    %>

    <table>
        <tr>
            <th>Task ID</th>
            <th>Task Name</th>
            <th>Task Date</th>
            <th>Task Status</th>
            <th>Action</th>
        </tr>

        <%
            for (Task task : tasks) {
                int taskId = task.getTaskid();
                String taskName = task.getTaskname();
                String taskDate = task.getTaskdate();
                int taskStatus = task.getTaskstatus();
                String statusClass = (taskStatus == 3) ? "status-completed" : (taskStatus == 2) ? "status-in-progress" : "status-not-started";
        %>

        <tr class="<%= (taskStatus == 3) ? "completed" : "" %>">
            <td><%=taskId%></td>
            <td><%=taskName%></td>
            <td><%=taskDate%></td>
            <td class="<%=statusClass%>">
                <%= (taskStatus == 3) ? " Completed" : (taskStatus == 2) ? " In Progress" : " Not Started" %>
            </td>
            <td>
                <% if (taskStatus != 3) { %>
                    <a href="./MarkTaskCompletedServlet?regId=<%=regId%>&taskId=<%=taskId%>" class="btn-complete">Complete</a>
                <% } else { %>
                    ✅
                <% } %>
            </td>
        </tr>

        <%
            }
        %>
    </table>

</body>
</html>
