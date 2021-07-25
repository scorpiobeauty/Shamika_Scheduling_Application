package View_Controller;

import DAO.DBappointments;
import Model.Appointments;
import Model.ReportByMonth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This is the class for the Report Total Appointments screen controller. */
public class ReportTotalApptController implements Initializable {

    Parent scene;
    Stage stage;


    @FXML
    private TableColumn<Appointments, String> monthcol;

    @FXML
    private TableColumn<Appointments, String> typecol;

    @FXML
    private TableColumn<Appointments, Integer> countcol;

    @FXML
    private TableView<ReportByMonth> reportTable;

    @FXML
    private ComboBox<String> monthbox;

    @FXML
    private Button viewmonth;

    @FXML
    private Button backbutton;

    @FXML
    private ComboBox<String> typebox;

    @FXML
    private Button viewtype;


    /**Returns to Report Main Screen. */
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


    /** Displays the list of months. */
    @FXML
   public void monthcombobox(ActionEvent event) {

    }




    /** Generates a report based on month selected. */
    @FXML
    public void viewmonthbutton(ActionEvent event) throws SQLException {
        System.out.println("View month button clicked");

        String selectedMonth = monthbox.getSelectionModel().getSelectedItem().toUpperCase();
        //String selectedType = typebox.getSelectionModel().getSelectedItem();

        reportTable.setItems(DBappointments.getApptByMonthAndType(selectedMonth));


    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        monthcol.setCellValueFactory((new PropertyValueFactory<>("month")));
        typecol.setCellValueFactory((new PropertyValueFactory<>("type")));
        countcol.setCellValueFactory((new PropertyValueFactory<>("count")));


        //MONTHS
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March",
                "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthbox.setItems(months);
        monthbox.getSelectionModel().selectFirst();


    }


}