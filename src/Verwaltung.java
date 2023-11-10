import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Verwaltung {

    private PersonDao personDao;
    private HouseholdDaoImpl householdDao;
    private PetDao petDao;


    public void doAllStuff(Connection connection) {

        Verwaltung verwaltung = new Verwaltung(connection);

        Scanner sc = new Scanner(System.in);
        int select;
        boolean cont = true;
        boolean contHouseheld = true;
        boolean contPersonSection = true;
        boolean contPetSection = true;
        boolean contPetEditSection = true;


        do { // do all


            int selectedHouseholdId = 0;
            String selectedHouseholdName = "";


            do {  //--------------------------------------------------------------------------- haushalt section
                System.out.println("wählen sie:");
                System.out.println("1.) Haushalt auswählen aus liste");
                System.out.println("2.) Haushalt erstellen neue");
                select = sc.nextInt();

                if (select == 1) {  // GET HAUSHALT
                    if (verwaltung.householdDao.getAllHouseholds().size() == 0) {
                        System.out.println("nix haushalt innen diese");
                    } else {

                        System.out.print("Liste mit Haushalten: ");
                        prindAllHousehold(verwaltung);
                        System.out.println();
                        System.out.println("wählen sie einen haushalt aus den sie verwenden möchteeeen:");
                        select = sc.nextInt();
                        selectedHouseholdId = selectedHousehold(verwaltung, select);  //choose haushalt, get id
                        System.out.println(selectedHouseholdId); //kontrollausgabe

                    }
                } else if (select == 2) {    //ADD HAUSHALT
                    System.out.println("Eingabe haushalt name des zu erstellenden haushaltes");
                    sc.nextLine();
                    String hNmae = sc.nextLine();
                    Household newHousehold = new Household(hNmae);
                    verwaltung.householdDao.addHousehold(newHousehold);  //neuer haushalt in datenbank eini
                    selectedHouseholdId = verwaltung.householdDao.getHouseholdId(hNmae);  // get id from creates haushalt
                    System.out.println(selectedHouseholdId);   //kontrollausgabe
                }

                System.out.println("wollen sie einen neuen haushalt erstellen oder mit dem erstellten/ausgewählten fortfahren?");
                System.out.println("1.) fortfahren\n2.) noch einen Haushalt erstellen");
                select = sc.nextInt();
                if (select == 1) {
                    contHouseheld = false;
                }
                sc.nextLine();
            } while (contHouseheld);

            selectedHouseholdName = verwaltung.householdDao.getHouseholdName(selectedHouseholdId);


            int selectedPersonId = 0;
            String selectedPersonName = "";

            do{

            System.out.println("was wollen sie mit diesem haushalt machen? " + selectedHouseholdName);
            System.out.println("1.) Haushalt update");
            System.out.println("2.) Haushalt delete");
            System.out.println("3.) read all Persons from this haushalt");
            System.out.println("4.) add Person to this haushalt");
            select = sc.nextInt();

            if (select == 1) {  //UPDATE HAUSHALD
                System.out.println("geben sie einen neuen namen für den haushalt ein:");
                sc.nextLine();
                String newName = sc.nextLine();
                verwaltung.householdDao.updateHousehold(selectedHouseholdId, newName);
                System.out.println("name wurde von " + selectedHouseholdName + " auf " + newName + " geändert");
                selectedHouseholdName = newName;  //reassign haushaltname

            } else if (select == 2) {  // DELETE HAUSHALT

                int areYouSure = areYouSure();  // abfrage oba si wiakli wiakli wiakli sicha is

                if (areYouSure == 1) {
                    System.out.println(selectedHouseholdName + " wird nun aus der Datenbank gelöscht");
                    verwaltung.householdDao.deleteHousehold(selectedHouseholdId);     //löschen des haushaltes aus datenbank
                } else {
                    System.out.println("daun hoid ned");
                }
            } else if (select == 3) { //select person from selected haushalt
                if (verwaltung.personDao.getPersonsByHousehold(selectedHouseholdId).size() == 0) {
                    System.out.println("es sind keine personen in diesem haushalt");
                } else {

                    System.out.print("Liste mit Personen in " + selectedHouseholdName + ": ");
                    printPersonsFromHousehold(verwaltung, selectedHouseholdId);   //method print persons in selected household()
                    System.out.println();
                    System.out.println("wählen sie eine Person mit der sie was machen möchteeeen:");
                    select = sc.nextInt();

                    selectedPersonId = selectPerson(verwaltung, select, selectedHouseholdId);  //choose person, get id
                    System.out.println("Kontrollausgabe selected person id: " + selectedPersonId); //kontrollausgabe
                }
            } else if (select == 4) { // create person & later get id by name after creation

                selectedPersonId = createPerson(verwaltung, selectedHouseholdId); //erstellen von person und rückagbe von person id
            }



                System.out.println("wollen sie eine neue person erstellen oder mit der erstellten/ausgewählten fortfahren?");
                System.out.println("1.) fortfahren\n2.) noch eine Person erstellen");
                select = sc.nextInt();
                if (select == 1) {
                    contPersonSection = false;
                }
                sc.nextLine();
        } while (contPersonSection);


            int selectedPetId = 0;
            String selectedPetName = "";

          do{

            selectedPersonName = verwaltung.personDao.getPersonName(selectedPersonId);

            System.out.println("was wollen sie mit der Person " + selectedPersonName + " machen?");
            System.out.println("1.) Person bearbeiten");
            System.out.println("2.) Person löschen");
            System.out.println("3.) Haustiere der Person anzeigen");
            System.out.println("4.) Haustier zu Person hinzufügen");
            select = sc.nextInt();



            if (select == 1) {
                System.out.println("sie haben person bearbeiten gewählt\ngeben sie die neuen informationen ein:");
                updatePerson(verwaltung, selectedPersonId);
            } else if (select == 2) {
                System.out.println("sie haben person löschen gewählt");

                int areYouSure = areYouSure();  // abfrage oba si wiakli wiakli wiakli sicha is

                if (areYouSure == 1) {
                    System.out.println(selectedPersonName + " wird nun aus der Datenbank gelöscht");
                    verwaltung.personDao.deletePerson(selectedPersonId);     //löschen des haushaltes aus datenbank
                } else {
                    System.out.println("daun hoid ned");
                }
            } else if (select == 3) {  //select pet from printed list

                if (verwaltung.petDao.getAllPetsByPersonId(selectedPersonId).size() == 0) {
                    System.out.println("es sind keine haustiere dieser person zugewiesen");
                } else {
                    System.out.print("Liste mit Haustieren von " + selectedPersonName + ": ");
                    printPetsByPerson(verwaltung, selectedPersonId);
                    System.out.println("wählen sie ein haustier aus der liste");
                    select = sc.nextInt();
                    selectedPetId = selectPet(verwaltung, select, selectedPersonId);
                    System.out.println("Kontrollausgabe selected pet id: " + selectedPetId); //kontrollausgabe
                }

            } else if (select == 4) { //add pet
                selectedPetId = createPet(verwaltung, selectedPersonId);
            }

              System.out.println("wollen sie eine neues Haustier erstellen oder mit der erstellten/ausgewählten fortfahren?");
              System.out.println("1.) fortfahren\n2.) noch ein Haustier erstellen");
              select = sc.nextInt();
              if (select == 1) {
                  contPetSection = false;
              }
              sc.nextLine();
        } while(contPetSection);

          do{


            System.out.println("was wollen sie mit dem Haustier " + selectedPetName + " machen?");
            System.out.println("1.) Haustier bearbeiten");
            System.out.println("2.) Haustier löschen");
            select = sc.nextInt();

            if (select == 1) {
                System.out.println("sie haben haustier bearbeiten gewählt\ngeben sie die neuen informationen ein:");
                updatePet(verwaltung, selectedPetId);
            } else if (select == 2) {

                System.out.println("sie haben haustier löschen gewählt");

                int areYouSure = areYouSure();  // abfrage oba si wiakli wiakli wiakli sicha is

                if (areYouSure == 1) {
                    System.out.println(selectedPetName + " wird nun aus der Datenbank gelöscht");
                    verwaltung.petDao.deletePet(selectedPetId);     //löschen des haushaltes aus datenbank
                } else {
                    System.out.println("daun hoid ned");
                }

            }

              System.out.println("wollen sie ein weiteres haustier bearbeiten oder wieder zurück zum start (haushalt section)?");
              System.out.println("1.) back to start (haushalt section)\n2.) noch ein Haustier erstellen");
              select = sc.nextInt();
              if (select == 1) {
                  contPetEditSection = false;
              }
        } while (contPetEditSection);

            sc.nextLine();
        } while (cont);

    }

    public static void updatePet(Verwaltung verwaltung, int selectedPetId) {
        System.out.println("Haustier Name:");
        String name = checkForValidPersonName();
        System.out.println("Haustier Alter");
        String age = checkForProgramTermination();
        System.out.println("Haustier Typ");
        String type = checkForValidPersonName();

        Pet editedPet = new Pet(name, age, type);
        verwaltung.petDao.updatePet(editedPet, selectedPetId);

        System.out.println("* bi bu bup pip - haustier wurde geändert *");
    }

    public static int createPet(Verwaltung verwaltung, int selectedPersonId) {
        System.out.println("Haustier Name:");
        String name = checkForValidPersonName();
        System.out.println("Haustier Alter");
        String age = checkForProgramTermination();
        System.out.println("Haustier Typ");
        String type = checkForValidPersonName();

        Pet newPet = new Pet(name, age, type);

        verwaltung.petDao.addPet(newPet, selectedPersonId);  //neuer haushalt in datenbank eini
        int createdPetId = verwaltung.petDao.getPetId(name);  // get id from created person

        System.out.println("* bi bu bup pip - haustier " + name + " wurde erstellt *");
        return createdPetId;
    }


    public static int selectPet(Verwaltung verwaltung, int select, int selectedPersonId) {
        int selectedPetId = 0;
        String selectedPetName = "";

        for (int i = 0; i < verwaltung.petDao.getAllPetsByPersonId(selectedPersonId).size(); i++) {
            if (i == select - 1) {
                selectedPetName = verwaltung.petDao.getAllPetsByPersonId(selectedPersonId).get(i).getName();
                System.out.println("ausgewähltes haustier: " + selectedPetName);
                selectedPetId = verwaltung.petDao.getPetId(selectedPetName);
            }
        }
        return selectedPetId;
    }

    public static void printPetsByPerson(Verwaltung verwaltung, int selectedPerson) {

        for (int i = 0; i < verwaltung.petDao.getAllPetsByPersonId(selectedPerson).size(); i++) {
            Pet p = verwaltung.petDao.getAllPetsByPersonId(selectedPerson).get(i);
            System.out.print((i + 1) + ".) " + p.getName() + " ");
        }
        System.out.println();
    }

    public static int areYouSure() {
        Scanner sc = new Scanner(System.in);
        int areYouSure = 0;
        System.out.println("sind sie sicher?");
        System.out.println("1.) yes \n2.)no");
        areYouSure = sc.nextInt();
        System.out.println("san sa si do wiakli sicha?");
        System.out.println("1.) yes \n2.)no");
        areYouSure = sc.nextInt();
        System.out.println("sind sie sich da wirklich sicher?");
        System.out.println("1.) yes \n2.)no");
        areYouSure = sc.nextInt();
        System.out.println("sind sie sich da wirklich wirklich sicher?");
        System.out.println("1.) joooo \n2.)no");
        areYouSure = sc.nextInt();
        System.out.println("sind sie sich da wirklich wirklich wirklich wiiiiirklich sicher?");
        System.out.println("1.) lösch den schas jetzt herrst du wappla \n2.)no");
        areYouSure = sc.nextInt();

        return areYouSure;
    }

    public static void updatePerson(Verwaltung verwaltung, int personId) {
        System.out.println("neuer Vorname");
        String firstName = checkForValidPersonName();
        System.out.println("neuer Nachname");
        String lastName = checkForValidPersonName();
        System.out.println("Geschlecht (männlich/weiblich/divers)");
        Gender gender = Gender.valueOf(checkForProgramTermination());
        System.out.println("Geburtstag");
        String birthDay = checkForProgramTermination();
        System.out.println("Straße");
        String street = checkForProgramTermination();
        System.out.println("Nr");
        String nr = checkForProgramTermination();
        System.out.println("Plz");
        String plz = checkForProgramTermination();
        System.out.println("Stadt");
        String city = checkForProgramTermination();

        Adress editedAdress = new Adress(street, nr, plz, city);
        Person editedPerson = new Person(firstName, lastName, gender, birthDay, editedAdress);
        verwaltung.personDao.updatePerson(editedPerson, personId);      // update id?

        System.out.println("* bi bu bup pip - person wurde geändert *");
    }

    public static int createPerson(Verwaltung verwaltung, int selectedHouseholdId) {

        int createdPersonId = 0;

        System.out.println("Vorname");
        String firstName = checkForValidPersonName();
        System.out.println("Nachname");
        String lastName = checkForValidPersonName();
        System.out.println("Geschlecht (männlich/weiblich/divers)");
        Gender gender = Gender.valueOf(checkForProgramTermination());
        System.out.println("Geburtstag");
        String birthDay = checkForProgramTermination();
        System.out.println("Straße");
        String street = checkForProgramTermination();
        System.out.println("Nr");
        String nr = checkForProgramTermination();
        System.out.println("Plz");
        String plz = checkForProgramTermination();
        System.out.println("Stadt");
        String city = checkForProgramTermination();

        Adress newAdress = new Adress(street, nr, plz, city);
        Person newPerson = new Person(firstName, lastName, gender, birthDay, newAdress);

        verwaltung.personDao.addPerson(newPerson, selectedHouseholdId);  //neuer haushalt in datenbank eini
        createdPersonId = verwaltung.personDao.getPersonId(firstName);  // get id from created person

        System.out.println("* bi bu bup pip - person " + firstName + " wurde erstellt *");

        return createdPersonId;
    }

    // methode select person
    public static int selectPerson(Verwaltung verwaltung, int select, int selectedHouseholdId) {

        int selectedPersonId = 0;
        String selectedpersonName = "";

        for (int i = 0; i < verwaltung.householdDao.getAllHouseholds().size(); i++) {
            if (i == select - 1) {
                selectedpersonName = verwaltung.personDao.getPersonsByHousehold(selectedHouseholdId).get(i).getFirstName();
                System.out.println("ausgewählte person: " + selectedpersonName);
                selectedPersonId = verwaltung.personDao.getPersonId(selectedpersonName);
            }
        }
        return selectedPersonId;

    }


    public static void printPersonsFromHousehold(Verwaltung verwaltung, int selectedHouseholdId) {

        for (int i = 0; i < verwaltung.personDao.getPersonsByHousehold(selectedHouseholdId).size(); i++) {
            Person p = verwaltung.personDao.getPersonsByHousehold(selectedHouseholdId).get(i);
            System.out.print((i + 1) + ".) " + p.getFirstName() + " ");
        }
    }


    public static int selectedHousehold(Verwaltung verwaltung, int select) {

        int selectedHouseholdId = 0;
        String selectedHouseholdName = "";

        for (int i = 0; i < verwaltung.householdDao.getAllHouseholds().size(); i++) {
            if (i == select - 1) {
                selectedHouseholdName = verwaltung.householdDao.getAllHouseholds().get(i).getName();
                System.out.println("ausgewählter haushalt: " + selectedHouseholdName);
                selectedHouseholdId = verwaltung.householdDao.getHouseholdId(selectedHouseholdName);
            }
        }
        return selectedHouseholdId;
    }

    public static void prindAllHousehold(Verwaltung verwaltung) {

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




    public static void containsPerson(Household toSearch) {

        String searchName = checkForValidPersonName();
        boolean nameFound = false;
        String firstName;
        String lastName;

        try {

            for (int i = 0; i < toSearch.getPv().size(); i++) {
                firstName = toSearch.getPv().get(i).getFirstName();
                lastName = toSearch.getPv().get(i).getLastName();

                if ((searchName.equals(firstName)) || (searchName.equals(lastName))) {
                    nameFound = true;
                }
            }
            if (nameFound) {
                System.out.println("Name wurde gefunden");
            } else {

                throw new NullPointerException();

            }
        } catch (NullPointerException e) {
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

    public void createFullPerson(String firstName, String lastName, Gender gender, String birthDay, String street, String nr, String plz, String city) {

        Adress adress = new Adress(street, nr, plz, city);

        // pv.add(new Person(firstName, lastName, gender, birthDay,adress));
    }


}
