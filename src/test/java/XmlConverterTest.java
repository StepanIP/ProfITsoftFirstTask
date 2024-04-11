import static org.junit.jupiter.api.Assertions.*;

import org.example.XmlConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class XmlConverterTest {
    private final String OUTPUT_PATH = "src/test/resources/result/";
    private final String CHECK_PATH = "src/test/resources/expected/";
    private final String FILENAME = "statistics_by_genres.xml";


    @BeforeEach
    public void setUp() {
        File file = new File(OUTPUT_PATH + FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testConvertToXml_Success() throws IOException {
        String expectedFile = "testXML.xml";

        Map<String, Integer> data = new LinkedHashMap<>();
        data.put("Drama", 3);
        data.put("Comedy", 2);
        data.put("Horror", 1);

        File file = new File(OUTPUT_PATH+FILENAME);

        XmlConverter.convertToXml(data, OUTPUT_PATH, "genres");

        assertTrue(file.exists());

        assertEquals(FILENAME, file.getName());

        String actualXml = Files.readString(Path.of(OUTPUT_PATH + FILENAME));
        String expectedXml = Files.readString(Path.of(CHECK_PATH + expectedFile));
        assertEquals(expectedXml, actualXml);
    }

    @Test
    public void testConvertToXml_EmptyData() throws IOException {
        String emptyFile = "empty.xml";
        Map<String, Integer> emptyData = Collections.emptyMap();

        XmlConverter.convertToXml(emptyData, OUTPUT_PATH, "genres");

        File file = new File(OUTPUT_PATH + FILENAME);
        assertTrue(file.exists());

        String actualXml = Files.readString(Path.of(OUTPUT_PATH + FILENAME));
        String expectedXml = Files.readString(Path.of(CHECK_PATH + emptyFile));

        assertEquals(expectedXml, actualXml);
    }


    @Test
    public void testConvertToXml_Failure() {
        String invalidOutputPath = "/invalid/path/";

        Map<String, Integer> data = new HashMap<>();

        assertThrows(IOException.class, () -> XmlConverter.convertToXml(data, invalidOutputPath, "genres"));
    }


}
