import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface PetDao {
    public void addPet(Pet pet, int selectedPersonId);

    public List<Pet> getAllPets();

    public void updatePet(Pet pet, int selectedPetId);

    public void deletePet(int petId);

    public ArrayList<Pet> getAllPetsByPersonId(int personId);

    public int getPetId(String name);
}
