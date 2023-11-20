import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface HouseholdDao {

    public int addHousehold(Household household) throws Exception;

    public List <Household> getAllHouseholds();

    public void updateHousehold(int householdId, String newName);

    public void deleteHousehold(int householdId);

    public int getHouseholdId(String name);

    public String getHouseholdName(int householdId);
}
