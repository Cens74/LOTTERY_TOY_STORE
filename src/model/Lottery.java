package model;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Lottery {
    public int qtyOfParticipants; // количество участников лотереи
    public Set<Lot> lotSet;  // игрушки, участвующих в лотерее
    public Lottery() {
        this.lotSet = new HashSet<>();
        this.qtyOfParticipants = 0;
    }

    /**
     * Формат файла, описывающего набор игрушек, участвующих в лотерее:
     * В одной строке через пробел перечислены данные игрушек-участников
     * На основании веса этих игрушек и числа участников лотереи вычисляется количество экземпляров каждой игрушки,
     * участвующее в розыгрыше, чтобы частота выпадения того или иного вида игрушек соответствовала весу.
     * @param fileName
     * @throws IOException
     */
    public Lottery (String fileName) throws IOException {
        this.lotsSet = new HashSet<>();
        File file = new File(fileName);

        try (FileReader fr = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] toyFields = line.split(",");
                Toy nextToy = new Toy(toyFields[]);
                lines.add(line);
            }
            String[] toyParameters = line.split(",");
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

             BufferedReader reader = new BufferedReader(fr);) {
            String line = reader.readLine();
            if (line != null) {
                String[] toyParameters = line.split(",");
                Toy nextToy = new Toy();
                lines.add(line);
            }
            while (line != null) {
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
