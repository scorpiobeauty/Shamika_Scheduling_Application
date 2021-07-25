package Model;

import DAO.DBcustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the class for the Customers model. */
public class Customers {

    public ObservableList<Appointments> assocAppointments = FXCollections.observableArrayList();


    private int custID;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private String division;
    private String country;
    private int divisionID;

    /** The constructor for Customers. */
    public Customers(int custID, String name, String address, String postal, String phone, String division, String country) {
        this.custID = custID;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.division = division;
        this.country = country;

    }

    public Customers(int custID, String custName, String address, String postal, String phone, FLDivisions division) {
        this.custID = custID;
        this.name = custName;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.division = String.valueOf(division);
    }

    public Customers(int id, String name, String address, String postal, String phone, int divisionID, String country) {

    }

    public Customers(int custID, String custName, String address, String postal, String phone, int divisionID) {
    }


    /** This will return all of the Customers from the database. */
    public static ObservableList<Customers> getAllCustomers() {
        return DBcustomers.getAllCustomers();
    }

    public static void addCust(Customers newCust) {
    }


    /** The getter for Customer ID. */
    public int getCustID() {
        return custID;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.custID = id;
    }

    /** The getter for Customer Name. */
    public String getName(){
        return name;
    }



    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** The getter for Customer Address. */
    public String getAddress(){
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** The getter for Customer Postal Code. */
    public String getPostal(){
        return postal;
    }

    /**
     * @param postal the postal to set
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /** The getter for Customer Phone. */
    public String getPhone(){
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    public ObservableList<Appointments> getAssocAppointments() {
        return assocAppointments;
    }

    /** The getter for Division.
     * @return*/
    public String getDivision(){
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /** The getter for Country. */
    public String getCountry(){
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }



    /** This overrides the toString method for the combo box. */
    @Override
    public String toString() {
        return ("#" +Integer.toString(custID) + " " + name);
    }


    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
