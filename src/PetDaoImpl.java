import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDaoImpl implements PetDao{
    Connection connection;

    public PetDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void addPet(Pet pet) {
        String query = "INSERT INTO pets (name, age, type, person_id) VALUES (?, ?, ?, ?)";                                             //person_id

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pet.getName());
            statement.setInt(2, pet.getAge());
            statement.setString(3, pet.getAnimalType());
            statement.setInt(2, pet.getId());                                                                             // added person id
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                pet.setId(generatedId); // Setze die automatisch generierte ID in der Pet-Instanz
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
    }

    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String query = "SELECT * FROM pets";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String animalType = resultSet.getString("type");
                int id = resultSet.getInt("person_id");                                                                                 //added id
                Pet pet = new Pet(name, age, animalType, id);
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }

        return pets;
    }


    public void updatePet(Pet pet) {
        String query = "UPDATE pets SET name = ?, age = ?, type = ? WHERE pet_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pet.getName());
            statement.setInt(2, pet.getAge());
            statement.setString(3, pet.getAnimalType());
            // Annahme: Die ID wird benötigt, um das spezifische Haustier zu identifizieren
            // Hier solltest du die entsprechende ID des Haustiers setzen                                                                       // pet id aus datenbank?
            statement.setInt(4, pet.getId()); // Annahme: Du hast ein Attribut id in der Pet-Klasse                                 // TODO - von wo bekomme ich pet_id?
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
    }

    public void deletePet(int petId) {
        String query = "DELETE FROM pets WHERE pet_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, petId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }
    }

    public Pet getPetByPersonId(int personId) {
        String query = "SELECT * FROM pets WHERE person_id = ?";
        Pet pet = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String animalType = resultSet.getString("type");
                int id = resultSet.getInt("person_id");                                                                                 //added person_id

                pet = new Pet(name, age, animalType, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier sollte die Fehlerbehandlung erfolgen
        }

        return pet;
    }

}