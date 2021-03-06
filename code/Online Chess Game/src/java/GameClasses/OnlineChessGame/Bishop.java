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
public class Bishop extends Piece{
    public Bishop() {
        super();
    }

    public Bishop(Bishop bishop) {
        super(bishop);
    }

    public Bishop(String name, Position currentPosition, String color, String team, ArrayList<Position> nextPossiblePosition) {
        super(name, currentPosition, color, team, nextPossiblePosition);
    }
    
    /**
     *
     * @param chessPlayBoard
     */
    @Override
    public void calculateNextPossiblePositions(ChessPlayBoard chessPlayBoard){
        if(this.getName().equals("dead") == false){            //checks if piece is not already dead
            ChessBoardBox[] chessBoardBox = chessPlayBoard.getChessBoardBox();
            Position temp;

            this.emptyNextPossiblePositions();
            //going right up 
            int i=1;
            while((temp = calculatePosition(i, i, 0, 0)) != null){
                if(this.addNewPosition(chessBoardBox, temp) == true){
                    break;
                }
                i++;
            }
            i=1;
            while((temp = calculatePosition(i, 0, 0, i)) != null){
                if(this.addNewPosition(chessBoardBox, temp) == true){
                    break;
                }
                i++;
            }
            i=1;
            while((temp = calculatePosition(0, i, i, 0)) != null){
                if(this.addNewPosition(chessBoardBox, temp) == true){
                    break;
                }
                i++;
            }
            i=1;
            while((temp = calculatePosition(0, 0, i, i)) != null){
                if(this.addNewPosition(chessBoardBox, temp) == true){
                    break;
                }
                i++;
            }
        }
    }
    
    private boolean addNewPosition(ChessBoardBox[] chessBoardBox, Position position){
        boolean exit = false;
        if(chessBoardBox[getChessBoardIndex(position.getRow(), position.getColumn())].isPieceSitStatus() == true){
            if(chessBoardBox[getChessBoardIndex(position.getRow(), position.getColumn())].getPiece().getTeam().equals(this.getTeam()) == false){
                this.addNextPossiblePosition(position);
                exit = true;
            }else{
                exit = true;
            }
        }else{
            this.addNextPossiblePosition(position);
        }
        
        return exit;
    }
    
}
