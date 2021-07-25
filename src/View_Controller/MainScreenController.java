package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This is the class for the Main screen controller. */
public class MainScreenController implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private AnchorPane mainscreen;

    @FXML
    private Button customers;

    @FXML
    private Button reportsbutton;


    @FXML
    private Button logout;

    /**Takes you to the Appointment screen. */
    @FXML
    public void appointmentscreenbutton(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Takes you to the Customer screen. */
    @FXML
    public void customerscreenbutton(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This button goes to the Report screen. */
    @FXML
    public void reportscreenbutton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ReportMainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**Returns to Login page. */
    @FXML
    public void returntologin(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }
}
