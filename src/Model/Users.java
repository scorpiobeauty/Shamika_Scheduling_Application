package Model;

import DAO.DBusers;
import javafx.collections.ObservableList;

/** This is the class for the Users model. */
public class Users {

    private int userID;
    private String userName;
    private String password;

    /** The constructor for Users.*/
    public Users(int userID, String userName, String password){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /** This will return all of the Users from the database. */
    public static ObservableList<Users> getAllUsers() {
        return DBusers.getAllUsers();
    }

    /** The getter for User ID. */
    public int getUserID() {
        return userID;

    }
    /**
     * @param userID the UserID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** The getter for User Name. */
    public String getUserName(){
        return userName;
    }

    /**
     * @param userName the User Name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** The getter for User Password. */
    public String getPassword(){
        return password;
    }

    /**
     * @param password the Password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** This overrides the toString method for the combo box. */
    @Override
    public String toString() {
        return ("#" + Integer.toString(userID) + " " + userName);
    }
}
