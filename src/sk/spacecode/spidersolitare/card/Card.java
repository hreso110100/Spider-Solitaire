package sk.spacecode.spidersolitare.card;

public class Card {

    private int rank;
    private String color;
    private String suit;
    private boolean flipped;

    Card(int rank, String color, String suit, boolean flipped) {
        this.rank = rank;
        this.color = color;
        this.suit = suit;
        this.flipped = flipped;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public int getRank() {
        return rank;
    }

    public String getColor() {
        return color;
    }

    public String getSuit() {
        return suit;
    }
}
