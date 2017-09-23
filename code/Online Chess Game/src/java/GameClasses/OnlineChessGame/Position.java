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
public class Position {
    String row;
    String column;

    public Position() {
        this.row = "";
        this.column = "";
    }
    
    public Position(String position){
        this.column = position.charAt(0) + "";
        this.row = position.charAt(1) + "";
    }
    public Position(Position position){
        row = position.row;
        column = position.column;
    }
    public Position(String row, String column) {
        this.row = row;
        this.column = column;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
    
    public void setPosition(Position position){
        this.column = position.getColumn();
        this.row = position.getRow();
    }
    
    @Override
    public String toString(){
        String position = null;
        
        position = column+row;
        
        return position;
    }
}
