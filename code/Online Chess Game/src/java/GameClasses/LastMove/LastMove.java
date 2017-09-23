/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.LastMove;

import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.Person;
import GameClasses.OnlineChessGame.Player;

/**
 *
 * @author ALI
 */
public class LastMove {
    private SimpleMove simpleMove;
    private PieceBeaten pieceBeaten;
    private SpecialMove specialMove;
    private Check check;
    private CheckMate checkMate;
    private boolean sImpleMove;
    private boolean pIeceBeaten;
    private boolean sPecialMove;
    private boolean cHeck;
    private boolean cHeckMate;
    private Person playedBy;
    private Person toBeRecievedBy;
    private boolean played;
    private boolean recieved;
    private boolean moveSkipped;
    private boolean hostBlockedPieces;
    private boolean clientBlockedPieces;
    
    public void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        simpleMove.fromShallowStructureToDeepStrcture(chessPlayBoard);
        pieceBeaten.fromShallowStructureToDeepStrcture(chessPlayBoard);
        specialMove.getCastle().fromShallowStructureToDeepStrcture(chessPlayBoard);
        specialMove.getEnPassantGiven().fromShallowStructureToDeepStrcture(chessPlayBoard);
        specialMove.getEnPassantPlayed().fromShallowStructureToDeepStrcture(chessPlayBoard);
        specialMove.getPawnUpgrade().fromShallowStructureToDeepStrcture(chessPlayBoard);
        check.fromShallowStructureToDeepStrcture(chessPlayBoard);
        checkMate.fromShallowStructureToDeepStrcture(chessPlayBoard);
        if(this.playedBy.getUserId().equals("") == false){
            this.playedBy = chessPlayBoard.getChessBoardPlayerListWindow().getPerson(this.playedBy.getUserId());
        }
        if(this.toBeRecievedBy.getUserId().equals("") == false){
            this.toBeRecievedBy = chessPlayBoard.getChessBoardPlayerListWindow().getPerson(this.toBeRecievedBy.getUserId());
        }
    }

    public LastMove() {
        this.simpleMove = new SimpleMove();
        this.pieceBeaten = new PieceBeaten();
        this.specialMove = new SpecialMove();
        this.check = new Check();
        this.checkMate = new CheckMate();
        this.sImpleMove = false;
        this.pIeceBeaten = false;
        this.sPecialMove = false;
        this.cHeck = false;
        this.cHeckMate = false;
        this.playedBy = new Player();
        this.toBeRecievedBy = new Player();
        this.played = false;
        this.recieved = false;
        this.moveSkipped = false;
        this.hostBlockedPieces = false;
        this.clientBlockedPieces = false;
    }

    public SimpleMove getSimpleMove() {
        return simpleMove;
    }

    public void setSimpleMove(SimpleMove simpleMove) {
        this.simpleMove = simpleMove;
    }

    public PieceBeaten getPieceBeaten() {
        return pieceBeaten;
    }

    public void setPieceBeaten(PieceBeaten pieceBeaten) {
        this.pieceBeaten = pieceBeaten;
    }

    public SpecialMove getSpecialMove() {
        return specialMove;
    }

    public void setSpecialMove(SpecialMove specialMove) {
        this.specialMove = specialMove;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public CheckMate getCheckMate() {
        return checkMate;
    }

    public void setCheckMate(CheckMate checkMate) {
        this.checkMate = checkMate;
    }

    public boolean issImpleMove() {
        return sImpleMove;
    }

    public void setsImpleMove(boolean sImpleMove) {
        this.sImpleMove = sImpleMove;
    }

    public boolean ispIeceBeaten() {
        return pIeceBeaten;
    }

    public void setpIeceBeaten(boolean pIeceBeaten) {
        this.pIeceBeaten = pIeceBeaten;
    }

    public boolean issPecialMove() {
        return sPecialMove;
    }

    public void setsPecialMove(boolean sPecialMove) {
        this.sPecialMove = sPecialMove;
    }

    public boolean iscHeck() {
        return cHeck;
    }

    public void setcHeck(boolean cHeck) {
        this.cHeck = cHeck;
    }

    public boolean iscHeckMate() {
        return cHeckMate;
    }

    public void setcHeckMate(boolean cHeckMate) {
        this.cHeckMate = cHeckMate;
    }

    public Person getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(Player playedBy) {
        this.playedBy = playedBy;
    }

    public Person getToBeRecievedBy() {
        return toBeRecievedBy;
    }

    public void setToBeRecievedBy(Player toBeRecievedBy) {
        this.toBeRecievedBy = toBeRecievedBy;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public boolean isRecieved() {
        return recieved;
    }

    public void setRecieved(boolean recieved) {
        this.recieved = recieved;
    }

    public boolean isMoveSkipped() {
        return moveSkipped;
    }

    public void setMoveSkipped(boolean moveSkipped) {
        this.moveSkipped = moveSkipped;
    }

    public boolean isHostBlockedPieces() {
        return hostBlockedPieces;
    }

    public void setHostBlockedPieces(boolean hostBlockedPieces) {
        this.hostBlockedPieces = hostBlockedPieces;
    }

    public boolean isClientBlockedPieces() {
        return clientBlockedPieces;
    }

    public void setClientBlockedPieces(boolean clientBlockedPieces) {
        this.clientBlockedPieces = clientBlockedPieces;
    }

    public void setCheckMateData() {
        this.cHeckMate = true;
        this.checkMate.setCheckMateBy(this.playedBy);
        this.checkMate.setCheckMateTo(this.toBeRecievedBy);
    }

    public boolean isPlayedByValid(Person opponentPlayer) {
        boolean result = false;
        
        if(this.playedBy.getUserId().equals(opponentPlayer.getUserId()) == true){
            result = true;
        }
        
        return result;
    }

    public boolean isToBeReceivedByValid(String userId) {
        boolean result = false;
        
        if(this.toBeRecievedBy.getUserId().equals(userId) == true){
            result = true;
        }
        
        return result;
    }
    
    
}
