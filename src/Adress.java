public class Adress {

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String street;
    private String nr;
    private String plz;
    private String city;


    Adress (String street, String nr, String plz, String city) {
        this.street = street;
        this.nr = nr;
        this.plz = plz;
        this.city = city;

    }

    public String getFullAddress() {
        return street + " " + nr + ", " + plz + " " + city;
    }

    public String toString() {
        //System.out.println(Person);

        String result = street + nr + plz + city;

        return result;
    }

}
