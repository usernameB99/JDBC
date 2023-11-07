import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Connection connection = DatabaseConnector.getInstance();

        Verwaltung verwaltung = new Verwaltung(connection);

        verwaltung.doAllStuff(connection);



//        Connection connection = DatabaseConnector.getInstance();
//
//        Verwaltung verwaltung = new Verwaltung(connection);
//
//        PetDao pets = new PetDaoImpl(connection);
//        PersonDao person = new PersonDaoImpl(connection);
//        HouseholdDao household = new HouseholdDaoImpl(connection);
//
//        Scanner sc = new Scanner(System.in);
//        int select;
//        boolean cont = true;
//
//        do{
//            int selectedHousehold;
//
//            System.out.println("wählen sie:");
//            System.out.println("1.) Haushalt auswählen");
//            System.out.println("2.) Haushalt erstellen");
//            select = sc.nextInt();
//
//            if(select ==1){
//
//                if(household.getAllHouseholds().size() == 0){
//                    System.out.println("nix haushalt innen diese");
//                } else{
//                    for (Household p: household.getAllHouseholds()) {
//                        System.out.println(p.getName());
//                    }
//                }
//            }
//            else if (select == 2){
//                System.out.println("Eingabe haushalt name des zu erstellenden haushaltes");
//                sc.nextLine();
//                String hNmae = sc.nextLine();
//                Household newHousehold = new Household(hNmae);
//                household.addHousehold(newHousehold);
//            }
//
//            System.out.println("wählen sie:");
//            System.out.println("1.) Haushalt update");
//            System.out.println("2.) Haushalt delete");
//            select = sc.nextInt();
//        } while(cont);












//        Pet oliver = new Pet("Oli", 5, "Dackel", 50);
//
//        pets.addPet(oliver);
//
//        for (Pet p: pets.getAllPets()) {
//            System.out.println(p.getName());
//        }










        //verwaltung.start() hier methoden aufrufen die ich in verwaltung erstelle damit des programm rennt

    }
}
