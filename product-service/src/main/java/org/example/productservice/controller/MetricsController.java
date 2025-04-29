package org.example.productservice.controller;

import org.example.productservice.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private MetricsService metricsService;

    @GetMapping("/process")
    public String process() {
        metricsService.process();
        return "Processed";
    }

    @PostMapping("/gauge/increment")
    public String incrementGauge() {
        metricsService.increment();
        return "Gauge incremented";
    }

    @PostMapping("/gauge/decrement")
    public String decrementGauge() {
        metricsService.decrement();
        return "Gauge decremented";
    }

    @PostMapping("/summary")
    public String recordSummary(@RequestParam int value) {
        metricsService.record(value);
        return "Summary recorded: " + value;
    }
}
