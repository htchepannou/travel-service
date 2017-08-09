package io.tchepannou.k.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tchepannou.k.travel.client.request.CreateProductRequest;
import io.tchepannou.k.travel.client.request.CreateProductResponse;
import io.tchepannou.k.travel.client.request.ScheduledTimeRequest;
import io.tchepannou.k.travel.client.request.UpdateProductRequest;
import io.tchepannou.k.travel.client.response.UpdateProductResponse;
import io.tchepannou.k.travel.dao.ProductDao;
import io.tchepannou.k.travel.dao.ScheduledTimeDao;
import io.tchepannou.k.travel.domain.Product;
import io.tchepannou.k.travel.domain.ScheduledTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ScheduledTimeDao scheduledTimeDao;

    private ObjectMapper mapper = new ObjectMapper();

    private DateFormat df = new SimpleDateFormat("HH:mm");

    @Before
    public void setUp(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        new ResourceDatabasePopulator(
                new ClassPathResource("/sql/clean.sql"),
                new ClassPathResource("/sql/ProductController.sql")
        ).execute(dataSource);
    }

    @Test
    public void findById() throws Exception {
        mockMvc
                .perform(get("/travel/v1/product/100"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.name", is("FLEX")))
                .andExpect(jsonPath("$.product.description", is("Flexible Ticket")))
                .andExpect(jsonPath("$.product.originCityId", is(101)))
                .andExpect(jsonPath("$.product.destinationCityId", is(102)))
                .andExpect(jsonPath("$.product.travelProviderId", is(1)))

                .andExpect(jsonPath("$.product.productType.id", is(100)))
                .andExpect(jsonPath("$.product.productType.name", is("BUS_SEAT")))

                .andExpect(jsonPath("$.product.schedule.length()", is(3)))
                .andExpect(jsonPath("$.product.schedule[0].dayOfWeek", is(0)))
                .andExpect(jsonPath("$.product.schedule[0].departureTime", is("05:00")))
                .andExpect(jsonPath("$.product.schedule[0].arrivalTime", is("08:00")))
                .andExpect(jsonPath("$.product.schedule[1].dayOfWeek", is(1)))
                .andExpect(jsonPath("$.product.schedule[1].departureTime", is("08:30")))
                .andExpect(jsonPath("$.product.schedule[1].arrivalTime", is("11:30")))
                .andExpect(jsonPath("$.product.schedule[2].dayOfWeek", is(1)))
                .andExpect(jsonPath("$.product.schedule[2].departureTime", is("10:30")))
                .andExpect(jsonPath("$.product.schedule[2].arrivalTime", is("13:30")))
        ;
    }

    @Test
    public void findByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/travel/v1/product/9999999"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        // Given
        final CreateProductRequest req = new CreateProductRequest();
        req.setName("CreateProductRequest");
        req.setDescription("Description of product");
        req.setProductTypeName("BUS_SEAT");
        req.setOriginCityId(1);
        req.setDestinationCityId(2);
        req.setTravelProviderId(3);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        final String jsonResponse = mockMvc
                .perform(
                        post("/travel/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
        CreateProductResponse resp = mapper.readValue(jsonResponse, CreateProductResponse.class);

        // Then
        Product product = productDao.findOne(resp.getId());
        assertThat(product.getName()).isEqualTo("CreateProductRequest");
        assertThat(product.getDescription()).isEqualTo("Description of product");
        assertThat(product.getOriginCityId()).isEqualTo(1);
        assertThat(product.getDestinationCityId()).isEqualTo(2);
        assertThat(product.getTravelProdiverId()).isEqualTo(3);
        assertThat(product.getTravelProductTypeId()).isEqualTo(100);
    }

    @Test
    public void shouldReturn400WhenCreatingProductWithInvalidTypeName() throws Exception {
        // Given
        final CreateProductRequest req = new CreateProductRequest();
        req.setName("test");
        req.setDescription("Description of product");
        req.setProductTypeName("xxxxx");
        req.setOriginCityId(1);
        req.setDestinationCityId(2);
        req.setTravelProviderId(3);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                ;
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        // Given
        final UpdateProductRequest req = new UpdateProductRequest();
        req.setName("UpdateProductRequest");
        req.setDescription("Description of product");
        req.setOriginCityId(201);
        req.setDestinationCityId(202);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        final String jsonResponse = mockMvc
                .perform(
                        post("/travel/v1/product/200")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
        UpdateProductResponse resp = mapper.readValue(jsonResponse, UpdateProductResponse.class);

        // Then
        Product product = productDao.findOne(resp.getId());
        assertThat(product.getName()).isEqualTo("UpdateProductRequest");
        assertThat(product.getDescription()).isEqualTo("Description of product");
        assertThat(product.getOriginCityId()).isEqualTo(201);
        assertThat(product.getDestinationCityId()).isEqualTo(202);
        assertThat(product.getTravelProdiverId()).isEqualTo(2);
        assertThat(product.getTravelProductTypeId()).isEqualTo(100);
    }

    @Test
    public void shouldReturn404WhenUpdatingProductWithInvalidId() throws Exception {
        // Given
        final UpdateProductRequest req = new UpdateProductRequest();
        req.setName("shouldReturn404WhenUpdatingProductWithInvalidId");
        req.setDescription("Description of product");
        req.setOriginCityId(201);
        req.setDestinationCityId(202);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/999999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                ;
    }

    @Test
    public void shouldScheduleTime() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("10:30");
        req.setDepartureTime("08:30");
        req.setDayOfWeek(1);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/300/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        // Then
        List<ScheduledTime> schedule = scheduledTimeDao.findByProductId(300, new PageRequest(0, 1000));
        assertThat(schedule).hasSize(1);
        assertThat(schedule.get(0).getArrivalTime().getTime()).isEqualTo(df.parse("10:30").getTime());
        assertThat(schedule.get(0).getDepartureTime().getTime()).isEqualTo(df.parse("08:30").getTime());
        assertThat(schedule.get(0).getDayOfWeek()).isEqualTo(1);

    }

    @Test
    public void shouldScheduleTimeTwice() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("11:30");
        req.setDepartureTime("08:30");
        req.setDayOfWeek(1);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/400/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void shouldReturn404WhenSchedulingTimeForInvalidProduct() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("10:30");
        req.setDepartureTime("08:30");
        req.setDayOfWeek(1);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/9999999/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void shouldUnscheduleTime() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("11:30");
        req.setDepartureTime("08:30");
        req.setDayOfWeek(1);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        delete("/travel/v1/product/500/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        // Then
        List<ScheduledTime> schedule = scheduledTimeDao.findByProductId(500, new PageRequest(0, 1000));
        assertThat(schedule).isEmpty();
    }

    @Test
    public void shouldReturn404WhenUnschedulingTimeForInvalidProduct() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("10:30");
        req.setDepartureTime("08:30");
        req.setDayOfWeek(1);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        delete("/travel/v1/product/9999999/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
        ;
    }


    @Test
    public void shouldReturn404WhenUnschedulingTimeForTime() throws Exception {
        // Given
        final ScheduledTimeRequest req = new ScheduledTimeRequest();
        req.setArrivalTime("1:30");
        req.setDepartureTime("1:29");
        req.setDayOfWeek(1);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        delete("/travel/v1/product/500/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
        ;
    }
}
