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
class MessageWindow {
    private int totalPlayers;

    public MessageWindow() {
        this.totalPlayers = 0;
    }

    public MessageWindow(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }
    
    public void addMessage(String newMessage){
    
    }
    
    public void refreshMessageWindow(){
    
    }
}
