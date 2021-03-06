package controller.dbqueries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Loan;
import model.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerQueries {
    /**
     * add a new customer
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @return the id of the customer
     */
    public static int addCustomer(String firstName,String lastName) {
        Connection con = null;
        PreparedStatement st1 = null;
        Statement st2 = null;
        try{
            con = Database.getConnection();
            st1 = con.prepareStatement("" +
                    "INSERT INTO customer (firstname,lastname) " +
                    "VALUES (?, ?);");
            st1.setString(1,firstName);
            st1.setString(2,lastName);
            // insert into database
            st1.executeUpdate();
            // get max id
            st2 = con.createStatement();
            ResultSet res = st2.executeQuery("" +
                    "SELECT max(cid) " +
                    "FROM customer;");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            ExceptionLog.write(e);
        } finally {
            try {
                if(st1 != null)
                    st1.close();
                if(st2 != null)
                    st2.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
        return 0;
    }
    /**
     * creates a list of all customers
     * @return a list of all customers
     */
    public static ObservableList<Customer> getCustomerList(boolean onlyActive){
        Connection con = null;
        Statement st = null;
        try{
            con = Database.getConnection();
            st = con.createStatement();
            String query;

            if(onlyActive)
                query =
                    "SELECT * " +
                    "FROM customer " +
                    "WHERE active = 1;";
            else
                query =
                    "SELECT * " +
                    "FROM customer;";
            ResultSet res = st.executeQuery(query);
            List<Customer> customers = new ArrayList<>();
            // loop over results
            while(res.next()){
                int cid = res.getInt(1);
                String firstName = res.getString(2);
                String lastName = res.getString(3);
                // create customer
                Customer c = new Customer(cid,firstName,lastName);
                customers.add(c);
            }
            return FXCollections.observableArrayList(customers);
        } catch (SQLException e) {
            ExceptionLog.write(e);
        } finally {
            try {
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
        return null;
    }
    /**
      * update the Customer
      * @param c Customer
     */
    public static void updateCustomer(Customer c){
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "UPDATE customer " +
                    "SET firstname = ?, lastname = ? " +
                    "WHERE cid = ?;");
            st.setString(1,c.getFirstName());
            st.setString(2,c.getLastName());
            st.setInt(3,c.getCid());
            st.executeUpdate();
        } catch (SQLException e) {
            ExceptionLog.write(e);
        } finally {
            try {
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
    }

    /**
     * Customer gets set to inactive
     * @param c the customer
     */
    public static void deleteCustomer(Customer c){
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "UPDATE customer " +
                    "SET firstname = '[deleted]', lastname = '', active = 0 " +
                    "WHERE cid = ?;");
            st.setInt(1,c.getCid());
            st.executeUpdate();
        } catch (SQLException e) {
            ExceptionLog.write(e);
        } finally {
            try {
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
    }
}
