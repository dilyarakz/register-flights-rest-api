package com.assessment.flights;

import com.assessment.flights.exceptions.CustomIllegalArgumentException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class invalidInputs {

    @Autowired
    private MockMvc mvc;

    private static final String URL = "http://localhost:8080/api/";

    @Test
    public void testGetFlightsWithEmptyStartDate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL+"flights?endDate=2022-01-01")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomIllegalArgumentException))
                .andExpect(result -> Assertions.assertEquals("startDate is empty", result.getResolvedException().getMessage()));
    }

    @Test
    public void testGetFlightsMethodWithStartDateWrongFormat() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL+"flights?startDate=2022/01/05&endDate=20-01-2001")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomIllegalArgumentException))
                .andExpect(result -> Assertions.assertEquals("startDate format is invalid", result.getResolvedException().getMessage()));
    }

    @Test
    public void testGetFlightsEndDateEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL+"flights?startDate=2022-01-05")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomIllegalArgumentException))
                .andExpect(result -> Assertions.assertEquals("endDate is empty", result.getResolvedException().getMessage()));
    }

    @Test
    public void testGetFlightsMethodWithEndDateFormatInvalid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL+"flights?startDate=2022-01-05&endDate=20/01/2001")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomIllegalArgumentException))
                .andExpect(result -> Assertions.assertEquals("endDate format is invalid", result.getResolvedException().getMessage()));
    }

    @Test
    public void testGetFlightsMethodWithInvalidDatesBadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL+"flights?startDate=2022-01-05&endDate=2022-01-01")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomIllegalArgumentException))
                .andExpect(result -> Assertions.assertEquals("endDate cannot be before startDate", result.getResolvedException().getMessage()));
    }

    @Test
    public void testGetFlightsMethodWithEndDateWrongFormat() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL+"flights?startDate=2022-01-05&endDate=20-01-2001")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomIllegalArgumentException))
                .andExpect(result -> Assertions.assertEquals("endDate format is invalid", result.getResolvedException().getMessage()));
    }



}
