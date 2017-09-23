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
public class BoardHost extends Person {
    private transient String playerId;
    private transient int tableNumber;

    public BoardHost() {
        super();
        this.playerId = "";
        this.tableNumber = 0;
    }

    public BoardHost(String playerId, int tableNumber, String userId, String firstName, String lastName, String dateOfBirth, String gender, String address, String contact, String primaryEmail, Rating rating) {
        super(userId, firstName, lastName, dateOfBirth, gender, address, contact, primaryEmail, rating);
        this.playerId = playerId;
        this.tableNumber = tableNumber;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    public void setBoardHost(BoardHost boardHost){
        this.setAddress(boardHost.getAddress());
        this.setContact(boardHost.getContact());
        this.setDateOfBirth(boardHost.getDateOfBirth());
        this.setFirstName(boardHost.getFirstName());
        this.setGender(boardHost.getGender());
        this.setLastName(boardHost.getLastName());
        this.setPrimaryEmail(boardHost.getPrimaryEmail());
        this.setUserId(boardHost.getUserId());
        this.setRating(boardHost.getRating());
        this.playerId = boardHost.playerId;
        this.tableNumber = boardHost.tableNumber;
    }
    
    public BoardHost toBoardHost(Player playerId){
        
        this.setAddress(playerId.getAddress());
        this.setContact(playerId.getContact());
        this.setDateOfBirth(playerId.getDateOfBirth());
        this.setFirstName(playerId.getFirstName());
        this.setGender(playerId.getGender());
        this.setLastName(playerId.getLastName());
        this.setPrimaryEmail(playerId.getPrimaryEmail());
        this.setUserId(playerId.getUserId());
        this.setRating(playerId.getRating());
        this.playerId = playerId.getUserId();
        this.tableNumber = 0;
        
        return this;
    }
}
