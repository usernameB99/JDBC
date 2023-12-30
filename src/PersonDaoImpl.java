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

    public void addPerson(Person person, int selectedHouseholdId) {
        String query = "INSERT INTO persons (firstname, lastname, gender, birthday, address, household_id) VALUES (?, ?, ?, ?, ?, ?)";                                //household id

        String fullAddress = person.getAdress().getFullAddress();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getGender().toString()); //Gender-Objekt to String
            statement.setString(4, person.getBirthDay());
            statement.setString(5, fullAddress);
            statement.setInt(6,selectedHouseholdId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public void updatePerson(Person person, int personId) {
        String query = "UPDATE persons SET firstname = ?, lastname = ?, gender = ?, birthday = ?, address = ? WHERE person_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getGender().toString());
            statement.setString(4, person.getBirthDay());
            statement.setString(5, person.getAdress().getFullAddress()); 
            statement.setInt(6, personId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(int personId) {
        String query = "DELETE FROM persons WHERE person_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, personId);                                           // Löschen anhand der Person-ID

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Person> getPersonsByHousehold(int householdId) {
        ArrayList<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons WHERE household_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, householdId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");

                Person person = new Person(firstName, lastName);
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
