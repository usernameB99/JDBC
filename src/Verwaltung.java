import java.sql.Connection;
import java.util.Scanner;

public class Verwaltung {

    private PersonDao personDao;
    private HouseholdDao householdDao;
    private PetDao petDao;


    public void runMainProgram(Connection connection) {

        Verwaltung verwaltung = new Verwaltung(connection);
        Scanner sc = new Scanner(System.in);
        int select;
        boolean cont = true;
        boolean contHouseheld = true;
        boolean contPersonSection = true;
        boolean contPetSection = true;
        boolean contPetEditSection = true;

        welcome();

            do { // do all
                int selectedHouseholdId = 0;
                String selectedHouseholdName = "";
                do {
                    select = askWhatToDoHouseholdStart(sc);
                    if (select == 1) {  // GET HAUSHALT
                        selectedHouseholdId = getHousehold(verwaltung, sc, selectedHouseholdId);
                    } else if (select == 2) {    //ADD HAUSHALT
                        selectedHouseholdId = addHousehold(verwaltung, sc);
                    }
                    select = continueHouseholdSection(sc);
                    if (select == 1) {
                        contHouseheld = false;
                    }
                } while (contHouseheld);
                selectedHouseholdName = verwaltung.householdDao.getHouseholdName(selectedHouseholdId);
                int selectedPersonId = 0;
                String selectedPersonName = "";
                do {
                    select = askWhatToDoHouseholdPerson(sc, selectedHouseholdName); //auswählen Haushalt Personen was machen
                    if (select == 1) {  //UPDATE HAUSHALD
                        selectedHouseholdName = updateHaushalt(verwaltung, sc, selectedHouseholdId, selectedHouseholdName);
                    } else if (select == 2) {  // DELETE HAUSHALT
                        deleteHousehold(verwaltung, selectedHouseholdId, selectedHouseholdName);
                    } else if (select == 3) { //select person from selected haushalt
                        if (verwaltung.personDao.getPersonsByHousehold(selectedHouseholdId).size() == 0) {
                            System.out.println("Es sind keine Personen in diesem Haushalt zugewiesen");
                            contPersonSection = true;
                        } else {
                            select = selectPersonFromHouseholdList(verwaltung, sc, selectedHouseholdId, selectedHouseholdName);
                            selectedPersonId = selectPerson(verwaltung, select, selectedHouseholdId);  //choose person, get id
                            System.out.println("Kontrollausgabe selected person id: " + selectedPersonId); //kontrollausgabe
                            contPersonSection = continueFromPersonSection(sc, contPersonSection);
                        }
                    } else if (select == 4) { // create person
                        selectedPersonId = createPerson(verwaltung, selectedHouseholdId); //erstellen von person und rückagbe von person id
                        contPersonSection = continueFromCreatePersonSection(sc, contPersonSection);
                    }
                } while (contPersonSection);
                int selectedPetId = 0;
                String selectedPetName = "";
                do {
                    selectedPersonName = verwaltung.personDao.getPersonName(selectedPersonId);
                    select = askWhatToDoPersonPetSectionStart(sc, selectedPersonName);
                    if (select == 1) { //update person
                        System.out.println("sie haben person bearbeiten gewählt\ngeben sie die neuen informationen ein:");
                        updatePerson(verwaltung, selectedPersonId);
                    } else if (select == 2) { //delete person
                        deletePersonMethod(verwaltung, selectedPersonId, selectedPersonName);
                    } else if (select == 3) {  //select pet from printed list
                        if (verwaltung.petDao.getAllPetsByPersonId(selectedPersonId).size() == 0) {
                            System.out.println("es sind keine haustiere dieser person zugewiesen");
                        } else {
                            selectedPetId = selectPetfromPetListbyPerson(verwaltung, sc, selectedPersonId, selectedPersonName);
                            contPetSection = continueSelectPetSection(sc, contPetSection);
                        }
                    } else if (select == 4) { //add pet
                        selectedPetId = createPet(verwaltung, selectedPersonId);
                        contPetSection = continueSelectPetSection(sc, contPetSection);
                    }
                } while (contPetSection);
                do {
                    select = whatToDoWithSelectedPetSection(sc, selectedPetName);
                    if (select == 1) { //update pet
                        System.out.println("sie haben haustier bearbeiten gewählt\ngeben sie die neuen informationen ein:");
                        updatePet(verwaltung, selectedPetId);
                    } else if (select == 2) {
                        deletePet(verwaltung, selectedPetId, selectedPetName);
                    }
                    contPetEditSection = continueEditPetOrBackToStart(sc, contPetEditSection);
                } while (contPetEditSection);
            } while (cont);
    }

    public void deleteHousehold(Verwaltung verwaltung, int selectedHouseholdId, String selectedHouseholdName) {
        System.out.println("Sie haben Haushalt löschen gewählt");
        System.out.println(selectedHouseholdName + " wird nun aus der Datenbank gelöscht");
        verwaltung.householdDao.deleteHousehold(selectedHouseholdId);     //löschen des haushaltes aus datenbank
    }

    private void deletePet(Verwaltung verwaltung, int selectedPetId, String selectedPetName) {
        System.out.println("sie haben haustier löschen gewählt");
        System.out.println(selectedPetName + " wird nun aus der Datenbank gelöscht");
        verwaltung.petDao.deletePet(selectedPetId);     //löschen des haushaltes aus datenbank
    }

    private boolean continueEditPetOrBackToStart(Scanner sc, boolean contPetEditSection) {
        int select;
        System.out.println("wollen sie ein weiteres haustier bearbeiten oder wieder zurück zum start (haushalt section)?");
        System.out.println("1.) back to start (haushalt section)\n2.) noch ein Haustier erstellen");
        select = checkIntForProgramTermination();
        if (select == 1) {
            contPetEditSection = false;
        }
        return contPetEditSection;
    }

    private int whatToDoWithSelectedPetSection(Scanner sc, String selectedPetName) {
        int select;
        System.out.println("* Bereich 4:");
        System.out.println("was wollen sie mit dem Haustier " + selectedPetName + " machen?");
        System.out.println("1.) Haustier bearbeiten");
        System.out.println("2.) Haustier löschen");
        select = checkIntForProgramTermination();
        return select;
    }

    private int selectPetfromPetListbyPerson(Verwaltung verwaltung, Scanner sc, int selectedPersonId, String selectedPersonName) {
        int selectedPetId;
        int select;
        System.out.print("Liste mit Haustieren von " + selectedPersonName + ": ");
        printPetsByPerson(verwaltung, selectedPersonId);
        System.out.println("wählen sie ein haustier aus der liste");
        select = checkIntForProgramTermination();
        selectedPetId = selectPet(verwaltung, select, selectedPersonId);
        return selectedPetId;
    }

    private boolean continueSelectPetSection(Scanner sc, boolean contPetSection) {
        int select;
        System.out.println("wollen sie eine neues Haustier erstellen oder mit der erstellten/ausgewählten fortfahren?");
        System.out.println("1.) fortfahren\n2.) noch ein Haustier erstellen");
        select = checkIntForProgramTermination();
        if (select == 1) {
            contPetSection = false;
        }
        return contPetSection;
    }

    private void deletePersonMethod(Verwaltung verwaltung, int selectedPersonId, String selectedPersonName) {
        System.out.println("sie haben person löschen gewählt");
        System.out.println(selectedPersonName + " wird nun aus der Datenbank gelöscht");
        verwaltung.personDao.deletePerson(selectedPersonId);     //löschen des haushaltes aus datenbank
    }

    private int askWhatToDoPersonPetSectionStart(Scanner sc, String selectedPersonName) {
        int select;
        System.out.println("* Bereich 3:");
        System.out.println("was wollen sie mit der Person " + selectedPersonName + " machen?");
        System.out.println("1.) Person bearbeiten");
        System.out.println("2.) Person löschen");
        System.out.println("3.) Haustiere der Person anzeigen");
        System.out.println("4.) Haustier zu Person hinzufügen");
        select = checkIntForProgramTermination();
        return select;
    }

    private boolean continueFromCreatePersonSection(Scanner sc, boolean contPersonSection) {
        int select;
        System.out.println("Wollen Sie mit dieser Person fortfahren, eine andere Person auswählen, oder eine neue Person erstellen?");
        System.out.println("1.) fortfahren\n2.) eine andere Person auswählen, eine weitere Person erstellen");
        select = checkIntForProgramTermination();
        if (select == 1) {
            contPersonSection = false;
        }
        return contPersonSection;
    }

    private boolean continueFromPersonSection(Scanner sc, boolean contPersonSection) {
        int select;
        select = continueSelectPerson(sc);
        if (select == 1) {
            contPersonSection = false;
        }
        return contPersonSection;
    }

    private int selectPersonFromHouseholdList(Verwaltung verwaltung, Scanner sc, int selectedHouseholdId, String selectedHouseholdName) {
        int select;
        System.out.print("Liste mit Personen in " + selectedHouseholdName + ": ");
        printPersonsFromHousehold(verwaltung, selectedHouseholdId);   //method print persons in selected household()
        System.out.println();
        System.out.println("wählen sie eine Person mit der sie was machen möchten:");
        select = checkIntForProgramTermination();
        return select;
    }

    private int continueSelectPerson(Scanner sc) {
        int select;
        System.out.println("Wollen Sie mit dieser Person fortfahren, eine andere Person auswählen, oder eine neue Person erstellen?");
        System.out.println("1.) fortfahren\n2.) eine andere Person auswählen, eine weitere Person erstellen");
        select = checkIntForProgramTermination();
        return select;
    }

    private String updateHaushalt(Verwaltung verwaltung, Scanner sc, int selectedHouseholdId, String selectedHouseholdName) {
        System.out.println("Geben Sie einen neuen Namen für den Haushalt ein:");
        String newName = checkForProgramTermination();
        verwaltung.householdDao.updateHousehold(selectedHouseholdId, newName);
        System.out.println("Der Name des Haushaltes wurde von " + selectedHouseholdName + " auf " + newName + " geändert");
        selectedHouseholdName = newName;  //reassign haushaltname
        return selectedHouseholdName;
    }

    private int askWhatToDoHouseholdPerson(Scanner sc, String selectedHouseholdName) {
        int select;
        System.out.println("* Bereich 2:");
        System.out.println("Was wollen Sie als nächstes machen? *ausgewählter Haushalt: " + selectedHouseholdName + " *");
        System.out.println("1.) Haushalt umbenennen");
        System.out.println("2.) Haushalt löschen");
        System.out.println("3.) Liste mit Personen anzeigen die diesem Haushalt zugewiesen sind");
        System.out.println("4.) Eine Person zu diesem Haushalt hinzufügen");
        select = checkIntForProgramTermination();
        return select;
    }

    private int continueHouseholdSection(Scanner sc) {
        int select;
        System.out.println("Möchten Sie mit diesem Haushalt fortfahren oder einen neuen Haushalt erstellen?");
        System.out.println("1.) fortfahren\n2.) einen weiteren Haushalt erstellen");
        select = checkIntForProgramTermination();
        return select;
    }

    private int addHousehold(Verwaltung verwaltung, Scanner sc) {
        int selectedHouseholdId = 0;
        System.out.println("Geben Sie den Namen des zu erstellenden Haushaltes ein:");
        String hName = "";
        do {
            hName = checkForProgramTermination();
            Household newHousehold = new Household(hName);
            try {
                selectedHouseholdId = verwaltung.householdDao.addHousehold(newHousehold);  //neuer haushalt in datenbank eini
            } catch (Exception e) {
                System.out.println("Dieser Haushaltsname existiert bereits. Geben Sie einen anderen Namen ein!");
            }
        }while (selectedHouseholdId == 0);
        selectedHouseholdId = verwaltung.householdDao.getHouseholdId(hName);  // get id from creates haushalt
        System.out.println("Sieh haben den Haushalt " + hName + " erstellt");
        return selectedHouseholdId;
    }

    private int askWhatToDoHouseholdStart(Scanner sc) {
        int select;
        System.out.println("* Bereich 1:");
        System.out.println("1.) Lassen Sie sich die Liste mit verfügbaren Haushalten anzeigen");
        System.out.println("2.) Hier können Sie einen neuen Haushalt erstellen");
        select = checkIntForProgramTermination();
        return select;
    }

    private int getHousehold(Verwaltung verwaltung, Scanner sc, int selectedHouseholdId) {
        int select;

        if (verwaltung.householdDao.getAllHouseholds().size() == 0) {
            System.out.println("In der Liste der verfügbaren Haushalte sind noch keine Haushalte enthalten");
        } else {
            System.out.print("Liste mit Haushalten: ");
            prindAllHousehold(verwaltung);
            System.out.println();
            System.out.println("Wählen Sie einen Haushalt aus den Sie verwenden möchten:");
            select = checkIntForProgramTermination();
            selectedHouseholdId = selectedHousehold(verwaltung, select);  //choose haushalt, get id

        }
        return selectedHouseholdId;
    }

    public void updatePet(Verwaltung verwaltung, int selectedPetId) {
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

    public int createPet(Verwaltung verwaltung, int selectedPersonId) {
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


    public int selectPet(Verwaltung verwaltung, int select, int selectedPersonId) {
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

    public void printPetsByPerson(Verwaltung verwaltung, int selectedPerson) {

        for (int i = 0; i < verwaltung.petDao.getAllPetsByPersonId(selectedPerson).size(); i++) {
            Pet p = verwaltung.petDao.getAllPetsByPersonId(selectedPerson).get(i);
            System.out.print((i + 1) + ".) " + p.getName() + " ");
        }
        System.out.println();
    }

    public int areYouSure() {
        Scanner sc = new Scanner(System.in);
        int areYouSure = 0;
        System.out.println("sind sie sicher?");
        System.out.println("1.) yes \n2.)no");
        areYouSure = checkIntForProgramTermination();
        System.out.println("san sa si do wiakli sicha?");
        System.out.println("1.) yes \n2.)no");
        areYouSure = checkIntForProgramTermination();
        System.out.println("sind sie sich da wirklich sicher?");
        System.out.println("1.) yes \n2.)no");
        areYouSure = checkIntForProgramTermination();
        System.out.println("sind sie sich da wirklich wirklich sicher?");
        System.out.println("1.) joooo \n2.)no");
        areYouSure = checkIntForProgramTermination();
        System.out.println("sind sie sich da wirklich wirklich wirklich wiiiiirklich sicher?");
        System.out.println("1.) lösch den schas jetzt herrst du wappla \n2.)no");
        areYouSure = checkIntForProgramTermination();

        return areYouSure;
    }

    public void updatePerson(Verwaltung verwaltung, int personId) {
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

    public int createPerson(Verwaltung verwaltung, int selectedHouseholdId) {

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

    public int selectPerson(Verwaltung verwaltung, int select, int selectedHouseholdId) {

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

    public void printPersonsFromHousehold(Verwaltung verwaltung, int selectedHouseholdId) {

        for (int i = 0; i < verwaltung.personDao.getPersonsByHousehold(selectedHouseholdId).size(); i++) {
            Person p = verwaltung.personDao.getPersonsByHousehold(selectedHouseholdId).get(i);
            System.out.print((i + 1) + ".) " + p.getFirstName() + " ");
        }
    }

    public int selectedHousehold(Verwaltung verwaltung, int select) {

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

    public void prindAllHousehold(Verwaltung verwaltung) {

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

    public void containsPerson(Household toSearch) {

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

    public String checkForProgramTermination() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        try {
            if (":q!".equals(input)) {
                throw new RuntimeException("Programm wird beendet.");
            }
        } catch (RuntimeException e) {
            System.out.println("Sie haben das Programm mit :q! beendet...");
            System.exit(0);
        }
        return input;
    }

    public int checkIntForProgramTermination() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        try {
            if (":q!".equals(input)) {
                throw new RuntimeException("Programm wird beendet.");
            }
        } catch (RuntimeException e) {
            System.out.println("Sie haben das Programm mit :q! beendet...");
            System.exit(0);
        }
        return Integer.parseInt(input);
    }


    public String checkForValidPersonName() {
        Boolean stay = true;
        String name = "";

        while (stay) {
            try {
                name = checkForProgramTermination();

                if (name.matches(".*\\d.*")) {
                    System.out.println("----- input has number ----- exit false");
                    throw new RuntimeException("Ungültiger Name: Der Name darf keine Zahlen enthalten.");
                } else {
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

    public void welcome() {

        printHeader();

        // Section 1
        printSection("* Bereich 1");
        printRow("Haushalte anzeigen", "Haushalt erstellen");

        // Section 2
        printSection("* Bereich 2");
        printRow("Haushalt umbenennen", "Haushalt löschen");
        printRow("Personen anzeigen", "Person hinzufügen");

        // Section 3
        printSection("* Bereich 3");
        printRow("Person bearbeiten", "Person löschen");
        printRow("Haustiere anzeigen", "Haustier hinzufügen");

        // Section 4
        printSection("* Bereich 4");
        printRow("Haustier bearbeiten", "Haustier löschen");

        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println();
        System.out.println("* Guten Tag, in diesem Programm werden Sie durch diese 4 oben gezeigte Bereiche geleitet.");
        System.out.println("* Zum Auswählen geben Sie die entsprechende Zahl ein und drücken Sie anschließend Enter.\n* Sie müssen einen Haushalt ausgewählt haben um zu den Personen zu gelangen.\n* Sowie eine Person ausgewählt sein muss um zu den Haustieren zu gelangen.");
        System.out.println("* Zum Verlassen des Programms geben Sie :q! ein.");
        System.out.println();
    }

    private void printHeader() {
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println("|               Herzlich Willkommen zur Haushaltsverwaltung                      |");
        //System.out.println("+--------------------------------------------------------------------------------+");
    }

    private void printSection(String sectionName) {
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println("\n" + sectionName);
        System.out.println("+--------------------------------------------------------------------------------+");
    }

    private void printRow(String action1, String action2) {
        System.out.printf("| %-38s | %-38s |\n", action1, action2);
    }
}
