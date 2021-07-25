package Model;

import DAO.DBfldivisions;

/** This is the class for the First Level Divisions model. */
public class FLDivisions extends DBfldivisions {



    private int divisionID;
    private String divisionName;
    private int countryID;

    /** The constructor for First-Level Divisions. */
    public FLDivisions(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }


    /** The getter for First-Level Divisions ID. */
    public int getDivisionID() {
        return divisionID;
    }

    /** The getter for First-Level Divisions Name. */
    public String getDivisionName(){
        return divisionName;
    }

    public int getCountryID(){
        return countryID;
    }


    /** This overrides the toString method for the combo box. */
    @Override
    public String toString() {
        return (divisionName);
    }
}
