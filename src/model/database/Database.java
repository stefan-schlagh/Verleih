package model.database;

import controller.dbqueries.ExceptionLog;
import controller.dbqueries.StaffQueries;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static void main(String[] args) {
        init();
        addData();
    }
    /**
     * get a connection to the database
     * @return a connection to the database
     * @throws SQLException database not found
     */
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:sqlite:Verleih.db");
    }
    /**
     * initialize the database
     */
    public static void init(){
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
                        "aName TEXT, " +
                        "available INTEGER " +
                    ");");
            // customer
            con.createStatement().execute("" +
                    "CREATE TABLE IF NOT EXISTS customer (" +
                        "cid INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "firstname TEXT, " +
                        "lastname TEXT, " +
                        "active INTEGER DEFAULT 1" +
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
                    "CREATE TABLE IF NOT EXISTS loan (" +
                        "lid INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cid INTEGER," +
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
    /**
     * add data
     */
    public static void addData(){
        addCustomerData();
        addArticleData();
    }
    /**
     * add some customers
     */
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
    /**
     * add some articles
     */
    public static void addArticleData(){
        Connection con = null;
        Statement st = null;
        try{
            con = Database.getConnection();
            st = con.createStatement();
            st.executeUpdate("" +
                    "INSERT INTO article(aName,available) " +
                    "VALUES " +
                    "('Buch 1',1), " +
                    "('Buch 2',1), " +
                    "('Buch 3',1);");
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
     * delete Database
     */
    public static void delete(){

        try {
            File f = new File("Verleih.db");
            //if file exists --> delete
            if (f.exists())
                if(!f.delete())
                    throw new IOException("Verleih.db could not be deleted!");
        } catch (IOException e){
            ExceptionLog.write(e);
        }
    }
}
