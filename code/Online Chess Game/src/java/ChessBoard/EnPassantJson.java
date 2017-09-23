/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

/**
 *
 * @author ALI
 */
public class EnPassantJson {
    private String previousPosition;
    private String currentPosition;
    private String pieceName;
    private String oppositeRightPawn;
    private String oppositeLeftPawn;

    public EnPassantJson() {
        this.previousPosition = "";
        this.currentPosition = "";
        this.pieceName = "";
        this.oppositeLeftPawn = "";
        this.oppositeRightPawn = "";
    }

    public String getOppositeRightPawn() {
        return oppositeRightPawn;
    }

    public void setOppositeRightPawn(String oppositeRightPawn) {
        this.oppositeRightPawn = oppositeRightPawn;
    }

    public String getOppositeLeftPawn() {
        return oppositeLeftPawn;
    }

    public void setOppositeLeftPawn(String oppositeLeftPawn) {
        this.oppositeLeftPawn = oppositeLeftPawn;
    }

    public String getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(String previousPosition) {
        this.previousPosition = previousPosition;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }
    
}
