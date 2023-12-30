import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDaoImpl implements PetDao{
    Connection connection;

    public PetDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void addPet(Pet pet, int selectedPersonId) {
        String query = "INSERT INTO pets (name, age, type, person_id) VALUES (?, ?, ?, ?)";                                             //person_id

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getAge());
            statement.setString(3, pet.getAnimalType());
            statement.setInt(4, selectedPersonId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String query = "SELECT * FROM pets";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String age = resultSet.getString("age");
                String animalType = resultSet.getString("type");
                Pet pet = new Pet(name, age, animalType);
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    public void updatePet(Pet pet, int selectedPetId) {
        String query = "UPDATE pets SET name = ?, age = ?, type = ? WHERE pet_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getAge());
            statement.setString(3, pet.getAnimalType());
            statement.setInt(4, selectedPetId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePet(int petId) {
        String query = "DELETE FROM pets WHERE pet_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, petId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pet> getAllPetsByPersonId(int personId) {
        ArrayList<Pet> pets = new ArrayList<>();
        String query = "SELECT * FROM pets WHERE person_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String age = resultSet.getString("age");
                String type = resultSet.getString("type");

                Pet pet = new Pet(name, age, type);
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    public int getPetId(String name){
        String query = "SELECT pet_id FROM pets WHERE name = ?";

        int petId = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                petId = resultSet.getInt("pet_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return petId;
    }
}
