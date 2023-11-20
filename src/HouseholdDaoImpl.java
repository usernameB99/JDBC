/*import jdk.internal.jimage.ImageStream;*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HouseholdDaoImpl implements HouseholdDao {

     Connection connection;

    public HouseholdDaoImpl(Connection connection) {
        this.connection = connection;
    }

    // Methode zum Hinzufügen eines Haushalts in die Datenbank
    public int addHousehold(Household household) throws Exception {                                     //return id here zum weiterarbeiten

        if (getHouseholdId(household.getName()) == 0) {
            String query = "INSERT INTO households (name) VALUES (?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, household.getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                // Hier sollte die Fehlerbehandlung erfolgen
            }
            return getHouseholdId(household.getName());
        } else {
            throw new Exception("Household already exists");
        }
    }

    // Methode zum Abrufen aller Haushalte aus der Datenbank
    public ArrayList<Household> getAllHouseholds() {
        ArrayList<Household> households = new ArrayList<>();
        String query = "SELECT * FROM households";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Household household = new Household(name);
                households.add(household);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }

        return households;
    }


    // Methode zum Löschen eines Haushalts aus der Datenbank anhand des Namens
    public void deleteHousehold(int householdId) {
        String query = "DELETE FROM households WHERE household_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, householdId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
    }

    public void updateHousehold(int householdId, String newName) {
        String query = "UPDATE households SET name = ? WHERE household_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newName);
            statement.setInt(2, householdId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
    }

    public int getHouseholdId(String name) {
        String query = "SELECT household_id FROM households WHERE name = ?";

        int householdId = 0;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                householdId = resultSet.getInt("household_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return householdId;

    }

    public String getHouseholdName(int householdId) {
        String query = "SELECT name FROM households WHERE household_id = ?";

        String householdName = "";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, householdId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                householdName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return householdName;

    }

}
