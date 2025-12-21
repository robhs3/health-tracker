package com.rob.health_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.time.LocalDate;

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
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getDailyMetricsBetween_validRequest_returns200() throws Exception {
        var from = LocalDate.parse("2000-01-01");
        var to = LocalDate.parse("2025-12-31");

        given(dailyMetricService.getBetween(from, to)).willReturn(List.of());

        mockMvc.perform(get("/api/daily-metrics").param("from", from.toString()).param("to", to.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getDailyMetricsLatest_returns200() throws Exception {
        given(dailyMetricService.getLatest()).willReturn(new DailyMetric());
        mockMvc.perform(get("/api/daily-metrics/latest")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getDailyMetricsStats_validRequest_returns200() throws Exception {
        var from = LocalDate.parse("2000-01-01");
        var to = LocalDate.parse("2025-12-31");

        given(dailyMetricService.getStatsBetween(from, to)).willReturn(new DailyMetricStats(0, 0.0, 0.0, null, null, null));

        mockMvc.perform(get("/api/daily-metrics/stats").param("from", "2000-01-01").param("to", "2025-12-31")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));      
    }
    
}
