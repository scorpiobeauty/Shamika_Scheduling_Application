package Main;


import Utility.dbConnection;
import Utility.dbQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/** This is the main class and loads the login screen. */
public class Main extends Application {

    /** Loads the login page. */
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/LoginScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) throws SQLException {



        //ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("Europe")).forEach(System.out::println);





        dbConnection.startConnection();

        Connection connection = dbConnection.getConnection();

        dbQuery.setStatement(connection);
        Statement statement = dbQuery.getStatement();

        /*
        String country;
        Scanner keyboard  = new Scanner(System.in);
        System.out.print("Enter Country: ");
        country = keyboard.nextLine();

         */

        /*
        String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_updated_By) VALUES('US','2021-3-17 00:00:00', 'admin', 'admin')";


        statement.execute(insertStatement);

        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + "rows affected");
        else
            System.out.println("Nothing changed");
        */


        launch(args);
        dbConnection.closeConnection();
    }
}
