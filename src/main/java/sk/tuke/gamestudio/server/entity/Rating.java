package sk.tuke.gamestudio.server.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * POJO class for rating
 */
@Entity

@NamedQueries({
        @NamedQuery(name = "Rating.getRatingByPlayer",
                query = "SELECT rating FROM Rating rating WHERE rating.game=:game AND rating.player=:player"),
        @NamedQuery(name = "Rating.getAverageRating",
                query = "SELECT rating FROM Rating rating WHERE rating.game=:game")})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String player;
    private String game;
    private int rating;
    private Date ratedon;

    public Rating() {

    }

    public Rating(String player, int rating, Date ratedon) {
        this.player = player;
        this.game = "spider-solitaire";
        this.rating = rating;
        this.ratedon = ratedon;
    }

    /**
     * @return rating object in string
     */

    @Override
    public String toString() {
        return "Rating{" + "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", rating=" + rating +
                ", ratedon=" + ratedon +
                '}';
    }

    // getters and setters

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedon() {
        return ratedon;
    }

    public void setRatedon(Date ratedon) {
        this.ratedon = ratedon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
