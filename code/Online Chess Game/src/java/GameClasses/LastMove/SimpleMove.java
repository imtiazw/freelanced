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
public class SimpleMove extends Move{
    private Piece pieceMoved;
    private Position previousPosition;
    private Position currentPosition;

    public SimpleMove() {
        super();
        this.pieceMoved = new Piece();
        this.previousPosition = new Position();
        this.currentPosition = new Position();
    }
    
    protected void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        if(this.pieceMoved.getName().equals("") == false){
            this.pieceMoved = chessPlayBoard.getPiece(this.pieceMoved.getName());
        }
    }

    public Piece getPieceMoved() {
        return pieceMoved;
    }

    public void setPieceMoved(Piece pieceMoved) {
        this.pieceMoved = pieceMoved;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Position previousPosition) {
        this.previousPosition = previousPosition;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }
    
    
}
