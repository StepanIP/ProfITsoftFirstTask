package org.example;

import java.io.IOException;
import java.util.Scanner;

/**
 * Клас Main містить метод main, який використовується для виконання програми для перетворення JSON у XML.
 */
public class Main {

    /**
     * Шлях до папки з даними для парсингу
     */
    private final static String INPUT_PATH = "src/main/resources/json";
    /**
     * Шлях де буде збережено статистику в форматі xml
     */
    private final static String OUTPUT_PATH = "src/main/resources/xml/";

    /**
     * Метод main виконує програму для перетворення JSON у XML.
     *
     * @param args Аргументи командного рядка.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть поле, за яким буде проводитись пошук: ");
        String field = scanner.nextLine();

        JSONParser jsonParser = new JSONParser(1, 1);
        try {
            XmlConverter.convertToXml(jsonParser.parseJsonFiles(INPUT_PATH, field), OUTPUT_PATH, field);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
