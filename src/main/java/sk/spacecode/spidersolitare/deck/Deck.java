package sk.spacecode.spidersolitare.deck;

import sk.spacecode.spidersolitare.card.Card;
import sk.spacecode.spidersolitare.card.Pack;
import sk.spacecode.spidersolitare.entities.Comment;
import sk.spacecode.spidersolitare.entities.Rating;
import sk.spacecode.spidersolitare.entities.Score;
import sk.spacecode.spidersolitare.features.History;
import sk.spacecode.spidersolitare.services.*;

import java.util.*;

public class Deck {
    private Foundations foundations;
    private Stock stock;
    private Tableau tableau;
    private Pack pack;
    private History history;
    public static int removeItemFromArrayIndex = 0;
    private int score;
    private int stepCounter;
    private int inputDestinationRow;

    public Deck() {
        foundations = new Foundations();
        stock = new Stock();
        tableau = new Tableau();
        pack = new Pack();
        history = new History();
        shuffleAndServeCards();
        game();
    }

    private void shuffleAndServeCards() {
        pack.createCardPack();
        Card[] cardArray = pack.getCardPack();
        Collections.shuffle(Arrays.asList(cardArray));
        tableau.fillTableau(cardArray);
        stock.fillStock(cardArray);
        drawDeck();
    }

    public int getStepCounter() {
        return stepCounter;
    }

    public void setStepCounter(int stepCounter) {
        this.stepCounter = stepCounter;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private void game() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input) {
                case "move": {
                    System.out.println("ENTER SOURCE ROW");
                    int inputSourceRow = scanner.nextInt();
                    System.out.println("ENTER SOURCE ROW INDEX");
                    int inputSourceRowIndex = scanner.nextInt();
                    System.out.println("ENTER DESTINATION ROW");
                    inputDestinationRow = scanner.nextInt();

                    moveCards(inputSourceRow, inputSourceRowIndex, inputDestinationRow);
                    break;
                }
                case "revert": {
                    history.removeFromHistory(tableau, this, foundations);
                    drawDeck();
                    break;
                }
                case "restart":
                    Deck deck = new Deck();
                    break;
                case "take":
                    takeCardsFromStock(tableau.getColumns());
                    break;
                case "exit": {

                    // TODO vymazat volania servisov po odovzdavke

                    callScoreService();
                    callCommentService();
                    callRatingService();
                    System.exit(0);
                    break;
                }
            }
        }
    }

    private void moveCardsCore(List<Card> sourceMovedList, List<Card> sourceList, List<Card> destinationList, int source, int sourceRowIndex, int destination) {

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
        history.addToHistory(source, sourceMovedList.size(), destination, 1);
        sourceList.removeAll(sourceMovedList);
        sourceMovedList.clear();
    }

    private void moveCards(int sourceRow, int sourceRowIndex, int destinationRow) {

        if (sourceRow < tableau.getColumns().length && destinationRow < tableau.getColumns().length) {
            List<Card> sourceList = tableau.getColumns()[sourceRow];
            List<Card> destinationList = tableau.getColumns()[destinationRow];
            List<Card> sourceMovedList = new ArrayList<>();

            if (!sourceList.isEmpty() && sourceRowIndex < sourceList.size()
                    && !destinationList.isEmpty() && (sourceList.get(sourceRowIndex).getRank() - destinationList.get(destinationList.size() - 1).getRank() == -1)) {
                moveCardsCore(sourceMovedList, sourceList, destinationList, sourceRow, sourceRowIndex, destinationRow);
                stepCounter++;
            } else if (!sourceList.isEmpty() && sourceRowIndex < sourceList.size() && destinationList.isEmpty()) {
                moveCardsCore(sourceMovedList, sourceList, destinationList, sourceRow, sourceRowIndex, destinationRow);
                stepCounter++;
            } else {
                System.out.println("WRONG INDEX OF SOURCE LIST OR LIST IS EMPTY !!!");
            }
        } else {
            System.out.println("SOURCE ROW OR DESTINATION ROW OUT OF INDEX !!!");
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
                    winGame(run, i);
                }
            }
        }
    }

    private void winGame(List<Card> run, int i) {
        if (run.size() == 13) {
            countScore();
            foundations.addRunAndCheckWin(run);
            tableau.getColumns()[i].removeAll(run);
            history.addToHistory(0, 0, inputDestinationRow, 3);
            run.clear();
            callScoreService();
            callCommentService();
            callRatingService();
        } else {
            run.clear();
        }
    }

    private void callScoreService() {
        Score score = new Score("david", getScore(), new Date());
        ScoreService scoreService = new ScoreServiceJDBC();
        scoreService.addScore(score);
        System.out.println(scoreService.getBestScores("spider-solitaire"));
    }

    private void callCommentService() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Comment comment = new Comment("david", input, new Date());
        CommentService commentService = new CommentServiceJDBC();
        commentService.addComment(comment);
        System.out.println(commentService.getComments("spider-solitaire"));
    }

    private void callRatingService() {
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();

        Rating rating = new Rating("david", input, new Date());
        RatingService ratingService = new RatingServiceJDBC();
        ratingService.setRating(rating);
        try {
            System.out.println(ratingService.getRating("spider-solitaire", "david"));
            System.out.println("AVERAGE RATING OF GAME IS " + ratingService.getAverageRating("spider-solitaire"));
        }catch (NullPointerException e) {
            System.out.println("ROW DOESN'T EXIST");
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
                if (i == columns.length - 1) {
                    history.addToHistory(0, 0, 0, 2);
                }
            } else {
                System.out.println("STOCK IS EMPTY OR TABLEAU HAS EMPTY ROW");
                break;
            }
        }
        checkForFullRun();
        drawDeck();
    }
}
