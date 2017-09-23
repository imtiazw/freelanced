/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccessClasses;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALI
 */
public class UpdateTableDAO {
    Connection connection;
    
    public UpdateTableDAO() throws SQLException, ClassNotFoundException{
        establishConnection();
    }
    
    public boolean insertIntoTable(String sql) throws SQLException{
        boolean result = false;
        
        Statement statement;
        try {
            statement = connection.createStatement();
            int rowsUpdated = statement.executeUpdate(sql);
        
            if(rowsUpdated > 0){
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
    
    public boolean updateVerificationStatus(String sql) throws SQLException{
        return insertIntoTable(sql);
    }
    
    public boolean updatePassword(String sql) throws SQLException{
        return insertIntoTable(sql);
    }
    private void establishConnection() throws SQLException, ClassNotFoundException{
        
        String url = "jdbc:odbc:Online_Chess_Game";
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        connection = DriverManager.getConnection(url);
    }
}
