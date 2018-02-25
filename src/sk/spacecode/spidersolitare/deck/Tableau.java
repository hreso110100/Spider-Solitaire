package sk.spacecode.spidersolitare.deck;

import sk.spacecode.spidersolitare.card.Card;

import java.util.ArrayList;
import java.util.List;

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

    public List<Card>[] getColumns() {
        return columns;
    }

    public List<Card> getTableau1() {
        return tableau1;
    }

    public List<Card> getTableau2() {
        return tableau2;
    }

    public List<Card> getTableau3() {
        return tableau3;
    }

    public List<Card> getTableau4() {
        return tableau4;
    }

    public List<Card> getTableau5() {
        return tableau5;
    }

    public List<Card> getTableau6() {
        return tableau6;
    }

    public List<Card> getTableau7() {
        return tableau7;
    }

    public List<Card> getTableau8() {
        return tableau8;
    }

    public List<Card> getTableau9() {
        return tableau9;
    }

    public List<Card> getTableau10() {
        return tableau10;
    }

    public void fillTableau(Card[] cards) {
        int index = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 1; j++) {

                if (i == 5) {
                    tableau1.add(cards[index++]);
                    tableau1.get(tableau1.size() - 1).setFlipped(true);
                    tableau2.add(cards[index++]);
                    tableau2.get(tableau2.size() - 1).setFlipped(true);
                    tableau3.add(cards[index++]);
                    tableau3.get(tableau3.size() - 1).setFlipped(true);
                    tableau4.add(cards[index]);
                    tableau4.get(tableau4.size() - 1).setFlipped(true);
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

                if (i == 4) {
                    tableau5.get(tableau5.size() - 1).setFlipped(true);
                    tableau6.get(tableau6.size() - 1).setFlipped(true);
                    tableau7.get(tableau7.size() - 1).setFlipped(true);
                    tableau8.get(tableau8.size() - 1).setFlipped(true);
                    tableau9.get(tableau9.size() - 1).setFlipped(true);
                    tableau10.get(tableau10.size() - 1).setFlipped(true);
                }
            }
        }
    }
}
