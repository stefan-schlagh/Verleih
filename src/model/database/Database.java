package model.database;

import controller.dbqueries.StaffQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static void main(String[] args) {
        Database d = new Database();
        d.init();
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:sqlite:Verleih.db");
    }
    public void init(){
        try{
            // create connection
            Connection con = getConnection();
            /*
                create tables
                    product
             */
            con.createStatement().execute("" +
                    "CREATE TABLE IF NOT EXISTS article (" +
                        "pid INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "pName TEXT" +
                    ");");
            // customer
            con.createStatement().execute("" +
                    "CREATE TABLE IF NOT EXISTS customer (" +
                        "cid INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cName TEXT" +
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
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
