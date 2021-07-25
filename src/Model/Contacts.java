package Model;

import DAO.DBcontacts;
import javafx.collections.ObservableList;

/** This is the class for the Contacts model. */
public class Contacts {

    private int contactID;
    private String contactName;
    private String email;

    /** The constructor for Contacts. */
    public Contacts(int contactID, String contactName, String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /** This will return all of the Contacts from the database. */
    public static ObservableList<Contacts> getAllContacts() {
        return DBcontacts.getAllContacts();
    }


    /** The getter for Contact ID. */
    public int getContactID() {
        return contactID;
    }

    /** The getter for Contact Name. */
    public String getContactName(){
        return contactName;
    }

    /** The getter for Contact Email. */
    public String getEmail(){
        return email;
    }

    /** This overrides the toString method for the combo box. */
    @Override
    public String toString() {
        return (contactName);
    }



}
