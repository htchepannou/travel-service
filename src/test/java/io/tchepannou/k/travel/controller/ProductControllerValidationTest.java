package io.tchepannou.k.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tchepannou.k.travel.client.request.ScheduledTimeRequest;
import io.tchepannou.k.travel.exception.BusinessErrors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerValidationTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    //-- Schedule
    @Test
    public void testScheduleNullDayOfWeek() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("11:30");
        req.setDepartureTime("08:30");
        req.setDayOfWeek(null);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/500/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].code", is(BusinessErrors.BAD_REQUEST.getCode())))
                .andExpect(jsonPath("$.errors[0].description", is("dayOfWeek=null - may not be null")))
        ;
    }

    @Test
    public void testScheduleMinDayOfWeek() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("11:30");
        req.setDepartureTime("08:30");
        req.setDayOfWeek(-1);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/500/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].code", is(BusinessErrors.BAD_REQUEST.getCode())))
                .andExpect(jsonPath("$.errors[0].description", is("dayOfWeek=-1 - must be greater than or equal to 0")))
        ;
    }

    @Test
    public void testScheduleMaxDayOfWeek() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("11:30");
        req.setDepartureTime("08:30");
        req.setDayOfWeek(7);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/500/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].code", is(BusinessErrors.BAD_REQUEST.getCode())))
                .andExpect(jsonPath("$.errors[0].description", is("dayOfWeek=7 - must be less than or equal to 6")))
        ;
    }

    @Test
    public void testScheduleDepartureBadFormat() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("11:30");
        req.setDepartureTime("2015-01-01");
        req.setDayOfWeek(0);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/500/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].code", is(BusinessErrors.INVALID_DEPARTURE_TIME_FORMAT.getCode())))
                .andExpect(jsonPath("$.errors[0].description", is("departureTime format is invalid. Expecting HH:mm")))
        ;
    }

    @Test
    public void testScheduleArrivalBadFormat() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("2015-01-01");
        req.setDepartureTime("10:30");
        req.setDayOfWeek(0);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/500/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].code", is(BusinessErrors.INVALID_ARRIVAL_TIME_FORMAT.getCode())))
                .andExpect(jsonPath("$.errors[0].description", is("arrivalTime format is invalid. Expecting HH:mm")))
        ;
    }
}
