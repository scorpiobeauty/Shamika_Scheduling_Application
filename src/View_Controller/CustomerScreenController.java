package View_Controller;

import DAO.DBappointments;
import DAO.DBcountries;
import DAO.DBcustomers;
import DAO.DBfldivisions;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
import Model.FLDivisions;
import Utility.dbConnection;
import Utility.dbQuery;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/** This is the class for the Customer screen controller and Lambda expression in initialize.  */
public class CustomerScreenController implements Initializable {

    Parent scene;
    Stage stage;

    private Customers selectedCustomer;
    int index;


    @FXML
    private AnchorPane Customerscreen;

    @FXML
    private TextField postalfield;

    @FXML
    private TextField addressfield;

    @FXML
    private TextField namefield;

    @FXML
    private TextField phonefield;


    @FXML
    private TextField customerIDbox;

    @FXML
    private TextField postalcode;

    @FXML
    private TextField addressbox;

    @FXML
    private Label customeridlabel;

    @FXML
    private Label namelabel;

    @FXML
    private Label phonenumberlabel;

    @FXML
    private Label postalcodelabel;

    @FXML
    public ComboBox<FLDivisions> divisionbox;

    @FXML
    public ComboBox<Countries> countrybox;

    @FXML
    private Label countrylabel;

    @FXML
    private Label divisionlabel;

    @FXML
    private Button cancelbutton;

    @FXML
    private Button addbutton;

    @FXML
    private TableView<Customers> customerinfo;

    @FXML
    private TableColumn<Customers, Integer> custid;

    @FXML
    private TableColumn<Customers, String> custname;

    @FXML
    private TableColumn<Customers, String> custnumber;

    @FXML
    private TableColumn<Customers, String> custcountry;

    @FXML
    private TableColumn<Customers, Integer> custdivision;

    @FXML
    private TableColumn<Customers, String> custaddress;

    @FXML
    private TableColumn<Customers, String> custpostal;



    @FXML
    private Button deletebutton;

    @FXML
    private Button udatebutton;

    @FXML
    private Button selectbutton;

    /** This method checks for blank fields. */
    private boolean checkBlanks() {
        if (namefield.getText().isEmpty() || addressfield.getText().isEmpty() || countrybox.getValue() == null ||
                phonefield.getText().isEmpty() || postalfield.getText().isEmpty() ||
                divisionbox.getValue() == null ) {
            return true;
        } else {
            return false;
        }
    }

    /** This method gets the selects the Customer by the Customer ID. */
    @FXML
    public void SelectedCustomer(ActionEvent event) throws SQLException, IOException {

        System.out.println("Selected customer button Clicked");

        Customers selectedCustomer = customerinfo.getSelectionModel().getSelectedItem();
        int customerId = selectedCustomer.getCustID();
        String custName = namefield.getText();
        String address = addressfield.getText();
        String postal = postalfield.getText();
        String phone = phonefield.getText();
        String division = String.valueOf(divisionbox.getValue());
        String country = String.valueOf(countrybox.getValue());

        if(selectedCustomer == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Customer first!");
            alert.showAndWait();

            return;
        }

        else {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            CustomerScreenController controller = loader.getController();
            controller.getModCustomer(customerinfo.getSelectionModel().getSelectedItem());
            stage.show();

        }

        Connection connection = dbConnection.getConnection();

        dbQuery.setStatement(connection);
        Statement statement = dbQuery.getStatement();

        String SelectCustStatement = "SELECT Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers where Customer_ID = " + customerId + "";

        System.out.println("Select Customer statement: " + SelectCustStatement);

        statement.execute(SelectCustStatement);

        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + "rows affected");
        else
            System.out.println("Customer Selected");



    }

    /** This method updates the customer in the table. */
    @FXML
   public void Updatecustomer(ActionEvent event) throws SQLException, IOException {

        System.out.println("Update button clicked");

        if (checkBlanks()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Check the Inputs");
            alert.setContentText("Click on a Customer in the table first. Then click on the Select Customer button. Reminder NO fields can be left blank. Click the Update button again to update.");
            alert.showAndWait();
            return;
        } else {
            int customerId = selectedCustomer.getCustID();

            if (selectedCustomer == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You must select a Customer first!");
                alert.showAndWait();

                return;
            }

            System.out.println("Update button Clicked");

            int custID = Integer.parseInt(customerIDbox.getText());
            String custName = namefield.getText();
            String address = addressfield.getText();
            String postal = postalfield.getText();
            String phone = phonefield.getText();
            int divisionID = divisionbox.getValue().getDivisionID();


            Customers modCust = new Customers(custID, custName, address, postal, phone, divisionID);

            Connection connection = dbConnection.getConnection();

            dbQuery.setStatement(connection);
            Statement statement = dbQuery.getStatement();

            String updateStatement = "UPDATE customers SET Customer_Name = '" + custName + "', Address = '" + address + "', Postal_Code = '" + postal + "', Phone = '" + phone + "', Division_ID = '" + divisionID + "'  WHERE Customer_ID = " + customerId + "";

            System.out.println("Update statement: " + updateStatement);

            statement.execute(updateStatement);

            if (statement.getUpdateCount() > 0)
                System.out.println(statement.getUpdateCount() + "rows affected");
            else
                System.out.println("Nothing changed");

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }


    /** This button will add a new customer to the table. */
    @FXML
   public void addnewcustomer(ActionEvent event) throws SQLException, IOException {
        if (checkBlanks()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Check the Inputs");
            alert.setContentText("There is one or more blanks fields. Please fill out all of the fields.");
            alert.showAndWait();
            return;
        } else {
            int id = 0;

            customerIDbox.setText(String.valueOf(++id));
            String name = namefield.getText();
            String address = addressfield.getText();
            String postal = postalfield.getText();
            String phone = phonefield.getText();
            int divisionID = divisionbox.getValue().getDivisionID();
            String country = custcountry.getText();


            Customers newCust = new Customers(id, name, address, postal, phone, divisionID, country);


            Connection connection = dbConnection.getConnection();

            dbQuery.setStatement(connection);
            Statement statement = dbQuery.getStatement();

            String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code , Phone,  Created_By,  Last_Updated_By, Division_ID) " +
                    "VALUES('" + name + "', '" + address + "', '" + postal + "', '" + phone + "', " + "' admin', " + "  'admin' , '" + divisionID + "' )";

            System.out.println("Insert statement: " + insertStatement);

            statement.execute(insertStatement);

            if (statement.getUpdateCount() > 0)
                System.out.println(statement.getUpdateCount() + "rows affected");
            else
                System.out.println("Nothing changed");

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    /** This field is used to add an address. */
    @FXML
    void addressbox(ActionEvent event) {

    }

    /**Returns to the Main screen. */
    @FXML
    public void cancelbutton(ActionEvent event) throws IOException {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to cancel?");
            alert.showAndWait();


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/Mainscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


    }

    /** This is a combobox to select the country and filters the divisions based on the Country selection. */
    @FXML
    public void countrybox(ActionEvent event) {


        System.out.println("countrybox");
        System.out.println(countrybox.getValue());

        Countries selectCountry = countrybox.getSelectionModel().getSelectedItem();

        divisionbox.setItems(DBfldivisions.getAllDivisions().stream().filter(division -> division.getCountryID() ==
                selectCountry.getId()).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        divisionbox.getSelectionModel().selectFirst();

    }



    /** This table display a list of customers. */
    @FXML
    public void customertable(ActionEvent event) {

        customerinfo.refresh();

    }

    /** This button will delete a customer from the table and show an alert if there are associated appointments. */
    @FXML
    public void deletecustomer(ActionEvent event) throws SQLException, IOException {
        System.out.println("delete button clicked");


        Customers selectedCustomer = customerinfo.getSelectionModel().getSelectedItem();
        int customerId = selectedCustomer.getCustID();




        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Customer first!");
            alert.showAndWait();

            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected customer?");
            alert.showAndWait();
        }


        try {
            Connection connection = dbConnection.getConnection();

            dbQuery.setStatement(connection);
            Statement statement = dbQuery.getStatement();

            String deleteStatement = "DELETE FROM customers where Customer_ID= " + customerId + "";

            System.out.println("Delete statement: " + deleteStatement);

            statement.execute(deleteStatement);

            if (statement.getUpdateCount() > 0)
                System.out.println(statement.getUpdateCount() + "rows affected");
            else
                System.out.println("Nothing changed");


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Customer Removed.");
            alert.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (SQLException throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("CANNOT DELETE");
            alert.setContentText("Customer has an Associated Appointment. All associated appointments will be removed. Please select Delete again to remove the customer. ");
            alert.showAndWait();

            ObservableList<Appointments> listOfAppointments = DBappointments.getAllAppointments();

            //delete all appointments containing selected customer
            for (Appointments appointment : listOfAppointments) {
                if (appointment.getCustID() == selectedCustomer.getCustID()) {
                     DBappointments.deleteAppt(appointment.getApptID());
                }

            }


        }
    }

    @FXML
    void customerIDbox(ActionEvent event) {

    }



    @FXML
    void divisionbox(ActionEvent event) {

    }

    @FXML
    void namebox(ActionEvent event) {

    }

    @FXML
    void phonebox(ActionEvent event) {

    }

    @FXML
    void postalcodebox(ActionEvent event) {

    }



    /** This is a method created to pass data between the selected customer table and fills out the boxes.*/
    public void getModCustomer(Customers customer) {
        selectedCustomer = customer;

        customerIDbox.setText(Integer.toString(customer.getCustID()));
        namefield.setText(customer.getName());
        addressfield.setText(customer.getAddress());
        postalfield.setText(customer.getPostal());
        phonefield.setText(customer.getPhone());

        ObservableList<Countries> countryAll = Countries.getAllCountries();
        countrybox.setItems(countryAll);
        FLDivisions divisions = null;
        for (FLDivisions f: DBfldivisions.getAllDivisions()){
            if (f.getDivisionName().equals(customer.getDivision()))
                divisions = f;
        }
        divisionbox.setValue(divisions);
        Countries countries = null;
        for (Countries c : DBcountries.getAllCountries()){
            if (c.getId() == (divisions.getCountryID()))
                countries = c;
        }

        countrybox.setValue(countries);
        //divisionbox.setValue(divisions);

    }



    /** This method initializes the controller and the Lambda justification is used for simplification. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerinfo.setItems(DBcustomers.getAllCustomers());
        selectedCustomer = new Customers(0, null, null, null, null, null, null);

        //Used Lambda expression to populate the customer table
        custid.setCellValueFactory((new PropertyValueFactory<>("custID")));
        custname.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getName()));
        custnumber.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getPhone()));
        custcountry.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getCountry()));
        custdivision.setCellValueFactory((new PropertyValueFactory<>("division")));
        custaddress.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getAddress()));
        custpostal.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getPostal()));

        divisionbox.setPromptText("SELECT DIVISION");
        countrybox.setPromptText("SELECT COUNTRY");

        ObservableList<Countries> countryAll = Countries.getAllCountries();
        countrybox.setItems(countryAll);




    }

}
