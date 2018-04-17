package sk.tuke.gamestudio.game.spidersolitaire.hresko.webui;

import sk.tuke.gamestudio.game.spidersolitaire.hresko.deck.Deck;

public class WebUI {

    private Deck deck;
    int parsedSourceRow;

    public WebUI() {
        deck = new Deck();
    }

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
                    deck.takeCardsFromStock(deck.getTableau().getColumns());
                    break;
                }
                case "move": {

                    parsedSourceRow = Integer.parseInt(sourceRow);
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

    public String renderStock() {
        StringBuilder stockBuilder = new StringBuilder();

        if (Deck.removeItemFromArrayIndex <= 49) {
            stockBuilder.append("<a id = stock-a href='" + "?command=take" + "'>\n");
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

                    if (deck.getTableau().getColumns()[j].size() == i) {
                        deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/empty.png" + "' width = 130px height=150px>");
                    } else if (deck.getTableau().getColumns()[j].size() > i && deck.getTableau().getColumns()[j].get(i).isFlipped()) {
                        switch (deck.getTableau().getColumns()[j].get(i).getRank()) {
                            case 1:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/AS.png" + "' width = 130px height=150px>");
                                break;
                            case 2:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/2S.png" + "' width = 130px height=150px>");
                                break;
                            case 3:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/3S.png" + "' width = 130px height=150px>");
                                break;
                            case 4:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/4S.png" + "' width = 130px height=150px>");
                                break;
                            case 5:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/5S.png" + "' width = 130px height=150px>");
                                break;
                            case 6:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/6S.png" + "' width = 130px height=150px>");
                                break;
                            case 7:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/7S.png" + "' width = 130px height=150px>");
                                break;
                            case 8:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/8S.png" + "' width = 130px height=150px>");
                                break;
                            case 9:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/9S.png" + "' width = 130px height=150px>");
                                break;
                            case 10:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/10S.png" + "' width = 130px height=150px>");
                                break;
                            case 11:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/JS.png" + "' width = 130px height=150px>");
                                break;
                            case 12:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/QS.png" + "' width = 130px height=150px>");
                                break;
                            case 13:
                                deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/KS.png" + "' width = 130px height=150px>");
                                break;
                        }
                    } else if (deck.getTableau().getColumns()[j].size() > i) {
                        deckBuilder.append("<img src='" + "/images/spider-solitaire/hresko/back.png" + "' width = 130px height=150px>");
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
