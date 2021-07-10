package player;

import gameUtil.AliveTroop;
import gameUtil.Card;

import java.util.ArrayList;

public class Status {
    private ArrayList<Card> cards;
    private ArrayList<Card> cardsDeskInUse;
    private ArrayList<AliveTroop> aliveTroops;
    private int trophy;
    private int level;
    private int elixirs;

    public Status(ArrayList<Card> cardsDeskInUse,
                  int trophy,
                  int level,
                  int elixirs)
    {
        this.cardsDeskInUse = cardsDeskInUse;
        this.trophy = trophy;
        this.level = level;
        this.elixirs = elixirs;
    }

    public ArrayList<AliveTroop> getAliveTroops() {
        return aliveTroops;
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
}
