package DAO;

import Model.Appointments;
import Model.Contacts;
import Utility.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the class for the database contacts. */

public class DBcontacts {

    /** This method returns all fields in the contacts database. */
    public static ObservableList<Contacts> getAllContacts(){

        ObservableList<Contacts> contactlist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from contacts";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");
                Contacts contacts = new Contacts(contactID, contactName, email);
                contactlist.add(contacts);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return contactlist;
    }

    /** This method finds appointments based on the Contact ID. */
    public static ObservableList<Appointments> getApptsByContact(int contactID) {
        ObservableList<Appointments> contactApptResult = FXCollections.observableArrayList();
        DBappointments dBappointments = new DBappointments();

        for (Appointments appointment : DBappointments.getAllAppointments()){
            if (appointment.getContactID() == contactID){
                contactApptResult.add(appointment);
            }
        }
        return contactApptResult;
    }
}
