package com.example.metrictracker.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InMemoryMetricRepositoryTest {

    @InjectMocks
    private InMemoryMetricRepository inMemoryMetricRepository;


    @Test
    void saveNewMetricSuccessful() {

    }

    @Test
    void saveNewMetricThrowsExceptionWhenMetricAlreadyExists() {

    }

    @Test
    void addMetricValuesSuccessful() {

    }

    @Test
    void getMetricByNameSuccessful() {

    }

    @Test
    void getMetricByNameThrowsExceptionIfMetricNotFound() {

    }
}
