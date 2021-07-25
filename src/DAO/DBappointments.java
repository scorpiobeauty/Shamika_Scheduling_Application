package DAO;

import Model.Appointments;
import Model.ReportByMonth;
import Utility.TimeConversions;
import Utility.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/** This is the class for the database appointments. */
public class DBappointments {

 /** This method returns all fields in the appointment database. */
    public static ObservableList<Appointments> getAllAppointments() {

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID, customers.Customer_Name, users.User_Name, contacts.Contact_Name\n" +
                    "FROM appointments join customers on appointments.Customer_ID = customers.Customer_ID join users on appointments.User_ID = users.User_ID join contacts on appointments.Contact_ID = contacts.Contact_ID;";


            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int apptID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = TimeConversions.UTCtoLocal(resultSet.getTimestamp("Start"));
                Timestamp end = TimeConversions.UTCtoLocal(resultSet.getTimestamp("End"));
                int custID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                String customer = resultSet.getString("Customer_Name");
                String user = resultSet.getString("User_Name");
                String contact = resultSet.getString("Contact_Name");

                Appointments appointments = new Appointments(apptID, title, description, location, type, TimeConversions.localUtc(start), TimeConversions.localUtc(end), custID, userID, contactID, customer, user, contact);
                appointmentList.add(appointments);


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }


        return appointmentList;
    }


    /** This method gets the type of appointments from the database and sort them by months for the report. */
    public static ObservableList<ReportByMonth> getApptByMonthAndType(String selectedMonth) throws SQLException {

        ObservableList<ReportByMonth> monthAndTypeReport = FXCollections.observableArrayList();

        String selectAppt = "SELECT count(Title) as Count,Type,MONTHNAME(Start) as mn,MONTH(Start) as Month from appointments where monthname(Start) = '" + selectedMonth + "' group by MONTH(Start),mn,Type order by Month;";
        PreparedStatement ps = dbConnection.startConnection().prepareStatement(selectAppt);


        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {

            String month = resultSet.getString("Month");
            String type = resultSet.getString("Type");
            int count = resultSet.getInt("Count");

            ReportByMonth rbm = new ReportByMonth(month, type, count);
            monthAndTypeReport.add(rbm);

            }
        return monthAndTypeReport;
    }


    /**This method checks for overlapping appointments. */
    public static Appointments checkOverlapAppt(Timestamp start, Timestamp end, int custID) throws SQLException {
        Appointments apptOverlap = null;

        ObservableList<Appointments> apptList = DBcustomers.getCustAppt(custID);

        for (Appointments appointment : apptList) {
            if (start.after(appointment.getStart()) && start.before(appointment.getEnd()) ||
                    end.after(appointment.getStart()) && end.before(appointment.getEnd()) ||
                    start.before(appointment.getStart()) && end.after(appointment.getStart()) ||
                    start.equals(appointment.getStart()) && end.equals(appointment.getEnd()) ||
                    start.equals(appointment.getStart()) || end.equals(appointment.getStart())) {

                apptOverlap = appointment;
            }
        }
        return apptOverlap;
    }

    /** This method sort appointments by current week in the appointment table when the month radio button is selected. */
    public static ObservableList<Appointments> getThisWeekAppts() {

        ObservableList<Appointments> weekAppts = FXCollections.observableArrayList();

        try {
            String sql = "select appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID, customers.Customer_Name, users.User_Name, contacts.Contact_Name FROM appointments join customers on appointments.Customer_ID = customers.Customer_ID join users on appointments.User_ID = users.User_ID join contacts on appointments.Contact_ID = contacts.Contact_ID where Start > curdate() and Start < CURDATE() + interval 7 day;";

            Appointments apptWeek;

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                apptWeek = new Appointments(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start"),
                        resultSet.getTimestamp("End"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Contact_Name"));

                apptWeek.setStart(TimeConversions.localUtc(apptWeek.getStart()));
                apptWeek.setEnd(TimeConversions.localUtc(apptWeek.getEnd()));

                weekAppts.add(apptWeek);

            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }
        return weekAppts;
    }

    /** This method sort appointments by current month in the appointment table when the month radio button is selected. */
        public static ObservableList<Appointments> getThisMonthAppts(){

        ObservableList<Appointments> monthAppts = FXCollections.observableArrayList();

        try {
            String sql = "select appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Contact_ID, appointments.User_ID, appointments.Customer_ID, contacts.Contact_Name, users.User_Name, customers.Customer_Name FROM appointments join customers on appointments.Customer_ID = customers.Customer_ID join users on appointments.User_ID = users.User_ID join contacts on appointments.Contact_ID = contacts.Contact_ID where Start > curdate() and Start < CURDATE() + interval 30 day;";

            Appointments apptMonth;

            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                apptMonth = new Appointments(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start"),
                        resultSet.getTimestamp("End"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Contact_Name"));

                apptMonth.setStart(TimeConversions.localUtc(apptMonth.getStart()));
                apptMonth.setEnd(TimeConversions.localUtc(apptMonth.getEnd()));


                monthAppts.add(apptMonth);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return monthAppts;

    }


    /** This method deletes the selected appointment. */
    public static void deleteAppt(int apptID) throws SQLException {

        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = dbConnection.startConnection().prepareStatement(deleteStatement);
        ps.setInt(1, apptID);

        ps.execute();
    }
}
