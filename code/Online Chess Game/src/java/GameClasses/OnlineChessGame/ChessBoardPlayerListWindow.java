/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ALI
 */
public class ChessBoardPlayerListWindow {
    private BoardHost boardHost;
    private Player player;

    public ChessBoardPlayerListWindow() {
        this.boardHost = new BoardHost();
        this.player = null;     //here player has association not composition
    }

    public ChessBoardPlayerListWindow(BoardHost boardHost, Player player) {
        this.boardHost.setBoardHost(boardHost);
        this.player = player;           //here player has association not composition
    }
    
    public ChessBoardPlayerListWindow(ChessBoardPlayerListWindow chessBoardPlayerListWindow){
        this.boardHost.setBoardHost(chessBoardPlayerListWindow.getBoardHost());
        this.player = chessBoardPlayerListWindow.getPlayer();           //here player has association not composition
    }

    public Person getPerson(String userId){
        Person person = null;
        
        if(this.boardHost.getUserId().equals(userId)){
            person = this.boardHost;
        }else if(this.player.getUserId().equals(userId)){
            person = this.player;
        }
        
        return person;
    }
    
    public BoardHost getBoardHost() {
        return boardHost;
    }

    public void setBoardHost(BoardHost boardHost) {
        this.boardHost.setBoardHost(boardHost);
    }

    public Player getPlayer() {
        return player;
    }
    
    public void bootPlayer(){
        if(this.player != null){
            this.player = null;
        }
    }

    public boolean setPlayer(Player player) {
        boolean result = false;
        if(this.player == null){
            this.player = player;           //player has association not composition
            result = true;
        }
        return result;
    }
    
    
    public boolean isPlayerHost(Player player){
        boolean result = false;
        
        if(player.getUserId().equals(this.boardHost.getPlayerId())){
            result = true;
        }
        
        return result;
    }
    
    public JSONArray getJsonPlayers() throws JSONException{
        JSONArray players = new JSONArray();
        JSONObject player;
            player = new JSONObject();
            player.put("userId", this.boardHost.getPlayerId());
            player.put("rating", this.boardHost.getRating().getRating());
            players.put(player);
        
        if(this.player != null){    
            player = new JSONObject();
            player.put("userId", this.player.getPlayerId());
            player.put("rating", this.player.getRating().getRating());
            players.put(player);
        }
        return players;
    }

    public Person getOpponentPlayer(String userId) {
        Person result = null;
        
        if(userId.equals(this.boardHost.getUserId()) == true){
            result = this.player;            
        }else if(userId.equals(this.player.getUserId()) == true){
            result = this.boardHost;
        }
        
        return result;
    }

    public void removePlayer() {
        bootPlayer();
    }
}
