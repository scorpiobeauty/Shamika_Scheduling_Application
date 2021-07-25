package DAO;

import Model.Countries;
import Utility.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the class for the database countries. */

public class DBcountries {
    /** This method returns all fields in the countries database. */
    public static ObservableList<Countries> getAllCountries(){

        ObservableList<Countries> countrylist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                Countries country = new Countries(countryID, countryName);
                countrylist.add(country);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }


        return countrylist;
    }




}

