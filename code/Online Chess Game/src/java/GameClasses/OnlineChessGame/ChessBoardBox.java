/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

/**
 *
 * @author ALI
 */
public class ChessBoardBox {
    private String color;
    private boolean pieceSitStatus;
    private Piece piece;
    private Position location;

    public ChessBoardBox() {
        this.color = "";
        this.pieceSitStatus = false;
        this.piece = null;
        this.location = new Position();
    }

    public ChessBoardBox(String color, boolean pieceSitStatus, Piece piece, Position location) {
        this.color = color;
        this.pieceSitStatus = pieceSitStatus;
        this.piece = piece;
        this.location = new Position(location);
        //this.location.setPosition(location);
    }
    
    public ChessBoardBox(ChessBoardBox chessBoardBox){
        color = chessBoardBox.color;
        pieceSitStatus = chessBoardBox.pieceSitStatus;
        piece = chessBoardBox.piece;
        location.setPosition(chessBoardBox.location);
    }

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location.column = location.column;
        this.location.row = location.row;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isPieceSitStatus() {
        return pieceSitStatus;
    }

    public void setPieceSitStatus(boolean pieceSitStatus) {
        this.pieceSitStatus = pieceSitStatus;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    
}
