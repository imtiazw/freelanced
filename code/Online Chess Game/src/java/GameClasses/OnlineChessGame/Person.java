/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import DataAccessClasses.PersonDAO;
import java.sql.SQLException;

/**
 *
 * @author ALI
 */
public class Person {
    private String userId;
    private transient String firstName;
    private transient String lastName;
    private transient String dateOfBirth;
    private transient String gender;
    private transient String address;
    private transient String contact;
    private transient String primaryEmail;
    private transient Rating rating;

    public Person() {
        this.userId = "";
        this.firstName = "";
        this.lastName = "";
        this.dateOfBirth = "";
        this.gender = "";
        this.address = "";
        this.contact = "";
        this.primaryEmail = "";
        this.rating = new Rating();
    }
    
    public Person(String userId, String firstName, String lastName, String dateOfBirth, String gender, String address, String contact, String primaryEmail, Rating rating) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.primaryEmail = primaryEmail;
        this.rating.setRating(rating);
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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating.setRating(rating);
    }
    
    public void updateRating(int rating) throws SQLException, ClassNotFoundException{
        this.rating.setRating(rating);
        new PersonDAO().newRating(this.userId, this.rating.getRating());
    }
}
