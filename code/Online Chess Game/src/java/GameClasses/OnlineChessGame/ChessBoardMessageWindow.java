/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ALI
 */
public class ChessBoardMessageWindow {
    private boolean enable;
    private HashMap<String, Queue<ChessBoardMessage>> chessBoardMessage;

    public ChessBoardMessageWindow() {
        this.enable = true;
        this.chessBoardMessage = new HashMap<String, Queue<ChessBoardMessage>>();
}

    public ChessBoardMessageWindow(boolean enable, HashMap<String, Queue<ChessBoardMessage>> chessBoardMessage) {
        this.enable = enable;
        this.chessBoardMessage = new HashMap<String, Queue<ChessBoardMessage>>();
        for(int i=0; i<chessBoardMessage.size(); i++){
            this.chessBoardMessage.putAll(chessBoardMessage);
        }
    }

    public ChessBoardMessageWindow(ChessBoardMessageWindow chessBoardMessageWindow){
        this.enable = chessBoardMessageWindow.isEnable();
        this.chessBoardMessage = new HashMap<String, Queue<ChessBoardMessage>>();
        this.chessBoardMessage.putAll(chessBoardMessageWindow.getChessBoardMessage());
    }
    
    public JSONObject getJsonUserMessages(String userId) throws JSONException{
        JSONObject userMessages = new JSONObject();
        if(this.chessBoardMessage.get(userId) != null){
            userMessages.put("messages", this.chessBoardMessage.get(userId));
            this.chessBoardMessage.get(userId).clear();
        }
        return userMessages;
    }
    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Queue<ChessBoardMessage> getOtherPlayersMessage(String userId){
        String key = "";
        Iterator<String> keys = this.chessBoardMessage.keySet().iterator();
        while(keys.hasNext() == true){
            key = keys.next();
            if(key.equals(userId) == false){
                break;
            }
        }
        return this.chessBoardMessage.get(key);
    }
    
    public HashMap<String,Queue<ChessBoardMessage>> getChessBoardMessage() {
        return this.chessBoardMessage;
    }

    public void setChessBoardMessage(HashMap<String, Queue<ChessBoardMessage>> chessBoardMessage) {
        this.chessBoardMessage.putAll(chessBoardMessage);
    }
    
    public void changeMessageWindowStatus(){
        this.enable = !this.enable;
    }

    public void appendMessageToThisPlayerMessage(Player invitedPlayer, String invitation) {
        
        this.chessBoardMessage.get(invitedPlayer.getUserId()).add(new ChessBoardMessage(invitation));
    }

    public boolean revertEnableChat() {
        boolean result = false;
        boolean previousStatus = this.enable;
        
        this.enable = !this.enable;
        
        if(previousStatus != this.enable){
            result = true;
        }
        
        return result;
    }
}
