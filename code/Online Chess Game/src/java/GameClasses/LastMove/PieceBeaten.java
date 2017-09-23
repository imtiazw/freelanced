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
public class PieceBeaten extends Move{
    private Piece pieceBeaten;
    private Piece beatenBy;
    private Position beatingPiecePreviousPosition;
    private Position beatingPieceCurrentPosition;
    private Position beatenPiecePosition;

    public PieceBeaten() {
        super();
        this.pieceBeaten = new Piece();
        this.beatenBy = new Piece();
        this.beatingPiecePreviousPosition = new Position();
        this.beatingPieceCurrentPosition = new Position();
        this.beatenPiecePosition = new Position();
    }

    protected void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        if(this.pieceBeaten.getName().equals("") == false){
            this.pieceBeaten = chessPlayBoard.getPiece(this.pieceBeaten.getName());
        }
        if(this.beatenBy.getName().equals("") == false){
            this.beatenBy = chessPlayBoard.getPiece(this.beatenBy.getName());
        }
    }
    
    public Piece getPieceBeaten() {
        return pieceBeaten;
    }

    public void setPieceBeaten(Piece pieceBeaten) {
        this.pieceBeaten = pieceBeaten;
    }

    public Piece getBeatenBy() {
        return beatenBy;
    }

    public void setBeatenBy(Piece beatenBy) {
        this.beatenBy = beatenBy;
    }

    public Position getBeatingPiecePreviousPosition() {
        return beatingPiecePreviousPosition;
    }

    public void setBeatingPiecePreviousPosition(Position beatingPiecePreviousPosition) {
        this.beatingPiecePreviousPosition = beatingPiecePreviousPosition;
    }

    public Position getBeatingPieceCurrentPosition() {
        return beatingPieceCurrentPosition;
    }

    public void setBeatingPieceCurrentPosition(Position beatingPieceCurrentPosition) {
        this.beatingPieceCurrentPosition = beatingPieceCurrentPosition;
    }

    public Position getBeatenPiecePosition() {
        return beatenPiecePosition;
    }

    public void setBeatenPiecePosition(Position beatenPiecePosition) {
        this.beatenPiecePosition = beatenPiecePosition;
    }
    
    
}
