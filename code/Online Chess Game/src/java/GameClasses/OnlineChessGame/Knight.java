/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import java.util.ArrayList;

/**
 *
 * @author ALI
 */
public class Knight extends Piece{
    public Knight() {
        super();
    }

    public Knight(Knight knight) {
        super(knight);
    }

    public Knight(String name, Position currentPosition, String color, String team, ArrayList<Position> nextPossiblePosition) {
        super(name, currentPosition, color, team, nextPossiblePosition);
    }
    
    @Override
    public void calculateNextPossiblePositions(ChessPlayBoard chessPlayBoard){
        if(this.getName().equals("dead") == false){            //checks if piece is not already dead
            ChessBoardBox[] chessBoardBox = chessPlayBoard.getChessBoardBox();
            Position temp;

            this.emptyNextPossiblePositions();

            if((temp = calculatePosition(2, 1, 0, 0)) != null){
                addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(1, 2, 0, 0)) != null){
                addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(0, 2, 1, 0)) != null){
                addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(0, 1, 2, 0)) != null){
                addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(0, 0, 2, 1)) != null){
                addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(0, 0, 1, 2)) != null){
                addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(1, 0, 0, 2)) != null){
                addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(2, 0, 0, 1)) != null){
                addNewPosition(chessBoardBox, temp);
            }
        }
    }
    
    private void addNewPosition(ChessBoardBox[] chessBoardBox, Position position){
        if(chessBoardBox[getChessBoardIndex(position.getRow(), position.getColumn())].isPieceSitStatus() == true){
                if(chessBoardBox[getChessBoardIndex(position.getRow(), position.getColumn())].getPiece().getTeam().equals(this.getTeam()) == false){
                    this.addNextPossiblePosition(position);
                }
            }else{
                this.addNextPossiblePosition(position);
            }
    }
}
