package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bankingsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "Admin1";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver class
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
