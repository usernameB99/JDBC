import java.util.ArrayList;

public class Household {

    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Person> getPv() {
        return pv;
    }

    private ArrayList <Person> pv = new ArrayList<>();

    public Household(String name) {
        this.name = name;
    }

    public String toString() {

        String result = "";

        for (Person p : pv){
            result += p.toString();
        }
        return result;
    }
}
