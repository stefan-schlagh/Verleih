package controller.dbqueries;

import model.database.Database;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffQueries {
    /**
     * add a staff member to the database
     * @param name the name of the staff member
     * @param password password
     */
    public static void addStaffMember(String name, String password){
        //hash pw
        String hashedPw = hash(password);

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "INSERT INTO staff (sName,password) " +
                    "VALUES (?, ?);");
            st.setString(1,name);
            st.setString(2,hashedPw);
            st.executeUpdate();
        } catch (SQLException e){
            //PrintWriter pw = new PrintWriter("error.log");
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
     * the name and the password are checked
     * @param name the name of the staff member
     * @param password the password of the staff member
     * @return the id of the staff member
     * @throws LoginException, if something went wrong
     * @see LoginException
     */
    public static int login(String name,String password) throws LoginException{
        //does the user exist?
        if(!doesStaffMemberExist(name))
            throw new LoginException("username does not exist!",LoginException.USERNAME_NOT_EXISTING);
        //check password
        String hashedPw = getPassword(name);
        if(!checkPW(password,hashedPw))
            throw new LoginException("wrong password!",LoginException.WRONG_PASSWORD);

        return getIdOfStaff(name);
    }
    /**
     * get the password of a staff member
     * @param name the name
     * @return the hashed password
     */
    public static String getPassword(String name){
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "SELECT password " +
                    "FROM staff " +
                    "WHERE sName = ?;");
            st.setString(1, name);
            ResultSet res = st.executeQuery();
            if (res.next())
                return res.getString(1);
            else {
                ExceptionLog.write(new Exception("staffMember not found"));
                return "";
            }
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
        return "";
    }
    /**
     * get the id of a staff member
     * @param name the name
     * @return the id
     */
    public static int getIdOfStaff(String name){
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "SELECT sid " +
                    "FROM staff " +
                    "WHERE sName = ?;");
            st.setString(1, name);
            ResultSet res = st.executeQuery();
            if (res.next())
                return res.getInt(1);
            else {
                ExceptionLog.write(new Exception("staffMember not found"));
                return 0;
            }
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
        return 0;
    }
    /**
     * returns whether the staff member with the name exists
     * @param name the name
     * @return true if exists
     */
    public static boolean doesStaffMemberExist(String name){
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "SELECT * " +
                    "FROM staff " +
                    "WHERE sName = ?;");
            st.setString(1, name);
            ResultSet res = st.executeQuery();
            //if there are no results --> does not exist
            return res.next();
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
        return false;
    }
    /**
     * set a new password
     * @param id the id of the staff members
     * @param password the new password
     */
    public static void setPassword(int id,String password){
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "UPDATE staff " +
                    "SET password = ?" +
                    "WHERE sid = ?;");
            st.setString(1, hash(password));
            st.setInt(2,id);
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
     * hash a password
     * @param password the password to be hashed
     * @return the hashed password
     */
    private static String hash(String password){
        // Hash a password for the first time
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    /**
     * check a password
     * @param candidate the unencrypted password to be checked
     * @param hashed the hashed password
     * @return is the password right?
     */
    private static boolean checkPW(String candidate,String hashed){
        return BCrypt.checkpw(candidate, hashed);
    }
}
