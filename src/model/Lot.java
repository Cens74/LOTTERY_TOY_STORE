package model;

import java.util.Objects;

public class Lot {
//    private int lotNumber;
    private NomenclatureItem item;
    private int weight;

    public Lot(int lotNumber, NomenclatureItem item, int weight) {
//        this.lotNumber = lotNumber;
        this.item = item;
        this.weight = weight;
    }

//    public int getLotNumber() {
//        return lotNumber;
//    }

//    public void setLotNumber(int lotNumber) {
//        this.lotNumber = lotNumber;
//    }

    public NomenclatureItem getItem() {
        return item;
    }

    public void setItem(NomenclatureItem item) {
        this.item = item;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("ЛОТ № %-5d: %50s",
//                lotNumber,
                item.getToy().toString());
    }
//    @Override
//    public int hashCode() {
//        int result = 19;
//        int base = 31;
//        result = result*base + lotNumber;
//        result = result*base + toy.hashCode();
//        return result;
//    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot lot)) return false;
        return ((Lot)o).hashCode() == this.hashCode();
    }
}
