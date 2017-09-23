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
public class InvitationListPlayerListWindow {
    private Competetor[] competetor;
    private static final int noOfCompetetors = 5;

    public InvitationListPlayerListWindow() {
        this.competetor = new Competetor[noOfCompetetors];
        for(int i=0; i<noOfCompetetors; i++){
            this.competetor[i] = new Competetor();
        }
    }

    public InvitationListPlayerListWindow(Competetor[] competetor) {
        for(int i=0; i<noOfCompetetors; i++){
            this.competetor[i].setCompetetor(competetor[i]);
        }
    }
    
    public InvitationListPlayerListWindow(InvitationListPlayerListWindow invitationListPlayerListWindow){
        for(int i=0; i<noOfCompetetors; i++){
            this.competetor[i].setCompetetor(invitationListPlayerListWindow.getCompetetor()[i]);
        }
    }

    public Competetor[] getCompetetor() {
        return competetor;
    }

    public void setCompetetor(Competetor[] competetor) {
        for(int i=0; i<noOfCompetetors; i++){
            this.competetor[i].setCompetetor(competetor[i]);
        }
    }
    
    
    public void addCompetetors(ArrayList<Player> competetors){
        for(int i=0; i<competetors.size(); i++){
            this.competetor[i].setAddress(competetors.get(i).getAddress());
            this.competetor[i].setContact(competetors.get(i).getContact());
            this.competetor[i].setDateOfBirth(competetors.get(i).getDateOfBirth());
            this.competetor[i].setFirstName(competetors.get(i).getFirstName());
            this.competetor[i].setGender(competetors.get(i).getGender());
            this.competetor[i].setLastName(competetors.get(i).getLastName());
            this.competetor[i].setPrimaryEmail(competetors.get(i).getPrimaryEmail());
            this.competetor[i].setUserId(competetors.get(i).getUserId());
            this.competetor[i].setRating(competetors.get(i).getRating());
            this.competetor[i].setPlayerId(competetors.get(i).getUserId());
            this.competetor[i].setPlaying(false);
        } 
    }
}
