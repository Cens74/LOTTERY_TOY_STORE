package model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class Toy {
    private final int toyID;
    private final String toyName;
    private int toyWeight;
    private final int toyUniqueNumber;

    public Toy(int toyID, String toyName, int toyWeight) {
        this.toyID = toyID;
        this.toyName = toyName;
        this.toyWeight = toyWeight;
        this.toyUniqueNumber = assignUniqueNumber();
    }
    private int assignUniqueNumber () {
        final int countBase = 331;
        Random random = new Random();
        int result =
        int result = toyID*countBase;
        result = (int) (result*countBase + currentTimeMillis()%Integer.MAX_VALUE);
        result = (result*countBase + toyName.hashCode())>>>1;
        return result;
    }
    public int getToyID() {
        return toyID;
    }

    public String getToyName() {
        return toyName;
    }

    public int getToyWeight() {
        return toyWeight;
    }

    public void setToyWeight(int toyWeight) {
        this.toyWeight = toyWeight;
    }
    // Игрушки считаются равными, если у них одинаковые ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Toy toy)) return false;
        return getToyID() == toy.getToyID();
    }
    @Override
    public int hashCode() {
        int result = 19;
        final int hashBase = 31;

        result = result* hashBase + toyID;
        result = result* hashBase + toyUniqueNumber;
        return result>>>3;
    }
    public String toString() {
        return String.format("ИГРУШКА %10d: ID = %5d, NAME = %-50s, WEIGHT= %5d", toyUniqueNumber, toyID, toyName, toyWeight);
    }
}
