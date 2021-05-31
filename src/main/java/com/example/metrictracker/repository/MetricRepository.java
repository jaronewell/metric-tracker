package com.example.metrictracker.repository;

import com.example.metrictracker.model.Metric;

import java.util.Collection;

public interface MetricRepository {

    Metric saveNewMetric(Metric metric);

    Metric addMetricValues(String metricName, Collection<Double> metricValue);

    Metric getMetricByName(String metricName);

    Collection<Metric> getAllMetrics();
}
