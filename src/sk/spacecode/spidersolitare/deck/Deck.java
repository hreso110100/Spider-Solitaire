package sk.spacecode.spidersolitare.deck;

import com.sun.deploy.util.ArrayUtil;
import sk.spacecode.spidersolitare.card.Card;
import sk.spacecode.spidersolitare.card.Pack;

import java.util.*;

public class Deck {
    private Foundations foundations;
    private Stock stock;
    private Tableau tableau;
    private Card card;
    private Pack pack;

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

    public void game() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input) {
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
            }
        }

    }

    public void hint() {
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
                if (j == tableau.getColumns()[i].size() - 1) {
                    System.out.print(tableau.getColumns()[i].get(j).getRank() + " ");
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }

    private void takeCardsFromStock(List[] columns) {

        int removeIndex = 0;

        for (int i = 0; i < columns.length; i++) {
            if (columns.length == 10 && stock.getStock().length > 0) {
                tableau.getColumns()[i].add(stock.getStock()
            } else {
                System.out.println("CANNOT SERVE CARDS FROM STOCK !!!");
            }
        }
    }
}
