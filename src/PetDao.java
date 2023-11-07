import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface PetDao {
    public void addPet(Pet pet);

    public List<Pet> getAllPets();

    // Weitere Methoden für Update, Delete, etc. können hinzugefügt werden

    public void updatePet(Pet pet);

    public void deletePet(int petId);

    public Pet getPetByPersonId(int personId);

}