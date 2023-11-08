import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PersonDao {

    public void addPerson(Person person, int selectedHouseholdId);

    public List<Person> getAllPersons();

    // Weitere Methoden für Update, Delete, etc. können hinzugefügt werden

    public void updatePerson(Person person, int personId);

    // Methode zum Löschen einer Person aus der Datenbank
    public void deletePerson(int personId);

    public ArrayList<Person> getPersonsByHousehold(int householdId);

    public int getPersonId(String firstname);

    public String getPersonName(int personId);

}