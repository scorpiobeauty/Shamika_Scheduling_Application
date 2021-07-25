package DAO;

import Model.Appointments;
import Model.Customers;
import Utility.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the class for the database customers. */

public class DBcustomers {

    /** This method returns all fields in the customers database. */
    public static ObservableList<Customers> getAllCustomers(){

        ObservableList<Customers> customerlist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT customers.Customer_ID,customers.Customer_Name,customers.Address,customers.Postal_Code,customers.Phone,first_level_divisions.division, countries.Country,customers.Division_ID,first_level_divisions.COUNTRY_ID\n" +
                    "FROM customers\n" +
                    "join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID\n" +
                    "join countries on first_level_divisions.Country_ID = countries.country_id;";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int custID = resultSet.getInt("Customer_ID");
                String name = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postal = resultSet.getString("Postal_code");
                String phone = resultSet.getString("Phone");
                String division = resultSet.getString("Division");
                String country = resultSet.getString("Country");
                Customers customers = new Customers(custID, name, address, postal, phone, division, country);
                customerlist.add(customers);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }


        return customerlist;
    }

    public static ObservableList<Appointments> getCustAppt(int custID) {

            ObservableList<Appointments> custAppts = FXCollections.observableArrayList();
            DBappointments dBappointments = new DBappointments();

            for (Appointments appointments : DBappointments.getAllAppointments()){
                if (appointments.getCustID() == custID){
                    custAppts.add(appointments);
                }
            }
            return custAppts;
        }


}
