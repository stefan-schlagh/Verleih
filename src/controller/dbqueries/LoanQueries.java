package controller.dbqueries;

import model.Article;
import model.Customer;
import model.Loan;
import model.Staff;
import model.database.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanQueries {
    /**
     * Select all Loans using Inner Joins.
     * a where statement has to be added
     * getLoanList takes a ResultSet of this query
     */
    private static final String SELECT_LOANS =
            "SELECT " +
                "l.lid, " +
                "c.cid, c.firstName, c.lastName, " +
                "a.aid, a.aName, a.available, " +
                "s.sid, s.sName, " +
                "l.startDate, l.endDate, l.returned " +
            "FROM loan l " +
            "INNER JOIN customer c " +
                "ON l.cid = c.cid " +
            "INNER JOIN article a " +
                "ON l.aid = a.aid " +
            "INNER JOIN staff s " +
                "ON l.sid = s.sid ";
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

    /**
     * get all loans of a user
     * @param cid the uid of the Costumer
     * @return a List of all Loans
     */
    public static List<Loan> getCustomerLoans(int cid){
        Connection con = null;
        PreparedStatement st1 = null;
        try{
            con = Database.getConnection();
            st1 = con.prepareStatement("" +
                    SELECT_LOANS +
                    "WHERE l.cid = ?;");
            st1.setInt(1,cid);
            ResultSet res = st1.executeQuery();

            return getLoanList(res);

        } catch (SQLException e){
            ExceptionLog.write(e);
            return new ArrayList<>();
        } finally {
            try {
                if(st1 != null)
                    st1.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
    }
    /**
     * get all active (not returned) loans of a user
     * @param cid the uid of the Costumer
     * @return a List of all Loans
     */
    public static List<Loan> getActiveCustomerLoans(int cid){
        Connection con = null;
        PreparedStatement st1 = null;
        try{
            con = Database.getConnection();
            st1 = con.prepareStatement("" +
                    SELECT_LOANS +
                    "WHERE l.cid = ? AND returned = 0;");
            st1.setInt(1,cid);
            ResultSet res = st1.executeQuery();

            return getLoanList(res);

        } catch (SQLException e){
            ExceptionLog.write(e);
            return new ArrayList<>();
        } finally {
            try {
                if(st1 != null)
                    st1.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
    }
    /**
     * get all loans of a article
     * @param aid the id of the article
     * @return a List of all Loans
     */
    public static List<Loan> getArticleLoans(int aid){
        Connection con = null;
        PreparedStatement st1 = null;
        try{
            con = Database.getConnection();
            st1 = con.prepareStatement("" +
                    SELECT_LOANS +
                    "WHERE a.aid = ?;");
            st1.setInt(1,aid);
            ResultSet res = st1.executeQuery();

            return getLoanList(res);

        } catch (SQLException e){
            ExceptionLog.write(e);
            return new ArrayList<>();
        } finally {
            try {
                if(st1 != null)
                    st1.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                ExceptionLog.write(e);
            }
        }
    }
    /**
     * convert a ResultSet into a usable list.
     * use the query provided in SELECT_LOANS
     * @param res the ResultSet
     * @return a list with all loans contained in the resultSet
     * @throws SQLException SQLException
     */
    private static List<Loan> getLoanList(ResultSet res) throws SQLException{

        List<Loan> loanList = new ArrayList<>();

        while(res.next()){
            int i = 0;
            i++;
            int lid = res.getInt(i);

            i++;
            int cid = res.getInt(i);
            i++;
            String firstName = res.getString(i);
            i++;
            String lastName = res.getString(i);

            Customer customer = new Customer(cid,firstName,lastName);

            i++;
            int aid = res.getInt(i);
            i++;
            String aName = res.getString(i);
            i++;
            boolean aAvailable = res.getInt(i) != 0;

            Article article = new Article(aid,aName,aAvailable);

            i++;
            int sid = res.getInt(i);
            i++;
            String sName = res.getString(i);

            Staff staff = new Staff(sid,sName);

            i++;
            //LocalDate startDate = res.getDate(i).toLocalDate();
            LocalDate startDate = LocalDate.parse(res.getString(i));
            i++;
            LocalDate endDate = LocalDate.parse(res.getString(i));
            i++;
            boolean returned = res.getInt(i) != 0;

            Loan loan = new Loan(lid,customer,article,staff,startDate,endDate,returned);

            loanList.add(loan);
        }
        return loanList;
    }
    /**
     * Loan gets updated. only the fields endDate and returned can be updated
     * @param loan an instance of Loan
     */
    public static void updateLoan(Loan loan){
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "UPDATE loan " +
                    "SET " +
                        "endDate = ?, " +
                        "returned = ? " +
                    "WHERE lid = ?;");
            st.setString(1,localDateToString(loan.getEndDate()));
            st.setInt(2,loan.isReturned() ? 1 : 0);
            st.setInt(3,loan.getLid());

            st.executeUpdate();
        } catch (SQLException e){
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
