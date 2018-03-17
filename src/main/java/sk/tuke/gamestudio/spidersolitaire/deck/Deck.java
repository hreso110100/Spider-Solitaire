package sk.tuke.gamestudio.spidersolitaire.deck;

import sk.tuke.gamestudio.spidersolitaire.card.Card;
import sk.tuke.gamestudio.spidersolitaire.card.Pack;
import sk.tuke.gamestudio.spidersolitaire.entities.Comment;
import sk.tuke.gamestudio.spidersolitaire.entities.Rating;
import sk.tuke.gamestudio.spidersolitaire.entities.Score;
import sk.tuke.gamestudio.spidersolitaire.features.History;
import sk.tuke.gamestudio.spidersolitaire.services.*;

import java.util.*;

/**
 * This class represents whole layout and core of game
 */

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
    private int foundationIndex;

    public Deck() {
        foundations = new Foundations();
        stock = new Stock();
        tableau = new Tableau();
        pack = new Pack();
        history = new History();
        shuffleAndServeCards();
        game();
    }

    /**
     * This method randomly serve cards to layouts
     */

    private void shuffleAndServeCards() {
        pack.createCardPack();
        Card[] cardArray = pack.getCardPack();
        Collections.shuffle(Arrays.asList(cardArray));
        tableau.fillTableau(cardArray);
        stock.fillStock(cardArray);
        drawDeck();
    }

    /**
     * This method represents simple console commands to play game
     */

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
                    history.returnToPreviousStep(tableau, this, foundations);
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

    /**
     * This method serve as core for moving cards
     *
     * @param sourceMovedList list which is moved
     * @param sourceList      list from which user moves cards
     * @param destinationList list where user moves cards
     * @param source          position of source list
     * @param sourceRowIndex  position of concrete item in given source
     * @param destination     position of destination list
     */

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

    /**
     * This method calls moveCardsCore and observe edge cases
     *
     * @param sourceRow      source row of moved cards
     * @param sourceRowIndex index of list item in source row
     * @param destinationRow destination row where cards are moved
     */

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

        if (checkIfGameIsLost()) {
            afterLost();
        }
    }

    /**
     * This method call services after game is lost
     */

    private void afterLost() {
        System.out.println("YOU LOST !");
        callScoreService();
        callCommentService();
        callRatingService();
        System.exit(0);
    }

    /**
     * This method check for full run of cards
     */

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
                    addRunToFoundations(run, i);
                }
            }
        }
    }

    /**
     * This method add run to foundation
     *
     * @param run       input run which is added to foundations
     * @param sourceRow represents row from which is run removed
     */

    private void addRunToFoundations(List<Card> run, int sourceRow) {
        if (run.size() == 13) {
            countScore();
            checkIfGameIsWon(run);
            tableau.getColumns()[sourceRow].removeAll(run);
            history.addToHistory(0, 0, inputDestinationRow, 3);
            run.clear();
        } else {
            run.clear();
        }
    }

    /**
     * This method check if game is won
     *
     * @param run run of cards which are added to foundations
     */

    private void checkIfGameIsWon(List<Card> run) {
        foundations.getFoundationList()[getFoundationIndex()].addAll(run);
        setFoundationIndex(getFoundationIndex() + 1);

        if (foundations.getFoundationList().length == 8) {
            System.out.println("CONGRATULATIONS, YOU ARE WINNER !!!");
            callScoreService();
            callCommentService();
            callRatingService();
        }
    }

    /**
     * This method check if game is lost
     *
     * @return true if yes or false if not
     */

    private boolean checkIfGameIsLost() {
        if (removeItemFromArrayIndex == 50) {
            for (int i = 0; i < tableau.getColumns().length; i++) {
                Card startOfRun = tableau.getColumns()[i].get(tableau.getColumns()[i].size() - 1);
                for (int j = tableau.getColumns()[i].size() - 2; j > 0; j--) {
                    if (tableau.getColumns()[i].get(j).getRank() - startOfRun.getRank() == 1 && tableau.getColumns()[i].get(j).isFlipped()) {
                        startOfRun = tableau.getColumns()[i].get(j);
                    }
                }

                for (int k = 0; k < tableau.getColumns().length; k++) {
                    Card lastItemOfColumn = tableau.getColumns()[k].get(tableau.getColumns()[k].size() - 1);

                    if (i != k && startOfRun.getRank() - lastItemOfColumn.getRank() == -1) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }


    /**
     * This method call score service
     */

    private void callScoreService() {
        Score score = new Score("david", getScore(), new Date());
        ScoreService scoreService = new ScoreServiceJDBC();
        scoreService.addScore(score);
        System.out.println("BEST SCORES");
        for (int i = 0; i < scoreService.getBestScores("spider-solitaire").size(); i++) {
            System.out.println(scoreService.getBestScores("spider-solitaire").get(i).getPlayer() + " " +
                    scoreService.getBestScores("spider-solitaire").get(i).getPoints());
        }
    }

    /**
     * This method calls comment service
     */

    private void callCommentService() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER YOUR COMMENT: ");
        String input = scanner.nextLine();

        Comment comment = new Comment("david", input, new Date());
        CommentService commentService = new CommentServiceJDBC();
        commentService.addComment(comment);
        System.out.println("RECENT COMMENTS");
        for (int i = 0; i < commentService.getComments("spider-solitaire").size(); i++) {
            System.out.println(commentService.getComments("spider-solitaire").get(i).getPlayer() + " " +
                    commentService.getComments("spider-solitaire").get(i).getComment());
        }
    }

    /**
     * This method calls rating service
     */

    private void callRatingService() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER YOUR RATING");
        Integer input = scanner.nextInt();

        Rating rating = new Rating("david", input, new Date());
        RatingService ratingService = new RatingServiceJDBC();
        ratingService.setRating(rating);

        try {
            System.out.println("AVERAGE RATING OF GAME IS " + ratingService.getAverageRating("spider-solitaire"));
        } catch (NullPointerException e) {
            System.out.println("ROW DOESN'T EXIST");
        }
    }

    /**
     * This method counts score of player
     */

    private void countScore() {
        if (stepCounter < 20) {
            score += 100;
        } else {
            score += (100 - stepCounter);
        }
    }

    /**
     * This method prints indexes of columns
     */

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

    /**
     * This method prints cards to tableau
     */

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

    /**
     * This method check length of every column
     *
     * @param columns array of all ten rows of tableau
     * @return true if is not empty, else returns false
     */

    private boolean checkLengthOfColumns(List[] columns) {
        for (List column : columns) {
            if (column.size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method add cards from stock to tableau
     *
     * @param columns represents array of columns in tableau
     */

    private void takeCardsFromStock(List[] columns) {

        for (int i = 0; i < columns.length; i++) {
            if (checkLengthOfColumns(tableau.getColumns()) && removeItemFromArrayIndex <= 49) {
                tableau.getColumns()[i].add(stock.getStock()[removeItemFromArrayIndex++]);
                if (i == columns.length - 1) {
                    history.addToHistory(0, 0, 0, 2);
                    stepCounter++;
                }
            } else {
                System.out.println("STOCK IS EMPTY OR TABLEAU HAS EMPTY ROW");
                if (checkIfGameIsLost()) {
                    afterLost();
                }
                break;
            }
        }
        checkForFullRun();
        drawDeck();
    }

    // getters and setters

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

    public int getFoundationIndex() {
        return foundationIndex;
    }

    public void setFoundationIndex(int foundationIndex) {
        this.foundationIndex = foundationIndex;
    }
}
