package model;

import java.util.Objects;

public class Lot {
    private int lotNumber;
    private Toy toy;

    public Lot(int lotNumber, Toy toy) {
        this.lotNumber = lotNumber;
        this.toy = toy;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public Toy getToy() {
        return toy;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }

    @Override
    public String toString() {
        return String.format("ЛОТ %-5d: %50s", lotNumber, toy);
    }

    @Override
    public int hashCode() {
        int result = 19;
        int base = 31;
        result = result*base + lotNumber;
        result = result*base + toy.hashCode();
        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot lot)) return false;
        return ((Toy)o).hashCode() == hashCode();
    }
}
