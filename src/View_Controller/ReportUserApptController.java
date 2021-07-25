package View_Controller;

import DAO.DBappointments;
import DAO.DBusers;
import Model.Appointments;
import Model.Customers;
import Model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/** This is the class for the Report User Appointment screen controller. */
public class ReportUserApptController implements Initializable {

    Parent scene;
    Stage stage;


    @FXML
    private TableView<Appointments> userReportTable;

    @FXML
    private TableColumn<Appointments, Integer> apptidcol;

    @FXML
    private TableColumn<Appointments, String> titlecol;

    @FXML
    private TableColumn<Appointments, String> typecol;

    @FXML
    private TableColumn<Appointments, String> descriptioncol;

    @FXML
    private TableColumn<Appointments, Date> startcol;

    @FXML
    private TableColumn<Appointments, Date> endcol;

    @FXML
    private TableColumn<Customers, Integer> customeridcol;

    @FXML
    private Button backbutton;

    @FXML
    private ComboBox<Users> userbox;

    @FXML
    private Button showresults;

    /** Returns to Report Main Screen. */
    @FXML
   public void returntoreportscreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to return?");
        alert.showAndWait();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ReportMainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Shows the results of the selected User. */
    @FXML
    public void showresultsbutton(ActionEvent event) {

        System.out.println("Show button clicked");

        int selectedUser = userbox.getSelectionModel().getSelectedItem().getUserID();

        userReportTable.setItems(DBusers.getApptsByUser(selectedUser));

    }

    @FXML
    void usercombobox(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userReportTable.setItems(DBappointments.getAllAppointments());
        userbox.setItems(DBusers.getAllUsers());
        userbox.getSelectionModel().selectFirst();

        apptidcol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titlecol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startcol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endcol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customeridcol.setCellValueFactory(new PropertyValueFactory<>("customer"));



    }


}

