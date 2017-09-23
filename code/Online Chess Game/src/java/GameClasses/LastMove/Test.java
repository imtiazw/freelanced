/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.LastMove;

import GameClasses.OnlineChessGame.Position;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 *
 * @author ALI
 */
public class Test {
    public static void main(String[] args){
        LastMove lastMove = new LastMove();
        Gson gson = new Gson(); 
        String json = gson.toJson(lastMove);
        System.out.print(json);
        System.out.print("\n\n\n");
        LastMove reverseLastMove = gson.fromJson(json, LastMove.class);
        ArrayList<Position> al = new ArrayList<Position>();
        al.add(new Position("1", "a"));
        reverseLastMove.getSimpleMove().getNativeNextPossiblePositions().put("test", al);
        System.out.print(reverseLastMove.getCheck().getOpponentKingNextPossiblePosition().toString());
        
        System.out.print(gson.toJson(reverseLastMove, LastMove.class));
        
    }
}
