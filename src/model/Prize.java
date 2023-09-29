package model;

import java.util.Comparator;

public class Prize implements Comparable<Prize>, Comparator<Prize> {
    private int prizeID;
    public Toy toy;
    public int priority;

    public Prize(int prizeID, Toy toy, int rangeForPriorities) {
        this.prizeID = prizeID;
        this.toy = toy;
        this.priority = (int) Math.round(Math.random()*rangeForPriorities);
    }

    public Toy getToy() {
        return toy;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }

    @Override
    public String toString() {
        return String.format("(приоритет %d): %s", this.priority, this.toy);
    }

    @Override
    public int compareTo(Prize o) {
        return this.priority-o.priority;
    }

    @Override
    public int compare(Prize o1, Prize o2) {
        return o1.priority-o2.priority;
    }
}
