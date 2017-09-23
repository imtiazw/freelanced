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
public class Rock extends Piece{
    private transient boolean castleValid;
    public Rock() {
        super();
        this.castleValid = true;
    }

    public Rock(Rock rock) {
        super(rock);
        this.castleValid = rock.castleValid;
    }

    public Rock(String name, Position currentPosition, String color, String team, ArrayList<Position> nextPossiblePosition) {
        super(name, currentPosition, color, team, nextPossiblePosition);
        this.castleValid = true;
    }

    public boolean isCastleValid() {
        return castleValid;
    }

    public void setCastleValid(boolean castleValid) {
        this.castleValid = castleValid;
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
            while((temp = calculatePosition(i, -1, 0, -1)) != null){
                if(this.addNewPosition(chessBoardBox, temp) == true){
                    break;
                }
                i++;
            }
            i=1;
            while((temp = calculatePosition(-1, i, -1, 0)) != null){
                if(this.addNewPosition(chessBoardBox, temp) == true){
                    break;
                }
                i++;
            }
            i=1;
            while((temp = calculatePosition(0, -1, i, -1)) != null){
                if(this.addNewPosition(chessBoardBox, temp) == true){
                    break;
                }
                i++;
            }
            i=1;
            while((temp = calculatePosition(-1, 0, -1, i)) != null){
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
