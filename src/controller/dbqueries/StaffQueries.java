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

        try {
            Connection con = Database.getConnection();
            PreparedStatement st = con.prepareStatement("" +
                    "INSERT INTO staff (sName,password) " +
                    "VALUES (?, ?);");
            st.setString(1,name);
            st.setString(2,hashedPw);
            st.executeUpdate();
        }catch (SQLException e){
            //PrintWriter pw = new PrintWriter("error.log");
            ExceptionLog.write(e);
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
        try {
            //does the user exist?
            if(!doesStaffMemberExist(name))
                throw new LoginException("username does not exist!",LoginException.USERNAME_NOT_EXISTING);
            //check password
            String hashedPw = getPassword(name);
            if(!checkPW(password,hashedPw))
                throw new LoginException("wrong password!",LoginException.WRONG_PASSWORD);

            return getIdOfStaff(name);

        }catch (SQLException e){
            ExceptionLog.write(e);
        }
        return 0;
    }
    /**
     * get the password of a staff member
     * @param name the name
     * @return the hashed password
     * @throws SQLException throws SQLException
     */
    public static String getPassword(String name) throws SQLException{
        Connection con = Database.getConnection();
        PreparedStatement st = con.prepareStatement("" +
                "SELECT password " +
                "FROM staff " +
                "WHERE sName = ?;");
        st.setString(1,name);
        ResultSet res = st.executeQuery();
        if(res.next())
            return res.getString(1);
        else {
            ExceptionLog.write(new Exception("staffMember not found"));
            return "";
        }
    }
    /**
     * get the id of a staff member
     * @param name the name
     * @return the id
     * @throws SQLException throws SQLException
     */
    public static int getIdOfStaff(String name) throws SQLException{
        Connection con = Database.getConnection();
        PreparedStatement st = con.prepareStatement("" +
                "SELECT sid " +
                "FROM staff " +
                "WHERE sName = ?;");
        st.setString(1,name);
        ResultSet res = st.executeQuery();
        if(res.next())
            return res.getInt(1);
        else {
            ExceptionLog.write(new Exception("staffMember not found"));
            return 0;
        }
    }
    /**
     * returns whether the staff member with the name exists
     * @param name the name
     * @return true if exists
     * @throws SQLException throws SQLException
     */
    public static boolean doesStaffMemberExist(String name) throws SQLException{
        Connection con = Database.getConnection();
        PreparedStatement st = con.prepareStatement("" +
                "SELECT * " +
                "FROM staff " +
                "WHERE sName = ?;");
        st.setString(1,name);
        ResultSet res = st.executeQuery();
        //if there are no results --> does not exist
        return res.next();
    }
    public static String hash(String password){
        // Hash a password for the first time
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static boolean checkPW(String candidate,String hashed){
        return BCrypt.checkpw(candidate, hashed);
    }
}
