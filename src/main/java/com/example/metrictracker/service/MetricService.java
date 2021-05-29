package com.example.metrictracker.service;

import com.example.metrictracker.model.Metric;
import com.example.metrictracker.repository.MetricRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MetricService {

    private final MetricRepository metricRepository;

    public MetricService(MetricRepository metricRepository){
        this.metricRepository = metricRepository;
    }

    public Metric createNewMetric(Metric metric){
        if(metric.getValues() == null){
            metric.setValues(new ArrayList<>());
        }
       return metricRepository.saveNewMetric(metric);
    }

    public Metric addValuesToMetric(String metricName, Collection<Double> values){
        return metricRepository.addMetricValues(metricName, values);
    }

    public Double getMeanForMetric(String metricName){
        return 0.0;
    }

    public Double getMedianForMetric(String metricName){
        return 0.0;
    }

    public Double getMinForMetric(String metricName){
        return 0.0;
    }

    public Double getMaxForMetric(String metricName){
        return 0.0;
    }

}
