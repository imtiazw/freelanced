/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.OnlineChessGame;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ALI
 */
public class Piece {
    private String name;
    private transient Position currentPosition;
    private transient String color;
    private transient String team;
    private transient ArrayList<Position> nextPossiblePosition;

    public Piece() {
        this.name = "";
        this.currentPosition = new Position();
        this.color = "";
        this.team = "";
        this.nextPossiblePosition = new ArrayList<Position>();
    }

    Piece(Piece piece){
        name = piece.name;
        currentPosition = new Position(piece.currentPosition);
        color = piece.color;
        team = piece.team;
        for(int i=0; i<piece.nextPossiblePosition.size(); i++){
            nextPossiblePosition.add(piece.nextPossiblePosition.get(i));
        }
        
    }
    
    public Piece(String name, Position currentPosition, String color, String team, ArrayList<Position> nextPossiblePosition) {
        this.name = name;
        this.currentPosition = new Position(currentPosition.getRow(), currentPosition.getColumn());
        this.color = color;
        this.team = team;
        this.nextPossiblePosition = new ArrayList<Position>();
        for(int i=0; i<nextPossiblePosition.size(); i++){
            this.nextPossiblePosition.add(nextPossiblePosition.get(i));
        }
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public ArrayList<Position> getNextPossiblePosition() {
        return nextPossiblePosition;
    }

    public ArrayList<String> getNextPossiblePositionsAsStrings(){
        ArrayList<String> nextPossiblePositionsAsStrings = new ArrayList<String>();
        for(int i=0; i<this.nextPossiblePosition.size(); i++){
            nextPossiblePositionsAsStrings.add(this.nextPossiblePosition.get(i).getColumn()+ this.nextPossiblePosition.get(i).getRow());
        }
        
        return nextPossiblePositionsAsStrings;
    }
    
    public void setNextPossiblePosition(ArrayList<Position> nextPossiblePosition) {
        for(int i=0; i<nextPossiblePosition.size(); i++){
            this.nextPossiblePosition.add(nextPossiblePosition.get(i));
        }
    }
    
    public boolean isValidMove(Position location2){
        boolean result = false;
        result = isValidMove(location2.toString());
        return result;
    }
    
    public boolean isValidMove(String location2){
        boolean result = false;
        Iterator nextPossiblePositions = this.nextPossiblePosition.iterator();
        while(nextPossiblePositions.hasNext() == true){
            if(nextPossiblePositions.next().toString().equals(location2) == true){
                result = true;
            }
        }
        return result;
    }
    
    public void setCurrentPosition(Position location2){
        this.currentPosition.column = location2.column;
        this.currentPosition.row = location2.row;
    }
    
    public void addNextPossiblePosition(Position position){
        this.nextPossiblePosition.add(position);
    }

    //this function tells us weather this asked position possible or not? If yes then returns this position else returns null
    public Position calculatePosition(int up, int right, int down, int left){
        String columns[] = {"","a","b","c","d","e","f","g","h"};
        String row = this.getCurrentPosition().getRow();
        String column = this.getCurrentPosition().getColumn();
        Position position = new Position(row, column);
        int rowsOrColumnsRecalculated = 0;
        int newRight = -1;
        if(left == 0 && isValidPosition(newRight = (columnIndex(column)+right), Integer.parseInt(row)) == true){
            position.setColumn(columns[newRight]);
            rowsOrColumnsRecalculated++;
        }
        int newLeft = -1;
        if(right == 0 && isValidPosition(newLeft = (columnIndex(column)-left), Integer.parseInt(row)) == true){
            position.setColumn(columns[newLeft]);
            rowsOrColumnsRecalculated++;
        }
        int newUp = -1;
        if(down == 0 && isValidPosition(columnIndex(column), (newUp = (Integer.parseInt(row))+up)) == true){
            position.setRow(newUp+"");
            rowsOrColumnsRecalculated++;
        }
        int newDown = -1;       //default value is -1 so that if anything goes wrong an exception is generated
        if(up == 0 && isValidPosition(columnIndex(column), (newDown = (Integer.parseInt(row))-down)) == true){
            position.setRow(newDown+"");
            rowsOrColumnsRecalculated++;
        }
        if(rowsOrColumnsRecalculated != numberOfInputs(up, right, down, left)){
            position = null;
        }
        
        return position;
    }
    
    public int columnIndex(String column){
        int index = 0;
        String columns[] = {"","a","b","c","d","e","f","g","h"};
        for(int i=1; i<columns.length; i++){
            if(columns[i].equals(column)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public boolean isValidPosition(int column, int row){
        boolean result = false;
        String columns[] = {"","a","b","c","d","e","f","g","h"};
        int totalRows = 8;
        if((column < columns.length && column >= 1) && (row >=1 && row <= totalRows)){
            result = true;
        }
        
        return result;
    }
    
    public int getChessBoardIndex(String row, String column){
        return (((8*(Integer.parseInt(row)-1))+columnIndex(column))-1);
    }
    
    public void emptyNextPossiblePositions(){
        if(this.nextPossiblePosition.isEmpty() == false){
            this.nextPossiblePosition.clear();
        }
    }
     
    public void calculateNextPossiblePositions(ChessPlayBoard chessPlayBoard){}

    void deadPiece() {
        this.currentPosition.setColumn("");
        this.currentPosition.setRow("");
        this.name = "dead";
        this.nextPossiblePosition.clear();
    }

    private int numberOfInputs(int up, int right, int down, int left) {
        int numberOfInputs = 0;
        if(up != 0 && up != -1){
            numberOfInputs++;
        }
        if(right != 0 && right != -1){
            numberOfInputs++;
        }
        if(down != 0 && down != -1){
            numberOfInputs++;
        }
        if(left != 0 && left != -1){
            numberOfInputs++;
        }
        
        return numberOfInputs;
    }
}
