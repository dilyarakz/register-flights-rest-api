package com.assessment.flights;

import com.assessment.flights.model.*;
import com.assessment.flights.utilities.FileReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HttpRequestTest {

    @Autowired
    private MockMvc mvc;


    private static final String URL = "http://localhost:8080/api/";

    private static List<String> eventsObj;
    private static List<String> resultsObj;

    @BeforeClass
    public static void beforeClass() throws Exception {
        FileReader<Event> fileReaderEvents = new FileReader();
        FileReader<Dates> fileReaderDates = new FileReader();
        ObjectMapper mapper = new ObjectMapper();
        eventsObj = fileReaderEvents.jsonReader("events.json", new Event());
        resultsObj = fileReaderDates.jsonReader("results.json", new Dates());
    }

    @Test
    public void testAddTicket() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(0))
                                .content(eventsObj.get(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("success"));
    }

    @Test
    public void testAddTicketDoubleId() throws Exception {

        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(0)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("success"));

        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(0)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("ticketId already exists"));
    }

    @Test
    public void testAddTicketandGetFlightsDateChange() throws Exception {
        System.out.println(resultsObj.get(0));
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("success"));

        mvc.perform(MockMvcRequestBuilders.get(URL+"flights?startDate=2022-01-03&endDate=2022-01-04")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultsObj.get(0)));

        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(3)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("success"));

        mvc.perform(MockMvcRequestBuilders.get(URL+"flights?startDate=2022-01-03&endDate=2022-01-04")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultsObj.get(1)));
    }

    @Test
    public void testAddTicketSeatAlreadyExists() throws Exception {

        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(4)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("success"));

        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(5)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("seatNumber already taken"));
    }

    @Test
    public void testInvalidInputEvent() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(6)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("Event object can not be empty"));
    }

    @Test
    public void testInvalidInputTicketId() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(7)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("Ticket Id can not be empty or zero"));
    }

    @Test
    public void testInvalidInputFlightDate() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(8)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("date can not be empty"));
    }

    @Test
    public void testInvalidInputWrongFormatFlightDate() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(9)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("date format is invalid"));
    }

    @Test
    public void testInvalidInputFlightNumberEmpty() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(10)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("Flight number can not be empty"));
    }

    @Test
    public void testInvalidInputFlightNumberAlphanumeric() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(11)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("Flight number must be alphanumeric"));
    }

    @Test
    public void testInvalidInputSeatNumber() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(12)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("Seat number can not be empty"));
    }

    @Test
    public void testInvalidInputSeatNumberAlphanumeric() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(13)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("Seat number must be alphanumeric"));
    }


    @Test
    public void testInvalidInputTicketCost() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post(URL+"tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(eventsObj.get(14)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(".status").value("failed"))
                .andExpect(MockMvcResultMatchers.jsonPath(".reason").value("Flight cost must be more than 0"));
    }

}
