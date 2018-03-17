package sk.tuke.gamestudio.spidersolitaire.deck;

import sk.tuke.gamestudio.spidersolitaire.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class represents main game board
 */

public class Tableau {

    private List[] columns;
    private List<Card> tableau1;
    private List<Card> tableau2;
    private List<Card> tableau3;
    private List<Card> tableau4;
    private List<Card> tableau5;
    private List<Card> tableau6;
    private List<Card> tableau7;
    private List<Card> tableau8;
    private List<Card> tableau9;
    private List<Card> tableau10;

    public Tableau() {
        tableau1 = new ArrayList<>();
        tableau2 = new ArrayList<>();
        tableau3 = new ArrayList<>();
        tableau4 = new ArrayList<>();
        tableau5 = new ArrayList<>();
        tableau6 = new ArrayList<>();
        tableau7 = new ArrayList<>();
        tableau8 = new ArrayList<>();
        tableau9 = new ArrayList<>();
        tableau10 = new ArrayList<>();
        columns = new List[]{
                tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7, tableau8, tableau9, tableau10
        };
    }

    /**
     *
     * @param cards represents first 54 cards from package
     */

    public void fillTableau(Card[] cards) {
        int index = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 1; j++) {

                if (i == 5) {
                    tableau1.add(cards[index++]);
                    tableau2.add(cards[index++]);
                    tableau3.add(cards[index++]);
                    tableau4.add(cards[index]);
                } else {
                    tableau1.add(cards[index++]);
                    tableau2.add(cards[index++]);
                    tableau3.add(cards[index++]);
                    tableau4.add(cards[index++]);
                    tableau5.add(cards[index++]);
                    tableau6.add(cards[index++]);
                    tableau7.add(cards[index++]);
                    tableau8.add(cards[index++]);
                    tableau9.add(cards[index++]);
                    tableau10.add(cards[index++]);
                }
            }
        }
    }

    // getters and setters

    @SuppressWarnings("unchecked")
    public List<Card>[] getColumns() {
        return columns;
    }
}
