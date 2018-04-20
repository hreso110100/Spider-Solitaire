package sk.tuke.gamestudio.game.spidersolitaire.hresko.deck;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.card.Card;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.card.Pack;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.features.History;
import sk.tuke.gamestudio.server.service.*;

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
    private int foundationsFilled;
    private int gameState;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    public Deck() {
        foundations = new Foundations();
        stock = new Stock();
        tableau = new Tableau();
        pack = new Pack();
        history = new History();
        removeItemFromArrayIndex = 0;
        shuffleAndServeCards();
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
        int isLast = 0;
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
        if (sourceRowIndex > 0 && !tableau.getColumns()[source].get(sourceRowIndex - 1).isFlipped()) {
            isLast = 1;
        }
        destinationList.addAll(sourceMovedList);
        history.addToHistory(source, sourceMovedList.size(), destination, 1, getStepCounter(), isLast);
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

    public void moveCards(int sourceRow, int sourceRowIndex, int destinationRow) {

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
/*
        if (checkIfGameIsLost()) {
            afterLost();
        }*/
    }

    /* COMMENT REASON : CAUSE BUGS IN WEB UI

    private void afterLost() {
        setGameState(1);
        callScoreService();
        callCommentService();
        callRatingService();
        System.exit(0);
    }

    */

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
        int isLast = 0;

        if (run.size() == 13) {
            checkIfGameIsWon(run);
            tableau.getColumns()[sourceRow].removeAll(run);
            history.addToHistory(0, 0, inputDestinationRow, 3, getStepCounter(), isLast);
            countScore();
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
        foundationsFilled++;
        if (foundationsFilled == 8) {
            setGameState(2);
            // TODO Servisy budu volane z renderDeck() v WEBUI
//            callScoreService();
//            callCommentService();
//            callRatingService();
//            System.exit(0);
        }
    }

/*  REASON OF COMMENTED : CAUSED BUGS IN WEB UI, USE CAN ONLY MANUALLY END GAME :/

    private boolean checkIfGameIsLost() {
        if (removeItemFromArrayIndex == 50) {
            for (int i = 0; i < tableau.getColumns().length; i++) {
                if (tableau.getColumns()[i].size() > 0) {
                    Card startOfRun = tableau.getColumns()[i].get(tableau.getColumns()[i].size() - 1);
                    for (int j = tableau.getColumns()[i].size() - 2; j > 0; j--) {
                        if (tableau.getColumns()[i].get(j).getRank() - startOfRun.getRank() == 1 && tableau.getColumns()[i].get(j).isFlipped()) {
                            startOfRun = tableau.getColumns()[i].get(j);
                        }
                    }

                    for (int k = 0; k < tableau.getColumns().length; k++) {
                        Card lastItemOfColumn;

                        if (tableau.getColumns()[k].size() > 1) {
                            lastItemOfColumn = tableau.getColumns()[k].get(tableau.getColumns()[k].size() - 1);

                            if (i != k && startOfRun.getRank() - lastItemOfColumn.getRank() == -1) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }*/

    /* CALLING SERVICES IN CONSOLE UI, NOT NEEDED IN WEB UI

    public void callScoreService() {
        Score score = new Score("david", getScore(), new Date());
        scoreService.addScore(score);
    }

    public void callCommentService() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER YOUR COMMENT: ");
        String input = scanner.nextLine();

        Comment comment = new Comment("david", input, new Date());
        commentService.addComment(comment);
    }

    public void callRatingService() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER YOUR RATING");
        Integer input = scanner.nextInt();

        Rating rating = new Rating("david", input, new Date());
        ratingService.setRating(rating);

        try {
            System.out.println("AVERAGE RATING OF GAME IS " + ratingService.getAverageRating("spider-solitaire"));
        } catch (NullPointerException e) {
            System.out.println("ROW DOESN'T EXIST");
        }
    }

    */

/*

    JDBC SERVICE METHODS (DEPRECATED)

    public void callScoreService() {
        Score score = new Score("david", getScore(), new Date());
        ScoreService scoreService = new ScoreServiceJDBC();
        scoreService.addScore(score);
        System.out.println("BEST SCORES");
        for (int i = 0; i < scoreService.getBestScores("spider-solitaire").size(); i++) {
            System.out.println(scoreService.getBestScores("spider-solitaire").get(i).getPlayer() + " " +
                    scoreService.getBestScores("spider-solitaire").get(i).getPoints());
        }
    }

    public void callCommentService() {
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

    public void callRatingService() {
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
*/

    /**
     * This method counts score of player
     */

    private void countScore() {
        if (stepCounter <= 20) {
            score += 100;
        } else if (stepCounter < 100) {
            score += (100 - stepCounter);
        }
        setStepCounter(0);
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

    public int takeCardsFromStock(List[] columns) {

        for (int i = 0; i < columns.length; i++) {
            if (checkLengthOfColumns(tableau.getColumns()) && removeItemFromArrayIndex <= 49) {
                tableau.getColumns()[i].add(stock.getStock()[removeItemFromArrayIndex++]);
                if (i == columns.length - 1) {
                    history.addToHistory(0, 0, 0, 2, getStepCounter(), 0);
                    stepCounter++;
                }
            } else {
                System.out.println("STOCK IS EMPTY OR TABLEAU HAS EMPTY ROW");
               /* if (checkIfGameIsLost()) {
                    afterLost();
                }*/
                return 2;
            }
        }
        checkForFullRun();
        return 1;
    }

    // getters and setters

    public int getStepCounter() {
        return stepCounter;
    }

    public void setStepCounter(int stepCounter) {
        this.stepCounter = stepCounter;

        if (stepCounter < 0) {
            this.stepCounter = 0;
        }
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

    public int getFoundationsFilled() {
        return foundationsFilled;
    }

    public void setFoundationsFilled(int foundationsFilled) {
        this.foundationsFilled = foundationsFilled;
    }

    public Foundations getFoundations() {
        return foundations;
    }

    public Tableau getTableau() {
        return tableau;
    }

    public History getHistory() {
        return history;
    }

    public int getInputDestinationRow() {
        return inputDestinationRow;
    }

    public void setInputDestinationRow(int inputDestinationRow) {
        this.inputDestinationRow = inputDestinationRow;
    }

    public int getGameState() {
        return gameState;
    }

    private void setGameState(int gameState) {
        this.gameState = gameState;
    }
}
