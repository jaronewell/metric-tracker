package com.example.metrictracker.controller;

import com.example.metrictracker.service.MetricService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class MetricControllerTest {

    @Autowired
    private MetricController metricController;

    @MockBean private MetricService metricService;

    @Autowired private MockMvc mockMvc;

}
