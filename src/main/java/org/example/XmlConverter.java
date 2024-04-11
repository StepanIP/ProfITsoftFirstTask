package org.example;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlConverter {

    private static final Logger logger = LoggerFactory.getLogger(XmlConverter.class);


    public static void convertToXml(Map<String, Integer> data, String outputPath, String field) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        Statistic statistic = new Statistic();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            XmlItem item = new XmlItem(entry.getKey(), entry.getValue());
            statistic.addItem(item);
        }

        String filename = "statistics_by_" + field + ".xml";
        File outputFile = new File(outputPath, filename);

        try {
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, statistic);
        } catch (IOException e) {
            logger.error("Failed to write XML to file: {}", filename, e);
            throw e;
        }
    }

    @JsonRootName("statistics")
    public static class Statistic {
        private List<XmlItem> items;

        public Statistic() {
            this.items = new ArrayList<>();
        }

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "item")
        public List<XmlItem> getItems() {
            return items;
        }

        public void setItem(List<XmlItem> items) {
            this.items = items;
        }

        public void addItem(XmlItem item) {
            items.add(item);
        }
    }

    public static class XmlItem {
        private String value;
        private int count;

        public XmlItem(String value, int count) {
            this.value = value;
            this.count = count;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}