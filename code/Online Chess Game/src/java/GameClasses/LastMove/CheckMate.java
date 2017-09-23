/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.LastMove;

import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.Person;

/**
 *
 * @author ALI
 */
public class CheckMate {
    private Person checkMateBy;
    private Person checkMateTo;

    public CheckMate() {
        this.checkMateBy = new Person();
        this.checkMateTo = new Person();
    }

    public CheckMate(Person checkMateBy, Person checkMateTo) {
        this.checkMateBy = checkMateBy;
        this.checkMateTo = checkMateTo;
    }

    protected void fromShallowStructureToDeepStrcture(ChessPlayBoard chessPlayBoard){
        if(this.checkMateBy.getUserId().equals("") == false){
            this.checkMateBy = chessPlayBoard.getChessBoardPlayerListWindow().getPerson(this.checkMateBy.getUserId());
        }
        if(this.checkMateTo.getUserId().equals("") == false){
            this.checkMateTo = chessPlayBoard.getChessBoardPlayerListWindow().getPerson(this.checkMateTo.getUserId());
        }
    }
    
    public Person getCheckMateBy() {
        return checkMateBy;
    }

    public void setCheckMateBy(Person checkMateBy) {
        this.checkMateBy = checkMateBy;
    }

    public Person getCheckMateTo() {
        return checkMateTo;
    }

    public void setCheckMateTo(Person checkMateTo) {
        this.checkMateTo = checkMateTo;
    }
}
