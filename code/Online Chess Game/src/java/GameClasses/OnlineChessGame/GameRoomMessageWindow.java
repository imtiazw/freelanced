/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import java.util.HashMap;
import java.util.Queue;
import org.json.JSONArray;

/**
 *
 * @author ALI
 */
public class GameRoomMessageWindow extends MessageWindow{
    private HashMap<String, Queue<GameRoomMessage>> gameRoomMessages;

    public GameRoomMessageWindow() {
        this.gameRoomMessages = new HashMap<String, Queue<GameRoomMessage>>();
    }

    public HashMap<String, Queue<GameRoomMessage>> getGameRoomMessage() {
        return this.gameRoomMessages;
    }

    public void setGameRoomMessage(HashMap<String, Queue<GameRoomMessage>> gameRoomMessages) {
        this.gameRoomMessages.putAll(gameRoomMessages);
    }
    
    public void addMessage(String playerId, String message){
        for(String key : this.gameRoomMessages.keySet()){
            if(key != null){
                if(key.equals(playerId) == false){
                    this.gameRoomMessages.get(key).add(new GameRoomMessage(message));
                }
            }
        }
    }
    
    public JSONArray getMessage(String playerId){
        Queue<GameRoomMessage> messageList = this.gameRoomMessages.get(playerId);
        JSONArray messages = new JSONArray();
        if(messageList != null){
            while(messageList.isEmpty() == false){
                messages.put(messageList.remove().getMessage());
            }
        }
        
        return messages;
    }

    public void addMessageToThisPlayerMessage(Player invitedPlayer, String invitation) {
        
        Queue<GameRoomMessage> invitedPlayerMessages = this.gameRoomMessages.get(invitedPlayer.getUserId());
        invitedPlayerMessages.add(new GameRoomMessage(invitation));
    }
}
