package com.weather.restapi.model;

import lombok.Getter;
import lombok.Setter;

//import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SensorReport {
    private long sensorID;
    // yyyy-MM-dd HH:mm:ss
    // 2023-02-25 10:35:25
    private String range;
    private String mode;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private double rainFall;
    private double snowFall;

    // range = day / week / month / all
    // mode = min / max / sum / avg
    public SensorReport(Sensor sensor, String range, String mode) {

        this.sensorID = sensor.getId();
        this.range = range;
        this.mode = mode;

        long dayToSubtract = getDayToSubtract(range);
        long refMilli = System.currentTimeMillis() - dayToSubtract * 24 * 60 * 60 * 1000;

        List<SensorReading> readings = sensor.getSensorReadings();
        var values = readings.stream().filter(r -> r.getDateTimeMilli() >= refMilli).map(SensorReading::getTemperature).collect(Collectors.toList());
        this.temperature = getAns(mode, values);

        values = readings.stream().filter(r -> r.getDateTimeMilli() >= refMilli).map(SensorReading::getHumidity).collect(Collectors.toList());
        this.humidity = getAns(mode, values);

        values = readings.stream().filter(r -> r.getDateTimeMilli() >= refMilli).map(SensorReading::getWindSpeed).collect(Collectors.toList());
        this.windSpeed = getAns(mode, values);

        values = readings.stream().filter(r -> r.getDateTimeMilli() >= refMilli).map(SensorReading::getRainFall).collect(Collectors.toList());
        this.rainFall = getAns(mode, values);

        values = readings.stream().filter(r -> r.getDateTimeMilli() >= refMilli).map(SensorReading::getSnowFall).collect(Collectors.toList());
        this.snowFall = getAns(mode, values);
    }

    private static double getAns(String mode, List<Double> values) {
        double t = 0.0;
        if (null == mode || mode.equalsIgnoreCase("avg")) {
            t = values.stream().mapToDouble(d -> d).average().orElse(0.0);
        } else if (mode.equalsIgnoreCase("min")) {
            t = values.stream().mapToDouble(d -> d).min().orElse(0.0);
        } else if (mode.equalsIgnoreCase("max")) {
            t = values.stream().mapToDouble(d -> d).max().orElse(0.0);
        } else if (mode.equalsIgnoreCase("sum")) {
            t = values.stream().mapToDouble(d -> d).sum();
        }
        return t;
    }

    private static long getDayToSubtract(String range) {
        if (null == range || range.equalsIgnoreCase("all")) {
            return 50 * 365;
        } else if (range.equals("day")) {
            return 1;
        } else if (range.equals("week")) {
            return 7;
        } else if (range.equals("month")) {
            return 30;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "SensorReport{" +
                "sensorID=" + sensorID +
                ", range='" + range + '\'' +
                ", mode='" + mode + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", rainFall=" + rainFall +
                ", snowFall=" + snowFall +
                '}';
    }
}
