/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author ALI
 */
class GameMovesWindow {
    private LinkedList<GameMove> gameMove;

    public GameMovesWindow() {
        this.gameMove = new LinkedList<GameMove>();
    }
    
    public GameMovesWindow(LinkedList<GameMove> gameMove) {
        this.gameMove = new LinkedList<GameMove>();
        for(int i=0; i<gameMove.size(); i++){
            this.gameMove.add(gameMove.get(i));
        }
    }

    public GameMovesWindow(GameMovesWindow gameMovesWindow){
        this.gameMove = new LinkedList<GameMove>();
        for(int i=0; i<gameMovesWindow.getGameMove().size(); i++){
            this.gameMove.add(gameMovesWindow.getGameMove().get(i));
        }
    }
    public LinkedList<GameMove> getGameMove() {
        return gameMove;
    }

    public void setGameMove(LinkedList<GameMove> gameMove) {
        for(int i=0; i<gameMove.size(); i++){
            this.gameMove.add(gameMove.get(i));
        }
    }
    
    public void addGameMove(GameMove gameMove){
        this.gameMove.add(gameMove);
    }
    
    public void addMove(Position previousPosition, Position currentPosition){}
    
    public void refreshGameMovesWindow(){}
}
