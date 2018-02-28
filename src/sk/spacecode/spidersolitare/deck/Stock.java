package sk.spacecode.spidersolitare.deck;

import sk.spacecode.spidersolitare.card.Card;

public class Stock {
    private Card[] stock;
    private static final int STARTING_INDEX = 54;

    Stock() {
        stock = new Card[50];
    }

    public Card[] getStock() {
        return stock;
    }

    public void fillStock(Card[] cards) {
        int index = STARTING_INDEX;
        int stockIndex = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                stock[stockIndex] = cards[index];
                index++;
                stockIndex++;
            }
        }
    }
}
