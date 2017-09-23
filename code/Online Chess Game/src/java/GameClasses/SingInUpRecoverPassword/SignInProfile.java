/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.SingInUpRecoverPassword;

import DataAccessClasses.PersonDAO;
import java.sql.SQLException;

/**
 *
 * @author ALI
 */
public class SignInProfile extends Profile {
    private boolean blockedAccount;

    public SignInProfile(String userId, String firstName, String lastName, String dateOfBirth, String gender, String address, String contact, String primaryEmail, String password, String secondaryEmail, boolean verifiedAccount, int rating, boolean blockedAccount) {
        super(userId, firstName, lastName, dateOfBirth, gender, address, contact, primaryEmail, password, secondaryEmail, verifiedAccount, rating);
        this.blockedAccount = blockedAccount;
    }

    public SignInProfile() {
        super();
        this.blockedAccount = false;
    }

    public void setBlockedAccount(boolean blockedAccount) {
        this.blockedAccount = blockedAccount;
    }
    
    public boolean isBlockedAccount(){
        boolean result = false;
        if(this.blockedAccount == true){
            result = true;
        }
        return result;
    }
    
    public boolean isAdmin(){
        boolean result = false;
        
        if(this.getUserId().equals("admin") == true){
            result = true;
        }
        
        return result;
    }
    
    public void changeBlockedAccountStatus(){
        
    }
    
    public boolean isValidAccount(String userId, String password) throws ClassNotFoundException, SQLException{
        boolean result = false;
        String sql = "SELECT [userId]"
                + "FROM [Authentication]"
                + "WHERE [userId] = '" + userId + "'"
                + "AND [password] = '" + password + "'";
        if(new PersonDAO().verifiedAccount(sql) == true){
            result = true;
        }    
        return result;
    }
    
    public boolean deleteDataBase(){
        boolean result = false;
        
        return result;
    }
}
