package com.example.metrictracker.exception;

/**
 * The MetricNotFoundException is used to indicate that a metric being searched for with the
 * provided name does not exist
 */
public class MetricNotFoundException extends RuntimeException {

    public MetricNotFoundException(){
        super("The metric with the given name does not exist");
    }
}
