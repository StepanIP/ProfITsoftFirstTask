package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private final static String INPUT_PATH = "src/main/resources/json";
    private final static String OUTPUT_PATH = "src/main/resources/xml/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть поле по якому буде проводитись пошук: ");
        String field = scanner.nextLine();

        JSONParser jsonParser = new JSONParser(1,1);
        try {
            XmlConverter.convertToXml(jsonParser.parseJsonFiles(INPUT_PATH, field), OUTPUT_PATH, field);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
