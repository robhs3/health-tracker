package com.rob.health_tracker.service;

import java.util.List;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.rob.health_tracker.dto.DailyMetricRequestDto;
import com.rob.health_tracker.util.CsvDailyMetricRow;
import com.rob.health_tracker.util.DailyMetricCsvParser;
import java.nio.file.Path;

@Service
public class DailyMetricCsvImportService {

    private final DailyMetricService dailyMetricService;

    public DailyMetricCsvImportService(DailyMetricService dailyMetricService) {
        this.dailyMetricService = dailyMetricService;
    }

    public void importFromCsv(Path path) throws IOException {
        DailyMetricCsvParser parser = new DailyMetricCsvParser();
        List<CsvDailyMetricRow> rows = parser.parse(path);
        importRows(rows);
    }

    public void importRows(List<CsvDailyMetricRow> rows) {
        for (CsvDailyMetricRow row : rows) {

            DailyMetricRequestDto dto =
                new DailyMetricRequestDto(
                    LocalDate.parse(row.date()),
                    Double.parseDouble(row.weight()),
                    Integer.parseInt(row.calories()),
                    Integer.parseInt(row.protein())
                );

            dailyMetricService.add(dto);
        }
    }
}