import java.sql.*;

public class ConnectionDB {
    static Connection con;
    public static Connection getConnection() {
        try {
            String mysqlDriver = "com.mysql.cj.jdbc.Driver";
            String mysqlUrl = "jdbc:mysql://localhost:3306/bank";
            String user = "root";
            String password = "admin";
            Class.forName(mysqlDriver);
            con = DriverManager.getConnection(mysqlUrl, user, password);
            System.out.println("Connection successful");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return con;
    }
}
