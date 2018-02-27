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
                    System.out.println("ENTER SOURCER ROW");
                    int inputSourceRow = scanner.nextInt();
                    System.out.println("ENTER SOURCE ROW INDEX");
                    int inputSourceRowIndex = scanner.nextInt();
                    System.out.println("ENTER DESTINATION ROW");
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
            List<Card> sourceMovedList = new ArrayList<>();

            if (!sourceList.isEmpty() && sourceRowIndex < sourceList.size()
                    && (sourceList.get(sourceRowIndex).getRank() - destinationList.get(destinationList.size() - 1).getRank() == -1)) {

                int firstItemAtColumn = sourceList.get(sourceRowIndex).getRank();
                sourceMovedList.add(sourceList.get(sourceRowIndex));

                for (int i = sourceRowIndex + 1; i < sourceList.size(); i++) {
                    if (firstItemAtColumn - sourceList.get(i).getRank() == 1) {
                        firstItemAtColumn = sourceList.get(i).getRank();
                        sourceMovedList.add(sourceList.get(i));
                    } else {
                        sourceMovedList.clear();
                        System.out.println("CANNOT MOVE CARDS !!!");
                    }
                }
                destinationList.addAll(sourceMovedList);
                sourceList.removeAll(sourceMovedList);
                sourceMovedList.clear();
            } else {
                System.out.println("WRONG INDEX OF SOURCE LIST OR LIST IS EMPTY !!!");
            }
        } else {
            System.out.println("SOURE ROW OR DESTINATION ROW OUT OF INDEX !!!");
        }
        addToFoundations();
        drawDeck();
    }


    private void hint() {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < tableau.getColumns().length; i++) {
            cards.add(tableau.getColumns()[i].get(tableau.getColumns()[i].size() - 1));
        }

        for (int j = 0; j < cards.size(); j++) {
            for (Card card : cards) {
                if (cards.get(j).getRank() - card.getRank() == -1) {
                    System.out.println(" HINT " + cards.get(j).getRank() + " " + card.getRank());
                }
            }
        }
    }

    private void addToFoundations() {

        for (int i = 0; i < tableau.getColumns().length; i++) {
            for (int j = 0; j < tableau.getColumns()[i].size(); j++) {
                if (tableau.getColumns()[i].get(j).getRank() == 13) {
                   
                }
            }
        }
    }

    private void drawDeck() {
        for (int i = 0; i < tableau.getColumns().length; i++) {
            System.out.print("ROW " + i + "   ");
            for (int j = 0; j < tableau.getColumns()[i].size(); j++) {
                if (j == tableau.getColumns()[i].size() - 1) {
                    tableau.getColumns()[i].get(j).setFlipped(true);
                }
                if (tableau.getColumns()[i].get(j).isFlipped()) {
                    switch (tableau.getColumns()[i].get(j).getRank()) {
                        case 1:
                            System.out.print("A ");
                            break;
                        case 11:
                            System.out.print("J ");
                            break;
                        case 12:
                            System.out.print("Q ");
                            break;
                        case 13:
                            System.out.print("K ");
                            break;
                        default:
                            System.out.print(tableau.getColumns()[i].get(j).getRank() + " ");
                            break;
                    }
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
                tableau.getColumns()[i].add(stock.getStock()[removeItemFromArrayIndex++]);
            } else {
                System.out.println("STOCK IS EMPTY.");
                break;
            }
        }
        drawDeck();
    }
}
