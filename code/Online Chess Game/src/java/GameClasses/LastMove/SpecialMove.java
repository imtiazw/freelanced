/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameClasses.LastMove;

/**
 *
 * @author ALI
 */
public class SpecialMove extends Move{
    private boolean eNPassantPlayed;
    private boolean cAstle;
    private boolean pAwnUpgrade;
    private boolean eNPassantGiven;
    private PawnUpgrade pawnUpgrade;
    private Castle castle;
    private EnPassantPlayed enPassantPlayed;
    private EnPassantGiven enPassantGiven;

    public SpecialMove() {
        super();
        this.eNPassantPlayed = false;
        this.cAstle = false;
        this.pAwnUpgrade = false;
        this.eNPassantGiven = false;
        this.pawnUpgrade = new PawnUpgrade();
        this.castle = new Castle();
        this.enPassantPlayed = new EnPassantPlayed();
        this.enPassantGiven = new EnPassantGiven();
    }

    public boolean iseNPassantPlayed() {
        return eNPassantPlayed;
    }

    public void seteNPassantPlayed(boolean eNPassantPlayed) {
        this.eNPassantPlayed = eNPassantPlayed;
    }

    public boolean iscAstle() {
        return cAstle;
    }

    public void setcAstle(boolean cAstle) {
        this.cAstle = cAstle;
    }

    public boolean ispAwnUpgrade() {
        return pAwnUpgrade;
    }

    public void setpAwnUpgrade(boolean pAwnUpgrade) {
        this.pAwnUpgrade = pAwnUpgrade;
    }

    public boolean iseNPassantGiven() {
        return eNPassantGiven;
    }

    public void seteNPassantGiven(boolean eNPassantGiven) {
        this.eNPassantGiven = eNPassantGiven;
    }

    public PawnUpgrade getPawnUpgrade() {
        return pawnUpgrade;
    }

    public void setPawnUpgrade(PawnUpgrade pawnUpgrade) {
        this.pawnUpgrade = pawnUpgrade;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public EnPassantPlayed getEnPassantPlayed() {
        return enPassantPlayed;
    }

    public void setEnPassantPlayed(EnPassantPlayed enPassantPlayed) {
        this.enPassantPlayed = enPassantPlayed;
    }

    public EnPassantGiven getEnPassantGiven() {
        return enPassantGiven;
    }

    public void setEnPassantGiven(EnPassantGiven enPassantGiven) {
        this.enPassantGiven = enPassantGiven;
    }
    
    
}
