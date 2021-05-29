package com.example.metrictracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class Metric {

    private String metricName;
    private Collection<Double> values;
}
