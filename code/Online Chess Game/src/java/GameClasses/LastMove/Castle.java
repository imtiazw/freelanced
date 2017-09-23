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
public class Castle {
    private Piece kingName;
    private Piece rockName;
    private Position kingPreviousPosition;
    private Position kingCurrentPosition;
    private Position rockPreviousPosition;
    private Position rockCurrentPosition;
    private boolean rightCastle;
    private boolean leftCastle;

    public Castle() {
        this.kingName = new Piece();
        this.rockName = new Piece();
        this.kingPreviousPosition = new Position();
        this.kingCurrentPosition = new Position();
        this.rockPreviousPosition = new Position();
        this.rockCurrentPosition = new Position();
        this.rightCastle = false;
        this.leftCastle = false;
    }

    protected void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        if(this.kingName.getName().equals("") == false){
            this.kingName = chessPlayBoard.getPiece(this.kingName.getName());
        }
        if(this.rockName.getName().equals("") == false){
            this.rockName = chessPlayBoard.getPiece(this.rockName.getName());
        }
    }
    
    public Piece getKingName() {
        return kingName;
    }

    public void setKingName(Piece kingName) {
        this.kingName = kingName;
    }

    public Piece getRockName() {
        return rockName;
    }

    public void setRockName(Piece rockName) {
        this.rockName = rockName;
    }

    public Position getKingPreviousPosition() {
        return kingPreviousPosition;
    }

    public void setKingPreviousPosition(Position kingPreviousPosition) {
        this.kingPreviousPosition = kingPreviousPosition;
    }

    public Position getKingCurrentPosition() {
        return kingCurrentPosition;
    }

    public void setKingCurrentPosition(Position kingCurrentPosition) {
        this.kingCurrentPosition = kingCurrentPosition;
    }

    public Position getRockPreviousPosition() {
        return rockPreviousPosition;
    }

    public void setRockPreviousPosition(Position rockPreviousPosition) {
        this.rockPreviousPosition = rockPreviousPosition;
    }

    public Position getRockCurrentPosition() {
        return rockCurrentPosition;
    }

    public void setRockCurrentPosition(Position rockCurrentPosition) {
        this.rockCurrentPosition = rockCurrentPosition;
    }

    public boolean isRightCastle() {
        return rightCastle;
    }

    public void setRightCastle(boolean rightCastle) {
        this.rightCastle = rightCastle;
    }

    public boolean isLeftCastle() {
        return leftCastle;
    }

    public void setLeftCastle(boolean leftCastle) {
        this.leftCastle = leftCastle;
    }
    
    
}
