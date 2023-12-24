package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Person {

    private String firstName;

    private String lastName;

    private String user_id;

    private String rank;

    public Person(){
        
    }

    public Person(String firstName, String lastName, String user_id, String rank){
        this.firstName = firstName;
        this.lastName = lastName;
        this.user_id = user_id;
        this.rank = rank;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return firstName + " " 
               + lastName + " "
               + user_id + " "
               + rank;
    }
}
