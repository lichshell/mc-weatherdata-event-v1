package org.lich.base.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lich.base.model.weather.WeatherData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class WeatherDataServiceTest {

    private WeatherDataService weatherDataService;
    private WeatherData weatherData;

    @BeforeEach
    void setUp() {
        weatherDataService = new WeatherDataService();
        weatherData = new WeatherData();
        weatherData.setId(101);
        weatherData.setName("Melbourne");
        weatherData.setDt(0);
    }

    @Test
    void shouldReturnSameWeatherData() {
        WeatherData result =
                weatherDataService.getWeatherData(weatherData);
        assertSame(weatherData, result);
    }

    @Test
    void shouldPreserveWeatherDataFields() {
        WeatherData result =
                weatherDataService.getWeatherData(weatherData);
        assertEquals(101, result.getId());
        assertEquals("Melbourne", result.getName());
        assertEquals(0, result.getDt());
    }

    @Test
    void shouldReturnNonNullWeatherData() {
        WeatherData result =
                weatherDataService.getWeatherData(weatherData);
        assertNotNull(result);
    }
}