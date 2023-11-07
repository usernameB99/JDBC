public class Pet {

    private String name;
    private int age;
    private String type;
    private int person_id;

    public int getId() {
        return person_id;
    }

    public void setId(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAnimalType() {
        return type;
    }

    public void setAnimalType(String animalType) {
        this.type = animalType;
    }

    public Pet(String name, int age, String animalType, int personID) {
        this.name = name;
        this.age = age;
        this.type = animalType;
        this.person_id = personID;
    }
}
