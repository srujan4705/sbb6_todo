package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    static Connection con;

    public static Connection getConn() {
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database URL (Make sure 'sb_todo' exists in MySQL)
            String url = "jdbc:mysql://localhost:3306/sb_todo?useSSL=false&serverTimezone=UTC";
            String user = "root";  // Default MySQL username in XAMPP
            String password = "";  // Default MySQL password is empty in XAMPP

            // Establish Connection
            con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database Connection Established!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL Driver Not Found! Add MySQL Connector JAR.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed! Check MySQL is running.");
            e.printStackTrace();
        }
        return con;
    }
}
