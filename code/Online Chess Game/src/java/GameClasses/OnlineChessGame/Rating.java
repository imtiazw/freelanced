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
public class Rating {
    private int rating;

    public Rating() {
        this.rating = 0;
    }

    public Rating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    void setRating(Rating rating) {
        this.rating = rating.rating;
    }
}
