package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This is the class for the Report Main screen controller. */
public class ReportMainScreenController implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private Button totalappt;

    @FXML
    private Button contanctschedule;

    @FXML
    private Button customerlocation;

    @FXML
    private Button backbutton;


    @FXML
    private Button userschedules;

    /**Goes to Contact Report Screen. */
    @FXML
    public void contactschedreport(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ReportContacts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /** Goes to User Schedule screen. */
    @FXML
    public void userschedulescreen(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ReportUserAppt.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /**Returns to Main Screen. */
    @FXML
   public void returntomain(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to return?");
        alert.showAndWait();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/Mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Goes to the Appointment Total Report screen. */
    @FXML
    public void totalapptscreen(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ReportTotalAppt.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
