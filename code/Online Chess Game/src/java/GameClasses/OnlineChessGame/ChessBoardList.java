/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import java.util.ArrayList;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ALI
 */
public class ChessBoardList {
    private static final int maximumNumberOfChessBoards = 30;
    private static int noOfChessBoards;
    private ChessBoard[] chessBoard;

    public ChessBoardList() {
        chessBoard = new ChessBoard[maximumNumberOfChessBoards];
        for(int i=0; i<maximumNumberOfChessBoards; i++){
            this.chessBoard[i] = new ChessBoard();
        }
        ChessBoardList.noOfChessBoards = 0;
    }

    public void decrementNoOfChessBoards(){
        noOfChessBoards--;
    }
    
    public int getMaximumNumberOfChessBoards() {
        return maximumNumberOfChessBoards;
    }

    public ChessBoard[] getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard[] chessBoard) {
        for(int i=0; i<chessBoard.length; i++){
            this.chessBoard[i] = new ChessBoard(chessBoard[i]);
        }
    }
    
    public JSONArray getJSONChessBoardList() throws JSONException{
        JSONObject chessBoard = null;
        JSONArray chessBoardList = new JSONArray();
        for(int i=0; i<maximumNumberOfChessBoards; i++){
            if(this.chessBoard[i].isPlaying() == true){
                chessBoard = new JSONObject();
                chessBoard.put("tableNumber",this.chessBoard[i].getTableNumber());
                chessBoard.put("boardHost",this.chessBoard[i].getChessBoardPlayerListWindow().getBoardHost().getPlayerId());
                chessBoard.put("playerId", getPlayerId(this.chessBoard[i].getChessBoardPlayerListWindow().getPlayer()));
                chessBoard.put("maximumGameTime",this.chessBoard[i].getChessPlayBoard().getMaximumGameTime());
                chessBoard.put("timePerMove",this.chessBoard[i].getChessPlayBoard().getTimePerMove());
                chessBoardList.put(chessBoard);
            }
        }
        return chessBoardList;
    }
    
    public String getPlayerId(Player player){
        String result = "";
        if(player != null){
            result = player.getPlayerId();
        }
        
        return result;
    }
    public boolean mayCreateChessBoard(){
        
        return (noOfChessBoards < maximumNumberOfChessBoards);
    }
    
    public int createBoard(int maximumGameTime, int timePerMove, BoardHost boardHost){
        int result = -1;
        for(int i=0; i<chessBoard.length; i++){
            if(chessBoard[i].isPlaying() == false){
                chessBoard[i].getChessPlayBoard().setTime(maximumGameTime, timePerMove);
                chessBoard[i].setPlaying(true);
                chessBoard[i].assignTableNumber(i);
                boardHost.setTableNumber(i);
                chessBoard[i].setHost(boardHost);
                chessBoard[i].getChessBoardMessageWindow().getChessBoardMessage().put(boardHost.getPlayerId(), new LinkedList<ChessBoardMessage>());
                result = i;
                ++noOfChessBoards;
                
                break;
            }
        }
        return result;
    }
    
    public boolean hostGame(){
       return mayCreateChessBoard();
    }
    
    public boolean join(int tableNumber, Player player){
        boolean result = false;
        for(int i=0; i<this.chessBoard.length; i++){
            if(this.chessBoard[i].getTableNumber() == tableNumber){
                this.chessBoard[i].setPlayer2(player);
                this.chessBoard[i].getChessBoardMessageWindow().getChessBoardMessage().put(player.getUserId(), new LinkedList<ChessBoardMessage>());
                result = true;
                break;
            }
        }
        return result;
    }
    
    public void insertChessBoard(ChessBoard chessBoard){
    
    }
    
    public void message(String message){
    
    }
    
    public void refreshChessBoardList(){
    
    }

    public ChessBoard isPlayerInChessBoard(Player invitedPlayer) {
        ChessBoard playerChessBoard = null;
        
        for(int i=0; i<maximumNumberOfChessBoards; i++){
            if(this.chessBoard[i].getChessBoardPlayerListWindow().getBoardHost() != null){
                if(this.chessBoard[i].getChessBoardPlayerListWindow().getBoardHost().getUserId().equals(invitedPlayer.getUserId()) == true) {
                    playerChessBoard = this.chessBoard[i];
                    break;

                }else{ 
                    if(this.chessBoard[i].getChessBoardPlayerListWindow().getPlayer() != null){
                        if(this.chessBoard[i].getChessBoardPlayerListWindow().getPlayer().getUserId().equals(invitedPlayer.getUserId()) == true){
                            playerChessBoard = this.chessBoard[i];
                            break;  
                        }
                    }
                }
            }
        }
        
        return playerChessBoard;
    }

    public void setChessBoard(int tableNumber, ChessBoard chessBoard) {
        this.chessBoard[tableNumber] = chessBoard;
        this.chessBoard[tableNumber].setTableNumber(tableNumber);
    }

    public ArrayList<Integer> getTableNumberWherePlayerIsSitting(Player player) {
        ArrayList<Integer> tableNumberList = new ArrayList<Integer>();
        
        for(int i=0; i<maximumNumberOfChessBoards; i++){
            if(this.chessBoard[i].getChessBoardPlayerListWindow().getBoardHost() != null){
                if(this.chessBoard[i].getChessBoardPlayerListWindow().getBoardHost().getUserId().equals(player.getUserId()) == true ||
                        ((this.chessBoard[i].getChessBoardPlayerListWindow().getPlayer() != null) && this.chessBoard[i].getChessBoardPlayerListWindow().getPlayer().getUserId().equals(player.getUserId()) == true)){
                    tableNumberList.add(i);
                }
            }
        }
        
        return tableNumberList;
    }

    
}
