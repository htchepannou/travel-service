package io.tchepannou.k.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tchepannou.k.travel.client.request.SetPriceRequest;
import io.tchepannou.k.travel.client.response.SetPriceResponse;
import io.tchepannou.k.travel.dao.PriceDao;
import io.tchepannou.k.travel.domain.Price;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PriceDao priceDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        new ResourceDatabasePopulator(
                new ClassPathResource("/sql/clean.sql"),
                new ClassPathResource("/sql/PriceController.sql")
        ).execute(dataSource);}

    @Test
    public void findById() throws Exception {
        mockMvc
                .perform(get("/travel/v1/price/100"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price.id", is(100)))
                .andExpect(jsonPath("$.price.productId", is(100)))
                .andExpect(jsonPath("$.price.amount", is(101.0)))
                .andExpect(jsonPath("$.price.currencyCode", is("USD")))
                .andExpect(jsonPath("$.price.fromDate", is("2017-01-02")))
                .andExpect(jsonPath("$.price.toDate", is("2017-04-05")))

                .andExpect(jsonPath("$.price.priceType.id", is(1)))
                .andExpect(jsonPath("$.price.priceType.name", is("ONE_WAY")))

        ;
    }

    @Test
    public void shouldReturn404WhenSearchingInvalidPriceId() throws Exception {
        mockMvc
                .perform(get("/travel/v1/price/9999999"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void findByProductId() throws Exception {
        mockMvc
                .perform(get("/travel/v1/product/300/prices"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prices.length()", is(2)))
                .andExpect(jsonPath("$.prices[0].id", is(301)))
                .andExpect(jsonPath("$.prices[0].productId", is(300)))
                .andExpect(jsonPath("$.prices[0].amount", is(311.0)))
                .andExpect(jsonPath("$.prices[0].currencyCode", is("USD")))
                .andExpect(jsonPath("$.prices[0].fromDate", is("2017-01-02")))
                .andExpect(jsonPath("$.prices[0].toDate", is("2017-04-05")))
                .andExpect(jsonPath("$.prices[0].priceType.id", is(1)))
                .andExpect(jsonPath("$.prices[0].priceType.name", is("ONE_WAY")))

                .andExpect(jsonPath("$.prices[1].id", is(302)))
                .andExpect(jsonPath("$.prices[1].productId", is(300)))
                .andExpect(jsonPath("$.prices[1].amount", is(312.0)))
                .andExpect(jsonPath("$.prices[1].currencyCode", is("USD")))
                .andExpect(jsonPath("$.prices[1].fromDate", is("2017-01-03")))
                .andExpect(jsonPath("$.prices[1].toDate", is("2017-04-06")))
                .andExpect(jsonPath("$.prices[1].priceType.id", is(2)))
                .andExpect(jsonPath("$.prices[1].priceType.name", is("RETURN")))
        ;
    }


    @Test
    public void shouldCreatePrice() throws Exception {
        // Given
        final SetPriceRequest req = new SetPriceRequest();
        req.setAmount(150.0);
        req.setCurrencyCode("USD");
        req.setFromDate("2017-01-02");
        req.setToDate("2017-03-05");

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        final String jsonResponse = mockMvc
                .perform(
                        post("/travel/v1/product/200/prices/ONE_WAY")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
        SetPriceResponse resp = mapper.readValue(jsonResponse, SetPriceResponse.class);

        // Then
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Price price = priceDao.findOne(resp.getId());
        assertThat(df.format(price.getFromDate())).isEqualTo("2017-01-02");
        assertThat(df.format(price.getToDate())).isEqualTo("2017-03-05");
        assertThat(price.getCurrencyCode()).isEqualTo("USD");
        assertThat(price.getAmount()).isEqualTo(150.0);
        assertThat(price.getProductId()).isEqualTo(200);
    }

    @Test
    public void shouldUpdatePrice() throws Exception {
        // Given
        final SetPriceRequest req = new SetPriceRequest();
        req.setAmount(150.0);
        req.setCurrencyCode("USD");
        req.setFromDate("2017-01-02");
        req.setToDate("2017-03-05");

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        final String jsonResponse = mockMvc
                .perform(
                        post("/travel/v1/product/200/prices/RETURN")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
        SetPriceResponse resp = mapper.readValue(jsonResponse, SetPriceResponse.class);

        // Then
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Price price = priceDao.findOne(resp.getId());
        assertThat(df.format(price.getFromDate())).isEqualTo("2017-01-02");
        assertThat(df.format(price.getToDate())).isEqualTo("2017-03-05");
        assertThat(price.getCurrencyCode()).isEqualTo("USD");
        assertThat(price.getAmount()).isEqualTo(150.0);
        assertThat(price.getProductId()).isEqualTo(200);
    }

    @Test
    public void shouldReturn404WhenSettingPriceWithInvalidProductId() throws Exception {
        // Given
        final SetPriceRequest req = new SetPriceRequest();
        req.setAmount(150.0);
        req.setCurrencyCode("USD");

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
         mockMvc
                .perform(
                        post("/travel/v1/product/99999999/prices/RETURN")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                ;
    }


    @Test
    public void shouldReturn400WhenSettingPriceWithInvalidPriceType() throws Exception {
        // Given
        final SetPriceRequest req = new SetPriceRequest();
        req.setAmount(150.0);
        req.setCurrencyCode("USD");

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/200/prices/__INVALID__")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
        ;
    }
}
