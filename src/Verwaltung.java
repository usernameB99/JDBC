import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Verwaltung {

private PersonDao personDao;
private HouseholdDaoImpl householdDao;
private PetDao petDao;



    public void doAllStuff(Connection connection){

        Verwaltung verwaltung = new Verwaltung(connection);

        Scanner sc = new Scanner(System.in);
        int select;
        boolean cont = true;


        do{
            int selectedHousehold = 0;
            String selectedHouseholdName = "";

            System.out.println("wählen sie:");
            System.out.println("1.) Haushalt auswählen aus liste");
            System.out.println("2.) Haushalt erstellen neue");
            select = sc.nextInt();

            if(select ==1){  // GET HAUSHALT
                if(verwaltung.householdDao.getAllHouseholds().size() == 0){
                    System.out.println("nix haushalt innen diese");
                } else{

                    System.out.print("Liste mit Haushalten: ");
                    prindAllHousehold(verwaltung);
                    System.out.println();
                    System.out.println("wählen sie einen haushalt aus den sie verwenden möchteeeen:");
                    select = sc.nextInt();
                    selectedHousehold = selectedHousehold(verwaltung, select);  //choose haushalt, get id
                    System.out.println(selectedHousehold); //kontrollausgabe

                }
            }

            else if (select == 2){    //ADD HAUSHALT
                System.out.println("Eingabe haushalt name des zu erstellenden haushaltes");
                sc.nextLine();
                String hNmae = sc.nextLine();
                Household newHousehold = new Household(hNmae);
                verwaltung.householdDao.addHousehold(newHousehold);  //neuer haushalt in datenbank eini
                selectedHousehold = verwaltung.householdDao.getHouseholdId(hNmae);  // get id from creates haushalt
                System.out.println(selectedHousehold);   //kontrollausgabe
            }

            selectedHouseholdName = verwaltung.householdDao.getHouseholdName(selectedHousehold);

            System.out.println("was wollen sie mit diesem haushalt machen? " + selectedHouseholdName);
            System.out.println("1.) Haushalt update");
            System.out.println("2.) Haushalt delete");
            System.out.println("3.) read all Persons from this haushalt");
            System.out.println("4.) add Person to this haushalt");
            select = sc.nextInt();

            if(select == 1){  //UPDATE HAUSHALD
                System.out.println("geben sie einen neuen namen für den haushalt ein:");
                String newName = sc.nextLine();
                verwaltung.householdDao.updateHousehold(selectedHousehold, newName);
                System.out.println("name wurde von " + selectedHouseholdName + " auf " + newName + " geändert");
                selectedHouseholdName = newName;  //reassign haushaltname

            } else if(select == 2){  // DELETE HAUSHALT
                int areYouSure = 0;
                System.out.println("sie haben haushalt löschen gewählt\n san sa si do wiakli sicha?");
                System.out.println("1.) yes \n2.)no");
                areYouSure = sc.nextInt();
                System.out.println("sind sie sich wirklich sicher?");
                System.out.println("1.) yes \n2.)no");
                areYouSure = sc.nextInt();
                System.out.println("sind sie sich wirklich wirklich sicher?");
                System.out.println("1.) joooo \n2.)no");
                areYouSure = sc.nextInt();
                System.out.println("sind sie sich wirklich wirklich wirklich sicher?");
                System.out.println("1.) lösch den schas jetzt herrst du wappla \n2.)no");
                areYouSure = sc.nextInt();
                if (areYouSure == 1){
                    System.out.println(selectedHouseholdName + " wird nun aus der Datenbank gelöscht");
                    verwaltung.householdDao.deleteHousehold(selectedHousehold);     //löschen des haushaltes aus datenbank
                } else{
                    System.out.println("daun hoid ned");
                }
            } else if(select == 2){
                //read all persons
            }




        } while(cont);





    }

    public static int selectedHousehold(Verwaltung verwaltung , int select){

        int selectedHouseholdId = 0;
        String selectedHouseholdName = "";

        for (int i = 0; i < verwaltung.householdDao.getAllHouseholds().size(); i++) {
            if (i == select-1){
                selectedHouseholdName = verwaltung.householdDao.getAllHouseholds().get(i).getName();
                System.out.println("ausgewählter haushalt: " + selectedHouseholdName );
                selectedHouseholdId = verwaltung.householdDao.getHouseholdId(selectedHouseholdName);
            }
        }
        return selectedHouseholdId;
    }

    public static void prindAllHousehold(Verwaltung verwaltung){

        for (int i = 0; i < verwaltung.householdDao.getAllHouseholds().size(); i++) {
            Household p = verwaltung.householdDao.getAllHouseholds().get(i);
            System.out.print((i + 1) + ".) " + p.getName() + " ");
        }
    }

    public Verwaltung(Connection connection) {
        personDao = new PersonDaoImpl(connection);
        householdDao = new HouseholdDaoImpl(connection);
        petDao = new PetDaoImpl(connection);
    }


    //-----------------------------------------------------------------------------------
    // - - -- -  - --  -- -  old stuff - - - - - - - - -- - - - - - -- - (remove later)
//personenverwaltung erstellen und zu liste hinzufügen
    //listen pv linz, pv cb usw hinzufügen

    //string to string methode zum ausdrucken der liste

    public ArrayList<Household> directorsList = new ArrayList<>();

    public void oldStuff() {

        Household cb = new Household("CB");
        Household linz = new Household("Linz");
        Household avengers = new Household("Avengers");

        Household current = null;

        Scanner sc = new Scanner(System.in);
        int select;
        boolean cont = true;

        System.out.println("Herzlich willkommen zur Haushaltsverwaltung");

        do {


            System.out.println("Wöhlen Sie eine Personenverwaltung aus: \n1.) Coders Bay \n2.) Linz \n3.) Avengers");
            System.out.println(":q! Programm beenden");
            //select = sc.nextInt();

    /*
            String input = sc.nextLine(); // Lies die Eingabe als Zeichenkette

            if (":q!".equals(input)) {
                // Wirf die Ausnahme, um das Programm zu beenden
                throw new RuntimeException("Programm wird beendet.");
            }
*/
            //select = Integer.parseInt(input); // Konvertiere die Eingabe in eine Zahl

            select = Integer.parseInt(checkForProgramTermination());


            if (select == 1) {
                current = cb;
            } else if (select == 2) {
                current = linz;
            } else if (select == 3)  {
                current = avengers;
            } else  {
                System.out.println("Tschüss.");
                System.exit(0);
            }



            System.out.println("1.) Person anlegen \n2.) Person löschen\n3.) Person suchen");
            select = Integer.parseInt(checkForProgramTermination());

            if (select == 1) {
                System.out.println("Welches Informationspaket wollen Sie für die Person anlegen?");
                System.out.println("1.) Simple - Vorname / Nachname");
                System.out.println("2.) Advanced - Vorname / Nachname / Geschlecht / Geburtstag");
                System.out.println("3.) Premium - Vorname / Nachname / Geschlecht / Geburtstag / Straße / Nr / Plz / Stadt");
                select = Integer.parseInt(checkForProgramTermination());
                //sc.nextLine();

                if (select == 1) {
                    System.out.println("Vorname");
                    //checkForProgramTermination(input);   //neu zuweisen scanner
                    String FirstName = checkForValidPersonName();
                    System.out.println("Nachname");
                    //checkForProgramTermination(input);
                    String LastName = checkForValidPersonName();

              //      current.createSimplePerson(FirstName, LastName);
                } else if (select == 2) {
                    System.out.println("Vorname");
                    String FirstName = checkForValidPersonName();
                    System.out.println("Nachname");
                    String LastName = checkForValidPersonName();
                    System.out.println("Geschlecht (männlich/weiblich/divers)");
                    Gender gender = Gender.valueOf(checkForProgramTermination());
                    System.out.println("Geburtstag");
                    String BirthDay = checkForProgramTermination();

           //         current.createHigherPerson(FirstName, LastName, gender, BirthDay);
                } else if (select == 3) {
                    System.out.println("Vorname");
                    String FirstName = checkForValidPersonName();
                    System.out.println("Nachname");
                    String LastName = checkForValidPersonName();
                    System.out.println("Geschlecht (männlich/weiblich/divers)");
                    Gender gender = Gender.valueOf(checkForProgramTermination());
                    System.out.println("Geburtstag");
                    String BirthDay = checkForProgramTermination();
                    System.out.println("Straße");
                    String Street = checkForProgramTermination();
                    System.out.println("Nr");
                    String Nr = checkForProgramTermination();
                    System.out.println("Plz");
                    String Plz = checkForProgramTermination();
                    System.out.println("Stadt");
                    String City = checkForProgramTermination();

             //       current.createFullPerson(FirstName, LastName, gender, BirthDay, Street, Nr, Plz, City);

                }

                //ArrayList<Person> list = current.getPv();
                //for (Person person : list) {
                //    System.out.println(person.getFirstName());
                //}

                for (Person person : current.getPv()) {
                    System.out.println(person.getFirstName());
                }

            } // Auswahl anlegen

            else if (select == 2){
                System.out.println("Sie haben löschen ausgewählt");

                for (int i = 0; i < current.getPv().size(); i++) {
                    System.out.println(i + ".) " + current.getPv().get(i).getFirstName());
                }

                System.out.println("Welche Person möchten Sie löschen?");
                select = sc.nextInt();

                current.getPv().remove(select);

            }

            else if (select == 3){
                System.out.println("Welche Person möchten Sie suchen?");
                containsPerson(current);

            }

        } while (cont); //main total

    }

    public void createHouseheld(String name) {

        directorsList.add(new Household(name));
    }

    public String toString() {


        String result = "";

        for (Household pv : directorsList) {
            result += pv.toString();
        }
        return result;
    }

    public static void containsPerson(Household toSearch){

        String searchName = checkForValidPersonName();
        boolean nameFound = false;
        String firstName;
        String lastName;

        try {

            for (int i = 0; i < toSearch.getPv().size(); i++) {
                firstName = toSearch.getPv().get(i).getFirstName();
                lastName = toSearch.getPv().get(i).getLastName();

                 if ((searchName.equals(firstName)) || (searchName.equals(lastName)))
                 {
                     nameFound = true;
                 }
            }
            if (nameFound){
                System.out.println("Name wurde gefunden");
            } else {

                    throw new NullPointerException();

            }
        } catch (NullPointerException e){
            System.out.println("NullPointException - Person wurde nicht gefunden.");
        }


    }

    public static String checkForProgramTermination() {
      Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        try {
            if (":q!".equals(input)) {
                throw new RuntimeException("Programm wird beendet.");
            }
        } catch (RuntimeException e) {
            System.exit(0);
        }


        System.out.println("---------checkForProgramTermination passed---------");
        return input;
    }


    public static String checkForValidPersonName() {
       Boolean stay = true;
       String name = "";
       // Scanner nameScanner = new Scanner(System.in);

        while (stay) {
            try {
                name = checkForProgramTermination();

                if (name.matches(".*\\d.*")) {
                    System.out.println("----- input has number ----- exit false");
                    //name= nameScanner.nextLine();
                    throw new RuntimeException("Ungültiger Name: Der Name darf keine Zahlen enthalten.");
                } else {
                    System.out.println("----- returninger bei void person shit passed----- ");
                    stay = false;
                    return name;

                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                System.out.println("Bitte erneut eingeben:");
            }
        }
        return name;
    }



}
