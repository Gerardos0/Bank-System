package src.person;
/**
 * Represents a person with personal details such as name, date of birth,
 * address, and phone number.
 * 
 * @author Hannah Ayala
 */
public class Person{
    private String firstName;
    private String lastName;
    private String dob;
    private String address;
    private String phone;

    /**
     * Constructs a Person object with default values.
     * The default values for firstName, lastName, dob, address, and phone are set to empty strings.
     */
    public Person(){
        firstName = "";
        lastName = "";
        dob = "";
        address = "";
        phone = "";        
    }

    /**
     * Constructs a Person object with specified values for each attribute.
     *
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     * @param dob The date of birth of the person.
     * @param address The address of the person.
     * @param phone The phone number of the person.
     */
    public Person(String firstName, String lastName, String dob, String address, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Sets the first name of the person.
     *
     * @param name The first name to set.
     */
    public void setFirstName(String name) {
        this.firstName = name;
    }

    /**
     * Sets the last name of the person.
     *
     * @param name The last name to set.
     */
    public void setLastName(String name) {
        this.lastName = name;
    }

    /**
     * Sets the date of birth of the person.
     *
     * @param dob The date of birth to set.
     */
    public void setDOB(String dob) {
        this.dob = dob;
    }

    /**
     * Sets the address of the person.
     *
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the phone number of the person.
     *
     * @param phone The phone number to set.
     */
    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the first name of the person.
     *
     * @return The first name of the person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of the person.
     *
     * @return The last name of the person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the date of birth of the person.
     *
     * @return The date of birth of the person.
     */
    public String getDOB() {
        return dob;
    }

    /**
     * Gets the address of the person.
     *
     * @return The address of the person.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the phone number of the person.
     *
     * @return The phone number of the person.
     */
    public String getPhoneNumber() {
        return phone;
    }

    /**
     * Gets the full name of the person by combining the first and last names.
     *
     * @return The full name of the person.
     */
    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    public String toString(){
        return "Name: " + getName() + "\nDate of Birth: " + dob +  "\nAddress: " + address + "\nPhone Number: " + phone;
    }
}