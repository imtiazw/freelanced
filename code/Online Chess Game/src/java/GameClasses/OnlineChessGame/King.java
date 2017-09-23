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
public class King extends Piece{
    private transient boolean castleValid; 
    public King() {
        super();
        this.castleValid = true;
    }

    public King(King king) {
        super(king);
        this.castleValid = king.castleValid;
    }

    public King(String name, Position currentPosition, String color, String team, ArrayList<Position> nextPossiblePosition) {
        super(name, currentPosition, color, team, nextPossiblePosition);
        this.castleValid = true;
    }

    public boolean isCastleValid() {
        return castleValid;
    }

    public void setCastleValid(boolean castleValid) {
        this.castleValid = castleValid;
    }
    
    @Override
    public void calculateNextPossiblePositions(ChessPlayBoard chessPlayBoard){
        if(this.getName().equals("dead") == false){            //checks if piece is not already dead
            ChessBoardBox[] chessBoardBox = chessPlayBoard.getChessBoardBox();
            Piece[] piece = chessPlayBoard.getPiece();

            this.emptyNextPossiblePositions();
            switch (this.getTeam()) {
                case "host":
                    if(this.castleValid == true){
                        if(this.getCurrentPosition().getColumn().equals("e") && this.getCurrentPosition().getRow().equals("1")){
                            if(((Rock)chessPlayBoard.getPiece("BRR")).isCastleValid() == true){
                                if(chessBoardBox[getChessBoardIndex("1", "h")].isPieceSitStatus() == true){
                                    if(chessBoardBox[getChessBoardIndex("1", "h")].getPiece().getName().equals("BRR")){
                                        if(chessBoardBox[getChessBoardIndex("1", "g")].isPieceSitStatus() == false && chessBoardBox[getChessBoardIndex("1", "f")].isPieceSitStatus() == false){
                                            this.addNextPossiblePosition(calculatePosition(-1, 2, -1, 0));
                                        }
                                    }
                                }
                            }else if(((Rock)chessPlayBoard.getPiece("BLR")).isCastleValid() == true){
                                if(chessBoardBox[getChessBoardIndex("1", "a")].isPieceSitStatus() == true){
                                    if(chessBoardBox[getChessBoardIndex("1", "a")].getPiece().getName().equals("BLR")){
                                        if(chessBoardBox[getChessBoardIndex("1", "b")].isPieceSitStatus() == false && chessBoardBox[getChessBoardIndex("1", "c")].isPieceSitStatus() == false && chessBoardBox[getChessBoardIndex("1", "d")].isPieceSitStatus() == false){
                                            this.addNextPossiblePosition(calculatePosition(-1, 0, -1, 2));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "player":
                    if(this.castleValid == true){
                        if(this.getCurrentPosition().getColumn().equals("e") && this.getCurrentPosition().getRow().equals("8")){
                            if(((Rock)chessPlayBoard.getPiece("PLR")).isCastleValid() == true){
                                if(chessBoardBox[getChessBoardIndex("8", "h")].isPieceSitStatus() == true){
                                    if(chessBoardBox[getChessBoardIndex("8", "h")].getPiece().getName().equals("PLR")){
                                        if(chessBoardBox[getChessBoardIndex("8", "g")].isPieceSitStatus() == false && chessBoardBox[getChessBoardIndex("8", "f")].isPieceSitStatus() == false){
                                            this.addNextPossiblePosition(calculatePosition(-1, 2, -1, 0));
                                        }
                                    }
                                }
                            }else if(((Rock)chessPlayBoard.getPiece("PRR")).isCastleValid() == true){
                                if(chessBoardBox[getChessBoardIndex("8", "a")].isPieceSitStatus() == true){
                                    if(chessBoardBox[getChessBoardIndex("8", "a")].getPiece().getName().equals("PRR")){
                                        if(chessBoardBox[getChessBoardIndex("8", "b")].isPieceSitStatus() == false && chessBoardBox[getChessBoardIndex("8", "c")].isPieceSitStatus() == false && chessBoardBox[getChessBoardIndex("8", "d")].isPieceSitStatus() == false){
                                            this.addNextPossiblePosition(calculatePosition(-1, 0, -1, 2));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
            Position temp;

            if((temp = calculatePosition(1, -1, 0, -1)) != null){
                this.addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(1, 1, 0, 0)) != null){
                this.addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(-1, 1, -1, 0)) != null){
                this.addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(0, 1, 1, 0)) != null){
                this.addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(0, -1, 1, -1)) != null){
                this.addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(0, 0, 1, 1)) != null){
                this.addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(-1, 0, -1, 1)) != null){
                this.addNewPosition(chessBoardBox, temp);
            }
            if((temp = calculatePosition(1, 0, 0, 1)) != null){
                this.addNewPosition(chessBoardBox, temp);
            }

            filterKingPositions(chessPlayBoard);
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

    private void filterKingPositions(ChessPlayBoard chessPlayBoard) {
        //this method will filter the kings positions in the next possible positions list.
        //i.e. those opposition pieces that can take king with this next possible position. Hence this next possition position will be eleminated.
        int startCounter = 0;
        int endCounter = 0;
        if(super.getTeam().equals("host") == true){
            startCounter = 16;
            endCounter = 32;
        }else if(super.getTeam().equals("player") == true){
            startCounter = 0;
            endCounter = 16;
        }
        
        deleteMatch(startCounter, endCounter, chessPlayBoard);
         
    }

    private void deleteMatch(int startCounter, int endCounter, ChessPlayBoard chessPlayBoard) {
        ChessBoardBox previousPositionChessBoardBox = null;
        ChessBoardBox currentPositionChessBoardBox = null;
        Piece[] pieces = chessPlayBoard.getPiece();
        ArrayList<String> opponentAllPossiblePositions = new ArrayList<String>();
        
        
        //this function will delete any match if found between two next possible positions
        
        
            for(int j=0; j<this.getNextPossiblePosition().size(); j++){
                
                String opponentKingName = opponentKing(this.getTeam());
                opponentAllPossiblePositions.clear();
                currentPositionChessBoardBox = chessPlayBoard.getChessBoardBoxAt(this.getNextPossiblePosition().get(j).toString());
                previousPositionChessBoardBox = chessPlayBoard.getChessBoardBoxAt(this.getCurrentPosition().toString());
                if(currentPositionChessBoardBox.isPieceSitStatus() == false){
                    
                    currentPositionChessBoardBox.setPieceSitStatus(true);
                    currentPositionChessBoardBox.setPiece(this);
                    previousPositionChessBoardBox.setPieceSitStatus(false);
                    previousPositionChessBoardBox.setPiece(null);
                    
                    for(int i=startCounter; i<endCounter; i++){
                        if(pieces[i].getName().equals(opponentKingName) == false){
                            pieces[i].calculateNextPossiblePositions(chessPlayBoard);
                            opponentAllPossiblePositions.addAll(pieces[i].getNextPossiblePositionsAsStrings());
                        }
                    }
                    
                    previousPositionChessBoardBox.setPieceSitStatus(true);
                    previousPositionChessBoardBox.setPiece(this);
                    currentPositionChessBoardBox.setPieceSitStatus(false);
                    currentPositionChessBoardBox.setPiece(null);
                    
                    if(opponentAllPossiblePositions.contains(this.getNextPossiblePosition().get(j).toString()) == true){
                        this.getNextPossiblePosition().remove(j);
                        j--;
                    }
                    
                    for(int i=startCounter; i<endCounter; i++){
                        if(pieces[i].getName().equals(opponentKingName) == false){
                            pieces[i].calculateNextPossiblePositions(chessPlayBoard);
                        }
                    }
                    
                }else{
                    Piece pieceTakenByKing = currentPositionChessBoardBox.getPiece();
                    ArrayList<String> opponentNextPossiblePositions = new ArrayList<String>();
                    
                    currentPositionChessBoardBox.setPiece(this);
                    previousPositionChessBoardBox.setPiece(null);
                    previousPositionChessBoardBox.setPieceSitStatus(false);
                    for(int k=startCounter; k<endCounter; k++){
                        if(pieces[k].getName().equals(pieceTakenByKing.getName()) == false){
                            if(pieces[k].getName().equals(opponentKingName) == false){
                                pieces[k].calculateNextPossiblePositions(chessPlayBoard);
                                opponentNextPossiblePositions.addAll(pieces[k].getNextPossiblePositionsAsStrings());
                            }
                        }
                    }
                    
                    currentPositionChessBoardBox.setPiece(pieceTakenByKing);
                    previousPositionChessBoardBox.setPiece(this);
                    previousPositionChessBoardBox.setPieceSitStatus(true);
                    
                    if(opponentNextPossiblePositions.contains(this.getNextPossiblePosition().get(j).toString()) == true){
                        this.getNextPossiblePosition().remove(j);
                        j--;
                    }
                    
                    for(int k=startCounter; k<endCounter; k++){
                        if(pieces[k].getName().equals(pieceTakenByKing.getName()) == false){
                            if(pieces[k].getName().equals(opponentKingName) == false){
                                pieces[k].calculateNextPossiblePositions(chessPlayBoard);
                            }
                        }
                    }
                }
            }
    }

    private String opponentKing(String team) {
        String kingName = "";
        if(team.equals("host") == true){
            kingName = "PK";
        }else if(team.equals("player") == true){
            kingName = "BK";
        }
        
        return kingName;
    }
}
