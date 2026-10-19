package org.lich.base.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lich.base.model.weather.WeatherData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherDataService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log =
            LogManager.getLogger(WeatherDataService.class);
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());
    public WeatherDataService(KafkaTemplate<String, String> kafkaTemplate,ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    public WeatherData getWeatherData(WeatherData weatherData){
        log.info("WeatherDataService.getWeatherData() processing ...");
        log.info(" Id : {}",  weatherData.getId());
        log.info(" Name : {}", weatherData.getName());
        log.info(" Date : {}", convertTimestamp(weatherData.getDt()));
        return weatherData;
    }
    public WeatherData getWeatherDataWithTrigger(WeatherData weatherData) throws JsonProcessingException {
        log.info("WeatherDataService.getWeatherData() processing ...");
        log.info(" Id : {}",  weatherData.getId());
        log.info(" Name : {}", weatherData.getName());
        log.info(" Date : {}", convertTimestamp(weatherData.getDt()));
        log.info(":: Trigger Kafka a message ::");
        String message = objectMapper.writeValueAsString(weatherData);
        String key = weatherData.getId()+"";
        triggerMessage(key, message);
        return weatherData;
    }

    private void triggerMessage(String key, String message) {
        kafkaTemplate.send("mc.demo.topic.v1", key, message);
    }

    private static String convertTimestamp(int inDate) {
        Instant instant = Instant.ofEpochSecond(inDate);
        return FORMATTER.format(instant);
    }
}
