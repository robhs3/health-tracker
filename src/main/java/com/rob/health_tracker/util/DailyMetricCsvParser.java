package com.rob.health_tracker.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

public class DailyMetricCsvParser {

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.builder()
                                            .setHeader()                 // read header from first record
                                            .setSkipHeaderRecord(true)   // don't return the header as a data record
                                            .setTrim(true)
                                            .build();

    public List<CsvDailyMetricRow> parse(Path path) throws IOException {
        try (Reader reader = Files.newBufferedReader(path);
             CSVParser parser = new CSVParser(reader, FORMAT)) {

            List<CsvDailyMetricRow> rows = new ArrayList<>();
            int rowNumber = 1; // data row count

            for (CSVRecord record : parser) {
                rows.add(new CsvDailyMetricRow(
                        rowNumber++,
                        record.get("date"),
                        record.get("weight"),
                        record.get("calories"),
                        record.get("protein")
                ));
            }

            return rows;
        }
    }
}
