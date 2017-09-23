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
public class GameRoom {
    private ChessBoardList chessBoardList;
    private GameRoomMessageWindow gameRoomMessageWindow;
    private GameRoomPlayerListWindow gameRoomPlayerListWindow;

    public GameRoom() {
        this.chessBoardList = new ChessBoardList();
        this.gameRoomMessageWindow = new GameRoomMessageWindow();
        this.gameRoomPlayerListWindow = new GameRoomPlayerListWindow();
    }

    public ChessBoardList getChessBoardList() {
        return chessBoardList;
    }

    public GameRoomMessageWindow getGameRoomMessageWindow() {
        return gameRoomMessageWindow;
    }

    public GameRoomPlayerListWindow getGameRoomPlayerListWindow() {
        return gameRoomPlayerListWindow;
    }
    
    
    public void refreshRoom(){
    
    }
}
