package com.example.metrictracker.repository;

import com.example.metrictracker.model.Metric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
@Slf4j
@Profile("local")
public class InMemoryMetricRepository implements MetricRepository {

    private final ConcurrentMap<String, Metric> metricMap = new ConcurrentHashMap<>();

    @Override
    public Metric saveNewMetric(Metric metric) {
        if (metricMap.containsKey(metric.getMetricName())) {
            //Throw Exception
            log.error("A metric with the name {} already exists", metric.getMetricName());
            throw new RuntimeException("A metric with the provided name already exists");
        }
        metricMap.put(metric.getMetricName(), metric);
        return metric;
    }

    @Override
    public Metric addMetricValues(String metricName, Collection<Double> metricValues) {
        if (metricMap.containsKey(metricName)) {
            metricMap.get(metricName).getValues().addAll(metricValues);
        }
        return null;
    }

    @Override
    public Metric getMetricByName(String metricName) {
        if (!metricMap.containsKey(metricName)) {
            //Throw Exception
            log.error("A metric with the name {} was not found", metricName);
            throw new RuntimeException("A metric with the provided name was not found {}");
        }

        return metricMap.get(metricName);
    }
}
