package sk.tuke.gamestudio.game.spidersolitaire.hresko.features;

import sk.tuke.gamestudio.game.spidersolitaire.hresko.card.Card;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.deck.Deck;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.deck.Foundations;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.deck.Tableau;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents logic to revert move
 */

public class History {

    private List<int[]> revertList;

    public History() {
        revertList = new ArrayList<>();
    }

    /**
     * This method return game to previous state.
     *
     * @param tableau     main board of game
     * @param deck        whole board of game including foundations etc.
     * @param foundations foundations of board
     */

    public void returnToPreviousStep(Tableau tableau, Deck deck, Foundations foundations) {

        List<Card> cardToRevert = new ArrayList<>();

        if (!revertList.isEmpty()) {

            int typeOfMove = revertList.get(revertList.size() - 1)[3];

            // classic move

            switch (typeOfMove) {
                case 1: {
                    int source = revertList.get(revertList.size() - 1)[2];
                    int sourceIndex = tableau.getColumns()[source].size() - revertList.get(revertList.size() - 1)[1];
                    int destination = revertList.get(revertList.size() - 1)[0];

                    for (int i = sourceIndex; i < tableau.getColumns()[source].size(); i++) {
                        cardToRevert.add(tableau.getColumns()[source].get(i));
                    }
                    if (revertList.get(revertList.size() - 1)[5] == 1) {
                        tableau.getColumns()[destination].get(tableau.getColumns()[destination].size() - 1).setFlipped(false);
                    }
                    tableau.getColumns()[destination].addAll(cardToRevert);
                    tableau.getColumns()[source].removeAll(cardToRevert);
                    deck.setStepCounter(deck.getStepCounter() - 1);
                    revertList.remove(revertList.size() - 1);
                    break;
                }

                // take cards from stock

                case 2: {
                    for (int i = 0; i < tableau.getColumns().length; i++) {
                        tableau.getColumns()[i].remove(tableau.getColumns()[i].size() - 1);
                    }
                    Deck.removeItemFromArrayIndex -= 10;
                    deck.setStepCounter(deck.getStepCounter() - 1);
                    revertList.remove(revertList.size() - 1);
                    break;
                }

                // revert if full run was added to foundations

                case 3: {
                    int destination = revertList.get(revertList.size() - 1)[2];
                    int steps = revertList.get(revertList.size() - 1)[4];

                    deck.setFoundationIndex(deck.getFoundationIndex() - 1);
                    deck.setFoundationsFilled(deck.getFoundationsFilled() - 1);
                    tableau.getColumns()[destination].addAll(foundations.getFoundationList()[deck.getFoundationIndex()]);
                    foundations.getFoundationList()[deck.getFoundationIndex()].clear();
                    revertList.remove(revertList.size() - 1);
                    deck.setStepCounter(steps);

                    if (deck.getStepCounter() >= 20) {
                        deck.setScore(deck.getScore() - (100 - steps));
                    } else {
                        deck.setScore(deck.getScore() - 100);
                    }

                    returnToPreviousStep(tableau, deck, foundations);
                    break;
                }
            }
        } else {
            System.out.println("NO STEPS TO REVERT !!!");
        }
    }

    /**
     * This method add to history each state of tableau
     *
     * @param sourceRow       row from which user move hresko or run
     * @param sourceRowLength length of source row
     * @param destinationRow  row where user moved hresko or run
     * @param typeOfMove      type of move with cards
     * @param stepCounter     number of steps
     * @param isLast          check if selected hresko was last in column
     */

    public void addToHistory(int sourceRow, int sourceRowLength, int destinationRow, int typeOfMove, int stepCounter, int isLast) {
        int[] step = new int[6];

        step[0] = sourceRow;
        step[1] = sourceRowLength;
        step[2] = destinationRow;
        step[3] = typeOfMove;
        step[4] = stepCounter;
        step[5] = isLast;

        revertList.add(step);
    }
}
