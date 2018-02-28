package sk.spacecode.spidersolitare.deck;

import sk.spacecode.spidersolitare.card.Card;
import sk.spacecode.spidersolitare.card.Pack;
import sk.spacecode.spidersolitare.features.History;

import java.util.*;

public class Deck {
    private Foundations foundations;
    private Stock stock;
    private Tableau tableau;
    private Pack pack;
    private History history;
    private static int removeItemFromArrayIndex = 0;
    private int score;
    private int stepCounter;

    public Deck() {
        foundations = new Foundations();
        stock = new Stock();
        tableau = new Tableau();
        pack = new Pack();
        history = new History();
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

    private void moveCardsCore(List<Card> sourceMovedList, List<Card> sourceList, List<Card> destinationList, int sourceRowIndex) {

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
    }

    private void moveCards(int sourceRow, int sourceRowIndex, int destinationRow) {

        stepCounter++;

        if (sourceRow < tableau.getColumns().length && destinationRow < tableau.getColumns().length) {
            List<Card> sourceList = tableau.getColumns()[sourceRow];
            List<Card> destinationList = tableau.getColumns()[destinationRow];
            List<Card> sourceMovedList = new ArrayList<>();

            if (!sourceList.isEmpty() && sourceRowIndex < sourceList.size()
                    && !destinationList.isEmpty() && (sourceList.get(sourceRowIndex).getRank() - destinationList.get(destinationList.size() - 1).getRank() == -1)) {
                moveCardsCore(sourceMovedList, sourceList, destinationList, sourceRowIndex);
            } else if (!sourceList.isEmpty() && sourceRowIndex < sourceList.size() && destinationList.isEmpty()) {
                moveCardsCore(sourceMovedList, sourceList, destinationList, sourceRowIndex);
            } else {
                System.out.println("WRONG INDEX OF SOURCE LIST OR LIST IS EMPTY !!!");
            }
        } else {
            System.out.println("SOURE ROW OR DESTINATION ROW OUT OF INDEX !!!");
        }
        checkForFullRun();
        drawDeck();
    }

    private void checkForFullRun() {

        List<Card> run = new ArrayList<>();

        for (int i = 0; i < tableau.getColumns().length; i++) {
            for (int j = 0; j < tableau.getColumns()[i].size(); j++) {
                if (tableau.getColumns()[i].get(j).getRank() == 13) {
                    run.add(tableau.getColumns()[i].get(j));
                    int startOfRun = tableau.getColumns()[i].get(j).getRank();

                    for (int k = j + 1; k < tableau.getColumns()[i].size(); k++) {
                        if (startOfRun - tableau.getColumns()[i].get(k).getRank() == 1) {
                            run.add(tableau.getColumns()[i].get(k));
                            startOfRun = tableau.getColumns()[i].get(k).getRank();
                        }
                    }

                    if (run.size() == 13) {
                        countScore();
                        foundations.addRunAndCheckWin(run);
                        tableau.getColumns()[i].removeAll(run);
                        run.clear();
                    } else {
                        run.clear();
                    }
                }
            }
        }
    }

    private void countScore() {
        if (stepCounter < 20) {
            score += 100;
        } else {
            score += (100 - stepCounter);
        }
    }

    private void drawIndexOnDeck() {
        int size = 0;

        for (int i = 0; i < tableau.getColumns().length; i++) {
            if (size < tableau.getColumns()[i].size()) {
                size = tableau.getColumns()[i].size();
            }
        }
        System.out.println("SCORE " + score + " STEPS " + stepCounter + '\n');
        System.out.print("COL      ");

        for (int j = 0; j < size; j++) {
            System.out.print(j + "  ");
        }
        System.out.println();
    }

    private void drawDeck() {
        drawIndexOnDeck();

        for (int i = 0; i < tableau.getColumns().length; i++) {
            System.out.print("ROW " + i + "   ");
            for (int j = 0; j < tableau.getColumns()[i].size(); j++) {
                if (j == tableau.getColumns()[i].size() - 1) {
                    tableau.getColumns()[i].get(j).setFlipped(true);
                }
                if (tableau.getColumns()[i].get(j).isFlipped()) {
                    switch (tableau.getColumns()[i].get(j).getRank()) {
                        case 1:
                            System.out.print(" A ");
                            break;
                        case 11:
                            System.out.print(" J ");
                            break;
                        case 12:
                            System.out.print(" Q ");
                            break;
                        case 13:
                            System.out.print(" K ");
                            break;
                        default:
                            System.out.print(" " + tableau.getColumns()[i].get(j).getRank() + " ");
                            break;
                    }
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }

    private boolean checkLengthOfColumns(List[] columns) {
        for (List column : columns) {
            if (column.size() == 0) {
                return false;
            }
        }
        return true;
    }

    private void takeCardsFromStock(List[] columns) {

        stepCounter++;

        for (int i = 0; i < columns.length; i++) {
            if (checkLengthOfColumns(tableau.getColumns()) && removeItemFromArrayIndex <= 49) {
                tableau.getColumns()[i].add(stock.getStock()[removeItemFromArrayIndex++]);
            } else {
                System.out.println("STOCK IS EMPTY OR TABLEAU HAS EMPTY ROW");
                break;
            }
        }
        checkForFullRun();
        drawDeck();
    }
}
