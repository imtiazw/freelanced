/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import GameClasses.LastMove.Check;
import GameClasses.LastMove.LastMove;
import GameClasses.LastMove.Move;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author ALI
 */
public class ChessPlayBoard {
    private static final int numberOfPieces = 32;
    private static final int numberOfChessBoardBox = 64;
    private Piece[] piece;
    private ChessBoardBox[] chessBoardBox;
    private Position[] location;
    private ChessBoardPlayerListWindow chessBoardPlayerListWindow;
    private String currentPlayerControl;
    private int maximumGameTime;
    private int timePerMoveConstant;
    private int timePerMove;
    private boolean gameStarted;
    private boolean hostReady;
    private boolean playerReady;
    private Timer timePerMoveTimer;
    private Timer maximumGameTimeTimer;
    private LastMove lastMove;
    private int maximumGameTimeConstant;

    public ChessPlayBoard() {
        this.piece = new Piece[numberOfPieces];

        this.piece[0] = new Rock("BLR",new Position("1","a"),"brown", "host", new ArrayList<Position>());
        this.piece[1] = new Knight("BLN", new Position("1", "b"), "brown", "host", new ArrayList<Position>());
        this.piece[2] = new Bishop("BLB", new Position("1", "c"), "brown", "host", new ArrayList<Position>());      
        this.piece[3] = new Queen("BQ", new Position("1", "d"), "brown", "host", new ArrayList<Position>());       
        this.piece[4] = new King("BK", new Position("1", "e"), "brown", "host", new ArrayList<Position>());
        this.piece[5] = new Bishop("BRB", new Position("1", "f"), "brown", "host", new ArrayList<Position>());
        this.piece[6] = new Knight("BRN", new Position("1", "g"), "brown", "host", new ArrayList<Position>());
        this.piece[7] = new Rock("BRR", new Position("1", "h"), "brown", "host", new ArrayList<Position>());
        this.piece[8] = new Pawn("BP1", new Position("2", "a"), "brown", "host", new ArrayList<Position>());    
        this.piece[9] = new Pawn("BP2", new Position("2", "b"), "brown", "host", new ArrayList<Position>());    
        this.piece[10] = new Pawn("BP3", new Position("2", "c"), "brown", "host", new ArrayList<Position>());
        this.piece[11] = new Pawn("BP4", new Position("2", "d"), "brown", "host", new ArrayList<Position>());       
        this.piece[12] = new Pawn("BP5", new Position("2", "e"), "brown", "host", new ArrayList<Position>());       
        this.piece[13] = new Pawn("BP6", new Position("2", "f"), "brown", "host", new ArrayList<Position>());
        this.piece[14] = new Pawn("BP7", new Position("2", "g"), "brown", "host", new ArrayList<Position>());
        this.piece[15] = new Pawn("BP8", new Position("2", "h"), "brown", "host", new ArrayList<Position>());
        
        this.piece[16] = new Pawn("PP8", new Position("7", "a"), "pink", "player", new ArrayList<Position>());
        this.piece[17] = new Pawn("PP7", new Position("7", "b"), "pink", "player", new ArrayList<Position>());      
        this.piece[18] = new Pawn("PP6", new Position("7", "c"), "pink", "player", new ArrayList<Position>());
        this.piece[19] = new Pawn("PP5", new Position("7", "d"), "pink", "player", new ArrayList<Position>());
        this.piece[20] = new Pawn("PP4", new Position("7", "e"), "pink", "player", new ArrayList<Position>());
        this.piece[21] = new Pawn("PP3", new Position("7", "f"), "pink", "player", new ArrayList<Position>()); 
        this.piece[22] = new Pawn("PP2", new Position("7", "g"), "pink", "player", new ArrayList<Position>());
        this.piece[23] = new Pawn("PP1", new Position("7", "h"), "pink", "player", new ArrayList<Position>());      
        this.piece[24] = new Rock("PRR",new Position("8", "a"),"pink", "player", new ArrayList<Position>());
        this.piece[25] = new Knight("PRN", new Position("8", "b"), "pink", "player", new ArrayList<Position>());
        this.piece[26] = new Bishop("PRB", new Position("8", "c"), "pink", "player", new ArrayList<Position>());
        this.piece[27] = new Queen("PQ", new Position("8", "d"), "pink", "player", new ArrayList<Position>());      
        this.piece[28] = new King("PK", new Position("8", "e"), "pink", "player", new ArrayList<Position>());       
        this.piece[29] = new Bishop("PLB", new Position("8", "f"), "pink", "player", new ArrayList<Position>());
        this.piece[30] = new Knight("PLN", new Position("8", "g"), "pink", "player", new ArrayList<Position>());
        this.piece[31] = new Rock("PLR", new Position("8", "h"), "pink", "player", new ArrayList<Position>());
        
        this.location = new Position[numberOfChessBoardBox];
        initializeLocations();
        
        this.chessBoardBox = new ChessBoardBox[numberOfChessBoardBox];
        for(int i=0; i<numberOfChessBoardBox; i++){
            if(i%2 == 0)
                this.chessBoardBox[i] = new ChessBoardBox("white", false, null, this.location[i]);
            else
                this.chessBoardBox[i] = new ChessBoardBox("red", false, null, this.location[i]);
        }
        
        for(int i=0; i<numberOfChessBoardBox; i++){
            if(i<16){  
                this.chessBoardBox[i].setPieceSitStatus(true);
                this.chessBoardBox[i].setPiece(this.piece[i]);
            }else if(i>47){  
                this.chessBoardBox[i].setPieceSitStatus(true);
                this.chessBoardBox[i].setPiece(this.piece[i-32]);
            }
        }
        
        for (Piece piece1 : this.piece) {
            if (piece1.getName().equals("dead") == false) {
                piece1.calculateNextPossiblePositions(this);
            }
        }
        
        this.currentPlayerControl = "";
        this.maximumGameTime = 30 * 60;
        this.maximumGameTimeConstant = 30 * 60;
        this.timePerMove = 2 * 60;
        this.timePerMoveConstant = 2 * 60;
        this.gameStarted = false;
        this.playerReady = false;
        this.hostReady = false;
        this.timePerMoveTimer = new Timer();
        this.maximumGameTimeTimer = new Timer();
        this.chessBoardPlayerListWindow = null;
        this.lastMove = null;
    }

    public ChessPlayBoard(Piece[] piece, ChessBoardBox chessBoardBox, String currentPlayerControl, int maximumGameTime, int timePerMove, boolean gameStarted,
                           boolean hostReady, boolean playerReady) {
        this.piece = new Piece[numberOfPieces];
        for(int i=0; i<piece.length; i++){
            this.piece[i] = new Piece();
            this.piece[i].setColor(piece[i].getColor());
            this.piece[i].setCurrentPosition(piece[i].getCurrentPosition());
            this.piece[i].setName(piece[i].getName());
            this.piece[i].setNextPossiblePosition(piece[i].getNextPossiblePosition());
            this.piece[i].setTeam(piece[i].getTeam());
        }
        this.chessBoardBox = new ChessBoardBox[numberOfChessBoardBox];
        for(int i=0; i<numberOfChessBoardBox; i++){
            this.chessBoardBox[i] = new ChessBoardBox(chessBoardBox);
        }
        this.currentPlayerControl = currentPlayerControl;
        this.maximumGameTime = maximumGameTime;
        this.maximumGameTimeConstant = maximumGameTime;
        this.timePerMove = timePerMove;
        this.timePerMoveConstant = timePerMove;
        this.gameStarted = gameStarted;
        this.hostReady = hostReady;
        this.playerReady = playerReady;
        this.timePerMoveTimer = new Timer();
        this.maximumGameTimeTimer = new Timer();
        this.chessBoardPlayerListWindow = null;
    }
    
    public ChessPlayBoard(ChessPlayBoard chessPlayBoard){
        this.setPiece(chessPlayBoard.getPiece());
        this.setChessBoardBox(chessPlayBoard.getChessBoardBox());
        this.setCurrentPlayerControl(chessPlayBoard.currentPlayerControl);
        this.setMaximumGameTime(chessPlayBoard.getMaximumGameTime());
        this.maximumGameTimeConstant = chessPlayBoard.getMaximumGameTimeConstant();
        this.setTimePerMove(chessPlayBoard.getTimePerMove());
        this.timePerMoveConstant = chessPlayBoard.timePerMoveConstant;
        this.gameStarted = chessPlayBoard.gameStarted;
        this.playerReady = chessPlayBoard.playerReady;
        this.hostReady = chessPlayBoard.hostReady;
        this.timePerMoveTimer = new Timer();
        this.maximumGameTimeTimer = new Timer();
        this.chessBoardPlayerListWindow = chessPlayBoard.chessBoardPlayerListWindow;
    }

    public int getMaximumGameTimeConstant() {
        return maximumGameTimeConstant;
    }

    
    public LastMove getLastMove() {
        return lastMove;
    }

    public void setLastMove(LastMove lastMove) {
        this.lastMove = lastMove;
    }

    public Timer getTimePerMoveTimer() {
        return timePerMoveTimer;
    }

    public void setTimePerMoveTimer(Timer timePerMoveTimer) {
        this.timePerMoveTimer = timePerMoveTimer;
    }

    public Timer getMaximumGameTimeTimer() {
        return maximumGameTimeTimer;
    }

    public void setMaximumGameTimeTimer(Timer maximumGameTimeTimer) {
        this.maximumGameTimeTimer = maximumGameTimeTimer;
    }

    public Piece[] getPiece() {
        return piece;
    }

    public void setPiece(Piece[] piece) {
        for(int i=0; i<piece.length; i++){
            this.piece[i].setColor(piece[i].getColor());
            this.piece[i].setCurrentPosition(piece[i].getCurrentPosition());
            this.piece[i].setName(piece[i].getName());
            this.piece[i].setNextPossiblePosition(piece[i].getNextPossiblePosition());
            this.piece[i].setTeam(piece[i].getTeam());
        }
    }

    public ChessBoardBox[] getChessBoardBox() {
        return chessBoardBox;
    }

    public void setChessBoardBox(ChessBoardBox[] chessBoardBox) {
        for(int i=0; i<numberOfChessBoardBox; i++){
            this.chessBoardBox[i].setColor(chessBoardBox[i].getColor());
            this.chessBoardBox[i].setPiece(chessBoardBox[i].getPiece());
            this.chessBoardBox[i].setPieceSitStatus(chessBoardBox[i].isPieceSitStatus());
        }
    }

    public ChessBoardPlayerListWindow getChessBoardPlayerListWindow() {
        return chessBoardPlayerListWindow;
    }

    public void setChessBoardPlayerListWindow(ChessBoardPlayerListWindow chessBoardPlayerListWindow) {
        this.chessBoardPlayerListWindow = chessBoardPlayerListWindow;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isHostReady() {
        return hostReady;
    }

    public void setHostReady(boolean hostReady) {
        this.hostReady = hostReady;
    }

    public boolean isPlayerReady() {
        return playerReady;
    }

    public void setPlayerReady(boolean playerReady) {
        this.playerReady = playerReady;
    }

    public int getMaximumGameTime() {
        return maximumGameTime;
    }

    public void setMaximumGameTime(int maximumGameTime) {
        this.maximumGameTime = maximumGameTime * 60;
        this.maximumGameTimeConstant = maximumGameTime * 60;
    }

    public int getTimePerMove() {
        return this.timePerMove;
    }

    public int getTimePerMoveConstant() {
        return this.timePerMoveConstant;
    }

    public void setTimePerMove(int timePerMove) {
        this.timePerMove = timePerMove * 5;
        this.timePerMoveConstant = timePerMove * 5;
    }

    public String getCurrentPlayerControl() {
        return currentPlayerControl;
    }
    
    public int getChessBoardIndex(String position) {
        int index = 0;
        for(int i=0; i<this.chessBoardBox.length; i++){
            if(this.chessBoardBox[i].getLocation().toString().equals(position) == true){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public void setCurrentPlayerControl(String player){
        this.currentPlayerControl = player;
    }
    
    public void refreshChessPlayBoard(){}
    
    public void startTime(){
        this.maximumGameTimeTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                if(maximumGameTime != 0){
                    maximumGameTime--;
                }
                if(maximumGameTime == 0){
                    gameStarted = false;
                    hostReady = false;
                    playerReady = false;
                    currentPlayerControl = "";
                    maximumGameTimeTimer.cancel();
                }
            }
        }, 1000, 1000);
    }
    
    public void startTimePerMove(){
        this.timePerMoveTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                if(timePerMove != 0){
                    timePerMove--;
                }
            }
        }, 1000, 1000);
    }
    
    public void setTime(int maximumGameTime, int timePerMove){
        this.maximumGameTime = maximumGameTime * 60;
        this.maximumGameTimeConstant = maximumGameTime * 60;;
        this.timePerMove = timePerMove * 5;
        this.timePerMoveConstant = timePerMove * 5;
    }
    
    public boolean isGameStarted(){
        return this.gameStarted;
    }
    
    public void switchPlayer(){
        if(this.currentPlayerControl.equals(this.chessBoardPlayerListWindow.getBoardHost().getPlayerId())){
            this.currentPlayerControl = this.chessBoardPlayerListWindow.getPlayer().getPlayerId();
        }else if(this.currentPlayerControl.equals(this.chessBoardPlayerListWindow.getPlayer().getPlayerId())){
            this.currentPlayerControl = this.chessBoardPlayerListWindow.getBoardHost().getPlayerId();
        }
    }
    
    public ChessBoardBox getChessBoardBoxAt(String location){
        ChessBoardBox chessBoardBox = null;
        
        for(int i =0; i<this.chessBoardBox.length; i++){
            if(this.chessBoardBox[i].getLocation().toString().equals(location) == true){
                chessBoardBox = this.chessBoardBox[i];
                break;
            }
        }
        return chessBoardBox;
    }

    private void initializeLocations() {
        String[] column = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] row = {"1", "2", "3", "4", "5", "6", "7", "8"};
        
        for(int i=0; i<this.location.length; i++){
            this.location[i] = new Position(row[i/row.length], column[i%column.length]);
            //this.location[i].setRow(row[i/row.length]);
            //this.location[i].setColumn(column[i%column.length]);
        }
    }
    
    public void replacePiece(Piece oldPiece, Piece newPiece){
        
        for(int i=0; i<this.piece.length; i++){
            if(this.piece[i].getName().equals(oldPiece.getName()) == true){
                switch(newPiece.getName().charAt(2)){
                    case 'B':
                        this.piece[i] = new Bishop(newPiece.getName(), oldPiece.getCurrentPosition(), oldPiece.getColor(), oldPiece.getTeam(), new ArrayList<Position>());
                        break;
                    case 'R':
                        this.piece[i] = new Rock(newPiece.getName(), oldPiece.getCurrentPosition(), oldPiece.getColor(), oldPiece.getTeam(), new ArrayList<Position>());
                        break;
                    case 'Q':
                        this.piece[i] = new Queen(newPiece.getName(), oldPiece.getCurrentPosition(), oldPiece.getColor(), oldPiece.getTeam(), new ArrayList<Position>());
                        break;
                    case 'N':
                        this.piece[i] = new Knight(newPiece.getName(), oldPiece.getCurrentPosition(), oldPiece.getColor(), oldPiece.getTeam(), new ArrayList<Position>());
                        break;
                }
                this.getChessBoardBox()[this.getChessBoardIndex(oldPiece.getCurrentPosition().toString())].setPiece(this.piece[i]);  //this will save this piece to chess play board
            }
        }
        /*
        Piece shellowUpgradedPiece = getUpgradedPiece(newPiece);
        shellowUpgradedPiece.setName(newPiece.getName());
        shellowUpgradedPiece.setColor(oldPiece.getColor());
        shellowUpgradedPiece.setCurrentPosition(oldPiece.getCurrentPosition());
        shellowUpgradedPiece.setTeam(oldPiece.getTeam());
        this.getPiece(oldPiece.getName());        //this will save this piece to chess play board
        */
    }
    
    private Piece getUpgradedPiece(Piece newPiece) {
        Piece shellowUpgradedPiece = null;
        
        switch(newPiece.getName().charAt(2)){
            case 'R':
                shellowUpgradedPiece = new Rock();
                break;
            case 'N':
                shellowUpgradedPiece = new Knight();
                break;
            case 'B':
                shellowUpgradedPiece = new Bishop();
                break;
            case 'Q':
                shellowUpgradedPiece = new Queen();
                break;
        }
        
        return shellowUpgradedPiece;
    }
    
    public Piece getPiece(String pieceName){
        Piece piece = null;
        for(int i=0; i<this.piece.length; i++){
            if(this.piece[i].getName().equals(pieceName) == true){
                piece = this.piece[i];
            }
        }
        
        return piece;
    }

    public void movePiece(Piece pieceMoved, Position previousPosition, Position currentPosition) {
        ChessBoardBox chessBoardBoxAtPreviousPosition = this.getChessBoardBoxAt(previousPosition.toString());
        ChessBoardBox chessBoardBoxAtCurrentPosition = this.getChessBoardBoxAt(currentPosition.toString());
        
        if(chessBoardBoxAtPreviousPosition.isPieceSitStatus() == true){
            if(chessBoardBoxAtPreviousPosition.getPiece().getName().equals(pieceMoved.getName()) == true){
                if(chessBoardBoxAtCurrentPosition.isPieceSitStatus() == false){
                    chessBoardBoxAtCurrentPosition.setPiece(pieceMoved);
                    chessBoardBoxAtCurrentPosition.getPiece().setCurrentPosition(currentPosition);
                    chessBoardBoxAtCurrentPosition.setPieceSitStatus(true);
                    chessBoardBoxAtPreviousPosition.setPieceSitStatus(false);
                    chessBoardBoxAtPreviousPosition.setPiece(null);
                }
            }
        }
        if(isKing(pieceMoved) == true){
            ((King) pieceMoved).setCastleValid(false);
        }
        if(isRock(pieceMoved) == true){
            ((Rock) pieceMoved).setCastleValid(false);
        }
    }
    
    private boolean isKing(Piece pieceMoved) {
        boolean result = false;
        
        if(pieceMoved.getName().charAt(1) == 'K'){
            result = true;
        }
        return result;
    }

    private boolean isRock(Piece pieceMoved) {
        boolean result = false;
        
        if(pieceMoved.getName().length() == 3){
            if(pieceMoved.getName().charAt(2) == 'R'){
                result = true;
            }
        }
        return result;
    }
    
    public void startDecreasingTimePerMove() {
        if(this.timePerMoveTimer != null){
            this.timePerMoveTimer.cancel();
            this.timePerMoveTimer = new Timer();
        }
            
        startTimePerMove();
    }
    
    public void resetTimePerMoveValue(){
        this.timePerMove = this.timePerMoveConstant;
    }
    

    
    
    
    
    
    
    
    
    
    //last move functions
    public void recalculatePositionsAfterMovePlayed(String team, Move move) {
        move.getNativeNextPossiblePositions().putAll(recalculateNativeNextPossiblePositions(team));
        move.getOpponentNextPossiblePositions().putAll(recalculateOpponentNextPossiblePositions(team));
        move.setNativeReturnSet(true);
        move.setOpponentReturnSet(true);
    }
    
    private HashMap<String, ArrayList<Position>> recalculateNativeNextPossiblePositions(String team) {
        HashMap<String, ArrayList<Position>> nativeNextPossiblePositions = new HashMap<String, ArrayList<Position>>();
        int startCounter = 0;
        int endCounter = 0;
        switch (team) {
            case "host":
                startCounter = 0;
                endCounter = 16;
                break;
            case "player":
                startCounter = 16;
                endCounter = 32;
                break;
        }
        
        nativeNextPossiblePositions.putAll(calculateNextPossiblePositions(startCounter, endCounter));
        
        return nativeNextPossiblePositions;
    }

    private HashMap<String, ArrayList<Position>> recalculateOpponentNextPossiblePositions(String team) {
        HashMap<String, ArrayList<Position>> nativeNextPossiblePositions = new HashMap<String, ArrayList<Position>>();
        int startCounter = 0;
        int endCounter = 0;
        switch (team) {
            case "player":
                startCounter = 0;
                endCounter = 16;
                break;
            case "host":
                startCounter = 16;
                endCounter = 32;
                break;
        }
        
        nativeNextPossiblePositions.putAll(calculateNextPossiblePositions(startCounter, endCounter));
        
        return nativeNextPossiblePositions;
    }
    
    private HashMap<String, ArrayList<Position>> calculateNextPossiblePositions(int startCounter, int endCounter) {
        HashMap<String, ArrayList<Position>> nextPossiblePositions = new HashMap<String, ArrayList<Position>>();
        
        for(int i=startCounter; i<endCounter; i++){
            if(this.piece[i].getName().equals("dead") == false){
                this.piece[i].calculateNextPossiblePositions(this);
                nextPossiblePositions.put(this.piece[i].getName(), this.piece[i].getNextPossiblePosition());
            }
        }
        
        return nextPossiblePositions;
    }
    
    private int getCurrentRowOrColumnIndex(String[] array, String position) {
        int i=0;
        for(i=0; i<array.length; i++){
            if(position.equals(array[i])){
                break;
            }
        }
        
        return i;
    }
    
    
    
    //end last move functions
    
    
    
    
    
    
    
    
    //check given functions
    public void findIfCheckGiven(Piece pieceMoved, String team) {
        ArrayList<Piece> nativePieceNames = isOpponentKingInTheLineOfFire(pieceMoved, team);
        HashMap<String, ArrayList<Position>> allowedPiecesOfOpponentWithAllowedPositions;
        Piece kingUnderCheck = getOpponentKing(team);
        ArrayList<Position> kingNextPossiblePositions = recalculateNextPossiblePositions(this, kingUnderCheck);
        
        allowedPiecesOfOpponentWithAllowedPositions = findAllowedPiecesOfOpponentWithAllowedPositions(nativePieceNames, kingUnderCheck, kingNextPossiblePositions, team);
        
        if(nativePieceNames.isEmpty() == false){
            setCheckData(kingUnderCheck, nativePieceNames, kingNextPossiblePositions, allowedPiecesOfOpponentWithAllowedPositions);
            this.lastMove.setcHeck(true);
        }    
    }
    
    private ArrayList<Piece> isOpponentKingInTheLineOfFire(Piece pieceMoved, String team) {
        ArrayList<Piece> nativePieceNames = new ArrayList<Piece>();
        Piece kingUnderCheck = getOpponentKing(team);
        ArrayList<Piece> discoveredCheckPieces = null;
        
        if(pieceMoved.getNextPossiblePositionsAsStrings().indexOf(kingUnderCheck.getCurrentPosition().toString()) != -1){
            nativePieceNames.add(pieceMoved);
        }
        
        if(team.equals("host")){
            
            discoveredCheckPieces = getDiscoveredCheckPieces(0, 16, kingUnderCheck.getCurrentPosition().toString());
            if(discoveredCheckPieces.contains(pieceMoved) == true){
                discoveredCheckPieces.remove(pieceMoved);
            }
            nativePieceNames.addAll(discoveredCheckPieces);
        }else if(team.equals("player")){
            
            discoveredCheckPieces = getDiscoveredCheckPieces(16, 32, kingUnderCheck.getCurrentPosition().toString());
            if(discoveredCheckPieces.contains(pieceMoved) == true){
                discoveredCheckPieces.remove(pieceMoved);
            }
            nativePieceNames.addAll(discoveredCheckPieces);
        }
        
        return nativePieceNames;
    }
    
    private Piece getOpponentKing(String team) {
        Piece opponentKing = null;
        switch (team) {
            case "host":
                opponentKing = this.getPiece("PK");
                break;
            case "player":
                opponentKing = this.getPiece("BK");
                break;
        }
        
        return opponentKing;
    }
    
    private ArrayList<Piece> getDiscoveredCheckPieces(int startCounter, int endCounter, String position) {
        ArrayList<Piece> discoveredCheckPieces = new ArrayList<Piece>();
        for(int i=startCounter; i<endCounter; i++){
            if(isQueenBishopRock(this.piece[i].getName()) == true){
                if(this.piece[i].getNextPossiblePosition() != null){
                    if(this.piece[i].getNextPossiblePositionsAsStrings().indexOf(position) != -1){
                        discoveredCheckPieces.add(this.piece[i]);
                    }
                }
            }
        }
        
        return discoveredCheckPieces;
    }
     
    private boolean isQueenBishopRock(String pieceName) {
        boolean result = false;
        char pieceSign = '\n';
        if(pieceName.length() == 3){
            pieceSign = pieceName.charAt(2);
        }else if(pieceName.length() == 2){
            pieceSign = pieceName.charAt(1);
        }
        
        switch(pieceSign){
            case 'B':   case 'R':   case 'Q':
                result = true;
                break;
        }
        
        return result;
    }
    
    private ArrayList<Position> recalculateNextPossiblePositions(ChessPlayBoard chessPlayBoard, Piece kingUnderCheck) {
        ArrayList<Position> nextPossiblePositions = null;
        
        kingUnderCheck.calculateNextPossiblePositions(chessPlayBoard);
        nextPossiblePositions = kingUnderCheck.getNextPossiblePosition();
        
        return nextPossiblePositions;
    }
    
    private boolean isSingleCheck(ArrayList<Piece> nativePieceNames) {
        boolean result = false;
        
        if(nativePieceNames.size() == 1){
            result = true;
        }
        
        return result;
    }
    
    private HashMap<String, ArrayList<Position>> findAllowedPiecesOfOpponentWithAllowedPositions(ArrayList<Piece> nativePieceNames, Piece kingUnderCheck, ArrayList<Position> kingNextPossiblePositions, String team) {
        HashMap<String, ArrayList<Position>> allowedPiecesOfOpponentWithAllowedPositions = new HashMap<String, ArrayList<Position>>();
        ArrayList<ArrayList<Position>> lineOfFire = new ArrayList<ArrayList<Position>>(nativePieceNames.size());
        HashMap<String, ArrayList<Position>> nativePieceWithItsLineOfFire = new HashMap<String, ArrayList<Position>>(nativePieceNames.size());
        for(int i=0; i<nativePieceNames.size(); i++){
                lineOfFire.add(getLineOfFire(nativePieceNames.get(i), kingUnderCheck));
            if(lineOfFire.isEmpty() == false){
                nativePieceWithItsLineOfFire.put(nativePieceNames.get(i).getName(), getForDisablingPieces(lineOfFire.get(i), kingUnderCheck));
            }
        }
        /*
        if(kingNextPossiblePositions.isEmpty() == false){
            for(int i=0; i<lineOfFire.size(); i++){
                for(int j=0; j<lineOfFire.get(i).size(); j++){
                    for(int k=0; k<kingNextPossiblePositions.size(); k++){
                        if(lineOfFire.get(i).get(j).toString().equals(kingNextPossiblePositions.get(k).toString()) == true){
                            kingNextPossiblePositions.remove(k);
                        }
                    }
                }
            }
        }
        */
        
        if(kingNextPossiblePositions.isEmpty() == false){
            allowedPiecesOfOpponentWithAllowedPositions.put(kingUnderCheck.getName(), kingNextPossiblePositions);
        }
        
        if(isSingleCheck(nativePieceNames) == true){
            allowedPiecesOfOpponentWithAllowedPositions.putAll(getCheckDisablingPiecesWithPositions(nativePieceWithItsLineOfFire, team));
        }
        
        return allowedPiecesOfOpponentWithAllowedPositions;
    }
    
    //start coding from here. This method will get the line of fire starting from piece position and passing through opponent king to the end of chess play board
    private ArrayList<Position> getLineOfFire(Piece nativePieceName, Piece kingUnderCheck) {
        ArrayList<Position> lineOfFire = new ArrayList<Position>();
        
        if(isBishop(nativePieceName) == true){
            lineOfFire = getBishopLineOfFire(nativePieceName, kingUnderCheck);
        }else if(isRock(nativePieceName) == true){
            lineOfFire = getRockLineOfFire(nativePieceName, kingUnderCheck);
        }else if(isQueen(nativePieceName) == true){
            lineOfFire = getQueenLineOfFire(nativePieceName, kingUnderCheck);
        }else{
            lineOfFire.add(nativePieceName.getCurrentPosition());
        }
        
        return lineOfFire;
    }
    
    private HashMap<String, ArrayList<Position>> getCheckDisablingPiecesWithPositions(HashMap<String, ArrayList<Position>> nativePieceWithItsLineOfFire, String team){
        HashMap<String, ArrayList<Position>> checkDisablingPiecesWithPositions = new HashMap<String, ArrayList<Position>>();
        ArrayList<Position> nativePieceLineOfFire = null;
        int startCounter = 0;
        int endCounter = 0;
        
        if(team.equals("host") == true){
            startCounter = 16;
            endCounter = 32;
        }else if(team.equals("player") == true){
            startCounter = 0;
            endCounter = 16;
        }
        
        Iterator<String> nativePieces = null;
        ArrayList<String> opponentPieceNextPossiblePositions = null;
        String nativePieceName = null;
        for(int i=startCounter; i<endCounter; i++){
            opponentPieceNextPossiblePositions = this.piece[i].getNextPossiblePositionsAsStrings();
            nativePieces = nativePieceWithItsLineOfFire.keySet().iterator();
            while(nativePieces.hasNext() == true){
                nativePieceName = nativePieces.next();
                nativePieceLineOfFire = nativePieceWithItsLineOfFire.get(nativePieceName);
                for(int j=0; j<nativePieceLineOfFire.size(); j++){
                    if(opponentPieceNextPossiblePositions.contains(nativePieceLineOfFire.get(j).toString()) == true){
                        if(checkDisablingPiecesWithPositions.get(this.piece[i].getName()) != null){
                            checkDisablingPiecesWithPositions.get(this.piece[i].getName()).add(nativePieceLineOfFire.get(j));
                        }else{
                            ArrayList<Position> disablingPositions = new ArrayList<Position>();
                            disablingPositions.add(nativePieceLineOfFire.get(j));
                            checkDisablingPiecesWithPositions.put(this.piece[i].getName(), disablingPositions);
                        }
                    }
                }
            }
        }
        
        return checkDisablingPiecesWithPositions;
    }

    private void setCheckData(Piece kingUnderCheck, ArrayList<Piece> nativePieceNames, ArrayList<Position> kingNextPossiblePositions, HashMap<String, ArrayList<Position>> allowedPiecesOfOpponentWithAllowedPositions) {
        
        Check check = this.lastMove.getCheck();
        check.setKingUnderCheck(kingUnderCheck);
        for(int i=0; i<nativePieceNames.size(); i++){
            check.getCheckGivenBy()[i] = nativePieceNames.get(i);
            check.getCheckGivenPosition()[i] = nativePieceNames.get(i).getCurrentPosition();
        }
        check.setKingPosition(kingUnderCheck.getCurrentPosition());
        check.getOpponentKingNextPossiblePosition().put(kingUnderCheck.getName(), kingNextPossiblePositions);
        check.getAllowedPiecesOfOpponentWithAllowedPositions().putAll(allowedPiecesOfOpponentWithAllowedPositions);
    }
    
    private boolean isBishop(Piece nativePieceName) {
        boolean result = false;
        if(nativePieceName.getName().length() == 3){
            if(nativePieceName.getName().charAt(2) == 'B'){
                result = true;
            }
        }
        
        return result;
    }
    
    private boolean isQueen(Piece nativePieceName) {
        boolean result = false;
        if(nativePieceName.getName().length() == 3){
            if(nativePieceName.getName().charAt(2) == 'Q'){
                result = true;
            }
        }else if(nativePieceName.getName().length() == 2){
            if(nativePieceName.getName().charAt(1) == 'Q'){
                result = true;
            }
        }
            
        return result;
    }
    
    private ArrayList<Position> getRockLineOfFire(Piece nativePieceName, Piece kingUnderCheck) {
        ArrayList<Position> rockLineOfFire = new ArrayList<Position>();
        String[] column = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] row = {"1", "2", "3", "4", "5", "6", "7", "8"};
        int  nativePieceRowIndex = Arrays.asList(row).indexOf(nativePieceName.getCurrentPosition().getRow());
        int  nativePieceColumnIndex = Arrays.asList(column).indexOf(nativePieceName.getCurrentPosition().getColumn());
        int  kingRowIndex = Arrays.asList(row).indexOf(kingUnderCheck.getCurrentPosition().getRow());
        int  kingColumnIndex = Arrays.asList(column).indexOf(kingUnderCheck.getCurrentPosition().getColumn());
        
        if((kingRowIndex - nativePieceRowIndex  > 0) && (kingColumnIndex - nativePieceColumnIndex == 0)){
            int i=nativePieceRowIndex;
            while(i<row.length){
                rockLineOfFire.add(new Position(row[i], column[nativePieceColumnIndex]));
                i++;
            }
            
        }else if((kingRowIndex - nativePieceRowIndex == 0) && (kingColumnIndex - nativePieceColumnIndex > 0)){
            int j=nativePieceColumnIndex;
            while(j<column.length){
                rockLineOfFire.add(new Position(row[nativePieceRowIndex], column[j]));
                j++;
            }
            
        }else if((kingRowIndex - nativePieceRowIndex < 0) && (kingColumnIndex - nativePieceColumnIndex == 0)){
            int i=nativePieceRowIndex;
            while(i>=0){
                rockLineOfFire.add(new Position(row[i], column[nativePieceColumnIndex]));
                i--;
            }
            
        }else if((kingRowIndex - nativePieceRowIndex == 0) && (kingColumnIndex - nativePieceColumnIndex < 0)){
            int j=nativePieceColumnIndex;
            while(j>=0){
                rockLineOfFire.add(new Position(row[nativePieceRowIndex], column[j]));
                j--;
            }
        }
        
        return rockLineOfFire;
    }
    
    private ArrayList<Position> getBishopLineOfFire(Piece nativePieceName, Piece kingUnderCheck) {
        ArrayList<Position> bishopLineOfFire = new ArrayList<Position>();
        String[] column = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] row = {"1", "2", "3", "4", "5", "6", "7", "8"};
        int  nativePieceRowIndex = Arrays.asList(row).indexOf(nativePieceName.getCurrentPosition().getRow());
        int  nativePieceColumnIndex = Arrays.asList(column).indexOf(nativePieceName.getCurrentPosition().getColumn());
        int  kingRowIndex = Arrays.asList(row).indexOf(kingUnderCheck.getCurrentPosition().getRow());
        int  kingColumnIndex = Arrays.asList(column).indexOf(kingUnderCheck.getCurrentPosition().getColumn());
        
        if((kingRowIndex - nativePieceRowIndex > 0) && (kingColumnIndex - nativePieceColumnIndex > 0)){
            int i=nativePieceRowIndex;
            int j=nativePieceColumnIndex;
            while(i<row.length && j<row.length){
                bishopLineOfFire.add(new Position(row[i], column[j]));
                i++;
                j++;
            }
            
        }else if((kingRowIndex - nativePieceRowIndex < 0) && (kingColumnIndex - nativePieceColumnIndex > 0)){
            int i=nativePieceRowIndex;
            int j=nativePieceColumnIndex;
            while(i>=0 && j<column.length){
                bishopLineOfFire.add(new Position(row[i], column[j]));
                i--;
                j++;
            }
            
        }else if((kingRowIndex - nativePieceRowIndex < 0) && (kingColumnIndex - nativePieceColumnIndex < 0)){
            int i=nativePieceRowIndex;
            int j=nativePieceColumnIndex;
            while(i>=0 && j>=0){
                bishopLineOfFire.add(new Position(row[i], column[j]));
                i--;
                j--;
            }
            
        }else if((kingRowIndex - nativePieceRowIndex > 0) && (kingColumnIndex - nativePieceColumnIndex < 0)){
            int i=nativePieceRowIndex;
            int j=nativePieceColumnIndex;
            while(i<row.length && j>=0){
                bishopLineOfFire.add(new Position(row[i], column[j]));
                i++;
                j--;
            }
        }
        
        return bishopLineOfFire;
    }
    
    private ArrayList<Position> getQueenLineOfFire(Piece nativePieceName, Piece kingUnderCheck) {
        ArrayList<Position> queenLineOfFire = new ArrayList<Position>();
        
        queenLineOfFire.addAll(getRockLineOfFire(nativePieceName, kingUnderCheck));
        queenLineOfFire.addAll(getBishopLineOfFire(nativePieceName, kingUnderCheck));
        
        return queenLineOfFire;
    }
    
    private ArrayList<Position> getForDisablingPieces(ArrayList<Position> orignalLineOfFire, Piece kingUnderCheck) {
        ArrayList<Position> lineOfFireForDisablingPieces = new ArrayList<Position>();
        
        lineOfFireForDisablingPieces.addAll(orignalLineOfFire);
        
        for(int i=0; i<lineOfFireForDisablingPieces.size(); i++){
            if(lineOfFireForDisablingPieces.get(i).toString().equals(kingUnderCheck.getCurrentPosition().toString()) == true){
                while(i<lineOfFireForDisablingPieces.size() == true){
                    lineOfFireForDisablingPieces.remove(i);
                }
            }
        }
        
        return lineOfFireForDisablingPieces;
    }

    private boolean isPawn(String name) {
        boolean result = false;
        
        if(name.charAt(1) == 'P'){
            result = true;
        }
        return result;
    }

    //end check given functions

    
    
    
    
    //piece beaten methods
    
    public void removePiece(Piece pieceBeaten) {
        ChessBoardBox chessBoardBox = this.getChessBoardBoxAt(pieceBeaten.getCurrentPosition().toString());
        chessBoardBox.setPieceSitStatus(false);
        chessBoardBox.setPiece(null);
        pieceBeaten.deadPiece();
    }
    
    //end of piece beaten methods
    
    
    
    
    
    
    //control methods

    public boolean isCurrentPlayerControlValid(String userId) {
        boolean result = false;
        
        if(userId.equals(this.currentPlayerControl) == true){
            result = true;
        }
        
        return result;
    }

    public boolean isMaximumGameTimeValid() {
        boolean result = false;
        
        if(this.maximumGameTime > 0){
            result = true;
        }
        
        return result;
    }
    //end of control methods
}