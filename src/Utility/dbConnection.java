package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** This is the class for the Database Connection. */
public class dbConnection {

    /**JDBC parts*/
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ05XZK";

    /**JDBC URL*/
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone = SERVER";

    /**Driver and Connection Interface Reference*/
    private static final String MYSQLJDBCDRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    /**Username*/
    private static final String userName = "U05XZK";

    /**Password*/
    private static final String passWord = "53688635166";

    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDRIVER);
            connection = DriverManager.getConnection(jdbcURL, userName,passWord);
            System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException e){

            e.printStackTrace();

        }
        catch(SQLException e){

            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(){
        return connection;
    }

    /**Closes the Connection*/
    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("Closed");
        } catch (Exception e) {

        }
    }


}

