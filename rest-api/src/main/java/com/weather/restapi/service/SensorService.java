package com.weather.restapi.service;

import com.weather.restapi.model.SensorReading;
import com.weather.restapi.model.SensorReport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SensorService {

    public String saveSensorReading(SensorReading reading);

    public List<SensorReport> getSensors(String sensorIDList, String range, String mode);
}
