import controller.Controller;
import exceptions.WrongPathToFileException;
import model.Toy;
import utils.Tuner;
import view.Viewer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Viewer viewer = new Viewer();
        Tuner tuner = new Tuner();
        Controller controller = new Controller(viewer, tuner);
        try {
            controller.run();
        } catch (WrongPathToFileException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("ОШИБКА ВВОДА-ВЫВОДА. ПРОГРАММА ЗАВЕРШАЕТ РАБОТУ. ");
            return;
        }
    }
}