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
public class PawnPromotedJson {
    private String previousPosition;
    private String currentPosition;
    private String pieceName;
    private String newPieceName;
    
    public PawnPromotedJson() {
        this.previousPosition = "";
        this.currentPosition = "";
        this.pieceName = "";
        this.newPieceName = "";
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

    public String getNewPieceName() {
        return newPieceName;
    }

    public void setNewPieceName(String newPieceName) {
        this.newPieceName = newPieceName;
    }
}
