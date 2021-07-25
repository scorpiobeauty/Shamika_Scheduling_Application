package Model;

import DAO.DBappointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;

/** This is the class for the Appointment model. */
public class Appointments  {

    //variables
    private int apptID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int custID;
    private int userID;
    private int contactID;
    private String custName;
    private String userName;
    private String contactName;



    /**Appointments Constructor*/
    public Appointments(int apptID, String title, String description, String location, String type, Timestamp start, Timestamp end, int custID, int userID, int contactID, String custName, String userName, String contactName) {
        this.apptID = apptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custID = custID;
        this.userID = userID;
        this.contactID = contactID;
        this.custName = custName;
        this.userName = userName;
        this.contactName = contactName;

    }


    public Appointments(int apptID, String title, String description, String location, String type, Timestamp start, Timestamp end, int custID, int userID, int contactID) {

        this.apptID = apptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custID = custID;
        this.userID = userID;
        this.contactID = contactID;

    }


    /** This will return all of the Appointments from the database. */
    public static ObservableList<Appointments> getAllAppointments() {
        return DBappointments.getAllAppointments();
    }

    public void Appointments(String location) {
        this.location = location;

    }

    /** The getter for Appt ID. */
    public int getApptID() {
        return apptID;
    }

    /**
     * @param apptID the apptID to set
     */
    public void setApptID(Integer apptID) {
        this.apptID = apptID;
    }

    /** The getter for Appt Title. */
    public String getTitle(){
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** The getter for Appt Description. */
    public String getDescription(){
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** The getter for Appt location. */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }


    /** The getter for Appt Type. */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /** The getter for Start time.
     * @return*/
    public Timestamp getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }


    /** The getter for End time.
     * @return*/
    public Timestamp getEnd() {
        return end;
    }


    /**
     * @param end the end to set
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /** The getter for Customer ID. */
    public int getCustID() {
        return custID;
    }

    /**
     * @param custID the custID to set
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /** The getter for User ID. */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** The getter for Contact ID. */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param contactID the contactID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


    /** The getter for Customer Name. */
    public String getCustomer() {
        return custName;
    }

    /**
     * @param custName the customer to set
     */
    public void setCustomer(String custName) {
        this.custName = custName;
    }


    /** The getter for User Name. */
    public String getUser() {
        return userName;
    }

    /**
     * @param userName the user to set
     */
    public void setUser(String userName) {
        this.userName= userName;
    }


    /** The getter for Contact name. */
    public String getContact() {
        return contactName;
    }

    /**
     * @param contactName the contact to set
     */
    public void setContact(String contactName) {
        this.contactName = contactName;
    }



    /** This overrides the toString method for the combo box. */
    @Override
    public String toString() {
        return (type);
    }

    public static ObservableList<Appointments> getContactAppts (int contactID) throws SQLException {
        ObservableList<Appointments> contApptList = FXCollections.observableArrayList();
        DBappointments dbAppointments = new DBappointments();

        for (Appointments appointments : DBappointments.getAllAppointments()){
            if (appointments.getContactID() == contactID){
                contApptList.add(appointments);
            }
        }
        return contApptList;
    }



}
