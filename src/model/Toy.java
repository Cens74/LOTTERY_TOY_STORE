package model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class Toy {
    private String toyType;
    private String toyName;

    public Toy(String toyType, String toyName) {
        this.toyType = toyType;
        this.toyName = toyName;
    }
//    private int assignUniqueNumber () {
//        final int countBase = 331;
//        Random random = new Random();
//        int result =
//        int result = toyID*countBase;
//        result = (int) (result*countBase + currentTimeMillis()%Integer.MAX_VALUE);
//        result = (result*countBase + toyName.hashCode())>>>1;
//        return result;
//    }
    public String getToyType() {
        return toyType;
    }

    public String getToyName() {
        return toyName;
    }


    public void setToyType (String toyType) {
        this.toyType = toyType;
    }
    public void setToyName (String toyType) {
        this.toyName = toyName;
    }
    // Игрушки считаются равными, если у них одинаковые ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Toy toy)) return false;
        return getToyType().equals(((Toy)o).getToyType()) && getToyName().equals(((Toy)o).getToyName());
    }
    @Override
    public int hashCode() {
        int result = 19;
        final int hashBase = 31;

        result = result* hashBase + toyType.hashCode();
        result = result* hashBase + toyName.hashCode();
        return result>>>3;
    }
    public String toString() {
        return String.format("ИГРУШКА <Тип = %-20s, Название = %-50s>", toyType, toyName);
    }
}
