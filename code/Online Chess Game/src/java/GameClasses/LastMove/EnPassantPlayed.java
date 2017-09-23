/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.LastMove;

import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.Piece;
import GameClasses.OnlineChessGame.Position;

/**
 *
 * @author ALI
 */
public class EnPassantPlayed {
    private Piece beatenPawnName;
    private Piece beatingPawnName;
    private Position beatenPawnPosition;
    private Position beatingPawnPreviousPosition;
    private Position beatingPawnCurrentPosition;

    public EnPassantPlayed() {
        this.beatenPawnName = new Piece();
        this.beatingPawnName = new Piece();
        this.beatenPawnPosition = new Position();
        this.beatingPawnPreviousPosition = new Position();
        this.beatingPawnCurrentPosition = new Position();
    }

    public EnPassantPlayed(Piece beatenPawnName, Piece beatingPawnName, Position beatenPawnPosition, Position beatingPawnPreviousPosition, Position beatingPawnCurrentPosition) {
        this.beatenPawnName = beatenPawnName;
        this.beatingPawnName = beatingPawnName;
        this.beatenPawnPosition = beatenPawnPosition;
        this.beatingPawnPreviousPosition = beatingPawnPreviousPosition;
        this.beatingPawnCurrentPosition = beatingPawnCurrentPosition;
    }

    protected void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        if(this.beatenPawnName.getName().equals("") == false){
            this.beatenPawnName = chessPlayBoard.getPiece(this.beatenPawnName.getName());
        }
        if(this.beatingPawnName.getName().equals("") == false){
            this.beatingPawnName = chessPlayBoard.getPiece(this.beatingPawnName.getName());
        }
    }
    
    public Piece getBeatenPawnName() {
        return beatenPawnName;
    }

    public void setBeatenPawnName(Piece beatenPawnName) {
        this.beatenPawnName = beatenPawnName;
    }

    public Piece getBeatingPawnName() {
        return beatingPawnName;
    }

    public void setBeatingPawnName(Piece beatingPawnName) {
        this.beatingPawnName = beatingPawnName;
    }

    public Position getBeatenPawnPosition() {
        return beatenPawnPosition;
    }

    public void setBeatenPawnPosition(Position beatenPawnPosition) {
        this.beatenPawnPosition = beatenPawnPosition;
    }

    public Position getBeatingPawnPreviousPosition() {
        return beatingPawnPreviousPosition;
    }

    public void setBeatingPawnPreviousPosition(Position beatingPawnPreviousPosition) {
        this.beatingPawnPreviousPosition = beatingPawnPreviousPosition;
    }

    public Position getBeatingPawnCurrentPosition() {
        return beatingPawnCurrentPosition;
    }

    public void setBeatingPawnCurrentPosition(Position beatingPawnCurrentPosition) {
        this.beatingPawnCurrentPosition = beatingPawnCurrentPosition;
    }
    
    
}
