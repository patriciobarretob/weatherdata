package com.weather.restapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Sensor {
    @Id
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SensorReading> sensorReadings = new ArrayList<>();

    public Sensor(SensorReading reading) {
        id = reading.getSensorID();
        append(reading);
    }

    public void append(SensorReading reading) {
        sensorReadings.add(reading);
    }
}
