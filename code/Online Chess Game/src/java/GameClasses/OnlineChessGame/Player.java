/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

/**
 *
 * @author ALI
 */
public class Player extends Person{
    private transient String playerId;

    public Player() {
        super();
        this.playerId = "";
    }

    public Player(String playerId, String userId, String firstName, String lastName, String dateOfBirth, String gender, String address, String contact, String primaryEmail, Rating rating) {
        super(userId, firstName, lastName, dateOfBirth, gender, address, contact, primaryEmail, rating);
        this.playerId = playerId;
    }

    
    public String getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
    
    public void setPlayer(Player player){
        this.setAddress(player.getAddress());
        this.setContact(player.getContact());
        this.setDateOfBirth(player.getDateOfBirth());
        this.setFirstName(player.getFirstName());
        this.setGender(player.getGender());
        this.setLastName(player.getLastName());
        this.setPlayerId(player.getPlayerId());
        this.setPrimaryEmail(player.getPrimaryEmail());
        this.setRating(player.getRating());
        this.setUserId(player.getUserId());
    }

    
}
