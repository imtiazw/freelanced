/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import GameClasses.SingInUpRecoverPassword.SignInProfile;
import com.google.common.collect.MinMaxPriorityQueue;
import static java.lang.StrictMath.abs;
import java.util.ArrayList;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ALI
 */
public class GameRoomPlayerListWindow {
    private Player[] player;
    private int noOfPlayers; 
    private static final int maximumNoOfPlayers = 50;

    public GameRoomPlayerListWindow() {
        this.player = new Player[maximumNoOfPlayers];
        for(int i=0; i<this.player.length; i++){
            this.player[i] = new Player();
        }
        this.noOfPlayers = 0;
    }

    public GameRoomPlayerListWindow(Player[] player) {
        this.player = new Player[maximumNoOfPlayers];
        for(int i=0; i<player.length; i++){
            this.player[i].setPlayer(player[i]);
        }
    }
    
    public GameRoomPlayerListWindow(GameRoomPlayerListWindow gameRoomPlayerListWindow){
        this.player = new Player[maximumNoOfPlayers];
        for(int i=0; i<player.length; i++){
            this.player[i].setPlayer(gameRoomPlayerListWindow.getPlayer()[i]);
        }
    }
    public Player[] getPlayer() {
        return player;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }
    
    public boolean addPlayer(SignInProfile singedInUser){
        boolean result = false;
        if(noOfPlayers < maximumNoOfPlayers){
            for(int i=0; i<maximumNoOfPlayers; i++){
                if(this.player[i].getUserId().equals("") == true){
                    this.player[i].setAddress(singedInUser.getAddress());
                    this.player[i].setContact(singedInUser.getContact());
                    this.player[i].setDateOfBirth(singedInUser.getDateOfBirth());
                    this.player[i].setFirstName(singedInUser.getFirstName());
                    this.player[i].setGender(singedInUser.getGender());
                    this.player[i].setLastName(singedInUser.getLastName());
                    this.player[i].setPlayerId(singedInUser.getUserId());
                    this.player[i].setPrimaryEmail(singedInUser.getPrimaryEmail());
                    this.player[i].setRating(new Rating(singedInUser.getRating()));
                    this.player[i].setUserId(singedInUser.getUserId());
                    break;
                }
            }
            noOfPlayers++;
            result = true;
        }
        return result;
    }
    
    public JSONArray getJsonPlayer() throws JSONException{
        JSONArray playerList = new JSONArray();
        JSONObject player;
        for(int i=0; i<maximumNoOfPlayers; i++){
            if(this.player[i].getUserId().equals("") == false){
                player = new JSONObject();
                player.put("userId", this.player[i].getUserId());
                player.put("rating", this.player[i].getRating().getRating());
                playerList.put(player);
            }
        }
        return playerList;
    }
    
    public Player getPlayer(String userId){
        Player result = null;
        for(int i=0; i<player.length; i++){
            if(player[i].getUserId().equals(userId) == true){
                result = player[i];
                break;
            }
        }
        return result;
    }
    
    public boolean removePlayer(Player player){
        boolean result = false;
        String playerUserId = player.getUserId();
        
        for(int i=0; i<maximumNoOfPlayers; i++){
            if(playerUserId.equals(this.player[i].getUserId()) == true){
                this.player[i] = new Player();
                noOfPlayers--;
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    public void refreshGameRoomPlayerListwindow(){}

    public ArrayList<Player> getCompetitorsList(Player player) {
        ArrayList<Player> competitorsList = new ArrayList<Player>(5);
        
        MinMaxPriorityQueue<Player> orderedPlayerList = insertPlayersIntoPriorityQueue(player);
        competitorsList.addAll(removeFIrstFivePlayers(orderedPlayerList));
        
        return competitorsList;
    }

    private MinMaxPriorityQueue<Player> insertPlayersIntoPriorityQueue(Player player) {
        
        Comparator<Player> comparator = new PlayerRatingIComparator();
        MinMaxPriorityQueue<Player> orderedPlayerList = MinMaxPriorityQueue.orderedBy(comparator).create();
        
        for (int i=0; i<this.noOfPlayers; i++) {
            orderedPlayerList.add(this.player[i]);
        }
        
        //orderedPlayerList.remove(player);   commented for testing purpose
        
        return orderedPlayerList;
    }

    private ArrayList<Player> removeFIrstFivePlayers(MinMaxPriorityQueue<Player> orderedPlayerList) {
        ArrayList<Player> competitorsList = new ArrayList<Player>();
        
        for(int i=0; (i<5 && orderedPlayerList.isEmpty() == false); i++){
            
            competitorsList.add(orderedPlayerList.pollFirst());
        }
        
        return competitorsList;
    }
    
    private class PlayerRatingIComparator implements Comparator<Player>{

        @Override
        public int compare(Player playerOne, Player playerTwo) {
            int result = 0;
            
            result = abs(playerOne.getRating().getRating() - playerTwo.getRating().getRating());
            return result;
        }
    
    }
}
