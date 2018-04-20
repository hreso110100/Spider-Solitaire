package sk.tuke.gamestudio.game.spidersolitaire.hresko.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.deck.Deck;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.ScoreService;
import sk.tuke.gamestudio.server.service.ScoreServiceJPA;

import java.util.Date;

public class WebUI {

    private Deck deck;
    private int value;

    public WebUI() {
        deck = new Deck();
    }

    @Autowired
    private ScoreService scoreService;

    public int getScore() {
        return deck.getScore();
    }

    public int getSteps() {
        return deck.getStepCounter();
    }

    public void processCommand(String command, String sourceRow, String sourceRowIndex, String destinationRow) {
        if (command != null) {
            switch (command) {

                case "take": {
                    value = deck.takeCardsFromStock(deck.getTableau().getColumns());
                    break;
                }
                case "exit": {
                    scoreService.addScore(new Score("david",getScore(),new Date()));
                    deck = new Deck();
                    break;
                }
                case "revert": {
                    deck.getHistory().returnToPreviousStep(deck.getTableau(), deck, deck.getFoundations());
                    break;
                }
                case "move": {

                    int parsedSourceRow = Integer.parseInt(sourceRow);
                    int parsedSourceRowIndex = Integer.parseInt(sourceRowIndex);
                    int parsedDestinationRow = Integer.parseInt(destinationRow);

                    deck.moveCards(parsedSourceRow, parsedSourceRowIndex, parsedDestinationRow);
                    break;
                }
                case "restart": {
                    deck = new Deck();
                    break;
                }
            }
        }

    }

    public String renderError() {

        StringBuilder errorBuilder = new StringBuilder();

        if (value == 2) {
            errorBuilder.append("<div class= alert id=success-alert>");
            errorBuilder.append("<strong>You should read rules ! </strong>");
            errorBuilder.append("Every column must contain at least one card to use stock.");
            errorBuilder.append("</div>");
            value = 0;

            return errorBuilder.toString();
        }

        return null;
    }

    public String renderStock() {
        StringBuilder stockBuilder = new StringBuilder();

        if (Deck.removeItemFromArrayIndex <= 49) {
            stockBuilder.append("<a href='" + "?command=take' class = stock-a>");
            stockBuilder.append("<img src='" + "/images/spider-solitaire/hresko/back.png" + "' width = 110px height=150px>");
            stockBuilder.append("</a>");
        } else {
            stockBuilder.append("<img class = stock-a src='" + "/images/spider-solitaire/hresko/empty_stock.png" + "' width = 110px height=150px>");
        }
        return stockBuilder.toString();
    }

    public String renderFoundations() {
        StringBuilder foundationBuilder = new StringBuilder();

        foundationBuilder.append("<img class = foundation src='" + "/images/spider-solitaire/hresko/empty.png" + "' width = 130px height=150px>");

        for (int i = 0; i < deck.getFoundations().getFoundationList().length; i++) {
            if (!deck.getFoundations().getFoundationList()[i].isEmpty()) {
                foundationBuilder.append("<img class = foundation src='" + "/images/spider-solitaire/hresko/KS.png" + "' width = 130px height=150px>");
            } else {
                foundationBuilder.append("<img class = foundation src='" + "/images/spider-solitaire/hresko/foundation_spot.png" + "' width = 130px height=150px>");
            }
        }
        return foundationBuilder.toString();
    }

    public String renderDeck() {

        StringBuilder deckBuilder = new StringBuilder();

        int size = 0;

        for (int row = 0; row < deck.getTableau().getColumns().length; row++) {
            if (size < deck.getTableau().getColumns()[row].size()) {
                size = deck.getTableau().getColumns()[row].size();
            }
        }

        for (int i = 0; i < deck.getTableau().getColumns().length; i++) {
            for (int j = 0; j < deck.getTableau().getColumns()[i].size(); j++) {
                if (j == deck.getTableau().getColumns()[i].size() - 1) {
                    deck.getTableau().getColumns()[i].get(j).setFlipped(true);
                }
            }
        }

        if (deck.getGameState() == 0) {
            for (int i = 0; i < size; i++) {

                deckBuilder.append("<div class='tableau-row row' style = \"z-index : ").append(i + 1).append("; padding-top :").append(i * 30).append("px;\">");
                for (int j = 0; j < 10; j++) {

                    if (i == 0 && deck.getTableau().getColumns()[j].size() == 0) {
                        deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/foundation_spot.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                    }

                    if (deck.getTableau().getColumns()[j].size() > i && deck.getTableau().getColumns()[j].get(i).isFlipped()) {
                        switch (deck.getTableau().getColumns()[j].get(i).getRank()) {
                            case 1:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/AS.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 2:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/2S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 3:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/3S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 4:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/4S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 5:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/5S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 6:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/6S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 7:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/7S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 8:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/8S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 9:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/9S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 10:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/10S.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 11:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/JS.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 12:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/QS.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                            case 13:
                                deckBuilder.append("<img id = ").append(j).append(i).append(" class = cards-items onclick='").append("replace(this.id);' ").append("src='").append("/images/spider-solitaire/hresko/KS.png").append("' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                                break;
                        }
                    } else {
                        if (i < deck.getTableau().getColumns()[j].size()) {
                            deckBuilder.append("<img class = cards-items src='" + "/images/spider-solitaire/hresko/back.png" + "' width = 110px height=150px style = \"left : ").append((j + 1) * 130).append("px;\">");
                        }
                    }
                }
                deckBuilder.append("</div>");
            }
        }
        return deckBuilder.toString();
    }

    public Deck getDeck() {
        return deck;
    }
}

