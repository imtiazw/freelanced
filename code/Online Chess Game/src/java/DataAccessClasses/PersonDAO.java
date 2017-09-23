/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccessClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ALI
 */
public class PersonDAO {
    
    Connection connection;

    public PersonDAO() throws SQLException, ClassNotFoundException {
        establishConnection();
    }
    
    public boolean newRating(String userId, int newRating) throws SQLException{
        boolean result = false;
        int rowsUpdated = 0;
        
        String sql = "UPDATE [Player] "
                + "SET [player_rating] = " + newRating
                + " WHERE [playerId] = '" + userId + "'";
        
        try{
            Statement statement = null;
            statement = connection.createStatement();
            rowsUpdated = statement.executeUpdate(sql);
        }catch(SQLException ex){
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        
        if(rowsUpdated > 0){
            result = true;
        }
        
        return result;
    }
    
    public boolean isUserIdRegistered(String sql, String userId) throws SQLException{
        boolean result = false;
        
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        
            if(resultSet.next() == true){
                if(resultSet.getString("userId").equals(userId) == true){
                    result = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        return result;
    }
    
    public boolean isPrimaryEmailRegistered(String sql, String primaryEmail) throws SQLException{
        boolean result = false;
        
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        
            if(resultSet.next() == true){
                if(resultSet.getString("primary_email").equals(primaryEmail) == true){
                    result = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        return result;
    }
    
    public boolean verifyEmail(String sql) throws SQLException{
        
        boolean result = false;
        
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        
            if(resultSet.next() == true){
                result = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        return result;
    }
    
    public String getUserId(String sql) throws SQLException{
        String result = "";
        
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        
            if(resultSet.next() == true){
                result = resultSet.getString("userId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        return result;
    }
    
    public boolean verifiedAccount(String sql) throws SQLException{
        return verifyEmail(sql);
    }
    
    public ArrayList personInfo(String sql) throws SQLException{
        ArrayList personInfo = new ArrayList();
        
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        
            if(resultSet.next() == true){
                   personInfo.add(0,resultSet.getString("userId"));
                   personInfo.add(1,resultSet.getString("first_name"));
                   personInfo.add(2,resultSet.getString("last_name"));
                   personInfo.add(3,resultSet.getString("date_of_birth"));
                   personInfo.add(4,resultSet.getString("gender"));
                   personInfo.add(5,resultSet.getString("address"));
                   personInfo.add(6,resultSet.getString("contact"));
                   personInfo.add(7,resultSet.getString("password"));
                   personInfo.add(8,resultSet.getString("primary_email"));
                   personInfo.add(9,resultSet.getString("secondary_email"));
                   personInfo.add(10,resultSet.getBoolean("verified_account"));
                   personInfo.add(11,resultSet.getBoolean("blocked_account"));
                   personInfo.add(12,resultSet.getInt("player_rating"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        return personInfo;
    }
    
    private void establishConnection() throws SQLException, ClassNotFoundException{
        
        String url = "jdbc:odbc:Online_Chess_Game";
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        connection = DriverManager.getConnection(url);
    }

    public JSONArray getForAdminsDataAsJsonArray(String sql) {
        JSONArray usersData = new JSONArray();
        JSONObject userData = null;
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        
            while(resultSet.next() == true){
                userData = new JSONObject();
                userData.put("userId", resultSet.getString("userId"));
                userData.put("blockedAccount", resultSet.getBoolean("blocked_account"));
                usersData.put(userData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return usersData;
    }

    public boolean updateTableRecord(String sql) {
        boolean result = false;
        int rowsUpdated = 0;
        
        try{
            Statement statement = null;
            statement = connection.createStatement();
            rowsUpdated = statement.executeUpdate(sql);
        }catch(SQLException ex){
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(rowsUpdated > 0){
            result = true;
        }
        
        return result;
    }
}
