/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.SingInUpRecoverPassword;

import DataAccessClasses.PersonDAO;
import DataAccessClasses.UpdateTableDAO;
import SignUp.GenerateEmail;
import SignUp.VerificationCode;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ALI
 */
public class RecoverThePassword {
    private String primaryEmail;
    private String secondaryEmail;

    public RecoverThePassword() {
        this.primaryEmail = "";
        this.secondaryEmail = "";
    }
    
    public RecoverThePassword(String primaryEmail, String secondaryEmail) {
        this.primaryEmail = primaryEmail;
        this.secondaryEmail = secondaryEmail;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }
    
    
    public String verifyEmail(String email) throws SQLException, ClassNotFoundException{
        String result = "";
        
        if(this.primaryEmail.equals("") == false){
            String sql = "SELECT [userId] "
                    + "FROM [Authentication] "
                    + "WHERE [primary_email] = '" + email + "'";
            result = isVerifiedEmail(sql);
        }else{
            String sql = "SELECT [userId] "
                    + "FROM [Authentication] "
                    + "WHERE [secondary_email] = '" + email + "'";
            result = isVerifiedEmail(sql);
        }
        
        return result;
    }
    
    public void recoverPassword(String email){
    
    }
    
    public boolean sendPasswordRecoveryEmail(String userId, String email) throws SQLException, ClassNotFoundException{
        boolean result = false;
        String url = "http://localhost:8080/OnlineChessGame/NewPassword?code=";
        String verificationCode = new VerificationCode().getCode();
        DateFormat dateFormat= new SimpleDateFormat();
        String date = dateFormat.format(new Date());
        String message = "You requested for Recover Password please click on the following link to Enter a new password\n" 
                       + url + verificationCode
                       + "\nnote: This link will expire in 24 hours";
        String sql = "UPDATE [Email Verification] "
                + "SET [verification_code] = '" + verificationCode + "', [date] = '" + date + "'"
                + "WHERE [userId] = '" + userId + "'";
        if(new UpdateTableDAO().insertIntoTable(sql) == true){            
            if(new GenerateEmail().sendEmail(email, message)){
                result = true;
            }
        }
        return result;
    }
    
    public void setPassword(String newPassword){
    
    }
    
    public void updateDatabasePassword(String newPassword){
    
    }
    
    private String isVerifiedEmail(String sql) throws ClassNotFoundException, SQLException{
        String result = "";
        result = new PersonDAO().getUserId(sql);
        return result;
    }
}
