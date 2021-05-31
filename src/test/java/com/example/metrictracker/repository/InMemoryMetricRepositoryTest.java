package com.example.metrictracker.repository;

import com.example.metrictracker.exception.MetricAlreadyExistsException;
import com.example.metrictracker.exception.MetricNotFoundException;
import com.example.metrictracker.model.Metric;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class InMemoryMetricRepositoryTest {

    @InjectMocks
    private InMemoryMetricRepository inMemoryMetricRepository;


    @Test
    void saveNewMetricSuccessful() {
        Metric testMetric = new Metric("test-metric", Arrays.asList(100.0, 3.0));

        inMemoryMetricRepository.saveNewMetric(testMetric);
        assertThat(((Map<String, Metric>) Objects.requireNonNull(ReflectionTestUtils.getField(inMemoryMetricRepository, "metricMap"))).get("test-metric")).isEqualTo(testMetric);
    }

    @Test
    void saveNewMetricThrowsExceptionWhenMetricAlreadyExists() {
        ConcurrentMap<String, Metric> metricMap = new ConcurrentHashMap<>();
        Metric testMetric = new Metric("test-metric", Arrays.asList(100.0, 3.0));
        metricMap.put(testMetric.getMetricName(), testMetric);
        ReflectionTestUtils.setField(inMemoryMetricRepository, "metricMap", metricMap);

        Assertions.assertThrows(MetricAlreadyExistsException.class, () -> inMemoryMetricRepository.saveNewMetric(new Metric("test-metric", Collections.emptyList())));
        assertThat(((Map<String, Metric>) Objects.requireNonNull(ReflectionTestUtils.getField(inMemoryMetricRepository, "metricMap"))).get("test-metric")).isEqualTo(testMetric);
    }

    @Test
    void addMetricValuesSuccessful() {
        ConcurrentMap<String, Metric> metricMap = new ConcurrentHashMap<>();
        Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(100.0, 3.0)));
        metricMap.put(testMetric.getMetricName(), testMetric);
        ReflectionTestUtils.setField(inMemoryMetricRepository, "metricMap", metricMap);

        inMemoryMetricRepository.addMetricValues("test-metric", new ArrayList<>(Arrays.asList(1.0, 2.0)));
        List<Double> expectedValues = new ArrayList<>(Arrays.asList(100.0, 3.0, 1.0, 2.0));
        assertThat(((Map<String, Metric>) Objects.requireNonNull(ReflectionTestUtils.getField(inMemoryMetricRepository, "metricMap"))).get("test-metric").getValues()).isEqualTo(expectedValues);
    }

    @Test
    void addMetricValuesThrowsExceptionIfMetricNotFound() {
        Assertions.assertThrows(MetricNotFoundException.class, () -> inMemoryMetricRepository.addMetricValues("test-metric", new ArrayList<>(Arrays.asList(1.0, 2.0))));
        assertThat(((Map<String, Metric>) Objects.requireNonNull(ReflectionTestUtils.getField(inMemoryMetricRepository, "metricMap"))).get("test-metric")).isNull();
    }

    @Test
    void getMetricByNameSuccessful() {
        ConcurrentMap<String, Metric> metricMap = new ConcurrentHashMap<>();
        Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(100.0, 3.0)));
        metricMap.put(testMetric.getMetricName(), testMetric);
        ReflectionTestUtils.setField(inMemoryMetricRepository, "metricMap", metricMap);

        Metric metric = inMemoryMetricRepository.getMetricByName("test-metric");

        assertThat(metric).isEqualTo(testMetric);
    }

    @Test
    void getMetricByNameThrowsExceptionIfMetricNotFound() {
        Assertions.assertThrows(MetricNotFoundException.class, () -> inMemoryMetricRepository.getMetricByName("test-metric"));
        assertThat(((Map<String, Metric>) Objects.requireNonNull(ReflectionTestUtils.getField(inMemoryMetricRepository, "metricMap"))).get("test-metric")).isNull();
    }

    @Test
    void getAllMetricsSuccessful() {
        ConcurrentMap<String, Metric> metricMap = new ConcurrentHashMap<>();
        Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(100.0, 3.0)));
        metricMap.put(testMetric.getMetricName(), testMetric);
        ReflectionTestUtils.setField(inMemoryMetricRepository, "metricMap", metricMap);

        Collection<Metric> metrics = inMemoryMetricRepository.getAllMetrics();

        assertThat(metrics).isEqualTo(metricMap.values());
    }
}
