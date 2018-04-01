package sk.tuke.gamestudio.game.spidersolitaire.hresko.card;

/**
 * POJO class defining Card
 */

public class Card {

    private int rank;
    private boolean flipped;

    Card(int rank, boolean flipped) {
        this.rank = rank;
        this.flipped = flipped;
    }

    // getters and setters

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}


