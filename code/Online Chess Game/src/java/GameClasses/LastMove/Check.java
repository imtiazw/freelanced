/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.LastMove;

import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.Piece;
import GameClasses.OnlineChessGame.Position;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ALI
 */
public class Check {
    private Piece kingUnderCheck;
    private Piece[] checkGivenBy;
    private Position kingPosition;
    private Position[] checkGivenPosition;
    private HashMap<String, ArrayList<Position>> opponentKingNextPossiblePosition;
    private HashMap<String, ArrayList<Position>> allowedPiecesOfOpponentWithAllowedPositions;

    public Check() {
        this.kingUnderCheck = new Piece();
        this.checkGivenBy = new Piece[2];
        this.kingPosition = new Position();
        this.checkGivenPosition = new Position[2];
        this.opponentKingNextPossiblePosition = new HashMap<String, ArrayList<Position>>();
        this.allowedPiecesOfOpponentWithAllowedPositions = new HashMap<String, ArrayList<Position>>();
    }

    public Check(Piece kingUnderCheck, Piece[] checkGivenBy, Position kingPosition, Position[] checkGivenPosition, HashMap<String, ArrayList<Position>> opponentKingNextPossiblePosition, HashMap<String, ArrayList<Position>> allowedPiecesOfOpponentWithAllowedPositions) {
        this.kingUnderCheck = kingUnderCheck;
        this.checkGivenBy = checkGivenBy;
        this.kingPosition = kingPosition;
        this.checkGivenPosition = checkGivenPosition;
        this.opponentKingNextPossiblePosition = opponentKingNextPossiblePosition;
        this.allowedPiecesOfOpponentWithAllowedPositions = allowedPiecesOfOpponentWithAllowedPositions;
    }
    
    protected void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        if(this.kingUnderCheck.getName().equals("") == false){
            this.kingUnderCheck = chessPlayBoard.getPiece(this.kingUnderCheck.getName());
        }
        for(int i=0; i<this.checkGivenBy.length; i++){
            if(this.checkGivenBy[i] != null){
                this.checkGivenBy[i] = chessPlayBoard.getPiece(this.checkGivenBy[i].getName());
            }
        }
        
    }
    
    public Piece getKingUnderCheck() {
        return kingUnderCheck;
    }

    public void setKingUnderCheck(Piece kingUnderCheck) {
        this.kingUnderCheck = kingUnderCheck;
    }

    public Piece[] getCheckGivenBy() {
        return checkGivenBy;
    }

    public void setCheckGivenBy(Piece[] checkGivenBy) {
        this.checkGivenBy = checkGivenBy;
    }

    public Position getKingPosition() {
        return kingPosition;
    }

    public void setKingPosition(Position kingPosition) {
        this.kingPosition = kingPosition;
    }

    public Position[] getCheckGivenPosition() {
        return checkGivenPosition;
    }

    public void setCheckGivenPosition(Position[] checkGivenPosition) {
        this.checkGivenPosition = checkGivenPosition;
    }

    public HashMap<String, ArrayList<Position>> getOpponentKingNextPossiblePosition() {
        return opponentKingNextPossiblePosition;
    }

    public void setOpponentKingNextPossiblePosition(HashMap<String, ArrayList<Position>> opponentKingNextPossiblePosition) {
        this.opponentKingNextPossiblePosition = opponentKingNextPossiblePosition;
    }

    public HashMap<String, ArrayList<Position>> getAllowedPiecesOfOpponentWithAllowedPositions() {
        return allowedPiecesOfOpponentWithAllowedPositions;
    }

    public void setAllowedPiecesOfOpponentWithAllowedPositions(HashMap<String, ArrayList<Position>> allowedPiecesOfOpponentWithAllowedPositions) {
        this.allowedPiecesOfOpponentWithAllowedPositions = allowedPiecesOfOpponentWithAllowedPositions;
    }

    public boolean isDoubleCheck() {
        boolean result = true;
        
        for (Piece checkGivenBy1 : this.checkGivenBy) {
            if (checkGivenBy1 == null) {
                result = false;
            }
        }
        
        return result;
    }

    public void removePiecesOtherThanKingInAllowedPieces() {
        ArrayList<Position> kingPositions = allowedPiecesOfOpponentWithAllowedPositions.get(this.kingUnderCheck.getName());
        allowedPiecesOfOpponentWithAllowedPositions.clear();
        if(kingPositions != null){
            allowedPiecesOfOpponentWithAllowedPositions.put(this.kingUnderCheck.getName(), kingPositions);
        }
    }

    public boolean isNativeAllowedPieceNull() {
        boolean result = false;
        
        if(this.allowedPiecesOfOpponentWithAllowedPositions.isEmpty() == true){
            result = true;
        }
        
        return result;
    }
    
    
}
