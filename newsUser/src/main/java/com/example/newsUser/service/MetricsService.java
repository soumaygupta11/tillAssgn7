package com.example.newsUser.service;


import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MetricsService {

    private final CollectorRegistry collectorRegistry;
    Map<String, Counter> counterNameWithCounter = new HashMap<>();

    Map<String, Histogram> histogramNameWithHistogram = new HashMap<>();
    @Autowired
    public MetricsService(CollectorRegistry collectorRegistry) {
        this.collectorRegistry = collectorRegistry;
    }

    public Counter getCounterWithName(String name) {
        return counterNameWithCounter.computeIfAbsent(name, k -> Counter.build().name(name)
                .labelNames("api")
                .help(name).register(collectorRegistry));
    }

    public Histogram getHistogramWithName(String name) {
        return histogramNameWithHistogram.computeIfAbsent(name, k -> Histogram.build().name(name)
                .labelNames("api")
                .help(name).register(collectorRegistry));
    }

}
