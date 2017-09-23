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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALI
 */
public class GamesDAO {
    
    
    Connection connection;

    public GamesDAO() throws SQLException, ClassNotFoundException {
        establishConnection();
    }

    public boolean addGameRecord(String host, String opponent) throws SQLException, ClassNotFoundException{
        boolean result = false;
        
        int rowsAdded = 0;
        int columnCount = new GamesDAO().getColumnCount() + 1;
        String sql = "INSERT INTO [Games] "
                + "VALUES (" + columnCount + ", '"+ host +"','" + opponent +"','" + host + "')";
        try{
            Statement statement = null;
            statement = connection.createStatement();
            rowsAdded = statement.executeUpdate(sql);
        }catch(SQLException ex){
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        
        if(rowsAdded > 0){
            result = true;
        }
        
        return result;
    }
    
    public int getColumnCount() throws SQLException{
        int columnCount = 0;
        String sql = "SELECT COUNT(DISTINCT [game_number]) "
                    + "FROM [Games]";
        try{
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next() == true){
                columnCount = resultSet.getInt(1);
            }
        }catch(SQLException ex){
            Logger.getLogger(UpdateTableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(connection != null){
                connection.close();
            }
        }
        
        return columnCount;
    }
    
    private void establishConnection() throws SQLException, ClassNotFoundException{
        
        String url = "jdbc:odbc:Online_Chess_Game";
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        connection = DriverManager.getConnection(url);
    }
    
}
