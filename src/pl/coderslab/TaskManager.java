package pl.coderslab;
/**
 * created by Dariusz Szpakowski on 30.05.2020
 */
import org.apache.commons.lang3.math.NumberUtils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    private static List<String> csvList = new ArrayList<>();

    public static void main(String[] args) {
        loading();
        menu();
    }

    public static void loading() {

        String csvFile = "tasks.csv";
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(cvsSplitBy);
                csvList.add(line);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu() {
        String[] options = {"add", "remove", "list", "exit"};
        System.out.print((ConsoleColors.YELLOW_BOLD));
        for (String menu : options) {
            System.out.println(menu);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print((ConsoleColors.RESET));
        System.out.print("Wpisz jedną z powyższych opcji: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "exit":
                exitApp();
                break;
            case "add":
                addItem();
                break;
            case "remove":
                removeItem();
                break;
            case "list":
                listTasks();
                break;
            default:
                System.out.println("Wpisz poprawną opcję");
                menu();
        }
    }

    public static void addItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj treść zadania: ");
        String newTask = scanner.nextLine();
        System.out.println("Podaj datę zadania w formacie yyyy-mm-dd: ");
        String addDate = scanner.nextLine();
        String newAddDate = addDate.replaceAll("-", "0");
        while (!NumberUtils.isParsable(newAddDate)) {
            System.err.println("Wpisz poprawny format daty.");
            addDate = scanner.nextLine();
            newAddDate = addDate.replaceAll("-", "0");
        }
        System.out.println("Waga zadania: true / false: ");
        String priority = scanner.nextLine();
        String s = newTask + "," + addDate + "," + priority;
        csvList.add(s);
        menu();
    }

    public static void removeItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Którą linię chcesz usunąć? ");
        try {
            int line = scanner.nextInt();
            while (line < 0) {
                System.err.println("Niepoprawna wartość. Spróbuj ponownie: ");
                line = scanner.nextInt();
            }
            csvList.remove(line);
            System.out.print((ConsoleColors.BLUE_UNDERLINED));
            System.out.println("Pomyślnie usunięto zadanie.");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Nie ma takiej wartości.");
            removeItem();
        } catch (InputMismatchException e) {
            System.err.println("Podano niepoprawną wartość.");
            removeItem();
        } finally {
            menu();
        }
    }

    public static void listTasks() {
        int i = 0;
        for (String s : csvList) {
            String tmp = s.replace(",", " ");
            System.out.println(i + ": " + tmp);
            i++;
        }
        menu();
    }

    public static void exitApp() {
        Path save = Paths.get("tasks.csv");
        try {
            Files.write(save, csvList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print((ConsoleColors.BLUE_UNDERLINED));
        System.out.println("Program został zakończony");
        Runtime.getRuntime().exit(0);
    }
}



