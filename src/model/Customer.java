package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

    private int cid;
    private StringProperty firstName;
    private StringProperty lastName;

    public Customer(int cid, String firstName,String lastName){
        this.cid = cid;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    public void setColumn(int column,String data){
        switch (column){
            case 1:
                setFirstName(data);
                break;
            case 2:
                setLastName(data);
                break;
        }
    }
    /**
     * return a string with first and lastname
     * @return string with first and lastname
     */
    public String getNameString(){
        return getFirstName() + " " + getLastName();
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getName(){
        return firstName.get() + " " + lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
}
