package com.example.newsUser.service;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LatencyMetricsService {

    private final CollectorRegistry collectorRegistry;
    private Map<String, Histogram> histogramNameWithHistogram = new HashMap<>();

    @Autowired
    public LatencyMetricsService(CollectorRegistry collectorRegistry) {
        this.collectorRegistry = collectorRegistry;
    }

    public Histogram getHistogramWithName(String name) {
        return histogramNameWithHistogram.computeIfAbsent(name, k -> Histogram.build().name(name)
                .labelNames("api")
                .help(name).register(collectorRegistry));
    }
}

