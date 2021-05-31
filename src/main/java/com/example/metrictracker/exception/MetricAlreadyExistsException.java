package com.example.metrictracker.exception;

/**
 * The MetricAlreadyExistsException is used to indicate a metric with the same name already exists
 * when trying to create a new metric with the same name
 */
public class MetricAlreadyExistsException extends RuntimeException {

    public MetricAlreadyExistsException(String metricName){
        super("The metric with the name \"" + metricName + "\" already exists");
    }
}
