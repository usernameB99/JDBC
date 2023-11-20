import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Connection connection = null;
        try {
            connection = DatabaseConnector.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Verwaltung verwaltung = new Verwaltung(connection);

        verwaltung.runMainProgram(connection);

    }
}
