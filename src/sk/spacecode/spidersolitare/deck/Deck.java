package sk.spacecode.spidersolitare.deck;

import sk.spacecode.spidersolitare.card.Card;
import sk.spacecode.spidersolitare.card.Pack;

import java.util.*;

public class Deck {
    private Foundations foundations;
    private Stock stock;
    private Tableau tableau;
    private Card card;
    private Pack pack;
    private static int removeItemFromArrayIndex = 0;

    public Deck() {
        foundations = new Foundations();
        stock = new Stock();
        tableau = new Tableau();
        pack = new Pack();
        shuffleAndServeCards(1);
        game();
    }

    private void shuffleAndServeCards(int typeOfGame) {
        pack.createCardPack(typeOfGame);
        Card[] cardArray = pack.getCardPack();
        Collections.shuffle(Arrays.asList(cardArray));
        tableau.fillTableau(cardArray);
        stock.fillStock(cardArray);
        drawDeck();
    }

    private void game() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input) {
                case "move": {
                    int inputSourceRow = scanner.nextInt();
                    int inputSourceRowIndex = scanner.nextInt();
                    int inputDestinationRow = scanner.nextInt();

                    moveCards(inputSourceRow, inputSourceRowIndex, inputDestinationRow);
                    break;
                }
                case "hint":
                    hint();
                    break;
                case "restart":
                    Deck deck = new Deck();
                    break;
                case "take":
                    takeCardsFromStock(tableau.getColumns());
                    break;
                case "display":
                    drawDeck();
                    break;
                case "exit":
                    System.exit(0);
                    break;
            }
        }
    }

    private void moveCards(int sourceRow, int sourceRowIndex, int destinationRow) {

        if (sourceRow < tableau.getColumns().length && destinationRow < tableau.getColumns().length) {

            List<Card> sourceList = tableau.getColumns()[sourceRow];
            List<Card> destinationList = tableau.getColumns()[destinationRow];

            if (!sourceList.isEmpty() && sourceRowIndex < sourceList.size()) {
                destinationList.add(sourceList.get(sourceRowIndex));
                sourceList.get(sourceRowIndex).setFlipped(true);
                sourceList.remove(sourceRowIndex);
            } else {
                System.out.println("WRONG INDEX OF SOURCE LIST OR LIST IS EMPTY !!!");
            }
        } else {
            System.out.println("SOURE ROW OR DESTINATION ROW OUT OF INDEX !!!");
        }
    }


    private void hint() {
        List<Card> cards = new ArrayList<>();

        cards.add(tableau.getTableau1().get(tableau.getTableau1().size() - 1));
        cards.add(tableau.getTableau2().get(tableau.getTableau2().size() - 1));
        cards.add(tableau.getTableau3().get(tableau.getTableau3().size() - 1));
        cards.add(tableau.getTableau4().get(tableau.getTableau4().size() - 1));
        cards.add(tableau.getTableau5().get(tableau.getTableau5().size() - 1));
        cards.add(tableau.getTableau6().get(tableau.getTableau6().size() - 1));
        cards.add(tableau.getTableau7().get(tableau.getTableau7().size() - 1));
        cards.add(tableau.getTableau8().get(tableau.getTableau8().size() - 1));
        cards.add(tableau.getTableau9().get(tableau.getTableau9().size() - 1));
        cards.add(tableau.getTableau10().get(tableau.getTableau10().size() - 1));

        System.out.println();

        for (int j = 0; j < cards.size(); j++) {
            for (Card card1 : cards) {
                if (cards.get(j).getRank() - card1.getRank() == -1) {
                    System.out.println(" HINT " + cards.get(j).getRank() + " " + card1.getRank());
                }
            }
        }
    }

    private void drawDeck() {
        for (int i = 0; i < tableau.getColumns().length; i++) {
            for (int j = 0; j < tableau.getColumns()[i].size(); j++) {
                if (tableau.getColumns()[i].get(j).isFlipped()) {
                    System.out.print(tableau.getColumns()[i].get(j).getRank() + " ");
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }

    private void takeCardsFromStock(List[] columns) {

        for (int i = 0; i < columns.length; i++) {
            if (columns.length == 10 && removeItemFromArrayIndex <= 49) {
                stock.getStock()[removeItemFromArrayIndex].setFlipped(true);
                tableau.getColumns()[i].add(stock.getStock()[removeItemFromArrayIndex++]);
            } else {
                System.out.println("CANNOT SERVE CARDS FROM STOCK !!!");
                break;
            }
        }
    }
}
