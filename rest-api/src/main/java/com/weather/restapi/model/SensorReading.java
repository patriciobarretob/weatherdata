package com.weather.restapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Min(0)
    private long id;

    @Min(0)
    private long sensorID;

    // yyyy-MM-dd HH:mm:ss
    // 2023-02-25 10:35:25
    @NotBlank
    private String dateTime;

    private long dateTimeMilli;

    private double temperature;
    @Min(0)
    private double humidity;
    @Min(0)
    private double windSpeed;
    @Min(0)
    private double rainFall;
    @Min(0)
    private double snowFall;
}
