/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.SingInUpRecoverPassword;

/**
 *
 * @author ALI
 */
public class Profile {
    private String userId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String contact;
    private String primaryEmail;
    private String password;
    private String secondaryEmail;
    private boolean verifiedAccount;
    private int rating;

    public Profile(String userId, String firstName, String lastName, String dateOfBirth, String gender, String address, String contact, String primaryEmail, String password, String secondaryEmail, boolean verifiedAccount, int rating) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.primaryEmail = primaryEmail;
        this.password = password;
        this.secondaryEmail = secondaryEmail;
        this.verifiedAccount = verifiedAccount;
        this.rating = rating;
    }
    
    public Profile() {
        this.userId = "";
        this.firstName = "";
        this.lastName = "";
        this.dateOfBirth = "";
        this.gender = "";
        this.address = "";
        this.contact = "";
        this.primaryEmail = "";
        this.password = "";
        this.secondaryEmail = "";
        this.verifiedAccount = false;
        this.rating = 0;
    }
    
    public void sendEmail(String primaryEmail){}
    
    public boolean verifyEmail(String verificationCode){
        boolean result = false;
        
        return result;
    }
    
    public boolean isValidVerificationCode(String verificationCode){
        boolean result = false;
        
        return result;
    }
    
    public void changeAccountStatus(String status){}
    
    public void message(String message){}
    
    public void updateDataBase(){}
    
    public void setProfile(String userId, String firstName, String lastName, String dateOfBirth, String gender, String address, String contact, String primaryEmail, String password, String secondaryEmail, boolean verifiedAccount, int rating) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.primaryEmail = primaryEmail;
        this.password = password;
        this.secondaryEmail = secondaryEmail;
        this.verifiedAccount = verifiedAccount;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public boolean isVerifiedAccount() {
        return verifiedAccount;
    }

    public void setVerifiedAccount(boolean verifiedAccount) {
        this.verifiedAccount = verifiedAccount;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    
}
