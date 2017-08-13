package io.tchepannou.k.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tchepannou.k.travel.client.request.SearchScheduleRequest;
import io.tchepannou.k.travel.dao.ScheduleDao;
import io.tchepannou.k.travel.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ScheduleDao scheduleDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        new ResourceDatabasePopulator(
                new ClassPathResource("/sql/clean.sql"),
                new ClassPathResource("/sql/ScheduleController.sql")
        ).execute(dataSource);
    }


    // Search
    @Test
    public void shouldSearch() throws Exception {
        // Given
        final SearchScheduleRequest req = new SearchScheduleRequest();
        req.setDepartureDateTime(DateUtil.toDate("2017-01-01", "yyyy-MM-dd"));
        req.setDestinationCityId(102);
        req.setOriginCityId(101);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/schedules/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schedules.length()", is(4)))
                .andExpect(jsonPath("$..id", hasItems(1000, 1001, 1200, 2000)))
        ;
    }

    @Test
    public void shouldSearchByProvider() throws Exception {
        // Given
        final SearchScheduleRequest req = new SearchScheduleRequest();
        req.setDepartureDateTime(DateUtil.toDate("2017-01-01", "yyyy-MM-dd"));
        req.setDestinationCityId(102);
        req.setOriginCityId(101);
        req.setTravelProviderId(1);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/schedules/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schedules.length()", is(3)))
                .andExpect(jsonPath("$..id", hasItems(1000, 1001, 1200)))
        ;
    }

    @Test
    public void shouldSearchByAssetType() throws Exception {
        // Given
        final SearchScheduleRequest req = new SearchScheduleRequest();
        req.setDepartureDateTime(DateUtil.toDate("2017-01-01", "yyyy-MM-dd"));
        req.setDestinationCityId(102);
        req.setOriginCityId(101);
        req.setAssetTypeName("BUS");

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/schedules/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schedules.length()", is(3)))
                .andExpect(jsonPath("$..id", hasItems(1000, 1001, 2000)))
        ;
    }
}
