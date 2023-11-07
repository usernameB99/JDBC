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

// methode string to string zum ausdrucken listeninhalt



//    public void createSimplePerson (String firstName, String lastName) {
//
//        //Person person1 = new Person(firstName, lastName);                                 NO NEED FOR THAT
//        pv.add(new Person(firstName,lastName));
//    }
//
//    public void createHigherPerson (String firstName, String lastName, Gender gender, String birthDay) {
//
//        pv.add(new Person(firstName,lastName,gender,birthDay));
//
//    }

//    public void createFullPerson (String firstName, String lastName, Gender gender, String birthDay,String street, String nr, String plz, String city ) {
//
//        Adress adress = new Adress(street,nr,plz,city);
//
//        Person person = new Person(firstName, lastName, gender, birthDay, adress);
//
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javajdbc", "bresl", "passme");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        PersonDao personDao = new PersonDao(connection); // Erstelle die PersonDao mit der Verbindung zur Datenbank
//
//        personDao.addPerson(person); // Füge die neue Person direkt in die Datenbank ein
//
//
//        //pv.add(new Person(firstName, lastName, gender, birthDay,adress));
//    }

    public String toString() {
        //System.out.println(Person);

        String result = "";

        for (Person p : pv){
            result += p.toString();
        }
        return result;
    }

}



