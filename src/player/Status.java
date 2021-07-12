package player;

import gameUtil.AliveTroop;
import gameUtil.Card;
import gameUtil.CardsCollection;
import javafx.geometry.Point2D;

import java.io.Serializable;
import java.util.ArrayList;

public class Status implements Serializable {
    private ArrayList<Card> cards;
    private final ArrayList<Card> cardsDeskInUse;
    transient private ArrayList<AliveTroop> aliveAllyTroops;
    transient private ArrayList<AliveTroop> aliveEnemyTroops;
    private final String username;
    private int trophy;
    private int level;
    private int XP;
    private int elixirs;

    public Status(ArrayList<Card> cardsDeskInUse,
                  int trophy,
                  int level,
                  int elixirs,
                  String username)
    {
        this.cardsDeskInUse = cardsDeskInUse;
        this.trophy = trophy;
        this.level = level;
        this.elixirs = elixirs;
        this.username = username;
    }

    public Status(String username) {
        this.username = username;
        this.trophy = 0;
        this.elixirs = 0;
        this.level = 1;
        this.XP = 0;
        aliveAllyTroops = new ArrayList<>();
        cards = CardsCollection.getCardSet(1);
        cardsDeskInUse = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            cardsDeskInUse.add(cards.get(i));
        resetTowers();
    }

    public ArrayList<AliveTroop> getAliveAllyTroops() {
        return aliveAllyTroops;
    }

    public void resetTowers() {
        aliveAllyTroops.clear();
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getArcherTowers().get(level - 1), new Point2D(21.0, 473)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getArcherTowers().get(level - 1), new Point2D(324.0, 473)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getKingTowers().get(level - 1), new Point2D(166.0, 567)));
    }

    public int getTrophy() {
        return trophy;
    }

    public void increaseTrophy(int num) {
        trophy += num;
    }

    public int getLevel() {
        return level;
    }

    public int getElixirs() {
        return elixirs;
    }

    public void increaseElixirs() {
        elixirs++;
    }

    public void increaseXP(int xp) {
        this.XP += xp;
    }

    public void increaseLevel() {
        XP = 0;
        level += 1;
    }

    public String getUsername() { return username; }

    public ArrayList<Card> getCardsDeskInUse() {
        return cardsDeskInUse;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
