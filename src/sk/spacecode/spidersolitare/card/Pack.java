package sk.spacecode.spidersolitare.card;

public class Pack {

    private Card[] cardPack;

    public Pack() {
        cardPack = new Card[104];
    }

    public Card[] getCardPack() {
        return cardPack;
    }

    public void createCardPack(int typeOfGame) {
        int index = 0;
        switch (typeOfGame) {
            case 1: {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 13; j++) {
                        cardPack[index++] = new Card(j, "black", "spades", false);
                    }
                }
            }
        }
    }

}
