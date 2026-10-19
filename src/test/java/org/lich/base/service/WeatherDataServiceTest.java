package org.lich.base.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lich.base.model.weather.WeatherData;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherDataServiceTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private WeatherDataService weatherDataServiceV1;
    private WeatherData weatherData;

    @BeforeEach
    void setUp() {
        weatherDataServiceV1 =
                new WeatherDataService(kafkaTemplate, objectMapper);

        weatherData = new WeatherData();
        weatherData.setId(9008177);
        weatherData.setName("Perth");
        weatherData.setDt(1792402000);
    }

    @Test
    void shouldReturnSameWeatherData() {
        WeatherData result =
                weatherDataServiceV1.getWeatherData(weatherData);
        assertSame(weatherData, result);
    }

    @Test
    void shouldPreserveWeatherDataFields() {
        WeatherData result =
                weatherDataServiceV1.getWeatherData(weatherData);
        assertEquals(9008177, result.getId());
        assertEquals("Perth", result.getName());
        assertEquals(1792402000, result.getDt());
    }

    @Test
    void shouldReturnNonNullWeatherData() {
        WeatherData result =
                weatherDataServiceV1.getWeatherData(weatherData);
        assertNotNull(result);
    }
    @Test
    void getWeatherDataWithTrigger_shouldSendWeatherDataToKafka()
            throws JsonProcessingException {
        String weatherEvent =
                "{\"id\":9008177,\"name\":\"Perth\"}";
        when(objectMapper.writeValueAsString(weatherData))
                .thenReturn(weatherEvent);
        // Act
        WeatherData result =
                weatherDataServiceV1.getWeatherDataWithTrigger(weatherData);

        assertSame(weatherData, result);
        verify(objectMapper).writeValueAsString(weatherData);
        verify(kafkaTemplate).send(
                "mc.demo.topic.v1",
                "9008177",
                weatherEvent
        );
    }
}