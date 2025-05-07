package org.example.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.productservice.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private MetricsService metricsService;

    @Operation(summary = "Trigger a dummy process", description = "Triggers a process and records related metrics.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Process completed successfully")
    })
    @GetMapping("/process")
    public String process() {
        metricsService.process();
        return "Processed";
    }

    @Operation(summary = "Increment gauge metric", description = "Increments the value of a gauge metric.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gauge incremented successfully")
    })
    @PostMapping("/gauge/increment")
    public String incrementGauge() {
        metricsService.increment();
        return "Gauge incremented";
    }

    @Operation(summary = "Decrement gauge metric", description = "Decrements the value of a gauge metric.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gauge decremented successfully")
    })
    @PostMapping("/gauge/decrement")
    public String decrementGauge() {
        metricsService.decrement();
        return "Gauge decremented";
    }

    @Operation(summary = "Record summary metric", description = "Records a value in a summary metric.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Summary recorded successfully")
    })
    @PostMapping("/summary")
    public String recordSummary(@RequestParam int value) {
        metricsService.record(value);
        return "Summary recorded: " + value;
    }
}
