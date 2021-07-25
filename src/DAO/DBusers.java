package DAO;

import Model.Appointments;
import Model.Users;
import Utility.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the class for the database users. */
public class DBusers {

    private static String loggedUser;

    /** This method returns all fields in the users database. */
    public static ObservableList<Users> getAllUsers(){

        ObservableList<Users> userlist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM users;";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int userID = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                Users users = new Users(userID, userName, password);
                userlist.add(users);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return userlist;
    }


    /** This method gets the user based on the User ID. */
    public static boolean getLoggedInUser(String username, String password) {

        try {

            String sql = "SELECT User_Name, Password from users";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("User_Name").equals(username) && resultSet.getString("Password").equals(password))

                    return true;


            }

            return false;


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }


    /** This method finds appointments based on the User ID. */
    public static ObservableList<Appointments> getApptsByUser(int userID) {
        ObservableList<Appointments> userApptResult = FXCollections.observableArrayList();
        DBappointments dBappointments = new DBappointments();

        for (Appointments appointments : DBappointments.getAllAppointments()){
            if (appointments.getUserID() == userID){
                userApptResult.add(appointments);
            }
        }
        return userApptResult;
    }


}
