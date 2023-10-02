package model;

public class Lot {
    private int nomenclatureID;
    private int weight;
    public Lot() {
        this.nomenclatureID = 0;
        this.weight = 0;
    }

    public Lot(int id, int weight) {
        this.nomenclatureID = id;
        this.weight = weight;
    }

    public int getNomenclatureID() {

        return this.nomenclatureID;
    }

    public void setItem(int id) {
        this.nomenclatureID = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("ЛОТ: ID ТОВАРА = %-6d, ВЕС = %-4d", nomenclatureID, weight);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot lot)) return false;
        return ((Lot)o).hashCode() == this.hashCode();
    }
}
