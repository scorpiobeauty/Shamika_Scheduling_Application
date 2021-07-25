package Model;

import DAO.DBcountries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the class for the Countries model. */
public class Countries {

    private static final ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    private int id;
    private String name;

    /** The constructor for Countries. */
    public Countries(int id, String name){
        this.id = id;
        this.name = name;
    }

    /** This will return all of the Countries from the database. */
    public static ObservableList<Countries> getAllCountries() {
        return DBcountries.getAllCountries();

    }


    /** The getter for Countries ID.
     * @return*/
    public int getId() {
        return id;
    }


    /** The getter for Countries Name. */
    public String getName(){
        return name;
    }


    /** This overrides the toString method for the combo box. */
    @Override
    public String toString() {
        return (name);
    }


}
