package org.example.productservice.controller;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TracingController {

    private final Tracer tracer;

    @Operation(summary = "Trace Example Endpoint", description = "Triggers a manual span for distributed tracing using Micrometer Tracer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tracing example completed successfully")
    })
    @GetMapping("/trace-example")
    public String traceExample() {
        Span span = tracer.nextSpan().name("traceExampleSpan").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
            return performTask();
        } finally {
            span.end();
        }
    }

    private String performTask() {
        // Giả lập công việc cần trace
        return "Tracing example completed.";
    }
}
