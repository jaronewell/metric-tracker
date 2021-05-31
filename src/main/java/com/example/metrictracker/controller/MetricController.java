package com.example.metrictracker.controller;

import com.example.metrictracker.exception.MetricHasNoValuesException;
import com.example.metrictracker.model.Metric;
import com.example.metrictracker.service.MetricService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/metrics")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    // Completes in O(1) to create a metric
    @PostMapping
    public ResponseEntity<Metric> createNewMetric(@RequestBody Metric metric) {
        return ResponseEntity.ok(metricService.createNewMetric(metric));
    }

    // Completes in O(n) dependent on number of values added
    @PostMapping("/{metricName}/values")
    public ResponseEntity<Metric> postValueToMetric(@PathVariable String metricName, @RequestBody Collection<Double> values) {
        return ResponseEntity.ok(metricService.addValuesToMetric(metricName, values));
    }

    //Completes in O(n) to get all metrics
    @GetMapping()
    public ResponseEntity<Collection<Metric>> getAllMetrics(){
        return ResponseEntity.ok(metricService.getAllMetrics());
    }

    // Completes in O(n) to add all elements in the values and divide by the count
    @GetMapping("/{metricName}/mean")
    public ResponseEntity<Double> getMetricMeanValue(@PathVariable String metricName) {
        return ResponseEntity.ok(metricService.getMeanForMetric(metricName));
    }

    // Completes in O(n log n) to sort the values and pick the middle
    @GetMapping("/{metricName}/median")
    public ResponseEntity<Double> getMetricMedianValue(@PathVariable String metricName) {
        return ResponseEntity.ok(metricService.getMedianForMetric(metricName));
    }

    // Completes in O(n) to check each element in the values for the min
    @GetMapping("/{metricName}/min")
    public ResponseEntity<Double> getMetricMinValue(@PathVariable String metricName) {
        return ResponseEntity.ok(metricService.getMinForMetric(metricName));
    }

    // Completes in O(n) to check each element in the values for the max
    @GetMapping("/{metricName}/max")
    public ResponseEntity<Double> getMetricMaxValue(@PathVariable String metricName) {
        return ResponseEntity.ok(metricService.getMaxForMetric(metricName));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MetricHasNoValuesException.class)
    public String handleExceptions(MetricHasNoValuesException ex) {
        return ex.getMessage();
    }

}
