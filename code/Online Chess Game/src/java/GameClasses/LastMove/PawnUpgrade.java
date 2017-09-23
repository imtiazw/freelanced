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
public class PawnUpgrade {
    private Piece pawnName;
    private Piece upgradedPiece;
    private Position pawnPreviousPosition;
    private Position pawnCurrentPosition;
    private Position upgradedPiecePosition;

    public PawnUpgrade() {
        this.pawnName = new Piece();
        this.upgradedPiece = new Piece();
        this.pawnPreviousPosition = new Position();
        this.pawnCurrentPosition = new Position();
        this.upgradedPiecePosition = new Position();
    }

    public PawnUpgrade(Piece pawnName, Piece upgradedPiece, Position pawnPreviousPosition, Position pawnCurrentPosition, Position upgradedPiecePosition) {
        this.pawnName = pawnName;
        this.upgradedPiece = upgradedPiece;
        this.pawnPreviousPosition = pawnPreviousPosition;
        this.pawnCurrentPosition = pawnCurrentPosition;
        this.upgradedPiecePosition = upgradedPiecePosition;
    }

    protected void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        if(this.pawnName.getName().equals("") == false){
            this.pawnName = chessPlayBoard.getPiece(this.pawnName.getName());
        }
        //here upgraded piece is not converted to deep structure as upgraded piece does not exists in the chess play board.
    }
    
    public Piece getPawnName() {
        return pawnName;
    }

    public void setPawnName(Piece pawnName) {
        this.pawnName = pawnName;
    }

    public Piece getUpgradedPiece() {
        return upgradedPiece;
    }

    public void setUpgradedPiece(Piece upgradedPiece) {
        this.upgradedPiece = upgradedPiece;
    }

    public Position getPawnPreviousPosition() {
        return pawnPreviousPosition;
    }

    public void setPawnPreviousPosition(Position pawnPreviousPosition) {
        this.pawnPreviousPosition = pawnPreviousPosition;
    }

    public Position getPawnCurrentPosition() {
        return pawnCurrentPosition;
    }

    public void setPawnCurrentPosition(Position pawnCurrentPosition) {
        this.pawnCurrentPosition = pawnCurrentPosition;
    }

    public Position getUpgradedPiecePosition() {
        return upgradedPiecePosition;
    }

    public void setUpgradedPiecePosition(Position upgradedPiecePosition) {
        this.upgradedPiecePosition = upgradedPiecePosition;
    }
    
    
}
