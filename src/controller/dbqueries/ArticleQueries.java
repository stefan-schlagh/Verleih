package controller.dbqueries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleQueries {
    /**
     * add a new article
     * @param name the name of the article
     * @return the id of the customer
     */
    public static int addArticle(String name) {
        Connection con = null;
        PreparedStatement st1 = null;
        Statement st2 = null;
        try{
            con = Database.getConnection();
            st1 = con.prepareStatement("" +
                    "INSERT INTO article (aName,available) " +
                    "VALUES (?, 1);");
            st1.setString(1,name);
            // insert into database
            st1.executeUpdate();
            // get max id
            st2 = con.createStatement();
            ResultSet res = st2.executeQuery("" +
                    "SELECT max(aid) " +
                    "FROM article;");
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
     * creates a list of all articles
     * @return a list of all articles
     */
    public static ObservableList<Article> getArticleList(){
        Connection con = null;
        Statement st = null;
        try{
            con = Database.getConnection();
            st = con.createStatement();
            ResultSet res = st.executeQuery("" +
                    "SELECT * " +
                    "FROM article;");
            List<Article> articles = new ArrayList<>();
            // loop over results
            while(res.next()){
                int aid = res.getInt(1);
                String name = res.getString(2);
                boolean available = res.getInt(3) != 0;
                // create customer
                Article a = new Article(aid,name,available);
                articles.add(a);
            }
            return FXCollections.observableArrayList(articles);
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
     * update the article
     * @param a article
     */
    public static void updateArticle(Article a){
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = Database.getConnection();
            st = con.prepareStatement("" +
                    "UPDATE article " +
                    "SET aName = ?, available = ? " +
                    "WHERE aid = ?;");
            st.setString(1,a.getName());
            st.setInt(2,a.isAvailable() ? 1 : 0);
            st.setInt(3,a.getAid());
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
