package org.example.productservice.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetricsService {
    private final Counter myCounter;
    private final AtomicInteger myGauge;
    private final Timer myTimer;
    private final DistributionSummary mySummary;

    public MetricsService(MeterRegistry registry) {
        myCounter = Counter.builder("my.counter")
                .description("A simple counter")
                .register(registry);
        myGauge = registry.gauge("my.gauge", new AtomicInteger(0));
        myTimer = Timer.builder("my.timer")
                .description("A simple timer")
                .register(registry);
        mySummary = DistributionSummary.builder("my.summary")
                .description("A simple summary")
                .register(registry);
    }

    public void process() {
        // Do something
        myCounter.increment();
        myTimer.record(() -> {
            // Do something
        });
    }

    public void increment() {
        myGauge.incrementAndGet();
    }

    public void decrement() {
        myGauge.decrementAndGet();
    }
    public void record(int value) {
        mySummary.record(value);
    }


}
