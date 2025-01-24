/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Abdel
 */
public class DataAccessLayer {

    private static Connection connection;

    public static final int FIRST = 0;
    public static final int LAST = 1;
    public static final int NEXT = 2;
    public static final int PREVIOUS = 3;

    static {
        try {
            DriverManager.registerDriver(new ClientDriver());
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/database", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean insert(User user) throws SQLException {
        boolean finalResult = false;

        PreparedStatement statment;
        try {
            statment = connection.prepareStatement("INSERT INTO MYUSERS "
                    + "(ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, PHONE) VALUES (?, ?, ?, ?, ?, ?)",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statment.setInt(1, user.getId());
            statment.setString(2, user.getFirstName());
            statment.setString(3, user.getMiddleName());
            statment.setString(4, user.getLastName());
            statment.setString(5, user.getEmail());
            statment.setString(6, user.getPhone());

            int result = statment.executeUpdate();
            if (result > 0) {
                finalResult = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finalResult;
    }

    public static boolean delete(int id) throws SQLException {
        boolean finalResult = false;

        PreparedStatement statment;
        try {
            statment = connection.prepareStatement("DELETE FROM MYUSERS WHERE ID=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statment.setInt(1, id);

            int result = statment.executeUpdate();
            if (result > 0) {
                finalResult = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finalResult;
    }

    public static User select(int pos, int id) throws SQLException {

        PreparedStatement statment;
        try {
            statment = connection.prepareStatement("SELECT * FROM MYUSERS ",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = statment.executeQuery();
            while (rs.next()) {
                if (rs.getInt("ID") == id) {
                    break;
                }

            }
            if (pos == NEXT && rs.next()) {
                return new User(rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("MIDDLE_NAME"),
                        rs.getString("LAST_NAME"), rs.getString("EMAIL"), rs.getString("PHONE"));
            }
            if (pos == PREVIOUS && rs.previous()) {
                return new User(rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("MIDDLE_NAME"),
                        rs.getString("LAST_NAME"), rs.getString("EMAIL"), rs.getString("PHONE"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static User selectPos(int pos) throws SQLException {

        PreparedStatement statment;
        try {
            statment = connection.prepareStatement("SELECT * FROM MYUSERS ",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = statment.executeQuery();

            if (pos == FIRST && rs.first()) {
                return new User(rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("MIDDLE_NAME"),
                        rs.getString("LAST_NAME"), rs.getString("EMAIL"), rs.getString("PHONE"));
            }

            if (pos == LAST && rs.last()) {
                return new User(rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("MIDDLE_NAME"),
                        rs.getString("LAST_NAME"), rs.getString("EMAIL"), rs.getString("PHONE"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean update(User user) throws SQLException {
        boolean finalResult = false;

        PreparedStatement statment;
        try {
            statment = connection.prepareStatement("UPDATE MYUSERS SET "
                    + "FIRST_NAME=?, MIDDLE_NAME=?, LAST_NAME=?, EMAIL=?, PHONE=? "
                    + "WHERE ID=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            statment.setString(1, user.getFirstName());
            statment.setString(2, user.getMiddleName());
            statment.setString(3, user.getLastName());
            statment.setString(4, user.getEmail());
            statment.setString(5, user.getPhone());
            statment.setInt(6, user.getId());

            int result = statment.executeUpdate();
            if (result > 0) {
                finalResult = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finalResult;
    }
}
