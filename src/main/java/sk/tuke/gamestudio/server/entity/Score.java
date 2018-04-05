package sk.tuke.gamestudio.server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * POJO class for score
 */

@Entity
@NamedQuery(name = "Score.getBestScores",
        query = "SELECT score FROM Score score WHERE score.game=:game ORDER BY score.points DESC")
public class Score implements Comparable<Score>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String game;
    private String player;
    private int points;
    private Date playedOn;

    public Score() {

    }

    public Score(String player, int points, Date playedOn) {
        this.game = "spider-solitaire";
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }

    /**
     * @return score in string
     */

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedOn +
                '}';
    }

    /**
     * @param o represents score
     * @return higher score
     */

    @Override
    public int compareTo(Score o) {
        if (o == null) return -1;
        return this.getPoints() - o.getPoints();
    }

    // getters and setters

    public String getGame() {
        return game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
