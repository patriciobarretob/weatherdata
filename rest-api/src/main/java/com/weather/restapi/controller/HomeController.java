package com.weather.restapi.controller;

import com.weather.restapi.model.SensorReading;
import com.weather.restapi.model.SensorReport;
import com.weather.restapi.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SensorService sensorService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute SensorReading user) {
        sensorService.saveSensorReading(user);
        return "SensorDataSaved";
    }

    @GetMapping(value = "/viewAll")
    public ModelAndView viewAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userData");
        List<SensorReport> sensors = sensorService.getSensors(null, "all", "avg");
        modelAndView.addObject("sensors", sensors);
        return modelAndView;
    }
}
