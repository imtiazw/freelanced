/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import java.util.ArrayList;

/**
 *
 * @author ALI
 */
class Competetor extends Person{
    private String playerId;
    private boolean playing;

    public Competetor() {
        super();
        this.playerId = "";
        this.playing = false;
    }

    public Competetor(String playerId, boolean playing, String userId, String firstName, String lastName, String dateOfBirth, String gender, String address, String contact, String primaryEmail, Rating rating) {
        super(userId, firstName, lastName, dateOfBirth, gender, address, contact, primaryEmail, rating);
        this.playerId = playerId;
        this.playing = playing;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
    
    public void setCompetetor(Competetor competetor){
        this.setAddress(competetor.getAddress());
        this.setContact(competetor.getContact());
        this.setDateOfBirth(competetor.getDateOfBirth());
        this.setFirstName(competetor.getFirstName());
        this.setGender(competetor.getGender());
        this.setLastName(competetor.getLastName());
        this.setPrimaryEmail(competetor.getPrimaryEmail());
        this.setUserId(competetor.getUserId());
        this.setRating(competetor.getRating());
        this.playerId = competetor.playerId;
        this.playing = competetor.playing;
    }
    
    
}
