package sk.tuke.gamestudio.spidersolitaire.game.card;

/**
 * This class represents package of cards
 */

public class Pack {

    private Card[] cardPack;

    public Pack() {
        cardPack = new Card[104];
    }

    /**
     * This method assigns rank to each card
     */

    public void createCardPack() {
        int index = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 13; j++) {
                cardPack[index++] = new Card(j, false);
            }
        }
    }

    // getters and setters

    public Card[] getCardPack() {
        return cardPack;
    }
}
