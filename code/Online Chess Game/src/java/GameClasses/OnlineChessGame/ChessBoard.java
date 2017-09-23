/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import java.sql.SQLException;

/**
 *
 * @author ALI
 */
public class ChessBoard {
    private int tableNumber;
    private boolean playing;
    private ChessPlayBoard chessPlayBoard;
    private GameMovesWindow gameMovesWindow;
    private ChessBoardMessageWindow chessBoardMessageWindow;
    private ChessBoardPlayerListWindow chessBoardPlayerListWindow;
    private InvitationListPlayerListWindow invitationListPlayerListWindow;

    
    public ChessBoard() {
        this.tableNumber = 0;
        this.playing = false;
        this.chessPlayBoard = new ChessPlayBoard();
        this.gameMovesWindow = new GameMovesWindow();
        this.chessBoardMessageWindow = new ChessBoardMessageWindow();
        this.chessBoardPlayerListWindow = new ChessBoardPlayerListWindow();
        this.invitationListPlayerListWindow = new InvitationListPlayerListWindow();
        this.chessPlayBoard.setChessBoardPlayerListWindow(this.chessBoardPlayerListWindow);
    }

    public ChessBoard(ChessBoard chessBoard) {
        this.setTableNumber(chessBoard.getTableNumber());
        this.setChessPlayBoard(chessBoard.getChessPlayBoard());
        this.setGameMovesWindow(chessBoard.getGameMovesWindow());
        this.setChessBoardMessageWindow(chessBoard.getChessBoardMessageWindow());
        this.setChessBoardPlayerListWindow(chessBoard.getChessBoardPlayerListWindow());
        this.setInvitationListPlayerListWindow(chessBoard.getInvitationListPlayerListWindow());
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ChessPlayBoard getChessPlayBoard() {
        return chessPlayBoard;
    }

    public void setChessPlayBoard(ChessPlayBoard chessPlayBoard) {
        this.chessPlayBoard = null;
        this.chessPlayBoard = chessPlayBoard;
    }

    public GameMovesWindow getGameMovesWindow() {
        return gameMovesWindow;
    }

    public void setGameMovesWindow(GameMovesWindow gameMovesWindow) {
        this.gameMovesWindow = new GameMovesWindow(gameMovesWindow);
    }

    public ChessBoardMessageWindow getChessBoardMessageWindow() {
        return chessBoardMessageWindow;
    }

    public void setChessBoardMessageWindow(ChessBoardMessageWindow chessBoardMessageWindow) {
        this.chessBoardMessageWindow = new ChessBoardMessageWindow(chessBoardMessageWindow);
    }

    public ChessBoardPlayerListWindow getChessBoardPlayerListWindow() {
        return chessBoardPlayerListWindow;
    }

    public void setChessBoardPlayerListWindow(ChessBoardPlayerListWindow chessBoardPlayerListWindow) {
        this.chessBoardPlayerListWindow = new ChessBoardPlayerListWindow(chessBoardPlayerListWindow);
    }

    public InvitationListPlayerListWindow getInvitationListPlayerListWindow() {
        return invitationListPlayerListWindow;
    }

    public void setInvitationListPlayerListWindow(InvitationListPlayerListWindow invitationListPlayerListWindow) {
        this.invitationListPlayerListWindow = new InvitationListPlayerListWindow(invitationListPlayerListWindow);
    }
    
    
    public void setHost(BoardHost player){
        this.chessBoardPlayerListWindow.setBoardHost(player);
    }
    
    public void message(String message){
    
    }
    
    public boolean join(Player player){
        return setPlayer2(player);
    }
    
    public boolean setPlayer2(Player player){
        return this.chessBoardPlayerListWindow.setPlayer(player);
    }
    
    public void refreshChessBoard(){
    
    }
    
    public void boot(){
    
    }
    
    public void startTime(){
    
    }
    
    public void startTimePerMove(){
    
    }
    
    public void exitChessBoard(Player looser, Player winner) throws ClassNotFoundException, SQLException{
        if(looser != null && winner != null){
            if(this.chessPlayBoard.isGameStarted() == true){
                looser.updateRating(looser.getRating().getRating()-20);
                winner.updateRating(winner.getRating().getRating()+20);

                String winnerNotificationPlayer = "<span class='winDueToQuit' style='color: green'>Other Player Quit You Win. Please Join Another Chess Board</span>";
                String winnerNotificationHost = "<span class='winDueToQuit' style='color: green'>Other Player Quit You Win.</span>";
                if(this.getChessBoardPlayerListWindow().isPlayerHost(winner) == true){
                    this.chessBoardMessageWindow.appendMessageToThisPlayerMessage(winner, winnerNotificationHost);
                    this.chessBoardPlayerListWindow.getBoardHost().getRating().setRating(this.chessBoardPlayerListWindow.getBoardHost().getRating().getRating() + 20);
                }else if(this.getChessBoardPlayerListWindow().isPlayerHost(winner) == false){
                    this.chessBoardMessageWindow.appendMessageToThisPlayerMessage(winner, winnerNotificationPlayer);
                    this.chessBoardPlayerListWindow.getBoardHost().getRating().setRating(this.chessBoardPlayerListWindow.getBoardHost().getRating().getRating() - 20);
                }
            }
        }
    }
    
    public void assignTableNumber(int tableNumber){
        this.tableNumber = tableNumber;
    }
    
}
