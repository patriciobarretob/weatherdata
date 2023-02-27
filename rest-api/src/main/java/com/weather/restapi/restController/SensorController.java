package com.weather.restapi.restController;

import com.weather.restapi.model.SensorReading;
import com.weather.restapi.model.SensorReport;
import com.weather.restapi.service.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class SensorController {
    private final SensorService sensorService;

    @PostMapping("/api/v1/weatherData")
    public String saveSensorReading(@RequestBody @Valid SensorReading reading) {
        sensorService.saveSensorReading(reading);
        return "Sensor reading saved successfully";
    }

    @GetMapping("/api/v1/weatherData/sensors/{sensorID}")
    public List<SensorReport> getSensorData(@PathVariable("sensorID") String sensorIDList, @RequestParam(name = "range", defaultValue = "all", required = false) String range, @RequestParam(name = "mode", defaultValue = "avg", required = false) String mode) {
        return sensorService.getSensors(sensorIDList, range, mode);
    }
}
