import controller.Controller;
import exceptions.WrongPathToFileException;
import model.Toy;
import view.Viewer;

public class Main {

    public static void main(String[] args) {
        Viewer viewer = new Viewer();
        Controller controller = new Controller(viewer);
        try {
            controller.run();
        } catch (WrongPathToFileException e) {
            System.out.println("ФАТАЛЬНАЯ ОШИБКА. ПРОГРАММА ЗАВЕРШАЕТ РАБОТУ. ");
            return;
        }
    }
}