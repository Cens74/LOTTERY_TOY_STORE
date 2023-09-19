package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class Lottery {
    public Set<Toy> lotsSet;  // множ-во игрушек, участвующих в лотерее
    public Lottery() {

        this.lotsSet = new HashSet<>();
    }
    public Lottery (String fileName) {
//
        File file = new File(fileName);
        FileReader fr;
        try (fr = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            if (line != null) {
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
