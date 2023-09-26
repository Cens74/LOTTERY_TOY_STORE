package model;

import exceptions.WrongInputDataException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class Toy {
//    private static Integer lastUsedToyID = 0;
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
        if (fields[1].matches("^[а-яА-Я., <>\\-()]*$") && fields[1].endsWith(">") && fields[1].startsWith("<")) {
            this.toyType = fields[1];
        } else throw new WrongInputDataException("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ В СТРОКЕ, ОПИСЫВАЮЩЕЙ ИГРУШКУ:\n ТИП - "+
                                                "ЭТО СТРОКА БЕЗ ИЗ РУССКИХ БУКВ, ПРОБЕЛОВ, ТОЧЕК, ЗАПЯТЫХ И (ВОЗМОЖНО) ДЕФИСОВ," +
                                                "ЗАКЛЮЧЕННАЯ В УГЛОВЫЕ СКОБКИ");
        if (fields[2].matches("^[а-яА-Я0-9\\-\",.<>()]*$")&& fields[2].startsWith("<") && fields[2].endsWith(">")) {
            this.toyName = fields[2];
        } else throw new WrongInputDataException("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ В СТРОКЕ, ОПИСЫВАЮЩЕЙ ИГРУШКУ:\n"+
                                "НАЗВАНИЕ - ЭТО СТРОКА ИЗ РУССКИХ БУКВ, ЦИФР, ПРОБЕЛОВ, ТОЧЕК, ЗАПЯТЫХ И (ВОЗМОЖНО) ДЕФИСОВ В УГЛОВЫХ СКОБКАХ");
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
    // Игрушки считаются равными, если у них одинаковые ID
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
        int result = 19;
        final int hashBase = 31;

        result = result* hashBase + toyType.hashCode();
        result = result* hashBase + toyName.hashCode();
        return result>>>3;
    }
    public String toString() {
        return String.format("ИГРУШКА %-5d: <Тип = %-20s, Название = %-50s>", toyID, toyType, toyName);
    }
}
