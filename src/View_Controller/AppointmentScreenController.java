package View_Controller;

import DAO.DBappointments;
import DAO.DBcontacts;
import DAO.DBcustomers;
import DAO.DBusers;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import Utility.TimeConversions;
import Utility.dbConnection;
import Utility.dbQuery;
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
import java.sql.*;
import java.time.*;
import java.util.ResourceBundle;

/** This is the class for the Appointment screen controller. */
public class AppointmentScreenController implements Initializable {

    Parent scene;
    Stage stage;

    private Appointments selectedAppt;
    int index;


    @FXML
    private TextField apptidbox;

    @FXML
    private TextField titlebox;

    @FXML
    private TextField descriptionbox;

    @FXML
    private ComboBox<Contacts> contactbox;

    @FXML
    private ComboBox<Appointments> typebox;

    @FXML
    private DatePicker startdate;

    @FXML
    private DatePicker enddate;

    @FXML
    private ComboBox<String> starttime;

    @FXML
    private ComboBox<String> endtime;

    @FXML
    private ComboBox<Appointments> locationbox;

    @FXML
    private ComboBox<Customers> customeridbox;

    @FXML
    private ComboBox<Users> useridbox;

    @FXML
    private Button addbutton;

    @FXML
    private Button updatebutton;

    @FXML
    private Button deletebutton;

    @FXML
    private Button cancelbutton;

    @FXML
    private TableView<Appointments> calendartable;

    @FXML
    private TableColumn<Appointments, Integer> apptidcol;

    @FXML
    private TableColumn<Appointments, String> titlecol;

    @FXML
    private TableColumn<Appointments, String> descriptcol;

    @FXML
    private TableColumn<Appointments, String> locationcol;

    @FXML
    private TableColumn<Contacts, String> contactcol;

    @FXML
    private TableColumn<Appointments, String> typecol;

    @FXML
    private TableColumn<Appointments, Date> startcol;

    @FXML
    private TableColumn<Appointments, Date> endcol;

    @FXML
    private TableColumn<Customers, Integer> custidcol;
    @FXML
    private TableColumn<Users, Integer> useridcol;

    @FXML
    private TextField typefield;

    @FXML
    private TextField locationfield;

    @FXML
    private RadioButton viewall;

    @FXML
    private ToggleGroup radiotogglegrp;

    @FXML
    private RadioButton viewmonth;

    @FXML
    private RadioButton viewweek;

    @FXML
    private Button SelectedApptbutton;

    @FXML
    private Button showbutton;

    /**
     * This method checks for blank fields.
     */
    private boolean checkBlanks() {
        if (titlebox.getText().isEmpty() || descriptionbox.getText().isEmpty() || contactbox.getValue() == null ||
                locationfield.getText().isEmpty() || typefield.getText().isEmpty() ||
                startdate.getValue() == null || starttime.getValue() == null || enddate.getValue() == null ||
                endtime.getValue() == null || customeridbox.getValue() == null || useridbox.getValue() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This will select the Appointment from the database to modify.
     */
    @FXML
    public void SelectedAppointment(ActionEvent event) throws IOException, SQLException {

        System.out.println("Selected appointment button Clicked");


            Appointments selectedAppt = calendartable.getSelectionModel().getSelectedItem();
            int apptID = selectedAppt.getApptID();



            if (selectedAppt == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You must click the Select Customer button first!");
                alert.showAndWait();

                return;
            } else {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Appointment.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                AppointmentScreenController controller = loader.getController();
                controller.getModAppt(calendartable.getSelectionModel().getSelectedItem());
                stage.show();

            }

            Connection connection = dbConnection.getConnection();

            dbQuery.setStatement(connection);
            Statement statement = dbQuery.getStatement();

            String SelectApptStatement = "SELECT * FROM appointments where Appointment_ID = " + apptID + "";

            System.out.println("Select Appointment statement: " + SelectApptStatement);

            statement.execute(SelectApptStatement);

            if (statement.getUpdateCount() > 0)
                System.out.println(statement.getUpdateCount() + "rows affected");
            else
                System.out.println("Appointment Selected");

        }

    /**
     * Adds a new Appointment to the database.
     */
    @FXML
    public void addnewappt(ActionEvent event) throws SQLException, IOException {

        System.out.println("add button clicked.");
        if (checkBlanks()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Check the Inputs");
            alert.setContentText("There is one or more blanks fields. Please fill out all of the fields.");
            alert.showAndWait();
            return;
        } else {
            //Timestamp for start
            System.out.println(startdate.getValue());
            System.out.println(starttime.getValue());
            LocalDate ldStart = startdate.getValue();
            LocalTime ltStart = LocalTime.parse(starttime.getValue());
            Timestamp tsStart = Timestamp.valueOf(LocalDateTime.of(ldStart, ltStart));
            System.out.println("Start time: " + tsStart);

            //Timestamp for end
            System.out.println(enddate.getValue());
            System.out.println(endtime.getValue());
            LocalDate ldEnd = enddate.getValue();
            LocalTime ltEnd = LocalTime.parse(endtime.getValue());
            Timestamp tsEnd = Timestamp.valueOf(LocalDateTime.of(ldEnd, ltEnd));
            System.out.println("End time: " + tsEnd);



                int id = 0;

                apptidbox.setText(String.valueOf(++id));
                String title = titlebox.getText();
                String description = descriptionbox.getText();
                String location = locationfield.getText();
                String type = typefield.getText();
                tsStart = Timestamp.valueOf(LocalDateTime.of(ldStart, ltStart));
                tsEnd = Timestamp.valueOf(LocalDateTime.of(ldEnd, ltEnd));
                int custID = Integer.parseInt(String.valueOf(customeridbox.getValue().getCustID()));
                int userID = useridbox.getSelectionModel().getSelectedItem().getUserID();
                int contactID = Integer.parseInt(String.valueOf(contactbox.getValue().getContactID()));
                String custName = String.valueOf(customeridbox.getValue());
                String userName = String.valueOf(useridbox.getValue());
                String contactName = String.valueOf(contactbox.getValue());


                //Converts business hours from est to local time
                ZonedDateTime starBusinessEst = ZonedDateTime.of(ldStart.getYear(), ldStart.getMonthValue(), ldStart.getDayOfMonth(), 8, 0, 0, 0, ZoneId.of("America/New_York"));
                ZonedDateTime endBusinessEst = ZonedDateTime.of(ldEnd.getYear(), ldEnd.getMonthValue(), ldEnd.getDayOfMonth(), 22, 0, 0, 0, ZoneId.of("America/New_York"));
                ZonedDateTime localStart = starBusinessEst.withZoneSameInstant(ZoneId.systemDefault());
                ZonedDateTime localEnd = endBusinessEst.withZoneSameInstant(ZoneId.systemDefault());

                //Outside business hours
                if (ltStart.isBefore(LocalTime.from(localStart)) || ltEnd.isAfter(LocalTime.from(localEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("OUTSIDE BUSINESS HOURS");
                    alert.setContentText("Business hours are 8AM to 10PM EST, including Weekends");
                    alert.showAndWait();
                    return;
                }

                //Checking for any overlapping appointments
                Appointments overlapAppt = DBappointments.checkOverlapAppt(tsStart, tsEnd, custID);
                if (overlapAppt != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("OVERLAPPING APPOINTMENT");
                    alert.setContentText("This appointment is overlapping another one. Please try again");
                    alert.showAndWait();
                    return;
                }


                    Appointments newAppt = new Appointments(id, title, description, location, type,  tsStart, tsEnd, custID, userID, contactID, custName, userName, contactName);


                    Connection connection = dbConnection.getConnection();

                    dbQuery.setStatement(connection);
                    Statement statement = dbQuery.getStatement();

                    String insertStatement = "INSERT INTO  appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                            "VALUES('" + title + "', '" + description + "', '" + location + "', '" + type + "', '" + TimeConversions.localUtc(tsStart) + "', '" + TimeConversions.localUtc(tsEnd) + "','" + userName + "', '" + userName + "' , '" + custID + "', '" + userID + "','" + contactID + "')";

                    System.out.println("Insert statement: " + insertStatement);

                    statement.execute(insertStatement);

                    if (statement.getUpdateCount() > 0)
                        System.out.println(statement.getUpdateCount() + "rows affected");
                    else
                        System.out.println("Nothing changed");


                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View_Controller/Appointment.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

            }
        }



        @FXML
        void apptidbox (ActionEvent event){

        }

        /** This method returns back to the main screen. */
        @FXML
        public void cancelbutton (ActionEvent event) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to cancel?");
            alert.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/Mainscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

        @FXML
        void contactbox (ActionEvent event){

        }

        @FXML
        void customeridbox (ActionEvent event){

        }

        /**Deletes the selected appointment from the table*/
        @FXML
        public void deleteappt (ActionEvent event) throws SQLException, IOException {

            System.out.println("Appointment delete Clicked");

            Appointments selectedAppt = calendartable.getSelectionModel().getSelectedItem();
            int apptId = selectedAppt.getApptID();

            if (selectedAppt == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You must select an appointment first!");
                alert.showAndWait();

                return;
            }


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected appointment?");
            alert.showAndWait();

            Connection connection = dbConnection.getConnection();

            dbQuery.setStatement(connection);
            Statement statement = dbQuery.getStatement();

            String deleteStatement = "DELETE FROM appointments where Appointment_ID= " + apptId + "";

            System.out.println("Delete statement: " + deleteStatement);

            statement.execute(deleteStatement);

            if (statement.getUpdateCount() > 0)
                System.out.println(statement.getUpdateCount() + "rows affected");
            else
                System.out.println("Nothing changed");


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("Appointment Deleted! | " + "Appointment ID: " + selectedAppt.getApptID() + " | Type: " + selectedAppt.getType());
            alert.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/Appointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

        @FXML
        void descriptionbox (ActionEvent event){

        }


        @FXML
        void locationbox (ActionEvent event){

        }

        @FXML
        void enddatecombobox (ActionEvent event){

        }

        @FXML
        void endtimecombobox (ActionEvent event){

        }


        @FXML
        void startdatepicker (ActionEvent event){

        }

        @FXML
        void starttimecombobox (ActionEvent event){

        }


        @FXML
        void titlebox (ActionEvent event){

        }

        @FXML
        void typebox (ActionEvent event){

        }


        @FXML
        public void showweekmonthallbutton (ActionEvent event){

            if (viewall.isSelected()) {
                System.out.println("View all button clicked");
                calendartable.setItems(DBappointments.getAllAppointments());
            } else {
                if (viewweek.isSelected()) {
                    System.out.println("View Week button clicked");

                    calendartable.setItems(DBappointments.getThisWeekAppts());


                }
            }
            if (viewmonth.isSelected()) {
                System.out.println("View Month button clicked");
                calendartable.setItems(DBappointments.getThisMonthAppts());

            }

        }


        /**This method updates the Appointment. */
        @FXML
        public void updateappt (ActionEvent event) throws SQLException, IOException {
            System.out.println("main part modify Clicked");

            if (checkBlanks()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Check the Inputs");
                alert.setContentText("Click on a appointment in the table first. Then click on the Select Appointment button. Reminder NO fields can be left blank. Click the Update button again to update.");
                alert.showAndWait();
                return;

            } else {

                int apptId = selectedAppt.getApptID();

                //Timestamp for start
                System.out.println(startdate.getValue());
                System.out.println(starttime.getValue());
                LocalDate ldStart = startdate.getValue();
                LocalTime ltStart = LocalTime.parse(starttime.getValue());
                Timestamp tsStart = Timestamp.valueOf(LocalDateTime.of(ldStart, ltStart));
                System.out.println("Start time: " + tsStart);

                //Timestamp for end
                System.out.println(enddate.getValue());
                System.out.println(endtime.getValue());
                LocalDate ldEnd = enddate.getValue();
                LocalTime ltEnd = LocalTime.parse(endtime.getValue());
                Timestamp tsEnd = Timestamp.valueOf(LocalDateTime.of(ldEnd, ltEnd));
                System.out.println("End time: " + tsEnd);

                //Converts business hours from est to local time
                ZonedDateTime starBusinessEst = ZonedDateTime.of(ldStart.getYear(), ldStart.getMonthValue(), ldStart.getDayOfMonth(), 8, 0, 0, 0, ZoneId.of("America/New_York"));
                ZonedDateTime endBusinessEst = ZonedDateTime.of(ldEnd.getYear(), ldEnd.getMonthValue(), ldEnd.getDayOfMonth(), 22, 0, 0, 0, ZoneId.of("America/New_York"));
                ZonedDateTime localStart = starBusinessEst.withZoneSameInstant(ZoneId.systemDefault());
                ZonedDateTime localEnd = endBusinessEst.withZoneSameInstant(ZoneId.systemDefault());

                //Outside business hours
                if (ltStart.isBefore(LocalTime.from(localStart)) || ltEnd.isAfter(LocalTime.from(localEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Outside Business Hours");
                    alert.setContentText("Business hours are 8AM to 10PM EST, including Weekends");
                    alert.showAndWait();
                    return;
                }


                int apptID = Integer.parseInt(apptidbox.getText());
                String title = titlebox.getText();
                String description = descriptionbox.getText();
                String location = locationfield.getText();
                String type = typefield.getText();
                tsStart = Timestamp.valueOf(LocalDateTime.of(ldStart, ltStart));
                tsEnd = Timestamp.valueOf(LocalDateTime.of(ldEnd, ltEnd));
                int custID = Integer.parseInt(String.valueOf(customeridbox.getValue().getCustID()));
                //int userID = Integer.parseInt(String.valueOf(useridbox.getValue().getUserID()));
                int userID = useridbox.getSelectionModel().getSelectedItem().getUserID();
                int contactID = Integer.parseInt(String.valueOf(contactbox.getValue().getContactID()));
                String custName = String.valueOf(customeridbox.getValue());
                String userName = String.valueOf(useridbox.getValue());
                String contactName = String.valueOf(contactbox.getValue());

                //Checking for any overlapping appointments
                Appointments overlapAppt = DBappointments.checkOverlapAppt(tsStart, tsEnd, custID);
                if (overlapAppt != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("OVERLAPPING APPOINTMENT");
                    alert.setContentText("This appointment is overlapping another one. Please try again");
                    alert.showAndWait();
                    return;

                }


                Appointments modAppt = new Appointments(apptID, title, description, location, type, tsStart, tsEnd, custID, userID, contactID, custName, userName, contactName);

                Connection connection = dbConnection.getConnection();

                dbQuery.setStatement(connection);
                Statement statement = dbQuery.getStatement();

                String updateStatement = "UPDATE appointments SET Title = '" + title + "', Description = '" + description + "', Location = '" + location + "', Type = '" + type + "', Start = '" + TimeConversions.localUtc(tsStart) + "', End = '" + TimeConversions.localUtc(tsEnd) + "', Customer_ID = '" + custID + "', User_ID = '" + userID + "', Contact_ID = '" + contactID + "' WHERE Appointment_ID = '" + apptId + "'";

                System.out.println("Update statement: " + updateStatement);

                statement.execute(updateStatement);

                if (statement.getUpdateCount() > 0)
                    System.out.println(statement.getUpdateCount() + "rows affected");
                else
                    System.out.println("Nothing changed");


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View_Controller/Appointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }

        @FXML
        void useridbox (ActionEvent event){

        }

        @FXML
        public void viewallbutton (ActionEvent event){


        }

        @FXML
        void viewmonthradio (ActionEvent event){

        }

        @FXML
        void viewweekradio (ActionEvent event){

        }


        /** This is a method created to pass data between the selected customer table and fills out the boxes.*/
        public void getModAppt (Appointments appointments){
            selectedAppt = appointments;

            apptidbox.setText(Integer.toString(appointments.getCustID()));
            titlebox.setText(appointments.getTitle());
            descriptionbox.setText(appointments.getDescription());
            locationfield.setText(appointments.getLocation());
            typefield.setText(appointments.getType());
            starttime.setValue(String.valueOf(appointments.getStart().toLocalDateTime().toLocalTime()));
            startdate.setValue(appointments.getStart().toLocalDateTime().toLocalDate());
            enddate.setValue(appointments.getStart().toLocalDateTime().toLocalDate());
            endtime.setValue(String.valueOf(appointments.getEnd().toLocalDateTime().toLocalTime()));


            ObservableList<Contacts> contacts = Contacts.getAllContacts();
            contactbox.setItems(contacts);
            Contacts contacts1 = null;
            for (Contacts co : DBcontacts.getAllContacts()) {
                if (co.getContactName().equals(selectedAppt.getContact()))
                    contacts1 = co;

            }
            contactbox.setValue(contacts1);


            ObservableList<Customers> customers = Customers.getAllCustomers();
            customeridbox.setItems(customers);
            Customers customers1 = null;
            for (Customers cu : DBcustomers.getAllCustomers()) {
                if (cu.getName().equals(selectedAppt.getCustomer()))
                    customers1 = cu;

            }
            customeridbox.setValue(customers1);


            ObservableList<Users> users = Users.getAllUsers();
            useridbox.setItems(users);
            Users users1 = null;
            for (Users us : DBusers.getAllUsers()) {
                if (us.getUserName().equals(selectedAppt.getUser()))
                    users1 = us;

            }
            useridbox.setValue(users1);
        }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Displays all appointments
        calendartable.setItems(DBappointments.getAllAppointments());

        selectedAppt = new Appointments(0,null,null,null,null,null,null,0,0,0,null,null,null);
        apptidcol.setCellValueFactory((new PropertyValueFactory<>("apptID")));
        titlecol.setCellValueFactory((new PropertyValueFactory<>("title")));
        descriptcol.setCellValueFactory((new PropertyValueFactory<>("description")));
        locationcol.setCellValueFactory((new PropertyValueFactory<>("location")));
        contactcol.setCellValueFactory((new PropertyValueFactory<>("contact")));
        typecol.setCellValueFactory((new PropertyValueFactory<>("type")));
        startcol.setCellValueFactory((new PropertyValueFactory<>("start")));
        endcol.setCellValueFactory((new PropertyValueFactory<>("end")));
        custidcol.setCellValueFactory((new PropertyValueFactory<>("customer")));
        useridcol.setCellValueFactory((new PropertyValueFactory<>("user")));


        contactbox.setPromptText("SELECT CONTACT");
        useridbox.setPromptText("SELECT USER");
        customeridbox.setPromptText("SELECT CUSTOMER");



        //Displays all contacts in combo box
        ObservableList<Contacts> contacts = Contacts.getAllContacts();
        contactbox.setItems(contacts);


        //Displays all customers in combo box
        ObservableList<Customers> customers = Customers.getAllCustomers();
        customeridbox.setItems(customers);

        //Displays all users in combo box
        ObservableList<Users> users = Users.getAllUsers();
        useridbox.setItems(users);


        //Populates the start and end time combo boxes
        LocalTime start = LocalTime.of(7, 0);
        LocalTime end = LocalTime.of(23, 00);

        while (start.isBefore(end.plusSeconds(1))) {

            starttime.getItems().add(String.valueOf(start));
            start = start.plusMinutes(30);
            endtime.getItems().add(String.valueOf(start));

        }
        starttime.getSelectionModel().select(String.valueOf(LocalTime.of(0, 0)));
        starttime.setPromptText("START TIME");
        starttime.getSelectionModel().selectFirst();
        endtime.getSelectionModel().select(String.valueOf(LocalTime.of(0,0)));
        endtime.setPromptText("END TIME");
        endtime.getSelectionModel().selectNext();


    }


}


