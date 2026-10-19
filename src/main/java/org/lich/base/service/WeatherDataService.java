package org.lich.base.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lich.base.model.weather.WeatherData;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherDataService {

    private static final Logger log =
            LogManager.getLogger(WeatherDataService.class);
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());
    public WeatherData getWeatherData(WeatherData weatherData){
        log.info("WeatherDataService.getWeatherData() processing ...");
        log.info(" Id : {}",  weatherData.getId());
        log.info(" Name : {}", weatherData.getName());
        log.info(" Date : {}", convertTimestamp(weatherData.getDt()));
        return weatherData;
    }

    private static String convertTimestamp(int inDate) {
        Instant instant = Instant.ofEpochSecond(inDate);
        return FORMATTER.format(instant);
    }
}
