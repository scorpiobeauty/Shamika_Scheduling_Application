package Utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/** This is the class for the Database Query. */
public class dbQuery {

    /** This is my statement reference. */
    private static Statement statement;

    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();

    }

    public static Statement getStatement(){
        return statement;
    }



}
