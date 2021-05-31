package com.example.metrictracker.controller;

import com.example.metrictracker.exception.MetricHasNoValuesException;
import com.example.metrictracker.exception.MetricNotFoundException;
import com.example.metrictracker.model.Metric;
import com.example.metrictracker.service.MetricService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class MetricControllerTest {

  @Autowired private MetricController metricController;

  @MockBean private MetricService metricService;

  @Autowired private MockMvc mockMvc;

  @Test
  void createNewMetricSuccessful() throws Exception {
    Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0)));

    String testMetricString =
        "{\n"
            + "    \"metricName\": \"test-metric\",\n"
            + "    \"values\": [\n"
            + "        7.0,\n"
            + "        2.0,\n"
            + "        6.0\n"
            + "    ]\n"
            + "}";

    Mockito.when(metricService.createNewMetric(any())).thenReturn(testMetric);

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/metrics")
                    .content(testMetricString)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString())
        .isEqualToIgnoringWhitespace(testMetricString);
  }

  @Test
  void postValuesToMetricSuccessful() throws Exception {
    Metric testMetric = new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0)));

    String testValuesString = "[2.0, 4.0]";
    String expectedResponseString =
        "{\n"
            + "    \"metricName\": \"test-metric\",\n"
            + "    \"values\": [\n"
            + "        7.0,\n"
            + "        2.0,\n"
            + "        6.0\n"
            + "    ]\n"
            + "}";

    Mockito.when(metricService.addValuesToMetric(eq("test-metric"), any())).thenReturn(testMetric);

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/metrics/test-metric/values")
                    .content(testValuesString)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString())
        .isEqualToIgnoringWhitespace(expectedResponseString);
  }

  @Test
  void getAllMetricsSuccessful() throws Exception {
    Collection<Metric> metrics =
        Collections.singletonList(
            new Metric("test-metric", new ArrayList<>(Arrays.asList(7.0, 2.0, 6.0))));

    String expectedResponseString =
        "[{\n"
            + "    \"metricName\": \"test-metric\",\n"
            + "    \"values\": [\n"
            + "        7.0,\n"
            + "        2.0,\n"
            + "        6.0\n"
            + "    ]\n"
            + "}]";

    Mockito.when(metricService.getAllMetrics()).thenReturn(metrics);

    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/metrics"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString())
        .isEqualToIgnoringWhitespace(expectedResponseString);
  }

  @Test
  void getMetricMeanValueSuccessful() throws Exception {
    Mockito.when(metricService.getMeanForMetric("test-metric")).thenReturn(5.0);

    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/metrics/test-metric/mean"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString()).isEqualTo("5.0");
  }

  @Test
  void getMetricMedianValueSuccessful() throws Exception {
    Mockito.when(metricService.getMedianForMetric("test-metric")).thenReturn(6.0);

    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/metrics/test-metric/median"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString()).isEqualTo("6.0");
  }

  @Test
  void getMetricMinValueSuccessful() throws Exception {
    Mockito.when(metricService.getMinForMetric("test-metric")).thenReturn(2.0);

    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/metrics/test-metric/min"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString()).isEqualTo("2.0");
  }

  @Test
  void getMetricMaxValueSuccessful() throws Exception {
    Mockito.when(metricService.getMaxForMetric("test-metric")).thenReturn(7.0);

    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/metrics/test-metric/max"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    assertThat(result.getResponse().getContentAsString()).isEqualTo("7.0");
  }

  @Test
  void exceptionIsHandledIfMetricContainsNoValuesAsInternalServerError() throws Exception {
    Mockito.when(metricService.getMaxForMetric("test-metric"))
        .thenThrow(new MetricHasNoValuesException());

    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/metrics/test-metric/max"))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andReturn();

    assertThat(result.getResponse().getContentAsString())
        .isEqualTo("The provided metric does not have any values associated with it");
  }

  @Test
  void allOtherExceptionsAreHandledAsBadRequest() throws Exception {
    Mockito.when(metricService.getMaxForMetric("test-metric"))
            .thenThrow(new MetricNotFoundException("test-metric"));

    MvcResult result =
            mockMvc
                    .perform(MockMvcRequestBuilders.get("/metrics/test-metric/max"))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();

    assertThat(result.getResponse().getContentAsString())
            .isEqualTo("The metric with the name \"test-metric\" does not exist");
  }
}
