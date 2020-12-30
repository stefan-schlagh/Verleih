package model.database;

import controller.dbqueries.ExceptionLog;
import controller.dbqueries.StaffQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static void main(String[] args) {
        Database d = new Database();
        d.init();

        Database.addCustomerData();
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:sqlite:Verleih.db");
    }
    public void init(){
        Connection con = null;
        try{
            // create connection
            con = getConnection();
            /*
                create tables
                    product
             */
            con.createStatement().execute("" +
                    "CREATE TABLE IF NOT EXISTS article (" +
                        "aid INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "aName TEXT" +
                    ");");
            // customer
            con.createStatement().execute("" +
                    "CREATE TABLE IF NOT EXISTS customer (" +
                        "cid INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "firstname TEXT, " +
                        "lastname TEXT" +
                    ");");
            // staff
            con.createStatement().execute("" +
                    "CREATE TABLE IF NOT EXISTS staff (" +
                        "sid INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "sName TEXT," +
                        "password TEXT" +
                    ");");
            //lends
            con.createStatement().execute("" +
                    "CREATE TABLE IF NOT EXISTS lends (" +
                        "lid INTEGER," +
                        "kid INTEGER," +
                        "aid INTEGER," +
                        "sid INTEGER," +
                        "startDate TEXT," +
                        "endDate TEXT," +
                        "returned INTEGER" +
                    ")");
            /*
                add admin account in staff
                    is admin already existing?
             */
            if(!StaffQueries.doesStaffMemberExist("admin")){
                StaffQueries.addStaffMember("admin","password");
            }
        } catch(SQLException e){
            ExceptionLog.write(e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
    }
    public static void addCustomerData(){
        Connection con = null;
        Statement st = null;
        try{
            con = Database.getConnection();
            st = con.createStatement();
            st.executeUpdate("" +
                    "INSERT INTO customer(firstname,lastname) " +
                    "VALUES " +
                        "('Fritz','Mustermann'), " +
                        "('Hans','Huber'), " +
                        "('Michael','Müller');");
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
