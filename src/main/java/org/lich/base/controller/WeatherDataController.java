package org.lich.base.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lich.base.model.weather.WeatherData;
import org.lich.base.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherDataController {

    @Autowired
    private WeatherDataService weatherDataService;

    @RequestMapping(path = "/v1/health-check", method = RequestMethod.GET)
    public ResponseEntity<String> echoHealth(){
            return ResponseEntity.status(HttpStatus.OK).body("Ok");
    }
    @RequestMapping(path = "/v1/trigger/event", method = RequestMethod.POST)
    public ResponseEntity<String> wdeTriggerV2(@RequestBody WeatherData weatherData) throws JsonProcessingException {
        weatherDataService.getWeatherData(weatherData);
        return ResponseEntity.status(HttpStatus.CREATED).body("Accepted");
    }
}
