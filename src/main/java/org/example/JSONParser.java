package org.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

public class JSONParser {
    private final ExecutorService executorService;
    private static final Logger logger = LoggerFactory.getLogger(XmlConverter.class);

    public JSONParser(int initialThreadAmount, int maxThreadAmount) {
        this.executorService = new ThreadPoolExecutor(initialThreadAmount, maxThreadAmount, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public Map<String, Integer> parseJsonFiles(String directoryPath, String parseField) {
        Map<String, Integer> result = new TreeMap<>();
        try {
            Files.list(Path.of(directoryPath))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> executorService.submit(() -> {
                        try {
                            parseFile(filePath.toString(), parseField, result);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            return sortByValue(result);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseFile(String file, String parseField, Map<String, Integer> result) throws IOException {
        try (InputStream in = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(in);
             JsonParser jParser = new JsonFactory().createParser(reader)) {

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
            logger.error("An error occurred while parsing the JSON file: {}", e.getMessage());
            throw e;
        }
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
