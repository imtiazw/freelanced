/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import GameClasses.LastMove.EnPassantGiven;
import java.util.ArrayList;

/**
 *
 * @author ALI
 */
public class Pawn extends Piece{
    public Pawn() {
        super();
    }

    public Pawn(Pawn pawn) {
        super(pawn);
    }

    public Pawn(String name, Position currentPosition, String color, String team, ArrayList<Position> nextPossiblePosition) {
        super(name, currentPosition, color, team, nextPossiblePosition);
    }
    
    @Override
    public void calculateNextPossiblePositions(ChessPlayBoard chessPlayBoard){
        
        if(this.getName().equals("dead") == false){            //checks if piece is not already dead
            ChessBoardBox[] chessBoardBox = chessPlayBoard.getChessBoardBox();
            String column = this.getCurrentPosition().getColumn();
            String row = this.getCurrentPosition().getRow();
            Position temp;
            this.emptyNextPossiblePositions();
            switch (this.getTeam()) {
                case "host":
                    if(this.getCurrentPosition().getRow().equals("2")){
                        if(chessBoardBox[getChessBoardIndex("2", column) + 16].isPieceSitStatus() == false){
                            if(chessBoardBox[getChessBoardIndex("2", column) + 8].isPieceSitStatus() == false){
                                this.addNextPossiblePosition(calculatePosition(2, -1, 0, -1));
                            }
                        }
                    }   
                    if((temp = calculatePosition(1, -1, 0, -1)) != null){
                        if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].isPieceSitStatus() == false){
                            this.addNextPossiblePosition(temp);
                        }
                    }if((temp = calculatePosition(1, 1, 0, 0)) != null){
                        if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].isPieceSitStatus() == true){
                            if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].getPiece().getTeam().equals("player") == true){
                                this.addNextPossiblePosition(temp);
                            }
                        }
                    }if((temp = calculatePosition(1, 0, 0, 1)) != null){
                        if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].isPieceSitStatus() == true){
                            if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].getPiece().getTeam().equals("player") == true){
                                this.addNextPossiblePosition(temp);
                            }
                        }
                    }
                    break;
                case "player":
                    if(this.getCurrentPosition().getRow().equals("7")){
                        if(chessBoardBox[getChessBoardIndex("7", column) - 16].isPieceSitStatus() == false){
                            if(chessBoardBox[getChessBoardIndex("7", column) - 8].isPieceSitStatus() == false){
                                this.addNextPossiblePosition(calculatePosition(0, -1, 2, -1));
                            }
                        }
                    }   
                    if((temp = calculatePosition(0, -1, 1, -1)) != null){
                        if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].isPieceSitStatus() == false){
                            this.addNextPossiblePosition(temp);
                        }
                    }   
                    if((temp = calculatePosition(0, 1, 1, 0)) != null){
                        if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].isPieceSitStatus() == true){
                            if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].getPiece().getTeam().equals("host") == true){
                                this.addNextPossiblePosition(temp);
                            }
                        }
                    }   
                    if((temp = calculatePosition(0, 0, 1, 1)) != null){
                        if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].isPieceSitStatus() == true){
                            if(chessBoardBox[getChessBoardIndex(temp.getRow(), temp.getColumn())].getPiece().getTeam().equals("host") == true){
                                this.addNextPossiblePosition(temp);
                            }
                        }
                    }
                    break;
            }

            if(chessPlayBoard.getLastMove() != null){
                if(chessPlayBoard.getLastMove().getSpecialMove().iseNPassantGiven() == true){
                    EnPassantGiven enPassantGiven = chessPlayBoard.getLastMove().getSpecialMove().getEnPassantGiven();
                    if(enPassantGiven.isLeftOpponentEnPassantPiece() == true){
                        if(enPassantGiven.getOpponentLeftEnPassantPiece().getName().equals(this.getName()) == true){
                            appnedEnPassantPossiblePosition(enPassantGiven.getOpponentLeftEnPassantPiece().getCurrentPosition(), chessPlayBoard , this.getTeam(), false, true);
                        }
                    }
                    if(enPassantGiven.isRightOpponentEnPassantPiece() == true){
                        if(enPassantGiven.getOpponentRightEnPassantPiece().getName().equals(this.getName()) == true){
                            appnedEnPassantPossiblePosition(enPassantGiven.getOpponentRightEnPassantPiece().getCurrentPosition(), chessPlayBoard , this.getTeam(), true, false);
                        }
                    }
                }
            }
        }
    }
    
    public void appnedEnPassantPossiblePosition(Position nativePieceCurrentPosition, ChessPlayBoard chessPlayBoard ,String team, boolean right, boolean left) {
        int chessBoardIndex = chessPlayBoard.getChessBoardIndex(nativePieceCurrentPosition.toString());
        Piece leftPiece = null;
        Piece rightPiece = null;
        Pawn leftPawn = null;
        Pawn rightPawn = null;
        if(team.equals("host") == true){
            if((chessBoardIndex + 1) % 8 != 1){
                if(left == true){
                    if(chessPlayBoard.getChessBoardBox()[chessBoardIndex-1].isPieceSitStatus() == true){
                        leftPiece = chessPlayBoard.getChessBoardBox()[chessBoardIndex-1].getPiece();
                        if(leftPiece.getTeam().equals("host") == false){
                            if(isPawn(leftPiece.getName()) == true){
                                this.getNextPossiblePosition().add(chessPlayBoard.getChessBoardBox()[chessBoardIndex +7].getLocation());
                            }
                        }
                    }
                }
            }
            if((chessBoardIndex + 1) % 8 != 0){
                if(right == true){
                    if(chessPlayBoard.getChessBoardBox()[chessBoardIndex+1].isPieceSitStatus() == true){
                        rightPiece = chessPlayBoard.getChessBoardBox()[chessBoardIndex+1].getPiece();
                        if(rightPiece.getTeam().equals("host") == false){
                            if(isPawn(rightPiece.getName()) == true){
                                this.getNextPossiblePosition().add(chessPlayBoard.getChessBoardBox()[chessBoardIndex +9].getLocation());
                            }
                        }
                    }
                }
            }
            
        }else if(team.equals("player") == true){
            if((chessBoardIndex + 1) % 8 != 1){
                if(right == true){
                    if(chessPlayBoard.getChessBoardBox()[chessBoardIndex-1].isPieceSitStatus() == true){
                        rightPiece = chessPlayBoard.getChessBoardBox()[chessBoardIndex-1].getPiece();
                        if(rightPiece.getTeam().equals("player") == false){
                            if(isPawn(rightPiece.getName()) == true){
                                this.getNextPossiblePosition().add(chessPlayBoard.getChessBoardBox()[chessBoardIndex -9].getLocation());
                            }
                        }
                    }
                }
            }
            if((chessBoardIndex + 1) % 8 != 0){
                if(left == true){
                    if(chessPlayBoard.getChessBoardBox()[chessBoardIndex+1].isPieceSitStatus() == true){
                        leftPiece = chessPlayBoard.getChessBoardBox()[chessBoardIndex+1].getPiece();
                        if(leftPiece.getTeam().equals("player") == false){
                            if(isPawn(leftPiece.getName()) == true){
                                this.getNextPossiblePosition().add(chessPlayBoard.getChessBoardBox()[chessBoardIndex -7].getLocation());
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean isPawn(String name) {
        boolean result = false;
        if(name.charAt(1) == 'P'){
            result = true;
        }
        
        return result;
    }
}
