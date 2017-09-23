/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.LastMove;

import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.Person;
import GameClasses.OnlineChessGame.Piece;
import GameClasses.OnlineChessGame.Player;
import GameClasses.OnlineChessGame.Position;

/**
 *
 * @author ALI
 */
public class EnPassantGiven{
    private Person nativePlayer;
    private Person opponentPlayer;
    private Piece nativeEnPassantPiece;
    private Position nativePiecePreviousPosition;
    private Position nativePieceCurrentPosition;
    private boolean rightOpponentEnPassantPiece;
    private boolean leftOpponentEnPassantPiece;
    private Piece opponentRightEnPassantPiece;
    private Piece opponentLeftEnPassantPiece;
    private Position opponentRightPiecePreviousPosition;
    private Position opponentRightPieceNextPossiblePosition;
    private Position opponentLeftPiecePreviousPosition;
    private Position opponentLeftPieceNextPossiblePosition;
    private boolean enPassantExpired;

    public EnPassantGiven() {
        super();
        this.nativePlayer = new Player();
        this.opponentPlayer = new Player();
        this.nativeEnPassantPiece = new Piece();
        this.nativePiecePreviousPosition = new Position();
        this.nativePieceCurrentPosition = new Position();
        this.rightOpponentEnPassantPiece = false;
        this.leftOpponentEnPassantPiece = false;
        this.opponentRightEnPassantPiece = new Piece();
        this.opponentLeftEnPassantPiece = new Piece();
        this.opponentRightPiecePreviousPosition = new Position();
        this.opponentRightPieceNextPossiblePosition = new Position();
        this.opponentLeftPiecePreviousPosition = new Position();
        this.opponentLeftPieceNextPossiblePosition = new Position();
        this.enPassantExpired = true;
    }

    public EnPassantGiven(Player nativePlayer, Player opponentPlayer, Piece nativeEnPassantPiece, Position nativePiecePreviousPosition, Position nativePieceCurrentPosition, boolean rightOpponentEnPassantPiece, boolean leftOpponentEnPassantPiece, Piece opponentRightEnPassantPiece, Piece opponentLeftEnPassantPiece, Position opponentRightPiecePreviousPosition, Position opponentRightPieceNextPossiblePosition, Position opponentLeftPiecePreviousPosition, Position opponentLeftPieceNextPossiblePosition, boolean enPassantExpired) {
        this.nativePlayer = nativePlayer;
        this.opponentPlayer = opponentPlayer;
        this.nativeEnPassantPiece = nativeEnPassantPiece;
        this.nativePiecePreviousPosition = nativePiecePreviousPosition;
        this.nativePieceCurrentPosition = nativePieceCurrentPosition;
        this.rightOpponentEnPassantPiece = rightOpponentEnPassantPiece;
        this.leftOpponentEnPassantPiece = leftOpponentEnPassantPiece;
        this.opponentRightEnPassantPiece = opponentRightEnPassantPiece;
        this.opponentLeftEnPassantPiece = opponentLeftEnPassantPiece;
        this.opponentRightPiecePreviousPosition = opponentRightPiecePreviousPosition;
        this.opponentRightPieceNextPossiblePosition = opponentRightPieceNextPossiblePosition;
        this.opponentLeftPiecePreviousPosition = opponentLeftPiecePreviousPosition;
        this.opponentLeftPieceNextPossiblePosition = opponentLeftPieceNextPossiblePosition;
        this.enPassantExpired = enPassantExpired;
    }
    
    protected void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        if(this.nativePlayer.getUserId().equals("") == false){
            this.nativePlayer = chessPlayBoard.getChessBoardPlayerListWindow().getPerson(this.nativePlayer.getUserId());
        }
        if(this.opponentPlayer.getUserId().equals("") == false){
            this.opponentPlayer = chessPlayBoard.getChessBoardPlayerListWindow().getPerson(this.opponentPlayer.getUserId());
        }
        if(this.nativeEnPassantPiece.getName().equals("") == false){
            this.nativeEnPassantPiece = chessPlayBoard.getPiece(this.nativeEnPassantPiece.getName());
        }
        if(this.opponentRightEnPassantPiece.getName().equals("") == false){
            this.opponentRightEnPassantPiece = chessPlayBoard.getPiece(this.opponentRightEnPassantPiece.getName());
        }
        if(this.opponentLeftEnPassantPiece.getName().equals("") == false){
            this.opponentLeftEnPassantPiece = chessPlayBoard.getPiece(this.opponentLeftEnPassantPiece.getName());
        }
    }

    public Person getNativePlayer() {
        return nativePlayer;
    }

    public void setNativePlayer(Player nativePlayer) {
        this.nativePlayer = nativePlayer;
    }

    public Person getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    public Piece getNativeEnPassantPiece() {
        return nativeEnPassantPiece;
    }

    public void setNativeEnPassantPiece(Piece nativeEnPassantPiece) {
        this.nativeEnPassantPiece = nativeEnPassantPiece;
    }

    public Position getNativePiecePreviousPosition() {
        return nativePiecePreviousPosition;
    }

    public void setNativePiecePreviousPosition(Position nativePiecePreviousPosition) {
        this.nativePiecePreviousPosition = nativePiecePreviousPosition;
    }

    public Position getNativePieceCurrentPosition() {
        return nativePieceCurrentPosition;
    }

    public void setNativePieceCurrentPosition(Position nativePieceCurrentPosition) {
        this.nativePieceCurrentPosition = nativePieceCurrentPosition;
    }

    public boolean isRightOpponentEnPassantPiece() {
        return rightOpponentEnPassantPiece;
    }

    public void setRightOpponentEnPassantPiece(boolean rightOpponentEnPassantPiece) {
        this.rightOpponentEnPassantPiece = rightOpponentEnPassantPiece;
    }

    public boolean isLeftOpponentEnPassantPiece() {
        return leftOpponentEnPassantPiece;
    }

    public void setLeftOpponentEnPassantPiece(boolean leftOpponentEnPassantPiece) {
        this.leftOpponentEnPassantPiece = leftOpponentEnPassantPiece;
    }

    public Piece getOpponentRightEnPassantPiece() {
        return opponentRightEnPassantPiece;
    }

    public void setOpponentRightEnPassantPiece(Piece opponentRightEnPassantPiece) {
        this.opponentRightEnPassantPiece = opponentRightEnPassantPiece;
    }

    public Piece getOpponentLeftEnPassantPiece() {
        return opponentLeftEnPassantPiece;
    }

    public void setOpponentLeftEnPassantPiece(Piece opponentLeftEnPassantPiece) {
        this.opponentLeftEnPassantPiece = opponentLeftEnPassantPiece;
    }

    public Position getOpponentRightPiecePreviousPosition() {
        return opponentRightPiecePreviousPosition;
    }

    public void setOpponentRightPiecePreviousPosition(Position opponentRightPiecePreviousPosition) {
        this.opponentRightPiecePreviousPosition = opponentRightPiecePreviousPosition;
    }

    public Position getOpponentRightPieceNextPossiblePosition() {
        return opponentRightPieceNextPossiblePosition;
    }

    public void setOpponentRightPieceNextPossiblePosition(Position opponentRightPieceNextPossiblePosition) {
        this.opponentRightPieceNextPossiblePosition = opponentRightPieceNextPossiblePosition;
    }

    public Position getOpponentLeftPiecePreviousPosition() {
        return opponentLeftPiecePreviousPosition;
    }

    public void setOpponentLeftPiecePreviousPosition(Position opponentLeftPiecePreviousPosition) {
        this.opponentLeftPiecePreviousPosition = opponentLeftPiecePreviousPosition;
    }

    public Position getOpponentLeftPieceNextPossiblePosition() {
        return opponentLeftPieceNextPossiblePosition;
    }

    public void setOpponentLeftPieceNextPossiblePosition(Position opponentLeftPieceNextPossiblePosition) {
        this.opponentLeftPieceNextPossiblePosition = opponentLeftPieceNextPossiblePosition;
    }

    public boolean isEnPassantExpired() {
        return enPassantExpired;
    }

    public void setEnPassantExpired(boolean enPassantExpired) {
        this.enPassantExpired = enPassantExpired;
    }
    
    
}
