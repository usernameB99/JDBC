import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao{
     Connection connection;

    public PersonDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void addPerson(Person person) {
        String query = "INSERT INTO persons (firstname, lastname, gender, birthday, address) VALUES (?, ?, ?, ?, ?)";                                //household id

        String fullAddress = person.getAdress().getFullAddress(); // Hier wird die vollständige Adresse als String abgerufen

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getGender().toString()); // Wenn das Gender-Objekt in einen String umgewandelt werden kann
            statement.setString(4, person.getBirthDay());
            statement.setString(5, fullAddress); // Annahme: Adress-Objekt hat eine Methode toString()
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Hier müsste der Code ergänzt werden, um Personen aus dem ResultSet zu erstellen und zur Liste hinzuzufügen
                // Beispiel: String firstName = resultSet.getString("first_name");
                //            ... (Analog für die anderen Attribute)
                //            Person person = new Person(firstName, ...);
                //            persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }

        return persons;
    }

    // Weitere Methoden für Update, Delete, etc. können hinzugefügt werden

    public void updatePerson(Person person) {
        String query = "UPDATE persons SET firstname = ?, lastname = ?, gender = ?, birthday = ?, address = ? WHERE person_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getGender().toString());
            statement.setString(4, person.getBirthDay());
            statement.setString(5, person.getAdress().getFullAddress());
            statement.setInt(6, person.getId()); // Angenommen, die ID der Person wird verwendet, um sie zu identifizieren

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
    }

    // Methode zum Löschen einer Person aus der Datenbank
    public void deletePerson(int personId) {
        String query = "DELETE FROM persons WHERE person_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, personId); // Löschen anhand der Person-ID

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
    }

    public ArrayList<Person> getPersonsByHousehold(int householdId) {
        ArrayList<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons WHERE household_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, householdId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                // Weitere Spalten aus der Datenbank lesen und entsprechend der Person zuweisen
                // Beispiel: Gender, Geburtstag, Adresse, etc.

                Person person = new Person(firstName, lastName);
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
        return persons;
    }

    public int getPersonId(String firstname){

        String query = "SELECT person_id FROM persons WHERE firstname = ?";

        int personId = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstname);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                personId = resultSet.getInt("person_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personId;
    }

    public String getPersonName(int personId){

        String query = "SELECT firstname FROM persons WHERE person_id = ?";

        String personName = "";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                personName = resultSet.getString("firstname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personName;
    }


}