package com.rob.health_tracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.rob.health_tracker.controller.DailyMetricController;
import com.rob.health_tracker.dto.DailyMetricResponseDto;
import com.rob.health_tracker.dto.DailyMetricStats;
import com.rob.health_tracker.entity.DailyMetric;
import com.rob.health_tracker.service.DailyMetricService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.time.LocalDate;

import static org.hamcrest.Matchers.nullValue;

@WebMvcTest(DailyMetricController.class)
class DailyMetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DailyMetricService dailyMetricService; // <-- whatever your controller depends on

    @Test
    void getHealth_returns200() throws Exception {
        mockMvc.perform(get("/api/health")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());      
    }


    @Test
    void getDailyMetricsAll_returns200() throws Exception {
        given(dailyMetricService.getAll()).willReturn(List.of());
        
        mockMvc.perform(get("/api/daily-metrics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    void getDailyMetricsAll_returnsDtos_withoutId() throws Exception {
        given(dailyMetricService.getAll()).willReturn(List.of(
                new DailyMetricResponseDto(LocalDate.of(2025, 1, 1), 165.2, 2800, 150),
                new DailyMetricResponseDto(LocalDate.of(2025, 1, 2), 165.0, 2750, 155)
        ));

        mockMvc.perform(get("/api/daily-metrics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))

                // element 0 fields
                .andExpect(jsonPath("$[0].date").value("2025-01-01"))
                .andExpect(jsonPath("$[0].weight").value(165.2))
                .andExpect(jsonPath("$[0].calories").value(2800))
                .andExpect(jsonPath("$[0].protein").value(150))
                .andExpect(jsonPath("$[0].id").doesNotExist());
    }


    @Test
    void getDailyMetricsBetween_validRequest_returns200() throws Exception {
        var from = LocalDate.parse("2000-01-01");
        var to = LocalDate.parse("2025-12-31");

        given(dailyMetricService.getBetween(from, to)).willReturn(List.of());

        mockMvc.perform(get("/api/daily-metrics")
                .param("from", from.toString())
                .param("to", to.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());;
    }


    @Test
    void getDailyMetricsLatest_returns200() throws Exception {
        given(dailyMetricService.getLatest()).willReturn(new DailyMetricResponseDto(
                                                        LocalDate.of(2025, 1, 1),
                                                        165.2,
                                                        2800,
                                                        150
        ));

        mockMvc.perform(get("/api/daily-metrics/latest")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.date").value("2025-01-01"))
            .andExpect(jsonPath("$.weight").value(165.2))
            .andExpect(jsonPath("$.calories").value(2800))
            .andExpect(jsonPath("$.protein").value(150))
            .andExpect(jsonPath("$.id").doesNotExist());
    }


    @Test
    void getDailyMetricsStats_validRequest_returns200() throws Exception {
        var from = LocalDate.parse("2000-01-01");
        var to = LocalDate.parse("2025-12-31");

        given(dailyMetricService.getStatsBetween(from, to)).willReturn(new DailyMetricStats(0, 0.0, 0.0, null, null, null));

        mockMvc.perform(get("/api/daily-metrics/stats").param("from", "2000-01-01").param("to", "2025-12-31")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.countDays").value(0))
                .andExpect(jsonPath("$.avgCalories").value(0.0))
                .andExpect(jsonPath("$.avgProtein").value(0.0))
                .andExpect(jsonPath("$.startWeight").value(nullValue()))
                .andExpect(jsonPath("$.endWeight").value(nullValue()))
                .andExpect(jsonPath("$.weightChange").value(nullValue()));      
    }
    
}
