package com.weather.restapi.service.impl;

import com.weather.restapi.model.Sensor;
import com.weather.restapi.model.SensorReading;
import com.weather.restapi.model.SensorReport;
import com.weather.restapi.repository.SensorRepository;
import com.weather.restapi.service.SensorService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ServiceImpl implements SensorService {

    SensorRepository sensorRepository;


    public ServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    @Override
    public String saveSensorReading(SensorReading reading) {

        // convert string date to milli
        String dateTime = reading.getDateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date convertedCurrentDate = sdf.parse(dateTime);
            reading.setDateTimeMilli(convertedCurrentDate.getTime());
        } catch (ParseException e) {
            return "Invalid date time";
        }

        if (!sensorRepository.existsById(reading.getSensorID())) {
            // new sensor
            Sensor sensor = new Sensor(reading);
            sensorRepository.save(sensor);
        } else {
            // old sensor
            // just update
            Sensor sensor = sensorRepository.findById(reading.getSensorID()).get();
            sensor.append(reading);
            sensorRepository.save(sensor);
        }
        return "Success!";
    }

    @Override
    public List<SensorReport> getSensors(String sensorIDList, String range, String mode) {

        // range = day / week / month / all
        // mode = min / max / sum / avg

        List<String> sidList;
        if (null == sensorIDList || sensorIDList.isEmpty() || "all".equalsIgnoreCase(sensorIDList)) {
            List<Long> idList = sensorRepository.findAll().stream().map(Sensor::getId).toList();
            sidList = idList.stream().map(Object::toString).toList();
        } else {
            sidList = Arrays.stream(sensorIDList.split(",")).toList();
        }

        var idList = new ArrayList<Long>();
        for (var sid : sidList) {
            var sensorID = Long.parseLong(sid);
            var byId = sensorRepository.findById(sensorID);
            if (byId.isPresent()) {
                idList.add(sensorID);
            }
        }

        if (idList.isEmpty()) {
            return new ArrayList<>();
        }

        var sensorReports = new ArrayList<SensorReport>();

        for (var id : idList) {
            Sensor sensor = sensorRepository.findById(id).get();
            SensorReport sensorReport = new SensorReport(sensor, range, mode);
            sensorReports.add(sensorReport);
        }

        return sensorReports;
    }
}
