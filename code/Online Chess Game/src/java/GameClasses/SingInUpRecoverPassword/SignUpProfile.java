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
public class SignUpProfile extends Profile{

    public SignUpProfile(String userId, String firstName, String lastName, String dateOfBirth, String gender, String address, String contact, String primaryEmail, String password, String secondaryEmail, boolean verifiedAccount, int rating) {
        super(userId, firstName, lastName, dateOfBirth, gender, address, contact, primaryEmail, password, secondaryEmail, verifiedAccount, rating);
    }

    public SignUpProfile() {
        super();
    }
    
    public String isAlreadyRegistered() throws SQLException, ClassNotFoundException{
        String result = "";
        String userIdSql = "SELECT [userId] "
                + "FROM [Authentication] "
                + "WHERE [userId] = '" + getUserId() + "'";
        String emailSql = "SELECT [primary_email] "
                + "FROM [Authentication] "
                + "WHERE [primary_email] = '" + getPrimaryEmail() + "'";
        if(new PersonDAO().isUserIdRegistered(userIdSql, getUserId()) == true){
            result = "userId";
        }else if(new PersonDAO().isPrimaryEmailRegistered(emailSql, getPrimaryEmail()) == true){
            result = "primaryEmail";
        }
        return result;
    } 
    
    public boolean changeVerificationStatus(){
        boolean result = false;
        
        return result;
    }
}
