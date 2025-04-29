package org.example.productservice.controller;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TracingController {
    private Tracer tracer;

    @GetMapping("/trace-example")
    public String traceExample() {
        Span span = tracer.nextSpan().name("traceExampleSpan").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            // Thực hiện logic của bạn ở đây
            return performTask();
        } finally {
            span.end();
        }
    }

    private String performTask() {
        // Logic của task
        return "Tracing example completed.";
    }
}
