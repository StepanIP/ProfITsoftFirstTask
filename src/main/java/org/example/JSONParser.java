package org.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class JSONParser {
    public Map<String, Integer> parseJsonFiles(String directoryPath, String parseField) {
        JsonFactory jsonFactory = new JsonFactory();
        Map<String, Integer> result = new TreeMap<>();

        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile).forEach(filePath -> {
                try (JsonParser jParser = jsonFactory.createParser(new String(Files.readAllBytes(filePath)))) {
                    while (jParser.nextToken() != null) {
                        String fieldName = jParser.getCurrentName();
                        if (parseField.equals(fieldName)) {
                            jParser.nextToken();
                            jParser.nextToken();
                            String fieldValue = jParser.getText();
                            while (!Objects.equals(fieldValue, "]")) {
                                if (result.containsKey(fieldValue)) {
                                    result.put(fieldValue, result.get(fieldValue) + 1);
                                } else {
                                    result.put(fieldValue, 1);
                                }
                                jParser.nextToken();
                                fieldValue = jParser.getText();
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sortByValue(result);
    }

    private static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());

        entries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
}
