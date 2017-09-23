/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.LastMove;

import GameClasses.OnlineChessGame.Position;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ALI
 */
public class Move {
    private HashMap<String, ArrayList<Position>> nativeNextPossiblePositions;
    private HashMap<String, ArrayList<Position>> opponentNextPossiblePositions; 
    boolean nativeReturnSet;
    boolean opponentReturnSet;

    public Move() {
        this.nativeNextPossiblePositions = new HashMap<String, ArrayList<Position>>();
        this.opponentNextPossiblePositions = new HashMap<String, ArrayList<Position>>();
        this.nativeReturnSet = false;
        this.opponentReturnSet = false;
    }

    public HashMap<String, ArrayList<Position>> getNativeNextPossiblePositions() {
        return nativeNextPossiblePositions;
    }

    public void setNativeNextPossiblePositions(HashMap<String, ArrayList<Position>> nativeNextPossiblePositions) {
        this.nativeNextPossiblePositions = nativeNextPossiblePositions;
    }

    public HashMap<String, ArrayList<Position>> getOpponentNextPossiblePositions() {
        return opponentNextPossiblePositions;
    }

    public void setOpponentNextPossiblePositions(HashMap<String, ArrayList<Position>> opponentNextPossiblePositions) {
        this.opponentNextPossiblePositions = opponentNextPossiblePositions;
    }

    public boolean isNativeReturnSet() {
        return nativeReturnSet;
    }

    public void setNativeReturnSet(boolean nativeReturnSet) {
        this.nativeReturnSet = nativeReturnSet;
    }

    public boolean isOpponentReturnSet() {
        return opponentReturnSet;
    }

    public void setOpponentReturnSet(boolean opponentReturnSet) {
        this.opponentReturnSet = opponentReturnSet;
    }

    
    
}
