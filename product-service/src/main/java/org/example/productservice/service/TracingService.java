package org.example.productservice.service;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TracingService {
    private Tracer tracer;

    public void myMethod() {
        Span newSpan = tracer.nextSpan().name("myMethodSpan").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(newSpan.start())) {
            // Thực hiện logic của bạn ở đây
            performTask();
        } finally {
            newSpan.end();
        }
    }

    private void performTask() {
        // Logic của task
    }
}
