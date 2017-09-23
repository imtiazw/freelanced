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
class GameMove {
    private Position previousPosition;
    private Position currentPosition;

    public GameMove() {
        this.previousPosition = new Position();
        this.currentPosition = new Position();
    }

    public GameMove(Position previousPosition, Position currentPosition) {
        this.previousPosition = new Position(previousPosition);
        this.currentPosition = new Position(currentPosition);
    }

    GameMove(GameMove gameMove){
        this.currentPosition.setPosition(gameMove.currentPosition);
        this.previousPosition.setPosition(gameMove.previousPosition);
    }
    
    public void setGameMove(GameMove gameMove){
        this.currentPosition.setPosition(gameMove.currentPosition);
        this.previousPosition.setPosition(gameMove.previousPosition);
    }
    
    public Position getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Position previousPosition) {
        this.previousPosition.setPosition(previousPosition);
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition.setPosition(currentPosition);
    }
    
    
}
