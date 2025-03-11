package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Register;
import beans.Task;
import factory.DBConn;

public class ToDoDAOImpl implements ToDoDAO {

  static ToDoDAO toDoDAO;
  Connection con;
  Statement stmt;
  PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4, pstmtLogin;
  ResultSet rs;

  // Singleton Pattern for DAO
  private ToDoDAOImpl() {
    try {
      con = DBConn.getConn();
      stmt = con.createStatement();
      pstmt1 = con.prepareStatement("INSERT INTO register VALUES (?,?,?,?,?,?,?)");
      pstmt2 = con.prepareStatement("INSERT INTO tasks VALUES (?,?,?,?,?)");
      pstmt3 = con.prepareStatement("INSERT INTO taskid_pks VALUES(?,?)");
      pstmt4 = con.prepareStatement("UPDATE taskid_pks SET taskid=? WHERE regid=?");
      pstmtLogin = con.prepareStatement("SELECT regid FROM register WHERE email=? AND pass=?");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Factory method for Singleton instance
  public static ToDoDAO getInstance() {
    if (toDoDAO == null) {
      toDoDAO = new ToDoDAOImpl();
    }
    return toDoDAO;
  }

  @Override
  public int register(Register register) {
      int regId = 0;
      try {
          // Generate Primary Key (PK)
          rs = stmt.executeQuery("SELECT MAX(regid) FROM register");
          if (rs.next()) {
              regId = rs.getInt(1);
          }
          regId++;

          // Insert record into the database
          pstmt1.setInt(1, regId);
          pstmt1.setString(2, register.getFname());
          pstmt1.setString(3, register.getLname());
          pstmt1.setString(4, register.getEmail());
          pstmt1.setString(5, register.getPass());
          pstmt1.setLong(6, register.getMobile());
          pstmt1.setString(7, register.getAddress());

          int i = pstmt1.executeUpdate();
          if (i == 1) {
              System.out.println("✅ Data inserted successfully into register table!");
          } else {
              System.out.println("❌ Data insertion failed!");
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return regId;
  }


  @Override
  public int login(String email, String pass) {
    int regId = 0;
    try {
      pstmtLogin.setString(1, email);
      pstmtLogin.setString(2, pass);
      rs = pstmtLogin.executeQuery();
      if (rs.next()) {
        regId = rs.getInt(1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
    }
    return regId;
  }

  @Override
  public List<Task> findTasksByRegId(int regId) {
    List<Task> taskList = new ArrayList<>();
    try {
      rs = stmt.executeQuery("SELECT * FROM tasks WHERE regid=" + regId);
      while (rs.next()) {
        Task task = new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
        taskList.add(task);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return taskList;
  }

  @Override
  public int addTask(Task task, int regId) {
    int taskId = 0;
    boolean isNew = true;
    int i, j = 0;
    try {
      rs = stmt.executeQuery("SELECT taskid FROM taskid_pks WHERE regid=" + regId);
      if (rs.next()) {
        taskId = rs.getInt(1);
        isNew = false;
      }
      taskId++;

      con.setAutoCommit(false);
      // Insert task into tasks table
      pstmt2.setInt(1, taskId);
      pstmt2.setString(2, task.getTaskname());
      pstmt2.setString(3, task.getTaskdate());
      pstmt2.setInt(4, task.getTaskstatus());
      pstmt2.setInt(5, task.getRegid());
      i = pstmt2.executeUpdate();

      // Insert/update taskid_pks table
      if (isNew) {
        pstmt3.setInt(1, regId);
        pstmt3.setInt(2, taskId);
        j = pstmt3.executeUpdate();
      } else {
        pstmt4.setInt(1, taskId);
        pstmt4.setInt(2, regId);
        j = pstmt4.executeUpdate();
      }

      if (i == 1 && j == 1) {
        con.commit();
        System.out.println("Transaction Success, Task added");
      } else {
        con.rollback();
        System.out.println("Transaction Failed");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return taskId;
  }

  @Override
  public boolean markTaskCompleted(int regid, int taskid) {
   boolean flag=false;
   try {
    int i=stmt.executeUpdate("update tasks set taskstatus=3 where regid="+regid+" and taskid="+taskid);
    if(i==1) {
     flag=true;
     System.out.println("Task "+taskid+" of "+regid+" completed");
    }
   } catch(Exception e) {
    e.printStackTrace();
   }
   return flag;
  }
 

  @Override
  public String getFLNameByRegID(int regId) {
	    String name = null;
	    try {
	        Connection con = DBConn.getConn();
	        String query = "SELECT fname FROM register WHERE regId = ?";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, regId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            name = rs.getString("fname");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return name;
	}

}
