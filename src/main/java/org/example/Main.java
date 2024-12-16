package org.example;

import java.io.*;
import java.util.*;

public class Main {
    private static List<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        try {
            String inputFile = "input.txt";
            String outputFile = "output.txt";

            Thread mainThread = new Thread(() -> {
                numbers = readFromFile(inputFile);
                System.out.println("Исходный массив: " + numbers);
            });

            mainThread.start();
            mainThread.join();

            System.out.println("Введите порядок сортировки (1 для возрастания, 2 для убывания):");
            Scanner scanner = new Scanner(System.in);
            String order = scanner.nextLine().trim().toLowerCase();

            Thread sortThread = new Thread(() -> {
                if (order.equals("1")) {
                    numbers.sort(Comparator.naturalOrder());
                } else if (order.equals("2")) {
                    numbers.sort(Comparator.reverseOrder());
                } else {
                    System.out.println("Некорректный порядок сортировки. Выполнена сортировка по возрастанию.");
                    numbers.sort(Comparator.naturalOrder());
                }
                System.out.println("Сортировка завершена: " + numbers);
            });

            sortThread.start();
            sortThread.join();

            writeToFile(outputFile, numbers);
            System.out.println("Отсортированный массив записан в файл: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> readFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] parts = reader.readLine().split(" ");
            for (String part : parts) {
                numbers.add(Integer.parseInt(part));
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла.");
            e.printStackTrace();
        }
        return numbers;
    }

    private static void writeToFile(String filename, List<Integer> numbers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int number : numbers) {
                writer.write(number + " ");
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи файла.");
            e.printStackTrace();
        }
    }
}
