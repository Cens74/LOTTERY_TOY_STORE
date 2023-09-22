package controller;

import exceptions.WrongInputDataException;
import exceptions.WrongPathToFileException;
import model.Lottery;
import model.Toy;
import model.ToyStore;
import view.Viewer;
import utils.Tuner;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class Controller {
    private final Viewer viewer;
    private final String DEFAULT_PATH = ".";
    private final String DEFAULT_TOY_STORE_FILE_NAME = "toyStore.txt";
    private final int QTY_OF_ATTEMPTS = 3;
//    private final Lottery lottery;
//    private final ToyStore store;
//    private final Tuner tuner;

    public Controller(Viewer viewer, Lottery lottery, ToyStore store) {
        this.viewer = viewer;
//        this.lottery = lottery;
//        this.store = store;
//        this.tuner = tuner;
    }
    public Controller(Viewer viewer) {
        this.viewer = viewer;
    }

    public void run() throws WrongPathToFileException {
        String message = "НАЧИНАЕМ ПОДГОТОВКУ К РОЗЫГРЫШУ В МАГАЗИНЕ ИГРУШЕК...";
        viewer.infoMessage(message);
        message = "НАЧИНАЕМ ПОДГОТОВКУ К РОЗЫГРЫШУ В МАГАЗИНЕ ИГРУШЕК...";
//        Toy toy = new Toy("1 Машинка Вольво-615");
//        System.out.println(toy.toString());
        message = "СКОЛЬКО ДЕТЕЙ БУДЕТ УЧАСТВОВАТЬ В РОЗЫГРЫШЕ? ";
        viewer.promptMessage(message);
        Scanner scanner = new Scanner(System.in);
        int numberOfParticipants = 0;
        int i;
        for (i = 0; i < QTY_OF_ATTEMPTS; i++) {
            if (scanner.hasNextInt()) {
                numberOfParticipants = scanner.nextInt();
                break;
            } else {
                message = "ВВЕДИТЕ ЦЕЛОЕ ПОЛОЖИТЕЛЬНОЕ ЧИСЛО";
                viewer.promptMessage(message);
            }
        }
        if (i == QTY_OF_ATTEMPTS) throw new WrongInputDataException("ОШИБКА ВВОДА. ПРОГРАММА ЗАВЕРШАЕТ СВОЮ РАБОТУ...");
        message = String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ %d УЧАСТНИКА(ОВ).", numberOfParticipants);
        viewer.infoMessage(message);
        message = "ВВЕДИТЕ ПУТЬ К КАТАЛОГУ, В КОТОРОМ НАХОДИТСЯ .TXT-ФАЙЛ С НОМЕНКЛАТУРОЙ МАГАЗИНА\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ТЕКУЩИЙ РАБОЧИЙ КАТАЛОГ ";
        String path = viewer.getUserInput(message);
        if (path == "") {
            try {
                path = new File(".").getCanonicalPath();
            } catch (IOException e) {
                throw new WrongPathToFileException("НЕКОРРЕКТНЫЙ ПУТЬ К ФАЙЛУ С НОМЕНКЛАТУРОЙ МАГАЗИНА");
            }
        }
        System.out.println("Path = " + path);
        message = "ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ), В КОТОРОМ ПЕРЕЧИСЛЕНА НОМЕНКЛАТУРА МАГАЗИНА\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ toyStore.txt";
        String fileName = viewer.getUserInput(message);
        if (fileName != "") {
            fileName = fileName.strip();
            if (!fileName.endsWith(".txt")) fileName = fileName.concat(".txt");
        } else {
            fileName = DEFAULT_TOY_STORE_FILE_NAME;
        }
        String fullFileName = String.format("%s\\%s", path, fileName);
        System.out.printf("Полное имя файла с номенклатурой магазина игрушек = %s\n", fullFileName);


//        String fullFileName = viewer.getUserInput(message);
//        String fileNAme =
//        try {
//            toyStoreFileName = (File)scanner.nextLine());
//        } catch (IOException e) {
//
//        }
    }
}
