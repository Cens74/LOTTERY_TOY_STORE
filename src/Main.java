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
            System.out.println("ФАТАЛЬНАЯ ОШИБКА. ПРОГРАММА ЗАВЕРШАЕТ РАБОТУ. ");
            return;
        } catch (IOException e) {
            System.out.println("ФАТАЛЬНАЯ ОШИБКА. ПРОГРАММА ЗАВЕРШАЕТ РАБОТУ. ");
            return;
        }
    }
}