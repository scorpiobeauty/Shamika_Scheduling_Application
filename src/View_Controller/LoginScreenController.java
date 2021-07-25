package View_Controller;

import DAO.DBappointments;
import DAO.DBusers;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This is the class for the Login screen controller and Lambda expression in print methods. */
public class LoginScreenController implements Initializable {

    @FXML
    private MenuBar loginscreen;

    @FXML
    private TextField usernamebox;

    @FXML
    private TextField passwordbox;

    @FXML
    private Text loginlabel;

    @FXML
    private Text schedulinglabel;

    @FXML
    private Text usernamelabel;

    @FXML
    private Text passwordlabel;

    @FXML
    private TextField zoneidbox;

    @FXML
    private Text zoneidlabel;

    @FXML
    private Label showzone;

    @FXML
    private Button submit;

    @FXML
    private Button exit;

    Parent scene;
    Stage stage;

    private ZoneId zoneId = ZoneId.systemDefault();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    Locale locale = Locale.getDefault();


    /**
     * Exits the application.
     */
    @FXML
    public void exitbutton(ActionEvent event) {

        System.out.println("system exit button clicked");
        System.exit(0);

    }

    /**
     * This field accepts a password.
     */
    @FXML
    public void passwordboxfield(ActionEvent event) {


    }

    /**
     * This will take you to the Main screen.
     * @return
     */
    @FXML
    public boolean submitbutton(ActionEvent event) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("main/lang", Locale.getDefault());

        if (usernamebox.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Username field blank");
            alert.setContentText("Please enter a Username.");
            alert.showAndWait();

        }
        if (passwordbox.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password field blank");
            alert.setContentText("Please enter a Password.");
            alert.showAndWait();
        }

        String username = usernamebox.getText();
        String password = passwordbox.getText();

        boolean validLogin = DBusers.getLoggedInUser(username, password);

        if(validLogin) {

            if (upcomingAppointments15min().size()>=1){
                for (Appointments appt:
                        upcomingAppointments15min()) {

                    if(Locale.getDefault().toString().equals("en_US")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WELCOME");
                    alert.setContentText("You have an Upcoming Appointment in 15mins! "+ "Appointment ID: "+ appt.getApptID()+" | Start: "+ appt.getStart());
                    alert.showAndWait();
                    }
                    if(Locale.getDefault().toString().equals("fr_FR")) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("BIENVENU");
                        alert.setContentText("Vous avez un rendez-vous à venir dans 15 minutes! "+ "ID de rendez-vous: "+ appt.getApptID()+" | Démarrer: "+ appt.getStart());
                        alert.showAndWait();


                    }

                }
            } else {

                if(Locale.getDefault().toString().equals("en_US")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WELCOME");
                    alert.setContentText("There are no Upcoming Appointments");
                    alert.showAndWait();
                }
                if(Locale.getDefault().toString().equals("fr_FR")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BIENVENU");
                    alert.setContentText("Il n'y a pas de rendez-vous à venir");
                    alert.showAndWait();

                }
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/mainscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            fileLogSuccess(username);
        }
        else {
            if(Locale.getDefault().toString().equals("en_US")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Incorrect username and/or password. Please try again.");
                alert.showAndWait();

                fileLogInvalid(username);
            }
            if(Locale.getDefault().toString().equals("fr_FR")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INCORRECT");
                alert.setContentText("Nom d'utilisateur et / ou mot de passe incorrects. Veuillez réessayer.");
                alert.showAndWait();

                fileLogInvalid(username);
            }

            }

        return false;
    }


    /**
     * This field accepts a username.
     */
    @FXML
    public void usernameboxfield(ActionEvent event) {


    }


    /** This creates the txt file and logs the successful login attempts/ The justification for using Lambda code here is to reduce the amount of code. */
    public static void fileLogSuccess(String username) {

        try {
            String loginLog = "login_activity.txt";
            File file = new File(loginLog);
            FileWriter fw = new FileWriter(loginLog, true);
            PrintWriter results = new PrintWriter(fw);
            LocalDateTime localDateTime = LocalDateTime.now();

            results.println("User: " + username +  " Successfully logged in at: " + Timestamp.valueOf(localDateTime) );
            results.close();
            //Lambda expression prints out message when users login is successful.
            new Thread(() -> System.out.println(username + " Logged Successfully"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /** This creates the txt file and logs the Unsuccessful login attempts / The justification for using Lambda code here is to reduce the amount of code. */
    public static void fileLogInvalid(String username) {

        try {
            String loginLog = "login_activity.txt";
            File file = new File(loginLog);
            FileWriter fw = new FileWriter(loginLog, true);
            PrintWriter results = new PrintWriter(fw);
            LocalDateTime localDateTime = LocalDateTime.now();

            results.println("User: " + username +  " Unsuccessful log in at: " + Timestamp.valueOf(localDateTime) );
            results.close();
            //Lambda expression prints out message when users login is unsuccessful.
            new Thread(() -> System.out.println(username + "Login Invalid"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static DBusers user = new DBusers();


    /** This method looks for appointments happening in the next 15 mins. */
    public ObservableList<Appointments> upcomingAppointments15min() {

        ObservableList<Appointments> allAppointments = DBappointments.getAllAppointments();
        ObservableList<Appointments> upcomingAppointments = FXCollections.observableArrayList();
        if (allAppointments!= null){

            for (Appointments appt : allAppointments){
                LocalDateTime start = appt.getStart().toLocalDateTime();
                LocalDateTime now = Timestamp.from(Instant.now()).toLocalDateTime();

                if (start.isBefore(now.plusMinutes(30))) {
                    if (start.isAfter(now)){
                        upcomingAppointments.add(appt);
                    }
                }

            }

        }
        return upcomingAppointments;

    }

    /** Initializes the controller. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ResourceBundle rb = ResourceBundle.getBundle("main/lang", Locale.getDefault());

            usernamelabel.setText(rb.getString("usernamelabel"));
            passwordlabel.setText(rb.getString("passwordlabel"));
            zoneidlabel.setText(rb.getString("zoneidlabel"));
            schedulinglabel.setText(rb.getString("schedulinglabel"));
            loginlabel.setText(rb.getString("loginlabel"));
            submit.setText(rb.getString("submitbutton"));
            exit.setText(rb.getString("exitbutton"));

        } catch (MissingResourceException missingResourceException) {
            missingResourceException.printStackTrace();
        }


        LocalDate parisDate = LocalDate.of(2021, 07, 17);
        LocalTime parisTime = LocalTime.of(3, 50);
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime parisZoneDate = ZonedDateTime.of(parisDate, parisTime, parisZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        Instant parisToGMT = parisZoneDate.toInstant();
        ZonedDateTime parisToLocal = parisZoneDate.withZoneSameInstant(localZoneId);
        ZonedDateTime GMTtoLocalZDT = parisToGMT.atZone(localZoneId);

        System.out.println("Local: " + ZonedDateTime.now());
        System.out.println("Paris: " + parisZoneDate);
        System.out.println("Paris->GMT: " + parisToGMT);
        System.out.println("GMT to Local: " + GMTtoLocalZDT);
        System.out.println("GMT to LocalDate: " + GMTtoLocalZDT.toLocalDate());
        System.out.println("GMT to LocalTime: " + GMTtoLocalZDT.toLocalTime());

        String date = String.valueOf(GMTtoLocalZDT.toLocalDate());
        String time = String.valueOf(GMTtoLocalZDT.toLocalTime());
        String dateTime = date + " " + time;


        System.out.println(dateTime);


        showzone.setText(String.valueOf(localZoneId));


    }


    }






