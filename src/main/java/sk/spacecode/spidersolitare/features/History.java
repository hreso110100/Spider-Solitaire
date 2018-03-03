package sk.spacecode.spidersolitare.features;

import sk.spacecode.spidersolitare.card.Card;
import sk.spacecode.spidersolitare.deck.Deck;
import sk.spacecode.spidersolitare.deck.Foundations;
import sk.spacecode.spidersolitare.deck.Tableau;

import java.util.ArrayList;
import java.util.List;

public class History {

    private List<int[]> revertList;

    public History() {
        revertList = new ArrayList<>();
    }

    public void removeFromHistory(Tableau tableau, Deck deck, Foundations foundations) {

        List<Card> cardToRevert = new ArrayList<>();

        if (!revertList.isEmpty()) {

            int typeOfMove = revertList.get(revertList.size() - 1)[3];

            switch (typeOfMove) {
                case 1: {
                    int source = revertList.get(revertList.size() - 1)[2];
                    int sourceIndex = tableau.getColumns()[source].size() - revertList.get(revertList.size() - 1)[1];
                    int destination = revertList.get(revertList.size() - 1)[0];

                    for (int i = sourceIndex; i < tableau.getColumns()[source].size(); i++) {
                        cardToRevert.add(tableau.getColumns()[source].get(i));
                    }
                    tableau.getColumns()[destination].get(tableau.getColumns()[destination].size() - 1).setFlipped(false);
                    tableau.getColumns()[destination].addAll(cardToRevert);
                    tableau.getColumns()[source].removeAll(cardToRevert);
                    deck.setStepCounter(deck.getStepCounter() - 1);
                    revertList.remove(revertList.size() - 1);
                    break;
                }

                case 2: {
                    for (int i = 0; i < tableau.getColumns().length; i++) {
                        tableau.getColumns()[i].remove(tableau.getColumns()[i].size() - 1);
                    }
                    Deck.removeItemFromArrayIndex -= 10;
                    deck.setStepCounter(deck.getStepCounter() - 1);
                    revertList.remove(revertList.size() - 1);
                    break;
                }

                case 3: {
                    int destination = revertList.get(revertList.size() - 1)[2];

                    foundations.setFoundationIndex(foundations.getFoundationIndex() - 1);
                    foundations.setFoundationFilled(foundations.getFoundationFilled() - 1);
                    tableau.getColumns()[destination].addAll(foundations.getFoundationList()[foundations.getFoundationIndex()]);
                    foundations.getFoundationList()[foundations.getFoundationIndex()].clear();
                    revertList.remove(revertList.size() - 1);

                    if (deck.getStepCounter() >= 20) {
                        deck.setScore(deck.getScore() - (100 - deck.getStepCounter()));
                    } else {
                        deck.setScore(deck.getScore() - 100);
                    }
                    removeFromHistory(tableau, deck, foundations);
                    break;
                }
            }
        } else {
            System.out.println("NO STEPS TO REVERT !!!");
        }
    }

    public void addToHistory(int source, int sourceLength, int destination, int typeOfMove) {
        int[] revertParams = new int[4];

        revertParams[0] = source;
        revertParams[1] = sourceLength;
        revertParams[2] = destination;
        revertParams[3] = typeOfMove;

        revertList.add(revertParams);
    }
}
