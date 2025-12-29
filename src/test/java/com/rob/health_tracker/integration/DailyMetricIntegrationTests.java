package com.rob.health_tracker.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DailyMetricIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postThenGet_persistsAndReturnsMetric() throws Exception {
        String body = """
            {
              "date": "2025-01-01",
              "weight": 165.2,
              "calories": 2800,
              "protein": 150
            }
            """;

        // POST persists to real DB (H2 in test profile)
        mockMvc.perform(post("/api/daily-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.date").value("2025-01-01"))
            .andExpect(jsonPath("$.weight").value(165.2))
            .andExpect(jsonPath("$.calories").value(2800))
            .andExpect(jsonPath("$.protein").value(150));

        // GET returns what was saved
        mockMvc.perform(get("/api/daily-metrics")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            // assuming this endpoint returns a JSON array
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].date").value("2025-01-01"))
            .andExpect(jsonPath("$[0].weight").value(165.2));
    }
}
