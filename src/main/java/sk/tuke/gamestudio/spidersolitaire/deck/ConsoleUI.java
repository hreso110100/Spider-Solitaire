package sk.tuke.gamestudio.spidersolitaire.deck;

import java.util.InputMismatchException;
import java.util.Scanner;

import static sk.tuke.gamestudio.spidersolitaire.deck.Deck.removeItemFromArrayIndex;

/**
 * This class represents simple console UI
 */

public class ConsoleUI {

    private Deck deck;

    public ConsoleUI() {
        deck = new Deck();
        drawDeck();
        game();
    }

    /**
     * This method represents simple console commands to play game
     */

    private void game() {
        while (true) {
            System.out.println("Enter your command");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input) {
                case "move": {
                    try {
                        System.out.println("ENTER SOURCE ROW");
                        int inputSourceRow = scanner.nextInt();
                        System.out.println("ENTER SOURCE ROW INDEX");
                        int inputSourceRowIndex = scanner.nextInt();
                        System.out.println("ENTER DESTINATION ROW");
                        deck.setInputDestinationRow(scanner.nextInt());

                        deck.moveCards(inputSourceRow, inputSourceRowIndex, deck.getInputDestinationRow());
                        drawDeck();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Wrong input, try again");
                        drawDeck();
                        break;
                    }
                }
                case "revert": {
                    deck.getHistory().returnToPreviousStep(deck.getTableau(), deck, deck.getFoundations());
                    drawDeck();
                    break;
                }
                case "restart":
                    deck = new Deck();
                    drawDeck();
                    break;
                case "take":
                    deck.takeCardsFromStock(deck.getTableau().getColumns());
                    drawDeck();
                    break;
                case "exit": {
                    deck.callScoreService();
                    deck.callCommentService();
                    deck.callRatingService();
                    System.exit(0);
                    break;
                }
                default:
                    System.out.println("Wrong command ! Try again");
            }
        }
    }


    /**
     * This method prints indexes of columns
     */

    private void drawIndexOnDeck() {
        int size = 0;

        for (int i = 0; i < deck.getTableau().getColumns().length; i++) {
            if (size < deck.getTableau().getColumns()[i].size()) {
                size = deck.getTableau().getColumns()[i].size();
            }
        }
        System.out.println("SCORE " + deck.getScore() + " STEPS " + deck.getStepCounter());
        System.out.println("FOUNDATIONS " + deck.getFoundationsFilled() + " STOCK " + (5 - removeItemFromArrayIndex / 10) + '\n');
        System.out.print("COL      ");

        for (int j = 0; j < size; j++) {
            System.out.print(j + "  ");
        }
        System.out.println();
    }

    /**
     * This method prints cards to tableau
     */

    private void drawDeck() {
        if (deck.getGameState() == 0) {
            drawIndexOnDeck();

            for (int i = 0; i < deck.getTableau().getColumns().length; i++) {
                System.out.print("ROW " + i + "   ");
                for (int j = 0; j < deck.getTableau().getColumns()[i].size(); j++) {
                    if (j == deck.getTableau().getColumns()[i].size() - 1) {
                        deck.getTableau().getColumns()[i].get(j).setFlipped(true);
                    }
                    if (deck.getTableau().getColumns()[i].get(j).isFlipped()) {
                        switch (deck.getTableau().getColumns()[i].get(j).getRank()) {
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
                                System.out.print(" " + deck.getTableau().getColumns()[i].get(j).getRank() + " ");
                                break;
                        }
                    } else {
                        System.out.print(" - ");
                    }
                }
                System.out.println();
            }
        } else if (deck.getGameState() == 2) {
            System.out.println("WINNER");
        } else if (deck.getGameState() == 1) {
            System.out.println("LOOSER");
        }
    }
}
