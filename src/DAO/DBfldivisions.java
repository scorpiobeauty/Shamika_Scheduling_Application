package DAO;

import Model.FLDivisions;
import Utility.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the class for the database First Level Divisions. */
public class DBfldivisions {

    /** This method returns all fields in the first level divisions database. */
    private static final ObservableList<DBfldivisions> allDivisions = FXCollections.observableArrayList();

    public static ObservableList<FLDivisions> getAllDivisions(){

        ObservableList<FLDivisions> flDivisionslist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryID = resultSet.getInt("COUNTRY_ID");
                FLDivisions division = new FLDivisions(divisionID, divisionName,countryID);
                flDivisionslist.add(division);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return flDivisionslist;
    }


    /**This returns all of the divisions in the US. */
    private static final ObservableList<FLDivisions> usDivision = FXCollections.observableArrayList();

    public static ObservableList<FLDivisions> getAllUSDivisions () {
        try {
            String sql = "SELECT * FROM WJ05XZK.first_level_divisions\n" +
                    "Where COUNTRY_ID = 1;";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryID = resultSet.getInt("COUNTRY_ID");
                FLDivisions divisionUS = new FLDivisions(divisionID, divisionName, countryID);
                usDivision.add(divisionUS);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return usDivision;
    }

    /**This returns all of the divisions in the UK. */
    private static final ObservableList<FLDivisions> ukDivision = FXCollections.observableArrayList();

    public static ObservableList<FLDivisions> getAllUKDivisions () {
        try {
            String sql = "SELECT * FROM WJ05XZK.first_level_divisions\n" +
                    "Where COUNTRY_ID = 2;";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryID = resultSet.getInt("COUNTRY_ID");
                FLDivisions divisionUK = new FLDivisions(divisionID, divisionName, countryID);
                ukDivision.add(divisionUK);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ukDivision;
    }

    /**This returns all of the divisions in the Canada. */
    private static final ObservableList<FLDivisions> canDivision = FXCollections.observableArrayList();

    public static ObservableList<FLDivisions> getAllCanDivisions () {
        try {
            String sql = "SELECT * FROM WJ05XZK.first_level_divisions\n" +
                    "Where COUNTRY_ID = 3;";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryID = resultSet.getInt("COUNTRY_ID");
                FLDivisions divisionCA = new FLDivisions(divisionID, divisionName, countryID);
                canDivision.add(divisionCA);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return canDivision;
    }




}
