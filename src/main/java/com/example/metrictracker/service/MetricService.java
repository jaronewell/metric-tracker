package com.example.metrictracker.service;

import com.example.metrictracker.exception.MetricHasNoValuesException;
import com.example.metrictracker.model.Metric;
import com.example.metrictracker.repository.MetricRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetricService {

  private final MetricRepository metricRepository;

  public MetricService(MetricRepository metricRepository) {
    this.metricRepository = metricRepository;
  }

  public Metric createNewMetric(Metric metric) {
    if (metric.getValues() == null) {
      metric.setValues(new ArrayList<>());
    }
    return metricRepository.saveNewMetric(metric);
  }

  public Metric addValuesToMetric(String metricName, Collection<Double> values) {
    return metricRepository.addMetricValues(metricName, values);
  }

  public Collection<Metric> getAllMetrics() {
    return metricRepository.getAllMetrics();
  }

  public Double getMeanForMetric(String metricName) {
    Collection<Double> metricValues = metricRepository.getMetricByName(metricName).getValues();
    if (metricValues.size() == 0) {
      throw new MetricHasNoValuesException();
    }
    return metricRepository.getMetricByName(metricName).getValues().stream()
            .mapToDouble(value -> value)
            .sum()
        / metricValues.size();
  }

  public Double getMedianForMetric(String metricName) {
    List<Double> sortedValues =
        metricRepository.getMetricByName(metricName).getValues().stream()
            .sorted()
            .collect(Collectors.toList());

    if (sortedValues.size() == 0) {
      throw new MetricHasNoValuesException();
    }

    int valuesCount = sortedValues.size();
    return valuesCount % 2 == 0
        ? (sortedValues.get(valuesCount / 2 - 1) + sortedValues.get(valuesCount / 2)) / 2
        : sortedValues.get(valuesCount / 2);
  }

  public Double getMinForMetric(String metricName) {
    return metricRepository.getMetricByName(metricName).getValues().stream()
        .mapToDouble(value -> value)
        .min()
        .orElseThrow(MetricHasNoValuesException::new);
  }

  public Double getMaxForMetric(String metricName) {
    return metricRepository.getMetricByName(metricName).getValues().stream()
        .mapToDouble(value -> value)
        .max()
        .orElseThrow(MetricHasNoValuesException::new);
  }
}
