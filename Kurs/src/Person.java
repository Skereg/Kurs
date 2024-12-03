import java.io.Serializable;

abstract class Person implements Serializable {
    protected String name;
    protected String phone;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public abstract String getContactInfo();
}