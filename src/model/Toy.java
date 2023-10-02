package model;

import exceptions.WrongInputDataException;

import java.util.Comparator;

public class Toy implements Comparable<Toy>, Comparator<Toy> {
    private int toyID;
    private String toyType;
    private String toyName;

    public Toy() {
        this.toyID = 0;
        this.toyType = "";
        this.toyName = "";
    }
    public Toy(int toyID, String toyType, String toyName) {
        this.toyID = toyID;
        this.toyType = toyType;
        this.toyName = toyName;
    }

    /**
     * Конструктор объекта класса Toy из строки, в которой через пробел перечислены:
     * ID - целое положительное число
     * Type - строка из русских букв и дефиса
     * Name - строка из русских букв, дефиса и двойных кавычек
     * @param toyParameters
     * @throws WrongInputDataException
     */
    public Toy(String toyParameters) throws WrongInputDataException {
        String[] fields = toyParameters.split(" ");
        if (fields.length != 3) {
            throw new WrongInputDataException("НЕВЕРНОЕ КОЛИЧЕСТВО ПОЛЕЙ В СТРОКЕ, ОПИСЫВАЮЩЕЙ ИГРУШКУ.");
        }
        try {
            this.toyID = Integer.parseInt(fields[0]);
        } catch (NumberFormatException e)  {
            throw new WrongInputDataException("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ В СТРОКЕ, ОПИСЫВАЮЩЕЙ ИГРУШКУ.");
        }
        if (fields[1].matches("^[а-яА-Я., <>\\-_()]*$")) {
            this.toyType = fields[1];
        } else throw new WrongInputDataException("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ В СТРОКЕ, ОПИСЫВАЮЩЕЙ ИГРУШКУ:\n ТИП - "+
                                                "ЭТО СТРОКА БЕЗ ИЗ РУССКИХ БУКВ, ПРОБЕЛОВ, ТОЧЕК, ЗАПЯТЫХ И (ВОЗМОЖНО) ДЕФИСОВ," +
                                                "ЗАКЛЮЧЕННАЯ В УГЛОВЫЕ СКОБКИ");
        if (fields[2].matches("^[а-яА-Я0-9\\-\",. _<>()]*$")) {
            this.toyName = fields[2];
        } else throw new WrongInputDataException("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ В СТРОКЕ, ОПИСЫВАЮЩЕЙ ИГРУШКУ:\n"+
                                "НАЗВАНИЕ - ЭТО СТРОКА ИЗ РУССКИХ БУКВ, ЦИФР, ПРОБЕЛОВ, ТОЧЕК, ЗАПЯТЫХ И (ВОЗМОЖНО) ДЕФИСОВ В УГЛОВЫХ СКОБКАХ");
    }

    public String getToyType() {
        return toyType;
    }

    public String getToyName() {
        return toyName;
    }

    public int getToyID() {
        return toyID;
    }
    public void setToyID(int toyID) {
        this.toyID = toyID;
    }

    public void setToyType (String toyType) {
        this.toyType = toyType;
    }
    public void setToyName (String toyType) {
        this.toyName = toyName;
    }

    @Override
    public int compare(Toy o1, Toy o2) {
        int o1Hash = o1.hashCode();
        int o2Hash = o2.hashCode();
        if (o1Hash < o2Hash) return -1;
        else if (o1Hash > o2Hash) return 1;
        return 0;
    }
    @Override
    public boolean equals(Object o) throws WrongInputDataException {
        if (this == o) return true;
        if (!(o instanceof Toy toy)) return false;
        if (toyID == ((Toy)o).getToyID()) {
            if (toyType.equals(((Toy)o).getToyType()) && toyName.equals(((Toy)o).getToyName())) {
                return true;
            } else {
                throw new WrongInputDataException("ОШИБКА В ИСХОДНЫХ ДАННЫХ: РАЗНЫЕ ИГРУШКИ НЕ МОГУТ ИМЕТЬ ОДИНАКОВЫЕ id!!!");
            }
        } else if (toyType.equals(((Toy)o).getToyType()) && toyName.equals(((Toy)o).getToyName())) {
            throw new WrongInputDataException("ОШИБКА В ИСХОДНЫХ ДАННЫХ: ОДИНАКОВЫЕ ИГРУШКИ НЕ МОГУТ ИМЕТЬ РАЗНЫЕ id!!!");
        } else return false;
    }
    @Override
    public int hashCode() {
        int result = 31;
        final int hashBase = 19;
        result = result* hashBase + toyType.hashCode();
        result = result* hashBase + toyName.hashCode();
        return (int) (result*(Math.round(Math.random()*19))>>>3);
    }
    public String toString() {
        return String.format("ИГРУШКА %-5d: Тип = %-32s, Название = %-47s", toyID, toyType, toyName);
    }
    @Override
    public int compareTo(Toy o) {
        return 0;
    }

}
