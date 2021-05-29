package com.example.metrictracker.controller;

import com.example.metrictracker.model.Metric;
import com.example.metrictracker.service.MetricService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/metric")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping
    public ResponseEntity<Metric> createNewMetric(@RequestBody Metric metric) {
        return ResponseEntity.ok(metricService.createNewMetric(metric));
    }

    @PostMapping("/{metricName}/values")
    public ResponseEntity<Metric> postValueToMetric(@PathVariable String metricName, @RequestBody Collection<Double> values) {
        return ResponseEntity.ok(metricService.addValuesToMetric(metricName, values));
    }

    @GetMapping("/{metricName}/mean")
    public ResponseEntity<Double> getMetricMeanValue(@PathVariable String metricName) {
        return ResponseEntity.ok(metricService.getMeanForMetric(metricName));
    }

    @GetMapping("/{metricName}/median")
    public ResponseEntity<Double> getMetricMedianValue(@PathVariable String metricName) {
        return ResponseEntity.ok(metricService.getMedianForMetric(metricName));
    }

    @GetMapping("/{metricName}/min")
    public ResponseEntity<Double> getMetricMinValue(@PathVariable String metricName) {
        return ResponseEntity.ok(metricService.getMinForMetric(metricName));
    }

    @GetMapping("/{metricName}/max")
    public ResponseEntity<Double> getMetricMaxValue(@PathVariable String metricName) {
        return ResponseEntity.ok(metricService.getMaxForMetric(metricName));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String handleValidationExceptions(RuntimeException ex) {
        return ex.getMessage();
    }
}
