package sk.tuke.gamestudio.spidersolitaire.deck;

import sk.tuke.gamestudio.spidersolitaire.card.Card;
import sk.tuke.gamestudio.spidersolitaire.services.CommentServiceJDBC;
import sk.tuke.gamestudio.spidersolitaire.services.RatingServiceJDBC;
import sk.tuke.gamestudio.spidersolitaire.services.ScoreServiceJDBC;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents list of runs
 */

public class Foundations {

    private List<Card>[] foundationList;

    @SuppressWarnings("unchecked")
    Foundations() {
        List<Card> foundation1 = new ArrayList<>();
        List<Card> foundation2 = new ArrayList<>();
        List<Card> foundation3 = new ArrayList<>();
        List<Card> foundation4 = new ArrayList<>();
        List<Card> foundation5 = new ArrayList<>();
        List<Card> foundation6 = new ArrayList<>();
        List<Card> foundation7 = new ArrayList<>();
        List<Card> foundation8 = new ArrayList<>();
        foundationList = new List[]{
                foundation1, foundation2, foundation3, foundation4, foundation5, foundation6, foundation7, foundation8
        };
    }

    // getters and setters

    public List<Card>[] getFoundationList() {
        return foundationList;
    }
}
