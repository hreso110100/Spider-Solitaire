package sk.tuke.gamestudio.spidersolitaire.server.entity;

import java.util.Date;

/**
 * POJO class for comment
 */

public class Comment {
    private String player;
    private String game;
    private String comment;
    private Date commentedOn;

    public Comment(String player, String comment, Date commentedOn) {
        this.player = player;
        this.game = "spider-solitaire";
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    /**
     * @return object in String
     */

    @Override
    public String toString() {
        return "Comment{" + "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", comment='" + comment + '\'' +
                ", commentedOn=" + commentedOn +
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }
}
