import org.example.JSONParser;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Клас JSONParserTest містить юніт-тести для класу JSONParser.
 */
class JSONParserTest {
    private final String THREAD_DIRECTORY = "src/test/resources/thread/";

    private final String TEST_DIRECTORY = "src/test/resources/data/";
    private final String PARSE_FIELD = "genres";

    /**
     * Метод testParseJsonFiles_Success_SingleThread перевіряє коректність роботи парсера з одним потоком.
     */
    @Test
    void testParseJsonFiles_Success_SingleThread() {
        JSONParser parser = new JSONParser(1, 1);

        Map<String, Integer> result = parser.parseJsonFiles(TEST_DIRECTORY, PARSE_FIELD);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(isSortedDescending(result));

        assertEquals(14, result.size());
        assertEquals(34, result.get("Drama"));
        assertEquals(15, result.get("Crime"));
        assertEquals(9, result.get("Action"));
        assertEquals(4, result.get("Biography"));
    }

    /**
     * Метод testParseJsonFiles_Success_MultipleThreads перевіряє коректність роботи парсера з різною кількістю потоків.
     */
    @Test
    void testParseJsonFiles_Success_MultipleThreads() {
        int[] threadAmounts = {1, 2, 4, 8};

        for (int threads : threadAmounts) {
            JSONParser parser = new JSONParser(threads, threads);

            long startTime = System.currentTimeMillis();
            parser.parseJsonFiles(THREAD_DIRECTORY, PARSE_FIELD);
            long endTime = System.currentTimeMillis();

            long executionTime = endTime - startTime;

            System.out.println("Execution time with " + threads + " thread(s): " + executionTime + " ms");
        }
    }

    /**
     * Метод testParseJsonFiles_InvalidDirectory перевіряє виняток, що виникає при передачі неправильного каталогу.
     */
    @Test
    void testParseJsonFiles_InvalidDirectory() {
        JSONParser parser = new JSONParser(1, 1);
        assertThrows(RuntimeException.class, () -> parser.parseJsonFiles("invalid_directory", PARSE_FIELD));
    }

    /**
     * Метод testParseJsonFiles_InvalidJsonFormat перевіряє виняток, що виникає при передачі неправильного формату JSON.
     */
    @Test
    void testParseJsonFiles_InvalidJsonFormat() {
        JSONParser parser = new JSONParser(1, 1);
        assertThrows(RuntimeException.class, () -> parser.parseJsonFiles("invalid_json_file.json", PARSE_FIELD));
    }

    /**
     * Метод isSortedDescending перевіряє, чи відсортовані значення у спадному порядку.
     *
     * @param map Карта, яка містить пари ключ-значення для перевірки.
     * @return Логічне значення, чи є значення відсортовані у спадному порядку.
     */
    private boolean isSortedDescending(Map<String, Integer> map) {
        return map.values().stream().sorted(Comparator.reverseOrder()).toList().equals(new ArrayList<>(map.values()));
    }
}
