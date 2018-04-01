package sk.tuke.gamestudio.game.spidersolitaire.hresko.deck;

import sk.tuke.gamestudio.game.spidersolitaire.hresko.card.Card;

/**
 * This class represents unused cards
 */

public class Stock {

    private Card[] stock;
    private static final int STARTING_INDEX = 54;

    Stock() {
        stock = new Card[50];
    }

    /**
     * @param cards represents remaining cards from package
     */

    public void fillStock(Card[] cards) {
        int index = STARTING_INDEX;

        for (int i = 0; i < stock.length; i++) {
            stock[i] = cards[index];
            index++;
        }
    }

    // getters and setters

    public Card[] getStock() {
        return stock;
    }
}
