public class Person {

    private String firstName;
    private String lastName;
    private Gender gender;
    private String birthDay;
    private Adress adress;

    private int id;

    public int getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    Person(String firstName, String lastName, Gender gender, String birthDay){
        this(firstName, lastName);
        this.gender = gender;
        this.birthDay = birthDay;
    }
    Person(String firstName, String lastName, Gender gender, String birthDay, Adress adress) {
        this(firstName, lastName, gender, birthDay);
        this.adress = adress;
    }

    //methode string to string
    public String toString() {
        //System.out.println(Person);

        String result = firstName + lastName + gender + birthDay + adress.toString();

        return result;
    }
}
