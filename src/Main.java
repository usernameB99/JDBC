import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Connection connection = DatabaseConnector.getInstance();

        Verwaltung verwaltung = new Verwaltung(connection);

        verwaltung.doAllStuff(connection);

    }
}
