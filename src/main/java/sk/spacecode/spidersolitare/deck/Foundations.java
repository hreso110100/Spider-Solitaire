package sk.spacecode.spidersolitare.deck;

import sk.spacecode.spidersolitare.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Foundations {

    private List<Card>[] foundationList;
    private int foundationFilled;
    private int foundationIndex;

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

    public List<Card>[] getFoundationList() {
        return foundationList;
    }

    public int getFoundationFilled() {
        return foundationFilled;
    }

    public void setFoundationFilled(int foundationFilled) {
        this.foundationFilled = foundationFilled;
    }

    public int getFoundationIndex() {
        return foundationIndex;
    }

    public void setFoundationIndex(int foundationIndex) {
        this.foundationIndex = foundationIndex;
    }

    public void addRunAndCheckWin(List<Card> run) {
        foundationList[foundationIndex++].addAll(run);
        foundationFilled++;
        if (foundationFilled == 8) {
            System.out.println("CONGRATULATIONS, YOU ARE WINNER !!!");
        }
    }
}
