package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static void main(String[] args) {
        Database d = new Database();
        d.init();
    }

    Connection con;

    //tabPane

    public Database(){
        init();
    }
    public void init(){
        try{
            // create connection
            con = DriverManager.getConnection("jdbc:sqlite:Verleih.db");
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
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
