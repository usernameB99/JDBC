import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/javajdbcneu";
    private static final String USER = "bresl";
    private static final String PASSWORD = "passme";

    private static Connection connection = null;

    public static Connection getInstance() throws SQLException {
        if (connection == null) {
            connection = openConnection();
        } else if (!connection.isValid(0)) {
            connection.close();
            connection = openConnection();
        }
        return connection;
    }


    private static Connection openConnection() {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


//System.out.println("Erfolgreich mit der Datenbank verbunden!");