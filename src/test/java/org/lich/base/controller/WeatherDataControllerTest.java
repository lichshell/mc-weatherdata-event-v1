package org.lich.base.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lich.base.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(WeatherDataController.class)
class WeatherDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherDataService weatherDataService;

    private String weatherEvent;
    @BeforeEach
    void setUp() {
        weatherEvent = """
                {
                    "coord": {
                        "lon": 144.9633,
                        "lat": -37.814
                    },
                    "weather": [
                        {
                            "id": 801,
                            "main": "Clouds",
                            "description": "few clouds",
                            "icon": "02n"
                        }
                    ],
                    "base": "stations",
                    "main": {
                        "temp": 23.65,
                        "feels_like": 23.22,
                        "temp_min": 21.86,
                        "temp_max": 24.39,
                        "pressure": 1020,
                        "humidity": 44,
                        "sea_level": 1020,
                        "grnd_level": 1015
                    },
                    "visibility": 10000,
                    "wind": {
                        "speed": 4.12,
                        "deg": 50
                    },
                    "clouds": {
                        "all": 15
                    },
                    "dt": 1792402193,
                    "sys": {
                        "type": 2,
                        "id": 2008797,
                        "country": "AU",
                        "sunrise": 1792351795,
                        "sunset": 1792399211
                    },
                    "timezone": 39600,
                    "id": 2158177,
                    "name": "Melbourne",
                    "cod": 200
                }
                """;
    }

    @Test
    void echoHealth() throws Exception {
        mockMvc.perform(get("/v1/health-check"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Ok"));
    }

    @Test
    void wdeTriggerV2With201Created() throws Exception {
        mockMvc.perform(post("/v1/trigger/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(weatherEvent))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().string("Accepted"));
    }
    @Test
    void wdeTriggerV2With400BadRequest() throws Exception {
        mockMvc.perform(post("/v1/trigger/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void wdeTriggerV2WithNegativeCase() throws Exception {
        mockMvc.perform(post("/v2/trigger/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(weatherEvent))
//                .andExpect(status().isNotFound())
                .andExpect(status().isNotFound());
    }
}