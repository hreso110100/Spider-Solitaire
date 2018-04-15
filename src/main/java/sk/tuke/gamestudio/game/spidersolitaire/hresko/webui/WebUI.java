package sk.tuke.gamestudio.game.spidersolitaire.hresko.webui;

import sk.tuke.gamestudio.game.spidersolitaire.hresko.card.Card;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.deck.Deck;

public class WebUI {

    private Deck deck;

    public WebUI() {
        deck = new Deck();
    }

    public int getScore() {
        return deck.getScore();
    }

    public int getSteps() {
        return deck.getStepCounter();
    }

    public void processCommand(String command) {
        if (command != null) {
            switch (command) {

                case "take": {
                    deck.takeCardsFromStock(deck.getTableau().getColumns());
                    break;
                }
                case "restart": {
                    deck = new Deck();
                    break;
                }
            }
        }
    }

    public String renderStock() {
        StringBuilder stockBuilder = new StringBuilder();

        if (Deck.removeItemFromArrayIndex <= 49) {
            stockBuilder.append("<a href='" + "?command=take" + "'>\n");
            stockBuilder.append("<img src='" + "/images/spider-solitaire/hresko/back.png" + "' width = 110px height=150px>");
            stockBuilder.append("</a>");
        } else {
            stockBuilder.append("<img src='" + "/images/spider-solitaire/hresko/empty_stock.png" + "' width = 110px height=150px>");
        }

        return stockBuilder.toString();
    }

    public String renderFoundations() {
        StringBuilder foundationBuilder = new StringBuilder();

        for (int i = 0; i < deck.getFoundations().getFoundationList().length; i++) {
            if (!deck.getFoundations().getFoundationList()[i].isEmpty()) {
                foundationBuilder.append("<img class = foundation src='" + "/images/spider-solitaire/hresko/KS.png" + "' width = 130px height=150px>");
            } else {
                foundationBuilder.append("<img class = foundation src='" + "/images/spider-solitaire/hresko/foundation_spot.png" + "' width = 130px height=150px>");
            }
        }
        return foundationBuilder.toString();
    }
}
