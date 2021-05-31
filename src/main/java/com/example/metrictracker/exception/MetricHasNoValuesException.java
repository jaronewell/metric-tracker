package com.example.metrictracker.exception;

/**
 * The MetricHasNoValuesException is used to indicate that a metric has no values associated with
 * it when trying to get a statistic for that metric
 */
public class MetricHasNoValuesException extends RuntimeException {

    public MetricHasNoValuesException() {
        super("The provided metric does not have any values associated with it");
    }
}
