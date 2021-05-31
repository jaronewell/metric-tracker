package com.example.metrictracker.service;

import com.example.metrictracker.exception.MetricHasNoValuesException;
import com.example.metrictracker.model.Metric;
import com.example.metrictracker.repository.MetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MetricServiceTest {

  @InjectMocks private MetricService metricService;

  @Mock private MetricRepository metricRepository;

  @Test
  void createNewMetricSuccessful() {
    Metric testMetric = new Metric("test-metric", null);
    Mockito.when(metricRepository.saveNewMetric(testMetric)).thenReturn(testMetric);

    Metric newMetric = metricService.createNewMetric(testMetric);
    Mockito.verify(metricRepository)
        .saveNewMetric(
            Mockito.argThat(
                metric ->
                    metric.getMetricName().equals("test-metric") && metric.getValues() != null));
    assertThat(newMetric).isEqualTo(testMetric);
  }

  @Test
  void createNewMetricThrowsExceptionIfNameIsInvalid() {
    Metric testMetric = new Metric("Invalid Name", null);

    assertThrows(IllegalArgumentException.class, () -> metricService.createNewMetric(testMetric));
    Mockito.verify(metricRepository, times(0)).saveNewMetric(any());
  }

  @Test
  void addValuesToMetricSuccessful() {
    Collection<Double> valuesToAdd = Arrays.asList(1.0, 2.0);
    Metric updatedMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0, 1.0, 2.0)));
    Mockito.when(metricRepository.addMetricValues("test-metric", valuesToAdd)).thenReturn(updatedMetric);

    Metric metric = metricService.addValuesToMetric("test-metric", valuesToAdd);

    assertThat(metric).isEqualTo(updatedMetric);
  }

  @Test
  void getAllMetricsSuccessful() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0)));
    Collection<Metric> testMetrics = Collections.singletonList(testMetric);
    Mockito.when(metricRepository.getAllMetrics()).thenReturn(testMetrics);

    Collection<Metric> metrics = metricService.getAllMetrics();
    assertThat(metrics).isEqualTo(testMetrics);
  }

  @Test
  void getMeanForMetricSuccessful() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0)));
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    Double mean = metricService.getMeanForMetric(testMetric.getMetricName());
    assertThat(mean).isEqualTo(5.0);
  }

  @Test
  void getMeanForMetricThrowsExceptionIfNoValuesFound() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>());
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    assertThrows(
        MetricHasNoValuesException.class,
        () -> metricService.getMeanForMetric(testMetric.getMetricName()));
  }

  @Test
  void getMedianForMetricWithOddCountSuccessful() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0)));
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    Double median = metricService.getMedianForMetric(testMetric.getMetricName());
    assertThat(median).isEqualTo(6.0);
  }

  @Test
  void getMedianForMetricWithEvenCountSuccessful() {
    Metric testMetric =
        new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0, 4.0)));
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    Double median = metricService.getMedianForMetric(testMetric.getMetricName());
    assertThat(median).isEqualTo(5.0);
  }

  @Test
  void getMedianForMetricThrowsExceptionIfNoValuesFound() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>());
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    assertThrows(
        MetricHasNoValuesException.class,
        () -> metricService.getMedianForMetric(testMetric.getMetricName()));
  }

  @Test
  void getMinForMetricSuccessful() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0)));
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    Double min = metricService.getMinForMetric(testMetric.getMetricName());
    assertThat(min).isEqualTo(2.0);
  }

  @Test
  void getMinForMetricThrowsExceptionIfNoValuesFound() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>());
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    assertThrows(
        MetricHasNoValuesException.class,
        () -> metricService.getMinForMetric(testMetric.getMetricName()));
  }

  @Test
  void getMaxForMetricSuccessful() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0)));
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    Double max = metricService.getMaxForMetric(testMetric.getMetricName());
    assertThat(max).isEqualTo(7.0);
  }

  @Test
  void getMaxForMetricThrowsExceptionIfNoValuesFound() {
    Metric testMetric = new Metric("test-metric", new ArrayList<>());
    Mockito.when(metricRepository.getMetricByName(testMetric.getMetricName()))
        .thenReturn(testMetric);

    assertThrows(
        MetricHasNoValuesException.class,
        () -> metricService.getMaxForMetric(testMetric.getMetricName()));
  }
}
