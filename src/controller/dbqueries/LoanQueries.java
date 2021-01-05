package controller.dbqueries;

import model.database.Database;

import java.sql.*;
import java.time.LocalDate;

public class LoanQueries {
    /**
     * create a new loan
     * @param cid id of the customer
     * @param aid id of the article
     * @param sid id of the staff
     * @param startDate startDate of the loan
     * @param endDate endDate of the loan
     * @return the id of the loan
     */
    public static int createLoan(int cid, int aid, int sid, LocalDate startDate,LocalDate endDate){
        Connection con = null;
        PreparedStatement st1 = null;
        PreparedStatement st2 = null;
        Statement st3 = null;
        try {
            // add loan
            con = Database.getConnection();
            st1 = con.prepareStatement("" +
                    "INSERT INTO loan (cid,aid,sid,startDate,endDate,returned) " +
                    "VALUES (?, ?, ?, ?, ?, ?);");
            st1.setInt(1,cid);
            st1.setInt(2,aid);
            st1.setInt(3,sid);
            st1.setString(4,localDateToString(startDate));
            st1.setString(5,localDateToString(endDate));
            st1.setInt(6,0);
            st1.executeUpdate();
            // set article to not available
            st2 = con.prepareStatement("" +
                    "UPDATE article " +
                    "SET available = 0 " +
                    "WHERE aid = ?");
            st2.setInt(1,aid);
            st2.executeUpdate();
            // get and return loan id
            st3 = con.createStatement();
            ResultSet res = st3.executeQuery("" +
                    "SELECT max(lid) " +
                    "FROM loan;");
            res.next();
            return res.getInt(1);
        } catch (SQLException e){
            ExceptionLog.write(e);
        } finally {
            try {
                if(st1 != null)
                    st1.close();
                if(st2 != null)
                    st2.close();
                if(st3 != null)
                    st3.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
        return 0;
    }
    public static String localDateToString(LocalDate date){
        return Date.valueOf(date).toString();
    }
}
