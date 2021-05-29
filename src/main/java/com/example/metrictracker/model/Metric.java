package com.example.metrictracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
public class Metric {

    private String metricName;
    private Collection<Double> values;

    public Metric(String metricName) {
        this.metricName = metricName;
        values = new ArrayList<>();
    }
}
